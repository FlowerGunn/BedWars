package org.screamingsandals.bedwars.utils.flowergun.gameplay.abilities;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.Ability;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.enums.DamageInstance;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.enums.DamageSource;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.enums.DamageTarget;

import java.util.ArrayList;

public class Willpower extends Ability implements IAbility {

    public Willpower(){
        this.name = "Сила воли";
        this.id = "willpower";
        this.icon = Material.GLISTERING_MELON_SLICE;
        this.rarity = 5;
        this.description = "Блокирование атак игроков щитом восполнит ближайшему#игроку в радиусе (values1)&7 блоков#(values2)&7 единиц сытости и 1 единицу голода";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 5 + 2 * level;
    }
    @Override
    public double calculateDoubleValue1(int level) {
        return 0.5 + 0.1 * level;
    }

    @Override
    public String formatValue2(int level) {
        return FlowerUtils.singleDecimal.format(calculateDoubleValue1(level));
    }


    @Override
    public void playerReceiveDamage(int level, DamageInstance damageInstance, Player victim, EntityDamageEvent event, CompoundValueModifier compoundValueModifier) {

        if ( damageInstance.damageSource == DamageSource.PLAYER ) {
            Player defender = (Player) event.getEntity();
            Game game = Main.getPlayerGameProfile(defender).getGame();

            if (defender.isBlocking() && event.getFinalDamage() == 0) {
                ArrayList<GamePlayer> players = new ArrayList<>(game.getConnectedGamePlayers());
                GamePlayer gDefender = Main.getPlayerGameProfile(defender);
                players.remove(gDefender);
                GamePlayer chosenAlly = null;
                double minDistance = 1000;

                for ( GamePlayer gamePlayer : players ) {
                    if ( !gamePlayer.isSpectator && game.getPlayerTeam(gamePlayer) == game.getPlayerTeam(gDefender)) {
                        double distance = gamePlayer.player.getLocation().distance(gDefender.player.getLocation());
                        if ( distance < minDistance ) {
                            minDistance = distance;
                            chosenAlly = gamePlayer;
                        }
                    }
                }

                if (chosenAlly == null) return;

                if ( minDistance < calculateIntValue1(level) ) {
                    chosenAlly.player.setSaturation(chosenAlly.player.getSaturation() + calculateIntValue1(level));
                    chosenAlly.player.setFoodLevel(chosenAlly.player.getFoodLevel() + 1);
                    playFXHealing(defender, chosenAlly.player,1);
                }


            }
        }


    }

}
