package org.screamingsandals.bedwars.utils.flowergun.abilities_base;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.api.game.GameStatus;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.game.TeamColor;
import org.screamingsandals.bedwars.utils.external.MiscUtils;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;
import org.screamingsandals.bedwars.utils.flowergun.shoputils.PurchasableItem;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CustomBlock;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.mechanics.ImpactInstance;
import org.screamingsandals.bedwars.utils.flowergun.managers.IconsManager;

import java.util.ArrayList;
import java.util.Random;

import static org.screamingsandals.bedwars.lib.lang.I.i18n;

public class Triggers {

    public static void gamePostLaunch(Game game, Player player)
    {
        GamePlayer gPlayer = Main.getPlayerGameProfile(player);
    }

    public static void playerDeath(PlayerDeathEvent event) {


        Player victim = event.getEntity();
        GamePlayer gameVictim = Main.getPlayerGameProfile(victim);
        ArrayList<LoadedAbility> loadedAbilities = gameVictim.loadedAbilities;
        for ( int i = 0; i < loadedAbilities.size(); i++ ) {
            if (loadedAbilities.get(i).isEmpty()) continue;
            loadedAbilities.get(i).getOwnedAbility().getAbility().playerDeath( loadedAbilities.get(i).activeLevel , event );
        }

        Game game = gameVictim.getGame();
        TeamColor victimTeam = game.getPlayerTeam(gameVictim).teamInfo.color;
        if (victimTeam == null) victimTeam = TeamColor.DEFAULT;

        Player eventKiller = event.getEntity().getKiller();

        GamePlayer gameKiller = gameVictim.getImpactLogKiller();
        ArrayList<GamePlayer> assisters = gameVictim.getImpactLogAssisters();
        TeamColor killerTeam = null;
        Player killer = null;
        gameVictim.flushImpactLog();

        if (gameKiller != null) {
            killer = gameKiller.player;
            if (game.getPlayerTeam(gameKiller) != null)
            killerTeam = game.getPlayerTeam(gameKiller).teamInfo.color;

            loadedAbilities = gameKiller.loadedAbilities;
            for ( int i = 0; i < loadedAbilities.size(); i++ ) {
                if (loadedAbilities.get(i).isEmpty()) continue;
                loadedAbilities.get(i).getOwnedAbility().getAbility().playerKill( loadedAbilities.get(i).activeLevel , killer, event );
            }

            if ( !game.getPlayerTeam(gameVictim).isBed ) {
                //WAYPOINT FINAL KILL
                Main.getStatsManager().addResourceToPlayer(killer, ResourceType.BONE, 20);
            }

        }

        if (killerTeam == null) killerTeam = TeamColor.DEFAULT;

        String deathMessage = "  ";
        if (killer != null) {
            // one killed two
            String assistersList = "";

            if (assisters.size() > 0) {
                assistersList += ChatColor.RESET + IconsManager.requestIcon("THUMBS_UP", victim) + IconsManager.requestIcon(IconType.OFFSET_MINUS_3, victim);
                String assistNotification = killerTeam.chatColor + killer.getName() + " " + ChatColor.RESET + IconsManager.requestIcon("THUMBS_UP", victim) + " " + IconsManager.requestIcon("SKULL", victim) + " " + victimTeam.chatColor + victim.getName();
                for ( GamePlayer assister : assisters ) {
                    if (game.getPlayerTeam(assister) == null) continue;
                    assistersList += game.getPlayerTeam(assister).teamInfo.color.assistCell;
                    if (assister.player != null) {
                        assister.player.sendTitle("", assistNotification, 10, 30, 10);
                        assister.player.playSound(assister.player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 0.5F, 0.8F);
                        Triggers.playerKillAssist(killer, victim, assister.player);
                    }
                }
                assistersList += " ";
            }

            killer.getWorld().spawnParticle(Particle.END_ROD, killer.getLocation(), 30, 0, 0, 0, 0.2);
            killer.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, killer.getLocation(), 20, 0, 0, 0, 0.2);

            killer.playSound(killer.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 0.5F, 0.8F);
            killer.sendTitle("", ChatColor.RESET + IconsManager.requestIcon("SKULL", victim) + " " + victimTeam.chatColor + victim.getName(), 10, 30, 10);

            deathMessage = killerTeam.chatColor + killer.getName() + " " + ChatColor.GRAY + assistersList + "" + ChatColor.RESET + IconsManager.requestIcon("SKULL", victim) + " " + victimTeam.chatColor + victim.getName();
        } else {
            // one died
            deathMessage = victimTeam.chatColor + victim.getName() + " " + ChatColor.RESET + IconsManager.requestIcon("SKULL", victim) + " " + victimTeam.chatColor + victim.getName();
        }

