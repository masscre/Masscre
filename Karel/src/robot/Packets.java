package robot;

/**
 * Nástroje pro práci s packety a jejich údaji.
 * @author Michal Petříček
 */
public class Packets {

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
