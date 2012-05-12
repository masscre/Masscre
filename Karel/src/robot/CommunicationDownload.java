package robot;


/**
 *
 * @author Michal Petříček
 */
public class CommunicationDownload extends Communication {

    public CommunicationDownload(ConnectionUDP connection, DataQueue dataQueue) {
        super(connection, dataQueue);
        cmd = Command.DOWNLOAD;
    }

    protected boolean finReceived = false;
    private long lastSentTime = -1;
    
    @Override
    public boolean receivePacket() {
        Packet recvPacket = null;
        try {
            recvPacket = connection.receivePacket();
            if(lastSentTime != -1 && System.currentTimeMillis() - lastSentTime > COMM_TIMEOUT){
                throw new ConnectionTimeoutException("Timeout (" + retries + ")");
            }
        } catch (ConnectionTimeoutException ex) {
            //Timeout
            if(retries++ > COMM_RETRIES){                
                resetConnection();
                return false;
            }            
            if(finReceived) return sendFin();
            else return sendAck();
        } catch (ConnectionException ex) {
            //IO chyba nebo ukoncene spojeni            
            resetConnection();
            return false;
        }
        retries = 0;

        //Spravne ID spojeni:
        if (recvPacket.getConnectionId() != connectionId) {            
            return true;
        }

        //Syntaxe:
        if (!recvPacket.isOk()) {            
            resetConnection();
            return false;
        }
        if (recvPacket.getFlags() == Packet.FLAG_SYN) {            
            resetConnection();
            return false;
        }
        if (Packets.getDistance(lastReceivedAck, recvPacket.getAcknowledgeNumber()) > Packet.MAX_DATA_LENGTH
                && Packets.getDistance(lastReceivedAck, recvPacket.getAcknowledgeNumber()) != 65536){
            //klesajici ack            
            resetConnection();
            return false;
        }

        lastReceivedAck = recvPacket.getAcknowledgeNumber();
        lastReceivedSeq = recvPacket.getSequenceNumber();

        //Násilně ukončené spojení:
        if (recvPacket.getFlags() == Packet.FLAG_RST) {            
            opened = false;
            return false;
        }

        //Ukončení spojení:
        if (recvPacket.getFlags() == Packet.FLAG_FIN) {            
            transferSuccessful = true;
            closeConnection();
            return false;
        }

        //Prijat fin a po nem dalsi data:
        if(finReceived){            
            resetConnection();
            return false;
        }

        //Vlozit data do okenka:
        try {
            window.insertPacket(recvPacket);
        } catch (CriticalException ex) {            
            return false;
        } catch (WindowException ex) {            
        }
        

        //Presunout data v poradi do vystupu:
        if (window.isDataAvailable()) {
            byte[] data = window.getData();            
            dataQueue.insert(data);
            nextAckToSend = window.getNextAck();
            transferedBytes += data.length;
            printProgress();
        }

        //Odeslat ACK:
        return sendAck();
    }

    @Override
    public void closeConnection() {
        finReceived = true;
        opened = false;
        sendFin();
    }

    @Override
    public void execute() throws CriticalException {
        if(!opened){
            openConnection();
        }

        while(receivePacket()){}

    }
    private boolean sendAck(){
        try {
            connection.sendPacket(new Packet(connectionId, nextSeqToSend, nextAckToSend, Packet.FLAG_EMPTY, null));
            lastSentTime = System.currentTimeMillis();
        } catch (ConnectionException ex) {            
            return false;
        }
        return true;
    }

    private boolean sendFin(){
        try {
            connection.sendPacket(new Packet(connectionId, lastReceivedSeq, lastReceivedAck, Packet.FLAG_FIN, null));
            lastSentTime = System.currentTimeMillis();
        } catch (ConnectionException ex) {            
            return false;
        }
        return false;
    }


}
