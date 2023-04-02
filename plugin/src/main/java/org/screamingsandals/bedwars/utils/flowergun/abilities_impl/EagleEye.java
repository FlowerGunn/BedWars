package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CustomStatusEffect;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;

public class EagleEye extends Ability implements IAbility {

    public EagleEye(){//rare
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.AMETHYST_SHARD, 35).addResource(ResourceType.RUBY, 20).addResource(ResourceType.ECHO_SHARD, 15).addResource(ResourceType.CATALYST_RARE, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.AMETHYST_SHARD, 70).addResource(ResourceType.RUBY, 50).addResource(ResourceType.ECHO_SHARD, 30).addResource(ResourceType.EXP_CRYSTAL_LVL2, 2);

        this.name = "Орлиный Глаз";
        this.id = "eagle_eye";
        this.item = Material.SPECTRAL_ARROW;
        this.rarity = 3;
        this.icon = IconType.BLINDNESS;

        this.abilityCategories.add(AbilityCategory.MANIPULATOR);
        this.abilityCategories.add(AbilityCategory.SUPPORT);

        this.description = "Попадание снарядом по противнику под эффектом#Свечения или Левитации снизит их защиту#на 3 ед. на (values1) секунд.";
        this.isOnCooldown = false;
    }

    @Override
    public int calculateIntValue1(int level) { return 20 + 40 * level; }


    @Override
    public String formatValue1(int level) {
        return "" + calculateIntValue1(level) / 20;
    }

    @Override
    public void playerDealDamage(int level, DamageInstance damageInstance, Player attacker, EntityDamageByEntityEvent event, CompoundValueModifier compoundValueModifier) {
        //Bukkit.getConsoleSender().sendMessage("player deal damage with " + attacker.getName());



        if ( event.getFinalDamage() > 0 && damageInstance.damageTarget == DamageTarget.PLAYER && damageInstance.damageRelay == DamageRelay.PROJECTILE)

            if (Main.isPlayerInGame(attacker)) {

                Player victim = (Player) event.getEntity();

                if ( victim.hasPotionEffect(PotionEffectType.LEVITATION) || victim.hasPotionEffect(PotionEffectType.GLOWING) ) {

                    GamePlayer gVictim = Main.getPlayerGameProfile(victim);
                    gVictim.addCustomStatusEffect(new CustomStatusEffect("eagle_eye", gVictim, Main.getPlayerGameProfile(attacker), Attribute.GENERIC_ARMOR, new CompoundValueModifier(-3, 0, 0), calculateIntValue1(level), false));
                    playFXDebuff(victim, 2);
                    notifyPlayerOnAbilityActivation(attacker);

                }
            }

    }

}
