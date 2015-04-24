package iCGameDev.MC.Fallout.Main;

import iCGameDev.MC.Fallout.Armors.Armor;
import iCGameDev.MC.Fallout.SchedulerTasks.SchedulerTaskTPDelay;
import iCGameDev.MC.Fallout.Weapons.Weapon;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Main extends JavaPlugin implements Listener{
	
	static HashMap<Snowball, Weapon> projectiles = new HashMap<Snowball, Weapon>();
	static HashMap<Material, Weapon> weaponMats = new HashMap<Material, Weapon>();
	static ArrayList<Weapon> weapons = new ArrayList<Weapon>();
	static ArrayList<Armor> armors = new ArrayList<Armor>();
	static ArrayList<ItemStack> weaponStacks = new ArrayList<ItemStack>();
	
	static File configFile;
	static File dataStorageFile;
	static FileConfiguration config;
	static FileConfiguration dataStorage;
	
	/*******************************************************************
	 * TODO - Finish banning system (date + reason storage etc)        *
	 * TODO - Finish/fix tpa system                                    *
	 * TODO - Decide on and make weapons                               *
	 * TODO - Decide on and make armors + armor damage reduction system*
	 * TODO - Admin command suite {                                    *
	 * Ban - In progress                                               *
	 * Kick                                                            *
	 * Mute                                                            *
	 * Tp                                                              *
	 * Invsee                                                          *
	 * Clear Inventory}                                                *
	 * TODO - Update TODO list                                         *
	 * TODO - Add more TODOs                                           *
	 * TODO - I like TODOs                                             *
	 * TODO - That is all                                              *
	 * TODO - Just kidding, but now it is                              *
	 *******************************************************************/
	
	public void onEnable(){
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);

	    configFile = new File(getDataFolder(), "config.yml");
	    dataStorageFile = new File(getDataFolder(), "dataStorage.yml");
		
		try {
			firstRun();
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	    config = new YamlConfiguration();
	    dataStorage = new YamlConfiguration();
	    loadYamls();
		
		int armorPos = 0;
		int weaponPos = 0;

		/*********************************************
		**CREATE ARMORS AND POPULATE HASHMAPS/ARRAYS**
		*********************************************/
		ItemStack b50is = new ItemStack(Material.DIAMOND_PICKAXE);
		ItemMeta b50im = b50is.getItemMeta();
		String b50name = "§rBarett 50. Cal";
		b50im.setDisplayName(b50name);
		ArrayList<String> b50lore = new ArrayList<String>();
		b50lore.add("§bExplode heads from a hundred meters!");
		b50im.setLore(b50lore);
		b50is.setItemMeta(b50im);
		Weapon b50c = new Weapon(b50name, b50is, 18, weaponPos);
		weapons.add(b50c);
		weaponStacks.add(b50c.getItemStack());
		weaponMats.put(b50c.getType(), b50c);
		weaponPos++;
		
		ItemStack m1911is = new ItemStack(Material.STONE_AXE);
		ItemMeta m1911im = m1911is.getItemMeta();
		String m1911name = "§rM1911";
		m1911im.setDisplayName(m1911name);
		ArrayList<String> m1911lore = new ArrayList<String>();
		m1911lore.add("§aThe choice of the police force");
		m1911im.setLore(m1911lore);
		m1911is.setItemMeta(m1911im);
		Weapon m1911c = new Weapon(m1911name, m1911is, 5, weaponPos);
		weapons.add(m1911c);
		weaponStacks.add(m1911c.getItemStack());
		weaponMats.put(m1911c.getType(), m1911c);
		weaponPos++;
		
		ItemStack rocketis = new ItemStack(Material.STICK);
		ItemMeta rocketim = m1911is.getItemMeta();
		String rocketname = "§rM1911";
		m1911im.setDisplayName(m1911name);
		ArrayList<String> rocketlore = new ArrayList<String>();
		rocketlore.add("§aThe choice of the police force");
		rocketim.setLore(m1911lore);
		rocketis.setItemMeta(m1911im);
		Weapon rocket = new Weapon(rocketname, rocketis, 10, weaponPos);
		weapons.add(rocket);
		weaponStacks.add(rocket.getItemStack());
		weaponMats.put(rocket.getType(), rocket);
		weaponPos++;

		/*********************************************
		**CREATE ARMORS AND POPULATE HASHMAPS/ARRAYS**
		*********************************************/
		ItemStack pwrArmHelmis = new ItemStack(Material.DIAMOND_HELMET);
		ItemMeta pAHim = pwrArmHelmis.getItemMeta();
		String pAHName="§r§bPower Armor Helmet";
		pAHim.setDisplayName(pAHName);
		ArrayList<String> pAHlore = new ArrayList<String>();
		pAHlore.add("Get powered up!");
		pAHim.setLore(pAHlore);
		pwrArmHelmis.setItemMeta(pAHim);
		Armor pwrArmHelm = new Armor(pAHName, pwrArmHelmis, 5, armorPos);
		armors.add(pwrArmHelm);
		armorPos++;
		
		ItemStack pwrArmChestis = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemMeta pACim = pwrArmChestis.getItemMeta();
		String pACName="§r§bPower Armor Chestplate";
		pACim.setDisplayName(pACName);
		ArrayList<String> pAClore = new ArrayList<String>();
		pAClore.add("Get powered up!");
		pACim.setLore(pAClore);
		pwrArmChestis.setItemMeta(pACim);
		Armor pwrArmChest = new Armor(pACName, pwrArmChestis, 5, armorPos);
		armors.add(pwrArmChest);
		armorPos++;
		
		ItemStack pwrArmLegis = new ItemStack(Material.DIAMOND_LEGGINGS);
		ItemMeta pALim = pwrArmHelmis.getItemMeta();
		String pALName="§r§bPower Armor Helmet";
		pALim.setDisplayName(pALName);
		ArrayList<String> pALlore = new ArrayList<String>();
		pALlore.add("Get powered up!");
		pALim.setLore(pALlore);
		pwrArmLegis.setItemMeta(pALim);
		Armor pwrArmLeg = new Armor(pALName, pwrArmLegis, 5, armorPos);
		armors.add(pwrArmLeg);
		armorPos++;
		
		ItemStack pwrArmBootis = new ItemStack(Material.DIAMOND_BOOTS);
		ItemMeta pABim = pwrArmHelmis.getItemMeta();
		String pABName="§r§bPower Armor Boots";
		pABim.setDisplayName(pABName);
		ArrayList<String> pABlore = new ArrayList<String>();
		pABlore.add("Get powered up!");
		pABim.setLore(pABlore);
		pwrArmHelmis.setItemMeta(pABim);
		Armor pwrArmBoot = new Armor(pABName, pwrArmBootis, 5, armorPos);
		armors.add(pwrArmBoot);
		armorPos++;

		/**************************************
		**ASSIGN THE MATERIALS TO THE WEAPONS**
		**************************************/
		for(Weapon w : weapons){
			Material m = w.getItemStack().getType();
			weaponMats.put(m, w);
		}
	}
	public void onDisable(){
		saveYamls();
	}
	
	private void firstRun() throws Exception {
	    if(!configFile.exists()){
	        configFile.getParentFile().mkdirs();
	        copy(getResource("config.yml"), configFile);
	    }
	    if(!dataStorageFile.exists()){
	        dataStorageFile.getParentFile().mkdirs();
	        copy(getResource("dataStorage.yml"), dataStorageFile);
	    }
	}private void copy(InputStream in, File file) {
	    try {
	        OutputStream out = new FileOutputStream(file);
	        byte[] buf = new byte[1024];
	        int len;
	        while((len=in.read(buf))>0){
	            out.write(buf,0,len);
	        }
	        out.close();
	        in.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public static void saveYamls() {
	    try {
	        config.save(configFile);
	     //ERROR
	        dataStorage.save(dataStorageFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void saveYaml(FileConfiguration f) {
	    try {
	        if(f==config){
	        	f.save(configFile);
	        }else if(f==dataStorage){
	        	f.save(dataStorageFile);
	        }else return;
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	public void loadYamls() {
	    try {
	        config.load(configFile);
	        dataStorage.load(dataStorageFile);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public static String chatHeader = "§9§l[§3§lFallout§9§l] ";
	
	public static boolean isNumeric(String s){
		try  
		  {  
		    @SuppressWarnings("unused")
			double d = Double.parseDouble(s);
		  }  
		  catch(NumberFormatException nfe)  
		  {  
		    return false;  
		  } 
		return true;
	}

	int count = 0;
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("temp")) { // If the player typed /basic then do the following...
			if(args[0].equalsIgnoreCase("giveweapon")){
				if(isNumeric(args[1])){
					int w = Integer.parseInt(args[1]);
					if(w>weapons.size()){
						sender.sendMessage("There aren't that many weapons!");
					}else{
						Player p = (Player)sender;
						p.getInventory().addItem(weapons.get(w).getItemStack());
					}
				}else{
					sender.sendMessage("You must enter a number!");
				}
				return true;
			}else if(args[0].equalsIgnoreCase("givearmor")){
				if(isNumeric(args[1])){
					int a = Integer.parseInt(args[1]);
					if(a>armors.size()){
						sender.sendMessage("There aren't that many weapons!");
					}else{
						Player p = (Player)sender;
						p.getInventory().addItem(armors.get(a).getItemStack());
					}
				}else{
					sender.sendMessage("You must enter a number!");
				}
				return true;
			}
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("tpa")){
			if(sender.hasPermission("fallout.tpa")){
				if(args[0] != null && !(args.length >= 2)){
					if(getServer().getPlayer(args[0]) != null){
						sender.sendMessage(chatHeader + "§aTeleporting you to §e" + args[0] + "§a.");
						final Player p = getServer().getPlayer(args[0]);
						final Player ps = (Player)sender;
						SchedulerTaskTPDelay task = new SchedulerTaskTPDelay();
						task.setPlayerTo(p);
						task.setPlayer(ps);
						task.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(this, task, 10, 10));
					}else{
						sender.sendMessage(chatHeader + "§bThat player can't be found!");
					}
				}
			}
		}
		if(cmd.getName().equalsIgnoreCase("seen")){
			if(args.length==1){
				sender.sendMessage(chatHeader + "§aRetrieving information on §6" + args[0]);
				Player p = getServer().getPlayer(args[0]);
				if(p != null){
					//Do not calculate date last seen
					sender.sendMessage(chatHeader + "Player is online!");
					return true;
				}else if(dataStorage.contains("users." + args[0])){
					SimpleDateFormat f = new SimpleDateFormat("ss.mm.hh.dd.MM.yyyy");
					Date da = new Date();
					String nd = f.format(da);
					String[] nds = nd.split("\\.");
					String cs = nds[0];
					String cm = nds[1];
					String ch = nds[2];
					String cd = nds[3];
					String cM = nds[4];
					String cy = nds[5];
					String s = dataStorage.getString("users." + args[0] + ".lseen.sec");
					String m = dataStorage.getString("users." + args[0] + ".lseen.min");
					String h = dataStorage.getString("users." + args[0] + ".lseen.hou");
					String d = dataStorage.getString("users." + args[0] + ".lseen.day");
					String M = dataStorage.getString("users." + args[0] + ".lseen.mon");
					String y = dataStorage.getString("users." + args[0] + ".lseen.yea");
					int pcs = Integer.parseInt(cs);
					int pcm = Integer.parseInt(cm);
					int pch = Integer.parseInt(ch);
					int pcd = Integer.parseInt(cd);
					int pcM = Integer.parseInt(cM);
					int pcy = Integer.parseInt(cy);
					int ps = Integer.parseInt(s);
					int pm = Integer.parseInt(m);
					int ph = Integer.parseInt(h);
					int pd = Integer.parseInt(d);
					int pM = Integer.parseInt(M);
					int py = Integer.parseInt(y);
					int sy = pcy-py;
					int sM = pcM-pM;
					int sd = pcd-pd;
					int sh = pch-ph;
					int sm = pcm-pm;
					int ss = pcs-ps;
					if(sy < 0){
						sy*=-1;
					}
					if(sM < 0){
						sM*=-1;
					}
					if(sd < 0){
						sd*=-1;
					}
					if(sh < 0){
						sh*=-1;
					}
					if(sm < 0){
						sm*=-1;
					}
					if(ss < 0){
						ss*=-1;
					}
					String calcDate = "§6";
					int l = 0;
					if(sy > 0 && l != 3){
						if(sy==1){
							calcDate = calcDate + " §e" +  sy + " §6year";
						}else{
							calcDate = calcDate + " §e" +  sy + " §6years";
						}
						l++;
					}
					if(sM > 0 && l != 3){
						if(l==2){
							if(sM==1){
								calcDate = calcDate + " and §e" + sM + " §6month";
								sender.sendMessage("DEBUG - SM == 1 && L == 2");
							}else{
								calcDate = calcDate + " and §e" + sM + " §6months";
								sender.sendMessage("DEBUG - SM != 1 && L == 2");
							}
						}else{
							if(sM==1){
								calcDate = calcDate + " §e" + sM + " §6month";
								sender.sendMessage("DEBUG - SM == 1");
							}else{
								calcDate = calcDate + " §e" + sM + " §6months";
								sender.sendMessage("DEBUG - SM != 1");
							}
						}
						l++;
					}
					if(sd > 0 && l != 3){
						if(l==2){
							if(sd==1){
								calcDate = calcDate + " and §e" + sd + " §6day";
								sender.sendMessage("DEBUG - SD == 1 && L == 2");
							}else{
								calcDate = calcDate + " and §e" + sd + " §6days";
								sender.sendMessage("DEBUG - SD != 1 && L == 2");
							}
						}else{
							if(sd==1){
								calcDate = calcDate + " §e" + sd + " §6day";
								sender.sendMessage("DEBUG - SD == 1");
							}else{
								calcDate = calcDate + " §e" + sd + " §6days";
								sender.sendMessage("DEBUG - SD != 1");
							}
						}
						l++;
					}
					if(sh > 0 && l != 3){
						if(l==2){
							if(sh==1){
								calcDate = calcDate + " and §e" + sh + " §6hour";
								sender.sendMessage("DEBUG - SH == 1 && L == 2");
							}else{
								calcDate = calcDate + " and §e" + sh + " §6hours";
								sender.sendMessage("DEBUG - SH != 1 && L == 2");
							}
						}else{
							if(sh==1){
								calcDate = calcDate + " §e" + sh + " §6hour";
								sender.sendMessage("DEBUG - SH == 1");
							}else{
								calcDate = calcDate + " §e" + sh + " §6hours";
								sender.sendMessage("DEBUG - SH != 1");
							}
						}
						l++;
					}
					if(sm > 0 && l != 3){
						if(l==2){
							if(sm==1){
								calcDate = calcDate + " and §e" + sm + " §6minute";
								sender.sendMessage("DEBUG - Sm == 1 && L == 2");
							}else{
								calcDate = calcDate + " and §e" + sm + " §6minutes";
								sender.sendMessage("DEBUG - Sm != 1 && L == 2");
							}
							
						}else{
							if(sm==1){
								calcDate = calcDate + " §e" + sm + " §6minute";
								sender.sendMessage("DEBUG - Sm == 1");
							}else{
								calcDate = calcDate + " §e" + sm + " §6minutes";
								sender.sendMessage("DEBUG - Sm != 1");
							}
						}
						l++;
					}
					if(ss > 0 && l != 3){
						if(l==0){
							calcDate = calcDate + " §e" + sm + " §6seconds";
							sender.sendMessage("DEBUG - SS - L == 0");
						}else{
							calcDate = calcDate + " and §e" + sm + " §6seconds";
							sender.sendMessage("DEBUG - SS - L == 0");
						}
					}
					
					//Boxy unicode chat header below:
					//\u250F\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2513\n\u2503                               \u2503\n\u2517\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u251B
					//Converted version:
					sender.sendMessage("§c" + args[0] + "§6 was last seen " + calcDate + " ago.");
					sender.sendMessage("§6IPs: §c" + dataStorage.getList("users." + args[0] + ".ips").toString().replace("[", "").replace("]", ""));
					sender.sendMessage("§6Last IP: §c" + dataStorage.get("users." + args[0] + ".lastip"));
					return true;
				}else{
					sender.sendMessage(chatHeader + "§cPlayer §e" + args[0] +  " §cnot found!");
					return true;
				}
			}else{
				sender.sendMessage(chatHeader + "§cInvalid arguments: §e/seen {player}");
				return true;
			}
		}
		if(cmd.getName().equalsIgnoreCase("ban")){
			if(args.length == 0){
				sender.sendMessage(chatHeader + "§cInvalid arguments: §e/ban {player} [reason]");
				return true;
			}else if(args.length == 1){
				sender.sendMessage(chatHeader + "§aBanning §b" + args[0] + "§a.");
				long ti = System.currentTimeMillis();
				if(dataStorage.contains("bans." + args[0])){
					int t = dataStorage.getInt("bans." + args[0] + ".count") + 1;
					dataStorage.set("bans." + args[0] + "." + t + ".time", ti);
					dataStorage.set("bans." + args[0] + "." + ".bannedBy", sender.getName());
				}else{
					dataStorage.set("bans." + args[0] + ".1.time", ti);
					dataStorage.set("bans." + args[0] + "." + ".bannedBy", sender.getName());
				}
				return true;
			}else if(args.length > 1){
				if(args.toString().contains("-t")){
					int pos = Arrays.asList(args).indexOf("-t");
					String td = args[pos+1];
					if(td.matches("[1234567890smhdwmoy]+")){
						
					}else{
						sender.sendMessage(chatHeader + "Invalid time argument!");
					}
				}
			}
		}
		saveYamls();
		return false; 
	}
	
	@SuppressWarnings("unchecked")
	@EventHandler
	public static void onPlayerJoin(PlayerJoinEvent e){
		e.setJoinMessage("§9" + e.getPlayer().getName() + " §8has joined.");
		String tabName = "§9" + e.getPlayer().getName();
		if(tabName.length() > 16){
			tabName.substring(0, 16);
			e.getPlayer().setPlayerListName(tabName);
		}else{
			e.getPlayer().setPlayerListName(tabName);
		}
		//TODO - ADD PERMISSIONS PREFIX SYSTEM
		String dn = e.getPlayer().getDisplayName();
		e.getPlayer().setDisplayName("§4§k|§3§k|§6§lBOSS§3§k|§4§k| §6§l" + dn + "§r");
		String s = e.getPlayer().getAddress().getHostName();
		if(dataStorage.contains("users." + e.getPlayer() + ".ips")){
			List<String> ips = (List<String>) dataStorage.getList("users." + e.getPlayer() + ".ips");
			if(!(ips==null)){
				if(!(ips.contains(s))){
					ips.add(s);
					dataStorage.set("users." + e.getPlayer().getName() + ".ips", ips);
				}else return;
			}else{
				List<String> nips = new ArrayList<String>();
				nips.add(s);
				dataStorage.set("users." + e.getPlayer().getName() + ".ips", ips);
			}
		}else{
			List<String> ips = new ArrayList<String>();
			ips.add(s);
			dataStorage.set("users."+e.getPlayer().getName() + ".ips", ips);
		}
		dataStorage.set("users." + e.getPlayer().getName() + ".lastip", s);
		saveYamls();
	}
	
	@EventHandler
	public static void onPlayerQuit(PlayerQuitEvent e){
		DateFormat dateFormat = new SimpleDateFormat("ss.mm.hh.dd.MM.yyyy");
		Date d = new Date();
		String dn = dateFormat.format(d);
		String[] dns = dn.split("\\.");
		String name = e.getPlayer().getName();
		Location l = e.getPlayer().getLocation();
		double x = l.getX();
		double y = l.getY();
		double z = l.getZ();
		dataStorage.set("users." + name + ".locX", x);
		dataStorage.set("users." + name + ".locY", y);
		dataStorage.set("users." + name + ".locZ", z);
		dataStorage.set("users." + name + ".lseen", d);
		saveYaml(dataStorage);
		e.setQuitMessage("§9" + e.getPlayer().getName() + " §8 has left.");
	}
	
	@EventHandler
	public static void onBreak(BlockBreakEvent e){
		if(!(e.getPlayer().hasPermission("fallout.break")) || e.getPlayer().isOp()){
			e.setCancelled(true);
		}else return;
	}
	
	//TODO - Optimize weapon detection
	@EventHandler
	public static void onPlayerInteract(PlayerInteractEvent e){
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(e.getItem().getType()==Material.DIAMOND_PICKAXE){
				Snowball s = e.getPlayer().launchProjectile(Snowball.class);
				s.setVelocity(e.getPlayer().getLocation().getDirection().multiply(4));
				projectiles.put(s, weapons.get(0));
			}else if(e.getItem().getType() == Material.STONE_AXE){
				Snowball s = e.getPlayer().launchProjectile(Snowball.class);
				s.setVelocity(e.getPlayer().getLocation().getDirection().multiply(4));
				projectiles.put(s, weaponMats.get(e.getItem().getType()));
			}else if(e.getItem().getType() == Material.STICK){
				Snowball s = e.getPlayer().launchProjectile(Snowball.class);
				s.setVelocity(e.getPlayer().getLocation().getDirection().multiply(4));
				projectiles.put(s, weaponMats.get(e.getItem().getType()));
			}else return;
		}else return;
	}
	
	@EventHandler
	public static void onDamage(EntityDamageByEntityEvent e){
		if(e.getCause().equals(DamageCause.PROJECTILE) && e.getDamager() instanceof Snowball){
			if(projectiles.containsKey(e.getDamager())){
				Weapon w = projectiles.get(e.getDamager());
				Entity en = e.getEntity();
				int dmg = w.getDamage();
				if(en instanceof LivingEntity){
					((LivingEntity) en).damage(dmg);
				}else return;
			}else return;
		}else return;
	}
	
	@EventHandler
	public static void onProjectileHit(ProjectileHitEvent e){
		if(projectiles.containsKey(e.getEntity())){
			if(projectiles.get(e.getEntity()).getItemStack().getType() == Material.STICK){
				Snowball s = (Snowball)e.getEntity();
				s.getWorld().createExplosion(s.getLocation().getX(), s.getLocation().getY(), s.getLocation().getZ(), (float) 0.8, false, false);
			}
			projectiles.remove(e.getEntity());
		}else return;
	}

}