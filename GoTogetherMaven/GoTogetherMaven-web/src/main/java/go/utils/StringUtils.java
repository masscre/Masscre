package go.utils;

public class StringUtils {
    
    public static String makeFirstCharUpperCase(String s) {
        char[] toCharArray = s.toCharArray();
        char a = toCharArray[0];
        String valueOf = String.valueOf(a);
        valueOf = valueOf.toUpperCase();
        String x;
        x = valueOf;
        String copyValueOf = String.copyValueOf(toCharArray, 1, toCharArray.length-1);
        x += copyValueOf;        
        return x;
    }
}
