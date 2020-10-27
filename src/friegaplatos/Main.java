package friegaplatos;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        Bandeja bandejaSucia = new Bandeja(10);
        Bandeja bandejaMojada = new Bandeja(0);
        Bandeja bandejaSeca = new Bandeja(0);
        Bandeja alacena = new Bandeja(0);

        Thread fregador = new Thread(new Fregador(bandejaSucia, bandejaMojada));
        Thread secador = new Thread(new Secador(bandejaMojada, bandejaSeca));
        Thread organizador = new Thread(new Organizador(bandejaSeca, alacena));

        startThreads(fregador, secador, organizador);

        try {
            TimeUnit.SECONDS.sleep(60);
            System.out.println("Please stop, im going to blow the candles");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        interruptThreads(fregador, secador, organizador);

        System.out.println("Happy birthday!");
    }

    private static void interruptThreads(Thread fregador, Thread secador, Thread organizador) {
        fregador.interrupt();
        secador.interrupt();
        organizador.interrupt();
    }


    private static void startThreads(Thread fregador, Thread secador, Thread organizador){
        fregador.start();
        secador.start();
        organizador.start();
    }

}