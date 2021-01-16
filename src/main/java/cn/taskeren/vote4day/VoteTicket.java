package cn.taskeren.vote4day;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import java.util.HashSet;
import java.util.Set;

import static cn.taskeren.vote4day.Vote4day.*;

public class VoteTicket {

	private final World world;
	private final Set<String> voters;

	private long lastUpdated = 0;

	VoteTicket(World world) {
		this.world = world;
		this.voters = new HashSet<>();

	}

	public World getWorld() {
		return this.world;
	}

	private void resetLastUpdated() {
		this.lastUpdated = world.getFullTime();
	}

	public long getLastUpdated() {
		return this.lastUpdated;
	}

	public void setTimeout() {
		instance.mgr.removeTicket(this);
	}

	private int getTotalPlayers() {
		return world.getPlayers().size();
	}

	private int getVotedPlayers() {
		return voters.size();
	}

	private float getRatio() {
		return (float) getVotedPlayers() / (float) getTotalPlayers();
	}

	private void update() {
		if(getRatio() > instance.config.getDouble("percent-pass")) {
			// 投票通过
			world.setTime(1000);
			if(!world.isClearWeather()) {
				world.setWeatherDuration(20);
			}
			broadcast(instance.lang.getString(LANG_WORLD_VOTE_PASS));
			voters.clear();
		}

		// 刷新时间
		resetLastUpdated();
	}

	boolean vote(CommandSender sender) {
		boolean r = voters.add(sender.getName());
		if(r) {
			// 提出投票
			if(voters.size() == 1) {
				String s1 = instance.lang.getString(LANG_START_VOTING_1).replace("{PLAYER}", sender.getName());
				String s2 = instance.lang.getString(LANG_START_VOTING_2);
				broadcast(s1);
				broadcast(s2);
			}

			// 投票提示
			String str = instance.lang.getString(LANG_PLAYER_VOTE_SUCCESS)
					.replace("{PLAYER}", sender.getName())
					.replace("{PERCENT}", getRatio()*100+"%")
					.replace("{VOTED}", Integer.toString(getVotedPlayers()))
					.replace("{TOTAL}", Integer.toString(getTotalPlayers()));
			broadcast(str);

			update();
		}
		else {
			String str = instance.lang.getString(LANG_PLAYER_VOTE_DUPLICATED);
			privateMsg(str, sender);
		}
		return r;
	}

	boolean unvote(CommandSender sender, boolean isAuto) {
		boolean r = voters.remove(sender.getName());
		if(!r) {
			// MISSING
			if(!isAuto) { // NOT FROM EVENT
				String str = instance.lang.getString(LANG_PLAYER_UNVOTE_MISSING);
				privateMsg(str, sender);
			}
		}
		else {
			String str = instance.lang.getString(LANG_PLAYER_UNVOTE_SUCCESS)
					.replace("{PLAYER}", sender.getName())
					.replace("{PERCENT}", getRatio()*100+"%")
					.replace("{VOTED}", Integer.toString(getVotedPlayers()))
					.replace("{TOTAL}", Integer.toString(getTotalPlayers()));
			broadcast(str);
		}
		return r;
	}

	private void broadcast(String msg) {
		world.getPlayers().forEach(p -> {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
		});
	}

	private void privateMsg(String msg, CommandSender sender) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
	}

}
