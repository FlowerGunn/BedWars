package org.screamingsandals.bedwars.utils.flowergun.gameplay;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.MiscUtils;
import org.screamingsandals.bedwars.utils.flowergun.customgui.shoputils.GameFlag;
import org.screamingsandals.bedwars.utils.flowergun.customgui.shoputils.PurchasableItem;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CustomBlock;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.GadgetType;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.enums.*;

import java.util.ArrayList;

import static org.screamingsandals.bedwars.lib.lang.I.i18n;

public class Triggers {

    public static void gamePostLaunch(Game game, Player player)
    {
        GamePlayer gPlayer = Main.getPlayerGameProfile(player);
    }

    public static void playerDeath(PlayerDeathEvent event) {

        GamePlayer gameVictim = Main.getPlayerGameProfile(event.getEntity());
        ArrayList<LoadedAbility> loadedAbilities = gameVictim.loadedAbilities;
        for ( int i = 0; i < loadedAbilities.size(); i++ ) {
            if (loadedAbilities.get(i) == null) continue;
            loadedAbilities.get(i).getOwnedAbility().getAbility().playerDeath( loadedAbilities.get(i).activeLevel , event );
        }

        if (event.getEntity().getKiller() != null) {
            GamePlayer gameKiller = Main.getPlayerGameProfile(event.getEntity());
            loadedAbilities = gameKiller.loadedAbilities;
            for ( int i = 0; i < loadedAbilities.size(); i++ ) {
                if (loadedAbilities.get(i) == null) continue;
                loadedAbilities.get(i).getOwnedAbility().getAbility().playerDeath( loadedAbilities.get(i).activeLevel , event );
            }
        }


    }

    public static void gamePreLaunch(Game game, Player player)
    {
        GamePlayer gPlayer = Main.getPlayerGameProfile(player);
    }

    public static void playerReceiveDamage(EntityDamageEvent event)
    {
//        GamePlayer gAttacker = Main.getPlayerGameProfile(attacker);
        Player victim = (Player) event.getEntity();
        GamePlayer gVictim = Main.getPlayerGameProfile(victim);
        ArrayList<LoadedAbility> loadedAbilities = gVictim.loadedAbilities;

        DamageType damageType = DamageType.PHYSICAL;
        DamageRelay damageRelay = DamageRelay.MELEE;
        DamageSource damageSource = DamageSource.ENVIRONMENT;
        DamageTarget damageTarget = DamageTarget.PLAYER;
        CompoundValueModifier compoundValueModifier = new CompoundValueModifier();

         if (event instanceof EntityDamageByEntityEvent) {
             EntityDamageByEntityEvent eventTemp = (EntityDamageByEntityEvent) event;
             Entity mobAttacker = eventTemp.getDamager();
             if (mobAttacker instanceof Projectile) {
                 Projectile projectile = (Projectile) mobAttacker;
                 mobAttacker = (Entity) projectile.getShooter();
                 damageRelay = DamageRelay.PROJECTILE;
//
//                 switch (projectile.getType()) {
//                     case FIREWORK -> {
//
//                     }
//                     case ARROW -> {
//
//                     }
//                     case SPECTRAL_ARROW -> {
//
//                     }
//                     case
//                 }
             }

             if (mobAttacker instanceof Player) {
                 Player attacker = (Player) mobAttacker;
                 GamePlayer gAttacker = Main.getPlayerGameProfile(attacker);

                 damageSource = DamageSource.PLAYER;

                 damageType = gAttacker.damageTypeDefenceInstance;
                 gAttacker.damageTypeDefenceInstance = DamageType.PHYSICAL;
                 if ( damageType == DamageType.SNOWBALL ) {
                     damageRelay = gAttacker.damageRelayDefenceInstance;
                 }
                 gAttacker.damageRelayDefenceInstance = DamageRelay.MELEE;
             }
             else {
                 damageSource = DamageSource.MOB;
             }
         } else {
             switch (event.getCause()) {
                 case FALL -> {
                     damageRelay = DamageRelay.CONTACT;
                     damageType = DamageType.PHYSICAL;
                     break;
                 }
                 case FIRE -> {
                     damageRelay = DamageRelay.STATUS;
                     damageType = DamageType.FIRE;
                     break;
                 }
                 case POISON -> {
                     damageRelay = DamageRelay.STATUS;
                     damageType = DamageType.POISON;
                     break;
                 }
                 case WITHER -> {
                     damageRelay = DamageRelay.STATUS;
                     damageType = DamageType.WITHER;
                     break;
                 }
                 case STARVATION -> {
                     damageRelay = DamageRelay.STATUS;
                     damageType = DamageType.STARVATION;
                     break;
                 }
                 case VOID -> {
                     damageRelay = DamageRelay.CONTACT;
                     damageType = DamageType.VOID;
                     break;
                 }
                 case BLOCK_EXPLOSION -> {
                     damageRelay = DamageRelay.CONTACT;
                     damageType = DamageType.EXPLOSION;
                     break;
                 }
             }
         }



        DamageInstance damageInstance = new DamageInstance(damageSource, damageTarget, damageRelay, damageType);

        if (damageInstance.damageSource == DamageSource.PLAYER) {
            Main.getPlayerGameProfile((Player) event.getEntity()).lastReceivedDamageInstance = damageInstance;
        }

        for ( int i = 0; i < loadedAbilities.size(); i++ ) {
            if (loadedAbilities.get(i) == null) continue;
            loadedAbilities.get(i).getOwnedAbility().getAbility().playerReceiveDamage( loadedAbilities.get(i).activeLevel, damageInstance, victim, event, compoundValueModifier);
        }

        event.setDamage(compoundValueModifier.processValueEffectiveDecrease(event.getDamage()));
    }

