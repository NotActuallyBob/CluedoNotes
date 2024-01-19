package org.example.cards;

import org.example.Accusation;

public interface ICardTracker {
    public boolean cardExistsByName(String cardName);
    public void playerShowed(int playerId, String cardShowed);

    void playerShowed(int playerId, Accusation accusation);

    public void playerSkipped(int playerId, Accusation accusation);
    public void printKnownCardsByPlayerId(int playerId);
}
