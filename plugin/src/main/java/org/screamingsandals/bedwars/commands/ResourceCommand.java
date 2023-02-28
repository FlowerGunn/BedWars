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
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GameCreator;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.OwnedResource;
import org.screamingsandals.bedwars.utils.flowergun.managers.ResourceManager;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ResourceCommand extends BaseCommand {

    public HashMap<String, GameCreator> gc = new HashMap<>();

    public ResourceCommand() {
        super("resource", ADMIN_PERMISSION, true, false);
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

            if (args.size() == 4) {

                String resourceId = args.get(2);
                int amount = Integer.parseInt(args.get(3));

                if ( amount <= 0 ) {
                    sender.sendMessage("Invalid number...");
                }

                if (!(Main.getResourceManager().isIdValid(resourceId))) {
                    sender.sendMessage("Resource ID " + resourceId + " is not valid.");
                    return false;
                }

                if (commandAction.equalsIgnoreCase("give")) {
                    ResourceManager.giveResourcesTo(offlinePlayer.getUniqueId(), ResourceType.valueOf(resourceId), amount);
                }
                else if (commandAction.equalsIgnoreCase("remove")) {
                    ResourceManager.giveResourcesTo(offlinePlayer.getUniqueId(), ResourceType.valueOf(resourceId), -amount);
                }


                return true;

            } else if (args.size() == 2) {
                if (commandAction.equalsIgnoreCase("list")) {

                    ArrayList<OwnedResource> ownedResources = new ArrayList<>();

                    if (offlinePlayer.isOnline()) {
                        ownedResources = new ArrayList<>( Main.getPlayerGameProfile(Bukkit.getPlayer(offlinePlayer.getUniqueId())).ownedResourceBundle.resources );
                        sender.sendMessage("loading resources from an online player");
                    } else {
                        ownedResources = Main.getResourceManager().getAllResourcesByUUID(offlinePlayer.getUniqueId());
                        sender.sendMessage("loading resources from an offline player");
                    }

                    sender.sendMessage("list of owned resources:");
                    for ( OwnedResource ownedResource : ownedResources) {
                        String message = "";
                        if (sender instanceof Player) message += ownedResource.getType().getIconType().getIcon((Player) sender);
                        message += " " + ownedResource.getResourceWithAmount();
                        sender.sendMessage( message );
                    }
                }
                return true;
            }
        }

        sender.sendMessage("Wrong usage. Usage: /bw resource <give/remove/list> <nickname> [id] [amount]");
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
        }
    }
}
