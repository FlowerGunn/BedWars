package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CustomStatusEffect;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;

import java.util.ArrayList;

public class Adrenaline extends Ability implements IAbility {

    private ArrayList<DamageType> damageTypes;

    public Adrenaline(){
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.NETHERITE_SCRAP, 15).addResource(ResourceType.POLISHED_RUBY, 12).addResource(ResourceType.BLAZE_POWDER, 30).addResource(ResourceType.CATALYST_EPIC, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.NETHERITE_SCRAP, 50).addResource(ResourceType.POLISHED_RUBY, 30).addResource(ResourceType.BLAZE_POWDER, 100).addResource(ResourceType.EXP_CRYSTAL_LVL2, 5);

        this.name = "Адреналин";
        this.id = "adrenaline";
        this.item = Material.WITHER_SKELETON_SKULL;
        this.rarity = 4;
        this.icon = IconType.INCREASE_DAMAGE;
        this.description = "Урон от Огня, Яда, Иссушения,#Голода или Магии уменьшен на (values2)%#и даст игроку +20% к ближнему урону на (values1) секунды.";

        this.abilityCategories.add(AbilityCategory.MADMAN);
        this.abilityCategories.add(AbilityCategory.FIGHTER);
        this.abilityCategories.add(AbilityCategory.TANK);

        this.damageTypes = new ArrayList<>();
        damageTypes.add(DamageType.FIRE);
        damageTypes.add(DamageType.POISON);
        damageTypes.add(DamageType.WITHER);
        damageTypes.add(DamageType.STARVATION);
        damageTypes.add(DamageType.MAGIC);
    }

    @Override
    public int calculateIntValue1(int level) {
        return 20 + 10 * level;
    }

    @Override
    public int calculateIntValue2(int level) {
        return 40 + 10 * level;
    }

    @Override
    public String formatValue1(int level) {
        return FlowerUtils.singleDecimal.format(calculateIntValue1(level) / 20.0);
    }


    @Override
    public void playerReceiveDamage(int level, DamageInstance damageInstance, Player victim, EntityDamageEvent event, CompoundValueModifier compoundValueModifier) {

        if (event.isCancelled()) return;

        if (Main.isPlayerInGame(victim)) {
            if ( damageTypes.contains(damageInstance.damageType) ) {

                compoundValueModifier.addExp(calculateIntValue2(level) * -0.01);
                playFXDefensiveUtility(victim, 1);
//                notifyPlayerOnAbilityActivation(victim);
                GamePlayer gamePlayer = Main.getPlayerGameProfile(victim);
                gamePlayer.addCustomStatusEffect(new CustomStatusEffect("adrenaline_damage", gamePlayer, gamePlayer, CustomStatusEffectType.DAMAGE_DEALT, new DamageInstance( DamageSource.PLAYER, null, DamageRelay.MELEE, null), new CompoundValueModifier(0, 0,  20 * 0.01), calculateIntValue1(level), false));
                //victim.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, calculateIntValue1(level), 0));
            }
        }

    }

}
