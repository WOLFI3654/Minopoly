package de.wolfi.minopoly.components.minigames;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import de.wolfi.minopoly.Main;
import de.wolfi.minopoly.events.CountdownFinishedEvent;
import de.wolfi.minopoly.utils.I18nHelper;
import de.wolfi.utils.TitlesAPI;

public class Countdown implements Listener {

	public static Countdown countdown = new Countdown();

	public Thread t;
	private int time;
	private String message;
	private boolean running;
	public static int i;

	public void load() {
		time = 15;
		running = false;
		message = "§4[§5Countdown§4]";
		Main.getMain().getServer().getPluginManager().registerEvents(countdown, Main.getMain());

		t = new Thread(getCountdown(), "Countdown");
	}

	public void start() {
		running = true;
		t.start();
	}

	public int getTime() {
		return i;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void stop() {
		running = false;
	}

	public void disable() {
		t.interrupt();
	}

	public boolean isRunning() {
		return running;
	}

	public Runnable getCountdown() {
		return new Runnable() {

			@Override
			public void run() {
				while(!running)
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					};
				while (running) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e1) {
						return;
					}
					if (running) {
						for (i = time; i > 0; i--) {

							for (Player p : Bukkit.getOnlinePlayers()) {
								p.setLevel(i);
							}
							if (i % 5 == 0 || i < 4) {
								I18nHelper.broadcast("minopoly.ferdinand.countdown."+i, true);
								Bukkit.broadcastMessage(message + " §6Spiel startet in §l" + i + "§r§6 Sekunden!");
								for (Player p : Bukkit.getOnlinePlayers()) {

									p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
									title(p, "{\"text\":\"Spiel startet in\",\"color\":\"gold\"}", false);
									title(p, "{\"text\":\"" + i + "\",\"color\":\"aqua\"}", true);
								}
							}
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						if (running) {
							for (Player p : Bukkit.getOnlinePlayers()) {
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
								title(p, "{\"text\":\"Viel Spaß!\",\"color\":\"red\"}", false);
								title(p, "{\"text\":\"" + p.getDisplayName() + "\",\"color\":\"aqua\"}", true);
								p.setLevel(0);
							}
							I18nHelper.broadcast("minopoly.ferdinand.countdown.start", true);
							Bukkit.getPluginManager().callEvent(new CountdownFinishedEvent());
						}
					}
					Countdown.this.stop();
					Countdown.this.disable();
				}
				

			}

		};
	}

	public void title(Player player, String title, boolean subtile) {
		if (subtile)
			TitlesAPI.sendSubtitle(player, 0, 21, 20, title);
		else
			TitlesAPI.sendTitle(player, 0, 21, 20, title);
	}


}
