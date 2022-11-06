package org.screamingsandals.bedwars.utils.flowergun.gameplay.abilities;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.Ability;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.enums.DamageRelay;

public class Levitator extends Ability implements IAbility {

    public Levitator(){
        this.name = "Левитатор";
        this.id = "levitator";
        this.icon = Material.WHITE_DYE;
        this.description = "Умирая от снарядов вы накладываете#Левитацию 2 на убийцу на (values1)&7 секунд";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 5 + level * 2;
    }


    @Override
    public void playerDeath(int level, PlayerDeathEvent event) {

        if ( event.getEntity().getKiller() != null ) {
            Player victim = event.getEntity();
            GamePlayer gVictim = Main.getPlayerGameProfile(victim);
            if ( gVictim.lastReceivedDamageInstance.damageRelay == DamageRelay.PROJECTILE ) {
                playFXCrowdControl(event.getEntity().getKiller(), 3);
                notifyPlayerOnAbilityActivation(victim);
                event.getEntity().getKiller().addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, calculateIntValue1(level) * 20, 1));
            }
        }

    }

}
