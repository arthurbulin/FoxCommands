package jawamaster.foxcommands.commands;

import java.util.UUID;

import jawamaster.foxcommands.JSONHandler;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListHomes implements CommandExecutor{

	public boolean onCommand(CommandSender commandSender, Command arg1, String arg2, String[] arg3) {
		
		if (commandSender instanceof Player){
			Player player = (Player) commandSender;
			UUID uuid = player.getUniqueId();
			
			player.sendMessage(ChatColor.RED + " > This command is no longer valid. ");
			player.sendMessage(ChatColor.RED + " > Please type /home to see how the new command works." );
			return true;
			 /*
			if (player.hasPermission("jawamaster.foxcommands.home")){
				String homes = JSONHandler.listHomes(uuid);
				player.sendMessage("You have the following homes: " + homes);
			}
			else {
				player.sendMessage("You do not have permission to do that");
			}
			*/
		}
		
		else {
			System.out.println("not yet implemented for console");
		}
		return true;
	}

}
