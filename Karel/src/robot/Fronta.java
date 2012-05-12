package robot;

import java.util.LinkedList;

/**
 * Fronta dat.
 * @author michal
 */
public class DataQueue {

    private LinkedList<byte[]> queue = new LinkedList<byte[]>();

    /**
     * Vloží data do fronty a případné vlákna čekající na remove() hodí
     * InterruptedException.
     * @param data data ke vložené
     */
    public synchronized void insert(byte[] data) {
        queue.add(data);
        notifyAll();
    }

    /**
     * Vrátí a odebere data z fronty. Respektive čeká na vložení dalších
     * dat při prázdné frontě.
     * @return byte[]
     * @throws InterruptedException
     */
    public synchronized byte[] remove() throws InterruptedException {
        while (isEmpty()) {
            wait();
        }
        return queue.removeFirst();
    }

    /**
     * Vrátí počet dat ve frontě.
     * @return počet bytů
     */
    public int getSize() {
        return queue.size();
    }

    /**
     * Je fronta dat prázdná?
     * @return true - ano
     */
    public boolean isEmpty() {
        return getSize() == 0;
    }
}
