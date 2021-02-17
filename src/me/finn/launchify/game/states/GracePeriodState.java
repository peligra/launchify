package me.finn.launchify.game.states;

import me.finn.launchify.Launchify;
import me.finn.launchify.game.Game;
import me.finn.launchify.game.GameState;
import me.finn.launchify.game.GameStateType;
import me.finn.launchify.game.LaunchPlayer;
import me.finn.launchify.utils.CustomRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.logging.Level;

public class GracePeriodState extends GameState {

    private Launchify pl;
    private Game game;
    Integer time = 5;
    CustomRunnable runnable;

    public GracePeriodState(Game game, Launchify pl) {
        this.pl = pl;
        this.game = game;
    }

    @Override
    public void onEnable(Launchify pl) {
        super.onEnable(pl);
        game.setTimeLeft(5);
        game.broadcastMessage("&6⛏ &fStarted &6grace period!");
        game.broadcastMessage("&b⚐ &fGrace period ends in &b5 &fseconds..");

        for (LaunchPlayer bp : game.getPlayers()) {
            pl.su.release(bp.getPlayer().getLocation());
            bp.getPlayer().addPotionEffect(
                    new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, false, false));
            bp.getPlayer().addPotionEffect(
                    new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
            bp.getPlayer().teleport(pl.am.getRandomSpawnLocation(game.getArena()));
        }
    }

    @Override
    public void onDisable(Launchify pl) {
        super.onDisable(pl);

        for (LaunchPlayer bp : game.getPlayers()) {
            bp.getPlayer().removePotionEffect(PotionEffectType.SPEED);
            bp.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
        }
    }

    @Override
    public void handleTick() {
        if (game.getTimeLeft() == 0) {
            Bukkit.getLogger().log(Level.INFO, "Started game in arena " + game.getArena().getName());
            game.setGameState(new ActiveGameState(game, pl));
        }
    }

    @Override
    public GameStateType getType() {
        return GameStateType.GRACE;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        if (pl.gm.getGameFromPlayer(p) == game) {
            game.removePlayer(p);
            e.setQuitMessage("");
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();

            if (pl.gm.getGameFromPlayer(p) == game) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (pl.gm.getGameFromPlayer(p) == game) {
            if (pl.gm.getLaunchPlayerFromPlayer(p) != null && pl.gm.getLaunchPlayerFromPlayer(p).isAlive()) {
                if (e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.LIME_CONCRETE) {
                    p.setVelocity(p.getLocation().getDirection().multiply(0.75));
                    p.setVelocity(new Vector(p.getVelocity().getX(), 1.75D, p.getVelocity().getZ()));
                    pl.su.launch(p.getLocation());
                }
                if (e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.GREEN_CONCRETE) {
                    e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(1.8));
                    e.getPlayer().setVelocity(new Vector(e.getPlayer().getVelocity().getX(), 1.1D, e.getPlayer().getVelocity().getZ()));
                    pl.su.launch(p.getLocation());
                }
                if (e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.YELLOW_CONCRETE) {
                    e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(3.5));
                    e.getPlayer().setVelocity(new Vector(e.getPlayer().getVelocity().getX(), 1.D, e.getPlayer().getVelocity().getZ()));
                    pl.su.launch(p.getLocation());
                }
                if (e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.RED_CONCRETE) {
                    e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(0.75));
                    e.getPlayer().setVelocity(new Vector(e.getPlayer().getVelocity().getX(), 2.5D, e.getPlayer().getVelocity().getZ()));
                    pl.su.launch(p.getLocation());
                }
                if (e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.BLUE_CONCRETE) {
                    e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(0.3));
                    e.getPlayer().setVelocity(new Vector(e.getPlayer().getVelocity().getX(), 0.8D, e.getPlayer().getVelocity().getZ()));
                    pl.su.launch(p.getLocation());
                }
            }
        }
    }

}
