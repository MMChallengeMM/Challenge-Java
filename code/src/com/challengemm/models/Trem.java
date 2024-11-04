package com.challengemm.models;

import com.challengemm.main.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Trem {

    private String idTrem;
    private List<Vagao> vagoes = new ArrayList<>();

    public void addVagao(Vagao vagao) {
        vagoes.add(vagao);
    }

    public void removeVagao(Vagao vagao) {
        vagoes.remove(vagao);
    }

    //Métodos Gerais


    public Trem() {
        this.idTrem = String.valueOf(Main.getTodosTrens().size() + 1);
        Main.addTremNoSistema(this);
    }

    public Trem(List<Vagao> vagoes) {
        this.idTrem = String.valueOf(Main.getTodosTrens().size() + 1);
        this.vagoes = vagoes;
        Main.addTremNoSistema(this);
    }

    public String getIdTrem() {
        return idTrem;
    }

    public List<Vagao> getVagoes() {
        return vagoes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trem trem = (Trem) o;
        return Objects.equals(getIdTrem(), trem.getIdTrem()) && Objects.equals(getVagoes(), trem.getVagoes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdTrem(), getVagoes());
    }

    @Override
    public String toString() {
        return "Trem{" +
                "idTrem='" + idTrem + '\'' +
                ", vagoes=" + vagoes +
                '}';
    }
}
