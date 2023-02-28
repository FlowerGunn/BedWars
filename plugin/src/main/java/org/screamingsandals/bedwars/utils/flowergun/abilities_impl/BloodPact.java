package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

public class BloodPact extends Ability implements IAbility {

    public BloodPact(){//legendary
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.CURSED_SILK, 15).addResource(ResourceType.MICROSCHEMA, 3).addResource(ResourceType.RUBY_LAMP, 5).addResource(ResourceType.CATALYST_LEGENDARY, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.CURSED_SILK, 40).addResource(ResourceType.MICROSCHEMA, 10).addResource(ResourceType.RUBY_LAMP, 10).addResource(ResourceType.EXP_CRYSTAL_LVL2, 12);

        this.name = "Кровавый Пакт";
        this.id = "bloodpact";
        this.item = Material.RED_CANDLE;
        this.rarity = 5;
        this.icon = IconType.REGENERATION;
        this.description = "Убийство противника восполнит игроку (values2) здоровья.#Убивая игрока противник получит (values2) здоровья.#При возрождении игрок получает Слабость 1 на (values1) секунд";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 1400 - level * 200;
    }

    @Override
    public double calculateDoubleValue1(int level) {
        return 2.5 + 0.5 * level;
    }

    @Override
    public String formatValue1(int level) {
        return "" + calculateIntValue1(level) / 20;
    }

    @Override
    public String formatValue2(int level) {
        return "" + FlowerUtils.singleDecimal.format( calculateDoubleValue1(level) );
    }

    @Override
    public void playerKill(int level, Player killer, PlayerDeathEvent event) {

        Ability.healHealth(event.getPlayer(),killer,calculateDoubleValue1(level));
        notifyPlayerOnAbilityActivation(killer);

    }

    @Override
    public void playerRespawn(int level, GamePlayer gamePlayer) {

        notifyPlayerOnAbilityActivation(gamePlayer.player);
        gamePlayer.player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, calculateIntValue1(level), 0));

    }

    @Override
    public void playerDeath(int level, PlayerDeathEvent event) {

        if ( event.getEntity().getKiller() != null ) {
            Player victim = event.getEntity();
            Player killer = event.getEntity().getKiller();

            Ability.healHealth(victim, killer, calculateDoubleValue1(level));
            notifyPlayerOnAbilityActivation(killer);

        }
    }

}
