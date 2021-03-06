package me.finn.launchify.managers;

import me.finn.launchify.Launchify;
import me.finn.launchify.game.Game;
import me.finn.launchify.game.GameStateType;
import me.finn.launchify.game.LaunchPlayer;
import me.finn.launchify.utils.Colorize;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ScoreboardManager {

    public Launchify pl;

    public ScoreboardManager(Launchify pl) {
        this.pl = pl;
    }

    public void createBoard(Player p) {
        org.bukkit.scoreboard.ScoreboardManager manager = Bukkit.getScoreboardManager();
        org.bukkit.scoreboard.Scoreboard board = manager.getNewScoreboard();
        Objective obj = board.registerNewObjective("launchifyBoard", "dummy", Colorize.color("&d&lLaunchify"));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        LaunchPlayer bp = pl.gm.getLaunchPlayerFromPlayer(p);
        Game game = pl.gm.getGameFromPlayer(p);

        int minAndSec = game.getTimeLeft()%3600;
        int min = minAndSec/60;
        int sec = minAndSec%60;

        String timeString = min + "m " + (sec == 0 ? "" : sec + "s");

        String date = new SimpleDateFormat("dd/MM/yy").format(Calendar.getInstance().getTime());

        if (bp == null || game == null) {
            return;
        }

        ArrayList<String> lines = new ArrayList<>();

        lines.add("&7" + date);
        lines.add("");
        lines.add("&fPlayers: &d" + game.getPlayers().size());
        if (game.getState().getType() == GameStateType.ACTIVE) {
            lines.add("&fTime Left: &d" + timeString);
            if (game.isPaused()) {
                lines.add("&8(paused)");
            }
            lines.add(" ");
            lines.add("&fKills: &d" + bp.getKills());
            lines.add("&fDeaths: &d" + bp.getDeaths());
            if (game.getPlayerMostKills() != null) {
                lines.add("&fTop Killer: &d" + game.getPlayerMostKills().getPlayer().getName() + " &7(" + game.getPlayerMostKills().getKills() + ")");
            }
        }
        if (game.isPaused() && game.getState().getType() != GameStateType.ACTIVE) {
            lines.add("&8(paused)");
        }
        lines.add("  ");
        lines.add("&fMap: &d" + game.getArena().getDisplayName());
        lines.add("&fState: &d" + game.getState().getType().getDisplayName());
        lines.add("   ");
        lines.add("&7youtube.com/finnn");

        Integer i = lines.size();
        for (String line : lines) {
            Score scoreLine = obj.getScore(Colorize.color(line));
            scoreLine.setScore(i);
            i--;
        }
        p.setScoreboard(board);

    }

    public void clearBoard(Player p) {
        org.bukkit.scoreboard.ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        p.setScoreboard(board);
    }

}
