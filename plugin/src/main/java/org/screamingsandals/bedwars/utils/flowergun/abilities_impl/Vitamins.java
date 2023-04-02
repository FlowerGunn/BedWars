package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
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

public class Vitamins extends Ability implements IAbility {

    public Vitamins(){//rare
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.RUBY, 20).addResource(ResourceType.MAGIC_DUST, 10).addResource(ResourceType.SLIMEBALL, 20).addResource(ResourceType.CATALYST_RARE, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.RUBY, 50).addResource(ResourceType.MAGIC_DUST, 20).addResource(ResourceType.SLIMEBALL, 50).addResource(ResourceType.EXP_CRYSTAL_LVL2, 2);

        this.name = "Витаминки";
        this.id = "vitamins";
        this.item = Material.GOLDEN_CARROT;
        this.rarity = 3;
        this.icon = IconType.SPEED;

        this.abilityCategories.add(AbilityCategory.SCOUT);
        this.abilityCategories.add(AbilityCategory.MANIPULATOR);

        this.description = "При съедании любой еды игрок восполнит#дополнительные (values2) ед. сытости и получит эффект#Спешки 1 на (values1) секунд. При наличии врага#в радиусе 10 блоков игрок получит эффекты#Скорости 1 и Прыгучести 2 на (values1) секунд.";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 60 + 40 * level;
    }

    @Override
    public double calculateDoubleValue1(int level) {
        return 0.4 + 0.1 * level;
    }


    @Override
    public String formatValue1(int level) {
        return "" + calculateIntValue1(level) / 20;
    }

    @Override
    public String formatValue2(int level) {
        return FlowerUtils.doubleDecimal.format( calculateDoubleValue1(level) );
    }

    @Override
    public void itemConsume(int level, Player player, PlayerItemConsumeEvent event) {

        if (event.isCancelled()) return;

        if (Main.isPlayerInGame(player)) {
            Ability.healFood(player, player, 0, calculateDoubleValue1(level));
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, calculateIntValue1(level), 0, false, false));

            if ( event.getItem().getType().isEdible() && isEnemyInRange(player, 10) ) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, calculateIntValue1(level), 0, false, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, calculateIntValue1(level), 1, false, false));
                playFXBuff(player, 2);
            } else {
                playFXBuff(player, 1);
            }
        }


    };

}
