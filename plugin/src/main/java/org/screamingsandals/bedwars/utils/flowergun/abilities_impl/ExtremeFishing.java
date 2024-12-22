package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CustomStatusEffect;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.AbilityCategory;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

public class ExtremeFishing extends Ability implements IAbility {


    public ExtremeFishing(){//legendary
        //legendary
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.SILK_COCOON, 500).addResource(ResourceType.POLISHED_RUBY, 20).addResource(ResourceType.PROCESSING_UNIT, 1).addResource(ResourceType.CATALYST_LEGENDARY, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.SILK_COCOON, 1000).addResource(ResourceType.POLISHED_RUBY, 50).addResource(ResourceType.PROCESSING_UNIT, 3).addResource(ResourceType.EXP_CRYSTAL_LVL2, 12);

        this.name = "Кукловод";
        this.id = "puppeteer";
        this.item = Material.FISHING_ROD;
        this.rarity = 5;
        this.icon = IconType.SILK_COCOON;

        this.abilityCategories.add(AbilityCategory.MANIPULATOR);
        this.abilityCategories.add(AbilityCategory.HEALER);
        this.abilityCategories.add(AbilityCategory.SUPPORT);

        this.description = "Имея на крючке удочки любого игрока,#оба игрока будут получать (values1) ед. лечения#каждую секунду. При возвращении удочки#целевой игрок получит эффекты Левитации 2#на 3 секунды и Медленного Падения на 6 секунд.";

    }

    @Override
    public double calculateDoubleValue1(int level) {
        return 0.3 + 0.1 * level;
    }

    @Override
    public String formatValue1(int level) {
        return FlowerUtils.singleDecimal.format(calculateDoubleValue1(level));
    }


    @Override
    public void fishAction(int activeLevel, Player player, PlayerFishEvent event) {
        if ( event.getState() == PlayerFishEvent.State.CAUGHT_ENTITY ) {
            if ( event.getCaught() instanceof Player target ) {
                target.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 60, 1, false, false));
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 120, 0, false, false));
            }
        }
    }

    @Override
    public void projectileHit(int level, final Player shooter, ProjectileHitEvent event) {
//        Bukkit.getConsoleSender().sendMessage("projectile1");


        if ( event.isCancelled() ) return;
//        Bukkit.getConsoleSender().sendMessage("projectile2");
        if ( event.getEntity() instanceof FishHook ) {
//            Bukkit.getConsoleSender().sendMessage("projectile3");
            if ( shooter.getFishHook() == null) return;
            FishHook hook = shooter.getFishHook();
//            Bukkit.getConsoleSender().sendMessage("projectile4");
            if ( !(event.getHitEntity() instanceof Player)) return;
//            Bukkit.getConsoleSender().sendMessage("projectile5");
            final Player target = (Player) event.getHitEntity();
//            GamePlayer gTarget = Main.getPlayerGameProfile(target);
//            if (gTarget == null) return;

            new BukkitRunnable() {
                @Override
                public void run() {
                    if ( shooter.getFishHook() == null ) {
                        this.cancel();
                    }
//                    if ( shooter.getFishHook() != null && shooter.getFishHook().getHookedEntity() != target ) {
//                        this.cancel();
//                    }
                    if ( target.getGameMode() != GameMode.SURVIVAL ) this.cancel();

                    if ( !this.isCancelled() ) {
//                        playLineFX(shooter.getLocation().add(0, 0.5, 0), target.getLocation().add(0, 0.5, 0), Color.RED);
                        healHealth(shooter, target, calculateDoubleValue1(level));
                        healHealth(shooter, shooter, calculateDoubleValue1(level));
//                        target.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60, 0, false, false));
//                        shooter.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60, 0, false, false));
                    }
                }
            }.runTaskTimer(Main.getInstance(), 10L, 20L);

        }

    }


}
