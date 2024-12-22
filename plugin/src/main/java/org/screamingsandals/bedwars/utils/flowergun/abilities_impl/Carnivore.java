package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
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

import java.util.ArrayList;

public class Carnivore extends Ability implements IAbility {

    private static ArrayList<Material> meats;
    
    static {
        meats = new ArrayList<>();
        meats.add(Material.BEEF);
        meats.add(Material.COOKED_BEEF);
        meats.add(Material.PORKCHOP);
        meats.add(Material.COOKED_PORKCHOP);
        meats.add(Material.TROPICAL_FISH);
        meats.add(Material.COD);
        meats.add(Material.SALMON);
        meats.add(Material.COOKED_SALMON);
        meats.add(Material.COOKED_COD);
        meats.add(Material.CHICKEN);
        meats.add(Material.COOKED_CHICKEN);
        meats.add(Material.MUTTON);
        meats.add(Material.COOKED_MUTTON);
        meats.add(Material.COOKED_RABBIT);
        meats.add(Material.RABBIT);
        meats.add(Material.RABBIT_FOOT);
        meats.add(Material.RABBIT_STEW);
        meats.add(Material.ROTTEN_FLESH);
    }

    public Carnivore(){//rare
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.BONE, 60).addResource(ResourceType.MAGIC_DUST, 10).addResource(ResourceType.EMERALD_DUST, 10).addResource(ResourceType.CATALYST_RARE, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.BONE, 150).addResource(ResourceType.MAGIC_DUST, 30).addResource(ResourceType.EMERALD_DUST, 20).addResource(ResourceType.EXP_CRYSTAL_LVL2, 2);

        this.name = "Хищник";
        this.id = "carnivore";
        this.item = Material.COOKED_BEEF;
        this.rarity = 3;
        this.icon = IconType.INCREASE_DAMAGE;

        this.abilityCategories.add(AbilityCategory.FIGHTER);
        this.abilityCategories.add(AbilityCategory.BULLDOZER);

        this.description = "Съедая любуя мясную еду игрок увеличит#весь наносимый ближний урон на +(values1)%#и увеличит весь получаемый физический#урон на +20% на 10 секунд";
        
    }

    @Override
    public int calculateIntValue1(int level) {
        return 17 + 3 * level;
    }

    @Override
    public void itemConsume(int level, Player player, PlayerItemConsumeEvent event) {
        if (event.isCancelled()) return;
        if (Main.isPlayerInGame(player)) {
            if ( meats.contains(event.getItem().getType()) ) {
                GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
                gamePlayer.addCustomStatusEffect(new CustomStatusEffect("carnivore_incoming", gamePlayer, gamePlayer, CustomStatusEffectType.DAMAGE_RECEIVED, new DamageInstance( null, DamageTarget.PLAYER, null, DamageType.PHYSICAL), new CompoundValueModifier(0, 0,  0.20), 200, false));
                gamePlayer.addCustomStatusEffect(new CustomStatusEffect("carnivore_outgoing", gamePlayer, gamePlayer, CustomStatusEffectType.DAMAGE_DEALT, new DamageInstance( DamageSource.PLAYER, null, DamageRelay.MELEE, null), new CompoundValueModifier(0, 0,  calculateIntValue1(level) * 0.01), 200, false));
            }
        }
    }
}
