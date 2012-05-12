package robot;

import java.util.Arrays;
import robot.NumberConversions;
import robot.Packets;

/**
 * Objekty této třídy představují jeden packet.
 * @author Michal Petříček
 */
public class Packet {

    /**
     * Maximální délka packetu v bytech.
     */
    public static final int MAX_PACKET_LENGTH = 265;

    /**
     * Maximální délka packetu v bytech.
     */
    public static final int MAX_DATA_LENGTH = 256;

    /**
     * Prázdný příznak.
     */
    public static final byte FLAG_EMPTY = 0;

    /**
     * Otevření nového spojení. Posílá klient i server (pouze) na
     * začátku v prvním paketu.
     */
    public static final byte FLAG_SYN = 1;
    /**
     * Ukončení spojení. Posílá klient i server, pokud již nemají
     * žádná další data k odeslání. Paket s nastaveným příznakem FIN již nemůže
     * obsahovat žádná data. Ukončení spojení nelze odvolat. Oba směry spojení
     * se uzavírají zvlášť. Sekvenční číslo se po odeslání FIN již nesmí
     * zvětšit.
     */
    public static final byte FLAG_FIN = 2;
    /**
     * Zrušení spojení kvůli chybě. Posílá klient i server v případě
     * detekování logické chyby v hodnotách v hlavičce. Např. přijatý paket
     * neobsahuje příznak SYN a ID spojení není evidováno. Nebo je hodnota
     * potvrzovacího čísla menší, než byla v posledním přijatém paketu (klesá).
     * Pozor na přetečení sekvenčních a potvrzovacích čísel. Žádná
     * z komunikujících stran po odeslání paketu s příznakem RST již dále
     * neukončuje spojení standardním způsobem - spojení je přenosem paketu
     * s příznakem RST definitivně ukončeno.
     */
    public static final byte FLAG_RST = 4;

    public static final byte CMD_DOWNLOAD = 0x01;

    public static final byte CMD_UPLOAD = 0x02;

    /**
     * <p>4B</p>
     * <p>identifikátor 'spojení' - vygenerovaný serverem (pro umožnění transportu
     * dat více souborů najednou).</p>
     * <p>Identifikátor spojení je nenulové číslo. Pakety, které mají nastaven
     * identifikátor spojení na nulu, jsou posílány od klienta k serveru a musí
     * mít nastaven příznak SYN. Odpověď od serveru se od žádosti o vytvoření
     * spojení pozná právě podle nenulové hodnoty identifikátoru spojení.</p>
     */
    private int connectionId;
    /**
     * <p>2B</p>
     * <p>Sekvenční číslo je pořadové číslo prvního bytu v proudu posílaných dat
     * plus počáteční hodnota sekvenčního čísla. Na začátku si toto číslo obě
     * komunikující strany náhodně vygenerují. První nastavení příznaku SYN
     * nebo FIN toto číslo také inkrementuje. </p>
     * <p>Tato čísla nemají znaménko a mohou přetéct. Přetečení nemá na
     * komunikaci vliv.</p>
     */
    private short sequenceNumber;
    /**
     * <p>2B</p>
     * <p>Číslo potvrzení sděluje protistraně pořadové číslo očekávaného bytu
     * v proudu přijímaných dat. Potvrzuje zároveň všechny byty s nižším
     * pořadovým číslem. </p>
     * <p>Tato čísla nemají znaménko a mohou přetéct. Přetečení nemá na
     * komunikaci vliv.</p>
     */
    private short acknowledgeNumber;
    /**
     * <p><table class="inline">
     * <tr>
     * <th>číslo bitu</th><th>7</th><th>6</th><th>5</th><th>4</th><th>3</th>
     * <th>2</th><th>1</th><th>0</th>
     * </tr>
     * <tr>
     * <td>příznak</td><td>NA</td><td>NA</td><td>NA</td><td>NA</td><td>NA</td>
     * <td>RST</td><td>FIN</td><td>SYN</td>
     * </tr>
     * </table>
     * </p>
     * <p>SYN - Otevření nového spojení. Posílá klient i server (pouze) na
     * začátku v prvním paketu.</p>
     * <p>FIN - Ukončení spojení. Posílá klient i server, pokud již nemají
     * žádná další data k odeslání. Paket s nastaveným příznakem FIN již nemůže
     * obsahovat žádná data. Ukončení spojení nelze odvolat. Oba směry spojení
     * se uzavírají zvlášť. Sekvenční číslo se po odeslání FIN již nesmí
     * zvětšit.</p>
     * <p>RST -Zrušení spojení kvůli chybě. Posílá klient i server v případě
     * detekování logické chyby v hodnotách v hlavičce. Např. přijatý paket
     * neobsahuje příznak SYN a ID spojení není evidováno. Nebo je hodnota
     * potvrzovacího čísla menší, než byla v posledním přijatém paketu (klesá).
     * Pozor na přetečení sekvenčních a potvrzovacích čísel. Žádná
     * z komunikujících stran po odeslání paketu s příznakem RST již dále
     * neukončuje spojení standardním způsobem - spojení je přenosem paketu
     * s příznakem RST definitivně ukončeno.</p>
     * <p>Jednotlivé příznaky (SYN, FIN, RST) nelze spolu kombinovat.</p>
     */
    private byte flags;
    /**
     * <p>Délka dat je určena velikostí paketu mínus velikost hlavičky.
     * Poslat lze najednou maximálně 256 bytů dat, takže nejmenší velikost
     * datagramu je 9 B (pouze hlavička) a největší 9 + 256 = 265 B. Data lze
     * poslat pouze v paketu bez nastaveného příznaku FIN a RST. Pokud má paket
     * nastaven příznak SYN a odesílatelem je klient, musí být v datové části
     * 1 byte s kódem příkazu.
     * <p>Data poslaná klientem se v číslují pomocí sekvenčního čísla,
     * avšak pouze tehdy, pokud není nastaven žádný příznak (zejména SYN).</p>
     */
    private byte[] data;

