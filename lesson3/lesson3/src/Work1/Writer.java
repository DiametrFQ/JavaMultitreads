package Work1;

import java.util.List;

public class Writer implements Runnable {
    private final List<Integer> sharedList;

    public Writer(List<Integer> sharedList) {
        this.sharedList = sharedList;
    }

    @Override
    public void run() {
        synchronized (sharedList) {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Запись числа: " + i);
                sharedList.add(i);
                try {
                    Thread.sleep(100);  // имитация задержки
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}