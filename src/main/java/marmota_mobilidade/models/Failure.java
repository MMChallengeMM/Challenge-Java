package marmota_mobilidade.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Failure extends _BaseEntity {
    @Builder.Default
    private final LocalDateTime registrationDate = LocalDateTime.now();
    @NonNull
    private FAILURE_TYPE failureType;
    @NonNull
    private String failureDescription;
    @Builder.Default
    private FAILURE_STATUS failureStatus = FAILURE_STATUS.PENDENTE;
    @Builder.Default
    private boolean onGeneralReport = false;

    @Override
    public String show_details() {
        return "Falha #%s - %s | (%s) | Status: %s - %s".formatted(this.getId(), this.getFailureType(), this.getRegistrationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm")), this.getFailureStatus(), this.getFailureDescription());
    }

}
