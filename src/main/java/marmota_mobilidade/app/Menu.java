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
    private final FailureRepo failureRepo = new FailureRepo();
    private final ReportRepo reportRepo = new ReportRepo();
    private static final Logger LOGGER = LogManager.getLogger(Menu.class);

    private void createReportOnRepo(ReportRepo repo) {
        var scan = new Scanner(System.in);

        try {
            var id = String.valueOf(repo.get().size());

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

        } catch (InputMismatchException e) {
            LOGGER.error("Erro na seleção", e);
            System.out.println("Opção Inválida");
        }
    }

    private void createUserOnRepo(UserRepo repo) {
        var scan = new Scanner(System.in);

        try {
            var id = String.valueOf(repo.get().size());

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
                    break;
                default:
                    System.out.println("Opção Inválida");
            }
        } catch (InputMismatchException e) {
            LOGGER.error("Erro na seleção", e);
            System.out.println("Opção Inválida");
        }
    }

    private void createFailureOnRepo(FailureRepo repo) {
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

        } catch (InputMismatchException e) {
            LOGGER.error("Erro na seleção", e);
            System.out.println("Opção Inválida");
        }

    }

    private int start() {
        var scan = new Scanner(System.in);
        var opcao = -1;

        while (true) {
            try {
                System.out.println("""
                        1. Criar falha
                        2. Listar falhas
                        3. Criar relatorios
                        4. Lista relatorios
                        5. Voltar para o login
                        0. Sair
                        """);
                opcao = scan.nextInt();

                switch (opcao) {
                    case 0:
                        return 0;
                    case 1:
                        createFailureOnRepo(failureRepo);
                        break;
                    case 2:
                        failureRepo.get().forEach(f -> System.out.println(f.show_details()));
                        break;
                    case 3:
                        createReportOnRepo(reportRepo);
                        break;
                    case 4:
                        reportRepo.get().forEach(r -> System.out.println(r.show_details()));
                        break;
                    case 5:
                        return -1;
                    default:
                        System.out.println("Opção inválida");
                }
            } catch (InputMismatchException e) {
                System.out.println("Valor Inválido");
            }
        }
    }

    private int startAdm() {
        var scan = new Scanner(System.in);
        var opcao = -1;

        while (true) {
            try {
                System.out.println("""
                        1. Criar falha
                        2. Listar falhas
                        3. Criar operador
                        4. Listar operadores
                        5. Criar relatorios
                        6. Lista relatorios
                        7. Voltar para o login
                        0. Sair
                        """);
                opcao = scan.nextInt();

                switch (opcao) {
                    case 0:
                        return 0;
                    case 1:
                        createFailureOnRepo(failureRepo);
                        break;
                    case 2:
                        failureRepo.get().forEach(f -> System.out.println(f.show_details()));
                        break;
                    case 3:
                        createUserOnRepo(userRepo);
                        break;
                    case 4:
                        userRepo.get().forEach(u -> System.out.println(u.show_details()));
                        break;
                    case 5:
                        createReportOnRepo(reportRepo);
                        break;
                    case 6:
                        reportRepo.get().forEach(r -> System.out.println(r.show_details()));
                        break;
                    case 7:
                        return -1;
                    default:
                        System.out.println("Opção inválida");
                }
            } catch (InputMismatchException e) {
                System.out.println("Valor Inválido");
            }
        }
    }

    public void loginStart() {
        var scan = new Scanner(System.in);
        var opcao = -1;

        System.out.println("Bem vindo ao sistema");
        try {
            while (true) {
                if (opcao == 0) {
                    System.out.println("Saindo...");
                    break;
                }
                System.out.println("""
                        Escolha um:
                        1. Operador
                        2. Administrador
                        0. Sair
                        """);
                opcao = scan.nextInt();

                switch (opcao) {
                    case 1:
                        opcao = start();
                        break;
                    case 2:
                        opcao = startAdm();
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        return;
                    default:
                        System.out.println("Opção inválida");
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Valor Inválido");
        }
    }
}
