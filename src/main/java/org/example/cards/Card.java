package org.example.cards;

import java.util.Objects;

public class Card {
    private final String cardName;
    private final CardType cardType;

    public Card(String cardName, CardType cardType) {
        this.cardName = cardName;
        this.cardType = cardType;
    }

    public String getCardName() {
        return cardName;
    }

    public CardType getCardType() {
        return cardType;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Card card = (Card) object;
        return Objects.equals(cardName, card.cardName) && cardType == card.cardType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardName, cardType);
    }
}
