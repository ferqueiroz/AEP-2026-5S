package org.ObservaAcao;

import org.ObservaAcao.Classes.Usuario;
import org.ObservaAcao.DAOs.UsuarioDAO;
import org.ObservaAcao.Utilidades.Funcoes;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    static void main() throws IOException, InterruptedException {
        String opcao;
        Usuario usuarioConectado = new Usuario();
        Scanner leitor = new Scanner(System.in);

        do {
            Funcoes.LimparConsole();

            System.out.println("-=MENU=-\n");
            System.out.println("1 - Login");
            System.out.println("2 - Registrar");
            System.out.println("0 - Sair");
            System.out.print("\nOpção: ");
            opcao = leitor.nextLine();

            switch (opcao){
                case "1":
                    usuarioConectado = Usuario.logarUsuario();
                    break;
                case "2":
                    usuarioConectado = Usuario.registrarUsuario();
                    break;
                case "0":
                    return;
                default:
                    continue;
            }
        } while (opcao != "0" && usuarioConectado == null);
    }
}
