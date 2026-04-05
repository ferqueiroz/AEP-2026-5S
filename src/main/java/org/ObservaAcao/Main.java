package org.ObservaAcao;

import org.ObservaAcao.Classes.Categoria;
import org.ObservaAcao.Classes.Solicitacao;
import org.ObservaAcao.Classes.Usuario;
import org.ObservaAcao.DAOs.CategoriaDAO;
import org.ObservaAcao.DAOs.SolicitacaoDAO;
import org.ObservaAcao.Utilidades.Funcoes;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static Usuario usuarioConectado = null;
    public static Scanner leitor = new Scanner(System.in);

    public static void solicitacoesMenu(){
        String opcao;

        do {
            Funcoes.limparConsole();

            System.out.println("-=Menu de Solicitações=-\n");
            switch (usuarioConectado.getTipoUsuario()) {
                case CIDADAO:
                    System.out.println("1 - Criar Solicitação");
                    System.out.println("2 - Listar Todas as Solicitações");
                    System.out.println("3 - Buscar Solicitação por Protocolo");
                    System.out.println("4 - Deletar Solicitação");
                    System.out.println("0 - Voltar\n");
                    System.out.print("Opção: ");

                    opcao = leitor.nextLine();

                    switch (opcao){
                        case "1":
                            Solicitacao.manutencaoSolicitacao();
                            break;
                        case "2":
                            Solicitacao.listarSolicitacoes(usuarioConectado.getId());
                            Funcoes.pressioneVoltar();
                            break;
                        case "3":
                            Solicitacao.buscarSolicitacaoPorProtocolo();
                            Funcoes.pressioneVoltar();
                            break;
                        case "4":
                            Solicitacao.deletarSolicitacao();
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
                    System.out.println("3 - Mudar Status e Responder Solicitações");
                    System.out.println("0 - Voltar\n");
                    System.out.print("Opção: ");
                    opcao = leitor.nextLine();

                    switch (opcao){
                        case "1":
                            Solicitacao.listarSolicitacoes();
                            Funcoes.pressioneVoltar();
                            break;
                        case "2":
                            Solicitacao.buscarSolicitacaoPorProtocolo();
                            Funcoes.pressioneVoltar();
                            break;
                        case "3":
                            Solicitacao.mudarStatusResponderSolicitacao();
                            Funcoes.pressioneVoltar();
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

        do {
            Funcoes.limparConsole();
            System.out.println("-=Menu=-\n");
            switch (usuarioConectado.getTipoUsuario()){
                case CIDADAO:
                    System.out.println("1 - Solicitações");
                    System.out.println("0 - Sair\n");
                    System.out.print("Opção: ");
                    opcao = leitor.nextLine();

                    switch (opcao){
                        case "1":
                            solicitacoesMenu();
                            break;
                        case "0":
                            return;
                        default:
                            continue;
                    }

                    break;
                case GERENTE:
                    System.out.println("1 - Solicitações");
                    System.out.println("2 - Categorias");
                    System.out.println("0 - Sair\n");
                    System.out.print("Opção: ");
                    opcao = leitor.nextLine();

                    switch (opcao){
                        case "1":
                            solicitacoesMenu();
                            break;
                        case "2":
                            categoriasMenu();
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
        } while(opcao != "0");
    }
}
