package org.ObservaAcao.Enums;

public enum TipoUsuario {
    CIDADAO("CIDADAO"),
    GERENTE("GERENTE");

    private String tipoUsuario;

    TipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }
}
