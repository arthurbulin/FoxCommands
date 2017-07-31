package jawamaster.foxcommands;

import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

public class FreezeHandler {
	static JSONObject frozenPlayers;
	
	//Load the frozen players from file
	public static void loadFrozen(){
		frozenPlayers = JSONHandler.getFrozenPlayers();
	}
	
	//Add target; use isFrozen before running.
	@SuppressWarnings("unchecked")
	public static void freezePlayer(Player target){
		frozenPlayers.put(target.getName(), target.getUniqueId());		
	}
	
	//Remove target; use isFrozen before running
	public static void unfreezePlayer(Player target){
		frozenPlayers.remove(target.getName());
	}
	
	//Check if a player is frozen
	public static boolean isFrozen(Player target){
		return frozenPlayers.containsKey(target.getName());
	}
}
