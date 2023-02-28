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
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.api.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.customgui.CustomGUI;

import java.util.List;

import static org.screamingsandals.bedwars.lib.lang.I18n.i18n;

public class ForgeCommand extends BaseCommand {

    public ForgeCommand() {
        super("forge", FORGE_PERMISSION, false, Main.getConfigurator().config.getBoolean("default-permissions.join"));
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Command is only for players.");
            return false;
        }

        Player player = (Player) sender;


        GamePlayer gamePlayer = Main.getPlayerGameProfile(player);

        if (args.size() == 0) {
            CustomGUI gui = new CustomGUI((Player) sender, "FORGE");
//            gui.setArg1(ChatColor.stripColor(player.getName()));
            gui.load();
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
