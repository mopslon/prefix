package prefi.mopslon.event;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import prefi.mopslon.main.Main;
import net.md_5.bungee.api.ChatColor;

public class Event<Int> implements Listener{
	
	FileConfiguration config = Main.getPlugin().getConfig();

	private String register( String permission,  ChatColor color, Player p, String messige) {
		String messige1 = messige;
		
		if(!(permission.equalsIgnoreCase("NONE"))) {
			if((p.hasPermission(permission))) {
				messige1 = color + messige;
				return messige1;
			}else messige1 = color + messige;
		}
		return messige1;
	}
	private String setJm(Player p, String messige) {
		
		if((p.hasPermission("GameMaster"))) {
			String messige1 = messige;
			messige1 = ChatColor.DARK_RED + "§4§lGameMaster §0§l| §r§4§l" + p.getName() + " joined the game";
			return messige1;
		}else if((p.hasPermission("Beta-Tester"))) {
			String messige1 = messige;
			messige1 = ChatColor.LIGHT_PURPLE + "§5Beta-Teste §0§l| §r§5" + p.getName() + " joined the game";
			return messige1;
		}else {
			String messige1 = messige;
			messige1 = ChatColor.GRAY + "§7Abenteurer §0§l| §r§7" + p.getName() + " joined the game";
			return messige1;
		}
	}
	private String setQm(Player p, String messige) {
		
		if((p.hasPermission("GameMaster"))) {
			String messige1 = messige;
			messige1 = ChatColor.DARK_RED + "§4§lGameMaster §0§l| §r§4§l" + p.getName() + " left the Game";
			return messige1;
		}else if((p.hasPermission("Beta-Tester"))) {
			String messige1 = messige;
			messige1 = ChatColor.LIGHT_PURPLE + "§5Beta-Teste §0§l| §r§5" + p.getName() + " left the Game";
			return messige1;
		}else {
			String messige1 = messige;
			messige1 = ChatColor.GRAY + "§7Abenteurer §0§l| §r§7" + p.getName() + " left the Game";
			return messige1;
		}
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Bukkit.getOnlinePlayers().forEach(all -> Tablist.setScoreboard(all));
		e.setJoinMessage(setJm(e.getPlayer(), e.getJoinMessage()));
	}
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Bukkit.getOnlinePlayers().forEach(all -> Tablist.setScoreboard(all));
		e.setQuitMessage(setQm(e.getPlayer(), e.getQuitMessage()));
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		String message = e.getMessage();
		if(p.hasPermission("GameMaster")) {
		message = register("GameMaster",  ChatColor.RED, e.getPlayer(), message);
		}else if(p.hasPermission("Beta-Tester")) {
		message = register("Beta-Tester",  ChatColor.LIGHT_PURPLE, e.getPlayer(), message);
		}else {
			message = ChatColor.GRAY + message;
		}
		if(p.hasPermission("coloredchat")) {
			message = message.replace("&", "§");
			e.setFormat(p.getDisplayName() + ": " + message);
		}else {
			e.setFormat(p.getDisplayName() + ": " + message);
		}
		
