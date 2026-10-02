package org.displayRigger.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.displayRigger.common.AddObject;

import java.util.List;

public class AddObjectToCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 2) {
            sender.sendMessage("/dr add <groupID> <objectID>");
            return true;
        }
        AddObject.objectAddConfirm(args[0], args[1]);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return List.of();
    }
}
