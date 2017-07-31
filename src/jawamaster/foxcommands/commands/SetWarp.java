package jawamaster.foxcommands.commands;

import jawamaster.foxcommands.JSONHandler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permissible;

public class SetWarp implements CommandExecutor {

	public boolean onCommand(CommandSender commandSender, Command arg1, String arg2, String[] arg3) {

		boolean permission = false;
		
		if (commandSender instanceof Player) { //check for player instance
			//System.out.println("instance check");
			if (((Player) commandSender).hasPermission("jawamaster.foxcommands.setwarp") || ((Player) commandSender).getName().toString().contains("Jawamaster")) //if it is check for permission
				permission = true;

			else{ //Return the message if they don't have permission and short out the command
				((Player) commandSender).sendMessage(ChatColor.RED + " > You do not have permission to execute that.");
				//System.out.print(permission);
				return true; 
				}
		}

		//Proceed if they have permission
		if (!permission){
			System.out.println("Not sure what happened, this is a last ditch catch on the setwarp command");
			return true;
		}
		
		//System.out.print("proceeding");
		if (arg3.length == 0) { //if command issued with no arguments
			if (commandSender instanceof Player) {
				((Player) commandSender).sendMessage("You must specify arguments.");
				((Player) commandSender).sendMessage("/setwarp <name> ");
			}
			else {
				System.out.println("You must specify arguments.");
			}
			return true;
		}

		else if ((arg3.length == 2) && arg3[1].contains("location") && (commandSender instanceof Player)){ //if command is issued as set here
			Location setLoc = ((Player) commandSender).getLocation();
			boolean warpadded = (boolean) JSONHandler.addWarp(setLoc, arg3[0]);

			if (warpadded)
				((Player) commandSender).sendMessage(ChatColor.GREEN + " > " + arg3[0] + " has been added to the warplist.");
			else
				((Player) commandSender).sendMessage(ChatColor.RED + " > " + arg3[0] + " already exists!");	
		}

		//check to see if it is a multiargument command
		else if (arg3.length >= 3) {
			String warpName = arg3[0]; //set the warpName
			String property = arg3[1]; //get what we are doing to the warp
			//TODO set auth discrimination for bools
			//Modify the properties
			if (property.contains("hidden") || property.contains("private")) {
				String auth = arg3[2];
				int p;

				if (property.contains("hidden"))
					p = 0; //0 is hidden
				else if (property.contains("private"))
					p = 1;
				else {
					((Player) commandSender).sendMessage(ChatColor.RED + " > Your property specification is wrong.");
					return true;
				}

				//TODO something wrong withthe getBoolean
				if (!auth.contains("true") && !auth.contains("false")){
					if (commandSender instanceof Player)
						((Player) commandSender).sendMessage(ChatColor.RED + " > Invalid boolean. true or false only");
					else
						System.out.println("Invalid boolean");
					
					return true;
				}
				
				boolean propset = JSONHandler.setProperty(warpName, p, Boolean.valueOf(auth));
				
				if (propset) {
					if (commandSender instanceof Player){
						((Player) commandSender).sendMessage(ChatColor.GREEN + " > " + warpName + " has been set to " + property + ": " + auth);
						return true;
					}
					else {
						System.out.println(warpName + " has been set " + property + ": " + auth);
						return true;
					}
				}
			}
			
			//adjust member auths
			else if (property.contains("addmember")){

				try{
					Player user = Bukkit.getPlayer(arg3[2]);
					JSONHandler.userModify(user.getUniqueId(), warpName, true, arg3[3]);
					
					String message = arg3[2] + " has been added to " + warpName;
					
					if (commandSender instanceof Player){
						((Player) commandSender).sendMessage(ChatColor.GREEN + " > " + message);
					}
					else {
						System.out.println(message);
					}
				} catch (Exception e){
					e.printStackTrace();
					if (commandSender instanceof Player) {
					((Player) commandSender).sendMessage(ChatColor.RED + " > Invalid player");
					} else {
						System.out.println("Attempting to add an invalid player to warp");
					}
					return true;
				}
				
			}
			
			//remove permissions
			else if (property.contains("removemember")){
				
				try{
					//System.out.println(arg3[2]);
					Player user = Bukkit.getPlayer(arg3[2]);
					//System.out.println("Player");
					JSONHandler.userModify(user.getUniqueId(), warpName, false, arg3[3]);
					
					String message = arg3[2] + " has been removed from " + warpName;
					
					if (commandSender instanceof Player){
						((Player) commandSender).sendMessage(ChatColor.GREEN + " > " + message);
					}
					else {
						System.out.println(message);
					}
				} catch (Exception e){
					if (commandSender instanceof Player) {
					((Player) commandSender).sendMessage(ChatColor.RED + " > Invalid player");
					} else {
						System.out.println("Attempting to add an invalid player to warp");
					}
					return true;
				}
			}
			
			else {
				((Player) commandSender).sendMessage(ChatColor.RED + " > Your property specification is invalid. Please check your syntax.");
				return true;
			}
		}
		else {
			((Player) commandSender).sendMessage("Invalid arguments");
		}



		return true;
	}

}