    /**
     * Cas vytvoreni pacektu.
     */
    private long time;

    /**
     * Konstruktor.
     * @param connectionId ID spojeni.
     * @param sequenceNumber Seq, pořadové číslo.
     * @param acknowledgeNumbr Ack, potvrzovací číslo.
     * @param flags Priznaky
     * @param data Data (null pro zadna)
     */
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

    /**
     * Konstruktor s pretypovanim.
     * @param connectionId ID spojeni.
     * @param sequenceNumber Seq, pořadové číslo.
     * @param acknowledgeNumbr Ack, potvrzovací číslo.
     * @param flags Priznaky
     * @param data Data (null pro zadna)
     */
    public Packet(int connectionId, int sequenceNumber, int acknowledgeNumber, int flags, byte[] data) {
        this(connectionId, (short) sequenceNumber, (short) acknowledgeNumber, (byte)flags, data);
    }

    /**
     * Konstruktor<br />
     * Z pole bytů vytvoří packet. Pokud má pole nesprávný formát, zapíší se
     * všude nuly, a funkceisOk vrátí false;
     * @param incoming Pole bytů packetu
     */
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

    /**
     * Je syntaxe packetu správná?
     * @return true, pokud ano
     */
    public boolean isOk(){
        if(data.length > MAX_PACKET_LENGTH){
            return false;
        }
        if(((flags == FLAG_FIN || flags == FLAG_RST) && data.length == 00)
                || (flags == FLAG_SYN && data.length == 1 && sequenceNumber == 0 && acknowledgeNumber == 0) && (data[0] == CMD_DOWNLOAD || data[0] == CMD_UPLOAD)){
            return true;
        }
        if(flags == FLAG_EMPTY && connectionId != 0){
            return true;
        }
        return false;
    }

    //------------GET-----------------------------------------------------------
    /**
     * Vrátí počet bytů v packetu.
     * @return Integer - velikost packetu
     */
    public int getSize(){
        return 9 + data.length;
    }

