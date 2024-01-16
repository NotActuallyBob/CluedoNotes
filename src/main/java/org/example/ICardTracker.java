package org.example;

public interface ICardTracker {
    public boolean cardExistsByName(String cardName);
    public void playerShowed(int playerId, String cardShowed);
    public void playerShowed(int playerId, String suspectCard, String roomCard, String weaponCard);
    public void playerSkipped(int playerId, String suspectCard, String roomCard, String weaponCard);
}
