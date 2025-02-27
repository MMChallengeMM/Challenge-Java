package marmota_mobilidade.models;

import lombok.*;
import lombok.experimental.SuperBuilder;
import marmota_mobilidade.repositories.FailureRepo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Report extends _BaseEntity {
    @NonNull
    private REPORT_TYPE reportType;
    private LocalDateTime generationDate;
    private int numberOfFailures;
    private String reportData;
    private String[] lastFailures = new String[5];

    public Report generateData(FailureRepo repo) {
        var failures = repo.get();

        this.generationDate = LocalDateTime.now();

        switch (this.getReportType()) {
            case GERAL:
                var generalFailures = failures.stream()
                        .filter(f -> !f.isOnGeneralReport())
                        .toList();

                this.numberOfFailures = generalFailures.size();

                this.reportData = generalFailures.stream()
                        .collect(Collectors.groupingBy(Failure::getFailureType, Collectors.counting()))
                        .entrySet()
                        .stream()
                        .max(Map.Entry.comparingByValue())
                        .map(Map.Entry::getKey)
                        .map(String::valueOf)
                        .orElse("Não há novas falhas para reportar.");

                this.lastFailures = generalFailures.stream()
                        .sorted(Comparator.comparing(Failure::getRegistrationDate).reversed())
                        .limit(5)
                        .map(Failure::show_details)
                        .toArray(String[]::new);
                break;
            case PERIODO:
                var scan1 = new Scanner(System.in);

                System.out.println("Digite a data inicial:");
                var inicialDate = LocalDateTime.parse("%sT00:00:00".formatted(scan1.nextLine()));

                System.out.println("Digite a data final:");
                var finalDate = LocalDateTime.parse("%sT23:59:59".formatted(scan1.nextLine()));

                var filteredFailures1 = failures.stream()
                        .filter(f -> f.getRegistrationDate().isBefore(finalDate) && f.getRegistrationDate().isAfter(inicialDate))
                        .toList();

                this.numberOfFailures = filteredFailures1.size();

                this.reportData = "De: %s-Até: %s".formatted(inicialDate.format(DateTimeFormatter.ofPattern("dd/MM/yy")), finalDate.format(DateTimeFormatter.ofPattern("dd/MM/yy")));

                this.lastFailures = filteredFailures1.stream()
                        .sorted(Comparator.comparing(Failure::getRegistrationDate).reversed())
                        .limit(5)
                        .map(Failure::show_details)
                        .toArray(String[]::new);
                break;
            case TIPO_DE_FALHA:
                var scan2 = new Scanner(System.in);

                System.out.println("""
                        1. Mecânica
                        2. Elétrica
                        3. Software
                        4. Outro
                        """);
                var failureType = FAILURE_TYPE.fromNumber(scan2.nextInt());
                scan2.nextLine();

                var filteredFailures2 = failures.stream()
                        .filter(f -> f.getFailureType() == failureType)
                        .toList();

                this.numberOfFailures = filteredFailures2.size();

                this.reportData = String.valueOf(failureType);

                this.lastFailures = filteredFailures2.stream()
                        .sorted(Comparator.comparing(Failure::getRegistrationDate).reversed())
                        .limit(5)
                        .map(Failure::show_details)
                        .toArray(String[]::new);
                break;

        }
        return this;
    }

    @Override
    public String show_details() {
        return "Relatório #%s - %s | (%s) - (%s) | N° de falhas: %s - %s".formatted(this.getId(), this.getReportType(), this.getGenerationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm")),this.getReportData(), this.getNumberOfFailures(), this.getLastFailures());
    }
}
