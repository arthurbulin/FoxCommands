package jawamaster.foxcommands.commands;

import jawamaster.foxcommands.JawaFoxCommands;
import jawamaster.foxcommands.TPHandler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SummonA implements CommandExecutor {

	public boolean onCommand(CommandSender commandSender, Command arg1, String arg2, String[] arg3) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;
			

			
			if (player.hasPermission("jawamaster.foxcommands.summona")){
				
				if (arg3.length == 0) {
					player.sendMessage("You must specify a player to summon");
					return true;
				}
				
				final Player target = Bukkit.getServer().getPlayer(arg3[0]);
				TPHandler.tpRequest(1, player, target);

				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(JawaFoxCommands.getInstance(), new Runnable() {
					public void run() {
						TPHandler.killRequest(target);
					}
				}, 600);
			}
			else {
				player.sendMessage(ChatColor.RED + "You do not have permission to execute that command");
				return true;
			}
		}
		return true;
	}

}
