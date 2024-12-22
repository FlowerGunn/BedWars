package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CustomStatusEffect;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;

public class BigCalliber extends Ability implements IAbility {

    public BigCalliber(){
        //epic
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.RUBY_LAMP, 2).addResource(ResourceType.REINFORCED_BONE_PLATE, 6).addResource(ResourceType.EMERALD_PLATE, 2).addResource(ResourceType.CATALYST_EPIC, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.RUBY_LAMP, 6).addResource(ResourceType.REINFORCED_BONE_PLATE, 15).addResource(ResourceType.EMERALD_PLATE, 4).addResource(ResourceType.EXP_CRYSTAL_LVL2, 5);

        this.name = "Большой Калибр";
        this.id = "bigcalliber";
        this.item = Material.GUNPOWDER;
        this.rarity = 4;
        this.icon = IconType.INCREASE_DAMAGE;

        this.abilityCategories.add(AbilityCategory.PYROTECHNIC);
        this.abilityCategories.add(AbilityCategory.RANGER);

        this.description = "Игрок наносит +(values1) ед. урона#используя фейерверки, однако замедляется#на 50% при выстреливании фейерверков.";
    }

    @Override
    public double calculateDoubleValue1(int level) {
        return 3 + 0.5 * level;
    }

    @Override
    public String formatValue1(int level) {
        return FlowerUtils.doubleDecimal.format( calculateDoubleValue1(level)  );
    }



    public void playerDealDamage(int level, DamageInstance damageInstance, Player attacker, EntityDamageByEntityEvent event, CompoundValueModifier compoundValueModifier) {
//        Bukkit.getConsoleSender().sendMessage("firework deal damage from " + attacker.getName());

        if ( event.isCancelled() ) return;

        if ( damageInstance.damageType == DamageType.FIREWORK && damageInstance.damageTarget == DamageTarget.PLAYER )

            if (Main.isPlayerInGame(attacker)) {
//                Bukkit.getConsoleSender().sendMessage("increase " + attacker.getName());
                compoundValueModifier.addDouble( calculateIntValue1(level) );

            }

    }

    public void projectileLaunch(int level, Player shooter, ProjectileLaunchEvent event) {

        if ( event.isCancelled() ) return;

        if ( event.getEntity() instanceof Firework ) {
            GamePlayer gAttacker = Main.getPlayerGameProfile(shooter);
            gAttacker.addCustomStatusEffect(new CustomStatusEffect("bigcalliber_slow", gAttacker, gAttacker, Attribute.GENERIC_MOVEMENT_SPEED, new CompoundValueModifier(0, 0,  -0.5), 60, false));

        }

    }

}
