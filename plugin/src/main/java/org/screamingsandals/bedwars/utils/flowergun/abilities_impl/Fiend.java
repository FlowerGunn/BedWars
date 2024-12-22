package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
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
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CustomStatusEffect;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;

import java.util.ArrayList;

public class Fiend extends Ability implements IAbility {

    private double radius;

    public Fiend(){//epic
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.CURSED_SILK, 8).addResource(ResourceType.BONE_PLATE, 10).addResource(ResourceType.ANOMALY, 1).addResource(ResourceType.CATALYST_EPIC, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.CURSED_SILK, 20).addResource(ResourceType.BONE_PLATE, 30).addResource(ResourceType.ANOMALY, 4).addResource(ResourceType.EXP_CRYSTAL_LVL2, 5);

        this.name = "Исчадие";
        this.id = "fiend";
        this.item = Material.CHORUS_FRUIT;
        this.rarity = 4;
        this.icon = IconType.DARKNESS;

        this.abilityCategories.add(AbilityCategory.MANIPULATOR);
        this.abilityCategories.add(AbilityCategory.BULLDOZER);
        this.abilityCategories.add(AbilityCategory.MADMAN);

        this.description = "При съедании цветка хоруса все враги в радиусе#10 блоков получат эффекты Темноты,#и потеряют 3 ед. брони на (values1) секунд.";

        this.radius = 10;

    }

    @Override
    public int calculateIntValue1(int level) {
        return 60 + 40 * level;
    }

    @Override
    public String formatValue1(int level) {
        return "" + calculateIntValue1(level) / 20;
    }

    @Override
    public void itemConsume(int activeLevel, Player player, PlayerItemConsumeEvent event) {

        if (event.isCancelled()) return;

        if (Main.isPlayerInGame(player)) {
            if ( event.getItem().getType() == Material.CHORUS_FRUIT ) {

                ArrayList<GamePlayer> enemies = Ability.findEnemiesInRange(player, this.radius);

                if ( enemies.size() > 0 ) {
                    for ( GamePlayer enemy : enemies ) {
                        enemy.player.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, calculateIntValue1(activeLevel), 0, false, false));
//                        enemy.player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, calculateIntValue1(activeLevel), 0, false, false));
                        enemy.addCustomStatusEffect(new CustomStatusEffect("fiend_max_health", Main.getPlayerGameProfile(player), enemy, Attribute.GENERIC_ARMOR, new CompoundValueModifier(-3, 0, 0), calculateIntValue1(activeLevel), false));
                        playFXDebuff(enemy.player, 2);
                    }
                    notifyPlayerOnAbilityActivation(player);
                }
            }
        }
    };


}
