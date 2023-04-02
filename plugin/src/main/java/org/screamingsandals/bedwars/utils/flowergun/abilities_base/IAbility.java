package org.screamingsandals.bedwars.utils.flowergun.abilities_base;

import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.shoputils.PurchasableItem;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.GadgetType;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.DamageInstance;

import java.util.ArrayList;

public interface IAbility {

    ResourceBundle getDisassembleResources();

    ResourceBundle getUpgradeResources(int i);

    String getRawName();

    Material getAbilityMaterial();

    ItemStack getAbilityGuiItem();

    int getMaxLevel();

    int getNextLevelCost(int ownedLevel);

    String getName();

    String getId();


    String getIconString(Player player);

    String getNameWithIcon(Player player);

    int getRarity();

    void playerDeath(int level, Player victim, Player killer, PlayerDeathEvent event);

    void shopPurchase(int level, Game game, Player player, PurchasableItem item, int amount);


    void playerKill(int level, Player victim, Player killer, PlayerDeathEvent event);

    void playerRespawn(int level, GamePlayer gamePlayer);


    void playerFirstSpawn(int level, GamePlayer gamePlayer);

    void processPurchasibleItem(int activeLevel, GamePlayer gamePlayer, PurchasableItem item);

    int calculateIntValue1(int level);

    int calculateIntValue2(int level);

    int calculateIntValue3(int level);

    double calculateDoubleValue1(int level);

    String formatValue1(int level);

    String formatValue2(int level);

    String formatValue3(int level);

    String formatValues1(int activeLevel, int slot);

    TextComponent formatValues1intoComponent(int activeLevel, int slot);

    TextComponent formatValues2intoComponent(int activeLevel, int slot);

    TextComponent formatValues3intoComponent(int activeLevel, int slot);

    String formatValues2(int activeLevel, int slot);

    String formatValues3(int activeLevel, int slot);

    ArrayList<String> parseDescription(int ownedLevel, int activeLevel, int slot);

    void playerDealDamage(int activeLevel, DamageInstance damageInstance, Player attacker, EntityDamageByEntityEvent event, CompoundValueModifier compoundValueModifier);

    void playerReceiveDamage(int activeLevel, DamageInstance damageInstance, Player victim, EntityDamageEvent event, CompoundValueModifier compoundValueModifier);

    void pickupItem(int level, Player player, EntityPickupItemEvent event);

    void blockPlace(int activeLevel, BlockPlaceEvent event);

    void gadgetUsed(int activeLevel, GamePlayer gamePlayer, GadgetType gadgetType, CompoundValueModifier compoundValueModifier);

    void blockBreak(int activeLevel, BlockBreakEvent event);

    void healPlayer(int activeLevel, Player healer, Player target, CompoundValueModifier compoundValueModifier);

    void healedByPlayer(int activeLevel, Player target, Player healer, CompoundValueModifier compoundValueModifier);

    void playerKillAssist(int activeLevel, Player killer, Player victim, Player assistant);

    ItemStack getGuiItem();

    void itemConsume(int activeLevel, Player player, PlayerItemConsumeEvent event);

    TextComponent parseDescriptionComponent(Player player,int effectiveOwnedLevel, int min, int slot);

    void projectileHit(int level, Player player, ProjectileHitEvent event);

    void projectileLaunch(int level, Player player, ProjectileLaunchEvent event);
}
