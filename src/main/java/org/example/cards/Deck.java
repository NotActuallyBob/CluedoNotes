package org.example.cards;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class Deck {
    private final Set<Card> allCards;
    private final Set<Card> allRoomCards;
    private final Set<Card> allWeaponCards;
    private final Set<Card> allSuspectCards;

    public Deck() {
        this.allCards = new HashSet<>();
        this.allSuspectCards = new HashSet<>();
        this.allRoomCards = new HashSet<>();
        this.allWeaponCards = new HashSet<>();

        try {
            readCardsFromFile("/en_suspects.txt", CardType.Suspect, allSuspectCards);
            readCardsFromFile("/en_rooms.txt", CardType.Room, allRoomCards);
            readCardsFromFile("/en_weapons.txt", CardType.Weapon, allWeaponCards);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readCardsFromFile(String path, CardType cardType, Set<Card> map) throws IOException {
        InputStream is = getClass().getResourceAsStream(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String cardName = reader.readLine();

        while (cardName != null) {
            cardName = cardName.toLowerCase();

            Card newCard = new Card(cardName, cardType);

            allCards.add(newCard);
            map.add(newCard);
            cardName = reader.readLine();
        }
    }

    public boolean contains(Card card) {
        return allCards.contains(card);
    }

    public boolean containsCardByName(String cardName) {
        for (Card card : allCards) {
            if(Objects.equals(card.getCardName(), cardName)) {
                return true;
            }
        }
        return false;
    }

    public Optional<Card> getCardByName(String name) {
        for (Card card : allCards){
            if(Objects.equals(card.getCardName(), name)) {
                return Optional.of(card);
            }
        }
        return Optional.empty();
    }

    public boolean contains(String cardName, CardType cardType) {
        switch (cardType) {
            case Suspect:
                for (Card card : allSuspectCards) {
                    if(Objects.equals(card.getCardName(), cardName)) {
                        return true;
                    }
                }
                break;
            case Room:
                for (Card card : allRoomCards) {
                    if(Objects.equals(card.getCardName(), cardName)) {
                        return true;
                    }
                }
                break;
            case Weapon:
                for (Card card : allWeaponCards) {
                    if(Objects.equals(card.getCardName(), cardName)) {
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    public Set<Card> getAllCards() {
        return new HashSet<>(allCards);
    }

    public Set<Card> getAllSuspectCards() {
        return new HashSet<>(allSuspectCards);
    }
    public Set<Card> getAllRoomCards() {
        return new HashSet<>(allRoomCards);
    }
    public Set<Card> getAllWeaponCardsCards() {
        return new HashSet<>(allWeaponCards);
    }

}
