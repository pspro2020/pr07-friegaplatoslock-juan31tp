package friegaplatos;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Organizador implements Runnable {

    private final Bandeja alacena;
    private final Bandeja bandejaSeca;
    Random rnd=new Random();
    String role;

    public Organizador(Bandeja bandejaSeca, Bandeja alacena) {
        this.bandejaSeca=bandejaSeca;
        this.alacena=alacena;
        role="Organizador";
    }

    @Override
    public void run() {
        Plato plato;
        while (!Thread.currentThread().isInterrupted()){
            try {
                plato=bandejaSeca.sacarPlato(role);
                TimeUnit.SECONDS.sleep(rnd.nextInt(8)+4);
                alacena.meterPlato(plato,role);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}