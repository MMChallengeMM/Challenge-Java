package marmota_mobilidade.system;

import marmota_mobilidade.models.*;
import marmota_mobilidade.repositories.FailureRepo;
import marmota_mobilidade.repositories.ReportRepo;
import marmota_mobilidade.repositories.UserRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;

public class Menu {
    private final UserRepo userRepo = new UserRepo();
    private final FailureRepo failureRepo = new FailureRepo();
    private final ReportRepo reportRepo = new ReportRepo();
    private static final Logger LOGGER = LogManager.getLogger(Menu.class);

    private void createReportOnRepo(ReportRepo repo) {
        var scan = new Scanner(System.in);

        try {
            LOGGER.info("Iniciando criação de relatorio.");

            var id = UUID.randomUUID();
            LOGGER.debug("Id aleatório de relatório criado com sucesso.");

            Screen.reportTypes();
            var reportType = REPORT_TYPE.fromNumber(scan.nextInt());
            scan.nextLine();
            LOGGER.debug("Tipo de relatório criado com sucesso: {}", reportType);

            var reportIncomplete = Report.builder().id(id).reportType(reportType).build();
            LOGGER.debug("Relaório iniciado.");

            var fullReport = reportIncomplete.generateData(failureRepo);
            failureRepo.get().forEach(f -> f.setOnGeneralReport(true));

            repo.add(fullReport);
            LOGGER.info("Relatório criado com sucesso.");

        } catch (InputMismatchException e) {
            LOGGER.error("Erro na seleção de tipo de relatório: {}", e.getMessage(), e);
            System.out.println("Valor Inválido");
        } catch (IllegalArgumentException e) {
            LOGGER.error("Número fora das opções: {}", e.getMessage(), e);
            System.out.println("Opção inválida");
        }
    }

    private void createUserOnRepo(UserRepo repo) {
        var scan = new Scanner(System.in);

        try {
            LOGGER.info("Iniciando criação de usuário.");

            var id = UUID.randomUUID();
            LOGGER.debug("Id aleatório de usuário criado com sucesso.");

            System.out.println("\nDigite o nome do usuário:");
            var name = scan.nextLine().trim();
            LOGGER.debug("Nome criado com sucesso;");

            Screen.reportTypes();
            var userShift = USER_SHIFT.fromNumber(scan.nextInt());
            scan.nextLine();
            LOGGER.debug("Turno do usário criado com sucesso");

            Screen.userTypes();
            var opcao = scan.nextInt();

            switch (opcao) {
                case 1:
                    LOGGER.info("Iniciando criação de operador.");
                    repo.add(Operator.builder()
                            .id(id)
                            .name(name)
                            .userShift(userShift)
                            .build());
                    LOGGER.debug("Operador adicionado");
                    break;
                case 2:
                    LOGGER.info("Iniciando criação de adminsrador.");
                    repo.add(Admin.builder()
                            .id(id)
                            .name(name)
                            .userShift(userShift)
                            .build());
                    LOGGER.debug("Administrador adicionado");
                    break;
                default:
                    System.out.println("Opção Inválida");
            }
        } catch (InputMismatchException e) {
            LOGGER.error("Erro na seleção de tipo de relatório: {}", e.getMessage(), e);
            System.out.println("Valor Inválido");
        } catch (IllegalArgumentException e) {
            LOGGER.error("Número fora das opções: {}", e.getMessage(), e);
            System.out.println("Opção inválida");
        }
    }

