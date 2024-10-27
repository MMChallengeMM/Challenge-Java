package com.challengemm.main;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.challengemm.models.*;

public class Main {

    private static final HistoricoFalhas todasFalhas = new HistoricoFalhas();

    public static void main(String[] args) {

        var estacao1 = new Estacao("1","Estação da Sé","Rua da Sé");
        var estacao2 = new Estacao("2","Estação Marechal Deodoro","Praça marechal");

        var linha1 = new Linha("1","Azul");
        var linha2 = new Linha("2","Vermelha");
        estacao1.addLinha(linha1);
        estacao1.addLinha(linha2);
        estacao2.addLinha(linha2);

        var equip1 = new Equipamento("1","Catraca",estacao1, STATUS_EQUIPAMENTO.ATIVO, new HistoricoFalhas());

        var operador1 = new Operador("1","Carlos",TURNO_USUARIO.MANHA,"Administração Geral");

        equip1.getHistoricoFalhas().addFalha(TIPO_FALHA.ELETRICA,"a");
        equip1.getHistoricoFalhas().addFalha(TIPO_FALHA.SOFTWARE,"b");
        equip1.getHistoricoFalhas().addFalha(TIPO_FALHA.MECANICA,"c");
        equip1.getHistoricoFalhas().addFalha(TIPO_FALHA.OUTRO,"d");
        equip1.getHistoricoFalhas().addFalha(TIPO_FALHA.SOFTWARE,"e");
        equip1.getHistoricoFalhas().addFalha(TIPO_FALHA.ELETRICA,"f");
        equip1.getHistoricoFalhas().addFalha(TIPO_FALHA.SOFTWARE,"g");

        new Manutencao("ID", LocalDateTime.now().minusDays(4), LocalDateTime.now().minusDays(2),equip1.getHistoricoFalhas().getFalhas().getLast(),"Era um parafuso que faltava").exibirManutencao();

        operador1.gerarNovoRelatorio(TIPO_RELATORIO.GERAL,equip1.getHistoricoFalhas());
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

}
