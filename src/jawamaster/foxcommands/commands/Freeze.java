package jawamaster.foxcommands.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Freeze implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender commandSender, Command arg1, String arg2, String[] arg3) {
		
		//Parse player permission and short circuit operation if player doesn't have permission
		if (commandSender instanceof Player){
			if ( !((Player) commandSender).hasPermission("jawamaster.foxcommands.freeze")){
				((Player) commandSender).sendMessage(ChatColor.RED + " > You do not have permission to execute that command.");
				return true;
			}
		}
		
		//Try to create target player instance.
		try {
			Player target = Bukkit.getPlayer(arg3[0]);
		} catch (Exception e){ //if failure print out to relevant source and short circuit operation
			
			if (commandSender instanceof Player)
				((Player) commandSender).sendMessage(ChatColor.RED + " > Unable to locate player " + arg3[0]);
			else
				System.out.println("Unable to locate player " + arg3[0]);
			
			return true;
		}
		
		return true;
	}
	
	public static void freezeTarget(Player target){
		
	}

}
