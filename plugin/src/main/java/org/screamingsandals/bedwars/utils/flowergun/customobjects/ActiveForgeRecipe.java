package org.screamingsandals.bedwars.utils.flowergun.customobjects;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.managers.AbilitiesManager;
import org.screamingsandals.bedwars.utils.flowergun.managers.IconsManager;
import org.screamingsandals.bedwars.utils.flowergun.managers.NotificationManager;
import org.screamingsandals.bedwars.utils.flowergun.managers.ResourceManager;

import java.sql.Timestamp;
import java.util.UUID;

public class ActiveForgeRecipe {

    @Getter
    @Setter
    private ForgeRecipe recipe;

    @Getter
    @Setter
    private int slot;

    @Getter
    @Setter
    private int entry_id;

    @Getter
    @Setter
    private UUID uuid;

    @Getter
    @Setter
    private boolean redeemed;

    @Getter
    @Setter
    private Timestamp startTime;

    public ActiveForgeRecipe( int entry_id, ForgeRecipe forgeRecipe, UUID uuid, int slot, Timestamp startTime, boolean redeemed ) {
        this.recipe = forgeRecipe;
        this.uuid = uuid;
        this.entry_id = entry_id;
        this.slot = slot;
        this.startTime = startTime;
        this.redeemed = redeemed;
    }

    public ActiveForgeRecipe( int entry_id, String recipeId, UUID uuid, int slot, Timestamp startTime ) {
        this.recipe = RecipeBook.getRecipeById(recipeId);
        this.uuid = uuid;
        this.entry_id = entry_id;
        this.slot = slot;
        this.startTime = startTime;
        this.redeemed = redeemed;
    }

    public void awardOutput(GamePlayer gamePlayer) {
        for (Resource resource : this.recipe.getOutput().resources) {
            ResourceManager.giveResourcesTo(gamePlayer.player.getUniqueId(), resource);
            //NotificationManager.getResourcesMessage(gamePlayer.player, resource);
        }
        if ( this.recipe.getOutputAbility() != null ) {
            IAbility ability = this.recipe.getOutputAbility();
            Main.getAbilitiesManager().giveAbilityToById(gamePlayer.player.getUniqueId(), ability.getId(), 1, true, true);
        }
    }
}
