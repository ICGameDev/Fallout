package iCGameDev.MC.Fallout.Main;

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
	static ArrayList<Weapon> weapons = new ArrayList<Weapon>();
	static ArrayList<ItemStack> weaponStacks = new ArrayList<ItemStack>();
	
	public void onEnable(){
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
		
		ItemStack b50is = new ItemStack(Material.DIAMOND_PICKAXE);
		ItemMeta b50im = b50is.getItemMeta();
		b50im.setDisplayName("Barett 50. Cal");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "Explode heads from a hundred meters!");
		b50im.setLore(lore);
		b50is.setItemMeta(b50im);
		Weapon b50c = new Weapon(b50is, 18, 20);
		weapons.add(b50c);
		weaponStacks.add(b50c.getItemStack());
	}
	public void onDisable(){
		
	}
	
	public static String chatHeader = "¤9¤l[¤b¤3Fallout¤9¤l] ";
	
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
						sender.sendMessage(chatHeader + "¤aTeleporting you to ¤e" + args[0] + "¤a.");
						Player p = getServer().getPlayer(args[0]);
						Player ps = (Player)sender;
						ps.getLocation().setX(p.getLocation().getX());
						ps.getLocation().setY(p.getLocation().getY());
						ps.getLocation().setZ(p.getLocation().getZ());
					}else{
						sender.sendMessage(chatHeader + "¤bThat player can't be found!");
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
