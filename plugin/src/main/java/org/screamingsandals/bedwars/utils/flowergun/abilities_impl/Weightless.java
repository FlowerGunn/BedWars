package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;

import java.util.Random;

public class Weightless extends Ability implements IAbility {

    public Weightless(){//legendary
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.MAGIC_SILK, 20).addResource(ResourceType.EMERALD_PLATE, 5).addResource(ResourceType.ANOMALY, 7).addResource(ResourceType.CATALYST_LEGENDARY, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.MAGIC_SILK, 50).addResource(ResourceType.EMERALD_PLATE, 15).addResource(ResourceType.ANOMALY, 15).addResource(ResourceType.EXP_CRYSTAL_LVL2, 12);

        this.name = "Невесомость";
        this.id = "weightless";
        this.item = Material.FEATHER;
        this.rarity = 5;
        this.icon = IconType.LEVITATION;

        this.abilityCategories.add(AbilityCategory.SCOUT);
        this.abilityCategories.add(AbilityCategory.FIGHTER);

        this.description = "При возрождении, убийстве, помощи в убийстве или ломании#кровати игрок получает эффекты Скорости 1, Прыгучести 1#и Медленного Падения на (values1) секунд. При наличии#на игроке эффектов Прыгучести или Скорости сила#обновлённых эффектов будет удвоена.";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 60 + 20 * level;
    }


    @Override
    public String formatValue1(int level) {
        return "" + calculateIntValue1(level);
    }


//    @Override
//    public int calculateIntValue2(int level) {
//        return 20*60*4 - 20*30 * (level - 1);
//    }

//    @Override
//    public String formatValue2(int level) {
//        return FlowerUtils.singleDecimal.format(calculateIntValue2(level) / 20.0 / 60.0);
//    }

    @Override
    public void playerKill(int level, Player victim, Player killer, PlayerDeathEvent event) {

        trigger(killer,level);
        
//        if (this.isOnCooldown) return;
//
//        Random random = new Random();
//
//        if ( random.nextBoolean() ) {
//            killer.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, calculateIntValue1(level), 0, false, false));
//        } else {
//            killer.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, calculateIntValue1(level), 2, false, false));
//        }
//
//        playFXSpeed(killer, 2);
//        notifyPlayerOnAbilityActivation(killer);

    }
    @Override
    public void playerFirstSpawn(int level, GamePlayer gamePlayer) {
        if ( Main.isPlayerInGame(gamePlayer.player) ) {
            trigger(gamePlayer.player, level);
        }
    }

    @Override
    public void playerRespawn(int level, GamePlayer gamePlayer) {
        if ( Main.isPlayerInGame(gamePlayer.player) ) {
            trigger(gamePlayer.player, level);
        }
    }

    private void trigger(Player player, int level) {

        if ( Main.isPlayerInGame(player) ) {

            int extra = 0;
            if ( player.hasPotionEffect(PotionEffectType.JUMP) || player.hasPotionEffect(PotionEffectType.SPEED) ) extra = 1;

            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, calculateIntValue1(level) * 20, extra, false, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, calculateIntValue1(level) * 20, extra, false, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, calculateIntValue1(level) * 20, 0, false, false));
            playFXSpeed(player, 2);
            notifyPlayerOnAbilityActivation(player);
        }

    }

    @Override
    public void blockBreak(int level, BlockBreakEvent event) {

        if (event.isCancelled()) return;
        String material = event.getBlock().getType().toString();

        if (material.contains("BED") ) {

            trigger(event.getPlayer(), level);

        }

    }

    public void playerKillAssist(int level, Player killer, Player victim, Player assistant) {

        trigger(assistant,level);

    }

        @Override
    public void playerReceiveDamage(int level, DamageInstance damageInstance, Player victim, EntityDamageEvent event, CompoundValueModifier compoundValueModifier) {
//
//        if (this.isOnCooldown) return;


//        Bukkit.getConsoleSender().sendMessage("player receive damage from " + ((EntityDamageByEntityEvent) event).getDamager().getName() + "   source = " + damageSource);
//        if (Main.isPlayerInGame(victim)) {
//
//            if ( victim.hasPotionEffect(PotionEffectType.LEVITATION) ) {
//                int duration = victim.getPotionEffect(PotionEffectType.LEVITATION).getDuration();
//                victim.addPotionEffect(victim.getPotionEffect(PotionEffectType.LEVITATION).withDuration(duration + 40));
//            }
//
//        }
    }
}
