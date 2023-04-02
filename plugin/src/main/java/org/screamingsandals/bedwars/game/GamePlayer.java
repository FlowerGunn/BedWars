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

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.api.game.GameStatus;
import org.screamingsandals.bedwars.utils.external.BungeeUtils;
import org.screamingsandals.bedwars.lib.nms.entity.PlayerUtils;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.*;
import org.screamingsandals.bedwars.utils.flowergun.managers.AbilitiesManager;
import org.screamingsandals.bedwars.utils.flowergun.managers.ForgeManager;
import org.screamingsandals.bedwars.utils.flowergun.managers.NewPlayerExperienceManager;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;
import org.screamingsandals.bedwars.utils.flowergun.shoputils.ShopInstance;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.LoadedAbility;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.OwnedAbility;
import org.screamingsandals.bedwars.utils.flowergun.other.comparators.SortByRarityOwnedAbility;
import org.screamingsandals.bedwars.utils.flowergun.mechanics.ImpactInstance;
import org.screamingsandals.bedwars.utils.flowergun.mechanics.ImpactLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.screamingsandals.bedwars.game.Game.ALL_ABILITIES_MODE;

public class GamePlayer {
    public Player player;
    public ArrayList<ActiveForgeRecipe> activeForgeRecipes;
    public ArrayList<ForgeRecipe> availableRecipes;

    //    public boolean isReturning = false;
//    public boolean isDisconnected = false;
    @Getter
    private ImpactLog impactLog;
    public DamageRelay damageRelayAttackInstance = DamageRelay.MELEE;
    public DamageRelay damageRelayDefenceInstance;
    public Game game = null;


    public CurrentTeam latestCurrentTeam;
    private StoredInventory oldInventory = new StoredInventory();
    private List<Player> hiddenPlayers = new ArrayList<>();

    private final double baseMaxHealth = 20.0;
    private final double baseKnockbackResistance = 1.0;
    private final double baseMovementSpeed = 1.0;
    private final double baseAttackSpeed = 1.0;
    private final double baseArmor = 1.0;
    private final double baseArmorToughness = 1.0;

    private ShopInstance customGUIShopInstance;
    public DamageInstance lastReceivedDamageInstance;
    public DamageInstance lastDealtDamageInstance;
    public DamageInstance incomingReceivedDamageInstance;
    public DamageInstance incomingDealtDamageInstance;

    @Getter
    @Setter
    private MenuType lastMenuVisited;

    public ShopInstance getCustomGUIShopInstance(){
        return this.customGUIShopInstance;
    }

    public void setCustomGUIShopInstance(ShopInstance shopInstance){
        this.customGUIShopInstance = shopInstance;
    }

    public List<GameFlag> playerFlags = new ArrayList<>();

    public OwnedResourceBundle ownedResourceBundle = new OwnedResourceBundle();

    public int lastDeathCounter;

    public boolean blockElytra = false;

    public boolean isSpectator = false;
    public boolean isTeleportingFromGame_justForInventoryPlugins = false;
    public boolean mainLobbyUsed = false;

    public ArrayList<LoadedAbility> loadedAbilities = new ArrayList<>();
    public ArrayList<OwnedAbility> ownedAbilities = new ArrayList<>();

    private int previousFlagCount;

    private ArrayList<CustomStatusEffect> customStatusEffects = new ArrayList<>();

