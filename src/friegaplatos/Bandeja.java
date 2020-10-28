package friegaplatos;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Bandeja {

    ArrayList<Plato> platosEnBandeja = new ArrayList();

    Lock reLock = new ReentrantLock();
    Condition noVacio = reLock.newCondition();

    public Bandeja(int nPlatos) {
        for (int i=0;i<nPlatos;i++){
            platosEnBandeja.add(new Plato(i));
        }
    }

    public void meterPlato(Plato plato, String role) throws InterruptedException {
        platosEnBandeja.add(plato);
        System.out.println(LocalTime.now() + " -- " + role + " put the dish numbered with the serial: " + plato.getSerial());

    }

    public Plato sacarPlato(String role) throws InterruptedException {
        Plato plato;
        reLock.lock();

        try {
            while (platosEnBandeja.isEmpty()) {
                System.out.println(LocalTime.now() + " -- " + " Please, " + role + " there are no dishes :/");
                noVacio.await();
            }
            plato=platosEnBandeja.remove(0);
            System.out.println(LocalTime.now() + " -- " +role + " took the dish numbered with the serial: " + plato.getSerial());
            noVacio.signal();
            return plato;
        }finally {
            reLock.unlock();
        }
    }

}