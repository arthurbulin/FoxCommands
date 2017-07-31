package jawamaster.foxcommands.commands;

import java.util.UUID;

import jawamaster.foxcommands.JSONHandler;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class TPW implements CommandExecutor {

	public boolean onCommand(CommandSender commandSender, Command cmd, String arg2, String[] arg3) {
		
		if (commandSender instanceof Player){
			Player player = (Player) commandSender;
			UUID uuid = player.getUniqueId();
			
			if (arg3.length == 0) {
				player.sendMessage("You must specify a world to tp to.");
				player.sendMessage("You can go to creative(c) or survival(s)");
				return true;
			}
			
			if (player.hasPermission("jawamaster.foxcommands.tpw")){
				
				String where;
				char fc = arg3[0].toLowerCase().charAt(0);
				//get player's current location
				Location lastloc = player.getLocation();
				
				//Find out where they want to go
				if (fc == 'c') {
					where = "creative";
				}
				else if (fc == 's') {
					where = "world";
				}
				else if (fc == 'g'){
					where = "games";
				}
				else{
					player.sendMessage(arg3[0] + " is not a valid world. Please choose either creative(c) or survival(s)");
					return true;
				}
				
				//get their last spot in that world
				Location toLoc = JSONHandler.getLastLocation(where, uuid);
				
				//send them to target world
				//System.out.println(toLoc);
				if (toLoc.getWorld() != lastloc.getWorld()) {
					player.teleport(toLoc, TeleportCause.COMMAND);
				} else {
					player.sendMessage("You cannot tpw to the world you are currently in");
					return true;
				}
				
				//set last spot in source world
				//JSONHandler.setLastLocation(uuid, lastloc);
				//System.out.println(lastloc);
				
			}
		}
		return true;
	}
}