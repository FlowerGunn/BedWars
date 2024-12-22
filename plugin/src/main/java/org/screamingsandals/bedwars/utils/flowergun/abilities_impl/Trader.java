package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.AbilityCategory;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.DamageInstance;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;
import org.screamingsandals.bedwars.utils.flowergun.shoputils.PurchasableItem;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;

import java.util.Random;

public class Trader extends Ability implements IAbility {

    public Trader(){//rare
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.PAPER, 35).addResource(ResourceType.GLOW_INK_SAC, 20).addResource(ResourceType.GOLD, 30).addResource(ResourceType.CATALYST_RARE, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.PAPER, 100).addResource(ResourceType.GLOW_INK_SAC, 50).addResource(ResourceType.GOLD, 80).addResource(ResourceType.EXP_CRYSTAL_LVL2, 2);

        this.name = "Торгаш";
        this.id = "trader";
        this.item = Material.GOLD_NUGGET;
        this.icon = IconType.GOLD_INGOT;
        this.stacks = 0;
        this.maxStacks = 10;

        this.abilityCategories.add(AbilityCategory.GUARDIAN);
        this.abilityCategories.add(AbilityCategory.ECONOMIST);

        this.description = "Игрок имеет шанс в (values1)% вернуть 1 золото при#покупке любого предмета требующего золото. Срабатывание#навыка даёт игроку заряд Торгаша, который уменьшает#весь получаемый урон на 1% за заряд.#Максимум 10 зарядов.";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 25 + level * 15;
    }


//    @Override
//    public int calculateIntValue2(int level) {
//        return 20*20 + level * 20 * 3;
//    }


    @Override
    public String formatValue2(int level) {
        return "" + calculateIntValue2(level) / 20;
    }

    public void playerReceiveDamage(int level, DamageInstance damageInstance, Player victim, EntityDamageEvent event, CompoundValueModifier compoundValueModifier) {
        if (!(event instanceof EntityDamageByEntityEvent)) return;

        if (event.isCancelled()) return;

//        Bukkit.getConsoleSender().sendMessage("player receive damage from " + ((EntityDamageByEntityEvent) event).getDamager().getName() + "   source = " + damageSource);
        if (Main.isPlayerInGame(victim)) {
            if (victim.isBlocking() && event.getFinalDamage() == 0) {
                playFXDefensiveUtility(victim, 1);
                compoundValueModifier.addExp(-0.01 * stacks);
            }
        }
    }

    @Override
    public void shopPurchase(int level, Game game, Player player, PurchasableItem item, int amount) {
//        Bukkit.getConsoleSender().sendMessage("shit");

        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, calculateIntValue2(level), 1, false, false));

        if (item.priceType1 == Main.getSpawnerType("gold") || item.priceType2 == Main.getSpawnerType("gold")) {
            Random random = new Random();
            int chance = calculateIntValue1(level);

            ItemStack discount = Main.getSpawnerType("gold").getStack();

            for ( int i = 0; i < amount; i++ ) {
                if ( random.nextInt(100) < chance ) {
                    player.getInventory().addItem(discount);
                    if ( stacks < maxStacks ) {
                        this.stacks++;
                        notifyPlayerOnAbilityActivation(player);
                    }
                }
            }
        }
    }

}
