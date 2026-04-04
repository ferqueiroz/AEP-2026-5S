package org.ObservaAcao.Classes;

import org.ObservaAcao.DAOs.CategoriaDAO;
import org.ObservaAcao.Utilidades.Funcoes;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Categoria {
    private Long id;
    private String descricao;

    private static Scanner leitor = new Scanner(System.in);

    public Categoria(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Categoria() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void definirDescricao(){
        String descricao;

        do {
            System.out.print("Descrição da Categoria: ");
            descricao = leitor.nextLine();
        } while (descricao == "");

        setDescricao(descricao);
    }

    public static Categoria manutencaoCategoria(){
        return manutencaoCategoria(0L);
    }

    public static Categoria manutencaoCategoria(Long id){
        Categoria categoria;

        do{
            categoria = new Categoria();

            categoria.definirDescricao();

            if (id == 0) CategoriaDAO.criarCategoria(categoria);
            else CategoriaDAO.atualizarCategoria(id, categoria);
        } while (categoria == null);

        return categoria;
    }

    public static void listarCategorias(){
        List<Categoria> categorias = new ArrayList<>();

        categorias = CategoriaDAO.listarCategorias();

        if (categorias.isEmpty()){
            System.out.print("Não existe nenhuma categoria cadastrada!\n\nPressione ENTER para voltar...");
            leitor.nextLine();
            return;
        }

        Funcoes.limparConsole();
        System.out.println("-=Categorias=-\n");
        for (Categoria categoria : categorias){
            System.out.println("---------------");
            System.out.printf("id: %d\n", categoria.getId());
            System.out.printf("descrição: %s\n", categoria.getDescricao());
        }

        System.out.println("---------------\n");
    }

    public static boolean validaCategoria(Long id){
        if (CategoriaDAO.buscarCategoria(id) == null) {
            System.out.println("\nCategoria não existe!\n");
            Funcoes.pressioneVoltar();
            return false;
        }

        return true;
    }
}
