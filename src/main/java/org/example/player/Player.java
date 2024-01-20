package org.example.player;

import org.example.cards.Card;
import org.example.cards.Deck;

import java.util.HashSet;
import java.util.Set;

public class Player {
    private boolean isOwn = false;
    private final int id;
    private final String name;
    private int cardQuantity;
    private Set<Card> knownCards;
    private Set<Card> possibleCards;

    public Player(int id, String name, int cardQuantity) {
        this.id = id;
        this.name = name;
        this.cardQuantity = cardQuantity;

        if(this.id == 0) {
            this.isOwn = true;
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void initCards(Deck deck, Set<Card> tableCards, Set<Card> ownCards) {
        if(isOwn) {
            possibleCards = new HashSet<>();
            knownCards = new HashSet<>(ownCards);
        } else {
            possibleCards = deck.getAllCards();
            possibleCards.removeAll(tableCards);
            possibleCards.removeAll(ownCards);

            knownCards = new HashSet<>();
        }
    }

    public boolean isCardPossible(Card card) {
        return possibleCards.contains(card);
    }

    public void makeCardImpossible(Card card) {
        possibleCards.remove(card);
    }

    public boolean hasCard(Card card) {
        return knownCards.contains(card);
    }

    public void makeCardKnown(Card card) {
        possibleCards.remove(card);
        knownCards.add(card);
    }

    public int getKnownCardQuantity() {
        return knownCards.size();
    }
    public int getCardQuantity() {
        return cardQuantity;
    }
}
