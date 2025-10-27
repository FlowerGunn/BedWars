package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CustomStatusEffect;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.AbilityCategory;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

public class BountyHunter extends Ability implements IAbility {

    public BountyHunter(){//epic
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.BONE_PLATE, 10).addResource(ResourceType.SEMICONDUCTOR, 1).addResource(ResourceType.GOLD_SHEET, 10).addResource(ResourceType.CATALYST_EPIC, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.BONE_PLATE, 30).addResource(ResourceType.SEMICONDUCTOR, 2).addResource(ResourceType.GOLD_SHEET, 30).addResource(ResourceType.EXP_CRYSTAL_LVL2, 5);

        this.name = "Охотник за Головами";
        this.id = "bountyhunter";
        this.item = Material.GOLDEN_HELMET;
        this.rarity = 4;
        this.icon = IconType.GOLD_INGOT;

        this.abilityCategories.add(AbilityCategory.FIGHTER);
        this.abilityCategories.add(AbilityCategory.ECONOMIST);

        this.description = "Убийства противников, если противник прожил#меньше 4 минут, даст игроку +30% к скорости#передвижения на (values2) секунд.#Иначе игрок получит 1 золото и ещё +1 золото#за каждые (values1) секунд жизни противника.";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 70 - 10 * level;
    }

    @Override
    public int calculateIntValue2(int level) {
        return 80 + 40 * level;
    } //speed buff


    @Override
    public String formatValue2(int level) {
        return "" + calculateIntValue2(level) / 20;
    }

    @Override
    public void playerKill(int level, Player victim, Player killer, PlayerDeathEvent event) {



        GamePlayer gKiller = Main.getPlayerGameProfile(killer);
        GamePlayer gVictim = Main.getPlayerGameProfile(victim );
        Game game = gVictim.getGame();

        int survivedFor = gVictim.lastDeathCounter - game.countdown;

        if ( survivedFor >= 4 * 60 ) {
            ItemStack kit = Main.getSpawnerType("gold").getStack();
            kit.setAmount( 1 + survivedFor / calculateIntValue1(level) );
            killer.getInventory().addItem(kit);
        } else {
            gKiller.addCustomStatusEffect(new CustomStatusEffect("bounty_hunter_speed", gKiller, gKiller, Attribute.GENERIC_MOVEMENT_SPEED, new CompoundValueModifier(0, 0, 30 * 0.01), calculateIntValue2(level), false));
        }

        notifyPlayerOnAbilityActivation(killer);

    };

}
