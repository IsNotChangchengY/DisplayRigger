package org.displayRigger.command;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.displayRigger.common.EditingStick;

import java.util.List;

public class GetEditingStickCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            player.getInventory().addItem(EditingStick.generateEditingStick());
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
        } else {
            sender.sendMessage("§c该命令只能由玩家执行！");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return List.of();
    }
}