        Firework firework = (Firework) victim.getWorld().spawnEntity(victim.getLocation().clone().add(0, 1.2, 0), EntityType.FIREWORK);
        FireworkMeta feuermeta = firework.getFireworkMeta();
        feuermeta.setPower(1);
        feuermeta.addEffect(FireworkEffect.builder().withColor(victimTeam.leatherColor).build());
        feuermeta.addAttributeModifier(Attribute.GENERIC_LUCK, new AttributeModifier("generic.luck", 1, AttributeModifier.Operation.ADD_NUMBER));
        firework.setFireworkMeta(feuermeta);
        firework.detonate();

        victim.getWorld().playSound(victim.getLocation().clone().add(0, 1.2, 0), Sound.ENTITY_ENDER_EYE_DEATH, 1.0F, 0.5F);

        for ( Player player : game.getConnectedPlayers()) {
            player.sendMessage(deathMessage);
        }

    }

    public static void playerKillAssist(Player killer, Player victim, Player assistant) {

        Main.getStatsManager().addResourceToPlayer(assistant, ResourceType.LAPIS, 3);

        GamePlayer gamePlayer = Main.getPlayerGameProfile(assistant);
        ArrayList<LoadedAbility> loadedAbilities = gamePlayer.loadedAbilities;
        for ( int i = 0; i < loadedAbilities.size(); i++ ) {
            if (loadedAbilities.get(i).isEmpty()) continue;
            loadedAbilities.get(i).getOwnedAbility().getAbility().playerKillAssist( loadedAbilities.get(i).activeLevel , killer, victim, assistant);
        }

    }


    public static void gamePreLaunch(Game game, Player player)
    {
        GamePlayer gPlayer = Main.getPlayerGameProfile(player);
    }

    public static void processDamageEvent(EntityDamageEvent event) {

        if (!(event.getEntity() instanceof LivingEntity victim)) return;

        DamageType damageType = DamageType.PHYSICAL;
        DamageRelay damageRelay = DamageRelay.MELEE;
        DamageSource damageSource = DamageSource.ENVIRONMENT;
        DamageTarget damageTarget = DamageTarget.MOB;

        if ( victim instanceof Player ) damageTarget = DamageTarget.PLAYER;

        CompoundValueModifier compoundValueModifier = new CompoundValueModifier();

        DamageInstance damageInstance = new DamageInstance(damageSource, damageTarget, damageRelay, damageType);;
        boolean isCustom = false;

        Entity mobAttacker = null;
        if (event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent eventTemp = (EntityDamageByEntityEvent) event;
            mobAttacker = eventTemp.getDamager();
            if (mobAttacker instanceof Projectile) {
                Projectile projectile = (Projectile) mobAttacker;
                mobAttacker = (Entity) projectile.getShooter();
                damageRelay = DamageRelay.PROJECTILE;

                switch (event.getCause()) {
                    case ENTITY_EXPLOSION -> {
//                        Bukkit.getConsoleSender().sendMessage("event damage cause is entity_explosion");
                        damageType = DamageType.FIREWORK;
                    }
                }


            }

            if (mobAttacker instanceof Player) {
                Player attacker = (Player) mobAttacker;
                GamePlayer gAttacker = Main.getPlayerGameProfile(attacker);

                damageSource = DamageSource.PLAYER;

                if ( gAttacker.lastDealtDamageInstance != null ) {
                    isCustom = true;
                    damageInstance = gAttacker.lastDealtDamageInstance;
                    gAttacker.lastDealtDamageInstance = null;
                }
            }
            else {
                damageSource = DamageSource.MOB;
            }

        } else {
            switch (event.getCause()) {
                case FALL -> {
                    damageRelay = DamageRelay.CONTACT;
                }
                case ENTITY_EXPLOSION -> {
                    damageType = DamageType.FIREWORK;
                    damageRelay = DamageRelay.PROJECTILE;
                }
                case FIRE -> {
                    damageRelay = DamageRelay.CONTACT;
                    damageType = DamageType.FIRE;
                    break;
                }
                case FIRE_TICK -> {
                    damageRelay = DamageRelay.STATUS;
                    damageType = DamageType.FIRE;
                }
                case POISON -> {
                    damageRelay = DamageRelay.STATUS;
                    damageType = DamageType.POISON;
                }
                case WITHER -> {
                    damageRelay = DamageRelay.STATUS;
                    damageType = DamageType.WITHER;
                }
                case STARVATION -> {
                    damageRelay = DamageRelay.STATUS;
                    damageType = DamageType.STARVATION;
                }
                case VOID -> {
                    damageRelay = DamageRelay.CONTACT;
                    damageType = DamageType.VOID;
                }
                case BLOCK_EXPLOSION -> {
                    damageRelay = DamageRelay.CONTACT;
                    damageType = DamageType.EXPLOSION;
                }
            }
        }

        if (!isCustom)
        damageInstance = new DamageInstance(damageSource, damageTarget, damageRelay, damageType);

        GamePlayer gAttacker = null;
        if (damageInstance.damageSource == DamageSource.PLAYER) {

            Player attacker = (Player) mobAttacker;

            gAttacker = Main.getPlayerGameProfile(attacker);
            ArrayList<LoadedAbility> loadedAbilities = gAttacker.loadedAbilities;

            if (gAttacker.getGame() == null || gAttacker.getGame().getStatus() == GameStatus.WAITING) return;



            for ( int i = 0; i < loadedAbilities.size(); i++ ) {
                if (loadedAbilities.get(i).isEmpty()) continue;
                loadedAbilities.get(i).getOwnedAbility().getAbility().playerDealDamage( loadedAbilities.get(i).activeLevel, damageInstance, attacker, (EntityDamageByEntityEvent) event, compoundValueModifier);
            }
        }

        GamePlayer gVictim = null;
        if (damageInstance.damageTarget == DamageTarget.PLAYER) {

            gVictim = Main.getPlayerGameProfile((Player) victim);
            ArrayList<LoadedAbility> loadedAbilities = gVictim.loadedAbilities;

            if (gVictim.getGame() == null || gVictim.getGame().getStatus() == GameStatus.WAITING) return;

            for ( int i = 0; i < loadedAbilities.size(); i++ ) {
                if (loadedAbilities.get(i).isEmpty()) continue;
                loadedAbilities.get(i).getOwnedAbility().getAbility().playerReceiveDamage( loadedAbilities.get(i).activeLevel, damageInstance, (Player) event.getEntity(), event, compoundValueModifier);
            }
            gVictim.lastReceivedDamageInstance = damageInstance;
        }

        event.setDamage(compoundValueModifier.processValueEffectiveDecrease(event.getDamage()));

        if ( damageInstance.damageTarget == DamageTarget.PLAYER && damageInstance.damageSource == DamageSource.PLAYER && event.getFinalDamage() != 0 && !event.isCancelled() ) {
//            Bukkit.getConsoleSender().sendMessage("added log impact");
            gVictim.logImpactInstance(new ImpactInstance(gAttacker, ImpactType.DAMAGE, ImpactPolarity.ENEMY, gAttacker.getGame().countdown, 1));

            Main.getStatsManager().addResourceToPlayer(gVictim, ResourceType.BONE, 5);
            Main.getStatsManager().addResourceToPlayer(gAttacker, ResourceType.LEATHER, 5);

            if (victim.isVisualFire()) {
                Bukkit.getConsoleSender().sendMessage("KILLED A PERSON OF FIRE: " + gAttacker.player.getDisplayName() + " -> " + gVictim.player.getDisplayName());
                Main.getStatsManager().addResourceToPlayer(gAttacker, ResourceType.BLAZE_POWDER, 3);
                Main.getStatsManager().addResourceToPlayer(gVictim, ResourceType.COAL, 10);
            }

            if (gVictim.lastReceivedDamageInstance.damageType == DamageType.SNOWBALL) {
                Bukkit.getConsoleSender().sendMessage("KILLED A PERSON WITH ICE: " + gAttacker.player.getDisplayName() + " -> " + gVictim.player.getDisplayName());
                Main.getStatsManager().addResourceToPlayer(gAttacker, ResourceType.ICE_POWDER, 10);
                Main.getStatsManager().addResourceToPlayer(gVictim, ResourceType.ICE_POWDER, 3);
            }

        }

    }

    public static void shopPurchase(Game game, Player player, PurchasableItem item, int amount)
    {



        GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
        ArrayList<LoadedAbility> loadedAbilities = gamePlayer.loadedAbilities;
        for ( int i = 0; i < loadedAbilities.size(); i++ ) {
            if (loadedAbilities.get(i).isEmpty()) continue;
            loadedAbilities.get(i).getOwnedAbility().getAbility().shopPurchase( loadedAbilities.get(i).activeLevel ,game, player, item, amount);
        }

        if ( item.getItemCategory() == ItemCategory.DISABLED_LOOT ) return;

        double shoppingValue = 0;

//        Bukkit.getConsoleSender().sendMessage("resource name = " + item.priceType1.getName());
//        Bukkit.getConsoleSender().sendMessage("config key = " + item.priceType1.getConfigKey());

        switch (item.priceType1.getConfigKey()) {
            case "bronze" -> { Main.getStatsManager().addResourceToPlayer(player, ResourceType.RAW_COPPER, item.priceAmount1); shoppingValue += item.priceAmount1 * FlowerUtils.copperShoppingValue;}
            case "iron" -> {Main.getStatsManager().addResourceToPlayer(player, ResourceType.RAW_IRON, item.priceAmount1); shoppingValue += item.priceAmount1 * FlowerUtils.ironShoppingValue;}
            case "gold" -> {Main.getStatsManager().addResourceToPlayer(player, ResourceType.RAW_GOLD, item.priceAmount1); shoppingValue += item.priceAmount1 * FlowerUtils.goldShoppingValue;}
            case "emerald" -> {Main.getStatsManager().addResourceToPlayer(player, ResourceType.EMERALD, item.priceAmount1); shoppingValue += item.priceAmount1 * FlowerUtils.emeraldShoppingValue;}
        }

        if (item.priceType2 != null) {
            switch (item.priceType2.getConfigKey()) {
                case "bronze" -> {Main.getStatsManager().addResourceToPlayer(player, ResourceType.RAW_COPPER, item.priceAmount2); shoppingValue += item.priceAmount2 * FlowerUtils.copperShoppingValue;}
                case "iron" -> {Main.getStatsManager().addResourceToPlayer(player, ResourceType.RAW_IRON, item.priceAmount2); shoppingValue += item.priceAmount2 * FlowerUtils.ironShoppingValue;}
                case "gold" -> {Main.getStatsManager().addResourceToPlayer(player, ResourceType.RAW_GOLD, item.priceAmount2); shoppingValue += item.priceAmount2 * FlowerUtils.goldShoppingValue;}
                case "emerald" -> {Main.getStatsManager().addResourceToPlayer(player, ResourceType.EMERALD, item.priceAmount2); shoppingValue += item.priceAmount2 * FlowerUtils.emeraldShoppingValue;}
            }
        }

        shoppingValue *= amount;

        boolean willRoundUp = false;
        Random random = new Random();
        if ( random.nextDouble() < shoppingValue % 1 ) willRoundUp = true;


        int finalShoppingValue = willRoundUp ? (int) Math.floor(shoppingValue) + 1 : (int) Math.floor(shoppingValue);

        if ( finalShoppingValue == 0 ) return;

        Main.getStatsManager().addResourceToPlayer(player, ResourceType.SCRAP, finalShoppingValue);

        if ( item.getItemCategory() == ItemCategory.POTION ) {
            Main.getStatsManager().addResourceToPlayer(player, ResourceType.AMETHYST_SHARD, finalShoppingValue);
            Main.getStatsManager().addResourceToPlayer(player, ResourceType.GLOW_INK_SAC, finalShoppingValue);
        }
        else if ( item.getItemCategory() == ItemCategory.BLAZING ) {
            Main.getStatsManager().addResourceToPlayer(player, ResourceType.BLAZE_POWDER, finalShoppingValue);
        }
        else if ( item.getItemCategory() == ItemCategory.BOW || item.getItemCategory() == ItemCategory.CROSSBOW ) {
            Main.getStatsManager().addResourceToPlayer(player, ResourceType.SILK_COCOON, finalShoppingValue);
        }
        else if ( item.getItemCategory() == ItemCategory.WOOLY) {
            Main.getStatsManager().addResourceToPlayer(player, ResourceType.SILK_COCOON, finalShoppingValue);
        }
        else if ( item.getItemCategory() == ItemCategory.WOODY || item.getItemCategory() == ItemCategory.BURNING ) {
            Main.getStatsManager().addResourceToPlayer(player, ResourceType.COAL, finalShoppingValue);
        }
        else if ( item.getItemCategory() == ItemCategory.ICY || item.getItemCategory() == ItemCategory.SNOWBALL ) {
            Main.getStatsManager().addResourceToPlayer(player, ResourceType.ICE_POWDER, finalShoppingValue);
        }
        else if ( item.getItemCategory() == ItemCategory.NETHERITE ) {
            Main.getStatsManager().addResourceToPlayer(player, ResourceType.NETHERITE_SCRAP, finalShoppingValue);
        }
        else if ( item.getItemCategory() == ItemCategory.LEATHERY ) {
            Main.getStatsManager().addResourceToPlayer(player, ResourceType.LEATHER, finalShoppingValue);
        }
        else if ( item.getItemCategory() == ItemCategory.FIREWORK ) {
            Main.getStatsManager().addResourceToPlayer(player, ResourceType.PAPER, finalShoppingValue);
            Main.getStatsManager().addResourceToPlayer(player, ResourceType.BLAZE_POWDER, finalShoppingValue);
        }
        else if ( item.getItemCategory() == ItemCategory.TOOLY ) {
            Main.getStatsManager().addResourceToPlayer(player, ResourceType.QUARTZ, finalShoppingValue);
        }
        else if ( item.getItemCategory() == ItemCategory.GLASSY ) {
            Main.getStatsManager().addResourceToPlayer(player, ResourceType.AMETHYST_SHARD, finalShoppingValue);
        }
        else if ( item.getItemCategory() == ItemCategory.SLIMY ) {
            Main.getStatsManager().addResourceToPlayer(player, ResourceType.SLIMEBALL, finalShoppingValue);
        }
        else if ( item.getItemCategory() == ItemCategory.COLOURFUL ) {
            Main.getStatsManager().addResourceToPlayer(player, ResourceType.GLOW_INK_SAC, finalShoppingValue);
        }


    }

    public static CompoundValueModifier healPlayer(Player healer, Player target)
    {
        GamePlayer gamePlayerHealer = Main.getPlayerGameProfile(healer);
        GamePlayer gamePlayerTarget = Main.getPlayerGameProfile(target);
        ArrayList<LoadedAbility> loadedAbilities = gamePlayerHealer.loadedAbilities;

        CompoundValueModifier compoundValueModifier = new CompoundValueModifier();

        for ( int i = 0; i < loadedAbilities.size(); i++ ) {
            if (loadedAbilities.get(i).isEmpty()) continue;
            loadedAbilities.get(i).getOwnedAbility().getAbility().healPlayer( loadedAbilities.get(i).activeLevel , healer, target, compoundValueModifier);
        }

        loadedAbilities = gamePlayerTarget.loadedAbilities;

        for ( int i = 0; i < loadedAbilities.size(); i++ ) {
            if (loadedAbilities.get(i).isEmpty()) continue;
            loadedAbilities.get(i).getOwnedAbility().getAbility().healedByPlayer( loadedAbilities.get(i).activeLevel , target, healer, compoundValueModifier);
        }

        return compoundValueModifier;
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

        ArrayList<ItemStack> modifiedDestroyedItems = new ArrayList<>(FlowerUtils.destroyedItems);
        ArrayList<Material> modifiedNonBreakingItems = new ArrayList<>();

        if (gamePlayer.hasFlag(GameFlag.INTELLECT_LEVEL_1)) modifiedDestroyedItems.remove(Main.getSpawnerType("bronze").getStack());
        if (gamePlayer.hasFlag(GameFlag.INTELLECT_LEVEL_2)) modifiedDestroyedItems.remove(Main.getSpawnerType("iron").getStack());

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
            if ( modifiedDestroyedItems.contains(clone) ) {
                itemStack.setAmount(0);
                continue;
            }
            if ( FlowerUtils.destroyedResources.contains(clone.getType()) ) {
                itemStack.setAmount(0);
                continue;
            }
            if ( itemStack.getType().getMaxDurability() > 1 ) {

                if (modifiedNonBreakingItems.contains(itemStack.getType())) continue;

                itemMeta = itemStack.getItemMeta();

                if (itemMeta.isUnbreakable()) continue;

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
            if (loadedAbilities.get(i).isEmpty()) continue;
            loadedAbilities.get(i).getOwnedAbility().getAbility().playerRespawn( loadedAbilities.get(i).activeLevel, gamePlayer);
        }

    }

    public static void playerFirstSpawn(GamePlayer gamePlayer) {
        ArrayList<LoadedAbility> loadedAbilities = gamePlayer.loadedAbilities;

        gamePlayer.player.setStatistic(Statistic.TIME_SINCE_REST, 72000);
        
        for ( int i = 0; i < loadedAbilities.size(); i++ ) {
            if (loadedAbilities.get(i).isEmpty()) continue;
            loadedAbilities.get(i).getOwnedAbility().getAbility().playerFirstSpawn( loadedAbilities.get(i).activeLevel, gamePlayer);
        }
    }


    public static void processPurchasibleItem(GamePlayer gamePlayer, PurchasableItem item) {
        ArrayList<LoadedAbility> loadedAbilities = gamePlayer.loadedAbilities;
        for ( int i = 0; i < loadedAbilities.size(); i++ ) {
            if (loadedAbilities.get(i).isEmpty()) continue;
            loadedAbilities.get(i).getOwnedAbility().getAbility().processPurchasibleItem( loadedAbilities.get(i).activeLevel, gamePlayer, item);
        }
    }

    public static void gadgetUsed(Player player, GadgetType gadgetType, CustomBlock customBlock) {

        GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
        Game game = gamePlayer.getGame();
        ArrayList<LoadedAbility> loadedAbilities = gamePlayer.loadedAbilities;
        CompoundValueModifier compoundValueModifier = new CompoundValueModifier();

        for ( int i = 0; i < loadedAbilities.size(); i++ ) {
            if (loadedAbilities.get(i).isEmpty()) continue;
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
                player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_PHANTOM_FLAP, 1F, 2F);
                player.getWorld().spawnParticle(Particle.SLIME, player.getLocation().clone().add(0, 1.2, 0), 10,0.2, 0.2, 0.2, 0.1);


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
            if (loadedAbilities.get(i).isEmpty()) continue;
            loadedAbilities.get(i).getOwnedAbility().getAbility().pickupItem( loadedAbilities.get(i).activeLevel, player, event);
        }
    }

    public static void blockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
        ArrayList<LoadedAbility> loadedAbilities = gamePlayer.loadedAbilities;
        for ( int i = 0; i < loadedAbilities.size(); i++ ) {
            if (loadedAbilities.get(i).isEmpty()) continue;
            loadedAbilities.get(i).getOwnedAbility().getAbility().blockBreak( loadedAbilities.get(i).activeLevel, event);
        }
    }

    public static void blockPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
        ArrayList<LoadedAbility> loadedAbilities = gamePlayer.loadedAbilities;
        for ( int i = 0; i < loadedAbilities.size(); i++ ) {
            if (loadedAbilities.get(i).isEmpty()) continue;
            loadedAbilities.get(i).getOwnedAbility().getAbility().blockPlace( loadedAbilities.get(i).activeLevel, event);
        }
    }

    public static void playerItemConsume(Player player, PlayerItemConsumeEvent event) {
        if (event.isCancelled()) return;
        GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
        ArrayList<LoadedAbility> loadedAbilities = gamePlayer.loadedAbilities;
        for ( int i = 0; i < loadedAbilities.size(); i++ ) {
            if (loadedAbilities.get(i).isEmpty()) continue;
            loadedAbilities.get(i).getOwnedAbility().getAbility().itemConsume( loadedAbilities.get(i).activeLevel, player, event);
        }
    }
}
