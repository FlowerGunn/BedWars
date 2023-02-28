package org.screamingsandals.bedwars.utils.flowergun.abilities_base;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.TextComponent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.Resource;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.managers.ColoursManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import static org.screamingsandals.bedwars.game.Game.ALL_ABILITIES_MODE;

public class OwnedAbility{

    public int dbEntryId;
    private Player owner;
    public UUID ownerUUID;

    @Getter
    public int ownedLevel;
    public int duplicatesOwned;
    public int instancesCrafted;
    @Setter
    @Getter
    public int lastEquippedSlot;

    @Getter
    protected IAbility ability;

    public OwnedAbility(int dbEntryId,Player player, IAbility ability, int ownedLevel, int duplicatesOwned, int instancesCrafted, int lastEquippedSlot) {
        this.dbEntryId = dbEntryId;
        this.owner = player;
        this.ownerUUID = player.getUniqueId();
        this.ability = ability;

        this.ownedLevel = ownedLevel;
        this.duplicatesOwned = duplicatesOwned;
        this.instancesCrafted = instancesCrafted;
        this.lastEquippedSlot = lastEquippedSlot;
    }

    public OwnedAbility(int dbEntryId, UUID uuid, String abilityId, int ownedLevel, int duplicatesOwned, int instancesCrafted, int lastEquippedSlot) {
        this.dbEntryId = dbEntryId;
        this.ownerUUID = uuid;

        this.ability = Ability.generateAbility(Main.getAbilitiesManager().findAbiilityInfoById(abilityId).getClass());

        this.ownedLevel = ownedLevel;
        this.duplicatesOwned = duplicatesOwned;
        this.instancesCrafted = instancesCrafted;
        this.lastEquippedSlot = lastEquippedSlot;
    }

    public Player getOwner() {
        if (this.owner == null) {
            this.owner = Bukkit.getPlayer(ownerUUID);
        }

        return this.owner;
    }

    public boolean isAvailable() {
        if (this.isOwned() || Main.getConfigurator().config.getBoolean(ALL_ABILITIES_MODE)) return true;
        return false;
    }

    public boolean isOwned() {
        return this.ownedLevel != 0;
    }

    public LoadedAbility loadAbility( int activeLevel ) {
        return new LoadedAbility( this, activeLevel);
    }

    public boolean isNextLevelUpgradeAvailable() {
        int cost = this.getNextLevelCost();
        if (cost == 0) return false;
        else if ( cost <= this.duplicatesOwned ) return true;
        else return false;
    }

    public boolean isEnoughToUpgrade() {
        return (Main.getPlayerGameProfile(this.getOwner()).ownedResourceBundle.isContainingBundle(this.getUpgradeResources()) && isNextLevelUpgradeAvailable());
    }

    public int getNextLevelCost() {
        return this.ability.getNextLevelCost(this.ownedLevel);
    }

    public ArrayList<String> parseDescription(int slot) {
        int effectiveOwnedLevel = Main.getConfigurator().config.getBoolean(ALL_ABILITIES_MODE) ? 3 : this.ownedLevel;
        return this.getAbility().parseDescription(effectiveOwnedLevel, Math.min(effectiveOwnedLevel, slot), slot );
    }

    public TextComponent parseDescriptionComponent(int slot, Player player) {
        int effectiveOwnedLevel = Main.getConfigurator().config.getBoolean(ALL_ABILITIES_MODE) ? 3 : this.ownedLevel;
        return this.getAbility().parseDescriptionComponent( player, effectiveOwnedLevel, Math.min(effectiveOwnedLevel, slot), slot );
    }

    public Collection<String> generateInventoryInfo(Player player) {
        return generateInventoryInfo(player, true, true, true, true);
    }

    public Collection<String> generateInventoryInfo(Player player, boolean showUpgradability, boolean showUpgradeInput, boolean showDisassembleOutput, boolean showDevServerWarning) {
        ArrayList<String> lore = new ArrayList<>();

        if (showUpgradability) {
            String levelString = ColoursManager.gray + " Уровень улучшения: " + ColoursManager.green + this.ownedLevel;
            lore.add("");
            if (this.isNextLevelUpgradeAvailable()) {
                levelString += FlowerUtils.upgradeAvailableMarker;
            }
            lore.add(levelString);
            String duplicatesString = ColoursManager.gray + " Дубликатов: " + ColoursManager.green + this.duplicatesOwned;
            lore.add(duplicatesString);
        }

        if ( showUpgradeInput && !this.isMaxLevel() ) {
            lore.add("");
            lore.add(ColoursManager.gray + " Ресурсы для улучшения: " );
            String playerColour = this.duplicatesOwned >= this.getNextLevelCost() ? ColoursManager.green + "" : ColoursManager.red + "";
            lore.add(ColoursManager.gray + " " + this.ability.getNameWithIcon(player) + ColoursManager.gray + " x" + this.getNextLevelCost() + ColoursManager.gray + " (" + playerColour + this.duplicatesOwned + ColoursManager.gray + ")");

            lore.addAll(this.getUpgradeResources().parseResourcesForPlayer(player, true));

        }

        if ( showDisassembleOutput ) {

            lore.add("");
            lore.add(ColoursManager.gray + " Разбор копии даст: " );
            lore.addAll(this.getDisassembleResources().parseResourcesForPlayer(player, false));

        }

        if ( showDevServerWarning && Main.getConfigurator().config.getBoolean(ALL_ABILITIES_MODE)) {
            lore.add("");
            lore.addAll(FlowerUtils.alphaWarning);
        }



        return lore;
    }

    public ResourceBundle getUpgradeResources() {
        return this.ability.getUpgradeResources(this.ownedLevel);
    }

    public ResourceBundle getDisassembleResources() {
        return this.ability.getDisassembleResources();
    }

    private boolean isMaxLevel() {
        return this.ownedLevel >= FlowerUtils.maxAbilityLevel;
    }

    public void save() {
        Main.getDatabaseManager().storeDatabaseAbility(this);
    }
}