    /**
     * Vytvoří bytovou reprezentaci packetu.
     * @return Packet jako pole bytů
     */
    public byte[] getPacket(){
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

    /**
     * Vrátí potvrzovací číslo packetu. V rozsahu shortu, tedy -2^16 až 2^16-1.
     * @return ACK
     */
    public short getAcknowledgeNumber() {
        return acknowledgeNumber;
    }

    /**
     * Vrátí ID spojení. Vrací jej jako integer, tedy v rozsahu -2^32 až 2^32-1.
     * @return CON
     */
    public int getConnectionId() {
        return connectionId;
    }

    /**
     * Vrátí pořadové číslo packetu. V rozsahu shortu, tedy -2^16 až 2^16-1.
     * @return ACK
     */
    public short getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Vrátí seq jako unsignet short, respektive int v rozsahu 0-65535.
     * @return Unsignet short - seq
     */
    public int getSequenceNumberInt(){
        if(sequenceNumber < 0){
            return sequenceNumber + 0xFFFF + 1;
        }else{
            return sequenceNumber;
        }
    }

    /**
     * Vrátí číslo následujícícho očekávaného packetu.
     * V rozsahu shortu, tedy -2^16 až 2^16-1.
     * @return next SEQ
     */
    public short getNextSequenceNumber(){
        if(data.length == 0){
            return (short) (sequenceNumber + 1);
        }else{
            return (short) (sequenceNumber + data.length);
        }
    }

    /**
     * Vrátí číslo následujícícho očekávaného packetu.
     * V rozsahu 0 - 65535.
     * @return next SEQ
     */
    public int getNextSequenceNumberInt(){
        if(data.length == 0){
            return (getSequenceNumberInt() + 1) % 65536;
        }else{
            return (getSequenceNumberInt() + data.length) % 65536;
        }
    }

    /**
     * Vrátí příznaky.
     * @return Příznaky.
     */
    public byte getFlags(){
        return flags;
    }

    /**
     * Čas odeslání, respektive přijetí packetu.
     * @return
     */
    public long getTime() {
        return time;
    }

    /**
     * Vrátí délku přijatch dat (bez hlavičky).
     * @return Velikost dat v bytech.
     */
    public int getDataLength(){
        if(data == null){
            return 0;
        }
        return data.length;
    }

    public byte[] getData(){
        return data;
    }

    //------------SET-----------------------------------------------------------

    /**
     * Nastaví čas odeslání/přijetí packetu na současný čas. Použít tesně
     * před odesláním packetu.
     */
    public void setTime(){
        this.time = System.currentTimeMillis();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Packet other = (Packet) obj;
        if (this.connectionId != other.connectionId) {
            return false;
        }
        if (this.sequenceNumber != other.sequenceNumber) {
            return false;
        }
        if (this.acknowledgeNumber != other.acknowledgeNumber) {
            return false;
        }
        if (this.flags != other.flags) {
            return false;
        }
        if (!Arrays.equals(this.data, other.data)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + this.connectionId;
        hash = 43 * hash + this.sequenceNumber;
        hash = 43 * hash + this.acknowledgeNumber;
        hash = 43 * hash + this.flags;
        hash = 43 * hash + Arrays.hashCode(this.data);
        hash = 43 * hash + (int) (this.time ^ (this.time >>> 32));
        return hash;
    }

    //------------ DALSI -------------------------------------------------------

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

    //------------STATICKE METODY-----------------------------------------------

    public static void main(String[] args) {
        short a = (short)65535;
        short b = (short) (a + 1);
        System.out.println(a);
        System.out.println(b);
        System.out.println(Packets.isOverflow(b, a, 2048));
        


        System.out.println();
        short[] s = new short[] {0, 1, Short.MAX_VALUE, Short.MIN_VALUE, -1};
        for (int i = 0; i < s.length; i++) {
            int temp = s[i];
            System.out.print("s" + (i + 1) + ": ");
            System.out.print(temp + " = ");
            System.out.println(NumberConversions.bytesToBinaryString(NumberConversions.intToBytes(temp)));
        }


    }

}
