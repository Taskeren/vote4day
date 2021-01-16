package cn.taskeren.vote4day;

import com.google.common.collect.Sets;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

	public void unvote(World world, CommandSender sender, boolean isAuto) {
		final VoteTicket vt = getOrCreateTicket(world);
		vt.unvote(sender, isAuto);
	}

	public void removeTicket(VoteTicket ticket) {
		removeTicket(ticket.getWorld());
	}

	public void removeTicket(World world) {
		VoteTicket ticket = this.votes.remove(world);
	}

	public Set<World> getWorldsExists() {
		return this.votes.keySet();
	}

	public Collection<VoteTicket> getTickets() {
		return this.votes.values();
	}

}
