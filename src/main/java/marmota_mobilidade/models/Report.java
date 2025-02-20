package marmota_mobilidade.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Report extends _BaseEntity {
    private REPORT_TYPE reportType;
    private LocalDateTime generationDate;
    private int numberOfFailures;
    private String reportData;
    private Failure[] lastFailures = new Failure[5];

}
