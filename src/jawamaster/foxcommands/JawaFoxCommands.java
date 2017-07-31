package jawamaster.foxcommands;

import java.io.File;

import jawamaster.foxcommands.commands.Back;
import jawamaster.foxcommands.commands.CExec;
import jawamaster.foxcommands.commands.Colors;
import jawamaster.foxcommands.commands.DelHome;
import jawamaster.foxcommands.commands.DelWarp;
import jawamaster.foxcommands.commands.FullBright;
import jawamaster.foxcommands.commands.GM;
import jawamaster.foxcommands.commands.Home;
import jawamaster.foxcommands.commands.ListHomes;
import jawamaster.foxcommands.commands.ListWarps;
import jawamaster.foxcommands.commands.MOTD;
import jawamaster.foxcommands.commands.RandomTP;
import jawamaster.foxcommands.commands.SetHome;
import jawamaster.foxcommands.commands.SetWarp;
import jawamaster.foxcommands.commands.Spawn;
import jawamaster.foxcommands.commands.SummonA;
import jawamaster.foxcommands.commands.TPA;
import jawamaster.foxcommands.commands.TPAccept;
import jawamaster.foxcommands.commands.TPW;
import jawamaster.foxcommands.commands.Warp;
import jawamaster.foxcommands.listeners.Death;
import jawamaster.foxcommands.listeners.ListenerMOTD;
import jawamaster.foxcommands.listeners.TeleportListener;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class JawaFoxCommands extends JavaPlugin {

	public static Plugin plugin = null;
	File dataDir = new File(getDataFolder() + File.separator + "data");
	
	
	@Override
	public void onEnable() {
		//Create a plugin instance
		plugin = this;
		
		//Check directories
		if (!getDataFolder().exists()){ //check for datafolder and make it if it doesnt exist
			getDataFolder().mkdir();
			dataDir.mkdir();
			System.out.println("Created plugin directories.");
		} else if (!dataDir.exists()){
			dataDir.mkdir();
			System.out.println("Data directory created.");
		}
		
		//Register commands
		//this.getCommand("checkperm").setExecutor(new CommandCheckPerm());
		
		this.getCommand("tpw").setExecutor(new TPW());
		
		this.getCommand("sethome").setExecutor(new SetHome());
		this.getCommand("home").setExecutor(new Home());
		this.getCommand("listhomes").setExecutor(new ListHomes());
		this.getCommand("delhome").setExecutor(new DelHome());
	
		this.getCommand("setwarp").setExecutor(new SetWarp());
		this.getCommand("listwarps").setExecutor(new ListWarps());
		this.getCommand("warp").setExecutor(new Warp());
		this.getCommand("delwarp").setExecutor(new DelWarp());
		
		this.getCommand("spawn").setExecutor(new Spawn());
		this.getCommand("gm").setExecutor(new GM());
		
		this.getCommand("back").setExecutor(new Back());
		
		this.getCommand("tpa").setExecutor(new TPA());
		this.getCommand("tpaccept").setExecutor(new TPAccept());
		this.getCommand("summona").setExecutor(new SummonA());
		
		this.getCommand("motd").setExecutor(new MOTD());
		
		this.getCommand("colors").setExecutor(new Colors());
		
		this.getCommand("randomtp").setExecutor(new RandomTP());
		this.getCommand("cexec").setExecutor(new CExec());
		
		this.getCommand("fullbright").setExecutor(new FullBright());
		
		getServer().getPluginManager().registerEvents(new TeleportListener(), this);
		getServer().getPluginManager().registerEvents(new Death(), this);
		getServer().getPluginManager().registerEvents(new ListenerMOTD(), this);
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public static Plugin getInstance() {
		return plugin;
	}


}
