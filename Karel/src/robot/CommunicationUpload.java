package robot;

/**
 *
 * @author Michal Petříček
 */
public class CommunicationUpload extends Communication {

    public CommunicationUpload(ConnectionUDP connection, DataQueue dataQueue) {
        super(connection, dataQueue);
        cmd = Command.UPLOAD;
    }

    @Override
    public void closeConnection() {
        try {
            Packet finPacket = new Packet(connectionId, nextSeqToSend, nextAckToSend, Packet.FLAG_FIN, null);
            boolean success = false;
            do{
                connection.sendPacket(finPacket);
                Packet recvPacket = null;
                try {
                    recvPacket = connection.receivePacket();
                } catch (ConnectionTimeoutException timeoutEx) {                    
                }
                success = finPacket.equals(recvPacket);
                retries++;
                if(!success){                    
                }
            }while(!success && retries <= 20);
            
            if(!success){                
            }

        } catch (ConnectionException ex) {            
        }
    }

    @Override
    public boolean receivePacket() {
        //TODO
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void execute() throws CriticalException {
        //TODO
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