		e.setMessage(message);
	}
	private int taskID;
	public void Kill(Player p , String teamPrefix , ChatColor color) {
		Bukkit.getOnlinePlayers().forEach(all -> Tablist.setScoreboard(all));
		Bukkit.getScheduler().cancelTask(taskID);
		config.set("red." +p.getUniqueId(), "true");
		Main.getPlugin().saveConfig();
		p.setDisplayName(teamPrefix + ChatColor.RED + p.getName() + "§r");
		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask( Main.getPlugin() , new Runnable() {
			Integer r = 6;
			@Override
			public void run() {
				r--;
				config.set("red." +p.getUniqueId(), "true");
				Main.getPlugin().saveConfig();
				if(p.hasPermission("GameMaster")) {
					p.setDisplayName(color + teamPrefix + p.getName());
					config.set("red." +p.getUniqueId(), null);
					Bukkit.getScheduler().cancelTask(taskID);
					Main.getPlugin().saveConfig();
				}
				if(r == 0) {
					p.setDisplayName(color + teamPrefix + p.getName());
					config.set("red." +p.getUniqueId(), null);
					Bukkit.getScheduler().cancelTask(taskID);
					Main.getPlugin().saveConfig();
				}
			}
		} , 20, 12000);
		
	}
	private int taskID1;
	public void hurt(Player p , String teamPrefix , ChatColor color) {
		Bukkit.getOnlinePlayers().forEach(all -> Tablist.setScoreboard(all));
		p.setDisplayName(teamPrefix + ChatColor.YELLOW + p.getName() + "§r");
		if(config.contains("red." +p.getUniqueId())) {
			Main.getPlugin().saveConfig();
			p.setDisplayName(teamPrefix + ChatColor.RED + p.getName() + "§r");
			return;
		}
		Bukkit.getScheduler().cancelTask(taskID1);
		taskID1 = Bukkit.getScheduler().scheduleSyncRepeatingTask( Main.getPlugin() , new Runnable() {
			Integer r2 = 6;
			@Override
			public void run() {
				r2--;
				config.set("yellow." +p.getUniqueId(), "true");
				if(config.contains("red." +p.getUniqueId())) {
					p.setDisplayName(teamPrefix + ChatColor.RED + p.getName() + "§r");
					config.set("yellow." +p.getUniqueId(), null);
					Bukkit.getScheduler().cancelTask(taskID1);
					Main.getPlugin().saveConfig();
				}
				if(r2 == 0) {
					p.setDisplayName(color + teamPrefix + p.getName());
					config.set("yellow." +p.getUniqueId(), null);
					Bukkit.getScheduler().cancelTask(taskID1);
					Main.getPlugin().saveConfig();
				}
			}
		} , 20, 12000);
		
	}
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player) {
			if(e.getDamager() instanceof Player) {
				Player p = (Player) e.getDamager();
				if((p.hasPermission("GameMaster"))) {
					return;
				}else if((p.hasPermission("Beta-Tester"))) {
					hurt(p, "§5Beta-Teste §0§l| §r§5",  ChatColor.LIGHT_PURPLE);
				}else {
					hurt(p, "§7Abenteurer §0§l| §r§7",  ChatColor.GRAY);
				}
			}
		}
	}

	@EventHandler
	public void onTarget(ProjectileHitEvent e) {
		if(e.getEntity().getShooter() instanceof Player) {
			if(e.getHitEntity() instanceof Player) {
				Player p = (Player) e.getEntity().getShooter();
				if((p.hasPermission("GameMaster"))) {
					return;
				}else if((p.hasPermission("Beta-Tester"))) {
					hurt(p, "§5Beta-Teste §0§l| §r§5",  ChatColor.LIGHT_PURPLE);
				}else {
					hurt(p, "§7Abenteurer §0§l| §r§7",  ChatColor.GRAY);
				}
			}
		}
	}
	
	@EventHandler
	public void onKill(PlayerDeathEvent e) {
		if(e.getEntity().getKiller() instanceof Player) {
			Player p = e.getEntity().getKiller();
			if((p.hasPermission("GameMaster"))) {
				return;
			}else if((p.hasPermission("Beta-Tester"))) {
				Kill(p, "§5Beta-Teste §0§l| §r§5",  ChatColor.LIGHT_PURPLE);
			}else {
				Kill(p, "§7Abenteurer §0§l| §r§7",  ChatColor.GRAY);
			}
		}
		if(e.getEntity().getKiller() instanceof Entity) {
		e.setDeathMessage(e.getEntity().getKiller().getDisplayName() + " §3hat den Spieler§r " + e.getEntity().getDisplayName() + " §3getötet§r");
		}else {
			e.setDeathMessage( e.getEntity().getDisplayName() + " §3ist gestorben");
		}
	}
	
}
