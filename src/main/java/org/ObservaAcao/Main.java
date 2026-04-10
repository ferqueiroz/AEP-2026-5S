package org.ObservaAcao;

import org.ObservaAcao.Classes.Usuario;
import org.ObservaAcao.Menus.CidadaoMenu;
import org.ObservaAcao.Menus.GerenteMenu;
import org.ObservaAcao.Utilidades.Funcoes;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;


public class Main {
    public static Usuario usuarioConectado = null;
    public static Scanner leitor = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            Funcoes.limparConsole();

            System.out.println("-=Menu=-\n");
            System.out.println("1 - Login");
            System.out.println("2 - Registrar");
            System.out.println("0 - Sair");
            System.out.print("\nOpção: ");

            String opcao = leitor.nextLine();

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

            switch (usuarioConectado.getTipoUsuario()){
                case CIDADAO:
                    CidadaoMenu.menu();
                    break;
                case GERENTE:
                    GerenteMenu.menu();
                    break;
                default:
                    System.out.println("\nNão foi gerado um menu para esse tipo de usuário\n");
                    Funcoes.pressioneContinuar();
            }
        }
    }
}
