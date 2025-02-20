package marmota_mobilidade.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Failure extends _BaseEntity {
    @NonNull
    private FAILURE_TYPE failureType;
    @NonNull
    private String failureDescription;
    private final LocalDateTime registrationDate = LocalDateTime.now();
    private FAILURE_STATUS failureStatus = FAILURE_STATUS.PENDENTE;

}
