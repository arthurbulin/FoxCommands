package jawamaster.foxcommands.commands;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class Spawn implements CommandExecutor{

	public boolean onCommand(CommandSender commandSender, Command arg1, String arg2, String[] arg3) {
		if (commandSender instanceof Player){
			Player player = (Player) commandSender;
			if (player.hasPermission("jawamaster.foxcommands.spawn")){
				player.teleport(player.getWorld().getSpawnLocation(), TeleportCause.COMMAND);
				player.sendMessage("Sending you to current world spawn");
				return true;
			} else{
				player.sendMessage(ChatColor.RED + "you do not have permission to do that.");
				return true;
			}
		}
		else {
			System.out.println("Its a fucking console you twat");
			return true;
		}
	}
	

}
