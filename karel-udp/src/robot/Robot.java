package robot;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Robot {
    
    private static Download download;
    private static Upload upload;
    
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException {
        if (args.length == 1) {
            System.out.println("Program bude downloadovat fotku.");
            String ip = args[0];            
            Robot.download = new Download(ip);
        } else if (args.length == 2) {
            System.out.println("Program bude uploadovat firmware.");
            String ip = args[0];
            String path = args[1];
            Robot.upload = new Upload();
        } else {
            Scanner in = new Scanner(System.in);
            System.out.println("Download - 1, Upload - 2");
            int x = in.nextInt();
            if (x == 1) {
                System.out.println("Program je v roli downloadu fotky.");
                System.out.print("Zadejte ip: ");
                String ip = in.next();
                Robot.download = new Download(ip);
            }
            if (x == 2) {
                System.out.println("Program je v roli uploadovani firmware.");
                System.out.print("Zadejte ip adresu: ");
                String ip = in.next();
                System.out.print("Zadejte cestu k firmware: ");
                String path = in.next();
                Robot.upload = new Upload();
            }
        }
    }
    
}

class Download {
    private String ip;
    DatagramSocket  socket;
    DatagramPacket  packetUDP;
    InetAddress     address, fromAddress;
    String			messageString;
    byte[]          message = new byte[255];
    int             fromPort, port = 4000;
    
    Download(String ip) throws SocketException, UnknownHostException, IOException {
        this.ip = ip;        
        socket = new DatagramSocket();
        address = InetAddress.getByName(ip);  
        Packet packet = Packet.getFirstPacket(); 
        System.out.println(packet);
        packetUDP = new DatagramPacket(packet.getPacket(),packet.getSize(),
                                    address, port);
        socket.send(packetUDP);
        System.out.println("Sending packet: "+packet);
        
        boolean cycle = true;
        
        while(cycle == true) {           
        
        packetUDP = new DatagramPacket(message,
                                message.length);
        socket.receive(packetUDP);
        byte[] data = new byte[Packet.MAX_PACKET_LENGTH];
        data = packetUDP.getData();
        Packet respond =  new Packet(data, packetUDP.getLength());
        System.out.println("respond: "+respond.toString());    
        
        
        
        }
        
        socket.close();
    }
    
   
}

class Upload {
    Upload() {
        System.out.println("Upload start");
    }
}



class Packet {
    
    //prepsat
    
public static final int MAX_PACKET_LENGTH = 265;
public static final int MAX_DATA_LENGTH = 256;
public static final byte FLAG_EMPTY = 0;
public static final byte FLAG_SYN = 1;
public static final byte FLAG_FIN = 2;
public static final byte FLAG_RST = 4;
public static final byte CMD_DOWNLOAD = 0x01;
public static final byte CMD_UPLOAD = 0x02;
private int connectionId;
private short sequenceNumber;
private short acknowledgeNumber;
private byte flags;
private byte[] data;

private long time;
    
    public Packet(int connectionId, short sequenceNumber, short acknowledgeNumber, byte flags, byte[] data) {
        this.connectionId = connectionId;
        this.sequenceNumber = sequenceNumber;
        this.acknowledgeNumber = acknowledgeNumber;
        this.flags = flags;
        if(data == null){
            this.data = new byte[0];
        }else{
            this.data = data;
        }
        this.time = 0;
    }
    
    public Packet(int connectionId, int sequenceNumber, int acknowledgeNumber, int flags, byte[] data) {
        this(connectionId, (short) sequenceNumber, (short) acknowledgeNumber, (byte)flags, data);
    }

    static Packet getFirstPacket() {
        return new Packet(0, 0, 0, Packet.FLAG_SYN, new byte[]{Packet.CMD_DOWNLOAD});
    }
    
