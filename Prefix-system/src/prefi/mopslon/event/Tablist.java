package prefi.mopslon.event;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import prefi.mopslon.main.Main;

import net.md_5.bungee.api.ChatColor;

public class Tablist {
	

	public static HashMap<String, Scoreboard> scoreboards = new HashMap<>();
	
	public static void setScoreboard(Player p) {
		Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
		FileConfiguration config = Main.getPlugin().getConfig();
		if(!config.contains("red."+p.getUniqueId())) {
			if(!config.contains("yellow."+p.getUniqueId())) {
				registerNewRankedTeam(sb, "001!GameMaster", "§4§lGameMaster §0§l| §4§l", "§r", "GameMaster", ChatColor.DARK_RED);
				registerNewRankedTeam(sb, "005Beta-Tester", "§5Beta-Teste §0§l| §5", "§r", "Beta-Tester", ChatColor.LIGHT_PURPLE);
				registerNewRankedTeam(sb, "999Default", "§7Abenteurer §0§l| §7", "§r", "abenteurer", ChatColor.GRAY);
			}
		}
	}

	private static void registerNewRankedTeam(Scoreboard sb, String teamName, String teamPrefix, String teamSuffix, String permission, ChatColor color) {
		Team t = sb.registerNewTeam(teamName);
		t.setPrefix(teamPrefix);
		t.setSuffix(teamSuffix);
		
		if(!(permission.equalsIgnoreCase("NONE"))) {
			if(!Bukkit.getOnlinePlayers().equals(null)) {
				for (Player all : Bukkit.getOnlinePlayers()) {
					if((sb.getEntryTeam(all.getName()) == null) && all.hasPermission(permission)) {
						t.addEntry(all.getName());
						String p = color + all.getName();
						all.setPlayerListName(teamPrefix + p + teamSuffix);
						all.setDisplayName(teamPrefix + p + teamSuffix);
						all.setCustomName(teamPrefix + p + teamSuffix);
						all.setCustomNameVisible(true);
					}
				}
			}
		}
	}

}