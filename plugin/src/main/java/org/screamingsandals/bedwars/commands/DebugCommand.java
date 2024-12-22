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
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.api.game.GameStatus;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.customgui.CustomGUI;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CustomStatusEffect;
import org.screamingsandals.bedwars.utils.flowergun.managers.NotificationManager;

import java.util.List;

import static org.screamingsandals.bedwars.lib.lang.I18n.i18n;

public class DebugCommand extends BaseCommand {

    public DebugCommand() {
        super("debug", ADMIN_PERMISSION, true, Main.getConfigurator().config.getBoolean("admin.debug"));
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) {

            if (args.size() >= 1) {

                String name = args.get(0);
                Player player = Bukkit.getPlayer(name);

                if (player == null || !Main.isPlayerInGame(player))
                    sender.sendMessage(i18n("info_player_is_not_exists"));
                else {
                    GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
                    if (gamePlayer.getGame().getStatus() == GameStatus.RUNNING) {
                        for (CustomStatusEffect customStatusEffect : gamePlayer.customStatusEffects ) {
                            sender.sendMessage(customStatusEffect.effectId + " " + customStatusEffect.ticksDuration + " " + customStatusEffect.isActive + " " + customStatusEffect.isPermanent + " " + customStatusEffect.isCancelled());
                        }
                    } else sender.sendMessage(i18n("info_not_found"));
                }

            } else {
                if (sender instanceof Player player) {
                    if ( !Main.isPlayerInGame(player))
                        sender.sendMessage(i18n("info_player_is_not_exists"));
                    else {
                        GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
                        if ( gamePlayer.getGame().getStatus() == GameStatus.RUNNING ) {
                            for (CustomStatusEffect customStatusEffect : gamePlayer.customStatusEffects ) {
                                sender.sendMessage(customStatusEffect.effectId + " " + customStatusEffect.ticksDuration + " " + customStatusEffect.isActive + " " + customStatusEffect.isPermanent + " " + customStatusEffect.isCancelled());
                            }
                        } else sender.sendMessage(i18n("info_not_found"));
                    }
                } else {
                    return false;
                }
            }
        return true;
    }

    @Override
    public void completeTab(List<String> completion, CommandSender sender, List<String> args) {
        if (args.size() == 1 && hasPermission(sender, ADMIN_PERMISSION, false)) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                completion.add(p.getName());
            }
        }
    }

}
