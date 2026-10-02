package org.displayRigger.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.List;
import java.util.Map;

public class DrCommand implements TabExecutor {

    private final Map<String, TabExecutor> subCommands = Map.of(
        "tool", new GetEditingStickCommand(),
        "addgroup", new AddGroupCommand(),
        "addobject", new AddObjectToCommand()
    );

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§c用法: /dr <子命令>");
            return true;
        }

        TabExecutor executor = subCommands.get(args[0].toLowerCase());
        if (executor == null) {
            sender.sendMessage("§c未知子命令: " + args[0]);
            return true;
        }

        String[] subArgs = new String[args.length - 1];
        System.arraycopy(args, 1, subArgs, 0, subArgs.length);
        return executor.onCommand(sender, command, label + " " + args[0], subArgs);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            return subCommands.keySet().stream()
                    .toList();
        }

        TabExecutor executor = subCommands.get(args[0].toLowerCase());
        if (executor != null) {
            String[] subArgs = new String[args.length - 1];
            System.arraycopy(args, 1, subArgs, 0, subArgs.length);
            return executor.onTabComplete(sender, command, label + " " + args[0], subArgs);
        }
        return List.of();
    }
}