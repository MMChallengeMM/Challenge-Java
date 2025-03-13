package marmota_mobilidade.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum USER_SHIFT {
    MANHA(1),
    TARDE(2),
    NOITE(3);

    private final int num;

    public static USER_SHIFT fromNumber(int num) {
        for (USER_SHIFT tipo : USER_SHIFT.values()) {
            if (tipo.getNum() == num) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Número inválido: %d.".formatted(num));
    }
}
