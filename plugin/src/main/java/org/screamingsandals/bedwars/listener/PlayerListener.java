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

package org.screamingsandals.bedwars.listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.util.RGBLike;
import net.md_5.bungee.api.chat.BaseComponent;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.*;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.api.APIUtils;
import org.screamingsandals.bedwars.api.RunningTeam;
import org.screamingsandals.bedwars.api.boss.StatusBar;
import org.screamingsandals.bedwars.api.events.BedwarsPlayerKilledEvent;
import org.screamingsandals.bedwars.api.events.BedwarsTeamChestOpenEvent;
import org.screamingsandals.bedwars.api.game.GameStatus;
import org.screamingsandals.bedwars.boss.BossBar18;
import org.screamingsandals.bedwars.commands.BaseCommand;
import org.screamingsandals.bedwars.game.*;
import org.screamingsandals.bedwars.special.listener.ProtectionWallListener;
import org.screamingsandals.bedwars.special.listener.RescuePlatformListener;
import org.screamingsandals.bedwars.statistics.PlayerStatistic;
import org.screamingsandals.bedwars.lib.debug.Debug;
import org.screamingsandals.bedwars.lib.nms.entity.PlayerUtils;
import org.screamingsandals.bedwars.utils.external.*;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.LoadedAbility;
import org.screamingsandals.bedwars.utils.flowergun.customgui.CustomGUI;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Triggers;
import org.screamingsandals.bedwars.utils.flowergun.managers.ColoursManager;
import org.screamingsandals.bedwars.utils.flowergun.managers.IconsManager;
import org.screamingsandals.bedwars.utils.flowergun.managers.NotificationManager;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;
import org.screamingsandals.simpleinventories.utils.StackParser;

import java.util.*;

import static org.screamingsandals.bedwars.lib.lang.I18n.*;
import static org.screamingsandals.bedwars.commands.BaseCommand.ADMIN_PERMISSION;

