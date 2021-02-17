package me.finn.launchify.commands.subcommands;


import me.finn.launchify.Launchify;
import me.finn.launchify.arena.LaunchArena;
import me.finn.launchify.commands.SubCommand;
import me.finn.launchify.game.Game;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameSub extends SubCommand {

    private Launchify pl;

    public GameSub(Launchify pl) {
        this.pl = pl;
    }

    @Override
    public String getName() {
        return "game";
    }

    @Override
    public String getPermission() {
        return "launchify.command.game";
    }

    @Override
    public List<String> getSubCommands() {
        return Arrays.asList("join");
    }

    @Override
    public void handle(CommandSender sender, ArrayList<String> args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("console cannot use this command!"); // todo: config
            return;
        }

        Player p = (Player) sender;

        if (args.size() == 0) {
            return;
        }

        String subcommand = args.get(0);

        if (subcommand.equalsIgnoreCase("join")) {
            LaunchArena arena = pl.am.getArenaFromString(args.get(1));
            if (arena == null) {
                p.sendMessage("that is not a valid arena name");
                return;
            }

            Game game = arena.getGame();

            game.addPlayer(p);
        }

    }
}
