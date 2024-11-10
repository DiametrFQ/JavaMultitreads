package Work3;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Clinic {
    private final Queue<Patient> queue = new LinkedList<>();
    private final Lock therapistLock = new ReentrantLock();
    private final Lock mriLock = new ReentrantLock();
    private int maxQueueLength = 0;

    public void addPatient(Patient patient) {
        synchronized (queue) {
            queue.add(patient);
            maxQueueLength = Math.max(maxQueueLength, queue.size());
            System.out.println("Пациент добавлен в очередь. Длина очереди: " + queue.size());
            queue.notifyAll();
        }
    }

    public void servePatients() {
        while (true) {
            Patient patient;
            synchronized (queue) {
                while (queue.isEmpty()) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                patient = queue.poll();
            }

            try {
                therapistLock.lock();
                System.out.println("Пациент " + patient.getId() + " на приеме у терапевта.");
                Thread.sleep(200); // время приема у терапевта
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                therapistLock.unlock();
            }

            try {
                mriLock.lock();
                System.out.println("Пациент " + patient.getId() + " на обследовании МРТ.");
                Thread.sleep(300); // время обследования на МРТ
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                mriLock.unlock();
            }
        }
    }

    public int getMaxQueueLength() {
        return maxQueueLength;
    }
}
