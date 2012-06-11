package go.utils;

public class Text {
    public static String removeCapitalAndDiacritic(String text) {
        String newText = text.toLowerCase();
        newText.replace('ě', 'e');
        newText.replace('š', 's');
        newText.replace('č', 'c');
        newText.replace('ř', 'r');
        newText.replace('ž', 'z');
        newText.replace('ý', 'y');
        newText.replace('á', 'a');
        newText.replace('í', 'i');
        newText.replace('é', 'e');        
        return newText;
    }
}
