package jawamaster.foxcommands.listeners;

import java.util.UUID;

import jawamaster.foxcommands.JSONHandler;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class Death implements Listener{
	
	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent pde){
		Player player = pde.getEntity();
		UUID uuid = player.getUniqueId();
		Location loc = player.getLocation();
		
		//player.sendMessage("death debug test");
		
		if (player.hasPermission("jawamaster.foxcommands.back")){
			JSONHandler.addBackLocation(uuid, loc);
		}
	}

}
