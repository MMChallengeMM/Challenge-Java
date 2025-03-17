package marmota_mobilidade.models;

import lombok.*;
import lombok.experimental.SuperBuilder;
import marmota_mobilidade.system.Screen;
import marmota_mobilidade.repositories.FailureRepo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
                break;
            case PERIODO:
                var dateRegex = "\\d{2}[^0-9]\\d{2}[^0-9]\\d{4}";
                var scan1 = new Scanner(System.in);

                LocalDateTime inicialDate = null;
                while (inicialDate == null) {
                    System.out.println("Digite a data inicial:");
                    while (!scan1.hasNext(dateRegex)) {
                        System.out.println("Formato inválido! Tente novamente. (dd-mm-aaaa)");
                        scan1.next();
                    }
                    inicialDate = LocalDateTime.parse("%sT00:00:00".formatted(scan1.next()
                            .replaceAll("[^0-9]", "-")
                            .replaceAll("(\\d{2})-(\\d{2})-(\\d{4})", "$3-$2-$1")));
                }

                LocalDateTime finalDate = null;
                while (finalDate == null) {
                    System.out.println("Digite a data final:");
                    while (!scan1.hasNext(dateRegex)) {
                        System.out.println("Formato inválido! Tente novamente. (dd-mm-aaaa)");
                        scan1.next();
                    }
                    finalDate = LocalDateTime.parse("%sT23:59:59".formatted(scan1.next()
                            .replaceAll("[^0-9]", "-")
                            .replaceAll("(\\d{2})-(\\d{2})-(\\d{4})", "$3-$2-$1")));
                }

                LocalDateTime finalInicialDate = inicialDate;
                LocalDateTime finalDate1 = finalDate;
                var filteredFailures1 = failures.stream()
                        .filter(f -> f.getRegistrationDate().isBefore(finalDate1) && f.getRegistrationDate().isAfter(finalInicialDate))
                        .toList();

                this.numberOfFailures = filteredFailures1.size();

                this.reportData = "De: %s-Até: %s".formatted(inicialDate.format(DateTimeFormatter.ofPattern("dd/MM/yy")), finalDate.format(DateTimeFormatter.ofPattern("dd/MM/yy")));
                break;
            case TIPO_DE_FALHA:
                var scan2 = new Scanner(System.in);

                while (true) {
                    try {
                        Screen.failureTypes();
                        var failureType = FAILURE_TYPE.fromNumber(scan2.nextInt());
                        scan2.nextLine();

                        var filteredFailures2 = failures.stream()
                                .filter(f -> f.getFailureType() == failureType)
                                .toList();

                        this.numberOfFailures = filteredFailures2.size();

                        this.reportData = String.valueOf(failureType);
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Valor inválida");
                        scan2.next();
                    } catch (IllegalArgumentException e) {
                        System.out.println("Opção inválida");
                    }
                }

        }
        return this;
    }

    @Override
    public String show_details() {

        return "Relatório #%s - %s | (%s) - (%s) | N° de falhas: %s".formatted(this.getId(), this.getReportType(), this.getGenerationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm")), this.getReportData(), this.getNumberOfFailures());
    }
}
