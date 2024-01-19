package org.example.cards;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Deck {
    private Set<String> allCards;
    private Set<String> allRoomCards;
    private Set<String> allWeaponCards;
    private Set<String> allSuspectCards;

    public Deck() {
        this.allCards = new HashSet<>();
        this.allSuspectCards = new HashSet<>();
        this.allRoomCards = new HashSet<>();
        this.allWeaponCards = new HashSet<>();

        try {
            readCardType("/en_weapons.txt", allWeaponCards);
            readCardType("/en_suspects.txt", allSuspectCards);
            readCardType("/en_rooms.txt", allRoomCards);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readCardType(String path, Set<String> map) throws IOException {
        InputStream is = getClass().getResourceAsStream(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line = reader.readLine();

        while (line != null) {
            line = line.toLowerCase();
            allCards.add(line);
            map.add(line);
            line = reader.readLine();
        }
    }

    public boolean cardExistsByName(String card) {
        return allCards.contains(card);
    }

    public Set<String> getAllCards() {
        return new HashSet<>(allCards);
    }

    public Set<String> getAllSuspectCards() {
        return new HashSet<>(allSuspectCards);
    }
    public Set<String> getAllRoomCards() {
        return new HashSet<>(allRoomCards);
    }
    public Set<String> getAllWeaponCardsCards() {
        return new HashSet<>(allWeaponCards);
    }

}
