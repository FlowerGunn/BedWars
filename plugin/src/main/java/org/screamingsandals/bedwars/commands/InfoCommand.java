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

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.api.game.GameStatus;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.statistics.PlayerStatistic;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.AbilitiesManager;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.LoadedAbility;

import java.util.ArrayList;
import java.util.List;

import static org.screamingsandals.bedwars.lib.lang.I18n.i18n;

public class InfoCommand extends BaseCommand {

    public InfoCommand() {
        super("info", STATS_PERMISSION, true, Main.getConfigurator().config.getBoolean("default-permissions.stats"));
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) {
        if (!Main.isPlayerStatisticsEnabled()) {
            sender.sendMessage(i18n("statistics_is_disabled"));
        } else {
            if (args.size() >= 1) {

                    String name = args.get(0);
                    Player player = Bukkit.getPlayer(name);

                if ( player == null || !Main.isPlayerInGame(player))
                    sender.sendMessage(i18n("info_player_is_not_exists"));
                else {
                    GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
                    if ( gamePlayer.getGame().getStatus() == GameStatus.RUNNING ) {

                        ArrayList<LoadedAbility> loadedAbilities = gamePlayer.loadedAbilities;

                        sender.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + " Способности игрока " + ChatColor.GRAY + player.getDisplayName());
                        sender.sendMessage("");

                        for (int i = 0; i < loadedAbilities.size(); i++) {
                            LoadedAbility loadedAbility = loadedAbilities.get(i);
                            sender.sendMessage("   " + AbilitiesManager.formatLoadedAbilityNameInSlot(gamePlayer, i));
                            if (loadedAbility != null) {
                                ArrayList<String> lore = loadedAbility.getOwnedAbility().getAbility().parseDescription(3, loadedAbility.getActiveLevel(), i + 1);
                                for (String line : lore ) {
                                    sender.sendMessage("     " + line);
                                }
                            }
                            sender.sendMessage("");
                        }

                    } else sender.sendMessage(i18n("info_not_found"));
                }

            } else {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if ( player == null || !Main.isPlayerInGame(player))
                        sender.sendMessage(i18n("info_player_is_not_exists"));
                    else {
                        GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
                        if ( gamePlayer.getGame().getStatus() == GameStatus.RUNNING ) {

                            ArrayList<LoadedAbility> loadedAbilities = gamePlayer.loadedAbilities;

                            sender.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + " Способности игрока " + ChatColor.GRAY + player.getDisplayName());
                            sender.sendMessage("");

                            for (int i = 0; i < loadedAbilities.size(); i++) {
                                LoadedAbility loadedAbility = loadedAbilities.get(i);
                                sender.sendMessage("   " + AbilitiesManager.formatLoadedAbilityNameInSlot(gamePlayer, i));
                                if (loadedAbility != null) {
                                    ArrayList<String> lore = loadedAbility.getOwnedAbility().getAbility().parseDescription(3, loadedAbility.getActiveLevel(), i + 1);
                                    for (String line : lore ) {
                                        sender.sendMessage("     " + line);
                                    }
                                }
                                sender.sendMessage("");
                            }

                        } else sender.sendMessage(i18n("info_not_found"));
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void completeTab(List<String> completion, CommandSender sender, List<String> args) {
        if (args.size() == 1 && Main.isPlayerStatisticsEnabled()
                && (hasPermission(sender, OTHER_STATS_PERMISSION, false) && hasPermission(sender, ADMIN_PERMISSION, false))) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                completion.add(p.getName());
            }
        }
    }

    public static void sendStats(CommandSender player, PlayerStatistic statistic) {
        player.sendMessage(i18n("statistics_header").replace("%player%", statistic.getName()));

        player.sendMessage(i18n("statistics_kills", false).replace("%kills%",
                Integer.toString(statistic.getKills())));
        player.sendMessage(i18n("statistics_deaths", false).replace("%deaths%",
                Integer.toString(statistic.getDeaths())));
        player.sendMessage(i18n("statistics_kd", false).replace("%kd%",
                Double.toString(statistic.getKD())));
        player.sendMessage(i18n("statistics_wins", false).replace("%wins%",
                Integer.toString(statistic.getWins())));
        player.sendMessage(i18n("statistics_loses", false).replace("%loses%",
                Integer.toString(statistic.getLoses())));
        player.sendMessage(i18n("statistics_games", false).replace("%games%",
                Integer.toString(statistic.getGames())));
        player.sendMessage(i18n("statistics_beds", false).replace("%beds%",
                Integer.toString(statistic.getDestroyedBeds())));
        player.sendMessage(i18n("statistics_score", false).replace("%score%",
                Integer.toString(statistic.getScore())));
    }

}
