package marmota_mobilidade.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FAILURE_STATUS {
    EM_ANALISE(1),
    PENDENTE(2),
    RESOLVIDA(3),
    CANCELADA(4);

    private final int num;

    public static FAILURE_STATUS fromNumber(int num) {
        for (FAILURE_STATUS tipo : FAILURE_STATUS.values()) {
            if (tipo.getNum() == num) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Número inválido: %d.".formatted(num));
    }
}
