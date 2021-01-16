package cn.taskeren.vote4day;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandUnvote implements CommandExecutor {

	private final Vote4day plugin;

	CommandUnvote(Vote4day v4d) {
		this.plugin = v4d;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			World world = ((Player) sender).getWorld();
			plugin.mgr.unvote(world, sender, false);
			return true;
		} else {
			sender.sendMessage("Player Only!");
		}
		return false;
	}

}
