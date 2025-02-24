package marmota_mobilidade.app;

import marmota_mobilidade.models.*;
import marmota_mobilidade.repositories.FailureRepo;
import marmota_mobilidade.repositories.ReportRepo;
import marmota_mobilidade.repositories.UserRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private final UserRepo userRepo = new UserRepo();
    private static final FailureRepo failureRepo = new FailureRepo();
    private final ReportRepo reportRepo = new ReportRepo();
    private static final Logger LOGGER = LogManager.getLogger(Menu.class);

    public static void createReportOnRepo(ReportRepo repo) {
        var scan = new Scanner(System.in);

        try {
            System.out.println("Digite o id:");
            var id = scan.nextLine();

            System.out.println("""
                    1. Geral
                    2. Período
                    3. Tipo de falha
                    """);
            var reportType = REPORT_TYPE.fromNumber(scan.nextInt());
            scan.nextLine();

            var reportIncomplete = Report.builder().id(id).reportType(reportType).build();

            var fullReport = reportIncomplete.generateData(failureRepo);
            failureRepo.get().forEach(f -> f.setOnGeneralReport(true));

            repo.add(fullReport);

        } catch (IllegalArgumentException e) {
            System.out.println("Opção Inválida");
        }
    }

    public static void createUserOnRepo(UserRepo repo) {
        var scan = new Scanner(System.in);

        System.out.println("Digite o id:");
        var id = scan.nextLine();

        System.out.println("Digite o nome:");
        var name = scan.nextLine();

        System.out.println("""
                1. Operador
                2. Adm
                """);
        var opcao = scan.nextInt();

        switch (opcao) {
            case 1:
                repo.add(Operator.builder().id(id).name(name).build());
                break;
            case 2:
                repo.add(Admin.builder().id(id).name(name).build());
            default:
                System.out.println("Opção Inválida");
        }


    }

    public static void createFailureOnRepo(FailureRepo repo) {
        var scan = new Scanner(System.in);

        try {
            LOGGER.info("Iniciando criação de falha");
            var id = String.valueOf(repo.get().size() + 1);

            System.out.println("""
                    1. Mecânica
                    2. Elétrica
                    3. Software
                    4. Outro
                    """);
            var failType = FAILURE_TYPE.fromNumber(scan.nextInt());
            scan.nextLine();

            System.out.println("Descreva a falha:");
            var description = scan.nextLine();

            repo.add(Failure.builder()
                    .id(id)
                    .failureType(failType)
                    .failureDescription(description)
                    .build());

        } catch (IllegalArgumentException e) {
            System.out.println("Opção Inválida");
        }

    }

    public void start() {
        LOGGER.info("Iniciando sistema...");
        System.out.println("Bem vindo ao Marmota Mobilidade - Operador");
        while (true) {
            try {
                System.out.println("""
                        1. Criar falha
                        2. Listar falhas
                        3. Criar operador
                        4. Listar usuários
                        5. Criar relatório
                        6. Listar relatórios
                        0. Sair
                        """);
                var scan = new Scanner(System.in);
                var opcao = scan.nextInt();

                switch (opcao) {
                    case 1:
                        createFailureOnRepo(failureRepo);
                        break;
                    case 2:
                        System.out.println(failureRepo.get());
                        break;
                    case 3:
                        createUserOnRepo(userRepo);
                        break;
                    case 4:
                        System.out.println(userRepo.get());
                        break;
                    case 5:
                        createReportOnRepo(reportRepo);
                        break;
                    case 6:
                        System.out.println(reportRepo.get());
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        break;

                    default:
                        System.out.println("Opção Inválida");

                }

                if (opcao == 0) {
                    LOGGER.info("Finalizando sistema...");
                    break;
                }
            } catch (InputMismatchException e) {
                LOGGER.error("Erro na seleção", e);
            }
        }
    }
}
