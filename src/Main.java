import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Scanner tgb = new Scanner(System.in);
        System.out.println("♦♥♣♠");
        System.out.println("Welcome to Java-BlackJack! How many people are playing?");
        int playerNumber = tgb.nextInt();
        String [] playerNames = new String[playerNumber];
        for (int i = 0; i < playerNumber; i++) {
            System.out.println("What is the name of player " + (i+1) + "?");
            playerNames[i] = tgb.next();
        }
        StartGame(playerNumber, playerNames);
    }
    public static void StartGame(int playerNumber, String[] playerNames) {
        Shuffle();
    }

    public static void Shuffle() {
        String [] cardDeck = new String[52];
        for (int i = 0; i < 13; i++) {
            cardDeck[i] = String.valueOf((i+1)) + "♦";
            System.out.println(cardDeck[i]);
        }
    }
}