    public GamePlayer(Player player) {

        this.latestCurrentTeam = null;
//        Bukkit.getConsoleSender().sendMessage("player generated and reset the currentteam");

        this.player = player;
        //WAYPOINT DONE load player owned abilities

        this.ownedAbilities = Main.getInstance().getAbilitiesManager().getAllAbilitiesByUUID(player.getUniqueId());


        for ( Class clazz : Main.getInstance().getAbilitiesManager().getAllAbilitiesClasses() ) {
            IAbility ability = Ability.generateAbility(clazz);
            if ( !AbilitiesManager.abilitiesContainId(this.ownedAbilities, ability.getId()) )
            this.ownedAbilities.add(new OwnedAbility( -1 ,player, ability, 0, 0, 0, -1 ));
        }

        Collections.sort(this.ownedAbilities, new SortByRarityOwnedAbility());

        this.impactLog = new ImpactLog();

        Main.getForgeManager().loadActiveForgeRecipes(this);
        loadAllResources();
        ForgeManager.loadAvailableRecipes(this);

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
        NewPlayerExperienceManager.loadPlayerStarterGoodies(this);
        }, 100L);

    }

    private void loadAllResources() {
        ownedResourceBundle.addAllResources(Main.getInstance().getResourceManager().getAllResourcesByUUID(this.player.getUniqueId()));
    }

    public void resetAbilitySlot(int slot) {
        this.loadedAbilities.set(slot, LoadedAbility.getEmptyLoadedAbility());
    }

    public void resetLoadedAbilityById(String id) {
        return;
    }

    public void resetLoadedAbility(OwnedAbility ownedAbility) {
//        Bukkit.getConsoleSender().sendMessage("step0 " + ownedAbility.getAbility().getId());
        for ( int i = 0; i < this.loadedAbilities.size(); i++ ) {
            LoadedAbility loadedAbility = this.loadedAbilities.get(i);
//            Bukkit.getConsoleSender().sendMessage("step1");
            if ( loadedAbility.isEmpty() ) continue;
//            Bukkit.getConsoleSender().sendMessage("step2 " + loadedAbility.getOwnedAbility().getAbility().getId());
            if ( loadedAbility.getOwnedAbility().getAbility().getId().equals(ownedAbility.getAbility().getId()) ) {
                this.loadedAbilities.set(i, LoadedAbility.getEmptyLoadedAbility());
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

    public OwnedAbility getTrulyOwnedAbilityById(String id) {
        for ( OwnedAbility ownedAbility : this.ownedAbilities ) {
            if ( ownedAbility.getAbility().getId().equals(id) ) {
                if ( ownedAbility.isOwned() )
                    return ownedAbility;
                else return null;
            }
        }
        return null;
    }

    public void changeGame(Game game) {

        //WAYPOINT flags reset
        this.previousFlagCount = -1;
        this.playerFlags = new ArrayList<>();
        this.lastReceivedDamageInstance = null;
        this.impactLog = new ImpactLog();

        this.customStatusEffects.clear();
        this.recalculateCustomEffects();

//        this.isDisconnected = false;

        //WAYPOINT DONE saving loaded abilities between matches
//        Bukkit.getConsoleSender().sendMessage("resetting abilities for " + player.getName());
        this.loadedAbilities = new ArrayList<>();
        this.loadedAbilities.add(LoadedAbility.getEmptyLoadedAbility());
        this.loadedAbilities.add(LoadedAbility.getEmptyLoadedAbility());
        this.loadedAbilities.add(LoadedAbility.getEmptyLoadedAbility());


        for ( OwnedAbility ownedAbility : this.ownedAbilities ) {
            if ( ownedAbility.lastEquippedSlot >= 0 ) {

                int slot = ownedAbility.lastEquippedSlot;
                int level = ownedAbility.ownedLevel;

                if (slot + 1 < level) level = slot + 1;

                this.loadedAbilities.set(slot, new LoadedAbility(ownedAbility, level));

            }
        }

        this.blockElytra = false;

        if (this.game != null && game == null) { // leaving the game
            this.game.internalLeavePlayer(this);
            this.game = null;
//            this.isSpectator = false;
            this.clean();
            if (Game.isBungeeEnabled()) {
                BungeeUtils.movePlayerToBungeeServer(player, Main.isDisabling());
            } else {
//                this.restoreInv();
            }
        } else if (this.game == null && game != null) { // joining the game
//            this.storeInv();
            this.clean();
            this.game = game;
            this.isSpectator = false;
            this.mainLobbyUsed = false;
            this.game.internalJoinPlayer(this);
            this.latestCurrentTeam = null;
            Bukkit.getConsoleSender().sendMessage("player joined a game and reset the currentteam");
//            if (this.game != null) {
//                this.latestGame = this.game.getName();
//            }
        } else if (this.game != null) { // switching between games
            this.game.internalLeavePlayer(this);
            this.game = game;
            this.isSpectator = false;
            this.clean();
            this.mainLobbyUsed = false;
            this.game.internalJoinPlayer(this);
//            if (this.game != null) {
//                this.latestGame = this.game.getName();
//            }
        }
    }

    public Game getGame() {
        return game;
    }

//    public String getLatestGameName() {
//        return this.latestGame;
//    }

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
        this.player.setAbsorptionAmount(0);

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
        if (location != null)
        return PlayerUtils.teleportPlayer(player, location, runnable);
        else {
            runnable.run();
            return true;
        }
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

        ArrayList<GameFlag> flags = new ArrayList<>();

        ArrayList<GameFlag> playerFlags = new ArrayList<>(this.playerFlags);

        flags.addAll(playerFlags);

        if (this.game == null) return flags;
        ArrayList<GameFlag> gameFlags = new ArrayList<>(game.gameFlags);
        flags.addAll(gameFlags);

        CurrentTeam team = this.game.getPlayerTeam(this);

        if (team == null) return flags;
        ArrayList<GameFlag> teamFlags = new ArrayList<>(team.teamFlags);
        flags.addAll(teamFlags);

        return flags;
    }

    public boolean hasFlag(GameFlag gameFlag) {
        if ( this.game == null ) return false;
        if ( this.game.getPlayerTeam(this) == null ) return false;
        return getAllPlayerFlags().contains(gameFlag);
    }

    public boolean didFlagAmountChange() {
        int currentFlagCount = this.getAllPlayerFlags().size();
//        Bukkit.getConsoleSender().sendMessage(this.previousFlagCount + " < prev | current > " + currentFlagCount + " | " + this.player.getName());
        if (this.previousFlagCount == currentFlagCount) {
            this.previousFlagCount = currentFlagCount;
            return false;
        }
        else {
            this.previousFlagCount = currentFlagCount;
            return true;
        }
    }

    public void logImpactInstance(ImpactInstance impactInstance) {
        this.impactLog.getImpactInstances().add(0,impactInstance);
    }

    public GamePlayer getImpactLogKiller() {
        return this.impactLog.getKiller(game.countdown);
    };

    public ArrayList<GamePlayer> getImpactLogAssisters() {
        return this.impactLog.getAssisters(game.countdown);
    };

    public void flushImpactLog() {
        this.impactLog = new ImpactLog();
    }

    public CompoundValueModifier extractCustomEffects( CustomStatusEffectType customStatusEffectType, DamageInstance pattern ) {
        CompoundValueModifier modifier = new CompoundValueModifier();
        for (CustomStatusEffect effect : this.customStatusEffects) {
            if ( effect.type == customStatusEffectType ) {
                if (pattern.contains(effect.damageExample)) {
                    modifier.join(effect.valueChange);
                }
            }
        }
        return modifier;
    }

    public void recalculateCustomEffects() {

//        Bukkit.getConsoleSender().sendMessage("recalculating custom statuses");

        CompoundValueModifier healthModifier = new CompoundValueModifier();
        CompoundValueModifier speedModifier = new CompoundValueModifier();
        CompoundValueModifier armorModifier = new CompoundValueModifier();
        CompoundValueModifier attackSpeedModifier = new CompoundValueModifier();

        for ( int i = 0; i < this.customStatusEffects.size(); i++ ) {
            CustomStatusEffect effect = this.customStatusEffects.get(i);
            if ( !effect.isActive && !effect.isPermanent ) {

//                Bukkit.getConsoleSender().sendMessage("removing expired custom status");
                this.customStatusEffects.remove(effect);
                i--;
            }
            else {
                switch (effect.attribute) {
                    case GENERIC_MOVEMENT_SPEED -> {
                        speedModifier.join( effect.valueChange );
                    }
                    case GENERIC_MAX_HEALTH -> {
                        healthModifier.join( effect.valueChange );
                    }
                    case GENERIC_ARMOR -> {
                        armorModifier.join( effect.valueChange );
                    }
                    case GENERIC_ATTACK_SPEED -> {
                        attackSpeedModifier.join( effect.valueChange );
                    }
                }
//                if ( effect.attribute == Attribute.GENERIC_MOVEMENT_SPEED ) {
//                    speedModifier.join( effect.valueChange );
//                } else if ( effect.attribute == Attribute.GENERIC_MAX_HEALTH ) {
//                    healthModifier.join( effect.valueChange );
//                } else if ( effect.attribute == Attribute.GENERIC_ARMOR ) {
//                    armorModifier.join( effect.valueChange );
//                }
            }
        }


//        Bukkit.getConsoleSender().sendMessage("applying custom status");

//        Bukkit.getConsoleSender().sendMessage("base hp = " + player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() );
//        Bukkit.getConsoleSender().sendMessage("value hp = " + player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() );
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue( healthModifier.processValueEffectiveDecrease(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue()) );
//        Bukkit.getConsoleSender().sendMessage("default speed = " + player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getDefaultValue() );
//        Bukkit.getConsoleSender().sendMessage("base speed = " + player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() );
//        Bukkit.getConsoleSender().sendMessage("value speed = " + player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue() );
//        Bukkit.getConsoleSender().sendMessage("modified speed = " + speedModifier.processValueEffectiveDecrease(player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getDefaultValue()) );
//        Bukkit.getConsoleSender().sendMessage("modifier 1 -> " + speedModifier.processValueEffectiveDecrease(1.0) );
        player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue( speedModifier.processValueEffectiveDecrease(FlowerUtils.defaultSpeedValue) );
//        Bukkit.getConsoleSender().sendMessage("value speed AGAIN = " + player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue() );
//
//        Bukkit.getConsoleSender().sendMessage("base armor = " + player.getAttribute(Attribute.GENERIC_ARMOR).getBaseValue() );
//        Bukkit.getConsoleSender().sendMessage("value armor = " + player.getAttribute(Attribute.GENERIC_ARMOR).getValue() );
        player.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue( armorModifier.processValueEffectiveDecrease(player.getAttribute(Attribute.GENERIC_ARMOR).getDefaultValue()) );
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue( attackSpeedModifier.processValueEffectiveDecrease(player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).getDefaultValue()) );

    }

    public void addCustomStatusEffect(CustomStatusEffect customStatusEffect) {
//        Bukkit.getConsoleSender().sendMessage("adding custom status");
        for ( int i = 0; i < this.customStatusEffects.size(); i++ ) {
            CustomStatusEffect effect = this.customStatusEffects.get(i);

//            Bukkit.getConsoleSender().sendMessage("current status...");
            if ( effect.effectId.equals(customStatusEffect.effectId) )
            {
//                Bukkit.getConsoleSender().sendMessage("removing custom status");
                this.customStatusEffects.remove(effect);
                i--;
            }
        }
        this.customStatusEffects.add(customStatusEffect);
        recalculateCustomEffects();
        customStatusEffect.runTaskLater(Main.getInstance(), customStatusEffect.ticksDuration);
    }

    public void removeCustomStatusEffectById( String id ) {
        for ( int i = 0; i < this.customStatusEffects.size(); i++ ) {
            CustomStatusEffect effect = this.customStatusEffects.get(i);

//            Bukkit.getConsoleSender().sendMessage("current status...");
            if ( effect.effectId.equals(id) )
            {
                this.customStatusEffects.remove(effect);
                return;
            }
        }
    }

    public String getAbilitiesIcons() {
        if (this.getGame() == null) return "";
        if (this.getGame().getStatus() == GameStatus.RUNNING || this.getGame().getStatus() == GameStatus.GAME_END_CELEBRATING) {
            String icons = "";
            for ( LoadedAbility loadedAbility : this.loadedAbilities) {
                if ( loadedAbility.isEmpty() ) {
                    icons += ChatColor.RESET + IconType.SLOT_LOCKED.getIcon(player) + ChatColor.RESET;
                } else {
                    icons += ChatColor.RESET + loadedAbility.getOwnedAbility().getAbility().getIconString(player);
                }
            }
            icons += " ";
            return icons;
        }
        else return "";
    }

    public void randomlySelectAllAbilities() {
        this.randomlySelectAbilityInSlot(0);
        this.randomlySelectAbilityInSlot(1);
        this.randomlySelectAbilityInSlot(2);
    }

    public boolean randomlySelectAbilityInSlot(int slot) {
        ArrayList<OwnedAbility> rareOwnedAbilities = new ArrayList<>();

        for ( OwnedAbility ownedAbility : this.ownedAbilities ) {
            if (ownedAbility.getAbility().getRarity() == 3 && ownedAbility.isAvailable()) rareOwnedAbilities.add(ownedAbility);
        }

        for ( int i = 0; i < this.loadedAbilities.size(); i++ ) {
            LoadedAbility loadedAbility = this.loadedAbilities.get(i);
            if ( rareOwnedAbilities.contains(loadedAbility.getOwnedAbility()) ) {
                rareOwnedAbilities.remove(loadedAbility.getOwnedAbility());
            }
        }

        if ( rareOwnedAbilities.size() <= 0 ) {
            return false;
        }

        Collections.shuffle(rareOwnedAbilities);

        int effectiveOwnedLevel = Main.getConfigurator().config.getBoolean(ALL_ABILITIES_MODE) ? 3 : rareOwnedAbilities.get(0).ownedLevel;

        int level = Math.min( effectiveOwnedLevel, slot + 1 );

        this.loadedAbilities.set(slot, new LoadedAbility(rareOwnedAbilities.get(0), level));
        return true;

    }


    public void updateShop() {
        this.setCustomGUIShopInstance(new ShopInstance( this.player, game.shop ));
    }

}
