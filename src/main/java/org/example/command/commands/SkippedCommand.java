package org.example.command.commands;

import org.example.Game;
import org.example.command.ICommand;

public class SkippedCommand implements ICommand {
    @Override
    public void execute() {
        Game.getInstance().skipped();
    }
}
