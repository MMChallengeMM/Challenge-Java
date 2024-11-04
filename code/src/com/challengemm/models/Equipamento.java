package com.challengemm.models;

import com.challengemm.main.Main;

import java.util.Objects;

public class Equipamento {

    private final String idEquipamento;
    private String nomeEquipamento;
    private MecanismoDaFerrovia localizacaoEquipamento;
    private STATUS_EQUIPAMENTO statusEquipamento;
    private HistoricoFalhas historicoFalhas;

    //Métodos Gerais

    public Equipamento(String nomeEquipamento, MecanismoDaFerrovia localizacaoEquipamento, STATUS_EQUIPAMENTO statusEquipamento) {
        this.idEquipamento = String.valueOf(Main.getTodosEquipamentos().size() + 1);
        this.nomeEquipamento = nomeEquipamento;
        this.localizacaoEquipamento = localizacaoEquipamento;
        this.localizacaoEquipamento.addEquipamento(this);
        this.statusEquipamento = statusEquipamento;
        this.historicoFalhas = new HistoricoFalhas();
        Main.addEquipamentoNoSistema(this);
    }

    public Equipamento(String nomeEquipamento, MecanismoDaFerrovia localizacaoEquipamento, STATUS_EQUIPAMENTO statusEquipamento, HistoricoFalhas historicoFalhas) {
        this.idEquipamento = String.valueOf(Main.getTodosEquipamentos().size() + 1);
        this.nomeEquipamento = nomeEquipamento;
        this.localizacaoEquipamento = localizacaoEquipamento;
        this.localizacaoEquipamento.addEquipamento(this);
        this.statusEquipamento = statusEquipamento;
        this.historicoFalhas = historicoFalhas;
        Main.addEquipamentoNoSistema(this);
    }

    public String getIdEquipamento() {
        return idEquipamento;
    }

    public String getNomeEquipamento() {
        return nomeEquipamento;
    }

    public void setNomeEquipamento(String nomeEquipamento) {
        this.nomeEquipamento = nomeEquipamento;
    }

    public MecanismoDaFerrovia getLocalizacaoEquipamento() {
        return localizacaoEquipamento;
    }

    public void setLocalizacaoEquipamento(MecanismoDaFerrovia localizacaoEquipamento) {
        this.localizacaoEquipamento = localizacaoEquipamento;
    }

    public STATUS_EQUIPAMENTO getStatusEquipamento() {
        return statusEquipamento;
    }

    public void setStatusEquipamento(STATUS_EQUIPAMENTO statusEquipamento) {
        this.statusEquipamento = statusEquipamento;
    }

    public HistoricoFalhas getHistoricoFalhas() {
        return historicoFalhas;
    }

    public void setHistoricoFalhas(HistoricoFalhas historicoFalhas) {
        this.historicoFalhas = historicoFalhas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipamento that = (Equipamento) o;
        return Objects.equals(getIdEquipamento(), that.getIdEquipamento()) && Objects.equals(getNomeEquipamento(), that.getNomeEquipamento()) && Objects.equals(getLocalizacaoEquipamento(), that.getLocalizacaoEquipamento()) && getStatusEquipamento() == that.getStatusEquipamento() && Objects.equals(getHistoricoFalhas(), that.getHistoricoFalhas());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdEquipamento(), getNomeEquipamento(), getLocalizacaoEquipamento(), getStatusEquipamento(), getHistoricoFalhas());
    }

    @Override
    public String toString() {
        return "Equipamento{" +
                "idEquipamento='" + idEquipamento + '\'' +
                ", nomeEquipamento='" + nomeEquipamento + '\'' +
                ", localizacaoEquipamento=" + localizacaoEquipamento.getNome() +
                ", statusEquipamento=" + statusEquipamento +
                ", falhas=" + historicoFalhas +
                '}';
    }
}

