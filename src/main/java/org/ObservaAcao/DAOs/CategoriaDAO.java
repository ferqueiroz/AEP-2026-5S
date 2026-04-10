package org.ObservaAcao.DAOs;

import org.ObservaAcao.Classes.Categoria;
import org.ObservaAcao.Conexao.Conexao;
import org.ObservaAcao.Utilidades.Funcoes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CategoriaDAO {
    private static Scanner leitor = new Scanner(System.in);

    public static List<Categoria> listarCategorias(){
        List<Categoria> listaCategorias = new ArrayList<>();

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement query = conexao.prepareStatement("SELECT * FROM categorias");
             ResultSet categorias = query.executeQuery()) {

            while (categorias.next()){
                listaCategorias.add(new Categoria(
                        categorias.getLong("cat_id"),
                        categorias.getString("cat_descricao"))
                );
            }
        } catch (Exception e) {
            System.out.print("\n❌ Ocorreu um erro ao tentar listar as Categorias!\n\nCausa: ");
            e.printStackTrace();
            leitor.nextLine();
        }

        return listaCategorias;
    }

    public static Categoria buscarCategoria(Long id){
        try (Connection conexao = Conexao.getConexao();
             PreparedStatement query = conexao.prepareStatement("SELECT * FROM categorias WHERE cat_id = ?")) {

            query.setLong(1, id);
            ResultSet categoria = query.executeQuery();

            return categoria.next() ?
                    new Categoria(
                            categoria.getLong("cat_id"),
                            categoria.getString("cat_descricao")
                    ) : null;
        } catch (Exception e) {
            System.out.print("\n❌ Ocorreu um erro ao tentar buscar a Categoria!\n\nCausa: ");
            e.printStackTrace();
            leitor.nextLine();
            return null;
        }
    }

    public static void criarCategoria(Categoria categoria){
        try (Connection conexao = Conexao.getConexao();
             PreparedStatement query = conexao.prepareStatement("INSERT INTO categorias (cat_descricao) VALUES (?)",
                                               PreparedStatement.RETURN_GENERATED_KEYS)) {

            query.setString(1, categoria.getDescricao());
            query.executeUpdate();

            ResultSet resultado = query.getGeneratedKeys();

            if(resultado.next()) categoria.setId(resultado.getLong(1));

            System.out.println("\n✅ Categoria criada com sucesso!\n");
            Funcoes.pressioneContinuar();
        } catch (Exception e) {
            categoria = null;
            System.out.print("\n❌ Ocorreu um erro ao tentar criar a Categoria!\n\nCausa: ");
            e.printStackTrace();
            leitor.nextLine();
        }
    }

    public static void atualizarCategoria(Long id, Categoria categoria){
        try (Connection conexao = Conexao.getConexao();
             PreparedStatement query = conexao.prepareStatement("UPDATE categorias SET cat_descricao = ? WHERE cat_id = ?")) {

            query.setString(1, categoria.getDescricao());
            query.setLong(2, id);
            query.executeUpdate();

            System.out.println("\n✅ Categoria atualizada com sucesso!\n");
            Funcoes.pressioneContinuar();
        } catch (Exception e) {
            System.out.print("\n❌ Ocorreu um erro ao tentar atualizar a Categoria!\n\nCausa: ");
            e.printStackTrace();
            leitor.nextLine();
        }
    }

    public static void deletarCategoria(Long id) {
        String sql = "DELETE FROM categorias WHERE cat_id = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement query = conexao.prepareStatement(sql)) {

            query.setLong(1, id);
            query.executeUpdate();

            System.out.println("🗑️ Categoria deletada com sucesso!\n");
            Funcoes.pressioneContinuar();
        } catch (Exception e) {
            System.out.print("\n❌ Ocorreu um erro ao tentar deletar a Categoria!\n\nCausa: ");
            e.printStackTrace();
            leitor.nextLine();
        }
    }
}
