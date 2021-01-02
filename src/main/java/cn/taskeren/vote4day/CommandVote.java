package cn.taskeren.vote4day;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandVote implements CommandExecutor {

	private final Vote4day plugin;

	CommandVote(Vote4day v4d) {
		this.plugin = v4d;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			World world = ((Player) sender).getWorld();
			plugin.mgr.vote(world, sender);
			return true;
		} else {
			sender.sendMessage("Player Only!");
		}
		return false;
	}

}
