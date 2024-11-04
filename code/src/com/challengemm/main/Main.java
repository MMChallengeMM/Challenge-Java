package com.challengemm.main;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.challengemm.models.*;

public class Main {

    private static final HistoricoFalhas todasFalhas = new HistoricoFalhas();
    private static final List<MecanismoDaFerrovia> todosMecanismos = new ArrayList<>();
    private static final List<Usuario> todosUsuarios = new ArrayList<>();
    private static final List<Relatorio> todosRelatorios = new ArrayList<>();
    private static final List<Equipamento> todosEquipamentos = new ArrayList<>();
    private static final List<Manutencao> todasManutencoes = new ArrayList<>();
    private static final List<Trem> todosTrens = new ArrayList<>();

    public static void main(String[] args) {

        var estacao1 = new Estacao("Estação da Sé","Rua da Sé");
        var estacao2 = new Estacao("Estação Marechal Deodoro","Praça marechal");

        var linha1 = new Linha("Azul");
        var linha2 = new Linha("Vermelha");
        estacao1.addLinha(linha1);
        estacao1.addLinha(linha2);
        estacao2.addLinha(linha2);

        var equip1 = new Equipamento("Catraca",estacao1, STATUS_EQUIPAMENTO.ATIVO);

        var operador1 = new Operador("Carlos",TURNO_USUARIO.MANHA,"Segurança Geral");
        var adm1 = new Administrador("Miguel",TURNO_USUARIO.NOITE,1);

        adm1.criarNovaFalha(TIPO_FALHA.ELETRICA, equip1,"a");

        adm1.criarNovaFalha(TIPO_FALHA.SOFTWARE, equip1,"b");
        adm1.criarNovaFalha(TIPO_FALHA.MECANICA, equip1,"c");
        adm1.criarNovaFalha(TIPO_FALHA.OUTRO,equip1 ,"d");
        adm1.criarNovaFalha(TIPO_FALHA.SOFTWARE,equip1 ,"e");
        adm1.criarNovaFalha(TIPO_FALHA.ELETRICA,equip1 ,"f");
        adm1.criarNovaFalha(TIPO_FALHA.SOFTWARE,equip1 ,"g");

        new Manutencao(LocalDateTime.now().minusDays(4), LocalDateTime.now().minusDays(2),equip1.getHistoricoFalhas().getFalhas().getLast(),"Era um parafuso que faltava")
                .exibirManutencao();

        operador1.gerarNovoRelatorio(TIPO_RELATORIO.GERAL,equip1.getHistoricoFalhas());

        System.out.println();
        System.out.println(todosUsuarios);
    }

    public static List<Falha> getTodasFalhas() {
        return new ArrayList<>(todasFalhas.getFalhas());
    }

    public static void addFalhaNoSistema(Falha falha) {
        todasFalhas.addFalha(falha);
    }

    public static void removeFalhaNoSistema(Falha falha) {
        todasFalhas.removeFalha(falha);
    }

    public static List<MecanismoDaFerrovia> getTodosMecanismos() {
        return new ArrayList<>(todosMecanismos);
    }

    public static void addMecanismoNoSistema(MecanismoDaFerrovia mecanismoDaFerrovia) {
        todosMecanismos.add(mecanismoDaFerrovia);
    }

    public static void removeMecanismoNoSistema(MecanismoDaFerrovia mecanismoDaFerrovia) {
        todosMecanismos.remove(mecanismoDaFerrovia);
    }

    public static List<Usuario> getTodosUsuarios() {
        return new ArrayList<>(todosUsuarios);
    }

    public static void addUsuarioNoSistema(Usuario usuario) {
        todosUsuarios.add(usuario);
    }

    public static void removeUsuarioNoSistema(Usuario usuario) {
        todosUsuarios.remove(usuario);
    }

    public static List<Relatorio> getTodosRelatorios() {
        return new ArrayList<>(todosRelatorios);
    }

    public static void addRelatorioNoSistema(Relatorio relatorio) {
        todosRelatorios.add(relatorio);
    }

    public static void removeRelatorioNoSistema(Relatorio relatorio) {
        todosRelatorios.remove(relatorio);
    }

    public static List<Equipamento> getTodosEquipamentos() {
        return new ArrayList<>(todosEquipamentos);
    }

    public static void addEquipamentoNoSistema(Equipamento equipamento) {
        todosEquipamentos.add(equipamento);
    }

    public static void removeEquipamentoNoSistema(Equipamento equipamento) {
        todosEquipamentos.remove(equipamento);
    }

    public static List<Manutencao> getTodasManutencoes() {
        return new ArrayList<>(todasManutencoes);
    }

    public static void addManutencaoNoSistema(Manutencao manutencao) {
        todasManutencoes.add(manutencao);
    }

    public static void removeManutencaoNoSistema(Manutencao manutencao) {
        todasManutencoes.remove(manutencao);
    }

    public static List<Trem> getTodosTrens() {
        return new ArrayList<>(todosTrens);
    }

    public static void addTremNoSistema(Trem trem) {
        todosTrens.add(trem);
    }

    public static void removeTremNoSistema(Trem trem) {
        todosTrens.remove(trem);
    }

}
