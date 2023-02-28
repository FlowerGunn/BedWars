package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.GadgetType;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

import java.util.Random;

public class Acrobatics extends Ability implements IAbility {

    public Acrobatics(){
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.MAGIC_DUST, 4).addResource(ResourceType.ICE_POWDER, 10).addResource(ResourceType.SLIMEBALL, 20).addResource(ResourceType.CATALYST_RARE, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.MAGIC_DUST, 15).addResource(ResourceType.ICE_POWDER, 80).addResource(ResourceType.SLIMEBALL, 120).addResource(ResourceType.EXP_CRYSTAL_LVL2, 2);

        this.name = "Акробат";
        this.id = "acrobatics";
        this.item = Material.SLIME_BLOCK;
        this.rarity = 3;
        this.icon = IconType.SLOW_FALLING;
        this.description = "Использование гаджета Трамплин наложит на игрока#эффекты Медленного падения на (values1) секунд#и Скорости 3 на (values2) секунд.";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 40 + 20 * level;
    }

    @Override
    public int calculateIntValue2(int level) {
        return 80 + 40 * level;
    }

    @Override
    public String formatValue1(int level) {
        return "" + calculateIntValue1(level) / 20;
    }

    @Override
    public String formatValue2(int level) {
        return "" + calculateIntValue2(level) / 20;
    }



    @Override
    public void gadgetUsed(int level, GamePlayer gamePlayer, GadgetType gadgetType, CompoundValueModifier compoundValueModifier) {
        if (gadgetType == GadgetType.TRAMPOLINE) {

            Player player = gamePlayer.player;

            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, calculateIntValue1(level), 0, false, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, calculateIntValue2(level), 2, false, false));

            playFXSpeed(player, 1);
        }
    }

}
