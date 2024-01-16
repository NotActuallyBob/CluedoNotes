package org.example;

import org.example.player.IPlayerRegistary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class CardTracker implements ICardTracker {
    Map<Integer, Integer> idToCardCount;
    private final IPlayerRegistary playerRegistary;
    private Set<String> allCards;
    private Set<String> allRoomCards;
    private Set<String> allWeaponCards;
    private Set<String> allSuspectCards;

    private Set<String> tableCards;
    private Map<Integer, Set<String>> playerIdToPossibleCards;
    private Map<Integer, Set<Set<String>>> playerIdToShows;
    private Map<Integer, Set<String>> playerIdToCards;

    private final CommandLine commandLine;

    public CardTracker (IPlayerRegistary playerRegistary) {
        this.playerRegistary = playerRegistary;
        this.commandLine = CommandLine.getInstance();

        init();
    }

    private void init() {
        this.allCards = new HashSet<>();
        this.allRoomCards = new HashSet<>();
        this.allWeaponCards = new HashSet<>();
        this.allSuspectCards = new HashSet<>();
        this.tableCards = new HashSet<>();
        this.playerIdToPossibleCards = new HashMap<>();
        this.playerIdToShows = new HashMap<>();
        this.playerIdToCards = new HashMap<>();

        try {
            readCardType("/weapons.txt", allWeaponCards);
            readCardType("/suspects.txt", allSuspectCards);
            readCardType("/rooms.txt", allRoomCards);
            int a= 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        initPlayerCardCounts();
        initTableCards();
        initOwnCards();
        initPossibleCards();
        initPossibleShows();
        initPlayerCards();
    }


    private void initPlayerCardCounts() {
        idToCardCount = new HashMap<>();
        for (int i = 1; i < playerRegistary.getPlayerCount(); i++) {
            int cardCount = commandLine.promptForInt("card count for player " + playerRegistary.getPlayerName(i));
            idToCardCount.put(i, cardCount);
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
        for (int i = 0; i < ownCardCount; i++) {
            String cardName = commandLine.promptForString((i + 1) + " own card name");
            addKnownCardToPlayer(playerRegistary.getOwnId(), cardName);
        }
    }
    private void initPossibleCards() {
        for (int i = 0; i < playerRegistary.getPlayerCount(); i++) {
            Set<String> possibleCards = new HashSet<>(allCards);

            possibleCards.removeAll(playerIdToCards.get(playerRegistary.getOwnId()));
            possibleCards.removeAll(tableCards);
            playerIdToPossibleCards.put(i, possibleCards);
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

    private void readCardType(String path, Set<String> map) throws IOException {
        InputStream is = getClass().getResourceAsStream(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line = reader.readLine();

        while (line != null) {
            allCards.add(line);
            map.add(line);
            line = reader.readLine();
        }
    }

    @Override
    public boolean cardExistsByName(String cardName) {
        return allCards.contains(cardName);
    }

    @Override
    public void playerShowed(int playerId, String cardShowed) {
        addKnownCardToPlayer(playerId, cardShowed);
    }

    @Override
    public void playerShowed(int playerId, String suspectCard, String roomCard, String weaponCard) {
        Set<Set<String>> playerShows = playerIdToShows.get(playerId);

        Set<String> possibleCards = playerIdToPossibleCards.get(playerId);
        Set<String> newShow = new HashSet<>();

        if(possibleCards.contains(suspectCard)) {
            newShow.add(suspectCard);
        }

        if(possibleCards.contains(roomCard)) {
            newShow.add(roomCard);
        }

        if(possibleCards.contains(weaponCard)) {
            newShow.add(weaponCard);
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
    public void playerSkipped(int playerId, String suspectCard, String roomCard, String weaponCard) {
        Set<String> playerPossibleCards = playerIdToPossibleCards.get(playerId);
        playerPossibleCards.remove(suspectCard);
        playerPossibleCards.remove(roomCard);
        playerPossibleCards.remove(weaponCard);

        Set<Set<String>> playerShows = playerIdToShows.get(playerId);
        Set<String> newKnownCards = new HashSet<>();

        for(Set<String> possibleShownCards : playerShows) {
            possibleShownCards.remove(suspectCard);
            possibleShownCards.remove(roomCard);
            possibleShownCards.remove(weaponCard);

            if(possibleShownCards.size() == 1) {
                newKnownCards.add(possibleShownCards.iterator().next());
            }
        }

        for (String knownCard : newKnownCards) {
            addKnownCardToPlayer(playerId, knownCard);
        }
    }

    private void addKnownCardToPlayer(int playerId, String card) {
        Set<Set<String>> playerShows = playerIdToShows.get(playerId);
        Iterator<Set<String>> iterator = playerShows.iterator();
        while(iterator.hasNext()) {
            Set<String> next = iterator.next();
            if(next.contains(card)) {
                playerShows.remove(next);
            }
        }

        for (int i = 0; i < playerIdToPossibleCards.size(); i++) {
            if(i == playerId) {
                continue;
            }

            Set<String> possibleCards = playerIdToPossibleCards.get(i);
            possibleCards.remove(card);
        }

        playerIdToCards.get(playerId).add(card);
    }
}
