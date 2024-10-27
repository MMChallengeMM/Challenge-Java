package com.challengemm.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HistoricoFalhas {
    private List<Falha> falhas = new ArrayList<>();

    public void addFalha(TIPO_FALHA tipoFalha,String descricaoFalha) {
        falhas.add(new Falha(tipoFalha,descricaoFalha));
    }

    public void addFalha(Falha falha) {
        falhas.add(falha);
    }

    public void removeFalha(Falha falha) {
        falhas.remove(falha);
    }

    public void exibirFalhas() {
        System.out.println("#ID (Status) | Data | Tipo | Descrição");
        falhas.forEach(f -> {
            System.out.printf("#%s (%s) - %s - %s: %s\n",
                    f.getIdFalha(),
                    f.getStatusFalha(),
                    f.getDataRegitro(),
                    f.getTipoFalha(),
                    f.getDescricaoFalha());
        });
    }

    public List<Falha> filtrarFalhasPorTipoFalha(TIPO_FALHA tipoFalha) {
        return falhas.stream()
                .filter(f -> f.getTipoFalha() == tipoFalha)
                .toList();
    }

    public List<Falha> filtrarFalhasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        return falhas.stream()
                .filter(f -> dataInicial.isBefore(f.getDataRegitro()) && dataFinal.isAfter(f.getDataRegitro()))
                .toList();
    }

    // Métodos Gerais

    public HistoricoFalhas() {
    }

    public HistoricoFalhas(List<Falha> falhas) {
        this.falhas = falhas;
    }

    public HistoricoFalhas(HistoricoFalhas historicoFalhas) {
        this.falhas = historicoFalhas.getFalhas();
    }

    public List<Falha> getFalhas() {
        return falhas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoricoFalhas that = (HistoricoFalhas) o;
        return Objects.equals(getFalhas(), that.getFalhas());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getFalhas());
    }

    @Override
    public String toString() {
        return "HistoricoFalhas{" +
                "falhas=" + falhas +
                '}';
    }
}
