package marmota_mobilidade.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Operator extends User {
    private String sector;

    @Override
    public String show_details() {
        return "Operador #%s - %s | Turno: %s - Setor: %s ".formatted(this.getId(), this.getName(), this.getUserShift(),this.getSector());
    }
}
