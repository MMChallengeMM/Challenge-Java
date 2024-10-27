package com.challengemm.models;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Scanner;

public abstract class Usuario {
    private String idUsuario;
    private String nome;
    private TURNO_USUARIO turnoUsuario;

    public void gerarNovoRelatorio(TIPO_RELATORIO tipoRelatorio, HistoricoFalhas historicoFalhas) {
        var scanner = new Scanner(System.in);
        switch (tipoRelatorio) {
            case GERAL:
                new Relatorio("ID", historicoFalhas).exibirRelatorio();
                break;
            case PERIODO:
                var formatadorData = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");

                System.out.println("Digite a data inicial (dd/MM/yy):");
                var dataInicial = LocalDateTime.parse(scanner.next() + " 00:00", formatadorData);

                System.out.println("Digite a data final (dd/MM/yy):");
                var dataFinal = LocalDateTime.parse(scanner.next() + " 23:59", formatadorData);

                new Relatorio("ID", historicoFalhas, dataInicial, dataFinal).exibirRelatorio();
                break;
            case TIPO_DE_FALHA:
                System.out.println("Digite o tipo de falha:");
                var tipoFalha = TIPO_FALHA.valueOf(Normalizer.normalize(scanner.next().toUpperCase(), Normalizer.Form.NFD)
                        .replaceAll("\\p{M}", ""));

                new Relatorio("ID", historicoFalhas, tipoFalha).exibirRelatorio();
                break;
        }
    }

    //Métodos Gerais

    public Usuario() {
    }

    public Usuario(String idUsuario, String nome, TURNO_USUARIO turnoUsuario) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.turnoUsuario = turnoUsuario;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TURNO_USUARIO getTurnoUsuario() {
        return turnoUsuario;
    }

    public void setTurnoUsuario(TURNO_USUARIO turnoUsuario) {
        this.turnoUsuario = turnoUsuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(idUsuario, usuario.idUsuario) && Objects.equals(nome, usuario.nome) && turnoUsuario == usuario.turnoUsuario;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, nome, turnoUsuario);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario='" + idUsuario + '\'' +
                ", nome='" + nome + '\'' +
                ", turnoUsuario=" + turnoUsuario +
                '}';
    }
}
