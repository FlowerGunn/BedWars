package org.screamingsandals.bedwars.utils.flowergun.gameplay.abilities;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.Ability;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.tools.IconType;
import org.screamingsandals.bedwars.utils.flowergun.tools.enums.DamageInstance;

public class SlimeShell extends Ability implements IAbility {

    public SlimeShell(){
        this.name = "Слизневая оболочка";
        this.id = "slimeshell";
        this.item = Material.SLIME_BALL;
        this.rarity = 3;
        this.icon = IconType.DAMAGE_RESISTANCE;
        this.description = "Вы получаете на (values1)&7% меньше урона,#когда находитесь под эффектом Замедления";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 10 + 3 * level;
    }



    @Override
    public void playerReceiveDamage(int level, DamageInstance damageInstance, Player victim, EntityDamageEvent event, CompoundValueModifier compoundValueModifier) {
//        if (!(event instanceof EntityDamageByEntityEvent)) return;

        if (event.isCancelled()) return;

//        GamePlayer gVictim = Main.getPlayerGameProfile(victim);
//        gVictim.addCustomStatusEffect(new CustomStatusEffect("test", gVictim, gVictim, Attribute.GENERIC_MAX_HEALTH, new CompoundValueModifier(4, 0, 0), 200, false));

//        EntityDamageByEntityEvent tempEvent = (EntityDamageByEntityEvent) event;
//        Bukkit.getConsoleSender().sendMessage("player receive damage from " + ((EntityDamageByEntityEvent) event).getDamager().getName() + "   source = " + damageSource);
        if (Main.isPlayerInGame(victim)) {
            if (victim.getPotionEffect(PotionEffectType.SLOW) != null) {
                playFXDefensiveUtility(victim,1);
                notifyPlayerOnAbilityActivation(victim);
                compoundValueModifier.addExp(-1 * calculateIntValue1(level) / 100.0);
            }
        }

    }

}
