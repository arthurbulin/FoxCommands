package jawamaster.foxcommands.listeners;

import jawamaster.foxcommands.JSONHandler;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ListenerMOTD implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		JSONHandler.runMOTD(player);
	}

}
