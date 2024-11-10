package Work3;

public class Patient extends Thread {
    private static int counter = 0;
    private final int id;
    private final Clinic clinic;

    public Patient(Clinic clinic) {
        this.clinic = clinic;
        this.id = ++counter;
    }

    public long getId() {
        return id;
    }

    @Override
    public void run() {
        clinic.addPatient(this);
    }
}

