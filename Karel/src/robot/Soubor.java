package robot;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;



/**
 * Okladani Streamu prichozich dat do souboru.
 * @author Michal Petříček
 */
public class FileWriter implements Runnable {

    private DataQueue data;
    private File file;
    private boolean run = true;

    public FileWriter(DataQueue data, String fileName) throws CriticalException {
        this.data = data;
        file = new File(fileName);

        if (file.exists()) {
            if (!file.isFile()) {
                throw new CriticalException(fileName + " neni soubor.");
            }
            
            try {
                file.delete();
                file.createNewFile();
            } catch (IOException ex) {
                throw new CriticalException("Nelze smazat/vytvorit soubor " + fileName);
            }
        }

        if (!file.exists()) {            
            try {
                file.createNewFile();
            } catch (IOException ex) {
                throw new CriticalException("Nelze vytvorit soubor " + fileName);
            }
        }

        if (!file.canWrite()) {
            throw new CriticalException("Do souboru " + fileName + " nelze zapisovat");
        }
    }

    public FileWriter(DataQueue data) throws CriticalException {
        this(data, "data.png");
    }

    public File getFile() {
        return file;
    }

    public synchronized void run() {
        OutputStream outStream = null;
        try {
            outStream = new FileOutputStream(file);
        } catch (FileNotFoundException ex) {            
            return;
        }
        BufferedOutputStream out = new BufferedOutputStream(outStream);

        while (run) {
            try {                             
                out.write(data.remove());
                out.flush();
            } catch (InterruptedException ex) {
                run = false;
            } catch (IOException ex) {                
                return;
            }
        }

        try {
            out.close();
        } catch (IOException ex) {            
        }

    }

    public void close() {
        run = false;
    }
}