    private void createFailureOnRepo(FailureRepo repo) {
        var scan = new Scanner(System.in);

        try {
            LOGGER.info("Iniciando criação de falha");

            var id = UUID.randomUUID();
            LOGGER.debug("Id aleatório de falha criado com sucesso.");

            Screen.failureTypes();
            var failType = FAILURE_TYPE.fromNumber(scan.nextInt());
            scan.nextLine();
            LOGGER.debug("Tipo de falha criada com sucesso: {}", failType);

            System.out.println("Descreva a falha:");
            var description = scan.nextLine();
            LOGGER.debug("Descrição criada com sucessso");

            repo.add(Failure.builder()
                    .id(id)
                    .failureType(failType)
                    .failureDescription(description)
                    .build());
            LOGGER.info("Falha adiciona com sucesso");
            System.out.println("Falha adicionada ao sistema");

        } catch (InputMismatchException e) {
            LOGGER.error("Erro na seleção de tipo de falha: {}", e.getMessage(), e);
            System.out.println("Valor Inválido");
        } catch (IllegalArgumentException e) {
            LOGGER.error("Número fora das opções: {}", e.getMessage(), e);
            System.out.println("Opção inválida");
        }
    }

    private int start() {
        var scan = new Scanner(System.in);
        var opcao = -1;
        LOGGER.info("Iniciando sistema como operador.");

        while (true) {
            try {
                Screen.system();
                opcao = scan.nextInt();

                switch (opcao) {
                    case 0:
                        return 0;
                    case 1:
                        createFailureOnRepo(failureRepo);
                        break;
                    case 2:
                        if (failureRepo.get().isEmpty()) {
                            System.out.println("\nNão há falhas");
                            break;
                        }
                        System.out.println("\n#ID | TIPO | DATA | STATUS | DESCRIÇÃO");
                        failureRepo.get().forEach(f -> System.out.println(f.show_details()));
                        break;
                    case 3:
                        createReportOnRepo(reportRepo);
                        break;
                    case 4:
                        if (reportRepo.get().isEmpty()) {
                            System.out.println("\nNão há relatórios");
                            break;
                        }
                        System.out.println("\n#ID | TIPO | DATA - INFO | TOTAL DE FALHAS | ÚLTIMAS FALHAS");
                        createReportOnRepo(reportRepo);
                        break;
                    case 5:
                        return -1;
                    default:
                        System.out.println("Opção inválida");
                }
            } catch (InputMismatchException e) {
                System.out.println("Valor Inválido");
                scan.next();
            }
        }
    }

    private int startAdm() {
        var scan = new Scanner(System.in);
        var opcao = -1;
        LOGGER.info("Iniciando sistema como Administrador.");

        while (true) {
            try {
                Screen.systemAdm();
                opcao = scan.nextInt();

                switch (opcao) {
                    case 0:
                        return 0;
                    case 1:
                        createFailureOnRepo(failureRepo);
                        break;
                    case 2:
                        if (failureRepo.get().isEmpty()) {
                            System.out.println("\nNão há falhas");
                            break;
                        }
                        System.out.println("\n#ID | TIPO | DATA | STATUS | DESCRIÇÃO");
                        failureRepo.get().forEach(f -> System.out.println(f.show_details()));
                        break;
                    case 3:
                        createUserOnRepo(userRepo);
                        break;
                    case 4:
                        if (userRepo.get().isEmpty()) {
                            System.out.println("\nNão há usuários");
                            break;
                        }
                        System.out.println("\n#ID | NOME | TURNO | INFO");
                        userRepo.get().forEach(u -> System.out.println(u.show_details()));
                        break;
                    case 5:
                        if (reportRepo.get().isEmpty()) {
                            System.out.println("\nNão há relatórios");
                            break;
                        }
                        System.out.println("\n#ID | TIPO | DATA - INFO | TOTAL DE FALHAS | ÚLTIMAS FALHAS");
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
                scan.next();
            }
        }
    }

    public void loginStart() {
        var scan = new Scanner(System.in);
        var opcao = -1;

        while (true) {
            try {
                if (opcao == 0) {
                    System.out.println("Saindo...");
                    break;
                }

                Screen.login();
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
            } catch (InputMismatchException e) {
                System.out.println("Valor Inválido");
                scan.next();
            }
        }

    }
}
