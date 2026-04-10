package org.ObservaAcao;

import org.ObservaAcao.Classes.Usuario;
import org.ObservaAcao.DAOs.CategoriaDAO;
import org.ObservaAcao.Enums.TipoUsuario;
import org.ObservaAcao.Utilidades.Funcoes;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;


public class Main {
    public static Usuario usuarioConectado = null;
    public static Scanner leitor = new Scanner(System.in);

    /*public static void solicitacoesMenu(){
        String opcao;

        do {
            Funcoes.limparConsole();

            System.out.println("-=Menu de Solicitações=-\n");
            switch (usuarioConectado.getTipoUsuario()) {
                case CIDADAO:
                    System.out.println("1 - Criar Solicitação");
                    System.out.println("2 - Listar Todas as Solicitações");
                    System.out.println("3 - Buscar Solicitação por Protocolo");
                    System.out.println("0 - Voltar\n");
                    System.out.print("Opção: ");

                    opcao = leitor.nextLine();

                    switch (opcao){
                        case "1":
                            break;
                        case "2":
                            break;
                        case "3":
                            break;
                        case "0":
                            return;
                        default:
                            continue;
                    }

                    break;
                case GERENTE:
                    System.out.println("1 - Listar Todas as Solicitações");
                    System.out.println("2 - Buscar Solicitação por Protocolo");
                    System.out.println("0 - Voltar\n");
                    System.out.print("Opção: ");
                    opcao = leitor.nextLine();

                    switch (opcao){
                        case "1":
                            break;
                        case "2":
                            break;
                        case "0":
                            return;
                        default:
                            continue;
                    }

                    break;
                default:
                    return;
            }
        } while (opcao != "0");
    }*/

    public static void solicitacoesMenu() {
        String opcao;

        do {
            Funcoes.limparConsole();

            System.out.println("-=Menu de Solicitações=-\n");

            if (usuarioConectado.getTipoUsuario() == TipoUsuario.CIDADAO) {
                System.out.println("1 - Criar Solicitação");
                System.out.println("2 - Listar Todas as Solicitações");
                System.out.println("3 - Buscar Solicitação por Protocolo");
            } else {
                System.out.println("1 - Listar Todas as Solicitações");
                System.out.println("2 - Buscar Solicitação por Protocolo");
            }

            System.out.println("0 - Voltar\n");
            System.out.print("Opção: ");

            opcao = leitor.nextLine();

            switch (opcao) {
                case "1":
                    break;
                case "2":
                    break;
                case "3":
                    if (usuarioConectado.getTipoUsuario() == TipoUsuario.CIDADAO) {
                        break;
                    }
                    break;
                case "0":
                    return;
                default:
                    continue;
            }

        } while (!opcao.equals("0"));
    }

    public static void categoriasMenu(){
        String opcao;
        Long id;

        do {
            Funcoes.limparConsole();

            System.out.println("-=Menu de Categorias=-\n");
            switch (usuarioConectado.getTipoUsuario()) {
                case CIDADAO:
                    return;
                case GERENTE:
                    System.out.println("1 - Listar Categorias");
                    System.out.println("2 - Criar Categoria");
                    System.out.println("3 - Atualizar Categoria");
                    System.out.println("4 - Deletar Categoria");
                    System.out.println("0 - Voltar\n");
                    System.out.print("Opção: ");

                    opcao = leitor.nextLine();

                    switch (opcao){
                        case "1":
                            Categoria.listarCategorias();
                            Funcoes.pressioneVoltar();
                            break;
                        case "2":
                            Categoria.manutencaoCategoria();
                            break;
                        case "3":
                            Categoria.listarCategorias();
                            System.out.print("Digite o código da categoria (0 para voltar): ");

                            id = leitor.nextLong();

                            if (id == 0) continue;

                            if (!Categoria.validaCategoria(id)) continue;

                            Categoria.manutencaoCategoria(id);
                            break;
                        case "4":
                            Categoria.listarCategorias();
                            System.out.print("Digite o código da categoria (0 para voltar): ");

                            id = leitor.nextLong();

                            if (id == 0) continue;

                            if (!Categoria.validaCategoria(id)) continue;

                            CategoriaDAO.deletarCategoria(id);
                            break;
                        case "0":
                            return;
                        default:
                            continue;
                    }
                    break;
                default:
                    return;
            }
        } while (opcao != "0");
    }

    static void main() {
        String opcao;

        do {
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
