package cn.taskeren.vote4day;

import org.bukkit.World;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class VoteManager {

	private final Map<World, VoteTicket> votes;

	VoteManager() {
		this.votes = new HashMap<>();
	}

	private VoteTicket getOrCreateTicket(World world) {
		if(!votes.containsKey(world)) {
			votes.put(world, new VoteTicket(world));
		}
		return votes.get(world);
	}

	/**
	 * 投票
	 * @param world  给定世界
	 * @param sender 给定玩家
	 */
	public void vote(World world, CommandSender sender) {
		final VoteTicket vt = getOrCreateTicket(world);
		vt.vote(sender);
	}

}
