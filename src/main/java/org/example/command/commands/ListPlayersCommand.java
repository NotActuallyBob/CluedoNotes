package org.example.command.commands;

import org.example.Game;
import org.example.command.Command;

public class ListPlayersCommand extends Command {

    @Override
    public void execute() {
        Game.getInstance().listAllPlayers();
    }
}
