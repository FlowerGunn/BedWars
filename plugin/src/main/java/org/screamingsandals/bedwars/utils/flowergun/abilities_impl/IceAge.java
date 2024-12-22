package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;
import org.screamingsandals.bedwars.utils.flowergun.shoputils.PurchasableItem;

import java.util.ArrayList;
import java.util.Random;

public class IceAge extends Ability implements IAbility {

    public IceAge(){
        //legendary
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.ICE_POWDER, 100).addResource(ResourceType.CRYO_SLIME, 10).addResource(ResourceType.PROCESSING_UNIT, 1).addResource(ResourceType.CATALYST_LEGENDARY, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.ICE_POWDER, 250).addResource(ResourceType.CRYO_SLIME, 25).addResource(ResourceType.PROCESSING_UNIT, 3).addResource(ResourceType.EXP_CRYSTAL_LVL2, 12);

        this.name = "Ледниковый Период";
        this.id = "iceage";
        this.item = Material.BLUE_ICE;
        this.icon = IconType.ICE_POWDER;

        this.abilityCategories.add(AbilityCategory.MADMAN);
        this.abilityCategories.add(AbilityCategory.MANIPULATOR);
        this.abilityCategories.add(AbilityCategory.SCOUT);

        this.description = "Во вкладке Особое в магазине игроку#доступен блок синего льда. Ломание блока синего#льда наложит (values2) секунд Заморозки на всех#противников в радиусе (values1) блоков.";
        this.rarity = 5;
    }

    @Override
    public int calculateIntValue1(int level) {
        return 14 + level * 2;
    }

    @Override
    public int calculateIntValue2(int level) {
        return 60 + 20 * level;
    }


    @Override
    public String formatValue2(int level) {
        return FlowerUtils.singleDecimal.format(calculateIntValue2(level) / 20.0);
    }

    @Override
    public void blockBreak(int level, BlockBreakEvent event) {

        if (event.isCancelled()) return;

        if ( event.getBlock().getType() == Material.BLUE_ICE ) {

            event.getBlock().getLocation().getBlock().setType(Material.AIR);

            Game game = Main.getPlayerGameProfile(event.getPlayer()).getGame();

            Location center = event.getBlock().getLocation();

            center.getWorld().spawnParticle(Particle.END_ROD, center, 50, 0.5, 0.5, 0.5, 0.6);
            center.getWorld().spawnParticle(Particle.SNOWFLAKE, center, 50, 0.5, 0.5, 0.5, 0.6);
            center.getWorld().spawnParticle(Particle.SNOWBALL, center, 50, 0.5, 0.5, 0.5, 0.6);

            Random random = new Random();
            int radius = calculateIntValue1(level);
            for ( int i = 0; i < 100; i++ ) {
                Block block = center.getWorld().getBlockAt(center.clone().add(random.nextInt(-radius, radius+1),random.nextInt(-2, 3),random.nextInt(-radius, radius+1)));
                if ( block.getType() == Material.AIR && block.getRelative(BlockFace.DOWN).isSolid() ) {
                    block.setType(Material.SNOW);
                    game.getRegion().addBuiltDuringGame(block.getLocation());
                }
            }

            final ArrayList<GamePlayer> enemies = Ability.findEnemiesInRange(event.getPlayer(), radius);


            new BukkitRunnable() {
                int i = 0;
                @Override
                public void run() {
                    if ( i >= calculateIntValue2(level) ) this.cancel();
                    i += 10;
                    for ( GamePlayer enemy : enemies ) {
                        enemy.player.setFreezeTicks(160);
                    }
                    //event.getPlayer().setFreezeTicks(160);
                }
            }.runTaskTimer(Main.getInstance(), 0L, 10L);

            notifyPlayerOnAbilityActivation(event.getPlayer());

        }

    }

    @Override
    public void playerFirstSpawn(int level, GamePlayer gamePlayer) {

        gamePlayer.playerFlags.add(GameFlag.ICE);

    }

}
