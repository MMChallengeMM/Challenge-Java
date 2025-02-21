package marmota_mobilidade.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum REPORT_TYPE {
    GERAL (1),
    PERIODO (2),
    TIPO_DE_FALHA (3);

    private final int num;

    public static REPORT_TYPE fromNumber(int num) {
        for (REPORT_TYPE tipo : REPORT_TYPE.values()) {
            if (tipo.getNum() == num) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Número inválido: %d.".formatted(num));
    }
}
