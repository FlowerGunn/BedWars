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

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.WeatherType;
import org.bukkit.boss.BarColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.api.ArenaTime;
import org.screamingsandals.bedwars.game.*;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.OwnedAbility;
import org.screamingsandals.bedwars.utils.flowergun.managers.AbilitiesManager;

import java.io.File;
import java.util.*;

import static org.screamingsandals.bedwars.lib.lang.I.m;
import static org.screamingsandals.bedwars.lib.lang.I18n.i18n;
import static org.screamingsandals.bedwars.lib.lang.I18n.i18nonly;

public class AbilityCommand extends BaseCommand {

    public HashMap<String, GameCreator> gc = new HashMap<>();

    public AbilityCommand() {
        super("ability", ADMIN_PERMISSION, true, false);
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) {

        if (args.size() >= 2){
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args.get(1));
            if (!offlinePlayer.hasPlayedBefore()) {
                sender.sendMessage("Player " + args.get(1) + " doesn't exist. =(");
                return false;
            }
            String commandAction = args.get(0);

            boolean all = false;

            if (args.size() == 3) {


                String abilityId = args.get(2);

                if (!(Main.getInstance().getAbilitiesManager().isIdValid(abilityId) || abilityId.equals("*"))) {
                    sender.sendMessage("Ability ID " + abilityId + " is not valid.");
                    return false;
                }

                if (abilityId.equals("*")) all = true;

                if (commandAction.equalsIgnoreCase("give")) {
                    if (all) {
                        Main.getInstance().getAbilitiesManager().giveAllAbilitiesTo(offlinePlayer.getUniqueId());
                        sender.sendMessage("gave all.");
                    }
                    else {
                        Main.getInstance().getAbilitiesManager().giveAbilityToById(offlinePlayer.getUniqueId(), abilityId, 1);
                        sender.sendMessage("gave 1.");
                    }
                }
                else if (commandAction.equalsIgnoreCase("remove")) {
                    if (all) {
                        Main.getInstance().getAbilitiesManager().removeAllAbilitiesFrom(offlinePlayer.getUniqueId());
                        sender.sendMessage("removed all.");
                    }
                    else {
                        if (Main.getInstance().getAbilitiesManager().removeAbilityFromById(offlinePlayer.getUniqueId(), abilityId, 1))
                            sender.sendMessage("removed 1.");
                        else
                            sender.sendMessage("can't remove.");
                    }
                }
                return true;

            } else if (args.size() == 2) {
                if (commandAction.equalsIgnoreCase("list")) {

                    ArrayList<OwnedAbility> playerAbilities = new ArrayList<>();

                    if (offlinePlayer.isOnline()) {
                        playerAbilities.addAll(Main.getPlayerGameProfile(Bukkit.getPlayer(offlinePlayer.getUniqueId())).ownedAbilities);
                        sender.sendMessage("loading abilties from an online player");
                    } else {
                        playerAbilities.addAll(Main.getInstance().getAbilitiesManager().getAllAbilitiesByUUID(offlinePlayer.getUniqueId()));
                        sender.sendMessage("loading abilties from an offline player");
                    }

                    sender.sendMessage("list of owned abilities:");
                    for ( OwnedAbility ownedAbility : playerAbilities ) {
                        sender.sendMessage(ownedAbility.getAbility().getName() + " - " + ownedAbility.getOwnedLevel() + "lvl " + ownedAbility.duplicatesOwned + "duplicates " + ownedAbility.instancesCrafted + "crafted " + ownedAbility.lastEquippedSlot + "slot");
                    }
                }
                return true;
            }
        }

        sender.sendMessage("Wrong usage. Usage: /bw ability <give/remove/list> <nickname> [id/*]");
        return false;
    }

    @Override
    public void completeTab(List<String> completion, CommandSender sender, List<String> args) {
        if (args.size() == 1) {
            completion.addAll(Arrays.asList("give", "remove", "list"));
        } else if (args.size() == 2) {
            for (Player player: Bukkit.getOnlinePlayers()) {
                completion.add(player.getName());
            }
        } else if (args.size() == 3) {
            completion.addAll(Arrays.asList("*"));
        }
    }
}
