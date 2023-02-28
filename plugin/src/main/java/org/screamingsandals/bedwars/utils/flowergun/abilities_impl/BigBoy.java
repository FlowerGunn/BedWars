package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;


import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CustomStatusEffect;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

public class BigBoy extends Ability implements IAbility{


    private boolean used;

    public BigBoy(){//rare
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.SCRAP, 150).addResource(ResourceType.BOLT, 15).addResource(ResourceType.POLISHED_RUBY, 20).addResource(ResourceType.CATALYST_RARE, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.SCRAP, 300).addResource(ResourceType.BOLT, 40).addResource(ResourceType.POLISHED_RUBY, 40).addResource(ResourceType.EXP_CRYSTAL_LVL2, 2);

        this.name = "Здоровяк";
        this.id = "bigboy";
        this.item = Material.ENDER_PEARL;
        this.iaId = "anicloud:polished_ruby";
        this.rarity = 3;
        this.icon = IconType.ABSORPTION;
        this.description = "Игрок получает +(values1) к макс. здоровью#и -(values2)% скорости на всю игру";
        this.used = false;
    }

    @Override
    public double calculateDoubleValue1(int level) {
        return 2 + level * 0.5;
    }

    @Override
    public int calculateIntValue1(int level) {
        return 35 - 5 * level;
    }

    @Override
    public String formatValue1(int level) {
        return "" + FlowerUtils.singleDecimal.format( calculateDoubleValue1(level) );
    }

    @Override
    public String formatValue2(int level) {
        return "" + calculateIntValue1(level);
    }

    @Override
    public void playerFirstSpawn(int level, GamePlayer gamePlayer) {

        gamePlayer.addCustomStatusEffect(new CustomStatusEffect("bigboy_slow", gamePlayer, gamePlayer, Attribute.GENERIC_MOVEMENT_SPEED, new CompoundValueModifier(0, 0, calculateIntValue1(level) * -0.01), 30, true));
        gamePlayer.addCustomStatusEffect(new CustomStatusEffect("bigboy_hp", gamePlayer, gamePlayer, Attribute.GENERIC_MAX_HEALTH, new CompoundValueModifier( 0, calculateDoubleValue1(level), 0), 30, true));


    }
}