    public static void playerDealDamage(DamageInstance damageInstance, Player attacker, EntityDamageByEntityEvent event)
    {
        GamePlayer gAttacker = Main.getPlayerGameProfile(attacker);
        ArrayList<LoadedAbility> loadedAbilities = gAttacker.loadedAbilities;

        if ( gAttacker.damageTypeAttackInstance == DamageType.SNOWBALL ) {
            damageInstance.damageType = gAttacker.damageTypeAttackInstance;
            damageInstance.damageRelay = gAttacker.damageRelayAttackInstance;
            gAttacker.damageTypeAttackInstance = DamageType.PHYSICAL;
            gAttacker.damageRelayAttackInstance = DamageRelay.MELEE;
        }
        CompoundValueModifier compoundValueModifier = new CompoundValueModifier();

        for ( int i = 0; i < loadedAbilities.size(); i++ ) {
            if (loadedAbilities.get(i) == null) continue;
            loadedAbilities.get(i).getOwnedAbility().getAbility().playerDealDamage( loadedAbilities.get(i).activeLevel, damageInstance, attacker, event, compoundValueModifier);
        }

//        Bukkit.getConsoleSender().sendMessage("start damage = " + event.getDamage());
//        event.setDamage(compoundValueModifier.processValueEffectiveIncrease(event.getDamage()));
//        Bukkit.getConsoleSender().sendMessage("end damage = " + event.getDamage());
//        Bukkit.getConsoleSender().sendMessage("final damage = " + event.getFinalDamage());

    }

    public static void shopPurchase(Game game, Player player, PurchasableItem item, int amount)
    {
        GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
        ArrayList<LoadedAbility> loadedAbilities = gamePlayer.loadedAbilities;
        for ( int i = 0; i < loadedAbilities.size(); i++ ) {
            if (loadedAbilities.get(i) == null) continue;
            loadedAbilities.get(i).getOwnedAbility().getAbility().shopPurchase( loadedAbilities.get(i).activeLevel ,game, player, item, amount);
        }
    }


