import Work1.Writer;
import Work1.Reader;
import Work3.Clinic;
import Work3.Patient;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
/** 1 Задание*/
        List<Integer> sharedList = new ArrayList<>();
        Thread writerThread = new Thread(new Writer(sharedList));
        Thread readerThread = new Thread(new Reader(sharedList));

        writerThread.start();
        readerThread.start();
////////////////////////////////////////////////////////////////////////////////////

/** 3 Задание*/
        Clinic clinic = new Clinic();

        Thread clinicService = new Thread(clinic::servePatients);
        clinicService.start();

        for (int i = 0; i < 10; i++) {
            new Patient(clinic).start();
            try {
                Thread.sleep(100); // Поступление пациентов с небольшим интервалом
            } catch (InterruptedException e) {
                System.out.println("Главный поток был прерван при ожидании между пациентами.");
                Thread.currentThread().interrupt(); // Восстановление состояния прерывания
            }
        }

        try {
            clinicService.join();
        } catch (InterruptedException e) {
            System.out.println("Главный поток был прерван при ожидании завершения clinicService.");
            Thread.currentThread().interrupt();
        }

        System.out.println("Максимальная длина очереди: " + clinic.getMaxQueueLength());
    }
}