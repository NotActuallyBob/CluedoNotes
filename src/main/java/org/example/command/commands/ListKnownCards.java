package org.example.command.commands;

import org.example.Game;
import org.example.command.ICommand;

public class ListKnownCards implements ICommand {

    @Override
    public void execute() {
        Game.getInstance().listKnownCards();
    }
}
