package org.ObservaAcao.Classes;

import org.ObservaAcao.Enums.StatusSolicitacao;

import java.time.LocalDateTime;

public class HistoricoStatusSolicitacao {
    private Long id;
    private Solicitacao solicitacao;
    private Usuario gerente;
    private StatusSolicitacao status;
    private LocalDateTime dataMudanca;
    private String resposta;

    public HistoricoStatusSolicitacao(Long id, Solicitacao solicitacao, Usuario gerente, StatusSolicitacao status, LocalDateTime dataMudanca, String resposta) {
        this.id = id;
        this.solicitacao = solicitacao;
        this.gerente = gerente;
        this.status = status;
        this.dataMudanca = dataMudanca;
        this.resposta = resposta;
    }

    public HistoricoStatusSolicitacao() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Solicitacao getSolicitacao() {
        return solicitacao;
    }

    public void setSolicitacao(Solicitacao solicitacao) {
        this.solicitacao = solicitacao;
    }

    public Usuario getGerente() {
        return gerente;
    }

    public void setGerente(Usuario gerente) {
        this.gerente = gerente;
    }

    public StatusSolicitacao getStatus() {
        return status;
    }

    public void setStatus(StatusSolicitacao status) {
        this.status = status;
    }

    public LocalDateTime getDataMudanca() {
        return dataMudanca;
    }

    public void setDataMudanca(LocalDateTime dataMudanca) {
        this.dataMudanca = dataMudanca;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }
}