package marmota_mobilidade.services;

import marmota_mobilidade.models.Admin;
import marmota_mobilidade.models.FAILURE_TYPE;
import marmota_mobilidade.models.Failure;
import marmota_mobilidade.models.Operator;
import marmota_mobilidade.repositories.FailureRepo;
import marmota_mobilidade.repositories.ReportRepo;
import marmota_mobilidade.repositories.UserRepo;

import java.util.Scanner;

public class Menu {
    private final UserRepo userRepo = new UserRepo();
    private final FailureRepo failureRepo = new FailureRepo();
    private final marmota_mobilidade.repositories.ReportRepo ReportRepo = new ReportRepo();

    public void iniciar() {
        System.out.println("Bem vindo ao Marmota Mobilidade");
        while (true) {
            System.out.println("""
                    1. Criar falha
                    2. Listar falhas
                    3. Criar operador
                    4. Listar usuários
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
                case 0:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção Inválida");
            }

            if (opcao == 0) {
                break;
            }
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
                repo.add(
                        Operator.builder()
                                .id(id)
                                .name(name)
                                .build());
                break;
            case 2:
                repo.add(
                        Admin.builder()
                                .id(id)
                                .name(name)
                                .build());
            default:
                System.out.println("Opção Inválida");
        }


    }

    public static void createFailureOnRepo(FailureRepo repo) {
        var scan = new Scanner(System.in);

        try {
            System.out.println("Digite o id:");
            var id = scan.nextLine();

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

            repo.add(
                    Failure.builder()
                            .id(id)
                            .failureType(failType)
                            .failureDescription(description)
                            .build()
            );

        } catch (IllegalArgumentException e) {
            System.out.println("Opção Inválida");
        }

    }
}
