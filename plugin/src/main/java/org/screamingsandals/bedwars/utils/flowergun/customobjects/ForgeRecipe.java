package org.screamingsandals.bedwars.utils.flowergun.customobjects;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.managers.AbilitiesManager;
import org.screamingsandals.bedwars.utils.flowergun.managers.ColoursManager;
import org.screamingsandals.bedwars.utils.flowergun.managers.NotificationManager;
import org.screamingsandals.bedwars.utils.flowergun.managers.RarityManager;

import java.util.ArrayList;

public class ForgeRecipe {

    @Getter
    @Setter
    private ResourceBundle input;
    @Getter
    @Setter
    private ResourceBundle output;

    @Getter
    @Setter
    private IAbility outputAbility;
    @Getter
    @Setter
    private int timeInSeconds;

    @Setter
    private String name;
    @Getter
    @Setter
    private String id;

    public ForgeRecipe( String id, String name, ResourceBundle input, ResourceBundle output) {
        this.id = id;
        this.name = name;
        this.input = input;
        this.output = output;
        this.timeInSeconds = 60;
    }

    public ForgeRecipe( String id, ResourceBundle input, Class abilityClass) {
        this.id = id;
        this.setOutputAbility(abilityClass);
        this.name = this.outputAbility.getName();
        this.input = input;
        this.timeInSeconds = 60;
        this.output = new ResourceBundle();
    }

    public String getName() {
        int rarity;
        if ( this.outputAbility != null ) rarity = this.outputAbility.getRarity();
        else rarity = this.output.resources.get(this.output.resources.size() - 1).getType().getRarity();
        return RarityManager.getRarityColour(rarity) + this.name;
    }

    public ForgeRecipe setOutputAbility(String outputAbilityID) {
        this.outputAbility = Main.getAbilitiesManager().findAbiilityInfoById(outputAbilityID);
        return this;
    }

    public ForgeRecipe setOutputAbility(Class outputAbilityClass) {
        this.outputAbility = Ability.generateAbility(outputAbilityClass);
        return this;
    }

    public ForgeRecipe setTimeInSeconds(int seconds){
        this.timeInSeconds = seconds;
        return this;
    }

    public ForgeRecipe setTimeInMinutes(int minutes){
        this.timeInSeconds = minutes * 60;
        return this;
    }

    public ForgeRecipe setTimeInHours(int hours){
        this.timeInSeconds = hours * 60 * 60;
        return this;
    }

    public ForgeRecipe setTimeInDays(int days){
        this.timeInSeconds = days * 24 * 60 * 60;
        return this;
    }


    public ArrayList<String> getRecipeProgress(GamePlayer player) {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ColoursManager.gray + "Используется:");
//        for ( Resource inputResource : this.getInput().resources) {
//            int playerAmount = player.ownedResourceBundle.getOwnedResourceAmount(inputResource.getType());
//            String playerColour = inputResource.amount <= playerAmount ? ColoursManager.green + "" : ColoursManager.red + "";
//            lore.add(ColoursManager.gray + inputResource.getResourceWithAmountAndIcon(player.player) + ColoursManager.gray + " (" + playerColour + playerAmount + ColoursManager.gray + ")");
//        }
        lore.addAll(this.input.parseResourcesForPlayer(player.player, true));
        lore.add("");
        lore.add(ColoursManager.gray + "Итог:");
//        for ( Resource outputResource : this.getOutput().resources) {
//            int playerAmount = player.ownedResourceBundle.getOwnedResourceAmount(outputResource.getType());
//            lore.add(ColoursManager.gray + outputResource.getResourceWithAmountAndIcon(player.player) + ColoursManager.gray + " (" + playerAmount + ")");
//        }

        lore.addAll(this.output.parseResourcesForPlayer(player.player, false));

        if ( this.outputAbility != null ) {
            lore.add(ColoursManager.gray + outputAbility.getNameWithIcon(player.player) + " x1");
        }
        return lore;
    }

    public ArrayList<String> getRecipeTime() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ColoursManager.gray + "Время крафта: ");
        lore.addAll(NotificationManager.parseTime(this.getTimeInSeconds()));
        return lore;
    }

    public ItemStack getGuiItem() {
        if ( this.outputAbility != null ) {
            return outputAbility.getGuiItem();
        }
        else return this.output.resources.get(0).type.getGuiItem();
    }

}
