package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.AbilityCategory;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.DamageRelay;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

import static org.screamingsandals.bedwars.lib.lang.I.i18nonly;

public class Levitator extends Ability implements IAbility {

    public Levitator(){//rare
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.MAGIC_DUST, 10).addResource(ResourceType.ECHO_DUST, 10).addResource(ResourceType.LAPIS, 40).addResource(ResourceType.CATALYST_RARE, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.MAGIC_DUST, 20).addResource(ResourceType.ECHO_DUST, 25).addResource(ResourceType.LAPIS, 80).addResource(ResourceType.EXP_CRYSTAL_LVL2, 2);

        this.name = "Левитатор";
        this.id = "levitator";
        this.item = Material.WHITE_DYE;
        this.icon = IconType.LEVITATION;
        this.rarity = 3;

        this.abilityCategories.add(AbilityCategory.MANIPULATOR);
        this.abilityCategories.add(AbilityCategory.RANGER);
        this.abilityCategories.add(AbilityCategory.GUARDIAN);

        this.description = "Умирая от снарядов игрок накладываете#Левитацию 2 на убийцу на (values1) секунд.#Убивая противников снарядами игрок получает#взрывное зелье на Левитацию 2 на (values1) секунд.";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 5 + level * 2;
    }


    @Override
    public void playerDeath(int level, Player victim, Player killer, PlayerDeathEvent event) {

        if ( killer != null ) {
            GamePlayer gVictim = Main.getPlayerGameProfile(victim);
            if ( gVictim.lastReceivedDamageInstance.damageRelay == DamageRelay.PROJECTILE ) {
                playFXSpeed(killer, 3);
                notifyPlayerOnAbilityActivation(victim);
                killer.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, calculateIntValue1(level) * 20, 1));
            }
        }
    }

    @Override
    public void playerKill(int level, Player victim, Player killer, PlayerDeathEvent event)  {

        GamePlayer gVictim = Main.getPlayerGameProfile(victim);

        if ( gVictim.lastReceivedDamageInstance.damageRelay == DamageRelay.PROJECTILE ) {
            ItemStack kit = new ItemStack(Material.SPLASH_POTION);
            PotionMeta potionMeta = (PotionMeta) kit.getItemMeta();
            potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.LEVITATION, calculateIntValue1(level) * 20, 1), true);
            potionMeta.setColor(Color.fromRGB(201, 193, 173));
            potionMeta.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + i18nonly("item_potion_levitation"));
            kit.setItemMeta(potionMeta);
            killer.getInventory().addItem(kit);
        }

    }

}
