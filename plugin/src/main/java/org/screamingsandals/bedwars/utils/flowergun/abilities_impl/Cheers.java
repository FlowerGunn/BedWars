package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;

public class Cheers extends Ability implements IAbility {

    public Cheers(){//rare
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.CHAIN, 10).addResource(ResourceType.POLISHED_RUBY, 10).addResource(ResourceType.RUBY, 20).addResource(ResourceType.CATALYST_RARE, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.CHAIN, 25).addResource(ResourceType.POLISHED_RUBY, 20).addResource(ResourceType.RUBY, 80).addResource(ResourceType.EXP_CRYSTAL_LVL2, 2);

        this.name = "Тост";
        this.id = "cheers";
        this.item = Material.HONEY_BOTTLE;
        this.rarity = 3;
        this.icon = IconType.REGENERATION;

        this.abilityCategories.add(AbilityCategory.HEALER);
        this.abilityCategories.add(AbilityCategory.SUPPORT);

        this.description = "При выпивании бутылки мёда и наличии союзника#в радиусе (values1) блоков оба игрока#получат эффект Регенерации 1 на (values2) секунд.";//#Владелец навыка теряет любые эффекты Регенерации#при получении урона от игроков.
    }

    @Override
    public int calculateIntValue1(int level) {
        return 14 + 2 * level;
    }

    @Override
    public int calculateIntValue2(int level) {
        return 80 + 40 * level;
    }


    @Override
    public String formatValue2(int level) {
        return FlowerUtils.singleDecimal.format(calculateIntValue2(level) / 20.0);
    }

    @Override
    public void itemConsume(int activeLevel, Player player, PlayerItemConsumeEvent event) {

        if (event.isCancelled()) return;

        if (Main.isPlayerInGame(player)) {
            if ( event.getItem().getType() == Material.HONEY_BOTTLE ) {

                GamePlayer chosenAlly = Ability.findClosestAlly(player, calculateIntValue1(activeLevel));

                if ( chosenAlly != null ) {

                    Ability.healRegen(player, chosenAlly.player, new PotionEffect(PotionEffectType.REGENERATION, calculateIntValue2(activeLevel), 0  ));
                    Ability.healRegen(player, player, new PotionEffect(PotionEffectType.REGENERATION, calculateIntValue2(activeLevel), 0  ));

//                    playFXHealing(player, chosenAlly.player,1);
//                    playFXHealing(player, player,1);

                }


            }
        }


    };

//    @Override
//    public void playerReceiveDamage(int level, DamageInstance damageInstance, Player victim, EntityDamageEvent event, CompoundValueModifier compoundValueModifier) {
//        //if (!(event instanceof EntityDamageByEntityEvent)) return;
//
//        if (event.isCancelled()) return;
//        if (Main.isPlayerInGame(victim)) {
//            if ( damageInstance.damageSource == DamageSource.PLAYER )
//            victim.removePotionEffect(PotionEffectType.REGENERATION);
//        }
//
//    }

}
