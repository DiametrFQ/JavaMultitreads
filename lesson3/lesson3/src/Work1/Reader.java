package Work1;

import java.util.List;

public class Reader implements Runnable {
    private final List<Integer> sharedList;

    public Reader(List<Integer> sharedList) {
        this.sharedList = sharedList;
    }

    @Override
    public void run() {
        synchronized (sharedList) {
            for (Integer number : sharedList) {
                System.out.println("Чтение числа: " + number);
                try {
                    Thread.sleep(100);  // имитация задержки
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
