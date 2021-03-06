package jawamaster.foxcommands;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class TPHandler {
	static Map<String, String> currentTPRequest = new HashMap<String, String>();
	static Map<String, String> currentSummonRequest = new HashMap<String, String>();
	public static long keepAlive = 1200;
	public static long coolDown = 1200;
	
	//Person the request is being sent to is the target
	public static void tpRequest(int type, Player player, Player target) {
		player.sendMessage(ChatColor.DARK_GREEN + " > Sending request to " + target.getName());


		if (type == 0) { //To tpa to a player
			target.sendMessage(ChatColor.DARK_GREEN +" > " + player.getDisplayName() + " has requested to teleport to you.");
			currentTPRequest.put(target.getName(), player.getName());
		}
		else if (type == 1) { //to summona a player to you
			target.sendMessage(ChatColor.DARK_GREEN + " > " + player.getDisplayName() + " has requested YOU teleport to them.");
			currentSummonRequest.put(target.getName(), player.getName());
		}

		target.sendMessage("Type /accept to accept the request.");
		//target.sendMessage("Type /deny to deny.");
	}

	public static void killRequest(Player target) {
		if (currentTPRequest.containsKey(target.getName())) {
			Player player = Bukkit.getServer().getPlayer(currentTPRequest.get(target.getName()));
			player.sendMessage(ChatColor.RED + "Your teleport request timed out.");
			currentTPRequest.remove(target.getName());
		}
		else if (currentSummonRequest.containsKey(target.getName())){
			Player player = Bukkit.getServer().getPlayer(currentSummonRequest.get(target.getName()));
			player.sendMessage(ChatColor.RED + "Your summon request timed out.");
			currentSummonRequest.remove(target.getName());
		}
		
	}
	
	public static boolean tpAccept(Player player) {
		if (currentTPRequest.containsKey(player.getName())){
			Player target = Bukkit.getServer().getPlayer(currentTPRequest.get(player.getName()));
			currentTPRequest.remove(player.getName());
			player.sendMessage(ChatColor.DARK_GREEN + "Teleporting " + target.getDisplayName() + " to you.");
			target.sendMessage(ChatColor.DARK_GREEN + "Teleporting you to " + player.getDisplayName());
			target.teleport(player, TeleportCause.COMMAND);
			return true;
		}
		else if (currentSummonRequest.containsKey(player.getName())) {
			Player target = Bukkit.getServer().getPlayer(currentSummonRequest.get(player.getName()));
			currentSummonRequest.remove(player.getName());
			player.sendMessage(ChatColor.DARK_GREEN + "Teleporting you to " + target.getDisplayName());
			target.sendMessage(ChatColor.DARK_GREEN + "Teleporting " + player.getDisplayName() + " to you.");
			player.teleport(target, TeleportCause.COMMAND);
			return true;
		}
		return false;
	}
	
	public static Location randomLocation(Player player, int worldborder) {
		Random r = new Random();
		
		int x = r.nextInt(worldborder + worldborder) - worldborder;
		int z = r.nextInt(worldborder + worldborder) - worldborder;
		int y = player.getWorld().getHighestBlockYAt(x, z);
		
		Location loc = new Location(player.getWorld(), x, y, z);
		
		return loc;
	}
	
	public static boolean blockCheck(Location loc){
		Block block = loc.getWorld().getHighestBlockAt(loc);
		
		Material type = block.getType();
		
		if ((type == Material.LAVA) || (type == Material.WATER)
				|| (type == Material.AIR) || (type == Material.STATIONARY_WATER)
				|| (type == Material.STATIONARY_LAVA) || (type == Material.CACTUS)
				|| (type == Material.MAGMA)) {
			
			return false;
		}
		else
			return true;
	}

}
