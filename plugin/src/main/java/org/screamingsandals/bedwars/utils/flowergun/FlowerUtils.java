package org.screamingsandals.bedwars.utils.flowergun;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.api.boss.BossBar;
import org.screamingsandals.bedwars.api.boss.BossBar19;
import org.screamingsandals.bedwars.api.game.ItemSpawner;
import org.screamingsandals.bedwars.boss.BossBar18;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.customgui.shoputils.GameFlag;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.screamingsandals.bedwars.lib.lang.I.i18n;
import static org.screamingsandals.bedwars.lib.lang.I.i18nonly;

public class FlowerUtils {

    public static ArrayList<ItemStack> destroyedResources;
    public static double durabilityReduction;
    public static double durabilityCounterReductionPerUnbreakingLevel;

    public static int firstEventTimestamp;
    public static int secondEventTimestamp;
    public static int thirdEventTimestamp;

    public static int toolsDamageScaleStartSeconds;
    public static int toolsDamageScaleEndSeconds;

    public static double toolsDamageScaleStartValue;
    public static double toolsDamageScaleEndValue;
    public static final double agility4aThreshold = 0.2;
    public static final double agility4bThreshold = 0.2;
    public static int fireballCooldown = 20;
    public static int snowballColldown = 80;
    public static double snowballDamage = 3.5;
    public static int deathmatchWarning = 60;
    public static double golemDamage = 5;
    public static double zoglinDamage = 3;
    public static double phantomDamage = 3;
    public static double blazeDamage = 3;
    private static double deathmatchWallPushPower = 0.8;

    public boolean destroyResources;

    public static List<Material> allowedRecipes = new ArrayList<>();
    public static List<Material> doubleBlocks = new ArrayList<>();
    public static List<Material> swords = new ArrayList<>();

    public static List<Material> axes = new ArrayList<>();
    public static List<Material> consumableItems = new ArrayList<>();
    public static List<Material> helmets = new ArrayList<>();
    public static List<Material> chestplates = new ArrayList<>();
    public static List<Material> leggins = new ArrayList<>();
    public static List<Material> boots = new ArrayList<>();
    public static final int axesChance = 80;
    public static final int swordsChance = 40;
    public static int swordOnShieldCooldown = 25;
    public static List<String> alphaWarning = Arrays.asList(ChatColor.DARK_RED + "" + ChatColor.BOLD + "АЛЬФА ТЕСТ!!!", ChatColor.RED + "Всем игрокам доступны все", ChatColor.RED + "способности на максимальном уровне.");

    public static DecimalFormat singleDecimal = new DecimalFormat("#.#");
    public static DecimalFormat doubleDecimal = new DecimalFormat("#.##");

