package org.ObservaAcao.Menus;

import org.ObservaAcao.Classes.Solicitacao;
import org.ObservaAcao.Main;
import org.ObservaAcao.Utilidades.Funcoes;

public class CidadaoMenu {
    public static void menu(){
        while (true){
            Funcoes.limparConsole();
            System.out.println("-=Menu=-\n");
            System.out.println("1 - Solicitações");
            System.out.println("0 - Sair\n");
            System.out.print("Opção: ");

            String opcao = Funcoes.leitor.nextLine();

            switch (opcao){
                case "1":
                    solicitacoesMenu();
                    break;
                case "0":
                    return;
            }
        }
    }

    public static void solicitacoesMenu(){
        while (true) {
            Funcoes.limparConsole();
            System.out.println("-=Menu de Solicitações=-\n");
            System.out.println("1 - Criar Solicitação");
            System.out.println("2 - Listar Todas as Solicitações");
            System.out.println("3 - Listar Todas as Solicitações (Mais Detalhes)");
            System.out.println("4 - Buscar Solicitação por Protocolo");
            System.out.println("5 - Deletar Solicitação");
            System.out.println("0 - Voltar\n");
            System.out.print("Opção: ");

            String opcao = Funcoes.leitor.nextLine();

            switch (opcao) {
                case "1":
                    Solicitacao.manutencaoSolicitacao();
                    break;
                case "2":
                    Solicitacao.listarSolicitacoes(Main.usuarioConectado.getId(), false);
                    Funcoes.pressioneVoltar();
                    break;
                case "3":
                    Solicitacao.listarSolicitacoes(Main.usuarioConectado.getId(), true);
                    Funcoes.pressioneVoltar();
                    break;
                case "4":
                    Solicitacao.buscarSolicitacaoPorProtocolo();
                    Funcoes.pressioneVoltar();
                    break;
                case "5":
                    Solicitacao.deletarSolicitacao();
                    break;
                case "0":
                    return;
            }
        }
    }
}
