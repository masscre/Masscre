package bezpecnost;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * KBE
 * Solution for the problem: Amsco
 * March, 2011
 *
 * @author Tomáš Malich
 */

class Bezpecnost {
	String type;
	
	boolean run() throws Exception {
	    type = nextToken();
	    if (type.equals("end")) return false;
	    if (type.equals("e")) System.out.println("encrypted text with password " + nextToken() + " is: " + nextToken());
	    if (type.equals("d")) System.out.println("decrypted text with password " + nextToken() + " is: " + nextToken());
	    return true;
	}    
	    
	
	public static void main(String[] args) throws Exception {
	    Bezpecnost inst = new Bezpecnost();
	    while (inst.run()) {}
	}	
	
	StringTokenizer st = new StringTokenizer("");
	BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

	String nextToken () throws Exception {    
	    while (!st.hasMoreTokens()) st = new StringTokenizer (stdin.readLine());
	    return st.nextToken();
	
	}


}
