package jawamaster.foxcommands.commands;

import jawamaster.foxcommands.JawaFoxCommands;
import jawamaster.foxcommands.TPHandler;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TPA implements CommandExecutor {

	
	public boolean onCommand(CommandSender commandSender, Command arg1, String arg2, String[] arg3) {
		if (commandSender instanceof Player){
			Player player = (Player) commandSender;
			
			if (player.hasPermission("jawamaster.foxcommands.tpa")){
				if (arg3.length == 0) {
					player.sendMessage("You must specify a player!");
					return true;
				}

				final Player target = Bukkit.getPlayer(arg3[0]);

				if (target == null) {
					player.sendMessage("Error: You need to specify a player.");
					return false;
				}
				if (target == player) {
					player.sendMessage("You can't teleport to yourself!");
					return true;
				}

				TPHandler.tpRequest(0, player, target);

				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(JawaFoxCommands.getInstance(), new Runnable() {
					public void run() {
						TPHandler.killRequest(target);
					}
				}, 600);
			}	
		}
		return true;
	}
	

}
