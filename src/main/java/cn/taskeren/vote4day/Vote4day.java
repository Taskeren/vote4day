package cn.taskeren.vote4day;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Vote4day extends JavaPlugin {

	static Vote4day instance;

	final VoteManager mgr = new VoteManager();
	FileConfiguration config = getConfig();
	YamlConfiguration lang;

	static final String LANG_START_VOTING_1 = "vote.start-voting.1";
	static final String LANG_START_VOTING_2 = "vote.start-voting.2";
	static final String LANG_PLAYER_VOTE_SUCCESS = "vote.player-vote.success";
	static final String LANG_PLAYER_VOTE_DUPLICATED = "vote.player-vote.duplicated";
	static final String LANG_WORLD_VOTE_PASS = "vote.world-vote-pass";
	static final String LANG_PLAYER_UNVOTE_SUCCESS = "vote.player-unvote.success";
	static final String LANG_PLAYER_UNVOTE_MISSING = "vote.player-unvote.missing";

	@Override
	public void onLoad() {
		instance = this;

		MetricsLite metrics = new MetricsLite(this, 9859);
	}

	@Override
	public void onEnable() {
		// 加载配置文件
		config.addDefault("percent-pass", 0.5f);
		config.options().copyDefaults(true);
		saveConfig();

		// 加载语言文件
		lang = YamlConfiguration.loadConfiguration(new File("./plugins/vote4day/language.yml"));
		lang.addDefault(LANG_START_VOTING_1, "&e{PLAYER} &7has just started the vote for the day.");
		lang.addDefault(LANG_START_VOTING_2, "&7Type &6/vday &7to vote.");
		lang.addDefault(LANG_PLAYER_VOTE_SUCCESS, "&e{PLAYER} &7voted! &6{PERCENT} ({VOTED}/{TOTAL})");
		lang.addDefault(LANG_PLAYER_VOTE_DUPLICATED, "&7&oDon't duplicately vote!");
		lang.addDefault(LANG_WORLD_VOTE_PASS, "&6&oRise and Shine!");
		lang.addDefault(LANG_PLAYER_UNVOTE_SUCCESS, "&e{PLAYER} &7unvoted! &6{PERCENT} ({VOTED}/{TOTAL})");
		lang.addDefault(LANG_PLAYER_UNVOTE_MISSING, "&7&oYou didn't vote.");
		lang.options().copyDefaults(true);
		try {
			lang.save(new File("./plugins/vote4day/language.yml"));
		} catch (IOException e) {
			getLogger().warning("Failed to save langugage.yml");
			e.printStackTrace();
		}

		new TimeoutThread().start();

		//noinspection ConstantConditions
		this.getServer().getPluginCommand("vday").setExecutor(new CommandVote(this));
		//noinspection ConstantConditions
		this.getServer().getPluginCommand("unvday").setExecutor(new CommandUnvote(this));
	}

	@Override
	public void onDisable() {
	}
}
