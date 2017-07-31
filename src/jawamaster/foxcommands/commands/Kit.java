package jawamaster.foxcommands.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Kit implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player){
			Player player = (Player) sender;
			
			//Here we need to give items to our player
			//create a new itemstack (type: diamond)
			ItemStack diamond = new ItemStack(Material.DIAMOND);
			ItemStack bricks = new ItemStack(Material.BRICK, 20);

			//set item amount, you can do this or like above with the material.brick, 20 thing
			//bricks.setAmount(20);
			
			//Give the player items to them
			player.getInventory().addItem(bricks, diamond);
		}
		
		//If the player or console uses the command correct we can return true
		return true;
	}

}
