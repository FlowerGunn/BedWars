package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;

public class Suffocator extends Ability implements IAbility {

    public Suffocator(){
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.SCRAP, 100).addResource(ResourceType.CATALYST_RARE, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.SCRAP, 200).addResource(ResourceType.EXP_CRYSTAL_LVL2, 2);

        this.name = "Душнила";
        this.id = "suffocator";
        this.item = Material.GLASS_BOTTLE;
        this.rarity = 7;
        this.icon = IconType.CAKE;

        this.abilityCategories.add(AbilityCategory.CLOWN);
        this.abilityCategories.add(AbilityCategory.MANIPULATOR);

        this.description = "Нанося урон противникам они получат 6 ед.#лечения и эффект Левитации 1 на (values1) секунд,#а игрок получит Отравление 1 и Медленное#Копание 3 на (values1) секунд.";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 60 + 20 * level;
    }

    @Override
    public String formatValue1(int level) {
        return "" + calculateIntValue1(level) / 20;
    }


    @Override
    public void playerDealDamage(int level, DamageInstance damageInstance, Player attacker, EntityDamageByEntityEvent event, CompoundValueModifier compoundValueModifier) {

        if ( event.getFinalDamage() > 0 && !event.isCancelled() && damageInstance.damageTarget == DamageTarget.PLAYER ) {
            if (Main.isPlayerInGame(attacker)) {

                Player victim = (Player) event.getEntity();
                playFXDebuff(victim, 1);
                healHealth(attacker, victim, 6);
                victim.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, calculateIntValue1(level), 0, false, false));
                attacker.addPotionEffect(new PotionEffect(PotionEffectType.POISON, calculateIntValue1(level), 0, false, false));
            }
        }
    }

}
