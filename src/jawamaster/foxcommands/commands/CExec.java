package jawamaster.foxcommands.commands;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CExec implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender player, Command arg1, String arg2, String[] arg3) {

		if (player instanceof Player) {
			if (!player.hasPermission("jawamaster.foxcommands.cexec")) {
				player.sendMessage(ChatColor.RED + "You do not have permission to do this.");
				return true;
			}
		}
		
		Player target = Bukkit.getPlayer(arg3[0]);
		
		String sequence = arg3[1].replace("/", "");
		
		if (arg3.length > 2) {
			for (int i = 2; i < arg3.length; i++) {
				sequence = sequence + " " + arg3[i];
			}
		}
		
		if (arg3[1].contains("/")) {
			target.performCommand(sequence);
		}
		else {
			target.chat(sequence);
		}
		
		return true;
	}

}