    public static void playerRespawn(Game game, GamePlayer gamePlayer)
    {
        Inventory inventory = gamePlayer.player.getInventory();
        ItemMeta itemMeta;
        int survivedFor = gamePlayer.lastDeathCounter - game.countdown;
        double durabilityToRemove;

        gamePlayer.player.setStatistic(Statistic.TIME_SINCE_REST, 72000);

//                            Bukkit.getConsoleSender().sendMessage( survivedFor + "     " + gamePlayer.lastDeathCounter + "     "+ game.countdown );
        gamePlayer.lastDeathCounter = game.countdown;

        if (survivedFor > FlowerUtils.toolsDamageScaleEndSeconds) durabilityToRemove = FlowerUtils.toolsDamageScaleEndValue;
        else if(survivedFor < FlowerUtils.toolsDamageScaleStartSeconds) durabilityToRemove = FlowerUtils.toolsDamageScaleStartValue;
        else {
            durabilityToRemove = FlowerUtils.toolsDamageScaleStartValue + (FlowerUtils.toolsDamageScaleEndValue - FlowerUtils.toolsDamageScaleStartValue) * (double) ( survivedFor - FlowerUtils.toolsDamageScaleStartSeconds ) / ( FlowerUtils.toolsDamageScaleEndSeconds - FlowerUtils.toolsDamageScaleStartSeconds );
        }

        ArrayList<ItemStack> modifiedDestroyedResources = new ArrayList<>(FlowerUtils.destroyedResources);
        ArrayList<Material> modifiedNonBreakingItems = new ArrayList<>();

        if (gamePlayer.hasFlag(GameFlag.INTELLECT_LEVEL_1)) modifiedDestroyedResources.remove(Main.getSpawnerType("bronze").getStack());
        if (gamePlayer.hasFlag(GameFlag.INTELLECT_LEVEL_2)) modifiedDestroyedResources.remove(Main.getSpawnerType("iron").getStack());

        if (gamePlayer.hasFlag(GameFlag.VITALITY_LEVEL_2)) {
            modifiedNonBreakingItems.add(Material.LEATHER_CHESTPLATE);
            modifiedNonBreakingItems.add(Material.LEATHER_LEGGINGS);
            modifiedNonBreakingItems.add(Material.LEATHER_BOOTS);
            modifiedNonBreakingItems.add(Material.LEATHER_HELMET);
        }

        ArrayList<GameFlag> flags = new ArrayList<>(gamePlayer.getAllPlayerFlags());

        for ( ItemStack itemStack : inventory) {
            if ( itemStack == null ) continue;
            ItemStack clone = itemStack.clone();
            clone.setAmount(1);
            if ( modifiedDestroyedResources.contains(clone) ) {
                itemStack.setAmount(0);
                continue;
            }
            if ( itemStack.getType().getMaxDurability() > 1 ) {

                if (modifiedNonBreakingItems.contains(itemStack.getType())) continue;

                itemMeta = itemStack.getItemMeta();
                int unbreakingLevel = itemMeta.getEnchantLevel(Enchantment.DURABILITY);
                int currentDamage = ((Damageable) itemMeta).getDamage();
                int maxDamage = itemStack.getType().getMaxDurability();
//                                    int finalDamage = currentDamage + (int)(( maxDamage - currentDamage) * (durabilityToRemove - FlowerUtils.durabilityCounterReductionPerUnbreakingLevel * unbreakingLevel));
                int finalDamage = currentDamage + (int)(( maxDamage ) * (durabilityToRemove - FlowerUtils.durabilityCounterReductionPerUnbreakingLevel * unbreakingLevel));
                if (finalDamage < 0) ((Damageable)itemMeta).setDamage( 0 );
                else if ( finalDamage > maxDamage ) itemStack.setAmount( 0 );
                else {
                    if ( finalDamage < currentDamage) finalDamage = currentDamage;

                    if ( (itemStack.getType() == Material.BOW || itemStack.getType() == Material.CROSSBOW) && flags.contains(GameFlag.AGILITY_LEVEL_2) ) {
                        if ( (finalDamage - currentDamage) > maxDamage * FlowerUtils.agility4aThreshold)
                            finalDamage = currentDamage + (int) ( maxDamage * FlowerUtils.agility4aThreshold);
                    }

                    ((Damageable)itemMeta).setDamage( finalDamage );
                }
                itemStack.setItemMeta(itemMeta);
            }
        }

        gamePlayer.player.sendTitle(i18n("lost_durability", "You lost: ", false).replace("%durability%", "" + ((int) Math.floor(durabilityToRemove * 100))), i18n("lost_durability_subtext", "-//-", false), 5, 60, 5);

        if ( flags.contains(GameFlag.VITALITY_LEVEL_4A) ) {
            gamePlayer.player.addPotionEffect( new PotionEffect(PotionEffectType.ABSORPTION, 2400, 0));
        } else if ( flags.contains(GameFlag.VITALITY_LEVEL_4B) ) {
            gamePlayer.player.addPotionEffect( new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1200, 0));
        }

