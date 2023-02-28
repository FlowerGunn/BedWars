package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;

public class Aromatherapy extends Ability implements IAbility {

    public Aromatherapy(){
        //epic
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.MAGIC_SILK, 5).addResource(ResourceType.EMERALD_DUST, 3).addResource(ResourceType.RUBY_LAMP, 1).addResource(ResourceType.CATALYST_EPIC, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.MAGIC_SILK, 20).addResource(ResourceType.EMERALD_DUST, 15).addResource(ResourceType.RUBY_LAMP, 4).addResource(ResourceType.EXP_CRYSTAL_LVL2, 5);

        this.name = "Ароматерапия";
        this.id = "aromatherapy";
        this.item = Material.GUNPOWDER;
        this.rarity = 4;
        this.icon = IconType.REGENERATION;
        this.description = "Игрок наносит на 50% меньше урона#используя феерверки. Союзники задетые феерверками игрока#исцеляются на (values1) ед.";
    }

    @Override
    public double calculateDoubleValue1(int level) {
        return 0.8 + 0.2 * level;
    }

    @Override
    public String formatValue1(int level) {
        return "" + FlowerUtils.singleDecimal.format( calculateDoubleValue1(level) );
    }



    public void playerDealDamage(int level, DamageInstance damageInstance, Player attacker, EntityDamageByEntityEvent event, CompoundValueModifier compoundValueModifier) {
//        Bukkit.getConsoleSender().sendMessage("firework deal damage from " + attacker.getName());

        if ( damageInstance.damageType == DamageType.FIREWORK && damageInstance.damageTarget == DamageTarget.PLAYER )

            if (Main.isPlayerInGame(attacker)) {

                GamePlayer gAttacker = Main.getPlayerGameProfile(attacker);
                Player victim = (Player) event.getEntity();
                GamePlayer gVictim = Main.getPlayerGameProfile(victim);

                Game game = gAttacker.getGame();

                if ( game.getPlayerTeam(gAttacker) == game.getPlayerTeam(gVictim) ) {

                    Ability.healHealth( attacker, victim, calculateDoubleValue1(level) );

                }
                else {
                    compoundValueModifier.addExp( -0.5 );
                }

            }

    }

}
