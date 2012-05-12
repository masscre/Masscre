package robot;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Robot {

    public static Download download;
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
    DatagramSocket socket;
    DatagramPacket packetUDP;
    InetAddress address, fromAddress;
    String messageString;
    byte[] message = new byte[255];
    int fromPort, port = 4000;
    private int nextSq = 0;
    private boolean firstPacketReceived = false;
    private Okno okno = new Okno();
    private Window window = new Window();
    protected long transferedBytes = 0;
    protected DataQueue dataQueue;

    Download(String ip) throws SocketException, UnknownHostException, IOException {
        this.ip = ip;
        socket = new DatagramSocket();
        socket.setSoTimeout(500);
        address = InetAddress.getByName(ip);
        Packet packet = Packet.getFirstPacket();
        packetUDP = new DatagramPacket(packet.getPacket(), packet.getSize(),
                address, port);
        socket.send(packetUDP);
        System.out.println("Sending first packet: " + packet);
        
        

        while (true) {

            packetUDP = new DatagramPacket(message,
                    message.length);
            try {
                socket.receive(packetUDP);
            } catch (Exception e) {
                System.out.println("TIMEOUT");
            }
            byte[] data = new byte[Packet.MAX_PACKET_LENGTH];
            data = packetUDP.getData();
            Packet respond = new Packet(data, packetUDP.getLength());
            System.out.println("<<< " + respond.toString());
            
            if (firstPacketReceived == false && respond.getFlags() != Packet.FLAG_SYN) {
                Packet p = Packet.getFirstPacket();
                packetUDP = new DatagramPacket(p.getPacket(), p.getSize(),
                        address, port);
                socket.send(packetUDP);
                System.out.println("Sending first packet: " + p);
            }

            if (respond.getFlags() == Packet.FLAG_RST) {
                System.out.println("Spojeni nasilne ukonceno");
                //Robot.download = new Download(ip);
                break;
            }

            if (respond.getFlags() == Packet.FLAG_FIN) {
                Packet send = new Packet(0, 0, 0, 0, new byte[]{Packet.FLAG_FIN});
                packetUDP = new DatagramPacket(send.getPacket(), send.getSize(), address, port);
                socket.send(packetUDP);
                System.out.println("Spojeni ukonceno");
                break;
            }

            if (respond.getFlags() == Packet.FLAG_SYN) {
                firstPacketReceived = true;
            } else if (firstPacketReceived == true) {
                int sq = respond.getSequenceNumberInt();
                int con = respond.getConnectionId();                
                int ack = window.add(respond);
                Packet send = new Packet(con, 0, ack, 0, new byte[]{Packet.DATA_EMPTY});
                System.out.println(" >>> " + send);
                packetUDP = new DatagramPacket(send.getPacket(), send.getSize(), address, port);
                socket.send(packetUDP);
            }

        }
        System.out.println(Data.data);
        socket.close();
    }
}

class Upload {

    Upload() {
        System.out.println("Upload start");
    }
}

class Window {

    private int sq = 0;
    private Packet[] pakety = new Packet[8];
    

    int add(Packet p) {
        if (sq == 0 && p.getSequenceNumberInt() == 0) {
            //System.out.println("Prijal jsem prvni packet s daty");
            Data.data.addPacket(p);
            sq = sq + 255;
            return sq;
        }
        if (p.getSequenceNumberInt() == sq) {
            //System.out.println("Prijal jsem packet co se mi hodi do rady");
            //System.out.println("Pridavam paket "+p);
            Data.data.addPacket(p);
            sq = sq + 255;            
            return sq;
        } else if (p.getSequenceNumberInt() <= sq + 2040) {
            //System.out.println("Prijal jsem packet co se mi vejde do okenka");
            boolean isThere = Data.data.isThere(p);
            if (isThere) {
                //System.out.println("Tento paket uz je pridany");
            } else {
                //System.out.println("Pridavam paket "+p);
                Data.data.addPacket(p);
                sq = sq + 255;
            }            
            return sq;
        } else {
            //System.out.println("Paket je mimo okno");
            return sq;
        }
    }
    
}

class Data {

    static Data data = new Data();
    public List<Packet> packetData = new ArrayList();

    public void addPacket(Packet p) {
        packetData.add(p);        
    }
    
