package robot;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;


/**
 *
 * @author Michal Petříček
 */
public class ConnectionUDP {

    private int port;
    InetAddress address;
    String addressName;
    private DatagramSocket socket;

    public ConnectionUDP(String address, int port) {
        this.addressName = address;
        this.port = port;
    }

    public void connect() throws CriticalException{
        if(isConnected()){
            throw new CriticalException("Spojeni je jiz vytvoreno");
        }

        if(port < 0 || port > 65535){
            throw new CriticalException("Nepovolene cislo portu: " + port);
        }
        try {
            address = Inet4Address.getByName(addressName);
        } catch (UnknownHostException ex) {
            throw new CriticalException("Neznama adresa: " + address);
        }

        try {
            socket = new DatagramSocket();
        } catch (SocketException ex) {
            throw new CriticalException("Nelze vytvorit socket: " + ex.getMessage());
        }
    }

    public void disconnect(){
        if(socket == null){
            return;
        }

        socket.close();
    }

    public boolean isConnected(){
        if(socket == null || socket.isClosed()){
            return false;
        }

        return true;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public void setPort(int port) throws CriticalException {
        if(isConnected()){
            throw new CriticalException("Nelze zmenit port: Spojeni je aktivni");
        }
        this.port = port;
    }

    public void setTimeout(int timeout) throws ConnectionException{
        if(socket == null){
            throw new ConnectionException("Spojeni neni navazano");
        }
        
        try {
            socket.setSoTimeout(timeout);
        } catch (SocketException ex) {
            throw new ConnectionException("Nepovedlo se nastavit novy timeout");
        }
    }

    /**
     * Odeslání packetu
     * @param packet Packet k odeslání
     * @throws IOException
     */
    public void sendPacket(Packet packet) throws ConnectionException {
        if (packet == null) {
            return;
        }
        packet.setTime();
        DatagramPacket datagramPacket = new DatagramPacket(packet.getPacket(), packet.getSize(), address, port);
        try {            
            socket.send(datagramPacket);
        } catch (IOException ex) {
            throw new ConnectionException("Chyba pri odesilani packetu: " + ex.getMessage());
        }
        System.out.println("SEND: "+packet);
    }

    /**
     * 
     * @return
     * @throws ConnectionTimeoutException Timeout přijímání packetu
     * @throws ConnectionException Chyba při přijímání packetu
     */
    public Packet receivePacket() throws ConnectionException, ConnectionTimeoutException {
        byte[] data = new byte[Packet.MAX_PACKET_LENGTH];
        DatagramPacket datagramPacket = new DatagramPacket(data, Packet.MAX_PACKET_LENGTH);
        try {
            socket.receive(datagramPacket);
        } catch (SocketTimeoutException ex){
            throw new ConnectionTimeoutException("Timeout");
        } catch (SocketException ex){
            throw new ConnectionException("Pri prijimani packetu doslo k chybe: " + ex.getMessage());
        } catch (IOException ex) {
            throw new ConnectionException("Pri prijimani packetu doslo k chybe: " + ex.getMessage());
        }
        
        Packet packet =  new Packet(data, datagramPacket.getLength());  
        System.out.println("RECEIVE: "+packet);
        return packet;
    }
}
