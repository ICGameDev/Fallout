package iCGameDev.MC.Fallout.Main;

import iCGameDev.MC.Fallout.Armors.Armor;
import iCGameDev.MC.Fallout.Weapons.Weapon;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.ChatColor;
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
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	static HashMap<Snowball, Weapon> projectiles = new HashMap<Snowball, Weapon>();
	static HashMap<Material, Weapon> weaponMats = new HashMap<Material, Weapon>();
	static ArrayList<Weapon> weapons = new ArrayList<Weapon>();
	static ArrayList<Armor> armors = new ArrayList<Armor>();
	static ArrayList<ItemStack> weaponStacks = new ArrayList<ItemStack>();
	
	public void onEnable(){
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
		
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
		
		ItemStack pwrArmChestis = new ItemStack(Material.DIAMOND_HELMET);
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
		
		ItemStack pwrArmLegis = new ItemStack(Material.DIAMOND_HELMET);
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
		
		ItemStack pwrArmBootis = new ItemStack(Material.DIAMOND_HELMET);
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
			}
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("tpa")){
			if(sender.hasPermission("fallout.tpa")){
				if(args[0] != null && !(args.length >= 2)){
					if(getServer().getPlayer(args[0]) != null){
						sender.sendMessage(chatHeader + "§aTeleporting you to §e" + args[0] + "§a.");
						Player p = getServer().getPlayer(args[0]);
						Player ps = (Player)sender;
						ps.getLocation().setX(p.getLocation().getX());
						ps.getLocation().setY(p.getLocation().getY());
						ps.getLocation().setZ(p.getLocation().getZ());
					}else{
						sender.sendMessage(chatHeader + "§bThat player can't be found!");
					}
				}
			}
		}
		return false; 
	}
	
	@EventHandler
	public static void onPlayerInteract(PlayerInteractEvent e){
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(e.getItem().getType()==Material.DIAMOND_PICKAXE){
				Snowball s = e.getPlayer().launchProjectile(Snowball.class);
				s.setVelocity(e.getPlayer().getLocation().getDirection().multiply(4));
				projectiles.put(s, weapons.get(0));
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
			projectiles.remove(e.getEntity());
		}else return;
	}

}