        ArrayList<LoadedAbility> loadedAbilities = gamePlayer.loadedAbilities;
        for ( int i = 0; i < loadedAbilities.size(); i++ ) {
            if (loadedAbilities.get(i) == null) continue;
            loadedAbilities.get(i).getOwnedAbility().getAbility().playerRespawn( loadedAbilities.get(i).activeLevel, gamePlayer);
        }

    }

    public static void playerFirstSpawn(GamePlayer gamePlayer) {
        ArrayList<LoadedAbility> loadedAbilities = gamePlayer.loadedAbilities;

        gamePlayer.player.setStatistic(Statistic.TIME_SINCE_REST, 72000);
        
        for ( int i = 0; i < loadedAbilities.size(); i++ ) {
            if (loadedAbilities.get(i) == null) continue;
            loadedAbilities.get(i).getOwnedAbility().getAbility().playerFirstSpawn( loadedAbilities.get(i).activeLevel, gamePlayer);
        }
    }


    public static void processPurchasibleItem(GamePlayer gamePlayer, PurchasableItem item) {
        ArrayList<LoadedAbility> loadedAbilities = gamePlayer.loadedAbilities;
        for ( int i = 0; i < loadedAbilities.size(); i++ ) {
            if (loadedAbilities.get(i) == null) continue;
            loadedAbilities.get(i).getOwnedAbility().getAbility().processPurchasibleItem( loadedAbilities.get(i).activeLevel, gamePlayer, item);
        }
    }

    public static void gadgetUsed(Player player, GadgetType gadgetType, CustomBlock customBlock) {

        GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
        Game game = gamePlayer.getGame();
        ArrayList<LoadedAbility> loadedAbilities = gamePlayer.loadedAbilities;
        CompoundValueModifier compoundValueModifier = new CompoundValueModifier();

        for ( int i = 0; i < loadedAbilities.size(); i++ ) {
            if (loadedAbilities.get(i) == null) continue;
            loadedAbilities.get(i).getOwnedAbility().getAbility().gadgetUsed( loadedAbilities.get(i).activeLevel, gamePlayer, gadgetType, compoundValueModifier );
        }

        switch (gadgetType) {
            case SNOWBALL -> {
                player.setCooldown(Material.SNOWBALL, compoundValueModifier.processValueEffectiveDecrease(FlowerUtils.snowballColldown));
                break;
            }
            case TRAMPOLINE -> {
                double radius = 3.5;
                double power = 0.2;
                int max = 15;

                int counter = game.countAdjacentCustomBlocks(customBlock, radius);

                if (counter > max) counter = max;
                MiscUtils.sendActionBarMessage(player, i18n("trampoline_success", "Weeee!!!! power:" + counter, false).replace("%power%", counter + ""));
//                event.setCancelled(true);
                player.setVelocity(new Vector(0, power * counter, 0));
//                player.setSneaking(false);
                break;
            }
        }
    }

    public static void gadgetUsed(Player player, GadgetType gadgetType) {
        Triggers.gadgetUsed(player, gadgetType, null);
    }

    public static void pickupItem(Player player, EntityPickupItemEvent event) {
        GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
        ArrayList<LoadedAbility> loadedAbilities = gamePlayer.loadedAbilities;
        for ( int i = 0; i < loadedAbilities.size(); i++ ) {
            if (loadedAbilities.get(i) == null) continue;
            loadedAbilities.get(i).getOwnedAbility().getAbility().pickupItem( loadedAbilities.get(i).activeLevel, player, event);
        }
    }
}
