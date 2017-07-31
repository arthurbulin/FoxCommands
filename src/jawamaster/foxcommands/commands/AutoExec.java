package jawamaster.foxcommands.commands;


import java.util.UUID;

import jawamaster.foxcommands.JSONHandler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AutoExec implements CommandExecutor{

	public boolean onCommand(CommandSender commandSender, Command arg1, String arg2, String[] arg3) {
		
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;
			UUID uuid = player.getUniqueId();
			
			if (player.hasPermission("jawamaster.foxcommands.autoexec")){

				if (arg3[0] == "init") { //So i can use the same command for exec init message
					player.sendMessage("AutoExec initialized");
					return true;
				}
				else { //Adds the autoexec section to the player JSON
					JSONHandler.initAutoExec(uuid);
				}

				if (arg3.length == 0) {//if no args return instructions
					player.sendMessage("You must specify a function: set, remove, list");
					return true;
				}
				else if (arg3[0] == "set") { 
					//set new exec in the format as follows
					// /autoexec set c <command> 
					
					if (arg3.length < 4) { //Make sure they give enough data
						player.sendMessage("You must specify command type and command");
						player.sendMessage("/autoexec set <name> <c/m> <command and arguments>");
						return true;						
					}
					
					String name = arg3[1];
					String type = arg3[2];
					
					if (!(type == "c") || !(type == "m")){ //make sure what they give is either a command or message
						player.sendMessage("Invalid type! Must be either command(c) or message(m)");
						return true;
					}
					
					//Assemble a usable command string
					String commandString = arg3[3]; //set the first value
					for (int i=4; i < arg3.length; i++){ //Iterate over the string[] for the needed strings
						commandString = commandString + " " + arg3[i]; //Actually add them on
						
					}
					
					
					return true;
				}
				else if (arg3[0] == "list") {
					//TODO arg list
					return true;
				}
				else if (arg3[0] == "remove") {
					//TODO arg remove
					return true;
				}
				else {
					player.sendMessage("Incorrect usage, please run /autoexec to see usage instructions");
					return true;
				}
			}
		}
		else {
			System.out.println("No, stop that, that doesnt even make sense");
			return true;
		}
		return true;
	}

}
