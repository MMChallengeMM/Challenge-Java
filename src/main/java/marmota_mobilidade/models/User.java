package marmota_mobilidade.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class User extends _BaseEntity {
    @NonNull
    private String name;
    private USER_SHIFT userShift;
}
