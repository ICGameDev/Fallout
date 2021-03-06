package iCGameDev.MC.Fallout.Weapons;

import org.bukkit.inventory.ItemStack;

public class Weapon extends ItemStack{
	
	public int dmg;
	public int type;
	public ItemStack is;
	public String name;
	
	public Weapon(String name, ItemStack is, int dmg, int type){
		this.dmg=dmg;
		this.type=type;
		this.is=is;
		this.name=name;
	}
	
	public String getName(){
		return name;
	}
	
	public int getDamage(){
		return dmg;
	}
	
	public int getWeapon(){
		return type;
	}
	
	public ItemStack getItemStack(){
		return is;
	}

}