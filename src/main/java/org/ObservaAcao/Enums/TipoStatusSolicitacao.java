package org.ObservaAcao.Enums;

public enum TipoStatusSolicitacao {
    ATENDIMENTO("ATENDIMENTO"),
    TRIAGEM("TRIAGEM"),
    EXECUCAO("EM EXECUÇÃO"),
    RESOLVIDO("RESOLVIDO"),
    ENCERRADO("ENCERRADO");

    private String tipoStatusSolicitacao;

    TipoStatusSolicitacao(String tipoStatusSolicitacao) {
        this.tipoStatusSolicitacao = tipoStatusSolicitacao;
    }

    public String getTipoStatusSolicitacao() {
        return tipoStatusSolicitacao;
    }
}