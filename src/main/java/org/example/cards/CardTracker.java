package org.example.cards;

import org.example.Accusation;
import org.example.CommandLine;
import org.example.player.IPlayerRegister;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class CardTracker implements ICardTracker {
    Map<Integer, Integer> playerIdToCardCount;
    private final IPlayerRegister playerRegistary;

    private Deck deck;

    private Set<String> tableCards;
    private Map<Integer, Set<String>> playerIdToPossibleCards;
    private Map<Integer, Set<Set<String>>> playerIdToShows;
    private Map<Integer, Set<String>> playerIdToCards;

    private final CommandLine commandLine;

    public CardTracker (IPlayerRegister playerRegistary, Deck deck) {
        this.playerRegistary = playerRegistary;
        this.commandLine = CommandLine.getInstance();
        this.deck = deck;

        init();
    }

    private void init() {

        this.tableCards = new HashSet<>();
        this.playerIdToPossibleCards = new HashMap<>();
        this.playerIdToShows = new HashMap<>();
        this.playerIdToCards = new HashMap<>();

        initPlayerCardCounts();
        initTableCards();
        initPlayerCards();
        initOwnCards();
        initPossibleCards();
        initPossibleShows();
    }


    private void initPlayerCardCounts() {
        playerIdToCardCount = new HashMap<>();
        for (int i = 1; i < playerRegistary.getPlayerCount(); i++) {
            int cardCount = commandLine.promptForInt("card count for player " + playerRegistary.getPlayerName(i));
            playerIdToCardCount.put(i, cardCount);
        }
    }

    private void initTableCards() {
        int cardsOnTableCount = commandLine.promptForInt("table card count");
        for (int i = 0; i < cardsOnTableCount; i++) {
            tableCards.add(commandLine.promptForString((i + 1) + " table card name"));
        }
    }

    private void initOwnCards() {
        int ownCardCount = commandLine.promptForInt("own card count");
        playerIdToCardCount.put(playerRegistary.getOwnId(), ownCardCount);

        for (int i = 0; i < ownCardCount; i++) {
            String cardName = commandLine.promptForString((i + 1) + " own card name");
            addKnownCardToPlayer(playerRegistary.getOwnId(), cardName);
        }
    }
    private void initPossibleCards() {
        for (int i = 0; i < playerRegistary.getPlayerCount(); i++) {
            Set<String> possibleCards = deck.getAllCards();

            possibleCards.removeAll(playerIdToCards.get(playerRegistary.getOwnId()));
            possibleCards.removeAll(tableCards);
            playerIdToPossibleCards.put(i, new HashSet<>(possibleCards));
        }
    }

    private void initPossibleShows() {
        for (int i = 0; i < playerRegistary.getPlayerCount(); i++) {
            playerIdToShows.put(i, new HashSet<>());
        }
    }

    private void initPlayerCards() {
        for (int i = 0; i < playerRegistary.getPlayerCount(); i++) {
            playerIdToCards.put(i, new HashSet<>());
        }
    }

    @Override
    public void playerShowed(int playerId, String cardShowed) {
        addKnownCardToPlayer(playerId, cardShowed);
    }

    @Override
    public void playerShowed(int playerId, Accusation accusation) {
        Set<Set<String>> playerShows = playerIdToShows.get(playerId);

        Set<String> possibleCards = playerIdToPossibleCards.get(playerId);
        Set<String> newShow = new HashSet<>();

        if(possibleCards.contains(accusation.suspectCard())) {
            newShow.add(accusation.suspectCard());
        }

        if(possibleCards.contains(accusation.roomCard())) {
            newShow.add(accusation.roomCard());
        }

        if(possibleCards.contains(accusation.weaponCard())) {
            newShow.add(accusation.weaponCard());
        }

        if(newShow.size() == 1) {
            addKnownCardToPlayer(playerId, newShow.iterator().next());
            return;
        } else if (newShow.isEmpty()) {
            System.out.println("No possible cards for player to show");
        }

        playerShows.add(newShow);
    }

    @Override
    public void playerSkipped(int playerId, Accusation accusation) {
        Set<String> playerPossibleCards = playerIdToPossibleCards.get(playerId);
        playerPossibleCards.remove(accusation.suspectCard());
        playerPossibleCards.remove(accusation.roomCard());
        playerPossibleCards.remove(accusation.weaponCard());

        Set<Set<String>> playerShows = playerIdToShows.get(playerId);
        Set<String> newKnownCards = new HashSet<>();

        for(Set<String> possibleShownCards : playerShows) {
            possibleShownCards.remove(accusation.suspectCard());
            possibleShownCards.remove(accusation.roomCard());
            possibleShownCards.remove(accusation.weaponCard());

            if(possibleShownCards.size() == 1) {
                newKnownCards.add(possibleShownCards.iterator().next());
            }
        }

        for (String knownCard : newKnownCards) {
            addKnownCardToPlayer(playerId, knownCard);
        }
    }

    private void addKnownCardToPlayer(int playerId, String card) {
        Set<Set<String>> setOfPlayerShows = playerIdToShows.get(playerId);
        if(setOfPlayerShows != null) {
            Iterator<Set<String>> iterator = setOfPlayerShows.iterator();
            while(iterator.hasNext()) {
                Set<String> next = iterator.next();
                if(next.contains(card)) {
                    setOfPlayerShows.remove(next);
                }
            }
        }

        for (int i = 0; i < playerIdToPossibleCards.size(); i++) {
            removeCardFromPlayersPossibleCards(i, card);
        }

        playerIdToCards.get(playerId).add(card);
    }

    private void removeCardFromPlayersPossibleCards(int playerId, String card) {
        Set<String> possiblePlayerCards = playerIdToPossibleCards.get(playerId);
        possiblePlayerCards.remove(card);
        if(possiblePlayerCards.size() == playerIdToCardCount.get(playerId) - playerIdToCards.get(playerId).size()) {
            for (String cardToAdd : possiblePlayerCards) {
                addKnownCardToPlayer(playerId, cardToAdd);
            }
        }
    }

    public void printKnownCardsByPlayerId(int playerId) {
        Set<String> cards = playerIdToCards.get(playerId);
        System.out.println(playerRegistary.getPlayerName(playerId) + ":");
        for (String card : cards) {
            System.out.println("   " + card);
        }
    }
}