    public boolean isThere(Packet p) {
        Iterator it = packetData.iterator();
        int sqp = p.getSequenceNumberInt();
        while(it.hasNext()) {
            Packet a = (Packet) it.next();
            if (a.getSequenceNumberInt() == sqp) return true;
        }
        return false;
    }

    public void make() {
    }

    @Override
    public String toString() {
        String r = "";
        Iterator it = packetData.iterator();
        while (it.hasNext()) {
            Packet p = (Packet) it.next();
            r = r + " " + p.getSequenceNumberInt();
        }
        return "Data: " + r;
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
    public static final byte DATA_EMPTY = 0x00;
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
        if (data == null) {
            this.data = new byte[0];
        } else {
            this.data = data;
        }
        this.time = 0;
    }

    public Packet(int connectionId, int sequenceNumber, int acknowledgeNumber, int flags, byte[] data) {
        this(connectionId, (short) sequenceNumber, (short) acknowledgeNumber, (byte) flags, data);
    }

    static Packet getFirstPacket() {
        return new Packet(0, 0, 0, Packet.FLAG_SYN, new byte[]{Packet.CMD_DOWNLOAD});
    }

    public Packet(byte[] incoming, int length) {
        time = System.currentTimeMillis();
        if (incoming.length < 9) {
            connectionId = 0;
            sequenceNumber = 0;
            acknowledgeNumber = 0;
            flags = 0;
            return;
        }
        connectionId = (incoming[0] << 24) & 0xFF000000
                | (incoming[1] << 16) & 0x00FF0000
                | (incoming[2] << 8) & 0x0000FF00
                | incoming[3] & 0x000000FF;
        sequenceNumber = (short) ((incoming[4] << 8) & 0xFF00 | incoming[5] & 0x00FF);
        acknowledgeNumber = (short) ((incoming[6] << 8) & 0xFF00 | incoming[7] & 0x00FF);
        flags = incoming[8];
        data = new byte[length - 9];
        for (int i = 0; i < length - 9; i++) {
            data[i] = incoming[i + 9];
        }
    }

    public int getConnectionId() {
        return connectionId;
    }

