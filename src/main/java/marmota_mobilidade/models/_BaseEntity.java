package marmota_mobilidade.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@ToString
public abstract class _BaseEntity {
    @NonNull
    private String id;
    @Builder.Default
    private boolean deleted = false;

    public String show_details(){
        return "";
    }
}
