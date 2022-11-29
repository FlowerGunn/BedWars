package org.screamingsandals.bedwars.utils.flowergun.gameplay.abilities;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.Ability;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.tools.IconType;

public class BloodPact extends Ability implements IAbility {

    public BloodPact(){
        this.name = "Кровавый Пакт";
        this.id = "bloodpact";
        this.item = Material.RED_CANDLE;
        this.rarity = 5;
        this.icon = IconType.REGENERATION;
        this.description = "Убийство противника восполнит вам (values2)&7 здоровья.#Убивая вас противник получит (values2)&7 здоровья.#При возрождении вы получаете Слабость 1 на (values1)&7 секунд";
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

        Ability.heal(event.getPlayer(),killer,calculateDoubleValue1(level));
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

            Ability.heal(victim, killer, calculateDoubleValue1(level));
            notifyPlayerOnAbilityActivation(killer);

        }
    }

}