    public Packet(byte[] incoming, int length){
        time = System.currentTimeMillis();
        if(incoming.length < 9){
            connectionId = 0;
            sequenceNumber = 0;
            acknowledgeNumber = 0;
            flags = 0;
            return;
        }
        connectionId = (incoming[0] << 24) & 0xFF000000
                     | (incoming[1] << 16) & 0x00FF0000
                     | (incoming[2] << 8 ) & 0x0000FF00
                     |  incoming[3]        & 0x000000FF;
        sequenceNumber = (short)((incoming[4] << 8) & 0xFF00 | incoming[5] & 0x00FF);
        acknowledgeNumber = (short)((incoming[6] << 8) & 0xFF00 | incoming[7] & 0x00FF);
        flags = incoming[8];
        data = new byte[length - 9];
        for (int i = 0; i < length - 9; i++) {
            data[i] = incoming[i + 9];
        }
    }
    
    public byte[] getPacket(){
        // prepsat
        byte[] packet = new byte[9 + data.length];
        packet[0] = (byte) (connectionId >> 24);
        packet[1] = (byte) (connectionId >> 16);
        packet[2] = (byte) (connectionId >> 8);
        packet[3] = (byte) connectionId;

        packet[4] = (byte) (sequenceNumber >> 8);
        packet[5] = (byte) sequenceNumber;

        packet[6] = (byte) (acknowledgeNumber >> 8);
        packet[7] = (byte) acknowledgeNumber;

        packet[8] = flags;

        System.arraycopy(data, 0, packet, 9, data.length);

        return packet;
    }
    
    public int getSize(){
        return 9 + data.length;
    }
    
    @Override
    public String toString() {
        int seq;
        if(sequenceNumber < 0){
            seq = sequenceNumber + 65536;
        }else{
            seq = sequenceNumber;
        }
        String seqS = "" + seq;
        seqS = NumberConversions.insertZeros(seqS, 5);

        int ack;
        if(acknowledgeNumber < 0){
            ack = acknowledgeNumber + 65536;
        }else{
            ack = acknowledgeNumber;
        }
        boolean dataNotEmpty = false;
        for (int i = 0; i < data.length; i++) {
            if(data[i] != 0){
                dataNotEmpty = true;
                break;
            }

        }
        String ackS = "" + ack;
        ackS = NumberConversions.insertZeros(ackS, 5);


        if(!dataNotEmpty){
            data = new byte[0];
        }

        //Connection ID:
        String con = NumberConversions.intToHexString(connectionId);
        con = NumberConversions.insertZeros(con, 8);

        //Příznaky:
        String flag;
        switch(flags){
            case FLAG_EMPTY:
                flag = "000";
                break;
            case FLAG_SYN:
                flag = "SYN";
                break;
            case FLAG_FIN:
                flag = "FIN";
                break;
            case FLAG_RST:
                flag = "RST";
                break;
            default:
                flag = NumberConversions.byteToBinaryString(flags);
        }

        String dataS = data.length < 10 ? " data(" + data.length + ")=" + NumberConversions.bytesToHexString(data, 10) + "}" :
            " data(" + data.length + ")=" + NumberConversions.bytesToString(data, 10) + "...}";

        return "Packet{"
                //+ "time=" + time
                + "con=" + con
                + " seq=" + seqS
                + " ack=" + ackS
                + " flags=" + flag
                //+ " data=" + bytesToHexString(data) + '}';
                + dataS;
    }
}

class NumberConversions {

    /**
     * Převede Signed Byte na Integer v rozsahu 0 až 255.
     * @param data Byte k převodu
     * @return Integer v rozsahu 0 až 255
     */
    public static int byteToInt(byte data){
        if (data < 0) {
            return (int) data + 256;
        } else {
            return (int) data;
        }
    }

    /**
     * Převede byte na Hex String v Network order pořadí bitů.
     * @param data Byt k převodu
     * @return Hexa číslo ve Stringu
     */
    public static String byteToHexString(byte data){
        return intToHexString(byteToInt(data));
    }

