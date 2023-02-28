package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;

import java.util.ArrayList;

public class Willpower extends Ability implements IAbility {

    public Willpower(){//legendary
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.POLISHED_RUBY, 30).addResource(ResourceType.ICE_POWDER, 20).addResource(ResourceType.LAPIS_SHEET, 15).addResource(ResourceType.CATALYST_LEGENDARY, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.POLISHED_RUBY, 70).addResource(ResourceType.ICE_POWDER, 80).addResource(ResourceType.LAPIS_SHEET, 40).addResource(ResourceType.EXP_CRYSTAL_LVL2, 12);

        this.name = "Сила воли";
        this.id = "willpower";
        this.item = Material.GLISTERING_MELON_SLICE;
        this.rarity = 5;
        this.icon = IconType.REGENERATION;
        this.description = "Получение урона от противников#или нанесение урона противникам в ближнем бою#восполнит ближайшему союзнику в радиусе (values1) блоков#(values2) единиц сытости и 1 единицу голода.";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 5 + 5 * level;
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
        if (event.isCancelled()) return;
        if ( damageInstance.damageSource == DamageSource.PLAYER ) {
            Player defender = victim;
            if (event.getFinalDamage() != 0) {
                proc(defender, level);
            }
        }
    }

    @Override
    public void playerDealDamage(int level, DamageInstance damageInstance, Player attacker, EntityDamageByEntityEvent event, CompoundValueModifier compoundValueModifier) {
        if (event.isCancelled()) return;
        if ( damageInstance.damageTarget == DamageTarget.PLAYER ) {
            Player defender = (Player) event.getEntity();
            if (event.getFinalDamage() != 0) {
                proc(attacker, level);
            }
        }
    }


    private void proc(Player defender, int level) {

        Game game = Main.getPlayerGameProfile(defender).getGame();
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

            healFood(defender, chosenAlly.player, 1, calculateDoubleValue1(level));

        }
    }

}
