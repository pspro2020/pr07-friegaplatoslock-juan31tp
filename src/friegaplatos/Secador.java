package friegaplatos;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Secador implements Runnable {

    private final Bandeja bandejaMojada;
    private final Bandeja bandejaSeca;
    Random rnd=new Random();
    String role;

    public Secador(Bandeja bandejaMojada, Bandeja bandejaSeca) {
        this.bandejaSeca=bandejaSeca;
        this.bandejaMojada=bandejaMojada;
        role="Secador";
    }

    @Override
    public void run() {
        Plato plato;
        while (!Thread.currentThread().isInterrupted()){
            try {
                plato=bandejaMojada.sacarPlato(role);
                TimeUnit.SECONDS.sleep(rnd.nextInt(3)+1);
                bandejaSeca.meterPlato(plato,role);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}