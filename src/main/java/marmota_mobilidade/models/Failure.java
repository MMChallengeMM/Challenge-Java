package marmota_mobilidade.models;

import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Failure extends _BaseEntity {
    @NonNull
    private FAILURE_TYPE failureType;
    @NonNull
    private String failureDescription;
    private final LocalDateTime registrationDate = LocalDateTime.now();
    private FAILURE_STATUS failureStatus = FAILURE_STATUS.PENDENTE;

}
