package org.screamingsandals.bedwars.utils.flowergun.abilities_base;

import dev.lone.itemsadder.api.CustomStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.external.MiscUtils;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.managers.ColoursManager;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;
import org.screamingsandals.bedwars.utils.flowergun.shoputils.PurchasableItem;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.managers.RarityManager;
import org.screamingsandals.bedwars.utils.flowergun.mechanics.ImpactInstance;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class Ability implements IAbility{

    protected boolean isOnCooldown;

    protected int maxLevel = 3;
    protected int rarity = 3;
    protected int maxDuplicates = 6;

    protected String name = "Ability";
    protected String id = "ability";

    protected ArrayList<AbilityCategory> abilityCategories = new ArrayList<>();
    protected String iaId = "";
    protected int cooldownMilliseconds = 5000;

    protected int maxStacks = 0;
    protected int stacks = 0;

    public Material item = Material.PAPER;
    public IconType icon = IconType.COPPER_INGOT;

    protected int customModelData = 0;

    protected String description = "Description";

    private ChatColor highlightColour =  ColoursManager.white;
    private ChatColor darkenColour = ColoursManager.darkGray;
    private ChatColor baseColour = ColoursManager.gray;

    private static final double levelupMultiplier = 3;

    protected ResourceBundle disassembleResources = new ResourceBundle().addResource(ResourceType.CATALYST_RARE, 1);
    protected ResourceBundle upgradeResources = new ResourceBundle().addResource(ResourceType.CATALYST_RARE, 1);

    protected static void playLineFX(Location start, Location end, Color color) {
        Vector currentPosition = start.toVector().multiply(-1).add(end.toVector());
        Vector movement = currentPosition.clone().normalize().multiply(0.2);
        currentPosition.multiply(0.001);

        double maxDistance = start.distance(end);
        World world = start.getWorld();
//        Particle.DustTransition gradient = new Particle.DustTransition(Color.fromRGB(242, 231, 133), Color.fromRGB(255, 251, 219), 3.0F);
        Particle.DustTransition colorOption = new Particle.DustTransition(color, color, 3);

        while ( currentPosition.length() < maxDistance ) {
            world.spawnParticle(Particle.DUST_COLOR_TRANSITION, start.clone().add(currentPosition), 1, colorOption );
            currentPosition.add(movement);
        }


    }

    @Override
    public ResourceBundle getDisassembleResources() {
        return this.disassembleResources;
    }

    @Override
    public ResourceBundle getUpgradeResources(int level) {
        ResourceBundle resourceBundle = this.upgradeResources.copy();
        resourceBundle.multiplyResources(Math.pow(levelupMultiplier, level - 1) );
        return resourceBundle;
    }

    public void loadCustomItem( String iaId ) {
//        CustomStack customStack = CustomStack.getInstance(iaId);
//        this.item = customStack.getItemStack().getType();
//        this.customModelData = customStack.getItemStack().getItemMeta().getCustomModelData();
        this.iaId = iaId;
    }

    public static void healRegen(Player healer, Player target, PotionEffect potionEffect) {

        CompoundValueModifier compoundValueModifier = Triggers.healPlayer(healer, target);

        if ( target.hasPotionEffect(PotionEffectType.LUCK) )
        compoundValueModifier.addExp((target.getPotionEffect(PotionEffectType.LUCK).getAmplifier() + 1) * 0.1);

        if ( target.hasPotionEffect(PotionEffectType.UNLUCK) )
        compoundValueModifier.addExp((target.getPotionEffect(PotionEffectType.UNLUCK).getAmplifier() + 1) * -0.1);

        int duration = compoundValueModifier.processValueEffectiveDecrease(potionEffect.getDuration());
        if ( duration > 0 )
        {
            potionEffect.withDuration(duration);
            target.addPotionEffect(potionEffect);
        }
        playFXHealing(healer, target, 1);
    }

    public static void healHealth(Player healer, Player target, double healAmount) {

        double newHealth = target.getHealth();

        CompoundValueModifier compoundValueModifier = Triggers.healPlayer(healer, target);

        if ( target.hasPotionEffect(PotionEffectType.LUCK) )
        compoundValueModifier.addExp((target.getPotionEffect(PotionEffectType.LUCK).getAmplifier() + 1) * 0.1);
        if ( target.hasPotionEffect(PotionEffectType.UNLUCK) )
        compoundValueModifier.addExp((target.getPotionEffect(PotionEffectType.UNLUCK).getAmplifier() + 1) * -0.1);

        newHealth += compoundValueModifier.processValueEffectiveDecrease(healAmount);

        double maxHealth = target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

        if (newHealth > maxHealth) newHealth = maxHealth;
        if (newHealth < 0) newHealth = 0;

        target.setHealth(newHealth);
        playFXHealing(healer, target, 1);

    }

    public static void healOverhealth(Player healer, Player target, double healAmount) {

        double newHealth = target.getAbsorptionAmount();

        CompoundValueModifier compoundValueModifier = Triggers.healPlayer(healer, target);

        if ( target.hasPotionEffect(PotionEffectType.LUCK) )
            compoundValueModifier.addExp((target.getPotionEffect(PotionEffectType.LUCK).getAmplifier() + 1) * 0.1);
        if ( target.hasPotionEffect(PotionEffectType.UNLUCK) )
            compoundValueModifier.addExp((target.getPotionEffect(PotionEffectType.UNLUCK).getAmplifier() + 1) * -0.1);

        newHealth += compoundValueModifier.processValueEffectiveDecrease(healAmount);

        double maxOverhealth = FlowerUtils.maxOverhealth;

        if (newHealth > maxOverhealth) newHealth = maxOverhealth;
        if (newHealth < 0) newHealth = 0;

        target.setAbsorptionAmount(newHealth);
        playFXShielding(healer, target, 1);

    }

    public static void healFood(Player healer, Player target, int foodAmount, double saturationAmount) {
        CompoundValueModifier compoundValueModifier = Triggers.healPlayer(healer, target);

        if ( target.hasPotionEffect(PotionEffectType.LUCK) )
            compoundValueModifier.addExp((target.getPotionEffect(PotionEffectType.LUCK).getAmplifier() + 1) * 0.1);
        if ( target.hasPotionEffect(PotionEffectType.UNLUCK) )
            compoundValueModifier.addExp((target.getPotionEffect(PotionEffectType.UNLUCK).getAmplifier() + 1) * -0.1);

        float saturation = (float) (target.getSaturation() + compoundValueModifier.processValueEffectiveDecrease(saturationAmount));
        if ( saturation < 0 ) saturation = 0;
        if ( saturation > 10 ) saturation = 10;
        target.setSaturation(saturation);

        int food = (target.getFoodLevel() + compoundValueModifier.processValueEffectiveDecrease(foodAmount));
        if ( food < 0 ) food = 0;
        if ( food > 20 ) food = 20;
        target.setFoodLevel(food);
        playFXHealing(healer, target,1);

    }

    public void notifyPlayerOnCooldownEnd( Player player ) {
        if ( Main.isPlayerInGame(player) ) {
            MiscUtils.sendActionBarMessage(player, ChatColor.GRAY + "Способность " + this.getName() + ChatColor.GRAY + " перезаряжена.");
        }
    }

    @Override
    public String getRawName() {
        return this.name;
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
    public Material getAbilityMaterial() {
        return this.item;
    }

    @Override
    public ItemStack getAbilityGuiItem() {
        ItemStack itemStack;
        if ( iaId != null ) {
            CustomStack customStack = CustomStack.getInstance(iaId);
            if ( customStack != null ) {
                itemStack = customStack.getItemStack();
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(this.getName());
                itemStack.setItemMeta(itemMeta);

                return itemStack;
            }
        }

        itemStack = new ItemStack(this.item);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setCustomModelData(this.customModelData);
        itemMeta.setDisplayName(this.getName());
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    @Override
    public int getMaxLevel() {
        return this.maxLevel;
    }

    @Override
    public int getNextLevelCost(int ownedLevel) {
        if ( ownedLevel < getMaxLevel() && ownedLevel > 0 ) {
            return ownedLevel + 1;
        }
        else return 0;
    }

    @Override
    public String getName() {
        return RarityManager.rarityColours.get(this.rarity) + this.name;
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
    public String getNameWithIcon(Player player) {
        return ChatColor.WHITE + this.icon.getIcon(player) + " " + this.getName();
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
            Bukkit.getConsoleSender().sendMessage("ERROR with class " + myclass.getName());
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void playerKillAssist(int activeLevel, Player killer, Player victim, Player assistant) {};

    @Override
    public void playerDeath(int level, Player victim, Player killer, PlayerDeathEvent event) {

    }


    @Override
    public void shopPurchase(int level, Game game, Player player, PurchasableItem item, int amount) {

    }

    @Override
    public void playerKill(int level, Player victim, Player killer, PlayerDeathEvent event) {};

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
            colour = i == activeLevel ? this.highlightColour + "" : i > slot ? this.darkenColour + "" + ChatColor.STRIKETHROUGH : this.baseColour + "";
            if ( i != this.maxLevel ){
                values += colour + formatValue1(i) + this.baseColour + "/";
            }
            else {
                values += colour + formatValue1(i) + ChatColor.RESET;
            }
        }
        return values + ColoursManager.gray;
    }

    @Override
    public TextComponent formatValues1intoComponent(int activeLevel, int slot) {
        TextColor colour;
        TextDecoration textDecoration = null;
        ComponentBuilder values = Component.empty().toBuilder();
        for (int i = 1; i <= this.maxLevel; i++ ) {
            if ( i == activeLevel) {
                colour = ColoursManager.getComponent(this.highlightColour);
                textDecoration = null;
            } else if ( i > slot ) {
                colour = ColoursManager.getComponent(this.darkenColour);
                textDecoration = TextDecoration.STRIKETHROUGH;
            } else {
                colour = ColoursManager.getComponent(this.baseColour);
                textDecoration = null;
            }

            ComponentBuilder component;
            if ( textDecoration != null )
                component = Component.text().content(formatValue1(i)).color(colour).decorate(textDecoration);
            else
                component = Component.text().content(formatValue1(i)).color(colour);

            if ( i != this.maxLevel ) component.append(Component.text("/").color(ColoursManager.getComponent(this.baseColour)));

            values.append(component);
        }
        return (TextComponent) values.build();
    }

    @Override
    public TextComponent formatValues2intoComponent(int activeLevel, int slot) {
        TextColor colour;
        TextDecoration textDecoration = null;
        ComponentBuilder values = Component.empty().toBuilder();
        for (int i = 1; i <= this.maxLevel; i++ ) {
            if ( i == activeLevel) {
                colour = ColoursManager.getComponent(this.highlightColour);
                textDecoration = null;
            } else if ( i > slot ) {
                colour = ColoursManager.getComponent(this.darkenColour);
                textDecoration = TextDecoration.STRIKETHROUGH;
            } else {
                colour = ColoursManager.getComponent(this.baseColour);
                textDecoration = null;
            }

            ComponentBuilder component;
            if ( textDecoration != null )
                component = Component.text().content(formatValue2(i)).color(colour).decorate(textDecoration);
            else
                component = Component.text().content(formatValue2(i)).color(colour);

            if ( i != this.maxLevel ) component.append(Component.text("/").color(ColoursManager.getComponent(this.baseColour)));

            values.append(component);
        }
        return (TextComponent) values.build();
    }

    @Override
    public TextComponent formatValues3intoComponent(int activeLevel, int slot) {
        TextColor colour;
        TextDecoration textDecoration = null;
        ComponentBuilder values = Component.empty().toBuilder();
        for (int i = 1; i <= this.maxLevel; i++ ) {
            if ( i == activeLevel) {
                colour = ColoursManager.getComponent(this.highlightColour);
                textDecoration = null;
            } else if ( i > slot ) {
                colour = ColoursManager.getComponent(this.darkenColour);
                textDecoration = TextDecoration.STRIKETHROUGH;
            } else {
                colour = ColoursManager.getComponent(this.baseColour);
                textDecoration = null;
            }

            ComponentBuilder component;
            if ( textDecoration != null )
                component = Component.text().content(formatValue3(i)).color(colour).decorate(textDecoration);
            else
                component = Component.text().content(formatValue3(i)).color(colour);

            if ( i != this.maxLevel ) component.append(Component.text("/").color(ColoursManager.getComponent(this.baseColour)));

            values.append(component);
        }
        return (TextComponent) values.build();
    }

    @Override
    public String formatValues2(int activeLevel, int slot) {
        String colour;
        String values = "";
        for (int i = 1; i <= this.maxLevel; i++ ) {
            colour = i == activeLevel ? this.highlightColour + "" : i > slot ? this.darkenColour + "" + ChatColor.STRIKETHROUGH : this.baseColour + "";
            if ( i != this.maxLevel ){
                values += colour + formatValue2(i) + this.baseColour + "/";
            }
            else {
                values += colour + formatValue2(i) + ChatColor.RESET;
            }
        }
        return values + ColoursManager.gray;
    }

    @Override
    public String formatValues3(int activeLevel, int slot) {
        String colour;
        String values = "";
        for (int i = 1; i <= this.maxLevel; i++ ) {
            colour = i == activeLevel ? this.highlightColour + "" : i > slot ? this.darkenColour + "" + ChatColor.STRIKETHROUGH : this.baseColour + "";
            if ( i != this.maxLevel ){
                values += colour + formatValue3(i) + this.baseColour + "/";
            }
            else {
                values += colour + formatValue3(i) + ChatColor.RESET;
            }
        }
        return values + ColoursManager.gray;
    }


    @Override
    public ArrayList<String> parseDescription(int ownedLevel, int activeLevel, int slot) {

        ArrayList<String> description = new ArrayList<>();
        description.add("");
        description.add(ChatColor.WHITE + RarityManager.getFullRarity(this.rarity));
        description.add("");
        String categories = "";
        for (int i = 0; i < abilityCategories.size(); i++ ) {
            if ( i > 0 ) categories += " ";
            categories += ColoursManager.gray + "[ " + this.abilityCategories.get(i).getColor() + this.abilityCategories.get(i).getName() + ColoursManager.gray + " ]";
        }
        description.add(categories);
        description.add("");

        String temp = this.baseColour + ( this.description ).replace("#", "#" + this.baseColour).replace("(values1)", formatValues1(activeLevel, slot)).replace("(values2)", formatValues2(activeLevel, slot)).replace("(values3)", formatValues3(activeLevel, slot));
        String str[] = ChatColor.translateAlternateColorCodes('&', temp ).split("#");
        ArrayList<String> lore = new ArrayList<>(Arrays.asList(str));

        description.addAll(lore);

        return description;
    }


    public TextComponent parseDescriptionComponent( Player player,int ownedLevel, int activeLevel, int slot){

        ComponentBuilder description = Component.text()
                .append(Component.text(this.getIconString(player))).append(Component.text(" ")).append(Component.text(this.getRawName()).color(ColoursManager.getComponent(RarityManager.getRarityColour(this.rarity))))
                .append(Component.newline())
                .append(Component.newline())
                .append(RarityManager.getFullRarityComponent(this.rarity)).color(TextColor.color(255, 255, 255))
                .append(Component.newline());

        //String temp = this.baseColour + ( this.description ).replace("#", "#" + this.baseColour).replace("(values1)", formatValues1(activeLevel, slot)).replace("(values2)", formatValues2(activeLevel, slot)).replace("(values3)", formatValues3(activeLevel, slot));
        String temp = this.description;
        String str[] = temp.split("#");
        ArrayList<String> lore = new ArrayList<>(Arrays.asList(str));

        TextReplacementConfig textReplacementConfig1 = TextReplacementConfig.builder().matchLiteral("(values1)").replacement(formatValues1intoComponent(activeLevel, slot)).build();
        TextReplacementConfig textReplacementConfig2 = TextReplacementConfig.builder().matchLiteral("(values2)").replacement(formatValues2intoComponent(activeLevel, slot)).build();
        TextReplacementConfig textReplacementConfig3 = TextReplacementConfig.builder().matchLiteral("(values3)").replacement(formatValues3intoComponent(activeLevel, slot)).build();

        for ( String line : lore) {
            description = description.append(Component.newline());
            description = description.append(Component.text(line).color(TextColor.color(ColoursManager.getComponent(this.baseColour))).replaceText(textReplacementConfig1).replaceText(textReplacementConfig2).replaceText(textReplacementConfig3));
        }

        return (TextComponent) description.build();
    };

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

    @Override
    public void itemConsume(int activeLevel, Player player, PlayerItemConsumeEvent event) {};

    @Override
    public void projectileHit(int level, Player player, ProjectileHitEvent event) {};

    @Override
    public void projectileLaunch(int level, Player player, ProjectileLaunchEvent event) {};

    public static void playFXSlow(LivingEntity player, int intensity) {
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_CHAIN_PLACE, 0.3F, 0.6F);
        if (player instanceof Player)
        Main.getStatsManager().addResourceToPlayer((Player) player, ResourceType.SLIMEBALL, 1);
        player.getWorld().spawnParticle(Particle.SLIME, player.getLocation().clone().add(0, 1.2, 0), intensity * 5,0.2, 0.2, 0.2, 0.1);
    }

    public static void playFXDebuff(LivingEntity player, int intensity) {
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 0.5F, 1.5F);
        player.getWorld().spawnParticle(Particle.SOUL, player.getLocation().clone().add(0, 1.2, 0), intensity * 3,0.2, 0.2, 0.2, 0.1);
    }

    public static void playFXHealing(Player source,Player player, int intensity) {
        Main.getPlayerGameProfile(player).logImpactInstance(new ImpactInstance(Main.getPlayerGameProfile(source), ImpactType.HEALING, ImpactPolarity.ALLY, Main.getPlayerGameProfile(player).getGame().countdown, intensity));
        Main.getStatsManager().addResourceToPlayer(source, ResourceType.RUBY, 1);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_CLERIC, 0.5F, 1F);
        player.getWorld().spawnParticle(Particle.HEART, player.getLocation().clone().add(0, 2.5, 0), intensity,0,0,0);
    }

    public static void playFXShielding(Player source,Player player, int intensity) {
        Main.getPlayerGameProfile(player).logImpactInstance(new ImpactInstance(Main.getPlayerGameProfile(source), ImpactType.PROTECTION, ImpactPolarity.ALLY, Main.getPlayerGameProfile(player).getGame().countdown, intensity));
        Main.getStatsManager().addResourceToPlayer(source, ResourceType.QUARTZ, 1);
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 0.1F, 0.7F);
        player.getWorld().spawnParticle(Particle.SCRAPE, player.getLocation().clone().add(0, 2.5, 0), intensity,0,0,0);
    }

    public static void playFXDamage(LivingEntity player, int intensity) {
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_HONEY_BLOCK_BREAK, 0.8F, 1.0F);
        player.getWorld().spawnParticle(Particle.DAMAGE_INDICATOR, player.getLocation().clone().add(0, 1.2, 0), intensity * 3, 0.2, 0.2, 0.2, 0.05);
    }

    public static void playFXItemGained(LivingEntity player, int intensity) {
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.2F);
        player.getWorld().spawnParticle(Particle.CRIT_MAGIC, player.getLocation().clone().add(0, 1.2, 0), intensity * 7, 0.2, 0.2, 0.2, 0.05);
    }

    public static void playFXDefensiveUtility(LivingEntity player, int intensity) {
        if (player instanceof Player) {
            Main.getStatsManager().addResourceToPlayer((Player) player, ResourceType.LEATHER, 1);
            Main.getPlayerGameProfile((Player) player).logImpactInstance(new ImpactInstance(Main.getPlayerGameProfile((Player) player), ImpactType.PROTECTION, ImpactPolarity.ALLY, intensity));
        }
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 0.2F, 2.0F);
        player.getWorld().spawnParticle(Particle.END_ROD, player.getLocation().clone().add(0, 1.2, 0), intensity * 3, 0.2, 0.2, 0.2, 0.05);
    }

    public static void playFXSpeed(LivingEntity player, int intensity) {
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PHANTOM_FLAP, 1F, 2F);
        player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation().clone().add(0, 1.2, 0), intensity * 3, 0.2, 0.2, 0.2, 0.05);
    }

    public static void playFXBuff(LivingEntity player, int intensity) {
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 0.5F, 1.5F);
        player.getWorld().spawnParticle(Particle.TOTEM, player.getLocation().clone().add(0, 1.2, 0), intensity * 3, 0.2, 0.2, 0.2, 0.05);
    }

    public static void playFXFire(LivingEntity player, int intensity) {
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 1F, 2F);
        player.getWorld().spawnParticle(Particle.LAVA, player.getLocation().clone().add(0, 1.2, 0), intensity * 3, 0.2, 0.2, 0.2, 0.05);
    }

    @Override
    @Deprecated
    public ItemStack getGuiItem() {
        return this.getAbilityGuiItem();
    }

    public static GamePlayer findClosestAlly(Player player, Location location, double radius) {
        Game game = Main.getPlayerGameProfile(player).getGame();
        if ( game == null ) return null;
        ArrayList<GamePlayer> players = new ArrayList<>(game.getConnectedGamePlayers());
        GamePlayer gPlayer = Main.getPlayerGameProfile(player);
        players.remove(gPlayer);
        GamePlayer chosenAlly = null;
        double minDistance = 1000;

        for ( GamePlayer gamePlayer : players ) {
            if ( !gamePlayer.isSpectator && game.getPlayerTeam(gamePlayer) == game.getPlayerTeam(gPlayer)) {
                double distance = gamePlayer.player.getLocation().distance(location);
                if ( distance < minDistance ) {
                    minDistance = distance;
                    chosenAlly = gamePlayer;
                }
            }
        }

        if ( radius < minDistance ) return null;
        return chosenAlly;
    }

    public static GamePlayer findClosestAlly(Player player, double radius) {
        return findClosestAlly(player, player.getLocation(), radius);
    }

    public static ArrayList<GamePlayer> findAlliesInRange(Player player, double radius) {
        Game game = Main.getPlayerGameProfile(player).getGame();
        if ( game == null ) return null;
        ArrayList<GamePlayer> players = new ArrayList<>(game.getConnectedGamePlayers());
        GamePlayer mainPlayer = Main.getPlayerGameProfile(player);
        players.remove(mainPlayer);

        for ( int i = 0; i < players.size(); i++ ) {
            GamePlayer gamePlayer = players.get(i);
            if ( gamePlayer.player.getLocation().distance(mainPlayer.player.getLocation()) < radius )
                if ( gamePlayer.isSpectator || game.getPlayerTeam(gamePlayer) != game.getPlayerTeam(mainPlayer)) {
                    i--;
                    players.remove(gamePlayer);
                }
        }

        return players;
    }


    public static ArrayList<GamePlayer> findEnemiesInRange(Player player, double radius) {
        Game game = Main.getPlayerGameProfile(player).getGame();
        if ( game == null ) return null;
        ArrayList<GamePlayer> players = new ArrayList<>(game.getConnectedGamePlayers());
        GamePlayer mainPlayer = Main.getPlayerGameProfile(player);
        players.remove(mainPlayer);

        for ( int i = 0; i < players.size(); i++ ) {
            GamePlayer gamePlayer = players.get(i);
            if ( gamePlayer.player.getLocation().distance(mainPlayer.player.getLocation()) < radius )
            if ( gamePlayer.isSpectator || game.getPlayerTeam(gamePlayer) == game.getPlayerTeam(mainPlayer)) {
                i--;
                players.remove(gamePlayer);
            }
        }
        return players;
    }

    public static boolean isEnemyInRange(Player player, double radius) {
        Game game = Main.getPlayerGameProfile(player).getGame();
        if ( game == null ) return false;
        ArrayList<GamePlayer> players = new ArrayList<>(game.getConnectedGamePlayers());
        GamePlayer mainPlayer = Main.getPlayerGameProfile(player);
        players.remove(mainPlayer);

        for ( GamePlayer gamePlayer : players ) {
            if ( gamePlayer.player.getLocation().distance(mainPlayer.player.getLocation()) < radius )
            if ( gamePlayer.isSpectator || game.getPlayerTeam(gamePlayer) != game.getPlayerTeam(mainPlayer)) {
                return true;
            }
        }
        return false;
    }

}
