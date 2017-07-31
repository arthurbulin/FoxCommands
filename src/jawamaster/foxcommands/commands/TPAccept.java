package jawamaster.foxcommands.commands;

import jawamaster.foxcommands.TPHandler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TPAccept implements CommandExecutor{

	public boolean onCommand(CommandSender commandSender, Command arg1, String arg2, String[] arg3) {
		if (commandSender instanceof Player){
			Player player = (Player) commandSender;
			if (player.hasPermission("jawamaster.foxcommands.tpaccept")){
				
				boolean accepted = TPHandler.tpAccept(player);
				
				if (!accepted){
					player.sendMessage("There is no pending TP request. It may have timed out.");
					return true;
				}
			}
			else
				player.sendMessage("No permissions!");
		}
		return true;
	}

}
