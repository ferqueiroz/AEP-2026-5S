package org.ObservaAcao.Menus;

import org.ObservaAcao.Classes.Categoria;
import org.ObservaAcao.Classes.Solicitacao;
import org.ObservaAcao.DAOs.CategoriaDAO;
import org.ObservaAcao.Utilidades.Funcoes;

public class GerenteMenu {
    public static void menu(){
        while (true) {
            Funcoes.limparConsole();
            System.out.println("-=Menu=-\n");
            System.out.println("1 - Solicitações");
            System.out.println("2 - Categorias");
            System.out.println("0 - Sair\n");
            System.out.print("Opção: ");

            String opcao = Funcoes.leitor.nextLine();

            switch (opcao) {
                case "1":
                    solicitacoesMenu();
                    break;
                case "2":
                    categoriasMenu();
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
            System.out.println("1 - Listar Todas as Solicitações");
            System.out.println("2 - Listar Todas as Solicitações (Mais Detalhes)");
            System.out.println("3 - Buscar Solicitação por Protocolo");
            System.out.println("4 - Mudar Status e Responder Solicitações");
            System.out.println("0 - Voltar\n");
            System.out.print("Opção: ");

            String opcao = Funcoes.leitor.nextLine();

            switch (opcao) {
                case "1":
                    Solicitacao.listarSolicitacoes(false);
                    Funcoes.pressioneVoltar();
                    break;
                case "2":
                    Solicitacao.listarSolicitacoes(true);
                    Funcoes.pressioneVoltar();
                    break;
                case "3":
                    Solicitacao.buscarSolicitacaoPorProtocolo();
                    Funcoes.pressioneVoltar();
                    break;
                case "4":
                    Solicitacao.mudarStatusResponderSolicitacao();
                    Funcoes.pressioneVoltar();
                case "0":
                    return;
            }
        }
    }

    public static void categoriasMenu(){
        while (true) {
            Funcoes.limparConsole();
            System.out.println("-=Menu de Categorias=-\n");
            System.out.println("1 - Listar Categorias");
            System.out.println("2 - Criar Categoria");
            System.out.println("3 - Atualizar Categoria");
            System.out.println("4 - Deletar Categoria");
            System.out.println("0 - Voltar\n");
            System.out.print("Opção: ");

            String opcao = Funcoes.leitor.nextLine();

            Long id;
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
                    id = buscarCodigoCategoria();

                    if (id == 0) continue;

                    Categoria.manutencaoCategoria(id);
                    break;
                case "4":
                    Categoria.listarCategorias();
                    id = buscarCodigoCategoria();

                    if (id == 0) continue;

                    CategoriaDAO.deletarCategoria(id);
                    break;
                case "0":
                    return;
            }
        }
    }

    public static Long buscarCodigoCategoria(){
        Long id;

        while (true) {
            System.out.print("Digite o código da categoria (0 para voltar): ");

            try {
                id = Funcoes.leitor.nextLong();
            } catch (Exception e) {
                continue;
            }

            if (id == 0) return id;

            if (Categoria.validaCategoria(id)) break;
        }

        return id;
    }

}
