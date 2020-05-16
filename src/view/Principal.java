package view;

import model.ThreadCavaleiro;

import java.util.concurrent.Semaphore;

public class Principal {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);
        ThreadCavaleiro[] cavaleiros = new ThreadCavaleiro[4];
        for (int i = 0; i < 4; i++) {
            cavaleiros[i] = new ThreadCavaleiro(i + 1, semaphore);
        }
        for (int i = 0; i < 4; i++) {
            cavaleiros[i].start();
        }
    }
}
