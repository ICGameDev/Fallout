package iCGameDev.MC.Fallout.Armors;

import org.bukkit.inventory.ItemStack;

public class Armor {
	
	public int armor;
	public int type;
	public ItemStack is;
	public String name;
	
	public Armor(String name, ItemStack is, int armor, int type){
		this.armor=armor;
		this.type=type;
		this.is=is;
		this.name=name;
	}
	
	public String getName(){
		return name;
	}
	
	public int getArmor(){
		return armor;
	}
	
	public int getWeapon(){
		return type;
	}
	
	public ItemStack getItemStack(){
		return is;
	}

}
