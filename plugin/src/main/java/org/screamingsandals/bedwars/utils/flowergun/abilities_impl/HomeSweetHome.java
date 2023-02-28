package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;

import java.util.Random;

public class HomeSweetHome extends Ability implements IAbility {

    public HomeSweetHome(){//epic
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.IRON_PLATE, 8).addResource(ResourceType.ECHO_SHARD, 60).addResource(ResourceType.BLAZE_POWDER, 50).addResource(ResourceType.CATALYST_EPIC, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.IRON_PLATE, 20).addResource(ResourceType.ECHO_SHARD, 120).addResource(ResourceType.AMETHYST_SHARD, 150).addResource(ResourceType.EXP_CRYSTAL_LVL2, 5);

        this.name = "Дом Милый Дом";
        this.id = "home_sweet_home";
        this.item = Material.MAGMA_CREAM;
        this.rarity = 4;
        this.icon = IconType.GOLD_INGOT;
        this.description = "Использование гаджета Телепортации на базу#вернёт его игроку с шансом (values1)%, а также#даст эффект Сопротивления 1 и Скорости 3 на (values2) секунд.";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 70 + 10 * level;
    }

    @Override
    public int calculateIntValue2(int level) {
        return 300 + 100 * level;
    }

    @Override
    public String formatValue2(int level) {
        return "" + calculateIntValue2(level) / 20;
    }



    @Override
    public void gadgetUsed(int level, GamePlayer gamePlayer, GadgetType gadgetType, CompoundValueModifier compoundValueModifier) {
        if (gadgetType == GadgetType.TP) {
            Random random = new Random();
            int chance = calculateIntValue1(level);

            Player player = gamePlayer.player;
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, calculateIntValue2(level), 0, false, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, calculateIntValue2(level), 2, false, false));

            playFXDefensiveUtility(player, 1);
            notifyPlayerOnAbilityActivation(player);

            if ( random.nextInt(100) < chance ) {


                gamePlayer.getCustomGUIShopInstance().findItemById("tp").giveForFree(player);
                playFXItemGained(player, 1);
            }
        }
    }

}
