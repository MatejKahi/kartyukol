package com.company;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        List<Card> cards = new CardsReader().ReadCardsFromFile("cisla.txt");
        new Analyzer().Analyze(cards);
    }
}

enum CardIssuer {Visa, MasterCard, Other}

class Card {
    private final String number;
    private final CardIssuer issuer;

    Card(String number, CardIssuer issuer) {
        this.number = number;
        this.issuer = issuer;
    }

    public CardIssuer getIssuer() {
        return issuer;
    }
}

class CardsReader {
    public List<Card> ReadCardsFromFile(String fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream(fileName));
        List<Card> result = new ArrayList<>();
        do {
            String cardNumber = scanner.nextLine();
            Card card = ParseCard(cardNumber);
            result.add(card);
        } while (scanner.hasNext());
        return result;
    }

    private Card ParseCard(String cardNumber) {
        if ((cardNumber.length() == 13 || cardNumber.length() == 16 || cardNumber.length() == 19) && cardNumber.charAt(0) == '4') {
            return new Card(cardNumber, CardIssuer.Visa);
        }
        if (cardNumber.length() == 16) {
            int firstFour = Integer.parseInt(cardNumber.substring(0, 4));
            if ((firstFour >= 2221 && firstFour <= 2720) || (firstFour >= 5100 && firstFour <= 5599))
                return new Card(cardNumber, CardIssuer.MasterCard);
        }
        return new Card(cardNumber, CardIssuer.Other);
    }
}

class Analyzer {
    public void Analyze(List<Card> cards) {
        int visaCount = 0;
        int mcCount = 0;

        for (Card card : cards) {
            if (card.getIssuer() == CardIssuer.Visa) visaCount++;
            if (card.getIssuer() == CardIssuer.MasterCard) mcCount++;
        }

        System.out.println("Amount of Visa cards: " + visaCount);
        System.out.println("Amount of MasterCard cards: " + mcCount);
        System.out.println("Total of all cards (ie. American Express):" + cards.size());
    }

}