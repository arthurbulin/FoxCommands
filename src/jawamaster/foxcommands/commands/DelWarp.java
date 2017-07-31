package jawamaster.foxcommands.commands;

import jawamaster.foxcommands.JSONHandler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelWarp implements CommandExecutor {

	public boolean onCommand(CommandSender commandSender, Command arg1, String arg2, String[] arg3) {
		
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;
			if (arg3.length == 0) {
				commandSender.sendMessage("You must specify a home to delete");
				return true;
			}
		
			if (player.hasPermission("jawamaster.foxcommands.delwarp")) {
			
				boolean deleted = JSONHandler.delWarp(arg3[0]);
			
				if (deleted) {
					player.sendMessage(arg3[0] + " has been removed!");
				}
				else {
					player.sendMessage(arg3[0] + " is not a valid warp");
				}
				return true;
			}
		}
		return true;
	}

} 