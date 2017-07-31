package jawamaster.foxcommands.commands;

import jawamaster.foxcommands.TPHandler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class RandomTP implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender commandSender, Command arg1, String arg2, String[] arg3) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;
			
			Player target;
			
			if (arg3.length > 0){
				target = Bukkit.getPlayer(arg3[0]);
			}
			else {
				target = player;
			}
			
			if (player.hasPermission("jawamaster.foxcommands.randomtp") || !(player instanceof Player)){
				player.sendMessage(ChatColor.DARK_GREEN + "Teleporting: " + target.getDisplayName());
				executeTP(target);
			}
			else {
				player.sendMessage(ChatColor.RED + "You do not have permission to execute that command.");
			}
		}
		else {
			if (arg3.length > 0) {
				Player target = Bukkit.getPlayer(arg3[0]);
				executeTP(target);
				System.out.println("Teleporting: " + target.getDisplayName() + " via console or command block.");
			}
		}
		return true;
	}
	
	public static void executeTP(Player target) {
		int worldborder = 29900;
		Location loc = TPHandler.randomLocation(target, worldborder);
		boolean safe = TPHandler.blockCheck(loc);
		int iter = 0;
		
		while (!safe && (iter < 80)) {
			loc = TPHandler.randomLocation(target, worldborder);
			safe = TPHandler.blockCheck(loc);
			iter++;
		}
		

		target.sendMessage("Randomly Teleporting you");
		target.teleport(loc, TeleportCause.COMMAND);
	}

}
