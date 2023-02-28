package org.screamingsandals.bedwars.utils.flowergun.customobjects;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.managers.AbilitiesManager;
import org.screamingsandals.bedwars.utils.flowergun.managers.NotificationManager;
import org.screamingsandals.bedwars.utils.flowergun.managers.ResourceManager;

import java.util.ArrayList;

public class ResourceChestReward {

    private Class abilityClass;
    private IAbility ability;

    @Getter
    private Resource resource;

    private ItemStack guiItem;
    private String name;
    private int rarity;

    public ResourceChestReward( Class abilityClass ) {
        this.abilityClass = abilityClass;
        generateItem();
    }

    public ResourceChestReward( Resource resource ) {
        this.resource = resource;
        generateItem();
    }



    public void giveRewardTo(Player player) {
        if (this.ability != null) {
            Main.getAbilitiesManager().giveAbilityToById(player.getUniqueId(), ability.getId(), 1);
        } else if ( this.resource != null ) {
            ResourceManager.giveResourcesTo(player.getUniqueId(), resource);
        } else {
            Bukkit.getConsoleSender().sendMessage("Player " + player.getDisplayName() + " ate a bag of shit.");
        }
    }

    public int getRarity() {
        return this.rarity;
    }

    public ItemStack generateItem() {
        ArrayList<String> lore = new ArrayList<>();
        lore.addAll(NotificationManager.getRewardTypeMessage(this));
        if ( this.guiItem == null ) {
            if ( resource != null ) {
                this.guiItem = resource.getType().getGuiItem();
                this.guiItem.setAmount(Math.min(64, resource.getAmount()));
                this.name = resource.getType().getName();
                this.rarity = resource.getType().getRarity();
            } else if ( abilityClass != null ) {
                this.ability = Ability.generateAbility(abilityClass);
                this.guiItem = this.ability.getAbilityGuiItem();
                this.name = this.ability.getName();
                this.rarity = this.ability.getRarity();
                lore.addAll(this.ability.parseDescription(3, 3, 3));
            } else {
                this.guiItem = new ItemStack(Material.STONE);
                this.name = "лол";
                this.rarity = 0;
            }
        }

        ItemMeta meta = this.guiItem.getItemMeta();
        meta.setLore(lore);
        this.guiItem.setItemMeta(meta);
        return this.guiItem;
    }

    public ItemStack getGuiItem() {
        return this.guiItem.clone();
    }

    public String getName() {
        return this.name;
    }
}
