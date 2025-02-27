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
public class Admin extends User {
    private int accessLevel;

    @Override
    public String show_details() {
        return "Administrador #%s - %s | Turno: %s - Acesso: %s ".formatted(this.getId(), this.getName(), this.getUserShift(),this.getAccessLevel());
    }
}
