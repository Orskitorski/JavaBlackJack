import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Scanner tgb = new Scanner(System.in);
        int topCard = 0;
        String [] dealersCards = new String[52];
        String [] cardDeck = Shuffle();

        System.out.println("♦♥♣♠ Welcome to Java-BlackJack! ♦♥♣♠");
        System.out.println("Press enter to begin");
        String temp = tgb.nextLine();
        System.out.println("Dealers cards:");
        for (; topCard < 2; topCard++) {
            dealersCards[topCard] = cardDeck[topCard];
        }
        System.out.print("[?," + dealersCards[1] + "]");
        System.out.println(" ");
        System.out.println(" ");

        String [] playerResults = StartGame(topCard, cardDeck);
        System.out.println("Your total score: " + playerResults[1]);
        if (!playerResults[0].equals("Normal")) {
            System.out.println(playerResults[0]);
        }
        System.out.println(" ");

        String [] dealerResults = Dealer(Integer.parseInt(playerResults[2]), dealersCards, cardDeck);
        System.out.println("Dealer's total score: " + dealerResults[1]);
        if (!dealerResults[0].equals("Normal")) {
            System.out.println(dealerResults[0]);
        }

        System.out.println(" ");
        if (Integer.parseInt(dealerResults[1]) > Integer.parseInt(playerResults[1]) && dealerResults[0].equals("Normal") || Integer.parseInt(dealerResults[1]) > Integer.parseInt(playerResults[1]) && dealerResults[0].equals("BlackJack") || playerResults[0].equals("Bust") && dealerResults[0].equals("Normal") || playerResults[0].equals("Bust") && dealerResults[0].equals("BlackJack")) {
            System.out.println("Dealer wins");
        }
        else if (dealerResults[0].equals("Bust") && playerResults[0].equals("Bust") || dealerResults[1].equals(playerResults[1])) {
            System.out.println("Draw");
        }
        else {
            System.out.println("Player wins");
        }
    }
    public static String[] StartGame(int topCard, String[] cardDeck) {
        Scanner tgb = new Scanner(System.in);
        String [] playersCards = new String[52];
        String result = null;
        int playersCardNumber = 0;

        System.out.println("Your cards:");
        playersCards[0] = cardDeck[topCard];
        playersCardNumber++;
        topCard++;
        System.out.print("[" +  playersCards[0] + "]");
        System.out.println(" ");
        System.out.println(" ");
        for (int i = 1; i < 52; i++) {
            System.out.println("Do you want to hit or stand?");
            String answer = tgb.nextLine();
            if (answer.toLowerCase().contains("hit")) {
                playersCardNumber++;
                playersCards[i] = cardDeck[topCard];
                topCard++;
                System.out.println("Your cards:");
                System.out.print("[" + playersCards[0] + ",");
                for (int j = 1; j < i+1; j++) {
                    System.out.print(playersCards[j]);
                    if (j < i) {
                        System.out.print(",");
                    }
                }
                System.out.print("]");
                System.out.println(" ");
                System.out.println(" ");
                int playersValue = ValueCalculator(playersCards, playersCardNumber);
                System.out.println("Your current card value is: " + playersValue);
                if (playersValue > 21) {
                    result = "Bust";
                    break;
                }
                else if (playersValue == 21) {
                    result = "BlackJack";
                    break;
                }
            }
            else if (answer.toLowerCase().contains("stand")) {
                break;
            }
        }

        if (result == null) {
            result = "Normal";
        }
        return new String[]{result, String.valueOf(ValueCalculator(playersCards, playersCardNumber)), String.valueOf(topCard)};
    }

    public static String [] Dealer(int topCard, String[] dealersCards, String[] cardDeck) {
        int dealersCardNumber = 2;
        int dealersValue = 0;
        String result = null;
        for (int i = topCard; i < 52 ; i++) {
            for (int j = 2; j < 52; j++) {
                dealersValue = ValueCalculator(dealersCards, dealersCardNumber);
                if (dealersValue > 17) {
                    i = 52;
                    break;
                }else {
                    dealersCards[j] = cardDeck[topCard];
                    topCard++;
                    dealersCardNumber++;
                }
                System.out.println("Dealers cards:");
                System.out.print("[" + dealersCards[0] + ",");
                for (int l = 1; l < dealersCardNumber; l++) {
                    System.out.print(dealersCards[l]);
                    if (l < (dealersCardNumber-1)) {
                        System.out.print(",");
                    }
                }
                System.out.print("]");
                System.out.println(" ");
                System.out.println(" ");
                dealersValue = ValueCalculator(dealersCards, dealersCardNumber);
                if (dealersValue > 21) {
                    result = "Bust";
                    break;
                }
                else if (dealersValue == 21) {
                    result = "BlackJack";
                    break;
                }
            }
        }
        if (result == null) {
            result = "Normal";
        }
        return new String[]{result, String.valueOf(dealersValue)};
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

    public static int ValueCalculator(String [] cards, int cardNumber) {
        int value = 0;

        for (int i = 2; i < 10; i++) {
            for (int j = 0; j < cardNumber; j++) {
                if (cards[j].contains(String.valueOf(i))) {
                    value += i;
                }
            }
        }

        for (int l = 0; l < cardNumber; l++) {
            if (cards[l].contains("A") && value < 11) {
                value += 11;
            }
            else if (cards[l].contains("A") && value > 10){
                value += 1;
            }

            if (cards[l].contains("10") || cards[l].contains("J") || cards[l].contains("Q") || cards[l].contains("K")) {
                value += 10;
            }
        }

        for (int i = 0; i < cardNumber; i++) {
            if (cards[i].contains("A") && value > 21) {
                value -= 10;
            }
        }

        return value;
    }
}