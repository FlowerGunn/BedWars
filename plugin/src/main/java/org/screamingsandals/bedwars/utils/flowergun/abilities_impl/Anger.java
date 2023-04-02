package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;

import static org.screamingsandals.bedwars.lib.lang.I.i18nonly;

public class Anger extends Ability implements IAbility {

    public Anger(){//rare
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.SCRAP, 250).addResource(ResourceType.COPPER_PLATE, 6).addResource(ResourceType.BLAZE_POWDER, 20).addResource(ResourceType.CATALYST_RARE, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.SCRAP, 500).addResource(ResourceType.COPPER_PLATE, 16).addResource(ResourceType.BLAZE_POWDER, 50).addResource(ResourceType.EXP_CRYSTAL_LVL2, 2);

        this.name = "Злоба";
        this.id = "anger";
        this.item = Material.BEE_NEST;
        this.rarity = 3;
        this.icon = IconType.INCREASE_DAMAGE;

        this.abilityCategories.add(AbilityCategory.FIGHTER);

        this.description = "Когда игрока убивает противник, игрок начинает#сильно злиться. Под статусом Злобы игрок наносит#на (values1)% больше урона своему убийце или на (values2)%#больше любому союзнику убийцы. Злоба кончается#когда игрок совершает любое убийство.";
        this.revengeTarget = null;
    }

    private GamePlayer revengeTarget;

    @Override
    public int calculateIntValue1(int level) {
        return 17 + 3 * level;
    }

    @Override
    public int calculateIntValue2(int level) {
        return 10 + 2 * level;
    }


    @Override
    public void playerDeath(int level, Player victim, Player killer, PlayerDeathEvent event) {

        if ( killer != null ) {
            this.revengeTarget = Main.getPlayerGameProfile(killer);
            notifyPlayerOnAbilityActivation(victim);
        }

    }

    @Override
    public void playerDealDamage(int level, DamageInstance damageInstance, Player attacker, EntityDamageByEntityEvent event, CompoundValueModifier compoundValueModifier) {

        if (event.isCancelled()) return;

        GamePlayer gAttacker = Main.getPlayerGameProfile(attacker);

        if ( damageInstance.damageTarget == DamageTarget.PLAYER ) {

            GamePlayer gVictim = Main.getPlayerGameProfile((Player) event.getEntity());
            Game game = gAttacker.getGame();

            if ( gVictim == this.revengeTarget ) {
                compoundValueModifier.addExp(calculateIntValue1(level) * 0.01 );
                playFXDamage(gVictim.player, 2);
            } else if ( game.getPlayerTeam(gAttacker) == game.getPlayerTeam(gVictim) ) {
                compoundValueModifier.addExp(calculateIntValue2(level) * 0.01 );
                playFXDamage(gVictim.player, 1);
            }

        }

    }

    @Override
    public void playerKill(int level, Player victim, Player killer, PlayerDeathEvent event)  {

        GamePlayer gVictim = Main.getPlayerGameProfile(victim);

        this.revengeTarget = null;

    }

}
