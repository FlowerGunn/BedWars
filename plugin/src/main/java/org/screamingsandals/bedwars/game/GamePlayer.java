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

package org.screamingsandals.bedwars.game;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.utils.BungeeUtils;
import org.screamingsandals.bedwars.lib.nms.entity.PlayerUtils;
import org.screamingsandals.bedwars.utils.flowergun.customgui.shoputils.ShopInstance;
import org.screamingsandals.bedwars.utils.flowergun.customgui.shoputils.GameFlag;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.Ability;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.LoadedAbility;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.OwnedAbility;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.comparators.SortByRarityOwnedAbility;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.enums.DamageInstance;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.enums.DamageRelay;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.enums.DamageType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GamePlayer {
    public final Player player;
    public DamageRelay damageRelayAttackInstance = DamageRelay.MELEE;
    public DamageRelay damageRelayDefenceInstance;
    private Game game = null;

    public DamageType damageTypeAttackInstance = DamageType.PHYSICAL;
    public DamageType damageTypeDefenceInstance = DamageType.PHYSICAL;
    private String latestGame = null;
    private StoredInventory oldInventory = new StoredInventory();
    private List<Player> hiddenPlayers = new ArrayList<>();

    private ShopInstance customGUIShopInstance;
    public DamageInstance lastReceivedDamageInstance;

    public ShopInstance getCustomGUIShopInstance(){
        return this.customGUIShopInstance;
    }

    public void setCustomGUIShopInstance(ShopInstance shopInstance){
        this.customGUIShopInstance = shopInstance;
    }

    public List<GameFlag> playerFlags = new ArrayList<>();

    public int lastDeathCounter;

    public boolean blockElytra = false;

    public boolean isSpectator = false;
    public boolean isTeleportingFromGame_justForInventoryPlugins = false;
    public boolean mainLobbyUsed = false;

    public ArrayList<LoadedAbility> loadedAbilities = new ArrayList<>();
    public ArrayList<OwnedAbility> ownedAbilities = new ArrayList<>();

    private int previousFlagCount;

    public GamePlayer(Player player) {

        //WAYPOINT TODO load player owned cosmetics

        this.ownedAbilities = new ArrayList<>();
        for ( Class clazz : Main.getInstance().getAbilitiesManager().getAllAbilities() ) {
            this.ownedAbilities.add(new OwnedAbility( 0 ,player, Ability.generateAbility(clazz), 3, 1, 0, -1 ));
        }

        Collections.sort(this.ownedAbilities, new SortByRarityOwnedAbility());

        this.player = player;
    }

    public void resetAbilitySlot(int slot) {
        this.loadedAbilities.set(slot, null);
    }

    public void resetLoadedAbilityById(String id) {
        return;
    }

    public void resetLoadedAbility(OwnedAbility ownedAbility) {
//        Bukkit.getConsoleSender().sendMessage("step0 " + ownedAbility.getAbility().getId());
        for ( int i = 0; i < this.loadedAbilities.size(); i++ ) {
            LoadedAbility loadedAbility = this.loadedAbilities.get(i);
//            Bukkit.getConsoleSender().sendMessage("step1");
            if ( loadedAbility == null ) continue;
//            Bukkit.getConsoleSender().sendMessage("step2 " + loadedAbility.getOwnedAbility().getAbility().getId());
            if ( loadedAbility.getOwnedAbility().getAbility().getId().equals(ownedAbility.getAbility().getId()) ) {
                this.loadedAbilities.set(i, null);
                return;
            }
        }
    }

    public OwnedAbility getOwnedAbilityById(String id) {
        for ( OwnedAbility ownedAbility : this.ownedAbilities ) {
            if ( ownedAbility.getAbility().getId().equals(id) ) {
                return ownedAbility;
            }
        }
        return null;
    }

    public void changeGame(Game game) {

        //WAYPOINT flags reset
        this.previousFlagCount = -1;
        this.playerFlags = new ArrayList<>();
        this.damageTypeAttackInstance = DamageType.PHYSICAL;
        this.damageTypeDefenceInstance = DamageType.PHYSICAL;
        this.damageRelayAttackInstance = DamageRelay.MELEE;
        this.damageRelayDefenceInstance = DamageRelay.MELEE;

        this.lastReceivedDamageInstance = null;

        //WAYPOINT TODO saving loaded abilities between matches
        this.loadedAbilities = new ArrayList<>();
        this.loadedAbilities.add(null);
        this.loadedAbilities.add(null);
        this.loadedAbilities.add(null);

        if (this.game != null && game == null) {
            this.game.internalLeavePlayer(this);
            this.game = null;
            this.isSpectator = false;
            this.blockElytra = false;
            this.clean();
            if (Game.isBungeeEnabled()) {
                BungeeUtils.movePlayerToBungeeServer(player, Main.isDisabling());
            } else {
                this.restoreInv();
            }
        } else if (this.game == null && game != null) {
            this.storeInv();
            this.clean();
            this.game = game;
            this.isSpectator = false;
            this.mainLobbyUsed = false;
            this.game.internalJoinPlayer(this);
            if (this.game != null) {
                this.latestGame = this.game.getName();
            }
        } else if (this.game != null) {
            this.game.internalLeavePlayer(this);
            this.game = game;
            this.isSpectator = false;
            this.clean();
            this.mainLobbyUsed = false;
            this.game.internalJoinPlayer(this);
            if (this.game != null) {
                this.latestGame = this.game.getName();
            }
        }
    }

    public Game getGame() {
        return game;
    }

    public String getLatestGameName() {
        return this.latestGame;
    }

    public boolean isInGame() {
        return game != null;
    }

    public boolean canJoinFullGame() {
        return player.hasPermission("bw.vip.forcejoin") || player.hasPermission("misat11.bw.vip.forcejoin");
    }

    public void storeInv() {
        oldInventory.inventory = player.getInventory().getContents();
        oldInventory.armor = player.getInventory().getArmorContents();
        oldInventory.xp = player.getExp();
        oldInventory.effects = player.getActivePotionEffects();
        oldInventory.mode = player.getGameMode();
        oldInventory.leftLocation = player.getLocation();
        oldInventory.level = player.getLevel();
        oldInventory.listName = player.getPlayerListName();
        oldInventory.displayName = player.getDisplayName();
        oldInventory.foodLevel = player.getFoodLevel();
    }

    public void restoreInv() {
        isTeleportingFromGame_justForInventoryPlugins = true;
        if (!mainLobbyUsed) {
            teleport(oldInventory.leftLocation, this::restoreRest);
        } else {
            mainLobbyUsed = false;
            restoreRest();
        }
    }

    private void restoreRest() {
        player.getInventory().setContents(oldInventory.inventory);
        player.getInventory().setArmorContents(oldInventory.armor);

        player.setLevel(oldInventory.level);
        player.setExp(oldInventory.xp);
        player.setFoodLevel(oldInventory.foodLevel);

        for (PotionEffect e : player.getActivePotionEffects())
            player.removePotionEffect(e.getType());

        player.addPotionEffects(oldInventory.effects);

        player.setPlayerListName(oldInventory.listName);
        player.setDisplayName(oldInventory.displayName);

        player.setGameMode(oldInventory.mode);

        player.setAllowFlight(oldInventory.mode == GameMode.CREATIVE || oldInventory.mode == GameMode.SPECTATOR);

        player.updateInventory();
        player.resetPlayerTime();
        player.resetPlayerWeather();
    }

    public void resetLife() {
        this.player.setAllowFlight(false);
        this.player.setFlying(false);
        this.player.setExp(0.0F);
        this.player.setLevel(0);
        this.player.setSneaking(false);
        this.player.setSprinting(false);
        this.player.setFoodLevel(20);
        this.player.setSaturation(10);
        this.player.setExhaustion(0);
        this.player.setMaxHealth(20D);
        this.player.setHealth(this.player.getMaxHealth());
        this.player.setFireTicks(0);
        this.player.setFallDistance(0);
        this.player.setGameMode(GameMode.SURVIVAL);

        if (this.player.isInsideVehicle()) {
            this.player.leaveVehicle();
        }

        for (PotionEffect e : this.player.getActivePotionEffects()) {
            this.player.removePotionEffect(e.getType());
        }
    }

    public void invClean() {
        PlayerInventory inv = this.player.getInventory();
        inv.setArmorContents(new ItemStack[4]);
        inv.setContents(new ItemStack[]{});

        this.player.updateInventory();
    }

    public void clean() {
        invClean();
        resetLife();
        new ArrayList<>(this.hiddenPlayers).forEach(this::showPlayer);
    }

    public boolean teleport(Location location) {
    	return PlayerUtils.teleportPlayer(player, location);
    }

    public boolean teleport(Location location, Runnable runnable) {
        return PlayerUtils.teleportPlayer(player, location, runnable);
    }

    public void hidePlayer(Player player) {
        if (!hiddenPlayers.contains(player) && !player.equals(this.player)) {
            hiddenPlayers.add(player);
            try {
                this.player.hidePlayer(Main.getInstance(), player);
            } catch (Throwable t) {
                this.player.hidePlayer(player);
            }
        }
    }

    public void showPlayer(Player player) {
        if (hiddenPlayers.contains(player) && !player.equals(this.player)) {
            hiddenPlayers.remove(player);
            try {
                this.player.showPlayer(Main.getInstance(), player);
            } catch (Throwable t) {
                this.player.showPlayer(player);
            }
        }

    }

    public List<GameFlag> getAllPlayerFlags() {
        CurrentTeam team = this.game.getPlayerTeam(this);

        ArrayList<GameFlag> flags = new ArrayList<>();


        ArrayList<GameFlag> gameFlags = new ArrayList<>(game.gameFlags);
        ArrayList<GameFlag> playerFlags = new ArrayList<>(this.playerFlags);

        flags.addAll(gameFlags);
        flags.addAll(playerFlags);

        if (team == null) return flags;
        ArrayList<GameFlag> teamFlags = new ArrayList<>(team.teamFlags);
        flags.addAll(teamFlags);

        return flags;
    }

    public boolean hasFlag(GameFlag gameFlag) {
        return getAllPlayerFlags().contains(gameFlag);
    }

    public boolean didFlagAmountChange() {
        int currentFlagCount = this.getAllPlayerFlags().size();
        Bukkit.getConsoleSender().sendMessage(this.previousFlagCount + " < prev | current > " + currentFlagCount + " | " + this.player.getName());
        if (this.previousFlagCount == currentFlagCount) {
            this.previousFlagCount = currentFlagCount;
            return false;
        }
        else {
            this.previousFlagCount = currentFlagCount;
            return true;
        }
    }

}
