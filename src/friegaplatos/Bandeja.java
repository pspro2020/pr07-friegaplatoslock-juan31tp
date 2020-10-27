package friegaplatos;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Bandeja {

    ArrayList<Plato> platosEnBandeja = new ArrayList();

    Lock reLock = new ReentrantLock();
    Condition noLleno = reLock.newCondition();
    Condition noVacio = reLock.newCondition();

    public Bandeja(int nPlatos) {
        for (int i=0;i<nPlatos;i++){
            platosEnBandeja.add(new Plato(i));
        }
    }

    public void meterPlato(Plato plato, String role) throws InterruptedException {

        try {
            while (platosEnBandeja.size()>10){
                System.out.println(LocalTime.now() + " -- " + " Please, " + role + " wait, there are no dishes");
                noLleno.await();
            }
            platosEnBandeja.add(plato);
            System.out.println(LocalTime.now() + " -- " + role + " put the dish numbered with the serial: " + plato.getSerial());
            noLleno.signal();
        } finally {
            reLock.unlock();
        }
    }

    public Plato sacarPlato(String role) throws InterruptedException {
        Plato plato;
        reLock.lock();

        try {
            while (platosEnBandeja.isEmpty()) {
                System.out.println(LocalTime.now() + " -- " + " Please, " + role + " there's no capacity for more dishes");
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