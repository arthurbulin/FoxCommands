package jawamaster.foxcommands.listeners;

import java.util.UUID;

import jawamaster.foxcommands.JSONHandler;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class TeleportListener implements Listener {

	@EventHandler
	public void onPlayerTeleportEvent(PlayerTeleportEvent pte){
		Player player = pte.getPlayer();
		UUID uuid = player.getUniqueId();

		if (player.hasPermission("jawamaster.foxcommands.back")){
			Location fromLoc = pte.getFrom();
			Location toLoc = pte.getTo();
			World fromWorld = fromLoc.getWorld();
			World toWorld = toLoc.getWorld();

			//player.sendMessage(pte.getCause().toString());

			if ((pte.getCause() == TeleportCause.COMMAND) || pte.getCause() == TeleportCause.PLUGIN){
				//player.sendMessage("unknown cause");
				if (player.hasPermission("jawamaster.foxcommands.back")) { 
					JSONHandler.addBackLocation(uuid, fromLoc);
					if (fromWorld != toWorld) {
						//System.out.println("world check");
						if ((fromWorld == Bukkit.getServer().getWorld("world")) || (fromWorld == Bukkit.getServer().getWorld("creative")) || (fromWorld == Bukkit.getServer().getWorld("games")) ){
							JSONHandler.setLastLocation(uuid, fromLoc);
						}
					}
				}
			}

		}
	}
}
