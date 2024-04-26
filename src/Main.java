import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Scanner tgb = new Scanner(System.in);
        System.out.println("♦♥♣♠ Welcome to Java-BlackJack! ♦♥♣♠");
        System.out.println("Press enter to begin");
        String temp = tgb.nextLine();
        System.out.println("Your total score: " + StartGame());
    }
    public static String StartGame() {
        Scanner tgb = new Scanner(System.in);
        String [] cardDeck = Shuffle();
        String [] dealersCards = new String[52];
        String [] playersCards = new String[52];
        int dealersCardNumber = 0;
        int playersCardNumber = 0;
        int topCard = 0;

        for (int i = 0; i < 1; i++) {
            System.out.println("Dealers cards:");
            for (; topCard < 2; topCard++) {
                dealersCards[topCard] = cardDeck[topCard];
                cardDeck[topCard] = null;
                dealersCardNumber++;
            }
            System.out.print("[?," + dealersCards[1] + "]");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println("Your cards:");
            playersCards[0] = cardDeck[topCard];
            playersCardNumber++;
            cardDeck[topCard] = null;
            topCard++;
            System.out.print("[" +  playersCards[0] + "]");
            System.out.println(" ");
            System.out.println(" ");
            for (int j = 1; j < 52; j++) {
                System.out.println("Do you want to hit or stand?");
                String answer = tgb.nextLine();
                if (answer.toLowerCase().contains("hit")) {
                    playersCardNumber++;
                    playersCards[j] = cardDeck[topCard];
                    cardDeck[topCard] = null;
                    topCard++;
                    System.out.println("Your cards:");
                    System.out.print("[" + playersCards[0] + ",");
                    for (int k = 1; k < j+1; k++) {
                        System.out.print(playersCards[k]);
                        if (k < j) {
                            System.out.print(",");
                        }
                    }
                    System.out.print("]");
                    System.out.println(" ");
                    System.out.println(" ");
                    int playersValue = PlayerValueCalculator(playersCards, playersCardNumber);
                    System.out.println("Your current card value is: " + playersValue);
                    if (playersValue > 21) {
                        return "Bust";
                    }
                    if (playersValue == 21) {
                        return "BlackJack";
                    }
                }
                else if (answer.toLowerCase().contains("stand")) {
                    break;
                }
            }
        }



        return String.valueOf(PlayerValueCalculator(playersCards, playersCardNumber));
    }

    public static String[] Shuffle() {
        String [] cardDeck = new String[52];
        for (int i = 0; i < 13; i++) {
            cardDeck[i] = (i+1) + "♦";
        }
        for (int i = 13; i < 26; i++) {
            cardDeck[i] = (i-12) + "♥";
        }
        for (int i = 26; i < 39; i++) {
            cardDeck[i] = (i-25) + "♣";
        }
        for (int i = 39; i < 52; i++) {
            cardDeck[i] = (i-38) + "♠";
        }
        for (int i = 0; i < 52; i++) {
            if (cardDeck[i].contains("11")) {
                cardDeck[i] = (cardDeck[i]).replace("11", "J");
            }
            if (cardDeck[i].contains("12")) {
                cardDeck[i] = (cardDeck[i]).replace("12", "Q");
            }
            if (cardDeck[i].contains("13")) {
                cardDeck[i] = (cardDeck[i]).replace("13", "K");
            }
            if (cardDeck[i].contains("1")) {
                cardDeck[i] = (cardDeck[i]).replace("1", "A");
            }
            if (cardDeck[i].contains("A0")) {
                cardDeck[i] = (cardDeck[i]).replace("A0", "10");
            }
        }
        System.out.println("Shuffling cards...");
        for (int i = 0; i < 52; i++) {
            String currentCard = cardDeck[i];
            int randomNumber = (int) ((Math.random()*52));
            cardDeck[i] = cardDeck[randomNumber];
            cardDeck[randomNumber] = currentCard;
        }
        System.out.println("Cards shuffled");
        System.out.println(" ");

        return cardDeck;
    }

    public static int PlayerValueCalculator(String [] playersCards, int playersCardNumber) {
        int playersValue = 0;
        for (int l = 0; l < playersCardNumber; l++) {
            if (playersCards[l].contains("A") && playersValue < 11) {
                playersValue += 11;
            }
            else if (playersCards[l].contains("A") && playersValue > 10){
                playersValue += 1;
            }
            if (playersCards[l].contains("2")) {
                playersValue += 2;
            }
            if (playersCards[l].contains("3")) {
                playersValue += 3;
            }
            if (playersCards[l].contains("4")) {
                playersValue += 4;
            }
            if (playersCards[l].contains("5")) {
                playersValue += 5;
            }
            if (playersCards[l].contains("6")) {
                playersValue += 6;
            }
            if (playersCards[l].contains("7")) {
                playersValue += 7;
            }
            if (playersCards[l].contains("8")) {
                playersValue += 8;
            }
            if (playersCards[l].contains("9")) {
                playersValue += 9;
            }
            if (playersCards[l].contains("10") || playersCards[l].contains("J") || playersCards[l].contains("Q") || playersCards[l].contains("K")) {
                playersValue += 10;
            }
        }
        return playersValue;
    }
}