public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDeath(PlayerDeathEvent event) {


        final Player victim = event.getEntity();

        if (Main.isPlayerInGame(victim)) {
            GamePlayer gVictim = Main.getPlayerGameProfile(victim);
            Game game = gVictim.getGame();
            CurrentTeam victimTeam = game.getPlayerTeam(gVictim);

            List<ItemStack> drops = new ArrayList<>(event.getDrops());


            int respawnTime = FlowerUtils.calculateRespawnTime(game); //Main.getConfigurator().config.getInt("respawn-cooldown.time", 5);

            event.setKeepInventory(game.getOriginalOrInheritedKeepInventory());
            event.setDroppedExp(0);


//            Inventory inventory = victim.getInventory();
//            ItemMeta itemMeta;
//
//            for ( ItemStack itemStack : inventory) {
//                if ( itemStack == null ) continue;
//                ItemStack clone = itemStack.clone();
//                clone.setAmount(1);
//                if ( FlowerUtils.destroyedResources.contains(clone) ) {
//                    itemStack.setAmount(0);
//                    continue;
//                }
//                if ( itemStack.getType().getMaxDurability() > 1 ) {
//                    itemMeta = itemStack.getItemMeta();
//                    int unbreakingLevel = itemMeta.getEnchantLevel(Enchantment.DURABILITY);
//                    int currentDamage = ((Damageable) itemMeta).getDamage();
//                    int maxDamage = itemStack.getType().getMaxDurability();
//                    ((Damageable)itemMeta).setDamage( currentDamage + (int)(( maxDamage - currentDamage) * (FlowerUtils.durabilityReduction - FlowerUtils.durabilityCounterReductionPerUnbreakingLevel * unbreakingLevel)) );
//                    itemStack.setItemMeta(itemMeta);
//                }
//            }

            if (game.getStatus() == GameStatus.RUNNING) {
                if (!game.getOriginalOrInheritedPlayerDrops()) {
                    //event.getDrops().clear();
                }

                Triggers.playerDeath(event);

                CurrentTeam team = game.getPlayerTeam(gVictim);
                SpawnEffects.spawnEffect(game, victim, "game-effects.kill");
                boolean isBed = team.isBed;
                if (isBed && game.getOriginalOrInheritedAnchorDecreasing() && "RESPAWN_ANCHOR".equals(team.teamInfo.bed.getBlock().getType().name())) {
                    isBed = Player116ListenerUtils.processAnchorDeath(game, team, isBed);
                }
                if (!isBed) {
                    gVictim.isSpectator = true;
                    team.players.remove(gVictim);
                    team.getScoreboardTeam().removeEntry(victim.getName());
                    if (Main.isPlayerStatisticsEnabled()) {
                        PlayerStatistic statistic = Main.getPlayerStatisticsManager().getStatistic(victim);
                        statistic.addLoses(1);
                        statistic.addScore(Main.getConfigurator().config.getInt("statistics.scores.lose", 0));


                        int amount = (game.getGameTime() - game.countdown) / 120;
                        Main.getStatsManager().addResourceToPlayer(victim, ResourceType.EXP_CRYSTAL_LVL1, amount);
                        Main.getStatsManager().reward(victim);
                    }
                    game.updateScoreboard();

                    if (team.players.size() <= 0) {
                        Bukkit.getConsoleSender().sendMessage("Команда " + team.teamInfo.color.chatColor + team.getName() + ChatColor.RESET + " полностью уничтожена за " + (game.getGameTime() - game.countdown) / 60 + ":" + (game.getGameTime() - game.countdown) % 60 + " на арене " + game.getName());
                    }
                }

                boolean onlyOnBedDestroy = Main.getConfigurator().config.getBoolean("statistics.bed-destroyed-kills",
                        false);

                Player killer = victim.getKiller();
                if (Main.isPlayerInGame(killer)) {
                    GamePlayer gKiller = Main.getPlayerGameProfile(killer);
                    if (gKiller.getGame() == game) {
                        if (!onlyOnBedDestroy || !isBed) {
                            game.dispatchRewardCommands("player-kill", killer,
                                    Main.getConfigurator().config.getInt("statistics.scores.kill", 10));
                        }
                        if (!isBed) {
                            //WAYPOINT FINAL KILL

                            game.dispatchRewardCommands("player-final-kill", killer,
                                    Main.getConfigurator().config.getInt("statistics.scores.final-kill", 0));
                        }
                        if (team.isDead()) {
                            SpawnEffects.spawnEffect(game, victim, "game-effects.teamkill");
                            Sounds.playSound(killer, killer.getLocation(),
                                    Main.getConfigurator().config.getString("sounds.team_kill.sound"),
                                    Sounds.ENTITY_PLAYER_LEVELUP,  (float) Main.getConfigurator().config.getDouble("sounds.team_kill.volume"), (float) Main.getConfigurator().config.getDouble("sounds.team_kill.pitch"));
                        } else {
                            Sounds.playSound(killer, killer.getLocation(),
                                    Main.getConfigurator().config.getString("sounds.player_kill.sound"),
                                    Sounds.ENTITY_PLAYER_BIG_FALL, (float) Main.getConfigurator().config.getDouble("sounds.player_kill.volume"), (float) Main.getConfigurator().config.getDouble("sounds.player_kill.pitch"));
                            if (!isBed) {
                                Main.depositPlayer(killer, Main.getVaultFinalKillReward());
                            } else {
                                Main.depositPlayer(killer, Main.getVaultKillReward());
                            }
                        }

                    }
                }

                BedwarsPlayerKilledEvent killedEvent = new BedwarsPlayerKilledEvent(game, victim,
                        Main.isPlayerInGame(killer) ? killer : null, drops);
                Main.getInstance().getServer().getPluginManager().callEvent(killedEvent);

                if (Main.isPlayerStatisticsEnabled()) {
                    PlayerStatistic diePlayer = Main.getPlayerStatisticsManager().getStatistic(victim);
                    PlayerStatistic killerPlayer;

                    if (!onlyOnBedDestroy || !isBed) {
                        diePlayer.addDeaths(1);
                        diePlayer.addScore(Main.getConfigurator().config.getInt("statistics.scores.die", 0));
                    }

                    if (killer != null) {
                        if (!onlyOnBedDestroy || !isBed) {
                            killerPlayer = Main.getPlayerStatisticsManager().getStatistic(killer);
                            if (killerPlayer != null) {
                                killerPlayer.addKills(1);
                                killerPlayer.addScore(Main.getConfigurator().config.getInt("statistics.scores.kill", 10));
                            }

                            if (!isBed) {
                                killerPlayer.addScore(Main.getConfigurator().config.getInt("statistics.scores.final-kill", 0));
                            }
                        }
                    }
                }
            }
            if (Main.getVersionNumber() < 115 && !Main.getConfigurator().config.getBoolean("allow-fake-death")) {
                PlayerUtils.respawn(Main.getInstance(), victim, 3L);
            }
            if (Main.getConfigurator().config.getBoolean("respawn-cooldown.enabled")
                    && victimTeam.isAlive()
                    && !gVictim.isSpectator) {
                game.makeSpectator(gVictim, false);

                new BukkitRunnable() {
                    int livingTime = respawnTime;
                    GamePlayer gamePlayer = gVictim;
                    Player player = gamePlayer.player;

                    @Override
                    public void run() {
                        if (livingTime > 0) {
                            player.sendTitle("", (ColoursManager.gray + "Возрождение... " + ColoursManager.light_blue + livingTime), 0, 20, 10);
                            player.playSound(player, Sound.ITEM_BOTTLE_FILL_DRAGONBREATH, 0.2F, 0.5F);
                        } else
                        if (livingTime == 0) {
                            game.makePlayerFromSpectator(gamePlayer);

                            //WAYPOINT TOOLS BREAKING

                            if ( game != null )
                            Triggers.playerRespawn(game, gamePlayer);

                            player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 0.1F, 0.65F);

                            this.cancel();
                        }
                        livingTime--;
                    }
                }.runTaskTimer(Main.getInstance(), 20L, 20L);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (Main.isPlayerGameProfileRegistered(event.getPlayer())) {
            GamePlayer gPlayer = Main.getPlayerGameProfile(event.getPlayer());

            if (gPlayer.isInGame()) {
                gPlayer.storeInv();
                gPlayer.changeGame(null);
            }

            if (findRejoinableGameOfPlayer(event.getPlayer()) == null) {
                Main.unloadPlayerGameProfile(event.getPlayer());
//                Bukkit.getConsoleSender().sendMessage("profile unloaded");
            }

        }

        if (Main.isPlayerStatisticsEnabled()) {
            Main.getPlayerStatisticsManager().unloadStatistic(event.getPlayer());
        }

        if (Main.getConfigurator().config.getBoolean("disable-server-message.player-join")) {
            event.setQuitMessage(null);
        }

        if (Main.isHologramsEnabled()) {
            Main.getHologramInteraction().cleanupPlayerLeave(event.getPlayer());
        }
    }

    public static Game findRejoinableGameOfPlayer( Player player ) {
        for ( String gameName : Main.getGameNames() ) {
            Game game = Main.getGame(gameName);
//            Bukkit.getConsoleSender().sendMessage("checking game " + game.getName());

            if (game.getStatus() != GameStatus.RUNNING) continue;

            for ( GamePlayer gamePlayer : game.getMatchedPlayers() ) {
//                Bukkit.getConsoleSender().sendMessage("checking matched player " + gamePlayer.player.getDisplayName());
                if ( gamePlayer.player.getDisplayName().equals(player.getDisplayName()) ) {

                    if ( gamePlayer.latestCurrentTeam.isBed ) {

                        if (gamePlayer.player == player) Bukkit.getConsoleSender().sendMessage("player is the same");
                        else Bukkit.getConsoleSender().sendMessage("player IS NOT THE SAME");
                        Main.getInstance().playersInGame.remove(gamePlayer);
                        gamePlayer.player = player;
                        Main.getInstance().playersInGame.put(player, gamePlayer);
                        Bukkit.getConsoleSender().sendMessage(player.getName() + " is found in game " + game.getName());
                        return game;

                    }
                }
            }
        }
        return null;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Bukkit.getConsoleSender().sendMessage("playerjoining " + player.getDisplayName());

        Game game = findRejoinableGameOfPlayer(player);

        IconsManager.loadIcons(player);

        if ( game != null ) {
            if (game.internalReturnPlayer(player)) return;
        }

        Main.getAbilitiesManager().checkDailyTrialSelection();

        if (Game.isBungeeEnabled() && Main.getConfigurator().config.getBoolean("bungee.auto-game-connect", false)) {
            new BukkitRunnable() {
                public void run() {
                    try {
                        Game game = (Game) Main.getInstance().getFirstWaitingGame();
                        if (game == null) {
                            game = (Game) Main.getInstance().getFirstRunningGame();
                        }

                        game.joinToGame(player);
                    } catch (NullPointerException ignored) {
                        if (!BaseCommand.hasPermission(player, ADMIN_PERMISSION, false)) {
                            BungeeUtils.movePlayerToBungeeServer(player, false);
                        }
                    }
                }
            }.runTaskLater(Main.getInstance(), 1L);
        }

        if (Main.getConfigurator().config.getBoolean("disable-server-message.player-join")) {
            event.setJoinMessage(null);
        }

        if (Main.getConfigurator().config.getBoolean("tab.enable") && Main.getConfigurator().config.getBoolean("tab.hide-foreign-players")) {
            Bukkit.getOnlinePlayers().stream().filter(Main::isPlayerInGame).forEach(p -> Main.getPlayerGameProfile(p).hidePlayer(player));
        }
    }

    @SuppressWarnings("unchecked")
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if (Main.isPlayerInGame(event.getPlayer())) {
            GamePlayer gPlayer = Main.getPlayerGameProfile(event.getPlayer());
            Game game = gPlayer.getGame();
            CurrentTeam team = game.getPlayerTeam(gPlayer);

            if (game.getStatus() == GameStatus.WAITING) {
                event.setRespawnLocation(gPlayer.getGame().getLobbySpawn());
                return;
            }
            // clear inventory to fix issue 148
            if (!game.getOriginalOrInheritedKeepInventory()) {
                //event.getPlayer().getInventory().clear();
                Bukkit.getConsoleSender().sendMessage("Dead inventory cleared!");
            }
            if (gPlayer.isSpectator) {
                if (team == null) {
                    event.setRespawnLocation(gPlayer.getGame().makeSpectator(gPlayer, true));
                } else {
                    event.setRespawnLocation(gPlayer.getGame().makeSpectator(gPlayer, false));
                }
            } else {
                event.setRespawnLocation(MiscUtils.findEmptyLocation(gPlayer.getGame().getPlayerTeam(gPlayer).teamInfo.spawn));

                if (Main.getConfigurator().config.getBoolean("respawn.protection-enabled", true)) {
                    RespawnProtection respawnProtection = game.addProtectedPlayer(gPlayer.player);
                    respawnProtection.runProtection();
                }

                SpawnEffects.spawnEffect(gPlayer.getGame(), gPlayer.player, "game-effects.respawn");
                if (gPlayer.getGame().getOriginalOrInheritedPlayerRespawnItems()) {
                    List<ItemStack> givedGameStartItems = StackParser.parseAll((Collection<Object>) Main.getConfigurator().config
                            .getList("gived-player-respawn-items"));
                    if (givedGameStartItems != null) {
                        MiscUtils.giveItemsToPlayer(givedGameStartItems, gPlayer.player, team.getColor());
                    } else {
                        Debug.warn("You have wrongly configured gived-player-respawn-items!", true);
                    }
                }
            }

            if (Main.getVersionNumber() <= 108 && !Main.getConfigurator().config.getBoolean("allow-fake-death")) {
                StatusBar boss = game.getStatusBar();
                if (boss instanceof BossBar18) {
                    BossBar18 boss18 = (BossBar18) boss;
                    if (!boss18.isViaPlayer(gPlayer.player)) {
                        boss18.removePlayer(gPlayer.player);
                        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                           boss18.addPlayer(gPlayer.player);
                        });
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerWorldChange(PlayerChangedWorldEvent event) {
        if (Main.isPlayerInGame(event.getPlayer())) {
            GamePlayer gPlayer = Main.getPlayerGameProfile(event.getPlayer());
            Game game = gPlayer.getGame();
            if (game.getWorld() != event.getPlayer().getWorld()
                    && game.getLobbySpawn().getWorld() != event.getPlayer().getWorld()) {
                gPlayer.changeGame(null);
            }
        }

        if (Main.isHologramsEnabled()) {
            Main.getHologramInteraction().updateHolograms(event.getPlayer(), 10L);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) {
            if (Main.getConfigurator().config.getBoolean("event-hacks.place") && Main.isPlayerInGame(event.getPlayer())) {
                event.setCancelled(false);
            } else {
                return;
            }
        }

        if (Main.isPlayerInGame(event.getPlayer())) {
            Game game = Main.getPlayerGameProfile(event.getPlayer()).getGame();
            if (game.getStatus() == GameStatus.WAITING) {
                event.setCancelled(true);
                return;
            }
            if (!game.blockPlace(Main.getPlayerGameProfile(event.getPlayer()), event.getBlock(),
                    event.getBlockReplacedState(), event.getItemInHand())) {
                event.setCancelled(true);
            }
            Triggers.blockPlace(event);
        } else if (!event.getPlayer().hasPermission("bw.admin")) {
            event.setCancelled(true);
        } else if (Main.getConfigurator().config.getBoolean("preventArenaFromGriefing")) {
            for (String gameN : Main.getGameNames()) {
                Game game = Main.getGame(gameN);
                if (game.getStatus() != GameStatus.DISABLED && GameCreator.isInArea(event.getBlock().getLocation(), game.getPos1(), game.getPos2())) {
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) {
            if (Main.getConfigurator().config.getBoolean("event-hacks.destroy") && Main.isPlayerInGame(event.getPlayer())) {
                event.setCancelled(false);
            } else {
                return;
            }
        }

        if (Main.isPlayerInGame(event.getPlayer())) {
            final Player player = event.getPlayer();
            final GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
            final Game game = gamePlayer.getGame();
            final Block block = event.getBlock();

            if (game.getStatus() == GameStatus.WAITING) {
                event.setCancelled(true);
                return;
            }

            if (!game.blockBreak(Main.getPlayerGameProfile(event.getPlayer()), event.getBlock(), event)) {
                event.setCancelled(true);
            }

            Triggers.blockBreak(event);

            //Fix for obsidian dropping
            if (game.getStatus() == GameStatus.RUNNING && gamePlayer.isInGame()) {
                if (block.getType() == Material.ENDER_CHEST) {
                    try {
                        event.setDropItems(false);
                    } catch (Throwable t) {
                        block.setType(Material.AIR);
                    }
                } else if ( block.getType() == Material.FIRE ) {
                    event.setCancelled(false);
                }
            }
        } else if (!event.getPlayer().hasPermission("bw.admin")) {
            event.setCancelled(true);
            return;
        } else if (Main.getConfigurator().config.getBoolean("preventArenaFromGriefing")) {
            for (String gameN : Main.getGameNames()) {
                Game game = Main.getGame(gameN);
                if (game.getStatus() != GameStatus.DISABLED && GameCreator.isInArea(event.getBlock().getLocation(), game.getPos1(), game.getPos2())) {
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommandExecuted(PlayerCommandPreprocessEvent event) {
        if (event.isCancelled()) {
            return;
        }

        final Player player = event.getPlayer();
        if (Main.isPlayerInGame(player)) {
            final String message = event.getMessage();
            final GamePlayer gamePlayer = Main.getPlayerGameProfile(event.getPlayer());
            if (Main.isCommandLeaveShortcut(message)) {
                event.setCancelled(true);
                gamePlayer.changeGame(null);
            } else if (!Main.isCommandAllowedInGame(message.split(" ")[0])) {
                //Allow players with permissions to use all commands
                if (BaseCommand.hasPermission(player, ADMIN_PERMISSION, false)) {
                    return;
                }

                event.setCancelled(true);
                event.getPlayer().sendMessage(i18nc("command_is_not_allowed", gamePlayer.getGame().getCustomPrefix()));
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) {
            return;
        }

        if (event.getClickedInventory().getType() == InventoryType.PLAYER) {
            Player p = (Player) event.getWhoClicked();
            if (Main.isPlayerInGame(p)) {
                GamePlayer gPlayer = Main.getPlayerGameProfile(p);
                Game game = gPlayer.getGame();
                if (game.getStatus() == GameStatus.WAITING || gPlayer.isSpectator) {
                    event.setCancelled(true);
                    if (event.getClick().isLeftClick() || event.getClick().isRightClick()) {
                        ItemStack item = event.getCurrentItem();
                        if (item != null) {
                            //p.closeInventory();
                            if (item.getType() == Material
                                    .valueOf(Main.getConfigurator().config.getString("items.jointeam", "COMPASS"))) {
                                if (game.getStatus() == GameStatus.WAITING) {
                                    game.openTeamSelectorInventory(p);
                                } else if (gPlayer.isSpectator) {
                                    // IDK
                                }
                            } else if (item.getType() == Material
                                    .valueOf(Main.getConfigurator().config.getString("items.startgame", "DIAMOND"))) {
                                if (game.getStatus() == GameStatus.WAITING && (p.hasPermission("bw.vip.startitem")
                                        || p.hasPermission("misat11.bw.vip.startitem"))) {
                                    if (game.checkMinPlayers()) {
                                        game.gameStartItem = true;
                                    } else {
                                        p.sendMessage(i18nc("vip_not_enough_players", game.getCustomPrefix()));
                                    }
                                }
                            } else if (item.getType() == Material
                                    .valueOf(Main.getConfigurator().config.getString("items.leavegame", "SLIME_BALL"))) {
                                game.leaveFromGame(p);
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onHunger(FoodLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player) || event.isCancelled()) {
            return;
        }

        Player player = (Player) event.getEntity();
        if (Main.isPlayerInGame(player)) {
            GamePlayer gPlayer = Main.getPlayerGameProfile(player);
            Game game = gPlayer.getGame();
            if (game.getStatus() == GameStatus.WAITING || gPlayer.isSpectator) {
                event.setCancelled(true);
            }

            if (game.getStatus() == GameStatus.RUNNING && Main.getConfigurator().config.getBoolean("disable-hunger")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        if (event.isCancelled() || !(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        if (Main.isPlayerInGame(player)) {
            GamePlayer gPlayer = Main.getPlayerGameProfile(player);
            if (gPlayer.getGame().getStatus() != GameStatus.RUNNING) {
                event.setCancelled(true);
            } else if (!gPlayer.getGame().getOriginalOrInheritedCrafting()) {
                if ( !FlowerUtils.allowedRecipes.contains(event.getRecipe().getResult().getType()) )
                event.setCancelled(true);

                if ( !event.isCancelled() && event.getRecipe() instanceof ShapelessRecipe )
                {
                    ShapelessRecipe shapelessRecipe = (ShapelessRecipe) event.getRecipe();

                    ArrayList<ItemStack> input = new ArrayList<>(shapelessRecipe.getIngredientList());
                    boolean isDye = false;
                    for ( ItemStack item : input ) {
                        if ( item.getType().toString().toLowerCase().contains("dye")) {
                            isDye = true;
                            break;
                        }
                    }
                    if (shapelessRecipe.getResult().getType().toString().toLowerCase().contains("dye") || isDye) {
                        Main.getStatsManager().addResourceToPlayer((Player) player, ResourceType.GLOW_INK_SAC, 1);
                    }

                }


            }
        } else if (!event.getWhoClicked().hasPermission("bw.admin")) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onDamage(EntityDamageEvent event) {

//        Main.getInstance().getLogger().info("processing entity damage" );
//
//        Main.getInstance().getLogger().info(event.isCancelled() + " cancelled");
//
//        if (event.isCancelled()) {
//            if (Main.getConfigurator().config.getBoolean("event-hacks.damage")
//                    && event.getEntity() instanceof Player
//                    && Main.isPlayerInGame((Player) event.getEntity())) {
//                event.setCancelled(false);
//                Bukkit.getConsoleSender().sendMessage("event hacks enabled");
//            } else {
//                return;
//            }
//        }

        final Entity entity = event.getEntity();

        if (!(entity instanceof Player)) {
            if (event.getCause() != DamageCause.VOID) {
                Game game = Main.getInGameEntity(entity);
                if (game != null) {
                    if (game.isEntityShop(entity) && game.getOriginalOrInheritedPreventKillingVillagers()) {
                        event.setCancelled(true);
                    }
                }
            }

            if (event instanceof EntityDamageByEntityEvent) {
                if (event.getEntity() instanceof ArmorStand) {
                    Entity damager = ((EntityDamageByEntityEvent) event).getDamager();
                    if (damager instanceof Player) {
                        Player player = (Player) damager;
                        if (Main.isPlayerInGame(player)) {
                            GamePlayer gPlayer = Main.getPlayerGameProfile(player);
                            if (gPlayer.getGame().getStatus() == GameStatus.WAITING || gPlayer.isSpectator) {
                                event.setCancelled(true);
                            }
                        }
                    }
                }
            }
            return;
        }

        Player player = (Player) event.getEntity();

//        Triggers.playerReceiveDamage(event);

        String cause = (double) Math.round(event.getDamage() * 100) / 100 + " | " + (double) Math.round(event.getFinalDamage() * 100) / 100 + " | " + event.getCause().name();
        if (event instanceof EntityDamageByEntityEvent) {
            cause += " | " + ((EntityDamageByEntityEvent) event).getDamager().getType().name();
            if (((EntityDamageByEntityEvent) event).getDamager() instanceof Player) {
                if ( player.isBlocking() ) {
                    cause += " | blocking";

                }
            }
        }

        if (ChatColor.stripColor(player.getName()).equals("FlowerGun")) {
            //WAYPOINT DAMAGE LOG
//            player.sendTitle("", ChatColor.GRAY + cause, 5,40, 5);
//            player.sendMessage(ChatColor.GREEN + cause );
//            Bukkit.getConsoleSender().sendMessage(cause);
        }


//        Material underPlayer = player.getWorld().getBlockAt(player.getLocation().add(0, 1, 0)).getType();
//        if ( event.getCause() == DamageCause.FALL &&  ( underPlayer == Material.SLIME_BLOCK || underPlayer == Material.HONEY_BLOCK ) ){
//            event.setCancelled(true);
//            Bukkit.getConsoleSender().sendMessage("removed unncessary fall damage");
//            return;
//        }


        if (Main.isPlayerInGame(player)) {
            GamePlayer gPlayer = Main.getPlayerGameProfile(player);
            Game game = gPlayer.getGame();
            if (gPlayer.isSpectator) {
                if (event.getCause() == DamageCause.VOID) {
                    gPlayer.player.setFallDistance(0);
//                    gPlayer.teleport(game.getSpecSpawn());
                }
                event.setCancelled(true);
            } else if (game.getStatus() == GameStatus.WAITING) {
                if (event.getCause() == DamageCause.VOID) {
                    gPlayer.player.setFallDistance(0);
                    gPlayer.teleport(game.getLobbySpawn());
                }
                event.setCancelled(true);
            } else if (game.getStatus() == GameStatus.RUNNING || game.getStatus() == GameStatus.GAME_END_CELEBRATING) {
                if (gPlayer.isSpectator) {
                    event.setCancelled(true);
                }
                if (event instanceof EntityDamageByEntityEvent) {
                    EntityDamageByEntityEvent edbee = (EntityDamageByEntityEvent) event;
                    if (edbee.getDamager() instanceof Player damager) {
                        GamePlayer gDamager = Main.getPlayerGameProfile(damager);
                        if (game.isProtectionActive((Player) edbee.getDamager())) {
                            Bukkit.getConsoleSender().sendMessage("trying to remove protection from " + gDamager.getGame().getPlayerTeam(gDamager).getName() + " " + gDamager.player.getName() + " -> " + game.getPlayerTeam(gPlayer).getName() + " " + gPlayer.player.getName());
                            if (gDamager.getGame().getPlayerTeam(gDamager) != game.getPlayerTeam(gPlayer)) {
                                game.removeProtectedPlayer(damager);
//
                                event.setCancelled(false);
                                return;
                            }
                        }
                    }
                }

                if (game.isProtectionActive(player) && event.getCause() != DamageCause.VOID) {
                    event.setCancelled(true);
                    return;
                }

                if (event.getCause() == DamageCause.VOID) {
                    player.setHealth(0.5);
                } else if (event instanceof EntityDamageByEntityEvent) {
                    EntityDamageByEntityEvent edbee = (EntityDamageByEntityEvent) event;
                    if (edbee.getDamager() instanceof Player) {
                        Player damager = (Player) edbee.getDamager();
                        if (Main.isPlayerInGame(damager)) {
                            GamePlayer gDamager = Main.getPlayerGameProfile(damager);
                            if (gDamager.isSpectator || (gDamager.getGame().getPlayerTeam(gDamager) == game.getPlayerTeam(gPlayer) && !game.getOriginalOrInheritedFriendlyfire())) {
//                                Bukkit.getConsoleSender().sendMessage("stopped teammate melee damage");
                                event.setCancelled(true);
                            }
                        }
                    } else if (edbee.getDamager() instanceof Firework && game.getStatus() == GameStatus.GAME_END_CELEBRATING) {
                        event.setCancelled(true);
                    } else if (edbee.getDamager() instanceof Projectile) {
                        Projectile projectile = (Projectile) edbee.getDamager();
//                        Bukkit.getConsoleSender().sendMessage("shooter = " + projectile.getShooter());
                        if (projectile.getShooter() instanceof Player) {
                            Player damager = (Player) projectile.getShooter();
                            if (Main.isPlayerInGame(damager)) {
                                GamePlayer gDamager = Main.getPlayerGameProfile(damager);
                                if (gDamager.isSpectator || gDamager.getGame().getPlayerTeam(gDamager) == game.getPlayerTeam(gPlayer) && !game.getOriginalOrInheritedFriendlyfire()) {
//                                    Bukkit.getConsoleSender().sendMessage("stopped teammate projectile damage");

                                    event.setCancelled(true);

                                    if ( gDamager.getGame().getPlayerTeam(gDamager) == game.getPlayerTeam(gPlayer) ) {
//                                        if (projectile instanceof Firework) {
//                                            FireworkMeta fireworkMeta = ((Firework) projectile).getFireworkMeta();
//                                            if (fireworkMeta.)
//                                        }

                                        if (projectile instanceof Arrow) {
                                            Arrow arrow = (Arrow) projectile;
                                            PotionEffectType effect = arrow.getBasePotionData().getType().getEffectType();
                                            if (effect == null) return;
//                                            Bukkit.getConsoleSender().sendMessage(effect + " " + effect.getName());
                                            if ( effect.equals(PotionEffectType.HEAL)) {
                                                if (player != damager)
                                                Ability.healHealth(gDamager.player, player, ((arrow.getBasePotionData().isUpgraded()? 1 : 0 ) + 1 ) * FlowerUtils.healingArrowHealPerLevel );
                                                else player.sendActionBar(ColoursManager.pink + "Лечение не сработало, но вам уже намного лучше...");
                                                projectile.remove();
                                            }
                                            else if (effect.equals(PotionEffectType.REGENERATION)) {
                                                if (player != damager)
                                                Ability.healRegen(gDamager.player, player, new PotionEffect(PotionEffectType.REGENERATION, FlowerUtils.regenArrowDuration, arrow.getBasePotionData().isUpgraded()? 1 : 0, false, false) );
                                                else player.sendActionBar(ColoursManager.pink + "Лечение не сработало, но вам уже намного лучше...");
                                                projectile.remove();
                                            }

                                        }

                                    }

                                }
                                else if ( gDamager.getGame().getPlayerTeam(gDamager) != game.getPlayerTeam(gPlayer) ) {

                                    if (projectile instanceof Arrow) {
                                        Arrow arrow = (Arrow) projectile;
                                        PotionEffectType effect = arrow.getBasePotionData().getType().getEffectType();

                                        if (effect == null) return;

                                        if ( effect.equals(PotionEffectType.HEAL) || effect.equals(PotionEffectType.REGENERATION)) {
                                            event.setCancelled(true);
                                        }

                                    }

                                }
                            }
                        }
                        if (projectile instanceof Fireball && game.getStatus() == GameStatus.RUNNING) {
                            final double damage = FlowerUtils.fireballDamage;

                            event.setDamage(1.0);

                            if ( projectile.getShooter() instanceof Player ) {
                                double radius = 3.0;

                                double power = 2.0;
                                double powerY = 1;

                                Location explosionLocation = projectile.getLocation();

                                double userZ = explosionLocation.getZ();
                                double userX = explosionLocation.getX();
                                double userY = explosionLocation.getY();
                                Player target;
                                double targetZ;
                                double targetX;
                                double targetY;
                                double targetZdirection;
                                double targetXdirection;
                                double targetYdirection;

                                target = player;

                                targetZ = target.getLocation().getZ();
                                targetX = target.getLocation().getX();
                                targetY = target.getLocation().getY();
                                targetZdirection = targetZ - userZ;
                                targetXdirection = targetX - userX;
                                targetYdirection = targetY - userY;
                                org.bukkit.util.Vector boom = new Vector(targetXdirection,targetYdirection,targetZdirection);

                                if ( boom.length() < radius )
                                {
                                    if (boom.length() < 0.6) boom = explosionLocation.getDirection();

                                    boom = boom.setY(0).normalize().multiply(power * ( 1 - ( boom.length() / radius ))).setY(powerY);
                                    event.setDamage( Math.max( 1.0, damage * ( 1 - ( boom.length() / radius ))) );

//                                    target.teleport(target.getLocation().add(0, 0.5, 0));
                                    target.setSneaking(false);
//                                    double elytraMultiplier = player.getInventory().getChestplate() != null && player.getInventory().getChestplate().getType() == Material.ELYTRA ? 0.5 : 1;
                                    double elytraMultiplier = player.isGliding() ? 0.5 : 1;
                                    target.setVelocity(boom.multiply( projectile.getShooter() == player ? 1 : 0.8 ).multiply(elytraMultiplier).multiply(Math.max(1.0 - target.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).getValue(), 0.0 )));
                                }

                            }


                        }
                    }
                }

                if (Main.getConfigurator().config.getBoolean("allow-fake-death") && !event.isCancelled() && (player.getHealth() - event.getFinalDamage()) <= 0) {
                    event.setCancelled(true);
                    FakeDeath.die(gPlayer);
                }
            }
        }
    }

    @EventHandler
    public void onLaunchProjectile(ProjectileLaunchEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Projectile projectile = event.getEntity();
        if (projectile.getShooter() instanceof Player) {
            Player damager = (Player) projectile.getShooter();
            if (Main.isPlayerInGame(damager)) {
                if (Main.getPlayerGameProfile(damager).isSpectator) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        if (Main.isPlayerInGame(player)) {
            GamePlayer gPlayer = Main.getPlayerGameProfile(player);
            if (gPlayer.getGame().getStatus() != GameStatus.RUNNING || gPlayer.isSpectator) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFly(PlayerToggleFlightEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        if (Main.isPlayerInGame(player) && !Main.getPlayerGameProfile(player).isSpectator
               && (!player.hasPermission("bw.bypass.flight") && Main.getConfigurator().config.getBoolean("disable-flight"))) {
            event.setCancelled(true);
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.isCancelled() && event.getAction() != Action.RIGHT_CLICK_AIR) {
            return;
        }
        GamePlayer gPlayer = Main.getPlayerGameProfile(player);
        Game game = gPlayer.getGame();

        if ( game == null && !event.getPlayer().hasPermission("bw.admin")) {
            event.setCancelled(true);
            return;
        } else if ( game == null ) return;

//        if ( event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.CRAFTING_TABLE) {
//            event.setCancelled(false);
//            return;
//        }

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.CHEST && game.getStatus() == GameStatus.WAITING) {
            event.setCancelled(true);
            return;
        }

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

//            Bukkit.getConsoleSender().sendMessage("Interact RIGHTCLICK");
            if (game.getStatus() == GameStatus.WAITING || gPlayer.isSpectator) {
                event.setCancelled(true);
                if (event.getMaterial() == Material
                        .valueOf(Main.getConfigurator().config.getString("items.startgame", "DIAMOND"))) {
                    if (game.getStatus() == GameStatus.WAITING && (player.hasPermission("bw.vip.startitem")
                            || player.hasPermission("misat11.bw.vip.startitem"))) {
                        if (game.checkMinPlayers()) {
                            game.gameStartItem = true;
                        } else {
                            player.sendMessage(i18nc("vip_not_enough_players", game.getCustomPrefix()));
                        }
                    }
                } else if (event.getMaterial() == Material
                        .valueOf(Main.getConfigurator().config.getString("items.leavegame", "SLIME_BALL"))) {
                    game.leaveFromGame(player);
                } else if (event.getItem() != null && event.getItem().equals(FlowerUtils.getAbilitiesMenuItemStack())) {
                    String name = ChatColor.stripColor(player.getName());
//                    for (String string : FlowerBlackList.blacklist) {
//                        if (string.equalsIgnoreCase(name)) {
//                            player.sendMessage(ChatColor.RED + "Способности временно недоступны.");
//                            Bukkit.getConsoleSender().sendMessage("Blocked Abilities for " + name);
//                            return;
//                        }
//                    }
                    CustomGUI customGUI = new CustomGUI(player, "ABILITIES");
                    customGUI.load();
                } else if (event.getItem() != null && event.getItem().equals(FlowerUtils.getAbilitiesMenuLockedItemStack())) {
                    PlayerStatistic statistic = Main.getPlayerStatisticsManager().getStatistic(player);
                    NotificationManager.abilitySelectionLockedMessage(player, statistic.getGames(), FlowerUtils.unlockAbilitySelectionGamesPlayedRequirement);
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_HURT, 0.2F, 1.0F);
                }
            } else if (game.getStatus() == GameStatus.RUNNING) {
                if (event.getClickedBlock() != null) {
                    if (event.getClickedBlock().getType() == Material.ENDER_CHEST) {
                        Block chest = event.getClickedBlock();
                        CurrentTeam team = game.getTeamOfChest(chest);
                        event.setCancelled(true);

                        if (team == null) {
                            player.openInventory(game.getFakeEnderChest(gPlayer));
                            return;
                        }

                        if (!team.players.contains(gPlayer)) {
                            player.sendMessage(i18nc("team_chest_is_not_your", game.getCustomPrefix()));
                            return;
                        }

                        BedwarsTeamChestOpenEvent teamChestOpenEvent = new BedwarsTeamChestOpenEvent(game, player,
                                team);
                        Main.getInstance().getServer().getPluginManager().callEvent(teamChestOpenEvent);

                        if (teamChestOpenEvent.isCancelled()) {
                            return;
                        }

                        player.openInventory(team.getTeamChestInventory());
                    } else if (event.getClickedBlock().getState() instanceof InventoryHolder) {
                        InventoryHolder holder = (InventoryHolder) event.getClickedBlock().getState();
                        game.addChestForFutureClear(event.getClickedBlock().getLocation(), holder.getInventory());
                    } else if (event.getClickedBlock().getType().name().contains("CAKE")) {
                        if (game.getOriginalOrInheritedCakeTargetBlockEating()) {
                            if (game.getTeamOfPlayer(event.getPlayer()).getTargetBlock().equals(event.getClickedBlock().getLocation())) {
                                event.setCancelled(true);
                            } else {
                                if (Main.getConfigurator().config.getBoolean("disableCakeEating", true)) {
                                    event.setCancelled(true);
                                }
                                for (RunningTeam team : game.getRunningTeams()) {
                                    if (team.getTargetBlock().equals(event.getClickedBlock().getLocation())) {
                                        event.setCancelled(true);
                                        if (Main.isLegacy()) {
                                            if (event.getClickedBlock().getState().getData() instanceof org.bukkit.material.Cake) {
                                                org.bukkit.material.Cake cake = (org.bukkit.material.Cake) event.getClickedBlock().getState().getData();
                                                if (cake.getSlicesEaten() == 0) {
                                                    game.getRegion().putOriginalBlock(event.getClickedBlock().getLocation(), event.getClickedBlock().getState());
                                                }
                                                cake.setSlicesEaten(cake.getSlicesEaten() + 1);
                                                if (cake.getSlicesEaten() >= 6) {
                                                    game.bedDestroyed(event.getClickedBlock().getLocation(), event.getPlayer(), false, false, true);
                                                    event.getClickedBlock().setType(Material.AIR);
                                                } else {
                                                    BlockState state = event.getClickedBlock().getState();
                                                    state.setData(cake);
                                                    state.update();
                                                }
                                            }
                                        } else {
                                            Player113ListenerUtils.yummyCake(event, game);
                                        }
                                        break;
                                    }
                                }
                            }
                        } else if (Main.getConfigurator().config.getBoolean("disableCakeEating", true)) {
                            event.setCancelled(true);
                        }
                    } else if (event.getClickedBlock().getType() == Material.DRAGON_EGG && Main.getConfigurator().config.getBoolean("disableDragonEggTeleport", true)) {
                        event.setCancelled(true); // Fix - #432
                    }
                }
            }

            if (event.getClickedBlock() != null) {
                if (game.getRegion().isBedBlock(event.getClickedBlock().getState()) || event.getClickedBlock().getType().name().equals("RESPAWN_ANCHOR")) {
                    // prevent Essentials to set home in arena
                    event.setCancelled(true);

                    if (event.getAction() == Action.RIGHT_CLICK_BLOCK && game.getStatus() == GameStatus.RUNNING && !gPlayer.isSpectator) {
                        ItemStack stack = event.getItem();
                        if (stack != null && stack.getAmount() > 0) {
                            boolean anchorFilled = false;
                            if (game.getOriginalOrInheritedAnchorDecreasing()
                                    && event.getClickedBlock().getType().name().equals("RESPAWN_ANCHOR")
                                    && game.getPlayerTeam(gPlayer).teamInfo.bed.equals(event.getClickedBlock().getLocation())
                                    && event.getItem() != null && event.getItem().getType() == Material.GLOWSTONE) {
                                anchorFilled = Player116ListenerUtils.anchorCharge(event, game, stack);
                            }

                            if (!anchorFilled && stack.getType().isBlock()) {
                                if (
                                        /* These special items don't work with the feature below */
                                        APIUtils.unhashFromInvisibleStringStartsWith(stack, ProtectionWallListener.PROTECTION_WALL_PREFIX) == null
                                        && APIUtils.unhashFromInvisibleStringStartsWith(stack, RescuePlatformListener.RESCUE_PLATFORM_PREFIX) == null
                                ) {
                                        BlockFace face = event.getBlockFace();
                                        Block block = event.getClickedBlock().getLocation().clone().add(MiscUtils.getDirection(face))
                                                .getBlock();
                                        if (block.getType() == Material.AIR) {
                                            BlockState originalState = block.getState();
                                            block.setType(stack.getType());
                                            try {
                                                // The method is no longer in API, but in legacy versions exists
                                                Block.class.getMethod("setData", byte.class).invoke(block,
                                                        (byte) stack.getDurability());
                                            } catch (Exception e) {
                                            }
                                            BlockPlaceEvent bevent = new BlockPlaceEvent(block, originalState,
                                                    event.getClickedBlock(), stack, player, true);
                                            Bukkit.getPluginManager().callEvent(bevent);

                                            if (bevent.isCancelled()) {
                                                originalState.update(true, false);
                                            } else {
                                                if (player.getGameMode() != GameMode.CREATIVE) {
                                                    stack.setAmount(stack.getAmount() - 1);
                                                }
                                                if (!player.isSneaking()) {
                                                    // IDK get right block place sound
                                                    Sounds.BLOCK_STONE_PLACE.playSound(player, block.getLocation(), 1, 1);
                                                }
                                            }
                                        }
                                        }
                            }
                        }
                    }
                }
            }
        } else if (event.getAction() == Action.LEFT_CLICK_BLOCK &&
                game.getStatus() == GameStatus.RUNNING && !gPlayer.isSpectator
                && event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.DRAGON_EGG
                && Main.getConfigurator().config.getBoolean("disableDragonEggTeleport", true)) {
            event.setCancelled(true);
            BlockBreakEvent blockBreakEvent = new BlockBreakEvent(event.getClickedBlock(), player);
            Bukkit.getPluginManager().callEvent(blockBreakEvent);
            if (blockBreakEvent.isCancelled()) {
                return;
            }
            try {
                if (blockBreakEvent.isDropItems()) {
                    event.getClickedBlock().breakNaturally();
                } else {
                    event.getClickedBlock().setType(Material.AIR);
                }
            } catch (Throwable ignored) {
                // 1.8.8 :(
                event.getClickedBlock().setType(Material.AIR);
            }
        }
    }

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        if (Main.isPlayerInGame(player)) {
            GamePlayer gPlayer = Main.getPlayerGameProfile(player);
            Game game = gPlayer.getGame();
            if (game.getStatus() == GameStatus.WAITING || gPlayer.isSpectator) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onSleep(PlayerBedEnterEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (Main.isPlayerInGame(event.getPlayer())) {
            event.setCancelled(true);
        } else {
            for (String gameN : Main.getGameNames()) {
                Game game = Main.getGame(gameN);
                if (GameCreator.isInArea(event.getBed().getLocation(), game.getPos1(), game.getPos2())) {
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (event.isCancelled() || !(event.getPlayer() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getPlayer();
        if (Main.isPlayerInGame(player)) {
            GamePlayer gProfile = Main.getPlayerGameProfile(player);
            if (gProfile.getGame().getStatus() == GameStatus.RUNNING) {
                if (gProfile.isSpectator) {
                    event.setCancelled(event.getInventory().getType() != InventoryType.PLAYER);
                    return;
                }
                if (event.getInventory().getType() == InventoryType.ENCHANTING
                        || event.getInventory().getType() == InventoryType.CRAFTING
                        || event.getInventory().getType() == InventoryType.ANVIL
                        || event.getInventory().getType() == InventoryType.BREWING
                        || event.getInventory().getType() == InventoryType.FURNACE
//                        || event.getInventory().getType() == InventoryType.WORKBENCH
                ) {
                    if (!gProfile.getGame().getOriginalOrInheritedCrafting()) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInteractAtEntity(PlayerInteractAtEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if (Main.isPlayerInGame(player)) {
            Game game = Main.getPlayerGameProfile(player).getGame();
            if (!(entity instanceof LivingEntity)) {
                return;
            }

            if (game.getStatus() != GameStatus.WAITING) {
                return;
            }
            LivingEntity living = (LivingEntity) entity;
            String displayName = ChatColor.stripColor(living.getCustomName());

            for (Team team : game.getTeams()) {
                if (team.name.equals(displayName)) {
                    event.setCancelled(true);
                    game.selectTeam(Main.getPlayerGameProfile(player), displayName);
                    return;
                }
            }
        } else if (BaseCommand.hasPermission(player, ADMIN_PERMISSION, false)) {
            List<MetadataValue> values = player.getMetadata(GameCreator.BEDWARS_TEAM_JOIN_METADATA);
            if (values.size() == 0) {
                return;
            }

            event.setCancelled(true);
            TeamJoinMetaDataValue value = (TeamJoinMetaDataValue) values.get(0);
            if (!((boolean) value.value())) {
                return;
            }

            if (!(entity instanceof LivingEntity)) {
                player.sendMessage(i18n("admin_command_jointeam_entitynotcompatible"));
                return;
            }

            LivingEntity living = (LivingEntity) entity;
            living.setRemoveWhenFarAway(false);
            living.setCanPickupItems(false);
            living.setCustomName(value.getTeam().color.chatColor + value.getTeam().name);
            living.setCustomNameVisible(Main.getConfigurator().config.getBoolean("jointeam-entity-show-name", true));

            if (living instanceof ArmorStand) {
                ArmorStandUtils.equipArmorStand((ArmorStand) living, value.getTeam());
            }

            player.removeMetadata(GameCreator.BEDWARS_TEAM_JOIN_METADATA, Main.getInstance());
            player.sendMessage(i18n("admin_command_jointeam_entity_added"));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent je) {
//        je.getPlayer().sendMessage(" ");
//        je.getPlayer().sendMessage(" ");
//        je.getPlayer().sendMessage( IconsManager.blue_excl + ColoursManager.ua_yellow + " Слава" + ColoursManager.ua_blue + " Украине" + ColoursManager.ua_yellow + "!");
//        je.getPlayer().sendMessage(" ");
//        je.getPlayer().sendMessage(" ");
        final Player player = je.getPlayer();

        if (Main.isHologramsEnabled()) {
            Main.getHologramInteraction().updateHolograms(player, 10L);
            Main.getLeaderboardHolograms().addViewer(player);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onChat(AsyncPlayerChatEvent event) {
        Bukkit.getConsoleSender().sendMessage( event.getPlayer().getName() + " > " + event.getMessage());
//        Bukkit.getConsoleSender().sendMessage("Cancelled: " + event.isCancelled());
//        Bukkit.getConsoleSender().sendMessage("Recepients amount: " + event.getRecipients());
//        if (event.isCancelled() || !Main.getConfigurator().config.getBoolean("chat.override")) {
//            return;
//        }


        Player player = event.getPlayer();
        if (Main.isPlayerInGame(player)) {

            event.setCancelled(true);
            GamePlayer gPlayer = Main.getPlayerGameProfile(player);
            Game game = gPlayer.getGame();
            CurrentTeam team = gPlayer.latestCurrentTeam;
//            CurrentTeam team = game.getPlayerTeam(gPlayer);
            String message = event.getMessage();
            boolean spectator = gPlayer.isSpectator;

                    String playerName = player.getName();
            String displayName = player.getDisplayName();
            String playerListName = player.getPlayerListName();

//            TextComponent textComponent = Component.text().append(Component.text("sex")).build();

//            player.sendMessage(Component.text("meow").color(TextColor.fromHexString("#112299")));
//            player.sendMessage(Component.text("meow").color(TextColor.color(218, 255, 0)));
//            player.sendMessage(Component.text("meow").color(TextColor.color(0xFF8F20)));
//            player.sendMessage(Component.text("meow").color(ColoursManager.getComponent(ColoursManager.orange)));
//            player.sendMessage(Component.text("meow").color(ColoursManager.getComponent(ColoursManager.darkRed)));
//            player.sendMessage(Component.text("meow").color(ColoursManager.getComponent(ColoursManager.light_blue)));
//            player.sendMessage(net.md_5.bungee.api.ChatColor.of("#112299").getColor().toString() + " meow");
//            player.sendMessage(net.md_5.bungee.api.ChatColor.of("#112299").getColor().getRed() + " meow");
//            player.sendMessage(net.md_5.bungee.api.ChatColor.of("#112299").getColor().getGreen() + " meow");
//            player.sendMessage(net.md_5.bungee.api.ChatColor.of("#112299").getColor().getBlue() + " meow");
//            player.sendMessage("test = " + ColoursManager.red.toString());

//            player.sendMessage(textComponent);

//            player.sendMessage(gPlayer.ownedAbilities.get(0).parseDescriptionComponent(1));

            boolean teamChat = Main.getConfigurator().config.getBoolean("chat.default-team-chat-while-running", true)
                    && game.getStatus() == GameStatus.RUNNING && (team != null || spectator);

            String allChat = Main.getConfigurator().config.getString("chat.all-chat-prefix", "@a");
            String tChat = Main.getConfigurator().config.getString("chat.team-chat-prefix", "@t");

            if (message.startsWith(allChat) && (!spectator || !Main.getConfigurator().config.getBoolean("chat.disable-all-chat-for-spectators"))) {
                teamChat = false;
                message = message.substring(allChat.length()).trim();
            } else if (message.startsWith(tChat) && (team != null || spectator)) {
                teamChat = true;
                message = message.substring(tChat.length()).trim();
            }

            ComponentBuilder chatMessage = Component.text();

            if (teamChat) {
                chatMessage//.append(Component.text("[").color(ColoursManager.getComponent(ColoursManager.gray)))
                        .append(Component.text(team != null ? team.teamInfo.color.bed.getIcon() : IconType.BED_GRAY.getIcon())) //.color(ColoursManager.getComponent(team != null ? team.teamInfo.color.chatColor : ColoursManager.darkGray)))
                        //.append(Component.text("]").color(ColoursManager.getComponent(ColoursManager.gray)))
                ;
            } else if (spectator) {
                chatMessage//.append(Component.text("[").color(ColoursManager.getComponent(ColoursManager.gray)))
                        .append(Component.text(IconType.BED_CROSSED.getIcon()))//.color(ColoursManager.getComponent(ColoursManager.darkGray)))
                        //.append(Component.text("]").color(ColoursManager.getComponent(ColoursManager.gray)))
                ;
            } else {
                chatMessage//.append(Component.text("[").color(ColoursManager.getComponent(ColoursManager.gray)))
                        .append(Component.text(IconType.GLOBAL.getIcon()))//.color(ColoursManager.getComponent(ColoursManager.white)))
                        //.append(Component.text("]").color(ColoursManager.getComponent(ColoursManager.gray)))
                ;
            }

            chatMessage.append(Component.text(" "));


            chatMessage.append(NotificationManager.generatePlayerNameWithAbilitiesComponent( gPlayer, team != null ? team.teamInfo.color.chatColor : ColoursManager.white));
            chatMessage.append(Component.text(" » ").color(ColoursManager.getComponent(ColoursManager.gray)));


//            event.setFormat(format + message.replaceAll("%", "%%")); // Fix using % in chat

            chatMessage.append(Component.text(message));

            Iterator<Player> recipients = event.getRecipients().iterator();
            while (recipients.hasNext()) {
                Player recipient = recipients.next();
                GamePlayer recipientgPlayer = Main.getPlayerGameProfile(recipient);
                Game recipientGame = recipientgPlayer.getGame();
                if (recipientGame != game) {
                    if ((game.getStatus() == GameStatus.WAITING && Main.getConfigurator().config.getBoolean("chat.separate-chat.lobby"))
                            || (game.getStatus() != GameStatus.WAITING && Main.getConfigurator().config.getBoolean("chat.separate-chat.game"))) {
                        recipients.remove();
                    }
                } else if (game.getPlayerTeam(recipientgPlayer) != team && teamChat) {
                    recipients.remove();
                }
            }

            for (Player p : event.getRecipients()) {
                p.sendMessage(chatMessage.build());
//                p.sendMessage(event.getFormat());
            }
//            event.setCancelled(true);
        } else {
            if (Main.getConfigurator().config.getBoolean("chat.separate-chat.lobby") || Main.getConfigurator().config.getBoolean("chat.separate-chat.game")) {
                Iterator<Player> recipients = event.getRecipients().iterator();
                while (recipients.hasNext()) {
                    Player recipient = recipients.next();
                    GamePlayer recipientgPlayer = Main.getPlayerGameProfile(recipient);
                    Game recipientGame = recipientgPlayer.getGame();
                    if (recipientGame != null) {
                        if ((recipientGame.getStatus() == GameStatus.WAITING && Main.getConfigurator().config.getBoolean("chat.separate-chat.lobby"))
                                || (recipientGame.getStatus() != GameStatus.WAITING && Main.getConfigurator().config.getBoolean("chat.separate-chat.game"))) {
                            recipients.remove();
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlaceLiquid(PlayerBucketEmptyEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        if (Main.isPlayerInGame(player)) {
            GamePlayer gPlayer = Main.getPlayerGameProfile(player);
            Game game = gPlayer.getGame();
            Location loc = event.getBlockClicked().getLocation();

            loc.add(MiscUtils.getDirection(event.getBlockFace()));

            Block block = loc.getBlock();
            if (game.getStatus() == GameStatus.RUNNING) {
                if (block.getType() == Material.AIR || game.getRegion().isBlockAddedDuringGame(block.getLocation())) {
                    game.getRegion().addBuiltDuringGame(block.getLocation());
                } else {
                    event.setCancelled(true);
                }
            } else if (game.getStatus() != GameStatus.DISABLED) {
                event.setCancelled(true);
            }
        } else if (Main.getConfigurator().config.getBoolean("preventArenaFromGriefing")) {
            for (String gameN : Main.getGameNames()) {
                Game game = Main.getGame(gameN);
                if (game.getStatus() != GameStatus.DISABLED && GameCreator.isInArea(event.getBlockClicked().getLocation(), game.getPos1(), game.getPos2())) {
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

    /* This event was replaced on 1.12 with newer (event handling is devided between Player112Listener and PlayerBefore112Listener) */
    public static void onItemPickup(Player player, Item item, Cancellable cancel) {
        if (cancel.isCancelled()) {
            return;
        }

        if (Main.isPlayerInGame(player)) {
            GamePlayer gPlayer = Main.getPlayerGameProfile(player);
            Game game = gPlayer.getGame();
            if (game.getStatus() == GameStatus.WAITING || gPlayer.isSpectator) {
                cancel.setCancelled(true);
            } else {
                for (ItemSpawner spawner : game.getSpawners()) {
                    if (spawner.getMaxSpawnedResources() > 0) {
                        spawner.remove(item);
                    }
                }
            }
        }
    }
}
