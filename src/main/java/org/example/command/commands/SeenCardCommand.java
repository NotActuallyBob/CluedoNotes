package org.example.command.commands;

import org.example.Game;
import org.example.command.ICommand;

public class SeenCardCommand implements ICommand {

    @Override
    public void execute() {
        Game.getInstance().seenCard();
    }
}
