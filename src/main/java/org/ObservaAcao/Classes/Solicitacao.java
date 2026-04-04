package org.ObservaAcao.Classes;

import org.ObservaAcao.Enums.TipoStatusSolicitacao;

import java.io.File;

public class Solicitacao {
    private Long id;
    private Usuario usuario;
    private String protocolo;
    private Categoria categoria;
    private String descricao;
    private File anexo;
    private int prioridade;
    private String resposta;
    private TipoStatusSolicitacao statusAtual;

    public Solicitacao(Long id, Usuario usuario, String protocolo, Categoria categoria, String descricao, File anexo, int prioridade, String resposta, TipoStatusSolicitacao statusAtual) {
        this.id = id;
        this.usuario = usuario;
        this.protocolo = protocolo;
        this.categoria = categoria;
        this.descricao = descricao;
        this.anexo = anexo;
        this.prioridade = prioridade;
        this.resposta = resposta;
        this.statusAtual = statusAtual;
    }

    public Solicitacao() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public File getAnexo() {
        return anexo;
    }

    public void setAnexo(File anexo) {
        this.anexo = anexo;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public TipoStatusSolicitacao getStatusAtual() {
        return statusAtual;
    }

    public void setStatusAtual(TipoStatusSolicitacao statusAtual) {
        this.statusAtual = statusAtual;
    }
}
