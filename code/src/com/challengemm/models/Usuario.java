package com.challengemm.models;

import com.challengemm.main.Main;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Scanner;

public abstract class Usuario {
    private final String idUsuario;
    private String nome;
    private TURNO_USUARIO turnoUsuario;

    public void gerarNovoRelatorio(TIPO_RELATORIO tipoRelatorio, HistoricoFalhas historicoFalhas) {
        var scanner = new Scanner(System.in);
        switch (tipoRelatorio) {
            case GERAL:
                new Relatorio(historicoFalhas).exibirRelatorio();
                break;
            case PERIODO:
                var formatadorData = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");

                System.out.println("Digite a data inicial (dd/MM/yy):");
                var dataInicial = LocalDateTime.parse(scanner.next() + " 00:00", formatadorData);

                System.out.println("Digite a data final (dd/MM/yy):");
                var dataFinal = LocalDateTime.parse(scanner.next() + " 23:59", formatadorData);

                new Relatorio(historicoFalhas, dataInicial, dataFinal).exibirRelatorio();
                break;
            case TIPO_DE_FALHA:
                System.out.println("Digite o tipo de falha:");
                var tipoFalha = TIPO_FALHA.valueOf(Normalizer.normalize(scanner.next().toUpperCase(), Normalizer.Form.NFD)
                        .replaceAll("\\p{M}", ""));

                new Relatorio(historicoFalhas, tipoFalha).exibirRelatorio();
                break;
        }
    }

    //Métodos Gerais

    public Usuario(String nome, TURNO_USUARIO turnoUsuario) {
        this.idUsuario = String.valueOf(Main.getTodosUsuarios().size() + 1);
        this.nome = nome;
        this.turnoUsuario = turnoUsuario;
        Main.addUsuarioNoSistema(this);
    }

    public String getIdUsuario() {
        return idUsuario;
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
        return Objects.equals(getIdUsuario(), usuario.getIdUsuario()) && Objects.equals(getNome(), usuario.getNome()) && getTurnoUsuario() == usuario.getTurnoUsuario();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdUsuario(), getNome(), getTurnoUsuario());
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
