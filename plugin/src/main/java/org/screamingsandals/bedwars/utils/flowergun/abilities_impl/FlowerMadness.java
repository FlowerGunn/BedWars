package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CustomStatusEffect;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;

import java.util.Random;

public class FlowerMadness extends Ability implements IAbility {

    public FlowerMadness(){
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.SCRAP, 100).addResource(ResourceType.CATALYST_RARE, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.SCRAP, 200).addResource(ResourceType.EXP_CRYSTAL_LVL2, 2);

        this.name = "Цветочное Безумие";
        this.id = "flowermadness";
        this.item = Material.BLUE_ORCHID;
        this.rarity = 7;
        this.icon = IconType.CAKE;

        this.abilityCategories.add(AbilityCategory.CLOWN);
        this.abilityCategories.add(AbilityCategory.MANIPULATOR);

        this.description = "Нанося урон противникам игрок#заполняет их инвентарь случайными цветами#в случайном количестве от (values1)#до (values2) штук за попадание.";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 10 + 5 * level;
    }

    @Override
    public int calculateIntValue2(int level) {
        return 20 + 5 * level;
    }

    @Override
    public void playerDealDamage(int level, DamageInstance damageInstance, Player attacker, EntityDamageByEntityEvent event, CompoundValueModifier compoundValueModifier) {

        if ( event.getFinalDamage() > 0 && !event.isCancelled() && damageInstance.damageTarget == DamageTarget.PLAYER ) {
            if (Main.isPlayerInGame(attacker)) {

                Random random = new Random();
                int a = random.nextInt(calculateIntValue1(level), calculateIntValue2(level));
                int b;
                for (int i = 0; i < a; i++) {
                    b = random.nextInt(FlowerUtils.flowers.size());
                    FlowerUtils.giveItemsToPlayerOverflow(new ItemStack(FlowerUtils.flowers.get(b)), (Player) event.getEntity());
                }

                playFXItemGained((Player) event.getEntity(), 3);

            }
        }
    }

}
