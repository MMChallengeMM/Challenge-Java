package marmota_mobilidade.system;

public class Screen {

    public static void login() {
        System.out.println("""
                        
                        Escolha um:
                        1. Operador
                        2. Administrador
                        0. Sair
                        --------------------------""");
    }

    public static void systemAdm() {
        System.out.println("""
                        
                        Bem vindo, Administrador, ao sistema de histórico de falhas!
                        =============================
                        Escolha uma opção:
                        1. Criar falha
                        2. Listar falhas
                        3. Criar usuário
                        4. Listar usuários
                        5. Criar relatorios
                        6. Lista relatorios
                        7. Voltar para o login
                        0. Sair
                        -----------------------------""");
    }

    public static void system() {
        System.out.println("""
                        
                        Bem vindo, Operador, ao sistema de histórico de falhas!
                        =============================
                        Escolha uma opção:
                        1. Criar falha
                        2. Listar falhas
                        3. Criar relatorios
                        4. Lista relatorios
                        5. Voltar para o login
                        0. Sair
                        -----------------------------""");
    }

    public static void failureTypes() {
        System.out.println("""
                    
                    Digite o tipo de falha:
                    1. Mecânica
                    2. Elétrica
                    3. Software
                    4. Outro
                    ---------------------------""");
    }

    public static void userTypes() {
        System.out.println("""
                    
                    Digite o tipo de usuário:
                    1. Operador
                    2. Adm
                    ---------------------------""");
    }

    public static void userShifts() {
        System.out.println("""
                    
                    Digite o tipo de usuário:
                    1. Manhã
                    2. Tarde
                    3. Noite
                    ---------------------------""");
    }

    public static void reportTypes() {
        System.out.println("""
                    
                    Digite o tipo de relatório:
                    1. Geral
                    2. Período
                    3. Tipo de falha
                    ---------------------------""");
    }
}
