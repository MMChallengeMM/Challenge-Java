package com.challengemm.models;

import java.util.Objects;


public class Administrador extends Usuario{
    private int nivelAcesso;

    public void criarNovaFalha(TIPO_FALHA tipoFalha, Equipamento equipamento, String descricao) {
        new Falha(tipoFalha, equipamento, descricao);
    }

    public Administrador() {
    }

    public Administrador(String idUsuario, String nome, TURNO_USUARIO turnoUsuario, int nivelAcesso) {
        super(idUsuario, nome, turnoUsuario);
        this.nivelAcesso = nivelAcesso;
    }

    public int getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(int nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Administrador that = (Administrador) o;
        return getNivelAcesso() == that.getNivelAcesso();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getNivelAcesso());
    }

    @Override
    public String toString() {
        return "Administrador{" +
                "nivelAcesso=" + nivelAcesso +
                "} " + super.toString();
    }
}
