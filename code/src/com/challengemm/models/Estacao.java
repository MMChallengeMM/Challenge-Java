package com.challengemm.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Estacao extends MecanismoDaFerrovia {

    private String enderecoEstacao;
    private List<Linha> linhas = new ArrayList<>();

    public void addLinha(Linha linha) {
        linhas.add(linha);
        if (linha.getEstacoes().contains(this)) {
            return;
        }
        linha.addEstacao(this);
    }

    public void removeLinha(Linha linha) {
        linhas.remove(linha);
        if (!linha.getEstacoes().contains(this)) {
            return;
        }
        linha.removeEstacao(this);
    }

    //Métodos gerais

    public Estacao(String nome, String enderecoEstacao) {
        super(nome);
        this.enderecoEstacao = enderecoEstacao;
    }

    public Estacao(String nome, List<Equipamento> equipamentos, String enderecoEstacao, List<Linha> linhas) {
        super(nome, equipamentos);
        this.enderecoEstacao = enderecoEstacao;
        this.linhas = linhas;
    }

    public String getEnderecoEstacao() {
        return enderecoEstacao;
    }

    public void setEnderecoEstacao(String enderecoEstacao) {
        this.enderecoEstacao = enderecoEstacao;
    }

    public List<Linha> getLinhas() {
        return linhas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Estacao estacao = (Estacao) o;
        return Objects.equals(getEnderecoEstacao(), estacao.getEnderecoEstacao()) && Objects.equals(getLinhas(), estacao.getLinhas());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getEnderecoEstacao(), getLinhas());
    }

    @Override
    public String toString() {
        return "Estacao{" +
                "enderecoEstacao='" + enderecoEstacao + '\'' +
                ", linhas=" + linhas.stream()
                .map(MecanismoDaFerrovia::getNome)
                .toList() +
                "} " + super.toString();
    }
}
