package marmota_mobilidade.services;

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
                    5. Sair
                    """);
            var scan = new Scanner(System.in);
            var opcao = scan.nextInt();

            switch (opcao) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    createOperatorOnRepo(userRepo);
                    break;
                case 4:
                    System.out.println(userRepo.get());
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção Inválida");
            }

            if (opcao == 5) {
                break;
            }
        }
    }

    public static void createOperatorOnRepo(UserRepo repo) {
        var scan = new Scanner(System.in);
        System.out.println("Digite o id:");
        var id = scan.nextLine();
        System.out.println("Digite o nome:");
        var name = scan.nextLine();
        repo.add(
                Operator.builder()
                        .id(id)
                        .name(name)
                        .build());
    }

    public static void createFailureOnRepo(FailureRepo repo) {
        var scan = new Scanner(System.in);
        System.out.println("Digite o id:");
        var id = scan.nextLine();
        System.out.println("""
                1. Mecânica
                2. Elétrica
                3. Software
                4. Outro
                """);

    }
}
