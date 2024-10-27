# Sistema de Histórico de Falhas do CCO
> Projeto desenvolvido em parceira da CCR como Challenge da FIAP 2024

---

## Representantes
- Arthur Ribeiro (560417)
- João Vincius Alves (559369)
- Juan Pablo (560445)

---

## Descritivo
- ### Introdução
  O objetivo desse projeto é de criar um sistema que possa diminuir o trabalho manual e otimizar a gestão de falhas dos Centros de Controle de Operações (CCO), proporcionando uma maneira eficiente  registrar e acompanhar as falhas em trens, estações e equipamentos. 

- ### Objetivos
   Dentro do sistema teremos dois tipos de usuários: Operadores e Administradores.  

   - Usuários poderão: Ver/gerar novos relatórios e checar históricos de falhas;
   - **_Apenas_** administradores poderão: Gerenciar falhas (Criar, remover, alterar).  

  O sistema é organizado em:
  1. Equipamentos: Cada equipamento terá um histórico de falhas e cada estação/trem/linha terão vários equipamentos;
  2. Falhas: Cada falha poderá pertencer a uma manutenção;
  3. Histórico de falha: Organizará as falhas;
  4. Manutenção: Responsável por registrar as resoluções das falhas;
  5. Relatório: Exibir um conceito geral do sistema de acordo com um histórico de falha.