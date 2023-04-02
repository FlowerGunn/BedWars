package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.managers.ColoursManager;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;

import java.util.Random;

public class Amogus extends Ability implements IAbility {

    public Amogus(){
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.SCRAP, 100).addResource(ResourceType.CATALYST_RARE, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.SCRAP, 200).addResource(ResourceType.EXP_CRYSTAL_LVL2, 2);

        this.name = "ඞ";
        this.id = "amogus";
        this.item = Material.BARRIER;
        this.rarity = 7;
        this.icon = IconType.CAKE;

        this.abilityCategories.add(AbilityCategory.CLOWN);
        this.abilityCategories.add(AbilityCategory.MANIPULATOR);

        this.description = "Нанося урон противникам игрок#отправляет на экран противника#Амогуса на (values1) секунд.";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 100 + 100 * level;
    }

    @Override
    public String formatValue1(int level) {
        return "" + calculateIntValue1(level) / 20;
    }


    @Override
    public void playerDealDamage(int level, DamageInstance damageInstance, Player attacker, EntityDamageByEntityEvent event, CompoundValueModifier compoundValueModifier) {

        if ( event.getFinalDamage() > 0 && !event.isCancelled() && damageInstance.damageTarget == DamageTarget.PLAYER ) {
            if (Main.isPlayerInGame(attacker)) {

                GamePlayer gAttacker = Main.getPlayerGameProfile(attacker);

                Player victim = (Player) event.getEntity();
                victim.sendTitle( gAttacker.game.getPlayerTeam(gAttacker).teamInfo.color.chatColor + "" + ChatColor.BOLD + "ඞ", "", 10, calculateIntValue1(level) ,10);

            }
        }
    }

}
