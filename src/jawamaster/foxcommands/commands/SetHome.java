package jawamaster.foxcommands.commands;

import java.util.UUID;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import jawamaster.foxcommands.JSONHandler;

public class SetHome implements CommandExecutor{

	//@SuppressWarnings("unchecked")
	public boolean onCommand(CommandSender commandSender, Command cmd, String name, String[] arg3) {
		
		if (commandSender instanceof Player){
			Player player = (Player) commandSender;

			player.sendMessage(ChatColor.RED + " > This command is no longer valid. ");
			player.sendMessage(ChatColor.RED + " > Please type /home to see how the new command works." );
			return true;
			/*
			if (player.hasPermission("jawamaster.foxcommands.sethome")){
				UUID uuid = (UUID) player.getUniqueId();
				Location loc = player.getLocation();
				
				if (arg3.length == 0){
					player.sendMessage("Error: You need to specify a home to set!");
					player.sendMessage("/sethome <homename>");
					return true;
				}
					
				boolean duplicate = JSONHandler.addHome(uuid, arg3[0], loc);
				
				if (duplicate){
					player.sendMessage("That home is already in use.");
				} else {
					player.sendMessage(arg3[0] + " set!");
				}
				
			}
			else
				player.sendMessage("You do not have permission to excute that"); */
		}
		else 
			System.out.print("There really isn't a way to use this command from the console. Stop trying. -Jawa");
		return true;
	}
	

}
