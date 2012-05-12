package robot;

import java.util.LinkedList;

/**
 * Okénko
 * @author Michal Petříček
 */
public class Window {
    private Byte[] data;
    private short windowStart;
    private final int windowWidth;

    public Window(int windowWidth) {
        this.windowWidth = windowWidth;
        data = new Byte[windowWidth];
        windowStart = 0;
    }

    public Window() {
        this(2048);
    }

    public void insertPacket(Packet packet) throws WindowException, CriticalException {
        //Null:
        if(packet == null){
            throw new CriticalException("Zadny packet k pridani do okenka");
        }

        //Packet s priznakem:
        if(packet.getFlags() != Packet.FLAG_EMPTY){
            throw new CriticalException("Packet neobsahuje data");
        }

        //Mimo okno:
        int distance = Packets.getDistance(windowStart, (short) (packet.getSequenceNumber() + packet.getDataLength()));
        if(distance > windowWidth){
            if(distance > 65536 - windowWidth){
                throw new WindowException("Packet se seq " + packet.getSequenceNumberInt() + " byl jiz ulozen");
            }else
            throw new CriticalException("Packet mimo rozsah okenka: "
                    + "w_start=" + NumberConversions.shortToInt(windowStart)
                    + ", w_width=" + windowWidth
                    + ", w_end=" + (windowWidth + NumberConversions.shortToInt(windowStart))
                    + ", p_seq=" +  NumberConversions.shortToInt(packet.getSequenceNumber()));
        }
        
        //Vložení dat do okenka:
        int pointer = packet.getSequenceNumber() - windowStart;
        if(pointer < 0) pointer += 0xFFFF + 1;

        for (byte b : packet.getData()) {
            if(data[pointer] != null){
                if(data[pointer] != b) {
                    throw new WindowException("Data packetu (" + NumberConversions.byteToHexString(b)
                        + ") prekryvaji obsahove jina data (" + NumberConversions.byteToHexString(data[pointer])
                        + ") na pozici " + pointer);
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
