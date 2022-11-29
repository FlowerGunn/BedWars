package org.screamingsandals.bedwars.utils.flowergun.gameplay;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.external.MiscUtils;
import org.screamingsandals.bedwars.utils.flowergun.customgui.shoputils.PurchasableItem;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.GadgetType;
import org.screamingsandals.bedwars.utils.flowergun.tools.IconType;
import org.screamingsandals.bedwars.utils.flowergun.tools.enums.AbilityTriggerType;
import org.screamingsandals.bedwars.utils.flowergun.tools.enums.DamageInstance;
import org.screamingsandals.bedwars.utils.flowergun.tools.enums.ResourceType;
import org.screamingsandals.bedwars.utils.flowergun.mechanics.ImpactInstance;
import org.screamingsandals.bedwars.utils.flowergun.mechanics.ImpactPolarity;
import org.screamingsandals.bedwars.utils.flowergun.mechanics.ImpactType;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Ability implements IAbility{

    protected boolean isOnCooldown;
    int maxLevel = 3;
    protected int rarity = 3;
    int maxDuplicates = 6;



    protected String name = "Ability";
    protected String id = "ability";
    protected int cooldownTicks = 100;
    protected int cooldownMilliseconds = 5000;

    int maxStacks = 0;
    int stacks = 0;

    public Material item = Material.PAPER;
    public IconType icon = IconType.COPPER_INGOT;

    protected int customModelData = 0;

    protected String description = "Description";


    List<Resource> craftingCost = Arrays.asList(new Resource(ResourceType.IRON, 5),new Resource(ResourceType.GOLD, 10));

    List<List<Resource>> resourceCostPerLevel = Arrays.asList(
            (Arrays.asList(new Resource(ResourceType.IRON, 5),new Resource(ResourceType.GOLD, 10))),
            (Arrays.asList(new Resource(ResourceType.IRON, 10),new Resource(ResourceType.GOLD, 20))),
            (Arrays.asList(new Resource(ResourceType.IRON, 20),new Resource(ResourceType.GOLD, 40)))
    );

    List<AbilityTriggerType> triggers = Arrays.asList(AbilityTriggerType.DEFAULT);
    private String highlightColour = ChatColor.WHITE + "";
    private String darkenColour = ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "";
    private String baseColour = ChatColor.GRAY + "";

    public Ability( ) {

    }

    protected static void heal(Player attacker, Player victim, double amount) {
        double newHealth = victim.getHealth();

        CompoundValueModifier compoundValueModifier = Triggers.healPlayer(attacker, victim);

        newHealth += compoundValueModifier.processValueEffectiveDecrease(amount);

        double maxHealth = victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

        if (newHealth > maxHealth) newHealth = maxHealth;

        victim.setHealth(newHealth);
        playFXHealing(attacker, victim, 1);

    }

    public void notifyPlayerOnCooldownEnd( Player player ) {
        if ( Main.isPlayerInGame(player) ) {
            MiscUtils.sendActionBarMessage(player, ChatColor.GRAY + "Способность " + this.getName() + ChatColor.GRAY + " перезаряжена.");
        }
    }

    public void notifyPlayerOnAbilityActivation( Player player ) {
        if ( Main.isPlayerInGame(player) ) {
            MiscUtils.sendActionBarMessage(player, ChatColor.GRAY + "Способность " + this.getName() + ChatColor.GRAY + " активировалась!");
        }
    }

    public void notifyPlayerOnStackCount( Player player ) {
        if ( Main.isPlayerInGame(player) ) {
            int stacksValue = this.stacks > this.maxStacks ? this.maxStacks : this.stacks;
            MiscUtils.sendActionBarMessage(player, ChatColor.GRAY + "Заряды способности " + this.getName() + ChatColor.GRAY + " - " + stacksValue + "/" + this.maxStacks);
        }
    }

    @Override
    public Material getItem() {
        return this.item;
    }

    @Override
    public String getName() {
        return RarityManager.rarityColours.get(this.rarity) + ChatColor.BOLD + this.name;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getIconString(Player player) {
        return this.icon.getIcon(player);
    }

    @Override
    public int getRarity() { return this.rarity; }

    public static IAbility generateAbility( Class myclass ) {
        try {
            return (IAbility) myclass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void playerKillAssist(int activeLevel, Player killer, Player victim, Player assistant) {};

    @Override
    public void playerDeath(int level, PlayerDeathEvent event) {

    }


    @Override
    public void shopPurchase(int level, Game game, Player player, PurchasableItem item, int amount) {

    }

    @Override
    public void playerKill(int level, Player killer, PlayerDeathEvent event) {};

    @Override
    public void playerRespawn(int level, GamePlayer gamePlayer) {

    }

    @Override
    public void playerFirstSpawn(int level, GamePlayer gamePlayer) {

    }

    @Override
    public void processPurchasibleItem(int activeLevel, GamePlayer gamePlayer, PurchasableItem item) {

    }

    @Override
    public int calculateIntValue1(int level) { return 0; }

    @Override
    public int calculateIntValue2(int level) { return 0; }

    @Override
    public int calculateIntValue3(int level) { return 0; }

    @Override
    public double calculateDoubleValue1(int level) { return 0; }


    @Override
    public String formatValue1(int level) { return String.valueOf(calculateIntValue1(level)); }
    @Override
    public String formatValue2(int level) { return String.valueOf(calculateIntValue2(level)); }
    @Override
    public String formatValue3(int level) { return String.valueOf(calculateIntValue3(level)); }


    @Override
    public String formatValues1( int activeLevel, int slot ) {
        String colour;
        String values = "";
        for (int i = 1; i <= this.maxLevel; i++ ) {
            colour = i == activeLevel ? this.highlightColour : i >= slot ? this.darkenColour : this.baseColour;
            if ( i != this.maxLevel ){
                values += colour + formatValue1(i) + this.baseColour + "/";
            }
            else {
                values += colour + formatValue1(i) + ChatColor.RESET;
            }
        }
        return values;
    }

    @Override
    public String formatValues2(int activeLevel, int slot) {
        String colour;
        String values = "";
        for (int i = 1; i <= this.maxLevel; i++ ) {
            colour = i == activeLevel ? this.highlightColour : i >= slot ? this.darkenColour : this.baseColour;
            if ( i != this.maxLevel ){
                values += colour + formatValue2(i) + this.baseColour + "/";
            }
            else {
                values += colour + formatValue2(i) + ChatColor.RESET;
            }
        }
        return values;
    }

    @Override
    public String formatValues3(int activeLevel, int slot) {
        String colour;
        String values = "";
        for (int i = 1; i <= this.maxLevel; i++ ) {
            colour = i == activeLevel ? this.highlightColour : i >= slot ? this.darkenColour : this.baseColour;
            if ( i != this.maxLevel ){
                values += colour + formatValue3(i) + this.baseColour + "/";
            }
            else {
                values += colour + formatValue3(i) + ChatColor.RESET;
            }
        }
        return values;
    }


    @Override
    public ArrayList<String> parseDescription(int ownedLevel, int activeLevel, int slot) {

        ArrayList<String> description = new ArrayList<>();
        description.add("");
        description.add(ChatColor.WHITE + RarityManager.getFullRarity(this.rarity));
        description.add("");

        String temp = this.baseColour + ( this.description ).replace("#", "#" + this.baseColour).replace("(values1)", formatValues1(activeLevel, slot)).replace("(values2)", formatValues2(activeLevel, slot)).replace("(values3)", formatValues3(activeLevel, slot));
        String str[] = ChatColor.translateAlternateColorCodes('&', temp ).split("#");
        ArrayList<String> lore = new ArrayList<>(Arrays.asList(str));

        description.addAll(lore);

        return description;

    }

    @Override
    public void playerDealDamage(int level, DamageInstance damageInstance, Player attacker, EntityDamageByEntityEvent event, CompoundValueModifier compoundValueModifier) {};

    @Override
    public void playerReceiveDamage(int level, DamageInstance damageInstance, Player victim, EntityDamageEvent event, CompoundValueModifier compoundValueModifier) {};
    @Override
    public void pickupItem(int level, Player player, EntityPickupItemEvent event) {};

    @Override
    public void blockBreak(int activeLevel, BlockBreakEvent event) {};

    @Override
    public void blockPlace(int activeLevel, BlockPlaceEvent event) {};

    @Override
    public void healPlayer(int activeLevel, Player healer, Player target, CompoundValueModifier compoundValueModifier) {};

    @Override
    public void healedByPlayer(int activeLevel, Player target, Player healer, CompoundValueModifier compoundValueModifier) {};

    @Override
    public void gadgetUsed(int activeLevel, GamePlayer gamePlayer, GadgetType gadgetType, CompoundValueModifier compoundValueModifier){  };

    public static void playFXCrowdControl(LivingEntity player, int intensity) {
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_CHAIN_PLACE, 0.3F, 0.6F);
        player.getWorld().spawnParticle(Particle.TOTEM, player.getLocation().clone().add(0, 1.2, 0), intensity * 5,0.2, 0.2, 0.2, 0.1);
    }

    public static void playFXHealing(Player source,Player player, int intensity) {
        Main.getPlayerGameProfile(player).logImpactInstance(new ImpactInstance(Main.getPlayerGameProfile(source), ImpactType.HEALING, ImpactPolarity.ALLY, Main.getPlayerGameProfile(player).getGame().countdown, 1));
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.3F, 0.6F);
        player.getWorld().spawnParticle(Particle.HEART, player.getLocation().clone().add(0, 2.5, 0), intensity,0,0,0);
    }

    public static void playFXDamage(LivingEntity player, int intensity) {
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_HONEY_BLOCK_BREAK, 0.8F, 1.0F);
        player.getWorld().spawnParticle(Particle.SMOKE_LARGE, player.getLocation().clone().add(0, 1.2, 0), intensity * 3, 0.2, 0.2, 0.2, 0.05);
    }

    public static void playFXItemGained(LivingEntity player, int intensity) {
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.2F);
        player.getWorld().spawnParticle(Particle.CRIT_MAGIC, player.getLocation().clone().add(0, 1.2, 0), intensity * 7, 0.2, 0.2, 0.2, 0.05);
    }

    public static void playFXDefensiveUtility(LivingEntity player, int intensity) {
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 0.8F, 1.3F);
        player.getWorld().spawnParticle(Particle.END_ROD, player.getLocation().clone().add(0, 1.2, 0), intensity * 3, 0.2, 0.2, 0.2, 0.05);
    }



}
