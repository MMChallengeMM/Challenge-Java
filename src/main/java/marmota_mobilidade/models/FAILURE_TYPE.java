package marmota_mobilidade.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FAILURE_TYPE {
    MECANICA(1),
    ELETRICA(2),
    SOFTWARE(3),
    OUTRO(4);

    private final int num;

    public static FAILURE_TYPE fromNumber(int num) {
        for (FAILURE_TYPE tipo : FAILURE_TYPE.values()) {
            if (tipo.getNum() == num) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Número inválido: %d.".formatted(num));
    }
}
