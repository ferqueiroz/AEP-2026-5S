package org.ObservaAcao.DAOs;

import org.ObservaAcao.Classes.Categoria;
import org.ObservaAcao.Classes.HistoricoStatusSolicitacao;
import org.ObservaAcao.Classes.Solicitacao;
import org.ObservaAcao.Conexao.Conexao;
import org.ObservaAcao.Enums.StatusSolicitacao;
import org.ObservaAcao.Utilidades.Funcoes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HistoricoStatusSolicitacaoDAO {
    private static Scanner leitor = new Scanner(System.in);

    public static List<HistoricoStatusSolicitacao> listarHistoricoStatusSolicitacao (Long solicitacao){
        List<HistoricoStatusSolicitacao> listaSolicitacoes = new ArrayList<>();

        try (Connection conexao = Conexao.getConexao();
             PreparedStatement query = conexao.prepareStatement("SELECT * FROM historicostatussolicitacao WHERE hss_solicitacao = ?")) {

            query.setLong(1, solicitacao);
            ResultSet histStatusSolic = query.executeQuery();

            while (histStatusSolic.next()){
                listaSolicitacoes.add(new HistoricoStatusSolicitacao(
                        histStatusSolic.getLong("hss_id"),
                        SolicitacaoDAO.buscarSolicitacao(histStatusSolic.getLong("hss_solicitacao")),
                        UsuarioDAO.buscarUsuario(histStatusSolic.getLong("hss_gerente")),
                        StatusSolicitacao.valueOf(histStatusSolic.getString("hss_status")),
                        histStatusSolic.getTimestamp("hss_dataMudanca").toLocalDateTime(),
                        histStatusSolic.getString("hss_resposta")
                ));
            }
        } catch (Exception e) {
            System.out.print("\n❌ Ocorreu um erro ao tentar listar os Históricos de Status da Solicitacão!\n\nCausa: ");
            e.printStackTrace();
            leitor.nextLine();
        }

        return listaSolicitacoes;
    }

    public static void criarHistoricoStatusSolicitacao(HistoricoStatusSolicitacao historico){
        try (Connection conexao = Conexao.getConexao();
             PreparedStatement query = conexao.prepareStatement(
                     "INSERT INTO historicostatussolicitacao " +
                         "(hss_solicitacao, hss_gerente, hss_status, hss_dataMudanca, hss_resposta) " +
                         "VALUES (?, ?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {

            query.setLong(1, historico.getSolicitacao().getId());
            if (historico.getGerente() != null) query.setLong(2, historico.getGerente().getId());
            else query.setNull(2, Types.BIGINT);
            query.setString(3, historico.getStatus().getStatusSolicitacao());
            query.setTimestamp(4, Timestamp.valueOf(historico.getDataMudanca()));
            query.setString(5, historico.getResposta());
            query.executeUpdate();

            ResultSet resultado = query.getGeneratedKeys();

            if(resultado.next()) historico.setId(resultado.getLong(1));
        } catch (Exception e) {
            historico = null;
            System.out.print("\n❌ Ocorreu um erro ao tentar criar o Histórico de Status da Solicitação!\n\nCausa: ");
            e.printStackTrace();
            leitor.nextLine();
        }
    }

    public static StatusSolicitacao buscarProximoStatusSolicitacao(Long solicitacao){
        try (Connection conexao = Conexao.getConexao();
             PreparedStatement query = conexao.prepareStatement("SELECT hss_status FROM historicostatussolicitacao WHERE hss_solicitacao = ? ORDER BY hss_datamudanca DESC")) {

            query.setLong(1, solicitacao);
            ResultSet status = query.executeQuery();

            if (status.next()) {
                switch (StatusSolicitacao.valueOf(status.getString("hss_status"))) {
                    case ATENDIMENTO:
                        return StatusSolicitacao.TRIAGEM;
                    case TRIAGEM:
                        return StatusSolicitacao.EXECUCAO;
                    case EXECUCAO:
                        return StatusSolicitacao.RESOLVIDO;
                    case RESOLVIDO:
                        return StatusSolicitacao.ENCERRADO;
                    case ENCERRADO:
                        return null;
                }
            } else {
                return StatusSolicitacao.ATENDIMENTO;
            }
        } catch (Exception e) {
            System.out.print("\n❌ Ocorreu um erro ao tentar buscar a Categoria!\n\nCausa: ");
            e.printStackTrace();
            leitor.nextLine();
            return null;
        }

        return null;
    }
}
