package org.ObservaAcao.Enums;

public enum StatusSolicitacao {
    ATENDIMENTO("ATENDIMENTO"),
    TRIAGEM("TRIAGEM"),
    EXECUCAO("EM EXECUÇÃO"),
    RESOLVIDO("RESOLVIDO"),
    ENCERRADO("ENCERRADO");

    private String StatusSolicitacao;

    StatusSolicitacao(String StatusSolicitacao) {
        this.StatusSolicitacao = StatusSolicitacao;
    }

    public String getStatusSolicitacao() {
        return StatusSolicitacao;
    }
}