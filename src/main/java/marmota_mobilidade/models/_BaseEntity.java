package marmota_mobilidade.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@ToString
public abstract class _BaseEntity {
    @NonNull
    private String id;
    private boolean deleted = false;
}
