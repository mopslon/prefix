package prefi.mopslon.main;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import prefi.mopslon.command.Command;
import prefi.mopslon.event.Event;
import prefi.mopslon.main.Main;
import prefi.mopslon.event.Tablist;

public class Main extends JavaPlugin{
	private static Main plugin;

	public void onEnable() {
		plugin = this;

		
		PluginManager PluginManiger = Bukkit.getPluginManager();
		PluginManiger.registerEvents(new Event<Object>(), this);
		getCommand("unred").setExecutor((CommandExecutor) new Command());
		
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				Bukkit.getOnlinePlayers().forEach(all -> Tablist.setScoreboard(all));
			}
		}, 20, 120);
	
	}
	
	public static Main getPlugin() {
		return plugin;
	}
}
