package org.screamingsandals.bedwars.utils.flowergun.gameplay.abilities;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.Ability;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.CustomStatusEffect;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.tools.IconType;

public class Mirroring extends Ability implements IAbility {

    public Mirroring(){
        this.name = "Отзеркаливание";
        this.id = "mirroring";
        this.item = Material.GLASS;
        this.rarity = 5;
        this.icon = IconType.ABSORPTION;
        this.description = "Убийства и помощи в убийстве увеличивают#ваше максимальное здоровье на &f1ед.&7 на (values1)&7 секунд.#Новые заряды обновляют длительность.#Убийства и помощи в убийстве считаются#отдельными зарядами.";
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
        gKiller.addCustomStatusEffect(new CustomStatusEffect("mirror_kill", gKiller, gKiller, Attribute.GENERIC_MAX_HEALTH, new CompoundValueModifier(1, 0, 0), calculateIntValue1(level), false));
        notifyPlayerOnAbilityActivation(killer);

    };

    @Override
    public void playerKillAssist(int activeLevel, Player killer, Player victim, Player assistant) {

        GamePlayer gAssistant = Main.getPlayerGameProfile(assistant);
        gAssistant.addCustomStatusEffect(new CustomStatusEffect("mirror_assist", gAssistant, gAssistant, Attribute.GENERIC_MAX_HEALTH, new CompoundValueModifier(1, 0, 0), calculateIntValue1(activeLevel), false));
        notifyPlayerOnAbilityActivation(assistant);

    };

}
