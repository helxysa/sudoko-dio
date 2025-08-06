package br.com.dio;

import br.com.dio.model.Board;
import br.com.dio.model.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

import static br.com.dio.utils.BoardTemplate.BOARD_TEMPLATE;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toMap;

public class Main {

    private final static Scanner scanner = new Scanner(System.in);

    private static Board board;

    private final static int BOARD_LIMIT = 9;

    public static void main(String[] args) {
        final var positions = Stream.of(args)
                .collect(toMap(
                        k -> k.split(";")[0],
                        v -> v.split(";")[1]));
        var option = -1;

        // Mostrar cabeçalho do jogo
        showGameHeader();

        while (true) {
            showMenu();
            option = scanner.nextInt();

            switch (option) {
                case 1 -> startGame(positions);
                case 2 -> inputNumber();
                case 3 -> removeNumber();
                case 4 -> showCurrentGame();
                case 5 -> showGameStatus();
                case 6 -> clearGame();
                case 7 -> finishGame();
                case 8 -> {
                    System.out.println("\n Obrigado por jogar! Até logo! ");
                    System.exit(0);
                }
                default -> System.out.println("❌ Opção inválida! Selecione uma das opções do menu.");
            }
        }
    }

    private static void startGame(final Map<String, String> positions) {
        if (nonNull(board)) {
            System.out.println("  O jogo já foi iniciado!");
            return;
        }

        List<List<Space>> spaces = new ArrayList<>();
        for (int i = 0; i < BOARD_LIMIT; i++) {
            spaces.add(new ArrayList<>());
            for (int j = 0; j < BOARD_LIMIT; j++) {
                var positionConfig = positions.get("%s,%s".formatted(i, j));
                var expected = Integer.parseInt(positionConfig.split(",")[0]);
                var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);
                var currentSpace = new Space(expected, fixed);
                spaces.get(i).add(currentSpace);
            }
        }

        board = new Board(spaces);
        System.out.println("\n JOGO INICIADO COM SUCESSO! ");
        System.out.println("=".repeat(50));
        showCurrentGame();
        System.out.println("=".repeat(50));
    }

    private static void inputNumber() {
        if (isNull(board)) {
            System.out.println(" O jogo ainda não foi iniciado!");
            return;
        }

        System.out.println("\n📝 INSERIR NÚMERO:");
        System.out.println("─".repeat(30));
        System.out.print("Informe a coluna (0-8): ");
        var col = runUntilGetValidNumber(0, 8);
        System.out.print("Informe a linha (0-8): ");
        var row = runUntilGetValidNumber(0, 8);
        System.out.printf("Informe o número para a posição [%s,%s] (1-9): ", col, row);
        var value = runUntilGetValidNumber(1, 9);
        if (!board.changeValue(col, row, value)) {
            System.out.printf(" A posição [%s,%s] tem um valor fixo!\n", col, row);
        } else {
            System.out.println("✅ Número inserido com sucesso!");
        }
    }

    private static void removeNumber() {
        if (isNull(board)) {
            System.out.println(" O jogo ainda não foi iniciado!");
            return;
        }

        System.out.println("\n🗑️  REMOVER NÚMERO:");
        System.out.println("─".repeat(30));
        System.out.print("Informe a coluna (0-8): ");
        var col = runUntilGetValidNumber(0, 8);
        System.out.print("Informe a linha (0-8): ");
        var row = runUntilGetValidNumber(0, 8);
        if (!board.clearValue(col, row)) {
            System.out.printf(" A posição [%s,%s] tem um valor fixo!\n", col, row);
        } else {
            System.out.println(" Número removido com sucesso!");
        }
    }

    private static void showCurrentGame() {
        if (isNull(board)) {
            System.out.println(" O jogo ainda não foi iniciado!");
            return;
        }

        var args = new Object[81];
        var argPos = 0;
        for (int i = 0; i < BOARD_LIMIT; i++) {
            for (var col : board.getSpaces()) {
                args[argPos++] = " " + ((isNull(col.get(i).getActual())) ? " " : col.get(i).getActual());
            }
        }
        System.out.println("\n🎲 TABULEIRO ATUAL:");
        System.out.printf((BOARD_TEMPLATE) + "\n", args);
    }

    private static void showGameStatus() {
        if (isNull(board)) {
            System.out.println(" O jogo ainda não foi iniciado!");
            return;
        }

        System.out.println("\n📊 STATUS DO JOGO:");
        System.out.println("─".repeat(30));
        System.out.printf("Status: %s\n", board.getStatus().getLabel());
        if (board.hasErrors()) {
            System.out.println(" O jogo contém erros!");
        } else {
            System.out.println(" O jogo não contém erros!");
        }
    }

    private static void clearGame() {
        if (isNull(board)) {
            System.out.println(" O jogo ainda não foi iniciado!");
            return;
        }

        System.out.println("\n LIMPAR JOGO:");
        System.out.println("─".repeat(30));
        System.out.println("  Tem certeza que deseja limpar seu jogo e perder todo seu progresso?");
        System.out.print("Digite 'sim' ou 'não': ");
        var confirm = scanner.next();
        while (!confirm.equalsIgnoreCase("sim") && !confirm.equalsIgnoreCase("não")) {
            System.out.print(" Informe 'sim' ou 'não': ");
            confirm = scanner.next();
        }

        if (confirm.equalsIgnoreCase("sim")) {
            board.reset();
            System.out.println(" Jogo limpo com sucesso!");
        } else {
            System.out.println(" Operação cancelada!");
        }
    }

    private static void finishGame() {
        if (isNull(board)) {
            System.out.println(" O jogo ainda não foi iniciado!");
            return;
        }

        System.out.println("\n🏁 FINALIZAR JOGO:");
        System.out.println("─".repeat(30));

        if (board.gameIsFinished()) {
            System.out.println("🎉 PARABÉNS! VOCÊ CONCLUIU O JOGO! 🎉");
            System.out.println("=".repeat(50));
            showCurrentGame();
            System.out.println("=".repeat(50));
            board = null;
        } else if (board.hasErrors()) {
            System.out.println(" Seu jogo contém erros! Verifique seu tabuleiro e ajuste-o.");
        } else {
            System.out.println(" Você ainda precisa preencher alguns espaços!");
        }
    }

    private static int runUntilGetValidNumber(final int min, final int max) {
        var current = scanner.nextInt();
        while (current < min || current > max) {
            System.out.printf("Informe um número entre %s e %s\n", min, max);
            current = scanner.nextInt();
        }
        return current;
    }

    private static void showGameHeader() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println(" BEM-VINDO AO JOGO SUDOKU! ");
        System.out.println("=".repeat(60));
        System.out.println(" Instruções:");
        System.out.println("   • Complete o tabuleiro 9x9 com números de 1 a 9");
        System.out.println("   • Cada linha, coluna e quadrado 3x3 deve conter números únicos");
        System.out.println("   • Números fixos não podem ser alterados");
        System.out.println("=".repeat(60));
    }

    private static void showMenu() {
        System.out.println("\n" + "─".repeat(50));
        System.out.println(" MENU PRINCIPAL");
        System.out.println("─".repeat(50));
        System.out.println("1 - Iniciar um novo Jogo");
        System.out.println("2 - Colocar um novo número");
        System.out.println("3 - Remover um número");
        System.out.println("4  - Visualizar jogo atual");
        System.out.println("5  - Verificar status do jogo");
        System.out.println("6  - Limpar jogo");
        System.out.println("7  - Finalizar jogo");
        System.out.println("8  - Sair");
        System.out.println("─".repeat(50));
        System.out.print("Escolha uma opção: ");
    }

}