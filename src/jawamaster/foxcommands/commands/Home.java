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

public class Home implements CommandExecutor{

	public boolean onCommand(CommandSender commandSender, Command cmd, String arg2, String[] arg3) {
		if (commandSender instanceof Player){
			Player player = (Player) commandSender;
			UUID uuid = player.getUniqueId();

			if (player.hasPermission("jawamaster.foxcommands.home")){ //Perm check
				if (arg3.length > 0) { //if the arg isnt empy
						
					//List homes
					if ((arg3[0].length() == 1) && (arg3[0].charAt(0) == 'l')) {
						//System.out.println("test2");
						String homes = JSONHandler.listHomes(uuid);
						player.sendMessage(ChatColor.GREEN + " > You have set the following homes: " + ChatColor.GOLD + homes);
					} 
					
					//Delete homes
					else if ((arg3[0].length() == 1) && (arg3[0].charAt(0) == 'd')) {
						if (arg3.length > 1) {
							boolean del = JSONHandler.delHome(uuid, arg3[1]);
							if (!del)
								player.sendMessage(ChatColor.RED + " > That home does not exist.");
							else
								player.sendMessage(ChatColor.GREEN + " > " + arg3[1] + " has been removed from your home list.");
							return true;
						}
						else {
							player.sendMessage(ChatColor.RED + " > You Need to specfy a home to delete");
							return true;
						}
					}
					
					//Set homes
					else if ((arg3[0].length() == 1) && (arg3[0].charAt(0) == 's')) {
						
						//This will allow us to keep  player from setting home in worlds where we dont give them explicit permission
						if (player.hasPermission("jawamaster.foxcommands.sethome")){
							Location loc = player.getLocation();
							
							if (arg3.length < 2){
								player.sendMessage(ChatColor.RED + " > You need to specify a home to set!");
								return true;
							}
								
							boolean duplicate = JSONHandler.addHome(uuid, arg3[1], loc);
							
							if (duplicate)
								player.sendMessage(ChatColor.RED + " > That home is already in use.");
							else 
								player.sendMessage(ChatColor.GREEN + " > " + arg3[1] + " set!");
						}
						else
							player.sendMessage(ChatColor.RED + " > You do not have permission to excute that here.");
						
					}
					else {
						//Get the location on the name
						//System.out.println("test3");
						Location loc = (Location) JSONHandler.getHome(uuid, arg3[0]);
						if (loc != null){
							player.teleport(loc); //send the player
						}
						else {
							player.sendMessage(ChatColor.RED + " > " + arg3[0] + " has not been set as a home!");
						}
						//player.sendMessage(ChatColor.RED + " > Invalid arguments.");
						///player.sendMessage(ChatColor.GREEN + "You must specify an appropriate argument or home name!");
						//player.sendMessage(ChatColor.GREEN + " > d <homename> - Delete a home");
						//player.sendMessage(ChatColor.GREEN + " > l - List available homes.");
						//player.sendMessage(ChatColor.GREEN + " > s <homename> - Set a new homename");
					}
				}
				else {
					//Get the default home location
					//System.out.println("test1");
					//Location loc = (Location) JSONHandler.getHome(uuid, "home");
					//player.teleport(loc, TeleportCause.COMMAND); //send the player
					player.sendMessage(ChatColor.GREEN + "You must specify an argument or home name!");
					player.sendMessage(ChatColor.GREEN + " > d <homename> - Delete a home");
					player.sendMessage(ChatColor.GREEN + " > l - List available homes.");
					player.sendMessage(ChatColor.GREEN + " > s <homename> - Set a new homename");
				}
			}
				
			else {
				player.sendMessage(ChatColor.RED + " > You do not have permission to do that");
			}
			
			
		} else {
			commandSender.sendMessage("You aren't a player, you can't bloody set a home you twat. -Jawa");
		}
		return true;
	}

}
