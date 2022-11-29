package org.screamingsandals.bedwars.utils.flowergun.gameplay.abilities;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.Ability;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.tools.IconType;
import org.screamingsandals.bedwars.utils.flowergun.tools.enums.DamageInstance;
import org.screamingsandals.bedwars.utils.flowergun.tools.enums.DamageTarget;
import org.screamingsandals.bedwars.utils.flowergun.tools.enums.DamageType;

public class Aromatherapy extends Ability implements IAbility {

    public Aromatherapy(){
        this.name = "Ароматерапия";
        this.id = "aromatherapy";
        this.item = Material.GUNPOWDER;
        this.rarity = 4;
        this.icon = IconType.REGENERATION;
        this.description = "Вы наносите на 50% меньше урона#используя феерверки. Союзники задетые вашими#феерверками исцеляются на (values1)&7 ед.";
    }

    @Override
    public double calculateDoubleValue1(int level) {
        return 0.8 + 0.4 * level;
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

                    Ability.heal( attacker, victim, calculateDoubleValue1(level) );

                }
                else {
                    compoundValueModifier.addExp( -0.5 );
                }

            }

    }

}
