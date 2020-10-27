package friegaplatos;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Fregador implements Runnable{

    private final Bandeja bandejaSucia;
    private final Bandeja bandejaMojada;
    Random rnd=new Random();
    String role;

    public Fregador(Bandeja bandejaSucia, Bandeja bandejaMojada) {
        this.bandejaSucia=bandejaSucia;
        this.bandejaMojada=bandejaMojada;
        role="Fregador";
    }

    @Override
    public void run() {
        Plato plato;
        while (!Thread.currentThread().isInterrupted()){
            try {
                plato=bandejaSucia.sacarPlato(role);
                TimeUnit.SECONDS.sleep(rnd.nextInt(8)+4);
                bandejaMojada.meterPlato(plato,role);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}