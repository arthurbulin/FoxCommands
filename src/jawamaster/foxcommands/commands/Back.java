package jawamaster.foxcommands.commands;

import java.util.UUID;

import jawamaster.foxcommands.JSONHandler;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class Back implements CommandExecutor{

	public boolean onCommand(CommandSender commandSender, Command arg1, String arg2, String[] arg3) {
		
		if (commandSender instanceof Player){
			Player player = (Player) commandSender;
			UUID uuid = player.getUniqueId();
			
			//Check user permissions
			if (player.hasPermission("jawamaster.foxcommands.back")){
				//Get the user's last back location
				Location loc = JSONHandler.getBackLocation(uuid);
				
				//if the returned location is null report to the user that there are no back locations
				if (loc == null){
					player.sendMessage(ChatColor.RED + " > You have no back locations!");
					return true;
				}
				
				JSONHandler.removeBackLocation(uuid);
				player.sendMessage(ChatColor.DARK_GREEN + "Going back!");
				player.teleport(loc, TeleportCause.UNKNOWN);
			}
			else {
				player.sendMessage(ChatColor.RED + " > You do not have permission to do that.");
			}
		}
		return true;
	}

}
