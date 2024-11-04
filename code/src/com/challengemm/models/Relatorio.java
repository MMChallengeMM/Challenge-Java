package com.challengemm.models;

import com.challengemm.main.Main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Relatorio {
    private String idRelatorio;
    private TIPO_RELATORIO tipoRelatorio;
    private LocalDateTime dataGeracao;
    private String dadosRelatorio;

    public void exibirRelatorio() {
        System.out.printf("""
                
                Relatório #%s
                Tipo de Relatório: %s
                Data: %s
                ========================
                %s
                """, idRelatorio, tipoRelatorio, dataGeracao.format(DateTimeFormatter.ofPattern("dd/MM/yy - HH:mm")), dadosRelatorio);
    }

    private String gerarDadosGeral(HistoricoFalhas historicoFalhas) {
        var falhas = historicoFalhas.getFalhas();

        if (falhas.isEmpty()) {
            return "Não há falhas";
        }

        return gerarDados(falhas);
    }

    private String gerarDadosPorTipoFalha(HistoricoFalhas historicoFalhas, TIPO_FALHA tipoFalha) {

        var falhas = historicoFalhas.filtrarFalhasPorTipoFalha(tipoFalha);

        if (falhas.isEmpty()) {
            return "Não há falhas desse tipo";
        }

        return gerarDados(falhas).replace(" mais frequente", "");
    }

    private String gerarDadosPorPeriodo(HistoricoFalhas historicoFalhas, LocalDateTime dataInicial, LocalDateTime dataFinal) {
        var falhas = historicoFalhas.filtrarFalhasPorPeriodo(dataInicial, dataFinal);

        if (falhas.isEmpty()) {
            return "Não há falhas nesse periodo";
        }

        return gerarDados(falhas);
    }

    private String gerarDados(List<Falha> falhas) {
        var numeroTotalFalhas = falhas.size();
        var falhaMaisFrequente = falhas.stream()
                .collect(Collectors.groupingBy(Falha::getTipoFalha, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);

        List<Falha> ultimasFalhas;

        if (falhas.size() < 5) {
            ultimasFalhas = falhas.stream()
                    .sorted(Comparator.comparing(Falha::getDataRegitro))
                    .toList().reversed()
                    .subList(0, falhas.size());
        } else {
            ultimasFalhas = falhas.stream()
                    .sorted(Comparator.comparing(Falha::getDataRegitro))
                    .toList().reversed()
                    .subList(0, 5);
        }

        StringBuilder ultimasFalhasFormatado = new StringBuilder();

        for (var falha : ultimasFalhas) {
            ultimasFalhasFormatado.append(falha.exibirFalha()).append("\n");
        }

        return """
                Tipo de falha mais frequente: %s
                Número total de falhas: %d
                Ultimas falhas:
                %s
                """.formatted(falhaMaisFrequente == null ? "Não há falhas" : falhaMaisFrequente.getKey(), numeroTotalFalhas, ultimasFalhasFormatado.toString());
    }


    // Métodos Gerais

    public Relatorio(HistoricoFalhas historicoFalhas) {
        this.idRelatorio = String.valueOf(Main.getTodosRelatorios().size() + 1);
        this.dataGeracao = LocalDateTime.now();
        this.tipoRelatorio = TIPO_RELATORIO.GERAL;
        this.dadosRelatorio = gerarDadosGeral(historicoFalhas);
        Main.addRelatorioNoSistema(this);
    }

    public Relatorio(HistoricoFalhas historicoFalhas, TIPO_FALHA tipoFalha) {
        this.idRelatorio = String.valueOf(Main.getTodosRelatorios().size() + 1);
        this.dataGeracao = LocalDateTime.now();
        this.tipoRelatorio = TIPO_RELATORIO.TIPO_DE_FALHA;
        this.dadosRelatorio = gerarDadosPorTipoFalha(historicoFalhas, tipoFalha);
        Main.addRelatorioNoSistema(this);
    }

    public Relatorio(HistoricoFalhas historicoFalhas, LocalDateTime dataInicial, LocalDateTime dataFinal) {
        this.idRelatorio = String.valueOf(Main.getTodosRelatorios().size() + 1);
        this.dataGeracao = LocalDateTime.now();
        this.tipoRelatorio = TIPO_RELATORIO.PERIODO;
        this.dadosRelatorio = gerarDadosPorPeriodo(historicoFalhas, dataInicial, dataFinal);
        Main.addRelatorioNoSistema(this);
    }

    public String getIdRelatorio() {
        return idRelatorio;
    }

    public LocalDateTime getDataGeracao() {
        return dataGeracao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Relatorio relatorio = (Relatorio) o;
        return Objects.equals(getIdRelatorio(), relatorio.getIdRelatorio()) && tipoRelatorio == relatorio.tipoRelatorio && Objects.equals(getDataGeracao(), relatorio.getDataGeracao()) && Objects.equals(dadosRelatorio, relatorio.dadosRelatorio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdRelatorio(), tipoRelatorio, getDataGeracao(), dadosRelatorio);
    }

    @Override
    public String toString() {
        return "Relatorio{" +
                "idRelatorio='" + idRelatorio + '\'' +
                ", tipoRelatorio=" + tipoRelatorio +
                ", dataGeracao=" + dataGeracao +
                ", dadosRelatorio='" + dadosRelatorio + '\'' +
                '}';
    }
}
