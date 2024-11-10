package Work2;

import java.util.Random;

public class Person extends Thread {
    private final int category; // 1 - молодые, 2 - пожилые, 3 - бизнесмены
    private final Window[] windows;

    public Person(int category, Window[] windows) {
        this.category = category;
        this.windows = windows;
    }

    @Override
    public void run() {
        Random random = new Random();
        boolean served = false;
        for (int i = 0; i < 3; i++) { // пробует попасть в 3 случайных окна
            int windowIndex = random.nextInt(windows.length);
            if (windows[windowIndex].service(this)) {
                served = true;
                break;
            }
        }
        if (!served) {
            System.out.println("Гражданин категории " + category + " ушел разгневанным.");
        }
    }

    public int getCategory() {
        return category;
    }
}