    public FlowerUtils() {

        firstEventTimestamp = 120;
        secondEventTimestamp = 480;
        thirdEventTimestamp = 1200;

        toolsDamageScaleStartSeconds = 60;
        toolsDamageScaleEndSeconds = 300;

        toolsDamageScaleStartValue = 0.75;
        toolsDamageScaleEndValue = 0.10;

        destroyResources = true;
        durabilityReduction = 0.4;
        durabilityCounterReductionPerUnbreakingLevel = 0.01;


        destroyedResources = new ArrayList<>();

        destroyedResources.add(Main.getSpawnerType("bronze").getStack());
        destroyedResources.add(Main.getSpawnerType("iron").getStack());
        destroyedResources.add(Main.getSpawnerType("gold").getStack());
        destroyedResources.add(Main.getSpawnerType("emerald").getStack());
        destroyedResources.add(new ItemStack(Material.ELYTRA));

        chestplates.add(Material.LEATHER_CHESTPLATE);
        chestplates.add(Material.CHAINMAIL_CHESTPLATE);
        chestplates.add(Material.GOLDEN_CHESTPLATE);
        chestplates.add(Material.IRON_CHESTPLATE);
        chestplates.add(Material.DIAMOND_CHESTPLATE);
        chestplates.add(Material.NETHERITE_CHESTPLATE);

        helmets.add(Material.LEATHER_HELMET);

        leggins.add(Material.LEATHER_LEGGINGS);

        boots.add(Material.LEATHER_BOOTS);
        boots.add(Material.IRON_BOOTS);
        boots.add(Material.GOLDEN_BOOTS);

        consumableItems.add(Material.POTION);
        consumableItems.add(Material.HONEY_BOTTLE);

        swords.add(Material.STONE_SWORD);
        swords.add(Material.DIAMOND_SWORD);
        swords.add(Material.GOLDEN_SWORD);
        swords.add(Material.IRON_SWORD);
        swords.add(Material.WOODEN_SWORD);
        swords.add(Material.NETHERITE_SWORD);

        axes.add(Material.STONE_AXE);
        axes.add(Material.DIAMOND_AXE);
        axes.add(Material.GOLDEN_AXE);
        axes.add(Material.IRON_AXE);
        axes.add(Material.WOODEN_AXE);
        axes.add(Material.NETHERITE_AXE);

        doubleBlocks.add(Material.ROSE_BUSH);
        doubleBlocks.add(Material.TALL_GRASS);
        doubleBlocks.add(Material.LARGE_FERN);
        doubleBlocks.add(Material.SUNFLOWER);
        doubleBlocks.add(Material.SPRUCE_DOOR);
        doubleBlocks.add(Material.BIRCH_DOOR);
        doubleBlocks.add(Material.DARK_OAK_DOOR);

        allowedRecipes.add(Material.DARK_OAK_PLANKS);
        allowedRecipes.add(Material.DARK_OAK_PRESSURE_PLATE);
        allowedRecipes.add(Material.DARK_OAK_BUTTON);
        allowedRecipes.add(Material.DARK_OAK_FENCE);
        allowedRecipes.add(Material.DARK_OAK_FENCE_GATE);
        allowedRecipes.add(Material.DARK_OAK_LOG);
        allowedRecipes.add(Material.DARK_OAK_SIGN);
        allowedRecipes.add(Material.DARK_OAK_SLAB);
        allowedRecipes.add(Material.DARK_OAK_STAIRS);
        allowedRecipes.add(Material.DARK_OAK_TRAPDOOR);
        allowedRecipes.add(Material.DARK_OAK_DOOR);

        allowedRecipes.add(Material.SPRUCE_PLANKS);
        allowedRecipes.add(Material.SPRUCE_PRESSURE_PLATE);
        allowedRecipes.add(Material.SPRUCE_BUTTON);
        allowedRecipes.add(Material.SPRUCE_FENCE);
        allowedRecipes.add(Material.SPRUCE_FENCE_GATE);
        allowedRecipes.add(Material.SPRUCE_LOG);
        allowedRecipes.add(Material.SPRUCE_SIGN);
        allowedRecipes.add(Material.SPRUCE_SLAB);
        allowedRecipes.add(Material.SPRUCE_STAIRS);
        allowedRecipes.add(Material.SPRUCE_TRAPDOOR);
        allowedRecipes.add(Material.SPRUCE_DOOR);

        allowedRecipes.add(Material.BIRCH_PLANKS);
        allowedRecipes.add(Material.BIRCH_PRESSURE_PLATE);
        allowedRecipes.add(Material.BIRCH_BUTTON);
        allowedRecipes.add(Material.BIRCH_FENCE);
        allowedRecipes.add(Material.BIRCH_FENCE_GATE);
        allowedRecipes.add(Material.BIRCH_LOG);
        allowedRecipes.add(Material.BIRCH_SIGN);
        allowedRecipes.add(Material.BIRCH_SLAB);
        allowedRecipes.add(Material.BIRCH_STAIRS);
        allowedRecipes.add(Material.BIRCH_TRAPDOOR);
        allowedRecipes.add(Material.BIRCH_DOOR);

        allowedRecipes.add(Material.STICK);
        allowedRecipes.add(Material.COBBLESTONE);
        allowedRecipes.add(Material.COBBLESTONE_SLAB);
        allowedRecipes.add(Material.COBBLESTONE_STAIRS);
        allowedRecipes.add(Material.COBBLESTONE_WALL);
        allowedRecipes.add(Material.MOSSY_COBBLESTONE);
        allowedRecipes.add(Material.MOSSY_COBBLESTONE_SLAB);
        allowedRecipes.add(Material.MOSSY_COBBLESTONE_STAIRS);
        allowedRecipes.add(Material.MOSSY_COBBLESTONE_WALL);
        allowedRecipes.add(Material.MOSSY_STONE_BRICKS);
        allowedRecipes.add(Material.MOSSY_STONE_BRICK_SLAB);
        allowedRecipes.add(Material.MOSSY_STONE_BRICK_STAIRS);
        allowedRecipes.add(Material.MOSSY_STONE_BRICK_WALL);
        allowedRecipes.add(Material.STONE_BUTTON);
        allowedRecipes.add(Material.STONE_SLAB);
        allowedRecipes.add(Material.STONE_STAIRS);
        allowedRecipes.add(Material.STONE_PRESSURE_PLATE);
        allowedRecipes.add(Material.STONE_BRICKS);
        allowedRecipes.add(Material.STONE_PRESSURE_PLATE);
        allowedRecipes.add(Material.STONE_BRICK_SLAB);
        allowedRecipes.add(Material.STONE_BRICK_STAIRS);
        allowedRecipes.add(Material.STONE_BRICK_WALL);
        allowedRecipes.add(Material.CRAFTING_TABLE);

        allowedRecipes.add(Material.BONE);
        allowedRecipes.add(Material.BONE_BLOCK);
        allowedRecipes.add(Material.BONE_MEAL);

        allowedRecipes.add(Material.BRICK_SLAB);
        allowedRecipes.add(Material.BRICKS);
        allowedRecipes.add(Material.BRICK_SLAB);
        allowedRecipes.add(Material.BRICK_STAIRS);
        allowedRecipes.add(Material.BRICK_WALL);
        allowedRecipes.add(Material.FLOWER_POT);

        allowedRecipes.add(Material.WHITE_STAINED_GLASS_PANE);
        allowedRecipes.add(Material.BLACK_STAINED_GLASS_PANE);
        allowedRecipes.add(Material.BLUE_STAINED_GLASS);
        allowedRecipes.add(Material.BROWN_STAINED_GLASS_PANE);
        allowedRecipes.add(Material.CYAN_STAINED_GLASS_PANE);
        allowedRecipes.add(Material.GRAY_STAINED_GLASS_PANE);
        allowedRecipes.add(Material.GREEN_STAINED_GLASS_PANE);
        allowedRecipes.add(Material.LIME_STAINED_GLASS_PANE);
        allowedRecipes.add(Material.MAGENTA_STAINED_GLASS_PANE);
        allowedRecipes.add(Material.ORANGE_STAINED_GLASS_PANE);
        allowedRecipes.add(Material.PINK_STAINED_GLASS_PANE);
        allowedRecipes.add(Material.PURPLE_STAINED_GLASS_PANE);
        allowedRecipes.add(Material.RED_STAINED_GLASS_PANE);
        allowedRecipes.add(Material.YELLOW_STAINED_GLASS_PANE);
        allowedRecipes.add(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        allowedRecipes.add(Material.LIGHT_GRAY_STAINED_GLASS_PANE);

        allowedRecipes.add(Material.BLUE_STAINED_GLASS);
        allowedRecipes.add(Material.BLACK_STAINED_GLASS);
        allowedRecipes.add(Material.BROWN_STAINED_GLASS);
        allowedRecipes.add(Material.CYAN_STAINED_GLASS);
        allowedRecipes.add(Material.GRAY_STAINED_GLASS);
        allowedRecipes.add(Material.GREEN_STAINED_GLASS);
        allowedRecipes.add(Material.LIGHT_BLUE_STAINED_GLASS);
        allowedRecipes.add(Material.LIGHT_GRAY_STAINED_GLASS);
        allowedRecipes.add(Material.LIME_STAINED_GLASS);
        allowedRecipes.add(Material.MAGENTA_STAINED_GLASS);
        allowedRecipes.add(Material.ORANGE_STAINED_GLASS);
        allowedRecipes.add(Material.PINK_STAINED_GLASS);
        allowedRecipes.add(Material.PURPLE_STAINED_GLASS);
        allowedRecipes.add(Material.RED_STAINED_GLASS);
        allowedRecipes.add(Material.WHITE_STAINED_GLASS);
        allowedRecipes.add(Material.YELLOW_STAINED_GLASS);

        allowedRecipes.add(Material.BLUE_DYE);
        allowedRecipes.add(Material.BROWN_DYE);
        allowedRecipes.add(Material.BLACK_DYE);
        allowedRecipes.add(Material.CYAN_DYE);
        allowedRecipes.add(Material.GRAY_DYE);
        allowedRecipes.add(Material.GREEN_DYE);
        allowedRecipes.add(Material.LIME_DYE);
        allowedRecipes.add(Material.MAGENTA_DYE);
        allowedRecipes.add(Material.ORANGE_DYE);
        allowedRecipes.add(Material.PINK_DYE);
        allowedRecipes.add(Material.PURPLE_DYE);
        allowedRecipes.add(Material.RED_DYE);
        allowedRecipes.add(Material.WHITE_DYE);
        allowedRecipes.add(Material.YELLOW_DYE);
        allowedRecipes.add(Material.LIGHT_BLUE_DYE);

        allowedRecipes.add(Material.BLACK_WOOL);
        allowedRecipes.add(Material.WHITE_WOOL);
        allowedRecipes.add(Material.BLUE_WOOL);
        allowedRecipes.add(Material.BROWN_WOOL);
        allowedRecipes.add(Material.CYAN_WOOL);
        allowedRecipes.add(Material.GRAY_WOOL);
        allowedRecipes.add(Material.GREEN_WOOL);
        allowedRecipes.add(Material.LIGHT_BLUE_WOOL);
        allowedRecipes.add(Material.LIGHT_GRAY_WOOL);
        allowedRecipes.add(Material.LIME_WOOL);
        allowedRecipes.add(Material.MAGENTA_WOOL);
        allowedRecipes.add(Material.YELLOW_WOOL);
        allowedRecipes.add(Material.RED_WOOL);
        allowedRecipes.add(Material.PINK_WOOL);
        allowedRecipes.add(Material.ORANGE_WOOL);
        allowedRecipes.add(Material.PURPLE_WOOL);

        allowedRecipes.add(Material.SANDSTONE);
        allowedRecipes.add(Material.SANDSTONE_SLAB);
        allowedRecipes.add(Material.SANDSTONE_STAIRS);
        allowedRecipes.add(Material.SMOOTH_SANDSTONE);
        allowedRecipes.add(Material.SMOOTH_SANDSTONE_SLAB);
        allowedRecipes.add(Material.SMOOTH_SANDSTONE_STAIRS);
        allowedRecipes.add(Material.CHISELED_SANDSTONE);

    }

    //def 0em 0gold 8iron 2br
    //ev1 60em 30gold 8iron 2br
    //ev2 40em 20gold 6iron 1br
    //ev3 20em 10gold 3iron 1br

    public static void firstEventRun(Game game) {
        List<ItemSpawner> itemSpawners = new ArrayList<>(game.unsafeGetItemSpawners());
        for (ItemSpawner itemSpawner: itemSpawners) {
            if (itemSpawner.getItemSpawnerType().getConfigKey().equals("gold")) itemSpawner.setInterval(30);
            if (itemSpawner.getItemSpawnerType().getConfigKey().equals("emerald")) itemSpawner.setInterval(60);
        }

        for (Player player : game.getConnectedPlayers()) {
            player.sendTitle("", ChatColor.GOLD + i18n("event1_text", "Gold and Emerald activated!", false), 5, 40, 5);
        }
        Bukkit.getConsoleSender().sendMessage("event1 happened " + game.gameFlags.size() + " "  + game.gameFlags);
        game.gameFlags.add(GameFlag.GOLD_AND_EMERALDS_SPAWN);

        Bukkit.getConsoleSender().sendMessage("event1 end " + game.gameFlags.size() + " " + game.gameFlags);

        BossBar bossbar = (BossBar) game.statusbar;
        if (bossbar instanceof BossBar19) {
            BossBar19 bossbar19 = (BossBar19) bossbar;
//            bossbar19.setColor(BarColor.WHITE);
//            bossbar19.setMessage("Stage 2: Gold and Emerald Spawn");
            bossbar19.setMessage(i18nonly("event1_bossbar"));
        }

    }

    public static void secondEventRun(Game game) {
        List<ItemSpawner> itemSpawners = new ArrayList<>(game.unsafeGetItemSpawners());
        for (ItemSpawner itemSpawner: itemSpawners) {
            if (itemSpawner.getItemSpawnerType().getConfigKey().equals("gold")) itemSpawner.setInterval(20);
            if (itemSpawner.getItemSpawnerType().getConfigKey().equals("emerald")) itemSpawner.setInterval(40);
            if (itemSpawner.getItemSpawnerType().getConfigKey().equals("iron")) itemSpawner.setInterval(6);
            if (itemSpawner.getItemSpawnerType().getConfigKey().equals("bronze")) itemSpawner.setInterval(1);
        }

        BossBar bossbar = (BossBar) game.statusbar;
        if (bossbar instanceof BossBar19) {
            BossBar19 bossbar19 = (BossBar19) bossbar;
//            bossbar19.setColor(BarColor.WHITE);
//            bossbar19.setMessage("Stage 3: Generators Speed Up");
            bossbar19.setMessage(i18nonly("event2_bossbar"));
        }

        for (Player player : game.getConnectedPlayers()) {
            player.sendTitle("", ChatColor.GOLD + i18n("event2_text", "Gold and Emerald sped UP!", false), 5, 40, 5);
        }
    }

    public static void thirdEventRun(Game game) {
        List<ItemSpawner> itemSpawners = new ArrayList<>(game.unsafeGetItemSpawners());
        for (ItemSpawner itemSpawner: itemSpawners) {
            if (itemSpawner.getItemSpawnerType().getConfigKey().equals("gold")) itemSpawner.setInterval(10);
            if (itemSpawner.getItemSpawnerType().getConfigKey().equals("emerald")) itemSpawner.setInterval(20);
            if (itemSpawner.getItemSpawnerType().getConfigKey().equals("iron")) itemSpawner.setInterval(3);
            if (itemSpawner.getItemSpawnerType().getConfigKey().equals("bronze")) itemSpawner.setInterval(1);
        }

        BossBar bossbar = (BossBar) game.statusbar;
        if (bossbar instanceof BossBar19) {
            BossBar19 bossbar19 = (BossBar19) bossbar;
//            bossbar19.setColor(BarColor.WHITE);
//            bossbar19.setMessage("Stage 4: Generators Overload");
            bossbar19.setMessage(i18nonly("event3_bossbar"));
        }

        for (Player player : game.getConnectedPlayers()) {
            player.sendTitle("", ChatColor.GOLD + i18n("event3_text", "Gold and Emerald on OVERDRIVE!", false), 5, 40, 5);
        }
    }

    public static void deathmatchStart(Game game) {
        Bukkit.getConsoleSender().sendMessage("deathmatch happened " + game.deathmatchStart + " " + game.deathmatchTime);
        BossBar bossbar = (BossBar) game.statusbar;

        game.arenaRadius = Math.max(Math.abs(game.getPos1().getBlockX() - game.getPos2().getBlockX()), Math.abs(game.getPos1().getBlockZ() - game.getPos2().getBlockZ())) / 2;

        game.arenaCeiling = Math.max(game.getPos1().getBlockY(), game.getPos2().getBlockY());
        game.arenaFloor = Math.min(game.getPos1().getBlockY(), game.getPos2().getBlockY());

//        game.currentCeiling = game.arenaCeiling;
//        game.currentFloor = game.arenaFloor;
//        game.currentRadius = game.arenaRadius;

        game.isDeathmatch = true;

        game.breakAllBeds();

        game.currentDeathmatchProgress = (double) game.deathmatchTime / (game.countdown - game.deathmatchStart);

        if (bossbar instanceof BossBar19) {
            BossBar19 bossbar19 = (BossBar19) bossbar;
            bossbar19.setColor(BarColor.PURPLE);
//            bossbar19.setMessage("Stage 5: Deathmatch");
            bossbar19.setMessage(i18nonly("event_deathmatch_bossbar"));
        }

        for (Player player : game.getConnectedPlayers()) {
            player.sendTitle("", ChatColor.GOLD + i18n("event_deathmatch_text", "Deathmatch!", false), 5, 40, 5);
            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1.0F, 1.0F);
        }

    }

