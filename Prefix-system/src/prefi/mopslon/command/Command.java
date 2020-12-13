package prefi.mopslon.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import prefi.mopslon.main.Main;

public class Command implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			Player p = (Player) sender;
			if(player.hasPermission("unred")) {
				if(args.length == 0) {
					FileConfiguration config = Main.getPlugin().getConfig();
					if(config.contains("red."+p.getUniqueId())) {

						if((p.hasPermission("GameMaster"))) {
							p.setDisplayName( "§4§lGameMaster §0§l| §r§4§l" + p.getName());
						}else if((p.hasPermission("Beta-Tester"))) {
							p.setDisplayName( "§5Beta-Teste §0§l| §r§5" + p.getName());
						}else {
							p.setDisplayName( "§7Abenteurer §0§l| §r§7" + p.getName());
						}
						config.set("red." +p.getUniqueId(), null);
						Main.getPlugin().saveConfig();
					}
					if(config.contains("yellow."+p.getUniqueId())) {

						if((p.hasPermission("GameMaster"))) {
							p.setDisplayName( "§4§lGameMaster §0§l| §r§4§l" + p.getName());
						}else if((p.hasPermission("Beta-Tester"))) {
							p.setDisplayName( "§5Beta-Teste §0§l| §r§5" + p.getName());
						}else {
							p.setDisplayName( "§7Abenteurer §0§l| §r§7" + p.getName());
						}
						config.set("yellow." +p.getUniqueId(), null);
						Main.getPlugin().saveConfig();
						
					}
					p.sendMessage("§3Du bist jetzt wieder normal");
				}else
					player.sendMessage("§cBitte benutze §3/unred§c!");
			}else
				player.sendMessage("§cDu hast keine rechte dafür!");
		}
		return false;
	}
	
}