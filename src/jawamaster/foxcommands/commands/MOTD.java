package jawamaster.foxcommands.commands;

import jawamaster.foxcommands.JSONHandler;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MOTD implements CommandExecutor {

	public boolean onCommand(CommandSender commandSender, Command arg1, String arg2, String[] arg3) {
		if (commandSender instanceof Player){
			Player player = (Player) commandSender;
			
			if (player.hasPermission("jawamaster.foxcommands.motd")){
				
				//player.sendMessage(arg3[0]);
				
				if (arg3.length == 0) {
					JSONHandler.runMOTD(player);
					return true;
				}
				
				if (player.hasPermission("jawamaster.foxcommands.motd.edit")) {
					if (arg3[0].contains("add")){
						int priority;
						
						if (arg3[1].contains("p")){
							priority = 0;
						}
						else if (arg3[1].contains("s")){
							priority = 1;
						}
						else{
							player.sendMessage("Please specify priority as p or s.");
							return true;
						}
						
						String motd = arg3[3];
						
						for (int i = 4; i < arg3.length; i++) {
							motd = motd + " " + arg3[i];
						}
					
						//player.sendMessage(arg3[2]);
						//player.sendMessage(motd);
						//player.sendMessage(Integer.toString(priority));
						boolean added = JSONHandler.addMOTD(arg3[2], motd, priority);
						
						if (!added) {
							player.sendMessage("Error: Something went wrong!");
						}
						else {
							player.sendMessage("MOTD added.");
							return true;
						}
					}
					
					else if (arg3[0].contains("remove")) {
						boolean removed = JSONHandler.removeMOTD(arg3[1]);
						if (!removed) {
							player.sendMessage(arg3[1] + " not removed! Something went wrong!");
						}
						else {
							player.sendMessage(ChatColor.GREEN + arg3[1] + " removed from motd list!");
						}
						return true;
					}
					
					else if (arg3[0].contains("list")) {
						JSONHandler.listMOTD(player);
						return true;
					}
					
					else if (arg3[0].contains("priority")) {
						//TODO adjust priority of motd messages
					}
					
					else {
						player.sendMessage("Arguments incorrect");
						player.sendMessage("Arguments are: add, remove, list, priority");
						return true;
					}	
				}
				else {
					player.sendMessage(ChatColor.RED + "You do not have permission to do that.");
				}
				
				
			}
			
		}
		else{
			System.out.println("Not impemented for the console yet");
			return true;
		}
		
		return true;
	}
	
	public static void runMotd(){
		
	}

}
