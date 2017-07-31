package jawamaster.foxcommands.commands;

import jawamaster.foxcommands.JSONHandler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListWarps implements CommandExecutor {

	public boolean onCommand(CommandSender commandSender, Command arg1, String arg2, String[] arg3) {
		
		if (commandSender instanceof Player)
		JSONHandler.listWarps(((Player) commandSender).getUniqueId());
		else //TODO add console support
			System.out.println("Not yet implimented for whatever method you are using");
		
		//if (commandSender instanceof Player){
			//Player player = (Player) commandSender;
			//player.sendMessage("These are the available warps: " + list);
		//}
		//else {
		//	System.out.println("These warps are available: " + list);
			
		//}
			
		return true;
	}

}
