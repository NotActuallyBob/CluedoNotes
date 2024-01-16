package org.example.player;

public interface IPlayerRegister {
    public String getPlayerName(int playerId);
    public Integer getPlayerId(String playerName);
    public int getPlayerCount();
    public int getOwnId();
}
