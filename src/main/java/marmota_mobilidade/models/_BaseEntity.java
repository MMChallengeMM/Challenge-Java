package marmota_mobilidade.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@ToString
public abstract class _BaseEntity {
    @NonNull
    private UUID id;
    @Builder.Default
    private boolean deleted = false;

    public String show_details(){
        return "";
    }
}
