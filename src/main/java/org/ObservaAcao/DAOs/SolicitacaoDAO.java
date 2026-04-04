package org.ObservaAcao.DAOs;

import org.ObservaAcao.Classes.Categoria;
import org.ObservaAcao.Classes.Solicitacao;
import org.ObservaAcao.Classes.Usuario;
import org.ObservaAcao.Conexao.Conexao;
import org.ObservaAcao.Enums.StatusSolicitacao;
import org.ObservaAcao.Utilidades.Funcoes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SolicitacaoDAO {
    private static Scanner leitor = new Scanner(System.in);

    public static List<Solicitacao> listarSolicitacoes(){
        return listarSolicitacoes(0L);
    }

    public static List<Solicitacao> listarSolicitacoes(Long usuario){
        List<Solicitacao> listaSolicitacoes = new ArrayList<>();
        String sql = "SELECT * FROM solicitacoes";

        if (usuario != 0) sql += " WHERE slc_usuario = ?";

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement query = conexao.prepareStatement(sql)) {

            if (usuario != 0) query.setLong(1, usuario);

            ResultSet solicitacoes = query.executeQuery();

            while (solicitacoes.next()){
                listaSolicitacoes.add(new Solicitacao(
                        solicitacoes.getLong("slc_id"),
                        UsuarioDAO.buscarUsuario(solicitacoes.getLong("slc_usuario")),
                        solicitacoes.getString("slc_protocolo"),
                        CategoriaDAO.buscarCategoria(solicitacoes.getLong("slc_categoria")),
                        solicitacoes.getString("slc_descricao"),
                        solicitacoes.getBytes("slc_anexo"),
                        solicitacoes.getInt("slc_prioridade"),
                        StatusSolicitacao.valueOf(solicitacoes.getString("slc_status")))
                );
            }
        } catch (Exception e) {
            System.out.print("\n❌ Ocorreu um erro ao tentar listar as Solicitacoes!\n\nCausa: ");
            e.printStackTrace();
            leitor.nextLine();
        }

        return listaSolicitacoes;
    }

    public static Solicitacao buscarSolicitacao(Long id){
        try (Connection conexao = Conexao.getConexao();
             PreparedStatement query = conexao.prepareStatement("SELECT * FROM solicitacoes WHERE slc_id = ?")) {

            query.setLong(1, id);
            ResultSet solicitacao = query.executeQuery();

            return solicitacao.next() ?
                    new Solicitacao(
                            solicitacao.getLong("slc_id"),
                            UsuarioDAO.buscarUsuario(solicitacao.getLong("slc_usuario")),
                            solicitacao.getString("slc_protocolo"),
                            CategoriaDAO.buscarCategoria(solicitacao.getLong("slc_categoria")),
                            solicitacao.getString("slc_descricao"),
                            solicitacao.getBytes("slc_anexo"),
                            solicitacao.getInt("slc_prioridade"),
                            StatusSolicitacao.valueOf(solicitacao.getString("slc_status"))
                    ) : null;
        } catch (Exception e) {
            System.out.print("\n❌ Ocorreu um erro ao tentar buscar a Solicitacao!\n\nCausa: ");
            e.printStackTrace();
            leitor.nextLine();
            return null;
        }
    }

    public static Solicitacao buscarSolicitacaoPorProtocolo(String protocolo){
        try (Connection conexao = Conexao.getConexao();
             PreparedStatement query = conexao.prepareStatement("SELECT * FROM solicitacoes WHERE slc_protocolo = ?")) {

            query.setString(1, protocolo);
            ResultSet solicitacao = query.executeQuery();

            return solicitacao.next() ?
                    new Solicitacao(
                            solicitacao.getLong("slc_id"),
                            UsuarioDAO.buscarUsuario(solicitacao.getLong("slc_usuario")),
                            solicitacao.getString("slc_protocolo"),
                            CategoriaDAO.buscarCategoria(solicitacao.getLong("slc_categoria")),
                            solicitacao.getString("slc_descricao"),
                            solicitacao.getBytes("slc_anexo"),
                            solicitacao.getInt("slc_prioridade"),
                            StatusSolicitacao.valueOf(solicitacao.getString("slc_status"))
                    ) : null;
        } catch (Exception e) {
            System.out.print("\n❌ Ocorreu um erro ao tentar buscar a Solicitacao!\n\nCausa: ");
            e.printStackTrace();
            leitor.nextLine();
            return null;
        }
    }

    public static void criarSolicitacao(Solicitacao solicitacao){
        try (Connection conexao = Conexao.getConexao();
             PreparedStatement query = conexao.prepareStatement(
                     "INSERT INTO solicitacoes " +
                         "(slc_usuario, slc_protocolo, slc_categoria, slc_descricao, slc_anexo, slc_prioridade, slc_status) " +
                         "VALUES(?, ?, ?, ?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {

            query.setLong(1, solicitacao.getUsuario().getId());
            query.setString(2, solicitacao.getProtocolo());
            query.setLong(3, solicitacao.getCategoria().getId());
            query.setString(4, solicitacao.getDescricao());
            query.setBytes(5, solicitacao.getAnexo());
            query.setInt(6, solicitacao.getPrioridade());
            query.setString(7, solicitacao.getStatus().getStatusSolicitacao());
            query.executeUpdate();

            ResultSet resultado = query.getGeneratedKeys();

            if(resultado.next()) solicitacao.setId(resultado.getLong(1));

            System.out.println("\n✅ Solicitacao criada com sucesso!\n");
            Funcoes.pressioneContinuar();
        } catch (Exception e) {
            solicitacao = null;
            System.out.print("\n❌ Ocorreu um erro ao tentar criar a Solicitacao!\n\nCausa: ");
            e.printStackTrace();
            leitor.nextLine();
        }
    }

    public static void atualizarSolicitacao(Long id, Solicitacao solicitacao){
        try (Connection conexao = Conexao.getConexao();
             PreparedStatement query = conexao.prepareStatement(
                     "UPDATE solicitacoes " +
                         "   SET slc_usuario = ?, slc_protocolo = ?, slc_categoria = ?, slc_descricao = ?, slc_anexo = ?, slc_prioridade = ?, slc_status = ? " +
                         " WHERE slc_id = ?")) {

            query.setLong(1, solicitacao.getUsuario().getId());
            query.setString(2, solicitacao.getProtocolo());
            query.setLong(3, solicitacao.getCategoria().getId());
            query.setString(4, solicitacao.getDescricao());
            query.setBytes(5, solicitacao.getAnexo());
            query.setInt(6, solicitacao.getPrioridade());
            query.setString(7, solicitacao.getStatus().getStatusSolicitacao());
            query.setLong(8, id);
            query.executeUpdate();

            System.out.println("\n✅ Solicitacao atualizada com sucesso!\n");
            Funcoes.pressioneContinuar();
        } catch (Exception e) {
            System.out.print("\n❌ Ocorreu um erro ao tentar atualizar a Solicitacao!\n\nCausa: ");
            e.printStackTrace();
            leitor.nextLine();
        }
    }

    public static void deletarSolicitacao(Long id) {
        try (Connection conexao = Conexao.getConexao();
             PreparedStatement query = conexao.prepareStatement(
                     "DELETE FROM solicitacoes " +
                         " WHERE slc_id = ?")) {

            query.setLong(1, id);
            query.executeUpdate();

            System.out.println("\n🗑️ Solicitacao deletada com sucesso!\n");
            Funcoes.pressioneContinuar();
        } catch (Exception e) {
            System.out.print("\n❌ Ocorreu um erro ao tentar deletar a Solicitacao!\n\nCausa: ");
            e.printStackTrace();
            leitor.nextLine();
        }
    }
}
