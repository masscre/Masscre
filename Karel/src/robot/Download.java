package robot;

public class Download {    
    
    public DataQueue dataQueue;
    private FileWriter fileWriter = null;
    private Thread fileWriterThread = null;
    private ConnectionUDP connection = null;
    private Communication communication = null;
    
    private ShutdownHook shutdownHook = new ShutdownHook();
    
    Download(String ip) throws CriticalException {
        Runtime.getRuntime().addShutdownHook(new Thread(shutdownHook));
        dataQueue = new DataQueue();
        System.out.println("INFO: Zacina stahovani fotky");
        fileWriter = new FileWriter(dataQueue);
        fileWriterThread = new Thread(fileWriter);
        fileWriterThread.start();
        connection = new ConnectionUDP(ip, 4000);
        connection.connect();
        communication = Communication.getInstance(connection, dataQueue, Communication.Command.DOWNLOAD);
        communication.execute();
        shutdown();
    }
    
    public void shutdown() {
        shutdownHook.run();
    }
    
    private class ShutdownHook implements Runnable {

        public ShutdownHook() {
        }

        public void run() {
            System.out.println("INFO: Application exitting...");

            if (communication != null && communication.isOpened()) {
                System.out.println("INFO: Communication is active, closing down...");
                try {
                    communication.resetConnection();
                } catch (Exception ex) {
                    System.out.println("ERR:  Communication could not be closed: " + ex.getMessage());
                }
            }

            if (connection != null && connection.isConnected()) {
                System.out.println("INFO: Connection is active, disconnecting...");
                try {
                    connection.disconnect();
                } catch (Exception ex) {
                    System.out.println("ERR:  Connection could not be disconnected: " + ex.getMessage());
                }
            }

            if (fileWriter != null && fileWriterThread != null) {
                System.out.println("INFO: File Writer is active, shutting down...");
                fileWriter.close();
                fileWriterThread.interrupt();
            }

            System.out.println("Logger shutting down...");            
        }
    }
}


