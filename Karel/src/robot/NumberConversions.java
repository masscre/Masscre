package robot;

/**
 *
 * @author Michal Petříček
 */
public class NumberConversions {

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
