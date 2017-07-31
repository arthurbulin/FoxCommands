package jawamaster.foxcommands.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Colors implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender commandSender, Command arg1, String arg2, String[] arg3) {
		
		commandSender.sendMessage(ChatColor.BLACK + "Black " + ChatColor.WHITE + " $0");
		commandSender.sendMessage(ChatColor.DARK_BLUE + "Dark Blue " + ChatColor.WHITE + " $1");
		commandSender.sendMessage(ChatColor.DARK_GREEN + "Dark Green " + ChatColor.WHITE + "$2");
		commandSender.sendMessage(ChatColor.DARK_AQUA + "Dark Aqua " + ChatColor.WHITE + "$3");
		commandSender.sendMessage(ChatColor.DARK_RED + "Dark Red " + ChatColor.WHITE + "$4");
		commandSender.sendMessage(ChatColor.DARK_PURPLE + "Dark Purple " + ChatColor.WHITE + "$5");
		commandSender.sendMessage(ChatColor.GOLD + "Gold " + ChatColor.WHITE + "$6");
		commandSender.sendMessage(ChatColor.GRAY + "Gray " + ChatColor.WHITE + "$7");
		commandSender.sendMessage(ChatColor.DARK_GRAY + "Dark Gray " + ChatColor.WHITE + "$8");
		commandSender.sendMessage(ChatColor.LIGHT_PURPLE + "Indigo " + ChatColor.WHITE + "$9");
		commandSender.sendMessage(ChatColor.GREEN + "Green " + ChatColor.WHITE + "$A");
		commandSender.sendMessage(ChatColor.AQUA + "Aqua " + ChatColor.WHITE + "$B");
		commandSender.sendMessage(ChatColor.RED + "Red " + ChatColor.WHITE + "$C");
		//commandSender.sendMessage(ChatColor. + "Pink " + ChatColor.WHITE + "$D");
		commandSender.sendMessage(ChatColor.YELLOW + "Yellow " + ChatColor.WHITE + "$E");
		//commandSender.sendMessage(ChatColor.BLACK + "Black " + ChatColor.WHITE + "$0");
		//commandSender.sendMessage(ChatColor.BLACK + "Black " + ChatColor.WHITE + "$0");
		
		
		return true;
	}

}
