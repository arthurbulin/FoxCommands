package jawamaster.foxcommands.commands;

import java.util.UUID;

import jawamaster.foxcommands.JSONHandler;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelHome implements CommandExecutor{

	public boolean onCommand(CommandSender commandSender, Command arg1, String arg2, String[] arg3) {
		if (commandSender instanceof Player){
			Player player = (Player) commandSender;
			UUID uuid = player.getUniqueId();
			
			player.sendMessage(ChatColor.RED + " > This command is no longer valid. ");
			player.sendMessage(ChatColor.RED + " > Please type /home to see how the new command works." );
			return true;
			
			/*
			if (player.hasPermission("jawamaster.foxcommands.home")){
				
				if (arg3.length > 0) {
					boolean del = JSONHandler.delHome(uuid, arg3[0]);
					if (!del)
						player.sendMessage("That home does not exist.");
					else
						player.sendMessage(arg3[0] + " has been removed from your home list.");
					return true;
				}
				else {
					player.sendMessage("You Need to specfy a home to delete");
					player.sendMessage("/delhome <home>");
					return true;
				}
				
			}
			else {
				player.sendMessage("You do not have permission to do that");
			} */
		}
		else {
			System.out.println("Not implemented for console");
		}
		
		return true;
	}
}
