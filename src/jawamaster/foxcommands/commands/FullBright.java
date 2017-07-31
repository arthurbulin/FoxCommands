package jawamaster.foxcommands.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FullBright implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender commandSender, Command arg1, String arg2, String[] arg3) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;
			
			if (player.hasPermission("jawamaster.foxcommands.fullbright")) {
				
				if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
					player.sendMessage(ChatColor.DARK_GREEN + " > Do not go gentle into that good nigth player.");
					player.removePotionEffect(PotionEffectType.NIGHT_VISION);
				}
				else {
					player.sendMessage(ChatColor.GREEN + " > Night vision activate!");
					player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1));
				}
				
			}
		}
		else {
			System.out.println("Console cannot execute this command");
		}
		return true;
	}

}
