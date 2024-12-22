/*
 * Copyright (C) 2022 ScreamingSandals
 *
 * This file is part of Screaming BedWars.
 *
 * Screaming BedWars is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Screaming BedWars is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Screaming BedWars. If not, see <https://www.gnu.org/licenses/>.
 */

package org.screamingsandals.bedwars.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.api.game.Game;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.screamingsandals.bedwars.lib.lang.I.i18n;

public class AllJoinCommand extends BaseCommand {

    public AllJoinCommand() {
        super("alljoin", ALL_JOIN_PERMISSION, true, false);
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) {
        Game game = null;
        if (args.size() >= 1) {
            String arenaName = args.get(0);
            if (Main.isGameExists(arenaName)) {
                game = Main.getGame(arenaName);
            }
        } else {
            game = Main.getInstance().getGameWithHighestPlayers();
        }

        if (game == null) {
            sender.sendMessage(i18n("no_arena_found"));
            return true;
        }

        final Game finalGame = game;

        if ( args.size() == 2) {
            if ( args.get(1).equals("*") ){
                for (Player player : Bukkit.getOnlinePlayers()) {
                    sender.sendMessage("Sending " + player.getName() + " to arena " + game.getName());
                    finalGame.joinToGame(player);
                }
            } else {
                ArrayList<String> players = new ArrayList<>(Arrays.asList(args.get(1).split(",")));
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (players.contains(player.getName())) {
                        sender.sendMessage("Sending " + player.getName() + " to arena " + game.getName());
                        finalGame.joinToGame(player);
                    }
                }
            }
        } else {
            for ( Player player : Bukkit.getOnlinePlayers() ) {
                if (player.hasPermission("bw.disable.joinall")) {
                    continue;
                }

                if (Main.isPlayerInGame(player)) {
                    continue;
//                Main.getPlayerGameProfile(player).getGame().leaveFromGame(player);
                }
                finalGame.joinToGame(player);
            }
        }

        return true;
    }

    @Override
    public void completeTab(List<String> completion, CommandSender sender, List<String> args) {
        if (args.size() == 1) {
            completion.addAll(Main.getGameNames());
        }
    }
}
