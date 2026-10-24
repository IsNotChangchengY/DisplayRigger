package org.displayRigger.command;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.displayRigger.common.GroupGlowing;
import org.displayRigger.common.GroupManager;

import java.util.List;

public class GroupGlowingCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("/dr glowing <groupID>");
            return true;
        }
        String groupID = args[0];
        if (!GroupManager.groupExist(groupID)) {
            sender.sendMessage(ChatColor.RED + "组" + groupID + "不存在");
            return true;
        }
        if (sender instanceof Player player) {
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            GroupGlowing.setGlowing(groupID, player);
        } else {
            sender.sendMessage(ChatColor.YELLOW + "只能在游戏内使用");
            return true;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            return List.of();
        }
        String input = args[0];
        return GroupManager.getGroups().stream()
                .filter(groupID -> groupID.startsWith(input))
                .toList();
    }
}
