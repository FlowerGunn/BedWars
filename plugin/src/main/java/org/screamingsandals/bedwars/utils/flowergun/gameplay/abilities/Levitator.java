package org.screamingsandals.bedwars.utils.flowergun.gameplay.abilities;

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
import org.screamingsandals.bedwars.utils.flowergun.gameplay.Ability;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.tools.IconType;
import org.screamingsandals.bedwars.utils.flowergun.tools.enums.DamageRelay;

import static org.screamingsandals.bedwars.lib.lang.I.i18nonly;

public class Levitator extends Ability implements IAbility {

    public Levitator(){
        this.name = "Левитатор";
        this.id = "levitator";
        this.item = Material.WHITE_DYE;
        this.icon = IconType.LEVITATION;
        this.description = "Умирая от снарядов вы накладываете#Левитацию 2 на убийцу на (values1)&7 секунд.#Убивая противников снарядами вы получаете#взрывное зелье на Левитацию 2 на (values1)&7 секунд.";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 5 + level * 2;
    }


    @Override
    public void playerDeath(int level, PlayerDeathEvent event) {

        if ( event.getEntity().getKiller() != null ) {
            Player victim = event.getEntity();
            GamePlayer gVictim = Main.getPlayerGameProfile(victim);
            if ( gVictim.lastReceivedDamageInstance.damageRelay == DamageRelay.PROJECTILE ) {
                playFXCrowdControl(event.getEntity().getKiller(), 3);
                notifyPlayerOnAbilityActivation(victim);
                event.getEntity().getKiller().addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, calculateIntValue1(level) * 20, 1));
            }
        }
    }

    @Override
    public void playerKill(int level, Player killer, PlayerDeathEvent event)  {

        Player victim = event.getEntity();
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
