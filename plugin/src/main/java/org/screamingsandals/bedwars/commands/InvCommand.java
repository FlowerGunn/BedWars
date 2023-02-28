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
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.api.game.GameStatus;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.statistics.PlayerStatistic;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.LoadedAbility;
import org.screamingsandals.bedwars.utils.flowergun.customgui.CustomGUI;
import org.screamingsandals.bedwars.utils.flowergun.managers.AbilitiesManager;
import org.screamingsandals.bedwars.utils.flowergun.managers.ColoursManager;

import java.util.ArrayList;
import java.util.List;

import static org.screamingsandals.bedwars.lib.lang.I18n.i18n;

public class InvCommand extends BaseCommand {

    public InvCommand() {
        super("inv", INV_PERMISSION, true, Main.getConfigurator().config.getBoolean("default-permissions.stats"));
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) {
        if (!Main.isPlayerStatisticsEnabled()) {
            sender.sendMessage(i18n("statistics_is_disabled"));
        } else {
            if (args.size() == 0) {

                if ( sender instanceof Player ) {

                    if (Main.isPlayerInGame((Player) sender)) {
                        sender.sendMessage(ColoursManager.red + "Вы не можете использовать эту команду в игре!");
                        return true;
                    }

                    CustomGUI customGUI = new CustomGUI( ((Player) sender), "INVENTORY");
                    customGUI.load();

                }
            }
        }
        return true;
    }

    @Override
    public void completeTab(List<String> completion, CommandSender sender, List<String> args) {
        if (args.size() == 1 && Main.isPlayerStatisticsEnabled()
                && (hasPermission(sender, INV_PERMISSION, false) && hasPermission(sender, ADMIN_PERMISSION, false))) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                completion.add(p.getName());
            }
        }
    }
}
