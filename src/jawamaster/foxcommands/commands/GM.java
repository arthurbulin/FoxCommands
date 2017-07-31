package jawamaster.foxcommands.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GM implements CommandExecutor{

	public boolean onCommand(CommandSender commandSender, Command arg1, String arg2, String[] arg3) {
		if (commandSender.hasPermission("jawamaster.foxcommands.gm")){
			if (commandSender instanceof Player){
				Player player = (Player) commandSender;
				
				if (player.hasPermission("jawamaster.foxcommands.gm.override") || (player.getWorld() == Bukkit.getServer().getWorld("creative"))){
					if (arg3.length == 0){
						//if player has override permission or if they are in creative world
						if (player.getGameMode() == GameMode.CREATIVE){
							player.setGameMode(GameMode.SURVIVAL);
							player.sendMessage(ChatColor.GREEN +  " > Gamemode changed to survival.");
							//Bukkit.getServer().broadcastMessage(player.getDisplayName() + " has changed their gamemode to Survival.");
							return true;
						}
						else if (player.getGameMode() == GameMode.SURVIVAL){
							player.setGameMode(GameMode.CREATIVE);
							player.sendMessage(ChatColor.GREEN + " > Gamemode changed to creative.");
							return true;
						}

					}
					else if (arg3.length == 1) {
						if (arg3[0].charAt(0) == 'c'){
							player.setGameMode(GameMode.CREATIVE);
							player.sendMessage(ChatColor.GREEN + " > Game mode changed to creative");
							return true;
						}
						else if ((arg3[0].charAt(0) == 's') && (!arg3[0].contains("sp"))) {
							player.setGameMode(GameMode.SURVIVAL);
							player.sendMessage(ChatColor.GREEN + " > Gamemode changed to survival.");
							return true;
						}
						else if (arg3[0].contains("sp")) {
							if (player.hasPermission("jawamaser.foxcommands.gm.override")){
								player.setGameMode(GameMode.SPECTATOR);
								player.sendMessage(ChatColor.GREEN + " > Gamemode changed to spectator.");
								return true;
							}
							else {
								player.sendMessage(ChatColor.RED + " > That permission is restricted to staff only");
								return true;
							}
						}
						else if (arg3[0].charAt(0) == 'a'){
							player.setGameMode(GameMode.ADVENTURE);
							player.sendMessage(ChatColor.GREEN + " > Gamemode changed to adventure you daring fuzzy you. Good luck!");
						}
					}
				}
				else {
					player.sendMessage("You do not have permission to do that here");
					return true;
				}
			}
		}
		else {
			System.out.println("Not implemented yet");
			//TODO console control of quick gamemode switching
		}
		
		return true;
	}
}
