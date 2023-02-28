package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CustomStatusEffect;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

public class Mirroring extends Ability implements IAbility {

    public Mirroring(){//legendary
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.AMETHYST_SHARD, 100).addResource(ResourceType.RUBY, 80).addResource(ResourceType.ECHO_DUST, 20).addResource(ResourceType.CATALYST_LEGENDARY, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.AMETHYST_SHARD, 300).addResource(ResourceType.RUBY, 200).addResource(ResourceType.ECHO_DUST, 50).addResource(ResourceType.EXP_CRYSTAL_LVL2, 12);

        this.name = "Отзеркаливание";
        this.id = "mirroring";
        this.item = Material.GLASS;
        this.rarity = 5;
        this.icon = IconType.ABSORPTION;
        this.description = "Убийства и помощи в убийстве увеличивают#максимальное здоровье игрока на 2ед. на (values1) секунд.#Новые заряды обновляют длительность.#Убийства и помощи в убийстве считаются#отдельными зарядами.";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 60 * 20 + 20 * 20 * level;
    }


    @Override
    public String formatValue1(int level) {
        return "" + calculateIntValue1(level) / 20;
    }

    @Override
    public void playerKill(int level, Player killer, PlayerDeathEvent event) {

        GamePlayer gKiller = Main.getPlayerGameProfile(killer);
        gKiller.addCustomStatusEffect(new CustomStatusEffect("mirror_kill", gKiller, gKiller, Attribute.GENERIC_MAX_HEALTH, new CompoundValueModifier(2, 0, 0), calculateIntValue1(level), false));
        notifyPlayerOnAbilityActivation(killer);

    };

    @Override
    public void playerKillAssist(int activeLevel, Player killer, Player victim, Player assistant) {

        GamePlayer gAssistant = Main.getPlayerGameProfile(assistant);
        gAssistant.addCustomStatusEffect(new CustomStatusEffect("mirror_assist", gAssistant, gAssistant, Attribute.GENERIC_MAX_HEALTH, new CompoundValueModifier(2, 0, 0), calculateIntValue1(activeLevel), false));
        notifyPlayerOnAbilityActivation(assistant);

    };

}
