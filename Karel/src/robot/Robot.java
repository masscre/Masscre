package robot;

import java.util.Scanner;

public class Robot {
    
    public static Download download;
    public static Upload upload;
    
    public static void main(String[] args) throws CriticalException {
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
