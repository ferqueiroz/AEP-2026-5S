package org.ObservaAcao.Utilidades;

import java.io.IOException;
import java.util.Scanner;

public class Funcoes {
    public static Scanner leitor = new Scanner(System.in);

    public static void limparConsole(){
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void pressioneContinuar(){
        System.out.print("Pressione ENTER para continuar...");
        leitor.nextLine();
    }

    public static void pressioneVoltar(){
        System.out.print("Pressione ENTER para voltar...");
        leitor.nextLine();
    }
}