    public static void deathmatchEnd(Game game) {

        game.isAnnihilation = true;

        Bukkit.getConsoleSender().sendMessage("deathmatch end");
        BossBar bossbar = (BossBar) game.statusbar;
        if (bossbar instanceof BossBar19) {
            BossBar19 bossbar19 = (BossBar19) bossbar;
            bossbar19.setColor(BarColor.PINK);
//            bossbar19.setMessage("Stage 6: Annihilation");
            bossbar19.setMessage(i18nonly("event_annihilation_bossbar"));
        }
        for (Player player : game.getConnectedPlayers()) {
            player.sendTitle("", ChatColor.GOLD + i18n("event_annihilation_text", "Annihilation!", false), 5, 40, 5);
            player.playSound(player.getLocation(), Sound.ENTITY_WITHER_DEATH, 1.0F, 1.0F);
        }
    }



    public static void processDeathmatch(Game game) {



        game.currentDeathmatchProgress = (game.getGameTime() - game.countdown - game.deathmatchStart) / (double) game.deathmatchTime;
        if (game.currentDeathmatchProgress > 1) game.currentDeathmatchProgress = 1;

        Location center = game.getCenter();

//        Bukkit.getConsoleSender().sendMessage("currentDeathmatchProgress " + game.currentDeathmatchProgress);
        game.currentFloor = game.arenaFloor + (game.deathmatchFloor - game.arenaFloor) * game.currentDeathmatchProgress;
//        Bukkit.getConsoleSender().sendMessage("currentFloor " + game.currentFloor);
        game.currentCeiling = game.arenaCeiling + (game.deathmatchCeiling - game.arenaCeiling) * game.currentDeathmatchProgress;
//        Bukkit.getConsoleSender().sendMessage("currentCeiling " + game.currentCeiling);
        game.currentRadius = game.deathmatchRadius + (game.arenaRadius - game.deathmatchRadius) * ( 1 - game.currentDeathmatchProgress );
//        Bukkit.getConsoleSender().sendMessage("currentRadius " + game.currentRadius);

        for (GamePlayer gamePlayer : game.getConnectedGamePlayers()) {
            if (!gamePlayer.isSpectator) {
                Location location = gamePlayer.player.getLocation();
                Location temp;

                boolean outside = false;

                temp = location.clone();
                temp.setY(game.currentCeiling); //up
                if (location.getY() > game.currentCeiling) {
                    gamePlayer.player.spawnParticle(Particle.SMOKE_LARGE, temp, 10, 2, 0, 2, 0.01);
                    outside = true;
                } else
                    gamePlayer.player.spawnParticle(Particle.END_ROD, temp, 10, 2, 0, 2, 0.01);

                temp = location.clone();
                temp.setY(game.currentFloor); //down
                if (location.getY() < game.currentFloor) {
                    gamePlayer.player.spawnParticle(Particle.SMOKE_LARGE, temp, 10, 2, 0, 2, 0.01);
                    outside = true;
                } else
                    gamePlayer.player.spawnParticle(Particle.END_ROD, temp, 10, 2, 0, 2, 0.01);

                
                
                temp = center.clone();
                temp.setY(location.getY()); //high x
                temp.setZ(location.getZ());
                temp.add(game.currentRadius, 0, 0);
                if (location.getX() > (center.getX() + game.currentRadius)) {
                    gamePlayer.player.spawnParticle(Particle.SMOKE_LARGE, temp, 10, 0, 2, 2, 0.01);
                    outside = true;
                    gamePlayer.player.setVelocity(new Vector(-deathmatchWallPushPower, 0 ,0));
                } else
                    gamePlayer.player.spawnParticle(Particle.END_ROD, temp, 10, 0, 2, 2, 0.01);

                temp = center.clone();
                temp.setY(location.getY()); //low x
                temp.setZ(location.getZ());
                temp.add(-game.currentRadius, 0, 0);
                if (location.getX() < (center.getX() - game.currentRadius)) {
                    gamePlayer.player.spawnParticle(Particle.SMOKE_LARGE, temp, 10, 0, 2, 2, 0.01);
                    outside = true;
                    gamePlayer.player.setVelocity(new Vector(deathmatchWallPushPower, 0 ,0));
                } else
                    gamePlayer.player.spawnParticle(Particle.END_ROD, temp, 10, 0, 2, 2, 0.01);

                temp = center.clone();
                temp.setY(location.getY()); //high z
                temp.setX(location.getX());
                temp.add(0, 0, game.currentRadius);
                if (location.getZ() > (center.getZ() + game.currentRadius)) {
                    gamePlayer.player.spawnParticle(Particle.SMOKE_LARGE, temp, 10, 2, 2, 0, 0.01);
                    outside = true;
                    gamePlayer.player.setVelocity(new Vector(0, 0 ,-deathmatchWallPushPower));
                } else
                    gamePlayer.player.spawnParticle(Particle.END_ROD, temp, 10, 2, 2, 0, 0.01);

                temp = center.clone();
                temp.setY(location.getY()); // low z
                temp.setX(location.getX());
                temp.add(0, 0, -game.currentRadius);
                if (location.getZ() < (center.getZ() - game.currentRadius)) {
                    gamePlayer.player.spawnParticle(Particle.SMOKE_LARGE, temp, 10, 2, 2, 0, 0.01);
                    outside = true;
                    gamePlayer.player.setVelocity(new Vector(0, 0 ,deathmatchWallPushPower));
                } else
                    gamePlayer.player.spawnParticle(Particle.END_ROD, temp, 10, 2, 2, 0, 0.01);

                if ( outside ) {
                    gamePlayer.player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 100, 0));
                    gamePlayer.player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 100, 0));
                    if (!game.isAnnihilation)
                    gamePlayer.player.setHealth( Math.min(gamePlayer.player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * ( 1 - game.currentDeathmatchProgress ) + 1, gamePlayer.player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() ) );
                    else if (gamePlayer.player.getHealth() > 1) gamePlayer.player.setHealth(1);
                    gamePlayer.player.playSound( location, Sound.ENTITY_PLAYER_HURT, 1.0F, 1.0F);
                }

            }
        }

    }

    public static void processAnnihilation(Game game) {

        for (GamePlayer gamePlayer : game.getConnectedGamePlayers()) {
            if (!gamePlayer.isSpectator) {
                gamePlayer.player.addPotionEffect( new PotionEffect(PotionEffectType.WITHER, 60, 1, false, false));
                gamePlayer.player.addPotionEffect( new PotionEffect(PotionEffectType.GLOWING, 60, 1, false, false));
            }
        }

    }

    public static void deathmatchWarning(Game game) {
        for (Player player : game.getConnectedPlayers()) {
            player.sendTitle("", ChatColor.GOLD + i18n("event_deathmatch_warning", "Deathmatch in 60 seconds!", false), 5, 40, 5);
            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 1.0F, 1.0F);
        }
    }

    public static ItemStack getAbilitiesMenuItemStack() {
        ItemStack itemStack = new ItemStack(Material.BLAZE_POWDER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(i18nonly("abilities_menu_item_name"));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
