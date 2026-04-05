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
             PreparedStatement query = conexao.prepareStatement("SELECT * FROM historicostatussolicitacao WHERE hss_solicitacao = ? ORDER BY hss_datamudanca")) {

            query.setLong(1, solicitacao);
            ResultSet histStatusSolic = query.executeQuery();

            while (histStatusSolic.next()){
                listaSolicitacoes.add(new HistoricoStatusSolicitacao(
                        histStatusSolic.getLong("hss_id"),
                        SolicitacaoDAO.buscarSolicitacao(histStatusSolic.getLong("hss_solicitacao")),
                        UsuarioDAO.buscarUsuario(histStatusSolic.getLong("hss_gerente")),
                        StatusSolicitacao.fromString(histStatusSolic.getString("hss_status")),
                        histStatusSolic.getTimestamp("hss_dataMudanca").toLocalDateTime(),
                        histStatusSolic.getString("hss_resposta"),
                        histStatusSolic.getTimestamp("hss_datafinalizacao").toLocalDateTime()
                ));
            }
        } catch (Exception e) {
            System.out.print("\n❌ Ocorreu um erro ao tentar listar os Históricos de Status da Solicitacão!\n\nCausa: ");
            e.printStackTrace();
            Funcoes.pressioneContinuar();
        }

        return listaSolicitacoes;
    }

    public static void criarHistoricoStatusSolicitacao(HistoricoStatusSolicitacao historico){
        try (Connection conexao = Conexao.getConexao();
             PreparedStatement query = conexao.prepareStatement(
                     "INSERT INTO historicostatussolicitacao " +
                         "(hss_solicitacao, hss_gerente, hss_status, hss_datamudanca, hss_resposta, hss_datafinalizacao) " +
                         "VALUES (?, ?, ?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {

            query.setLong(1, historico.getSolicitacao().getId());
            if (historico.getGerente() != null) query.setLong(2, historico.getGerente().getId());
            else query.setNull(2, Types.BIGINT);
            query.setString(3, historico.getStatus().getStatusSolicitacao());
            query.setTimestamp(4, Timestamp.valueOf(historico.getDataMudanca()));
            query.setString(5, historico.getResposta());
            if (historico.getDataFinalizacao() != null) query.setTimestamp(6, Timestamp.valueOf(historico.getDataFinalizacao()));
            else query.setNull(6, Types.TIMESTAMP);
            query.executeUpdate();

            ResultSet resultado = query.getGeneratedKeys();

            if(resultado.next()) historico.setId(resultado.getLong(1));
        } catch (Exception e) {
            historico = null;
            System.out.print("\n❌ Ocorreu um erro ao tentar criar o Histórico de Status da Solicitação!\n\nCausa: ");
            e.printStackTrace();
            Funcoes.pressioneContinuar();
        }
    }

    public static void atualizarHistoricoStatusSolicitacao(Long id, HistoricoStatusSolicitacao historico){
        try (Connection conexao = Conexao.getConexao();
             PreparedStatement query = conexao.prepareStatement(
                     "UPDATE historicostatussolicitacao " +
                         "   SET hss_solicitacao = ?, hss_gerente = ?, hss_status = ?, hss_dataMudanca = ?, hss_resposta = ?, hss_datafinalizacao = ? " +
                         " WHERE hss_id = ?")) {

            query.setLong(1, historico.getSolicitacao().getId());
            if (historico.getGerente() != null) query.setLong(2, historico.getGerente().getId());
            else query.setNull(2, Types.BIGINT);
            query.setString(3, historico.getStatus().getStatusSolicitacao());
            query.setTimestamp(4, Timestamp.valueOf(historico.getDataMudanca()));
            query.setString(5, historico.getResposta());
            if (historico.getDataFinalizacao() != null) query.setTimestamp(6, Timestamp.valueOf(historico.getDataFinalizacao()));
            else query.setNull(6, Types.TIMESTAMP);
            query.setLong(7, id);
            query.executeUpdate();
        } catch (Exception e) {
            System.out.print("\n❌ Ocorreu um erro ao tentar atualizar o Histórico de Status da Solicitação!\n\nCausa: ");
            e.printStackTrace();
            Funcoes.pressioneContinuar();
        }
    }

    public static HistoricoStatusSolicitacao buscarUltimoStatusSolicitacao(Long solicitacao){
        try (Connection conexao = Conexao.getConexao();
             PreparedStatement query = conexao.prepareStatement("SELECT * FROM historicostatussolicitacao WHERE hss_solicitacao = ? ORDER BY hss_datamudanca DESC")) {

            query.setLong(1, solicitacao);
            ResultSet status = query.executeQuery();

            if (!status.next()) return null;

            return new HistoricoStatusSolicitacao(
                    status.getLong("hss_id"),
                    SolicitacaoDAO.buscarSolicitacao(status.getLong("hss_solicitacao")),
                    UsuarioDAO.buscarUsuario(status.getLong("hss_gerente")),
                    StatusSolicitacao.fromString(status.getString("hss_status")),
                    status.getTimestamp("hss_datamudanca").toLocalDateTime(),
                    status.getString("hss_resposta"),
                    status.getTimestamp("hss_datafinalizacao").toLocalDateTime()
                    );
        } catch (Exception e) {
            System.out.print("\n❌ Ocorreu um erro ao tentar buscar o último status da Solicitação!\n\nCausa: ");
            e.printStackTrace();
            Funcoes.pressioneContinuar();
            return null;
        }
    }

    public static StatusSolicitacao buscarProximoStatusSolicitacao(Long solicitacao){
        try (Connection conexao = Conexao.getConexao();
             PreparedStatement query = conexao.prepareStatement("SELECT hss_status FROM historicostatussolicitacao WHERE hss_solicitacao = ? ORDER BY hss_datamudanca DESC")) {

            query.setLong(1, solicitacao);
            ResultSet status = query.executeQuery();

            if (status.next()) {
                switch (StatusSolicitacao.fromString(status.getString("hss_status"))) {
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
            System.out.print("\n❌ Ocorreu um erro ao tentar buscar o próximo status da Solicitação!\n\nCausa: ");
            e.printStackTrace();
            Funcoes.pressioneContinuar();
            return null;
        }

        return null;
    }
}
