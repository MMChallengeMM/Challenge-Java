package com.challengemm.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Operador extends Usuario{
    private String setorOperacao;

    public Operador(String nome, TURNO_USUARIO turnoUsuario, String setorOperacao) {
        super(nome, turnoUsuario);
        this.setorOperacao = setorOperacao;
    }

    public String getSetorOperacao() {
        return setorOperacao;
    }

    public void setSetorOperacao(String setorOperacao) {
        this.setorOperacao = setorOperacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Operador operador = (Operador) o;
        return Objects.equals(getSetorOperacao(), operador.getSetorOperacao());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSetorOperacao());
    }

    @Override
    public String toString() {
        return "Operador{" +
                "setorOperacao='" + setorOperacao + '\'' +
                "} " + super.toString();
    }
}
