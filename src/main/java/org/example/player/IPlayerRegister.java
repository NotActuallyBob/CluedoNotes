package org.example.player;

public interface IPlayerRegister {
    public String getPlayerName(int playerId);
    public Integer getPlayerId(String playerName);
    public Player getPlayer(int playerId);
    public int getPlayerCount();
    public int getOwnId();
}
