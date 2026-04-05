package org.ObservaAcao.Classes;

import org.ObservaAcao.Enums.StatusSolicitacao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class HistoricoStatusSolicitacao {
    private Long id;
    private Solicitacao solicitacao;
    private Usuario gerente;
    private StatusSolicitacao status;
    private LocalDateTime dataMudanca;
    private String resposta;
    private LocalDateTime dataFinalizacao;

    private static Scanner leitor = new Scanner(System.in);

    public void definirResposta(){
        String resposta;

        do {
            System.out.print("Resposta do Status Atual: ");
            resposta = leitor.nextLine();
        } while (resposta == "");

        setResposta(resposta);
    }

    @Override
    public String toString() {
        System.out.printf("Gerente: %s | ", this.gerente != null ? this.gerente.getNome() : "");
        System.out.printf("Status: %s | ", this.status.getStatusSolicitacao());
        System.out.printf("Data Mudança: %s | ", this.dataMudanca.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        System.out.printf("Data Finalização: %s\n", this.dataFinalizacao != null ? this.dataFinalizacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) : "");
        System.out.printf("Resposta: %s\n", this.resposta != null ? this.resposta : "");
        return "";
    }

    public HistoricoStatusSolicitacao(Long id, Solicitacao solicitacao, Usuario gerente, StatusSolicitacao status, LocalDateTime dataMudanca, String resposta, LocalDateTime dataFinalizacao) {
        this.id = id;
        this.solicitacao = solicitacao;
        this.gerente = gerente;
        this.status = status;
        this.dataMudanca = dataMudanca;
        this.resposta = resposta;
        this.dataFinalizacao = dataFinalizacao;
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

    public LocalDateTime getDataFinalizacao() {
        return dataFinalizacao;
    }

    public void setDataFinalizacao(LocalDateTime dataFinalizacao) {
        this.dataFinalizacao = dataFinalizacao;
    }
}