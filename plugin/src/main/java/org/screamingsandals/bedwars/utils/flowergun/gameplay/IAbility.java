package org.screamingsandals.bedwars.utils.flowergun.gameplay;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.customgui.shoputils.PurchasableItem;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.GadgetType;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.enums.DamageInstance;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.enums.DamageSource;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.enums.DamageTarget;

import java.util.ArrayList;

public interface IAbility {

    Material getIcon();

    String getName();

    String getId();


    int getRarity();

    void playerDeath(int level, PlayerDeathEvent event);

    void shopPurchase(int level, Game game, Player player, PurchasableItem item, int amount);


    void playerKill(int level, PlayerDeathEvent event);

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

    String formatValues2(int activeLevel, int slot);

    String formatValues3(int activeLevel, int slot);

    ArrayList<String> parseDescription(int ownedLevel, int activeLevel, int slot);

    void playerDealDamage(int activeLevel, DamageInstance damageInstance, Player attacker, EntityDamageByEntityEvent event, CompoundValueModifier compoundValueModifier);

    void playerReceiveDamage(int activeLevel, DamageInstance damageInstance, Player victim, EntityDamageEvent event, CompoundValueModifier compoundValueModifier);

    void pickupItem(int level, Player player, EntityPickupItemEvent event);

    void gadgetUsed(int activeLevel, GamePlayer gamePlayer, GadgetType gadgetType, CompoundValueModifier compoundValueModifier);

}
