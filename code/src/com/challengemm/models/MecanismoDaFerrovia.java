package com.challengemm.models;

import com.challengemm.main.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class MecanismoDaFerrovia {

    private final String id;
    private String nome;
    private List<Equipamento> equipamentos = new ArrayList<>();

    public void addEquipamento(Equipamento equipamento) {
        equipamentos.add(equipamento);
    }

    public void removeEquipamento(Equipamento equipamento) {
        equipamentos.remove(equipamento);
    }

    // Métodos Gerais


    public MecanismoDaFerrovia(String nome) {
        this.id = String.valueOf(Main.getTodosMecanismos().size() + 1);
        this.nome = nome;
        Main.addMecanismoNoSistema(this);
    }

    public MecanismoDaFerrovia(String nome, List<Equipamento> equipamentos) {
        this.id = String.valueOf(Main.getTodosMecanismos().size() + 1);
        this.nome = nome;
        this.equipamentos = equipamentos;
        Main.addMecanismoNoSistema(this);
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Equipamento> getEquipamentos() {
        return equipamentos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MecanismoDaFerrovia that = (MecanismoDaFerrovia) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getNome(), that.getNome()) && Objects.equals(getEquipamentos(), that.getEquipamentos());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome(), getEquipamentos());
    }

    @Override
    public String toString() {
        return "MecanismoDaFerrovia{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", equipamentos=" + equipamentos +
                '}';
    }
}
