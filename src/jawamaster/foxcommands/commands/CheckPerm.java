package jawamaster.foxcommands.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



public class CheckPerm implements CommandExecutor {

	// this will return a message to the player taht will tell them if the target has a permission
	//The format should be /checkperm <user> <permission string>
	public boolean onCommand(CommandSender commandSender, Command cmd, String ply, String[] perm) {
		
		//If the sender is a player, if not tell the console user to bugger off
		if (commandSender instanceof Player){
			
			Player player =(Player) commandSender; //cast commandsender to player
			Player target = Bukkit.getServer().getPlayer(perm[0]); //cast string to player target
			
			if (target.hasPermission(perm[1])){ //if the target does have the permission print this stuff
				player.sendMessage(perm[0] + " HAS " + perm[1]);
				System.out.println(player + " checked permission " + perm[1] + " for player " + target);
			}
			else { //if they dont then do this
				player.sendMessage(perm[0] + " Does NOT have " + perm[1]);
			}
			
		}
		else { //tell the console to bugger off for now
			System.out.println("I haven't implemented console yet, bugger off. -Jawa");
		}
		
		return false;
	}
}
