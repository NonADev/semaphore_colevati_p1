package model;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class ThreadCavaleiro extends Thread {
    private int idCaveleiro;
    private int buff = 0;
    private Semaphore semaphore;
    private static boolean tocha = true, pedra = true;
    private static int portaCerta = ThreadLocalRandom.current().nextInt(1, 5);

    public ThreadCavaleiro(int idCavaleiro, Semaphore semaphore) {
        this.idCaveleiro = idCavaleiro;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        cavalgar();
        entrarPorta();
    }

    private void entrarPorta() {
        if (this.idCaveleiro == this.portaCerta) {
            System.out.println(String.format(ConsoleColors.GREEN_BRIGHT+"caveleiro #%s saiu são e salvo!"+ConsoleColors.RESET, this.idCaveleiro));
        } else {
            System.out.println(String.format(ConsoleColors.RED_BRIGHT+"caveleiro #%s foi devorado por uma temivel criatura!"+ConsoleColors.RESET, this.idCaveleiro));
        }
    }

    private void cavalgar() {
        int distanciaTotal = 2000;
        Double distanciaPercorrida = 0.0;
        while (distanciaPercorrida < distanciaTotal) {
            try {
                this.semaphore.acquire();
                Double deslocamento = ThreadLocalRandom.current().nextDouble(2, 4.001);
                distanciaPercorrida += deslocamento + this.buff;
                eventoEncontro(distanciaPercorrida);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                this.semaphore.release();
                try {
                    sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(String.format(ConsoleColors.BLUE_BRIGHT+"cavaleiro #%s entrou no corredor"+ConsoleColors.RESET, this.idCaveleiro));
    }

    private void eventoEncontro(Double distanciaPercorrida) {
        if (distanciaPercorrida >= 500 && this.tocha == true) {
            System.out.println(String.format(ConsoleColors.YELLOW_BOLD+"o cavaleiro #%s pegou a tocha"+ConsoleColors.RESET, this.idCaveleiro));
            this.tocha = false;
            this.buff += 2;
        } else if (distanciaPercorrida >= 1500 && this.pedra == true) {
            System.out.println(String.format(ConsoleColors.YELLOW_BOLD+"o cavaleiro #%s pegou a pedra"+ConsoleColors.RESET, this.idCaveleiro));
            this.pedra = false;
            this.buff += 2;
        }
    }
}
