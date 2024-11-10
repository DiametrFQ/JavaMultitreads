package Work2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Window {
    private final int acceptedCategory; // -1 для первого окна (принимает всех), 2 для пожилых, 3 для бизнесменов
    private final Lock lock = new ReentrantLock();

    public Window(int acceptedCategory) {
        this.acceptedCategory = acceptedCategory;
    }

    public boolean service(Person person) {
        if (acceptedCategory != -1 && acceptedCategory != person.getCategory()) {
            return false; // окно не принимает данную категорию
        }
        if (lock.tryLock()) {
            try {
                System.out.println("Гражданин категории " + person.getCategory() + " обслуживается в окне " + acceptedCategory);
                Thread.sleep(200); // обслуживание
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
            return true;
        }
        return false; // окно занято
    }
}