    public byte[] getPacket() {
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

    public int getSize() {
        return 9 + data.length;
    }

    public byte getFlags() {
        return flags;
    }

    @Override
    public String toString() {
        int seq;
        if (sequenceNumber < 0) {
            seq = sequenceNumber + 65536;
        } else {
            seq = sequenceNumber;
        }
        String seqS = "" + seq;
        seqS = NumberConversions.insertZeros(seqS, 5);

        int ack;
        if (acknowledgeNumber < 0) {
            ack = acknowledgeNumber + 65536;
        } else {
            ack = acknowledgeNumber;
        }
        boolean dataNotEmpty = false;
        for (int i = 0; i < data.length; i++) {
            if (data[i] != 0) {
                dataNotEmpty = true;
                break;
            }

        }
        String ackS = "" + ack;
        ackS = NumberConversions.insertZeros(ackS, 5);


        if (!dataNotEmpty) {
            data = new byte[0];
        }

        //Connection ID:
        String con = NumberConversions.intToHexString(connectionId);
        con = NumberConversions.insertZeros(con, 8);

        //Příznaky:
        String flag;
        switch (flags) {
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

        String dataS = data.length < 10 ? " data(" + data.length + ")=" + NumberConversions.bytesToHexString(data, 10) + "}"
                : " data(" + data.length + ")=" + NumberConversions.bytesToString(data, 10) + "...}";

        return "Packet{"
                //+ "time=" + time
                + "con=" + con
                + " seq=" + seqS
                + " ack=" + ackS
                + " flags=" + flag
                //+ " data=" + bytesToHexString(data) + '}';
                + dataS;
    }

    public int getSequenceNumberInt() {
        if (sequenceNumber < 0) {
            return sequenceNumber + 0xFFFF + 1;
        } else {
            return sequenceNumber;
        }
    }
    
    public short getNextSequenceNumber(){
        if(data.length == 0){
            return (short) (sequenceNumber + 1);
        }else{
            return (short) (sequenceNumber + data.length);
        }
    }
    public short getSequenceNumber() {
        return sequenceNumber;
    }
    public byte[] getData(){
        return data;
    }
    public int getDataLength(){
        if(data == null){
            return 0;
        }
        return data.length;
    }
}

class NumberConversions {

    /**
     * Převede Signed Byte na Integer v rozsahu 0 až 255.
     * @param data Byte k převodu
     * @return Integer v rozsahu 0 až 255
     */
    public static int byteToInt(byte data) {
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
    public static String byteToHexString(byte data) {
        return intToHexString(byteToInt(data));
    }

    /**
     * Převede pole bytů na Hex String.
     * @param data Byty k převodu
     * @return Hexa číslo ve Stringu
     */
    public static String bytesToHexString(byte[] data, int maxLength) {
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < data.length && i < maxLength; i++) {
            temp.append(byteToHexString(data[i]));
            if (i != data.length - 1 && i != maxLength - 1) {
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
    public static String bytesToHexStringSmall(byte[] data, int maxLength) {
        return bytesToHexString(data, maxLength).toLowerCase();
    }

    /**
     * Převede integer na Hexa číslo ve Stringu.
     * Stejné jako Integer.toHexString(int n), ale u lichého počtu učísel se
     * na začátek doplní nula a vše je v upper-case.
     * @param n Číslo k převodu
     * @return String čísla n v hexa
     */
    public static String intToHexString(int n) {
        String temp = Integer.toHexString(n);
        if (temp.length() % 2 == 1) {
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
    public static int shortToInt(short n) {
        if (n < 0) {
            return n + 65536;
        } else {
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

    public static String bytesToText(byte[] data, int maxLength) {
        String s = "";
        for (int i = 0; i < data.length && i < maxLength; i++) {
            if (byteToInt(data[i]) < 32) {
                s = s + "[" + byteToInt(data[i]) + "]";
            } else {
                s = s + (char) data[i];
            }
        }
        return s;
    }

    public static String bytesToString(byte[] data, int maxLength) {
        String s = "";
        for (int i = 0; i < data.length && i < maxLength; i++) {
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
    public static int hexToInt(String n) {
        return Integer.parseInt(n, 16);
    }

    /**
     * String s dopní zepředu nulami do požadované délky.
     * @param s
     * @param totalLength
     * @return
     */
    public static String insertZeros(String s, int totalLength) {
        if (s.length() < totalLength) {
            int conLength = s.length();
            for (int i = 0; i < totalLength - conLength; i++) {
                s = "0" + s;
            }
        }
        return s;
    }
}

class Okno {
    private Byte[] data;
    private short windowStart;
    private final int windowWidth;

    public Okno(int windowWidth) {
        this.windowWidth = windowWidth;
        data = new Byte[windowWidth];
        windowStart = 0;
    }

    public Okno() {
        this(2048);
    }

    public void insertPacket(Packet packet) {
        System.out.println("Pridavam packet "+packet);
        //Null:
        if(packet == null){
            System.out.println("Zadny packet k pridani do okenka");
            return;
        }

        //Packet s priznakem:
        if(packet.getFlags() != Packet.FLAG_EMPTY){
            System.out.println("Packet neobsahuje data");
            return;
        }

        //Mimo okno:
        int distance = Packets.getDistance(windowStart, (short) (packet.getSequenceNumber() + packet.getDataLength()));
        if(distance > windowWidth){
            if(distance > 65536 - windowWidth){
                System.out.println("Packet se seq " + packet.getSequenceNumberInt() + " byl jiz ulozen");
            }else
            System.out.println("Packet mimo rozsah okenka: "
                    + "w_start=" + NumberConversions.shortToInt(windowStart)
                    + ", w_width=" + windowWidth
                    + ", w_end=" + (windowWidth + NumberConversions.shortToInt(windowStart))
                    + ", p_seq=" +  NumberConversions.shortToInt(packet.getSequenceNumber()));
            return;
        }
        
        //Vložení dat do okenka:
        int pointer = packet.getSequenceNumber() - windowStart;
        if(pointer < 0) pointer += 0xFFFF + 1;

        for (byte b : packet.getData()) {
            if(data[pointer] != null){
                if(data[pointer] != b) {
                    System.out.println("Data packetu (" + NumberConversions.byteToHexString(b)
                        + ") prekryvaji obsahove jina data (" + NumberConversions.byteToHexString(data[pointer])
                        + ") na pozici " + pointer);
                    return;
                }
            }else{                
                data[pointer] = b;
            }
            pointer++;
        }        
    }

    public byte[] getData(){
        LinkedList<Byte> completeData = new LinkedList<Byte>();
        int pointer = 0;

        while(pointer < data.length && data[pointer] != null){
            completeData.add(data[pointer]);
            data[pointer] = null;
            pointer++;
        }

        rotateData(pointer);
        windowStart += completeData.size();

        byte[] finalData = new byte[completeData.size()];
        for (int i = 0; i < finalData.length; i++) {
            finalData[i] = completeData.get(i);
        }
        return finalData;
    }

    /**
     * Orotuje data v okénku, aby se začínalo prvním nepřijatým bytem.
     */
    private void rotateData(int pointer){
        int start = 0;
        for (int i = pointer; i < data.length; i++) {
            data[start] = data[i];
            data[i] = null;
            start++;
        }
    }

    public boolean isDataAvailable(){
        return data[0] != null;
    }

    public short getNextAck(){
        int pointer = 0;
        while(pointer < windowWidth && data[pointer] != null){
            pointer++;
        }
        return (short) (pointer + windowStart);
    }

    public short getWindowStart(){
        return windowStart;
    }

    @Override
    public String toString() {
        int wStart = NumberConversions.shortToInt(windowStart);
        String wStartS = String.valueOf(wStart);
        wStartS = NumberConversions.insertZeros(wStartS, 5);

        int nextAck = NumberConversions.shortToInt(getNextAck());
        String nextAckS = String.valueOf(nextAck);
        nextAckS = NumberConversions.insertZeros(nextAckS, 5);

        StringBuilder out = new StringBuilder();
        out.append("WINDOW: start=").append(wStartS);
        out.append(", nextAck=").append(nextAckS);
        out.append(", data=");

        for (int i = 0; i < data.length; i += Packet.MAX_DATA_LENGTH) {
            out.append(data[i] == null ? "-" : "x");
        }

        return out.toString();
    }

}

class Packets {

    private Packets() {}


    /**
     * Detekce přetečení. Pokud je first &lt; last a vzdálenost mezi oběma čísly
     * je větší než (65536 - maxDistance), došlo k přetečení.
     * @param n1
     * @param n2
     * @param maxDistance
     * @return
     */
    public static boolean isOverflow(short first, short second, int maxDistance){
        if(maxDistance >= Short.MAX_VALUE){
            throw new IllegalArgumentException();
        }

        int a = first;
        int b = second;

        if(a < 0) a += 65536;
        if(b < 0) b += 65536;

        if(a < b){
            return false;
        }else{
            if(b + (65536 - a) > maxDistance){
                return false;
            }else{
                return true;
            }
        }
    }

    /**
     * Vrátí vzdálenost mezi prvním a druhým číslem. Při přetečení se pokračuje
     * dokola po kladném směru (doprava).
     * @param first První číslo
     * @param second Druhé číslo
     * @return Vzdálenost druhého čísla od prvního po ose doprava
     */
    public static int getDistance(short first, short second){
        int a = first;
        int b = second;

        if(a < 0) a += 65536;
        if(b < 0) b += 65536;

        if(a < b){
            return b - a;
        }else{
            return b + (65536 - a);
        }
    }


}

class DataQueue {

    private LinkedList<byte[]> queue = new LinkedList<byte[]>();

    /**
     * Vloží data do fronty a případné vlákna čekající na remove() hodí
     * InterruptedException.
     * @param data data ke vložené
     */
    public synchronized void insert(byte[] data) {
        queue.add(data);
        notifyAll();
    }

    /**
     * Vrátí a odebere data z fronty. Respektive čeká na vložení dalších
     * dat při prázdné frontě.
     * @return byte[]
     * @throws InterruptedException
     */
    public synchronized byte[] remove() throws InterruptedException {
        while (isEmpty()) {
            wait();
        }
        return queue.removeFirst();
    }

    /**
     * Vrátí počet dat ve frontě.
     * @return počet bytů
     */
    public int getSize() {
        return queue.size();
    }

    /**
     * Je fronta dat prázdná?
     * @return true - ano
     */
    public boolean isEmpty() {
        return getSize() == 0;
    }
}


