package pt.isec.pa.deepsea;

import pt.isec.pa.deepsea.model.state.GameContext;

import java.util.Scanner;

public class TestUI {
    public static void main(String[] args) {
        GameContext context = new GameContext();
        Scanner sc = new Scanner(System.in);

        boolean running = true;

        while (running) {
            System.out.println("\n====================");
            System.out.println("Estado atual: " + context.getState());
            System.out.println("====================");

            if (context.getState() == pt.isec.pa.deepsea.model.state.GameState.MINIGAME) {
                System.out.println(context.getMiniGameAsString());
            } else {
                System.out.println(context.getCurrentGridAsString());
            }
            System.out.println(context.getShipInfoAsString());
            System.out.println(context.getDronesInfoAsString());
            System.out.println(context.getCommandInfoAsString());
            System.out.print("> ");

            String option = sc.nextLine().trim();

            switch (option) {
                case "1" -> {
                    System.out.print("Direção (w/a/s/d): ");
                    String command = sc.nextLine().trim();

                    if (context.move(command))
                        System.out.println("Movimento efetuado.");
                    else
                        System.out.println("Movimento inválido.");
                }

                case "2" -> {
                    if (context.enterShop())
                        System.out.println("Entraste na shop.");
                    else
                        System.out.println("Não podes entrar na shop neste estado.");
                }

                case "3" -> {
                    if (context.leaveShop())
                        System.out.println("Saíste da shop.");
                    else
                        System.out.println("Não podes sair da shop neste estado.");
                }

                case "4" -> {
                    int id = askDroneId(sc);

                    if (context.repair(id))
                        System.out.println("Drone reparado.");
                    else
                        System.out.println("Não foi possível reparar o drone.");
                }

                case "5" -> {
                    int id = askDroneId(sc);

                    if (context.refuel(id))
                        System.out.println("Drone abastecido.");
                    else
                        System.out.println("Não foi possível abastecer o drone.");
                }

                case "6" -> {
                    int id = askDroneId(sc);

                    if (context.descend(id))
                        System.out.println("Drone lançado para o fosso.");
                    else
                        System.out.println("Não foi possível lançar o drone.");
                }

                case "7" -> {
                    if (context.mining())
                        System.out.println("Minério recolhido.");
                    else
                        System.out.println("Não foi possível minerar.");
                }

                case "8" -> {
                    if (context.retrieveArtifact())
                        System.out.println("Artefacto recolhido. Entraste no minijogo.");
                    else
                        System.out.println("Não foi possível recolher artefacto.");
                }

                case "9" -> {
                    if (context.winMini())
                        System.out.println("Minijogo ganho.");
                    else
                        System.out.println("Não podes ganhar o minijogo agora.");
                }

                case "10" -> {
                    if (context.loseMini())
                        System.out.println("Minijogo perdido.");
                    else
                        System.out.println("Não podes perder o minijogo agora.");
                }

                case "11" -> {
                    System.out.println(context.getMiniGameAsString());

                    System.out.print("Row: ");
                    int row = Integer.parseInt(sc.nextLine().trim());

                    System.out.print("Col: ");
                    int col = Integer.parseInt(sc.nextLine().trim());

                    if (context.movePiece(row, col))
                        System.out.println("Peça movida.");
                    else
                        System.out.println("Movimento inválido no minigame.");

                    System.out.println(context.getMiniGameAsString());
                }

                case "20" -> {
                    if (context.ascend())
                        System.out.println("Ascend");
                    else
                        System.out.println("Não podes ascender agora.");
                }

                case "save" -> {
                    if (context.saveGame())
                        System.out.println("Jogo guardado.");
                    else
                        System.out.println("Erro ao guardar jogo.");
                }

                case "load" -> {
                    GameContext loaded = GameContext.loadGame();
                    if (loaded != null) {
                        context = loaded;
                        System.out.println("Jogo carregado.");
                    } else {
                        System.out.println("Erro ao carregar jogo.");
                    }
                }

                case "0" -> running = false;

                default -> System.out.println("Opção inválida.");
            }
        }

        sc.close();
        System.out.println("A sair...");
    }

    private static int askDroneId(Scanner sc) {
        while (true) {
            try {
                System.out.print("ID do drone: ");
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("ID inválido. Escreve um número.");
            }
        }
    }
}