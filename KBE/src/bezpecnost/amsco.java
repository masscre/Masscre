package bezpecnost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * KBE
 * Solution for the problem: Amsco
 * March, 2011
 *
 * @author Tomas Malich
 */

class Bezpecnost {
	String type;
        Column[] columns;
	
	boolean run() throws Exception {
	    type = nextToken();
	    if (type.equals("end")) return false;
	    if (type.equals("e")) System.out.println(encrypt(nextToken(), checkText(nextToken())));
	    if (type.equals("d")) System.out.println(decrypt(nextToken(), nextToken()));
	    return true;
	}    
	    
	
	public static void main(String[] args) throws Exception {
	    Bezpecnost inst = new Bezpecnost();
	    while (inst.run()) {}
	}	
	
	StringTokenizer st = new StringTokenizer("");
	BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

	String nextToken() throws Exception {    
	    while (!st.hasMoreTokens()) st = new StringTokenizer (stdin.readLine());
	    return st.nextToken();	
	}
        
        String encrypt(String password, String text) {
            boolean even = true;
            if (text.length()%2 != 0) even = false;
            int y;
            if (even == true) {
                y = password.length() + password.length()/2;
            } else {
                y = password.length() - 1 + password.length()/2;
            }
            double linesNumberDouble = text.length()/y;
            int linesNumber = (int)(linesNumberDouble);
            if (linesNumberDouble > (double)(linesNumber)) linesNumber++;
            
            columns = new Column[password.length()];
            
            for (int i = 0; i < password.length(); i++) {
                columns[i] = new Column(linesNumber);
            }
            
            String textArray[] = new String[text.length()-text.length()/3];
            
            boolean duplex = true;
            int cursor = 0;            
            for (int i = 0; i < textArray.length; i++) {
                if (duplex == true) {
                    try {
                        textArray[i] = text.substring(cursor, cursor+2);
                    } catch (Exception e) {
                        textArray[i] = text.substring(cursor, cursor+1);
                    }
                    cursor = cursor + 2;                    
                    duplex = false;
                } else {
                    try {
                        textArray[i] = text.substring(cursor, cursor+1);
                    } catch (Exception e) {
                        
                    }                    
                    cursor++;
                    duplex = true;
                }
            }
            
            int x = 0;            
            for (int i = 0; i < textArray.length; i++) {
                columns[x].addLine(textArray[i]);
                x++;
                if (x == columns.length) x = 0;
            }
           
            int[] numbers = new int[password.length()];
            for (int i = 0; i < password.length(); i++) {
                try {
                    numbers[i] = Integer.parseInt(password.substring(i, i+1));
                } catch (Exception e){}
            }     
            
            String encryptText = null;
             
            int current = 0;
            for (int i = 0; i < columns.length; i++) {
                for (int c = 0; c < linesNumber; c++) {
                    String tok = columns[numbers[current]-1].readLine();
                    if (encryptText == null && tok != null) {
                        encryptText = tok;
                    }
                    else if (tok != null) encryptText = encryptText + tok;
                }
                current++;
            }         
            
            encryptText = encryptText.toUpperCase();
            
            String result = null;
            
            int space = 0;
            for (int i = 0; i < encryptText.length(); i++) {                
                if (space == 5) {
                    result = result + " ";
                    i--;
                    space = 0;
                } else if (result != null) {
                    result = result + encryptText.substring(i, i+1);
                    space++;
                } else {
                    result = encryptText.substring(i, i+1);
                    space++;
                }
            }
            
            return result;
        }
        
        String decrypt(String password, String text) {
            return "desifrovany text";
        }
        
        String checkText(String text) {
            String result = null;
            for (int i = 0; i < text.length(); i++) {
                if (result == null && check(text.substring(i, i+1)) == true) {
                    result = text.substring(i, i+1);
                }
                else if (check(text.substring(i, i+1)) == true){
                    result = result + text.substring(i, i+1);
                }
            }
            System.out.println(result);
            return result;
        }
        
        boolean check(String a) {
            if ("A".equals(a)) return true;
            if ("B".equals(a)) return true;
            if ("C".equals(a)) return true;
            if ("D".equals(a)) return true;
            if ("E".equals(a)) return true;
            if ("F".equals(a)) return true;
            if ("G".equals(a)) return true;
            if ("H".equals(a)) return true;
            if ("I".equals(a)) return true;
            if ("J".equals(a)) return true;
            if ("K".equals(a)) return true;
            if ("L".equals(a)) return true;
            if ("M".equals(a)) return true;
            if ("N".equals(a)) return true;
            if ("O".equals(a)) return true;
            if ("P".equals(a)) return true;
            if ("Q".equals(a)) return true;
            if ("R".equals(a)) return true;
            if ("S".equals(a)) return true;
            if ("T".equals(a)) return true;
            if ("U".equals(a)) return true;
            if ("V".equals(a)) return true;
            if ("W".equals(a)) return true;
            if ("X".equals(a)) return true;
            if ("Y".equals(a)) return true;
            if ("Z".equals(a)) return true;
            if ("a".equals(a)) return true;
            if ("b".equals(a)) return true;
            if ("c".equals(a)) return true;
            if ("d".equals(a)) return true;
            if ("e".equals(a)) return true;
            if ("f".equals(a)) return true;
            if ("g".equals(a)) return true;
            if ("h".equals(a)) return true;
            if ("i".equals(a)) return true;
            if ("j".equals(a)) return true;
            if ("k".equals(a)) return true;
            if ("l".equals(a)) return true;
            if ("m".equals(a)) return true;
            if ("n".equals(a)) return true;
            if ("o".equals(a)) return true;
            if ("p".equals(a)) return true;
            if ("q".equals(a)) return true;
            if ("r".equals(a)) return true;
            if ("s".equals(a)) return true;
            if ("t".equals(a)) return true;
            if ("u".equals(a)) return true;
            if ("v".equals(a)) return true;
            if ("w".equals(a)) return true;
            if ("x".equals(a)) return true;
            if ("y".equals(a)) return true;
            if ("z".equals(a)) return true;
            return false;
        }


}

class Column {
    public String[] column;
    int i = 0;
    int y = 0;
    public int size = 0;
    
    Column(int size) {
        column = new String[size];
        this.size = size;
    }
    
    void addLine(String text) {
        column[i] = text;
        i++;
    }
    
    String readLine() {
        String r = column[y];
        y++;        
        return r;
    }   
}
