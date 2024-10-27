package com.challengemm.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Manutencao {
    private String idManutencao;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private STATUS_MANUTENCAO statusManutencao;
    private Falha falhaParaResolver;
    private String descricaoManutencao;

    public void exibirManutencao() {
        System.out.printf("""
                
                Manutenção #%s
                Status: %s
                Data: (%s) - (%s)
                ========================
                Falha %s========================
                Descrição:
                %s
                """, idManutencao, statusManutencao,
                dataInicio.format(DateTimeFormatter.ofPattern("dd/MM/yy")),
                dataFim.format(DateTimeFormatter.ofPattern("dd/MM/yy")), falhaParaResolver.exibirFalha(),descricaoManutencao);
    }


    public Manutencao() {
    }

    public Manutencao(String idManutencao, LocalDateTime dataInicio, LocalDateTime dataFim, Falha falhaParaResolver, String descricaoManutencao) {
        this.idManutencao = idManutencao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;

        if (this.dataFim.isBefore(LocalDateTime.now())) {
            this.statusManutencao = STATUS_MANUTENCAO.CONCLUIDA;
        } else if (this.dataInicio.isBefore(LocalDateTime.now())) {
            this.statusManutencao = STATUS_MANUTENCAO.EM_PROCESSO;
        } else {
            this.statusManutencao = STATUS_MANUTENCAO.AGUARDANDO;
        }

        this.falhaParaResolver = falhaParaResolver;
        this.descricaoManutencao = descricaoManutencao;
    }

    public String getIdManutencao() {
        return idManutencao;
    }

    public void setIdManutencao(String idManutencao) {
        this.idManutencao = idManutencao;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public STATUS_MANUTENCAO getStatusManutencao() {
        return statusManutencao;
    }

    public void setStatusManutencao(STATUS_MANUTENCAO statusManutencao) {
        this.statusManutencao = statusManutencao;
    }

    public Falha getFalhaParaResolver() {
        return falhaParaResolver;
    }

    public void setFalhaParaResolver(Falha falhaParaResolver) {
        this.falhaParaResolver = falhaParaResolver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manutencao that = (Manutencao) o;
        return Objects.equals(getIdManutencao(), that.getIdManutencao()) && Objects.equals(getDataInicio(), that.getDataInicio()) && Objects.equals(getDataFim(), that.getDataFim()) && Objects.equals(getStatusManutencao(), that.getStatusManutencao()) && Objects.equals(getFalhaParaResolver(), that.getFalhaParaResolver());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdManutencao(), getDataInicio(), getDataFim(), getStatusManutencao(), getFalhaParaResolver());
    }

    @Override
    public String toString() {
        return "Manutencao{" +
                "idManutencao='" + idManutencao + '\'' +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                ", statusManutencao='" + statusManutencao + '\'' +
                ", falhaParaResolver=" + falhaParaResolver +
                '}';
    }
}
