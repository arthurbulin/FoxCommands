package jawamaster.foxcommands.commands;

import jawamaster.foxcommands.JSONHandler;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class Warp implements CommandExecutor {

	public boolean onCommand(CommandSender commandSender, Command arg1, String arg2, String[] arg3) {
		if (commandSender instanceof Player){
			Player player = (Player) commandSender;
			
			if (arg3.length == 0){
				player.sendMessage(ChatColor.RED + " > You must specify a warp! /listwarps to see the available ones!");
				return true;
			}
				
			if (player.hasPermission("jawamaster.foxcommands.warp") && (arg3.length == 1)){

				//Check for warp existance and short circuit if it doesn't exist
				if (!JSONHandler.warpExists(arg3[0])) {
					player.sendMessage(ChatColor.RED + " > " + arg3[0] + " is not a valid warp location.");
					return true;
				}

				//This will allow specific users to be denied permission
				//get user permission
				boolean warpPerm = JSONHandler.userHasPermission(player.getUniqueId(), arg3[0], "use");
				boolean warpHidden = JSONHandler.getProperty(arg3[0], "hidden");
				
				//if they have the perm, just do it. If they have set perm give them global tp
				if (player.hasPermission("jawamaster.foxcommands.setwarp") || warpPerm) {
					warpPlayer(player, arg3[0]);
				}
				else{
					player.sendMessage(ChatColor.RED + " > You do not have permission to access that warp.");
					return true;
				}



			}

			//This is used in the instance if a player attempting to tp another player
			else if (player.hasPermission("jawamaster.foxcommands.warpother") && arg3.length >= 2){
				String warp = arg3[0];
				Player target = Bukkit.getPlayer(arg3[1]);
				//Sets the default override
				boolean override = false;
				
				//Determines if the override is in place. If not there are is no override if the target doesn't have permission for that warp
				if (arg3.length == 3){
					if (arg3[2].contains("override")){
						override = true;
					}
				}

				//Warp the target player if they have the permission or override is specified
				if (JSONHandler.userHasPermission(target.getUniqueId(), warp, "use") || override){
					warpPlayer(target, warp); //Warp the target
					player.sendMessage(ChatColor.GREEN + " > Warping " + target.getName() + " to " + warp); //Send a message to the one doing the warping
				}
			}
			else { //If no permissions or something wierd happens they are told they don't have permission
				player.sendMessage(ChatColor.RED + " > You do not have permission to use this command!");
			}
		}
		
		
		else if (!(commandSender instanceof Player)){
			boolean override = false;
			Player target = Bukkit.getPlayer(arg3[1]);
			boolean hasPerm = false;
	
			if (arg3.length == 3) {
				if (arg3[2].contains("override")) {
					override = true;
				}
			}
			
			if (!override) {
				hasPerm = JSONHandler.userHasPermission(target.getUniqueId(), arg3[1], "use");
			}
			else {
				hasPerm = true;
			}
			
			if (hasPerm || override) {
				warpPlayer(target, arg3[0]);
			}
		}
		
		else {
			System.out.println("not implemented for console yet");
		}
		
		return true;
	}
	
	private void warpPlayer(Player player, String warp){
		
		Location warpLoc = JSONHandler.getWarpLoc(warp);
		
		player.sendMessage(ChatColor.GREEN + " > Warping you to " + warp);
		player.teleport(warpLoc, TeleportCause.COMMAND);
		
	}

}
