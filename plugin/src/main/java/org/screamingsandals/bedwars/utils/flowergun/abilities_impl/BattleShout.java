package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CustomStatusEffect;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.AbilityCategory;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

import java.util.ArrayList;

public class BattleShout extends Ability implements IAbility {

    public BattleShout(){//epic
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.MAGIC_SILK, 5).addResource(ResourceType.EMERALD_DUST, 3).addResource(ResourceType.RUBY_LAMP, 1).addResource(ResourceType.CATALYST_EPIC, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.MAGIC_SILK, 20).addResource(ResourceType.EMERALD_DUST, 15).addResource(ResourceType.RUBY_LAMP, 4).addResource(ResourceType.EXP_CRYSTAL_LVL2, 5);

        this.name = "Боевой Крик";
        this.id = "battleshout";
        this.item = Material.GOAT_HORN;
        this.rarity = 4;
        this.icon = IconType.ABSORPTION;

        this.abilityCategories.add(AbilityCategory.HEALER);
        this.abilityCategories.add(AbilityCategory.MANIPULATOR);
        this.abilityCategories.add(AbilityCategory.SUPPORT);

        this.description = "Убийство противников даст всем союзникам#в радиусе 15 блоков 1 ед. щита. Помощь в убийстве#даст всем союзникам в радиусе 15 блоков#Регенерацию 1 на (values1) секунд.";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 120 + 40 * level;
    }


    @Override
    public String formatValue1(int level) {
        return "" + calculateIntValue1(level) / 20;
    }

    @Override
    public void playerKill(int level, Player victim, Player killer, PlayerDeathEvent event) {

        ArrayList<GamePlayer> allies = findAlliesInRange(killer, 15);
        allies.add(Main.getPlayerGameProfile(killer));
        notifyPlayerOnAbilityActivation(killer);

        for ( GamePlayer ally : allies ) {
            healOverhealth(killer, ally.player, 1);
        }

    };

    @Override
    public void playerKillAssist(int level, Player killer, Player victim, Player assistant) {

        ArrayList<GamePlayer> allies = findAlliesInRange(assistant, 15);
        allies.add(Main.getPlayerGameProfile(assistant));
        notifyPlayerOnAbilityActivation(assistant);

        for ( GamePlayer ally : allies ) {
            healRegen(assistant, ally.player, new PotionEffect(PotionEffectType.REGENERATION, calculateIntValue1(level), 0));
        }

    };

}
