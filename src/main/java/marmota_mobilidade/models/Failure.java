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
    private final LocalDateTime registrationDate = LocalDateTime.now();
    @NonNull
    private FAILURE_TYPE failureType;
    @NonNull
    private String failureDescription;
    private FAILURE_STATUS failureStatus = FAILURE_STATUS.PENDENTE;

}
