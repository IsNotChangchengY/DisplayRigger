package org.displayRigger.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.displayRigger.common.GroupManager;

import java.util.List;

public class RemoveObjectFromGroupCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§c该命令只能由玩家执行！");
            return true;
        }
        if (args.length != 2) {
            sender.sendMessage("/dr removeobject <groupID> <objectID>");
            return true;
        }
        GroupManager.removeObjectFromGroup(args[0], args[1], player);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            String input = args[0].toLowerCase();
            return GroupManager.getGroups().stream()
                    .filter(name -> name.toLowerCase().startsWith(input))
                    .toList();
        }
        if (args.length == 2 && GroupManager.groupExist(args[0])) {
            String input = args[1].toLowerCase();
            return GroupManager.getObjects(args[0]).stream()
                    .filter(name -> name.toLowerCase().startsWith(input))
                    .toList();
        }
        return List.of();
    }
}