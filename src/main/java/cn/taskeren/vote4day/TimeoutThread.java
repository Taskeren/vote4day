package cn.taskeren.vote4day;

import java.util.Collection;

public class TimeoutThread extends Thread {

	public static final long TIMEOUT = 20 * 60 * 5;

	public void run() {
		while(Vote4day.instance.isEnabled()) {
			Collection<VoteTicket> tickets = Vote4day.instance.mgr.getTickets();
			tickets.forEach(ticket -> {
				final long lastUpdated = ticket.getLastUpdated();
				final long timeoutFixed = lastUpdated + TIMEOUT;
				final long current = ticket.getWorld().getFullTime();
				if(timeoutFixed < current) {
					ticket.setTimeout();
				}
			});
		}
	}

}
