import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner tgb = new Scanner(System.in);
        boolean gameInProgress = true;
        System.out.println("♦♥♣♠ Welcome to Java-BlackJack! ♦♥♣♠");
        System.out.println("Press enter to begin");
        String temp = tgb.nextLine(); //Variabel som dröjer programmet tills man skriver in något eller trycker på enter

        while (gameInProgress) { //While loop som bara körs om spelaren har specifierat att de vill köra igen
            int topCard = 0;
            String [] dealersCards = new String[52]; //Array med dealerns kort
            String [] cardDeck = Shuffle(); //Kallar på metoden Shuffle för att skapa en kortlek och blanda den, metoden returnerar en array med alla kort i en blandad ordning

            System.out.println("Dealers cards:");
            for (; topCard < 2; topCard++) { //Sätter dealerns startkort till de två korten överst i högen
                dealersCards[topCard] = cardDeck[topCard];
            }
            System.out.print("[?," + dealersCards[1] + "]"); //Visar bara spelaren ett av dealerns kort
            System.out.println(" ");
            System.out.println(" ");

            String [] playerResults = PlayersTurn(topCard, cardDeck); //Kallar på metoden PlayersTurn för att ge spelaren en tur att spela, denna metod returnerar flera värden som då finns i en array
            System.out.println("Your total score: " + playerResults[1]); //Skriver ut spelarens poäng (spelarens poäng ligger på plats 1 i arrayen)
            if (!playerResults[0].equals("Normal")) { //Om spelaren får antingen Bust eller BlackJack så skriver programmet ut det, om det bara är normal så skrivs det inte ut
                System.out.println(playerResults[0]); //Spelarens resultat (Bust, BlackJack eller Normal) ligger på plats 0 i arrayen
            }
            System.out.println(" ");

            System.out.println("Press enter to continue");
            String temp2 = tgb.nextLine(); //Variabel som dröjer programmet tills man skriver in något eller trycker på enter

            String [] dealerResults = DealersTurn(Integer.parseInt(playerResults[2]), dealersCards, cardDeck); //Kallar på metoden DealersTurn för att ge dealern en tur att spela (returnerar också en array)
            System.out.println("Dealer's total score: " + dealerResults[1]); //Skriver ut dealerns poäng
            if (!dealerResults[0].equals("Normal")) { //Skriver bara ut resultatet om det är antingen Bust eller BlackJack
                System.out.println(dealerResults[0]);
            }

            System.out.println(" ");
            //If sats som räknar ut om det är spelaren eller dealern som vinner utifrån deras poäng och spelreglerna
            if (Integer.parseInt(dealerResults[1]) > Integer.parseInt(playerResults[1]) && dealerResults[0].equals("Normal") || Integer.parseInt(dealerResults[1]) > Integer.parseInt(playerResults[1]) && dealerResults[0].equals("BlackJack") || playerResults[0].equals("Bust") && dealerResults[0].equals("Normal") || playerResults[0].equals("Bust") && dealerResults[0].equals("BlackJack")) {
                System.out.println("Dealer wins");
            }
            else if (dealerResults[0].equals("Bust") && playerResults[0].equals("Bust") || dealerResults[1].equals(playerResults[1])) {
                System.out.println("Draw");
            }
            else {
                System.out.println("Player wins");
            }
            System.out.println(" ");
            System.out.println("Do you want to play again? y/n");
            String answer = tgb.nextLine();
            if (!answer.toLowerCase().contains("y")) {
                gameInProgress = false;
            }
        }
    }
    public static String[] PlayersTurn(int topCard, String[] cardDeck) {
        Scanner tgb = new Scanner(System.in);
        String [] playersCards = new String[52]; //Array med spelarens kort
        String result = null; //Variabel som senare sätts till resultatet (Bust, BlackJack eller Normal)
        int playersCardNumber = 0; //Variabel som håller koll på spelarens kortmängd

        System.out.println("Your cards:");
        playersCards[0] = cardDeck[topCard];
        playersCards[1] = cardDeck[topCard+1]; //Sätter spelarens startkort till de två översta korten i kortleken
        playersCardNumber += 2; //Adderar spelarens kortmängd med 2 eftersom 2 kort har lagts till (kortmängden adderas varenda gång ett kort läggs till i spelarens deck)
        topCard += 2; //Adderar toppkortet med 2 eftersom 2 kort har tagits bort (Toppkortet adderas varenda gång ett kort tas från kortleken)
        System.out.print("[" +  playersCards[0] + "," + playersCards[1] + "]"); //Skriver ut spelarens startkort
        System.out.println(" ");
        System.out.println(" ");
        int playersValue = ValueCalculator(playersCards, playersCardNumber); //Kallar på metoden ValueCalculator för att räkna ut spelarens poäng för att se om det är en BlackJack redan från början
        if (playersValue == 21) { //Sätter resultatet till BlackJack och avslutar metoden om det är en BlackJack
            result = "BlackJack";
            return new String[]{result, String.valueOf(playersValue), String.valueOf(topCard)}; //Om spelaren har fått BlackJack avslutas spelarens tur och alla värden returneras
        }
        for (int i = 2; i < 52; i++) {
            System.out.println("Do you want to hit or stand? h/s"); //Frågar spelaren om de vill ha fler kort (hit) eller vill avstå (stand)
            String answer = tgb.nextLine();
            if (answer.toLowerCase().contains("h")) { //Går endast igenom koden om spelaren skriver hit
                playersCardNumber++;
                playersCards[i] = cardDeck[topCard]; //Sätter nästa kort i spelarens deck till det översta kortet i leken
                topCard++;
                System.out.println("Your cards:");
                System.out.print("[" + playersCards[0] + ",");
                for (int j = 1; j < i+1; j++) { //Skriver ut spelarens nya deck
                    System.out.print(playersCards[j]);
                    if (j < i) {
                        System.out.print(","); //Skriver bara ut ett komma om det är före sista kortet som ska skrivas ut
                    }
                }
                System.out.print("]");
                System.out.println(" ");
                System.out.println(" ");
                playersValue = ValueCalculator(playersCards, playersCardNumber); //Kallar på metoden ValueCalculator för att räkna ut spelarens totala poäng
                System.out.println("Your current card value is: " + playersValue);
                if (playersValue > 21) { //Om spelarens poäng är över 21 så avslutas spelarens tur och resultatet blir Bust
                    result = "Bust";
                    return new String []{result, String.valueOf(ValueCalculator(playersCards, playersCardNumber)), String.valueOf(topCard)}; //Returnerar resultatet, spelarens poäng och toppkortsnumret
                }
                else if (playersValue == 21) { //Om spelaren får exakt 21 poäng så avslutas spelarens tur och resultatet blir BlackJack
                    result = "BlackJack";
                    return new String []{result, String.valueOf(ValueCalculator(playersCards, playersCardNumber)), String.valueOf(topCard)}; //Returnerar resultatet, spelarens poäng och toppkortsnumret
                }
            }
            else if (answer.toLowerCase().contains("s")) { //Avslutar spelarens tur om spelaren skriver stand
                result = "Normal";
                return new String []{result, String.valueOf(ValueCalculator(playersCards, playersCardNumber)), String.valueOf(topCard)}; //Returnerar resultatet, spelarens poäng och toppkortsnumret
            }
        }
        return new String[]{result, String.valueOf(ValueCalculator(playersCards, playersCardNumber)), String.valueOf(topCard)}; //Returnerar resultatet, spelarens poäng och toppkortsnumret
    }

    public static String [] DealersTurn(int topCard, String[] dealersCards, String[] cardDeck) {
        System.out.println("Dealers cards:");
        System.out.println("[" + dealersCards[0] + "," + dealersCards[1] + "]");
        System.out.println(" ");
        int dealersCardNumber = 2; //Sätter dealerns kortnummer till 2 eftersom de redan har 2 kort
        int dealersValue = ValueCalculator(dealersCards, dealersCardNumber); //Kallar på metoden ValueCalculator för att räkna ut dealerns poäng för att se om det är en BlackJack redan från början
        String result = null;
        if (dealersValue == 21) { //Sätter resultatet till BlackJack och avslutar metoden om det är en BlackJack
            result = "BlackJack";
            return new String[]{result, String.valueOf(dealersValue)};
        }
        for (int i = topCard; i < 52 ; i++) { //Gör så att platsen i arrayen med spelkort inte kan gå out of bounds
            for (int j = 2; j < 52; j++) {
                dealersValue = ValueCalculator(dealersCards, dealersCardNumber); //Räknar ut dealerns poäng för att se om dealern får ta upp fler kort eller inte
                if (dealersValue > 17) { //Om dealerns kortvärde är över 17 får den inte ta upp fler kort och då avslutas for-loopen
                    i = 52;
                    break;
                }
                else { //Annars får dealern ytterligare ett kort
                    dealersCards[j] = cardDeck[topCard];
                    topCard++;
                    dealersCardNumber++;
                }
                System.out.println("Dealers cards:"); //Dealerns kortlek skrivs ut varje gång de får ett nytt kort
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
                dealersValue = ValueCalculator(dealersCards, dealersCardNumber); //Kallar på metoden ValueCalculator för att räkna ut dealerns poäng
                if (dealersValue > 21) { //Om dealerns poäng är över 21 så blir resultatet Bust och for-loopen avslutas
                    result = "Bust";
                    break;
                }
                else if (dealersValue == 21) { //Om dealerns poäng är exakt 21 så blir resultatet BlackJack och for-loopen avslutas
                    result = "BlackJack";
                    break;
                }
            }
        }
        if (result == null) { //Sätter resultatet till Normal om det inte ändrats sedan början
            result = "Normal";
        }
        return new String[]{result, String.valueOf(dealersValue)}; //Returnerar resultatet och dealerns poäng
    }

    public static String[] Shuffle() {
        String [] cardDeck = new String[52];
        for (int i = 0; i < 13; i++) { //For loopar som sätter alla platser i kortleken till alla spelkort som ingår i en kortlek
            cardDeck[i] = (i+1) + "♦"; //De första 12 platserna är ruter
        }
        for (int i = 13; i < 26; i++) {
            cardDeck[i] = (i-12) + "♥"; //De andra 12 platserna är hjärter osv.
        }
        for (int i = 26; i < 39; i++) {
            cardDeck[i] = (i-25) + "♣";
        }
        for (int i = 39; i < 52; i++) {
            cardDeck[i] = (i-38) + "♠";
        }
        for (int i = 0; i < 52; i++) {
            if (cardDeck[i].contains("11")) { //Ersätter alla 11:or med J för knäktar
                cardDeck[i] = (cardDeck[i]).replace("11", "J");
            }
            if (cardDeck[i].contains("12")) { //Ersätter alla 12:or med Q för damer
                cardDeck[i] = (cardDeck[i]).replace("12", "Q");
            }
            if (cardDeck[i].contains("13")) { //Ersätter alla 13:n med K för kungar
                cardDeck[i] = (cardDeck[i]).replace("13", "K");
            }
            if (cardDeck[i].contains("1")) { //Ersätter alla 1:or med A för ess
                cardDeck[i] = (cardDeck[i]).replace("1", "A");
            }
            if (cardDeck[i].contains("A0")) { //Eftersom alla 1:or har ersatts med A så blir 10:or till A0, dessa ersätts återigen med 10
                cardDeck[i] = (cardDeck[i]).replace("A0", "10");
            }
        }
        System.out.println("Shuffling cards...");
        for (int i = 0; i < 52; i++) { //Blandar kortleken genom att byta ut det nuvarande kortet i kortleken med ett annat slumpmässigt kort, gör detta för varenda plats i kortleken
            String currentCard = cardDeck[i];
            int randomNumber = (int) ((Math.random()*52));
            cardDeck[i] = cardDeck[randomNumber];
            cardDeck[randomNumber] = currentCard;
        }
        System.out.println("Cards shuffled");
        System.out.println(" ");
        return cardDeck; //Returnerar den blandade kortleken
    }

    public static int ValueCalculator(String [] cards, int cardNumber) {
        int value = 0;

        for (int i = 2; i < 10; i++) { //Kollar om kortleken innehåller 2-10 och adderar den totala poängen med det värde som den hittar
            for (int j = 0; j < cardNumber; j++) {
                if (cards[j].contains(String.valueOf(i))) {
                    value += i;
                }
            }
        }

        int numberOfAces = 0; //Variabel som ska hålla koll på hur många A i spelarens lek som inte har blivit omräknade till 1
        for (int l = 0; l < cardNumber; l++) { //Kollar om kortleken innehåller A, 10, J, Q eller K och adderar den totala poängen med respektive värden som de korten har
            if (cards[l].contains("A") && value < 11) {
                value += 11;
                numberOfAces++;
            }
            else if (cards[l].contains("A") && value > 10){ //Om värdet av kortleken redan är högre än 10 och programmet hittar ett A i den så sätts A:ets värde till 1 istället för 11 så att inte spelaren går Bust
                value += 1;
            }

            if (cards[l].contains("10") || cards[l].contains("J") || cards[l].contains("Q") || cards[l].contains("K")) {
                value += 10;
            }
        }

        for (int i = 0; i < cardNumber; i++) { //Om värdet är över 21 och kortleken innehåller ett A som inte ännu har gjorts om till 1 så subtraheras 10
            if (cards[i].contains("A") && value > 21 && numberOfAces > 0) {
                value -= 10;
            }
        }

        return value; //Returnerar poängen
    }
}