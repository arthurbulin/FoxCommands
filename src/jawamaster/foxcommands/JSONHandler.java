package jawamaster.foxcommands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSONHandler {

	//This will be used for pretty printing our jsons

	
	//PLAYER JSON METHODS
	//write our json files out
	public static void writeJSON(UUID uuid, JSONObject obj) {
		File playerdat = new File(JawaFoxCommands.getInstance().getDataFolder() + "/data/" +  uuid.toString() + ".json");
		Gson gsonpp = new GsonBuilder().setPrettyPrinting().create();
		
		try { //open our writer and write the player file
			PrintWriter writer = new PrintWriter(playerdat);
			writer.print(gsonpp.toJson(obj));
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//Retrieve the json data
	public static JSONObject getJSON(UUID uuid) { 
		File playerdat = new File(JawaFoxCommands.getInstance().getDataFolder() + "/data/" + uuid.toString() + ".json");
		JSONParser parser = new JSONParser();
		
		//If there is no player file create it
		if (!playerdat.exists())
			createJSON(uuid);
		
		try { //try to parse the player file
			JSONObject obj = (JSONObject) parser.parse(new FileReader(playerdat));
			return obj;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;	
	}
	
	//create the file and blank json object for a player if they don't exist
	@SuppressWarnings("unchecked")
	public static void createJSON(UUID uuid) {
		//Create the blank JSON objects
		JSONObject dat = new JSONObject();
		JSONObject locs = new JSONObject();
		World world = Bukkit.getServer().getWorld("world");
		World creativeWorld = Bukkit.getServer().getWorld("creative");
		World gamesWorld = Bukkit.getServer().getWorld("games");
		
		JSONObject world_spawn = unzipLocation(world.getSpawnLocation());
		JSONObject creative_spawn = unzipLocation(creativeWorld.getSpawnLocation());
		JSONObject games_spawn = unzipLocation(gamesWorld.getSpawnLocation());
		
		//Create our JSON world objects
		locs.put("creative", creative_spawn);
		locs.put("world", world_spawn);
		locs.put("games", games_spawn);
		
		//Create our upper json objects
		dat.put("homes", null);
		dat.put("last_locations", locs);
		
		//Write it to the player's dat file
		writeJSON(uuid, dat);
	}
	
	//get last location from the json file
	public static Location getLastLocation(String wld, UUID uuid) {
		JSONObject obj = getJSON(uuid);
		JSONObject llocs = (JSONObject) obj.get("last_locations");
		JSONObject lloc;
		
		if (llocs.containsKey(wld)){
			lloc = (JSONObject) llocs.get(wld);
		} else {
			lloc = (JSONObject) unzipLocation(Bukkit.getServer().getWorld(wld).getSpawnLocation());
		}
		
		double x = (Double) lloc.get("x");
		double y = (Double) lloc.get("y");
		double z = (Double) lloc.get("z");
		double yaw = (Double) lloc.get("yaw");
		//Even though im telling it what world to do to start with this is better so that i can name worlds
		//even if that isnt their world name
		World worldl = Bukkit.getServer().getWorld((String) lloc.get("world"));
		
		return new Location(worldl, x, y, z, (float) yaw, 0.0f);
	}
	
	//set the last location in the jsons
	@SuppressWarnings("unchecked")
	public static void setLastLocation(UUID uuid, Location loc) {
		JSONObject obj = getJSON(uuid); //open player json
		JSONObject newLoc = new JSONObject(); //Create json object for new location
		
		JSONObject lloc = (JSONObject) obj.get("last_locations"); //pull last locations out of player json
		String world = loc.getWorld().toString(); //get world of last location
		world = world.replace("CraftWorld{name=", ""); //strip out extras
		world = world.replace("}", "");
		newLoc = unzipLocation(loc); //pass location to unzip and get back json obj
		
		lloc.put(world, newLoc); //put newLoc in last locations
		obj.put("last_locations", lloc); //put last location back in player json object
		
		writeJSON(uuid, obj); //write player json object back to file
		
	}
	
	//Add a home to the json list
	@SuppressWarnings("unchecked")
	public static boolean addHome(UUID uuid, String name, Location loc){
		JSONObject obj = getJSON(uuid);
		JSONObject newhome = new JSONObject();
		JSONObject homes;
		
		if (obj.get("homes") != null)
			homes = (JSONObject) obj.get("homes");
		else
			homes = new JSONObject();
		
		if (homes.containsKey(name))
			return true;
		
		else {
			
			newhome = unzipLocation(loc);
			homes.put(name, newhome);
			obj.put("homes", homes);
			writeJSON(uuid, obj);
			
			return false;
		}
		
	}
	
	//Get the home
	public static Location getHome(UUID uuid, String name){
		JSONObject obj = getJSON(uuid);
		JSONObject homes = (JSONObject) obj.get("homes");
		
		if (homes.containsKey(name)){
			JSONObject home = (JSONObject) homes.get(name);
			Location loc = zipLocation(home);
			return loc;
		}
		else {
			return null;
		}
	}
	
	//Return the list of homes
	public static String listHomes(UUID uuid){
		JSONObject obj = getJSON(uuid);
		JSONObject homes = (JSONObject) obj.get("homes");
		
		return homes.keySet().toString();
	}
	
	//Unzip a Location into an JSONObject and return it, this can be used for homes and last_locs
	@SuppressWarnings("unchecked")
	public static JSONObject unzipLocation(Location loc){
		JSONObject obj = new JSONObject();
		
		String world = loc.getWorld().toString();
		world = world.replace("CraftWorld{name=", "");
		world = world.replace("}", "");
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		float yaw = loc.getYaw();
		
		obj.put("x", x);
		obj.put("y", y);
		obj.put("z", z);
		obj.put("yaw", yaw);
		obj.put("world", world);

		return obj;
	}
	
	public static Location zipLocation(JSONObject obj) {
		double x = (Double) obj.get("x");
		double y = (Double) obj.get("y");
		double z = (Double) obj.get("z");
		double yaw = (Double) obj.get("yaw");
		String world = (String) obj.get("world");
		//System.out.println(world);
		
		Location loc = new Location((World) Bukkit.getServer().getWorld(world), x, y, z, (float) yaw, 0.0f);
		
		return loc;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean delHome(UUID uuid, String home) {
		JSONObject obj = getJSON(uuid);
		JSONObject homes = (JSONObject) obj.get("homes");
		
		if (homes.containsKey(home)) {
			homes.remove(home);
			obj.put("homes", homes);
			writeJSON(uuid, obj);
			return true;
		}
		else {
			return false;
		}
	}
	
	//WARP METHODS
//********************************************************************************
	//write to the warp file
	public static void writeWarpFile(JSONObject obj){
		File warpfile = new File(JawaFoxCommands.getInstance().getDataFolder() + "/warps.json");
		Gson gsonpp = new GsonBuilder().setPrettyPrinting().create();
		
		try{
			PrintWriter writer = new PrintWriter(warpfile);
			writer.print(gsonpp.toJson(obj));
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Create the default warp json
	@SuppressWarnings("unchecked")
	public static void createWarpJson(){
		JSONObject obj = new JSONObject();
		
		Location creative = Bukkit.getServer().getWorld("creative").getSpawnLocation();
		Location world = Bukkit.getServer().getWorld("world").getSpawnLocation();
		
		JSONObject crt = unzipLocation(creative);
		JSONObject wld = unzipLocation(world);
		
		obj.put("creative", crt);
		obj.put("world", wld);
		writeWarpFile(obj);
	}
	
	//Add a warp object to the warp file
	@SuppressWarnings("unchecked")
	public static boolean addWarp(Location loc, String name){
		JSONObject obj = getWarpJSON();
		JSONObject warp = unzipLocation(loc);
		
		if (obj.containsKey(name)) {
			return false;
		}
		else {
			warp.put("private", false);
			warp.put("hidden", false);
			obj.put(name, warp);

			
			writeWarpFile(obj);
			return true;
		}
	}
	
	//get from the warp file
	public static JSONObject getWarpJSON(){
		JSONParser parser = new JSONParser();
		File warpfile = new File(JawaFoxCommands.getInstance().getDataFolder() + "/warps.json");
		
		if (!warpfile.exists()) {
			JSONHandler.createWarpJson();
		}
		
		try { //try to parse the player file
			JSONObject obj = (JSONObject) parser.parse(new FileReader(warpfile));
			return obj;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void listWarps(UUID uuid){
		JSONObject obj = getWarpJSON();
		JSONObject warps = (JSONObject) getJSON(uuid).get("warps");
		boolean hasHidden = false;
		
		Player player = Bukkit.getPlayer(uuid);
		boolean playerPerm;
		
		Set keyset = (Set) obj.keySet();
		String[] keys = (String[]) keyset.toArray(new String[keyset.size()]);
		
		String[] listNotHidden = new String[keys.length];
		String[] listHidden = new String[keys.length];
		
		int j = 0;
		int k = 0;
		
		for (int i = 0; i < keys.length; i++) {
			if (!getWarpProperties("hidden", keys[i])){
				listNotHidden[j] = keys[i];
				j++;
				
			}
			else {
				playerPerm = userHasPermission(uuid, keys[i], "view");
				if (player.hasPermission("jawamaster.foxcommands.setwarp") || playerPerm){
					listHidden[k] = keys[i];
					k++;
					hasHidden = true;
				}
			}
		}
		
		//This is probably not the best way but im going to do it for now
		String[] listNH = new String[j];
		String[] listH = new String[k];
		
		for (int i = 0; i < j; i++){
			listNH[i] = listNotHidden[i];
		}
		for (int i = 0; i < k; i++){
			listH[i] = listHidden[i];
		}
		
		player.sendMessage(ChatColor.GREEN + " > These are the available warps: " + ChatColor.GOLD + Arrays.toString(listNH));

		if (player.hasPermission("jawamaster.foxcommands.setwarp") || hasHidden)
			player.sendMessage(ChatColor.GREEN + " > These are the available hidden warps: " + ChatColor.GOLD + Arrays.toString(listH));
	}
	
	public static Location getWarpLoc(String name){
		JSONObject obj = getWarpJSON();
		JSONObject warpLoc = new JSONObject();
		
		if (!obj.containsKey(name)){
			return null;
		}
		
		JSONObject warp = (JSONObject) obj.get(name);
		warpLoc.put("x", warp.get("x"));
		warpLoc.put("y", warp.get("y"));
		warpLoc.put("z", warp.get("z"));
		warpLoc.put("world", warp.get("world"));
		warpLoc.put("yaw", warp.get("yaw"));
		
		
		return zipLocation(warpLoc);
		
	}
	
	//checks the warp's properties
	public static boolean getWarpProperties(String property, String warp){
		JSONObject obj = getWarpJSON();
		JSONObject warpObj;
		
		//This first instance should never happen
		if (!obj.containsKey(warp))
			return false;
		else //set the warpObj to our JSON data
			warpObj = (JSONObject) obj.get(warp);
		
		if (warpObj.containsKey(property)) {
			if (warpObj.get(property).toString().contains("true"))
				return true;
			else
				return false;
		}
		else
			return false;
	}

	public static boolean delWarp(String name){
		JSONObject obj = getWarpJSON();
		
		if (!obj.containsKey(name))
			return false;

		obj.remove(name);
		writeWarpFile(obj);
		return true;
	}

	//Add a property to a warp
	public static boolean setProperty(String warp, int p, boolean state) {
		JSONObject obj = getWarpJSON();
		JSONObject warpJ = (JSONObject) obj.get(warp);
		JSONObject warpHolder = new JSONObject();
		JSONObject otherProperty = new JSONObject();
		String property;
		String otherPropertyName;
		boolean otherPropertyState;
		
		//Before running this you should run warpExists
		
		//decides which property to modify
		if (p == 0){ 
			property = "hidden";
			otherPropertyName = "private";
			if (warpJ.containsKey("private")){
				otherProperty.put("private", warpJ.get("private"));
			}
			else
				otherProperty.put("private", false);
		}	
		else if (p == 1) {
			property = "private";
			otherPropertyName = "hidden";
			if (warpJ.containsKey("hidden")){
				otherProperty.put("hidden", warpJ.get("hidden"));
			}
			else
				otherProperty.put("hidden", false);
		}
		else
			return false;
		

		warpHolder.put("world", warpJ.get("world"));
		warpHolder.put("x", warpJ.get("x"));
		warpHolder.put("y", warpJ.get("y"));
		warpHolder.put("z", warpJ.get("z"));
		warpHolder.put("yaw", warpJ.get("yaw"));
		warpHolder.put(otherPropertyName, otherProperty.get(otherPropertyName));
		
		//assign the property to the warp
		//System.out.println(state);
		warpHolder.put(property, state);
		//put the warp back in the data
		//System.out.println(warpHolder);
		obj.remove(warp);
		obj.put(warp, warpHolder);
		//write to file
		writeWarpFile(obj);
		//return that it was successful
		return true;
	}
	
	//Return the property of a warp
	public static boolean getProperty(String warp, String p) {
		JSONObject obj = getWarpJSON();
		JSONObject warpJ = (JSONObject) obj.get(warp);
		
		if (!warpJ.containsKey(p))
			return false; //always assume that a missing property is false
		
		if (warpJ.get(p).toString().contains("true"))
			return true;
		else
			return false;
	}
	
	//Check if a warp exists; this is semi redundent
	public static boolean warpExists(String warp) {
		JSONObject obj = getWarpJSON();
		
		if (obj.containsKey(warp))
			return true;
		else
			return false;
	}
	
	//Modify a user's permissions
	public static boolean userModify(UUID uuid, String warp, boolean auth, String permission){
		//TODO based on auth add or remove a user's permission
		JSONObject obj = getJSON(uuid);
		JSONObject warps;
		JSONObject permissions;
		
		//check to see if they player already has permissions present
		if (obj.containsKey("warps")) {
			warps = (JSONObject) obj.get("warps"); //set warps to the warp object from the player file
			if (warps.containsKey(warp)) { //If warp is present
				permissions = (JSONObject) warps.get(warp); //load the permissions from the object
			}
			else //if there is no warp by that name generate a new permisisons object
				permissions = new JSONObject();
		}
		else {//If player doesnt have any warps create a new warp and permissions object
			warps = new JSONObject();
			permissions = new JSONObject();
		}
		
		if (permission.contains("view"))
			permissions.put("view", Boolean.toString(auth));
		
		if (permission.contains("use"))
			permissions.put("use", Boolean.toString(auth));
		
		if (permission.contains("all")){
			permissions.put("view", Boolean.toString(auth));
			permissions.put("use", Boolean.toString(auth));
		}
		
		warps.put(warp, permissions);
		
		obj.put("warps", warps);
		
		writeJSON(uuid, obj);
		
		return true;
	}
	
	public static boolean userHasPermission(UUID uuid, String warp, String property){
		JSONObject obj = getJSON(uuid);
		JSONObject warps;
		
		if (obj.containsKey("warps")) { //check for warps in the player json
			warps = (JSONObject) obj.get("warps");
			if (warps.containsKey(warp)){ //Check for specific warp in JSON
				return Boolean.valueOf(((JSONObject) warps.get(warp)).get(property).toString());
			}
			else if (!getProperty(warp, "private"))//return false if specific warp not present
				return true;
			else
				return false;
		}
		else //return false if player has no warp perms
			return false;
		
	}
	
	//MOTD handling
	//write the motd file
//********************************************************************************
	public static void writeMOTD(JSONObject obj){
		File motd = new File(JawaFoxCommands.getInstance().getDataFolder()+"/motd.json");
		Gson gsonpp = new GsonBuilder().setPrettyPrinting().create();
		
		try {
			FileWriter writer = new FileWriter(motd);
			writer.write(gsonpp.toJson(obj));
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	//create default MOTD
	@SuppressWarnings("unchecked") //Hide unneccessray warnings
	public static void createMOTD() {
		JSONObject obj = new JSONObject();
		obj.put("priority", null);
		obj.put("standard", null);
		writeMOTD(obj);
	}

	//get motd from the file
	public static JSONObject getMOTD(){
		File motd = new File(JawaFoxCommands.getInstance().getDataFolder() + "/motd.json");
		JSONParser parser = new JSONParser();
		
		//If there is no motd file create it
		if (!motd.exists())
			createMOTD();
		
		try { //try to parse the player file
			JSONObject obj = (JSONObject) parser.parse(new FileReader(motd));
			return obj;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	//add motd message to motd file
	public static boolean addMOTD(String name, String MOTD, int p) {
		JSONObject obj = getMOTD();
		JSONObject pr = (JSONObject) obj.get("priority");
		JSONObject st = (JSONObject) obj.get("standard");
		
		JSONObject motds;
		boolean checkp;
		boolean checks;
		String key;
		
		//Null checks
		if (pr == null) {
			checkp = false;
			pr = new JSONObject();
		}
		else
			checkp = pr.containsKey(name);
		
		if (st == null) {
			checks = false;
			st = new JSONObject();
		}
		else 
			checks = st.containsKey(name);
		
		//Type check
		if (p == 0) {
			key = "priority";
			motds = pr;
		}
		else if (p == 1) {
			key = "standard";
			motds = st;
		}
		else {
			return false;
		}	
		
		//check for motd in file already
		if (checks || checkp) {
			return false;
		}
		else {
			motds.put(name, MOTD);
			obj.put(key, motds);
			writeMOTD(obj);
			return true;
		}
		
	}

	//Remove MOTD from file
	public static boolean removeMOTD(String name) {
		JSONObject obj = getMOTD();
		JSONObject p = (JSONObject) obj.get("priority");
		JSONObject s = (JSONObject) obj.get("standard");

		if (p.containsKey(name)) {
			p.remove(name);
			obj.put("priority", p);
			writeMOTD(obj);
			return true;
		}
		else if (s.containsKey(name)) {
			s.remove(name);
			obj.put("standard", s);
			writeMOTD(obj);
			return true;
		}
		else
			return false;
		
		
	}

	//Return motd list in order of priority
	public static void listMOTD (Player player) {
		JSONObject obj = getMOTD();
		JSONObject p = (JSONObject) obj.get("priority");
		JSONObject s = (JSONObject) obj.get("standard");
		
		if (!(p == null)) {
			//create an iterator so we can iterate over the two sets of motds
			Set keyset = (Set) p.keySet();
			String[] keys = (String[]) keyset.toArray(new String[keyset.size()]);
			
			player.sendMessage("These are the current Priority MOTDs: ");
			for (int i = 0; i < keys.length; i++) {
				player.sendMessage(keys[i] + ": " + ChatColor.translateAlternateColorCodes('$', (String) p.get(keys[i])));
			}
		}
		
		if (!(s == null)) {
			//Same as above
			Set keyset = (Set) s.keySet();
			String[] keys = (String[]) keyset.toArray(new String[keyset.size()]);
			
			player.sendMessage("These are the current Standard MOTDs: ");
			for (int i = 0; i < keys.length; i++) {
				player.sendMessage(keys[i] + ": " + (String) s.get(keys[i]));
			}
		}
		
		if ((s == null) && (p == null)) {
			player.sendMessage(ChatColor.RED + "There are no MOTDs currently set");
		}
		
	}

	public static void runMOTD (Player player) {
		JSONObject obj = getMOTD();
		JSONObject pr = (JSONObject) obj.get("priority");
		JSONObject st = (JSONObject) obj.get("standard");
		boolean checkpr = true;
		boolean checkst = true;
		int numToPrint = 7;
		int numPriority;
		int numStandard;
		
		//TODO perform null checks first
		if (pr == null)
			checkpr = false;
		
		if (st == null)
			checkst = false;
				
		//TODO print all priority messages
		if (checkpr) {
			Set keyset = (Set) pr.keySet();
			String[] keys = (String[]) keyset.toArray(new String[keyset.size()]);
			
			numPriority = keys.length;
			
			player.sendMessage(ChatColor.GREEN + "====Priority Messages====");
			for (int i = 0; i < keys.length; i++) {
				player.sendMessage(ChatColor.GREEN + "> " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('$', (String) pr.get(keys[i])));
			}
		} else
			numPriority = 0;
		
		//Print random priority messages
		if (checkst){
			Set keyset = (Set) st.keySet(); //Get the things i need to iterate
			String[] keys = (String[]) keyset.toArray(new String[keyset.size()]);

			numStandard = keys.length; //get the number of standard motds

			if (numPriority < numToPrint) //Establish how many we need to print based on what we already printed
				numToPrint = numToPrint - numPriority;
			else
				numToPrint = 0;

			//Don't try and print more than we have
			if (numToPrint >= numStandard){ 
				numToPrint = numStandard;
			}
			
			//generate a int[] or size numToPrint
			Random r = new Random();
			ArrayList<Integer> runs = new ArrayList<>();

			for (int i = 0; i < numToPrint;) {
				int j = r.nextInt(keys.length);
				if (!runs.contains(j)){
					runs.add(j);
					i++;
				}
			}

			//if it is greater than zero either print all or generate a random selection
			player.sendMessage(ChatColor.GREEN + "====In Other News====");
			for (int i = 0; i < runs.size(); i++) {
				player.sendMessage(ChatColor.GREEN + "> " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('$', (String) st.get(keys[runs.get(i)])));
			}
		}
	}
		
	
//*******************************************************************************
	
	//AUTOEXEC Functions
	//Checks if a player has an autoexec clause in their JSON file, if not this will add it
	//with a basic autoexec init message
	public static void initAutoExec(UUID uuid) {
		JSONObject obj = getJSON(uuid);
		
		if (!obj.containsKey("autoexec")) {
			JSONObject autoexec = new JSONObject();
			JSONObject def = new JSONObject();
			def.put("c", "autoexec init");
			autoexec.put("init", def);
			obj.put("autoexec", autoexec);
			writeJSON(uuid, obj);
		}
		
	}

	public static void setAutoExec(UUID uuid, JSONObject ae){
		
	}

//********************************************************************************
	//Back command functions
	public static void addBackLocation(UUID uuid, Location loc){
		JSONObject obj = getJSON(uuid);
		JSONObject backLoc;
		JSONObject Loc = unzipLocation(loc);
		JSONObject newBackLoc = new JSONObject();
		
		//If no back location repopulate it will nulls
		if (!obj.containsKey("back")) {
			backLoc = new JSONObject();
			for (int i=0; i < 6; i++){
				backLoc.put(Integer.toString(i), "null");
			}
		}
		else { //Else load the object
			backLoc = (JSONObject) obj.get("back");
		}
		
		//shift them down by getting the one before and moving it down in a new object
		for (int i=1; i < 6; i++){
			
			newBackLoc.put(Integer.toString(i), backLoc.get(Integer.toString(i-1)));
		}
		
		newBackLoc.put("0", Loc);
		
		obj.put("back", newBackLoc);
		writeJSON(uuid, obj);
	}

	public static Location getBackLocation(UUID uuid){
		JSONObject obj = getJSON(uuid);
		JSONObject backs = (JSONObject) obj.get("back");
		
		JSONObject backloc; 
		
		if (backs.get("0").toString().contains("null")) {
			return null;
		}
		else {
			backloc = (JSONObject) backs.get("0");
		}
		
		Location loc = zipLocation(backloc);
		
		return loc;
	}
	
	public static void removeBackLocation(UUID uuid) {
		JSONObject obj = getJSON(uuid); //Get the player object
		JSONObject backs = (JSONObject) obj.get("back"); //Get the backs from the player objects
		
		//Shift all of these values upwards
		for (int i = 0; i <5; i++){
			backs.put(Integer.toString(i), backs.get(Integer.toString(i + 1)));
		}
		backs.put("5", "null");
		
		obj.put("back", backs);
		writeJSON(uuid, obj);
	}

//********************************************************************************
	//Admin functions
	public static JSONObject getFrozenPlayers(){
		File frozenPlayers = new File(JawaFoxCommands.getInstance().getDataFolder() + "/frozenplayers.json");
		JSONParser parser = new JSONParser();
		JSONObject obj;
		
		//If there is no file create it
		if (!frozenPlayers.exists()) {
			obj = new JSONObject();
			return obj; 
		}
		else {
			try { // try to parse the player file
				obj = (JSONObject) parser.parse(new FileReader(frozenPlayers));
				return obj;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static void setFrozenPlayers(JSONObject frozenPlayers) {
		File frozenPlayersFile = new File(JawaFoxCommands.getInstance().getDataFolder() + "/frozenplayers.json");
		Gson gsonpp = new GsonBuilder().setPrettyPrinting().create();
		
		try { //open our writer and write the player file
			PrintWriter writer = new PrintWriter(frozenPlayersFile);
			writer.print(gsonpp.toJson(frozenPlayers));
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	//random tp generations



}