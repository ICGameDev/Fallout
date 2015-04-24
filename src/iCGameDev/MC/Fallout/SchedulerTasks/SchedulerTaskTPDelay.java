package iCGameDev.MC.Fallout.SchedulerTasks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SchedulerTaskTPDelay implements Runnable {
	private int id;
	private Player p;
	private Player pt;
	public int getId() {
		return id;
	}	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setPlayer(Player p){
		this.p=p;
	}
	
	public void setPlayerTo(Player pt){
		this.pt=pt;
	}
	
	int count = 10;
	
	public void run() {
		if(count==0){
			Location lt = pt.getLocation();
			p.getLocation().setWorld(lt.getWorld());
			p.getLocation().setX(lt.getX());
			p.getLocation().setY(lt.getY());
			p.getLocation().setZ(lt.getZ());
			Bukkit.getScheduler().cancelTask(id);
		}else{
			p.sendMessage("Teleporting in " + count + "seconds");
		}
		count--;
	}
}