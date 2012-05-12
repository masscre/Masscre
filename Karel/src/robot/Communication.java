package robot;


/**
 *
 * @author Michal Petříček
 */
public abstract class Communication {

    protected ConnectionUDP connection;
    protected int connectionId;
    protected Command cmd = null;

    protected short nextSeqToSend;
    protected short nextAckToSend;

    protected short lastReceivedSeq;
    protected short lastReceivedAck;

    protected DataQueue dataQueue;
    protected Window window;

    protected boolean opened = false;
    protected int retries = 0;

    protected long transferedBytes = 0;
    private long lastPrintedSize = 1;
    protected boolean transferSuccessful = false;

    public static final int INIT_COMM_TIMEOUT = 1000;
    public static final int INIT_COMM_RETRIES = 20;
    public static final int COMM_TIMEOUT = 1000;
    public static final int COMM_RETRIES = 20;

    public static enum Command {
        UPLOAD, DOWNLOAD
    }

    protected Communication(ConnectionUDP connection, DataQueue dataQueue) {
        this.connection = connection;
        this.window = new Window();
        this.dataQueue = dataQueue;
    }

    public static Communication getInstance (ConnectionUDP connection, DataQueue dataQueue, Command cmd){
        switch(cmd){
            case DOWNLOAD:
                return new CommunicationDownload(connection, dataQueue);
            case UPLOAD:
                return new CommunicationUpload(connection, dataQueue);
            default:
                return null;
        }
    }

    public boolean openConnection() throws CriticalException {
        boolean success = false;
        Packet firstPacket = getFirstPacket(cmd);

        int retryCount = 0;
        boolean retry = false;
        long sentTime = System.currentTimeMillis();
        try {
            connection.setTimeout(INIT_COMM_TIMEOUT);
            connection.sendPacket(firstPacket);
        } catch (ConnectionException ex) {            ;
            return false;
        }

        while (retryCount < INIT_COMM_RETRIES && !success) {
            try {
                if (retry) {
                    retry = false;
                    connection.sendPacket(firstPacket);
                    sentTime = System.currentTimeMillis();
                }

                Packet recvPacket = connection.receivePacket();

                if (recvPacket.getFlags() == Packet.FLAG_SYN
                        && recvPacket.getDataLength() == 1
                        && recvPacket.getData()[0] == getCommandType(cmd)
                        && recvPacket.getConnectionId() != 0) {
                    if (recvPacket.isOk()) {
                        success = true;
                        connectionId = recvPacket.getConnectionId();
                        lastReceivedAck = recvPacket.getAcknowledgeNumber();
                        lastReceivedSeq = recvPacket.getSequenceNumber();
                    } else {                        
                        return false;
                    }
                } else if (System.currentTimeMillis() - sentTime < INIT_COMM_TIMEOUT) {                    
                } else {
                    retryCount++;
                    retry = true;                    
                }
            } catch (ConnectionTimeoutException ex) {
                retryCount++;
                retry = true;                
            } catch (ConnectionException ex) {
                retryCount++;
                retry = true;                
            }
        }

        if (!success) {            
            return false;
        }

        opened = true;

        return true;
    }

    public abstract void closeConnection();

    public void resetConnection() {
        try {
            connection.sendPacket(new Packet(connectionId, nextSeqToSend, nextAckToSend, Packet.FLAG_RST, null));
        } catch (ConnectionException ex) {            
        }
        opened = false;
    }

    public abstract boolean receivePacket();

    public abstract void execute() throws CriticalException;

    public boolean isOpened() {
        return opened;
    }

    public boolean isTransferSuccessful() {
        return transferSuccessful;
    }

    public int getConnectionId() {
        return connectionId;
    }

    /**
     * Počet úspěšně přenesených bytů.
     * @return
     */
    public long getTransferedBytes() {
        return transferedBytes;
    }

    protected void printProgress(){
        if(transferedBytes / (50*1024) == lastPrintedSize){            
            lastPrintedSize++;
        }
    }

    private static Packet getFirstPacket(Command cmd) throws CriticalException {
        return new Packet(0, 0, 0, Packet.FLAG_SYN, new byte[]{getCommandType(cmd)});
    }

    private static byte getCommandType(Command cmd) throws CriticalException {
        switch (cmd) {
            case DOWNLOAD:
                return Packet.CMD_DOWNLOAD;
            case UPLOAD:
                return Packet.CMD_UPLOAD;
            default:
                throw new CriticalException("Neznamy prikaz: " + cmd);
        }
    }
}
