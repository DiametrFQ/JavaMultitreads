package Work2;

import java.util.Random;

public class App {
    public static void main(String[] args) {
        Window[] windows = {new Window(-1), new Window(2), new Window(3)};
        Random random = new Random();

        for (int i = 0; i < 20; i++) {
            int category = random.nextInt(3) + 1; // 1, 2 или 3
            new Person(category, windows).start();
        }
    }
}