    /**
     * Převede pole bytů na Hex String.
     * @param data Byty k převodu
     * @return Hexa číslo ve Stringu
     */
    public static String bytesToHexString(byte[] data, int maxLength){
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < data.length && i < maxLength; i++) {
            temp.append(byteToHexString(data[i]));
            if(i != data.length - 1 && i != maxLength - 1){
                temp.append(" ");
            }
        }
        return temp.toString();
    }

    /**
     * Převede pole bytů na Hex String, malá písmenka.
     * @param data Byty k převodu
     * @return Hexa číslo ve Stringu
     */
    public static String bytesToHexStringSmall(byte[] data,  int maxLength){
        return bytesToHexString(data, maxLength).toLowerCase();
    }

    /**
     * Převede integer na Hexa číslo ve Stringu.
     * Stejné jako Integer.toHexString(int n), ale u lichého počtu učísel se
     * na začátek doplní nula a vše je v upper-case.
     * @param n Číslo k převodu
     * @return String čísla n v hexa
     */
    public static String intToHexString(int n){
        String temp = Integer.toHexString(n);
        if(temp.length() % 2 == 1){
            temp = '0' + temp;
        }
        return temp.toUpperCase();
    }

    /**
     * Převede Číslo v Integeru na pole bytů v Network order.
     * @param n Číslo k převodu
     * @return Pole bytů
     */
    public static byte[] intToBytes(int n) {
        byte[] barr = new byte[4];

        for (int i = barr.length - 1; i >= 0; i--) {
            barr[i] = (byte) n;
            n >>= 8;
        }

        return barr;
    }

    /**
     * Převede short na int v rozsahu 0 až 65535.
     * @param n Short
     * @return Integer
     */
    public static int shortToInt(short n){
        if(n < 0){
            return n + 65536;
        }else{
            return n;
        }
    }

    /**
     * Převede pole bytů na binární kód ve Stringu v Network Order.
     * @param data Byty k převodu
     * @return String bytů v bináru
     */
    public static String bytesToBinaryString(byte[] data) {
        String temp = "";
        for (int i = 0; i < data.length; i++) {
            temp += byteToBinaryString(data[i]);
        }
        return temp;
    }

    /**
     * Převede byte na binární kód ve Stringu v Network Order.
     * @param b Byte k převodu
     * @return String bytu v bináru
     */
    public static String byteToBinaryString(byte b) {
        String temp = "";
        int mask = 0x80;
        while (mask > 0) {
            if ((mask & b) != 0) {
                temp += '1';
            } else {
                temp += '0';
            }
            mask >>= 1;
        }
        return temp;
    }

    public static String bytesToText(byte[] data, int maxLength){
        String s = "";
        for (int i = 0; i < data.length && i < maxLength; i++) {
            if(byteToInt(data[i]) < 32){
                s = s + "[" + byteToInt(data[i]) + "]";
            }else{
                s = s + (char)data[i];
            }
        }
        return s;
    }

    public static String bytesToString(byte[] data, int maxLength){
        String s = "";
        for (int i = 0; i <  data.length && i < maxLength; i++) {
            s += intToHexString(byteToInt(data[i])) + " ";
        }
        return s;
    }

    /*public static String bytesToStringDebug(byte[] data, int maxLength){
        String temp = "";
        if(data.length >=3) {
            temp = intToHexString(byteToInt(data[1]));
            temp += intToHexString(byteToInt(data[2]));
            temp += intToHexString(byteToInt(data[3]));
            temp = hexToInt(temp) + ": ";
        }


        String s = temp;
        for (int i = 0; i <  data.length && i < maxLength; i++) {
            s += intToHexString(byteToInt(data[i])) + " ";
        }
        return s;
    }-/

    /**
     * Převede HEX String na integer
     * @param n String čisla
     * @return číslo v int
     */
    public static int hexToInt(String n){
        return Integer.parseInt(n, 16);
    }

    /**
     * String s dopní zepředu nulami do požadované délky.
     * @param s
     * @param totalLength
     * @return
     */
    public static String insertZeros(String s, int totalLength){
        if(s.length() < totalLength){
            int conLength = s.length();
            for (int i = 0; i < totalLength - conLength; i++) {
                s = "0" + s;
            }
        }
        return s;
    }

}
