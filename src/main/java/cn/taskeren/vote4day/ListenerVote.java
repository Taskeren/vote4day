package cn.taskeren.vote4day;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ListenerVote implements Listener {

	@EventHandler
	public void playerChangeDim(PlayerChangedWorldEvent event) {
		final World world = event.getFrom();
		final Player player = event.getPlayer();
		Vote4day.instance.mgr.unvote(world, player, true);
	}

	@EventHandler
	public void playerQuit(PlayerQuitEvent event) {
		final Player player = event.getPlayer();
		Vote4day.instance.mgr.getWorldsExists().forEach(world -> {
			Vote4day.instance.mgr.unvote(world, player, true);
		});
	}

}
