package jawamaster.foxcommands.listeners;

import jawamaster.foxcommands.FreezeHandler;
import jawamaster.foxcommands.JSONHandler;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class FrozenListener implements Listener {

	@EventHandler
	public void onPlayerMovement(PlayerMoveEvent event) {
		Player target = event.getPlayer();
		if (FreezeHandler.isFrozen(target)){
			event.setCancelled(true);
		}
	}
}
