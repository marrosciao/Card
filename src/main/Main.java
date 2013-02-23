package main;

import board.Board;
import card.Ability;
import card.Card;
import card.Card.CardType;
import deck.Deck;
import java.util.Scanner;
import player.Player;

/**
 *
 * @author Ben Rudi
 */
public class Main {

    public static void main(String args[]) {

        Boolean playerOneTurn = true;
        Scanner scanner = new Scanner(System.in);

        Board board = new Board();
        Player playerOne = new Player();
        Ability none = new Ability("None", "");
        Ability drawOne = new Ability("DrawOne", "Draw one card.");

        Deck playerDeck = playerOne.getPlayerDeck();
        Deck playerHand = playerOne.getHand();
        Deck playerDiscard = playerOne.getPlayerDiscard();
        Deck centerDeck = board.getCenterDeck();
        Deck voidDeck = board.getCenterVoid();
        Deck centerRow = board.getCenterRow();

        Card apprentice = new Card("Apprentice", CardType.HERO,
                1, 0, 0,
                0, 0, 0,
                none);

        Card militia = new Card("Militia", CardType.HERO,
                0, 1, 0,
                0, 0, 0,
                none);

        Card mystic = new Card("Mystic", CardType.HERO,
                3, 0, 0,
                3, 0, 1,
                none);
        
        Card heavy = new Card("Heavy", CardType.HERO,
                0, 2, 0,
                2, 0, 1,
                none);
        
        Card initiate = new Card("Initiate", CardType.HERO,
                0, 0, 0,
                1, 0, 1,
                drawOne);

        for (int i = 0; i < 8; i++) {
            playerOne.getPlayerDeck().add(apprentice);
        }
        for (int i = 0; i < 2; i++) {
            playerOne.getPlayerDeck().add(militia);
        }
        for (int i = 0; i < 5; i++) {
            board.getCenterDeck().add(mystic);
            board.getCenterDeck().add(initiate);
            board.getCenterDeck().add(heavy);
        }

        playerDeck.shuffle();
        playerOne.draw(playerHand, playerDeck, 5);

        centerDeck.shuffle();
        playerOne.draw(centerRow, centerDeck, 6);

        while (board.getHonorLeft() > 0) {
            if (playerOneTurn) {
                System.out.println("Cards in hand: " + playerOne.cardsByName(playerHand));

                String playerOption = "";
                while (!playerOption.equals("3")) {
                    System.out.println("1: Play a card.");
                    System.out.println("2: Buy a card.");
                    System.out.println("3: End turn.");
                    playerOption = scanner.nextLine();

                    if (playerOption.equals("1")) {
                        if (playerHand.size() > 0) {
                            System.out.println("Which card would you like to play?");
                            for (int j = 0; j < playerHand.size(); j++) {
                                System.out.println(j + 1 + ":" + playerHand.getCard(j).getName());
                            }
                            String cardChoice = scanner.nextLine();
                            int choice = Integer.parseInt(cardChoice) - 1;
                            playerOne.play(playerOne, playerHand.getCard(choice));
                        } else {
                            System.out.println("No cards left in hand.");
                        }
                    } else if (playerOption.equals("2")) {
                        System.out.println("Which card would you like to buy?");
                        System.out.println("Rune: " + playerOne.getRuneTotal());
                        System.out.println("Battle: " + playerOne.getBattleTotal());
                        for (int j = 0; j < centerRow.size(); j++) {
                            System.out.println(j + 1 + ": " + centerRow.getCard(j).getName()
                                    + " Rune Cost: " + centerRow.getCard(j).getRuneCost()
                                    + " Battle Cost: " + centerRow.getCard(j).getBattleCost());
                        }
                        String card = scanner.nextLine();
                        int choice = Integer.parseInt(card) - 1;
                        playerOne.buy(centerRow.getCard(choice), playerOne, board);
                    } else if (playerOption.equals("3")) {
                        System.out.println("End of turn.");
                        playerOne.endOfTurn();
                        playerOneTurn = false;
                    } else {
                        System.out.println("Please enter a valid option.");
                    }
                } // player out of cards

            } else { // end of player one turn
                playerOneTurn = true;
            } // start on player one turn again!
        } // end of game - no honor left
    }
}