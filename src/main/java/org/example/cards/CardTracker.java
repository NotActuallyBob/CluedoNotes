package org.example.cards;

import org.example.CommandLine;
import org.example.player.IPlayerRegister;

import java.util.*;

public class CardTracker implements ICardTracker {
    private final IPlayerRegister playerRegistary;

    private final Deck deck;

    private Set<Card> tableCards;
    private Set<Card> ownCards;

    private final CommandLine commandLine;

    public CardTracker (CommandLine commandLine, IPlayerRegister playerRegistary, Deck deck) {
        this.playerRegistary = playerRegistary;
        this.commandLine = commandLine;
        this.deck = deck;

        init();
    }

    private void init() {
        this.tableCards = new HashSet<>();
        this.ownCards = new HashSet<>();

        initTableCards();
        initOwnCards();
        initPlayerCards();
    }

    private void initTableCards() {
        commandLine.println("Table cards..");

        int cardsOnTableCount = commandLine.promptForInt("table card count");
        for (int i = 0; i < cardsOnTableCount; i++) {
            tableCards.add(commandLine.promptForCard());
        }
    }

    private void initOwnCards() {
        commandLine.println("Own cards..");

        int ownCardCount = playerRegistary.getPlayer(playerRegistary.getOwnId()).getCardQuantity();
        for (int i = 0; i < ownCardCount; i++) {
            ownCards.add(commandLine.promptForCard());
        }
    }

    private void initPlayerCards() {
        for (int i = 0; i < playerRegistary.getPlayerCount(); i++) {
            playerRegistary.getPlayer(i).initCards(deck, tableCards, ownCards);
        }
    }
}
