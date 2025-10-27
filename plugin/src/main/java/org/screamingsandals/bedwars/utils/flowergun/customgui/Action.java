package org.screamingsandals.bedwars.utils.flowergun.customgui;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.Team;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ActiveForgeRecipe;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.RecipeBook;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.Resource;
import org.screamingsandals.bedwars.utils.flowergun.managers.NotificationManager;
import org.screamingsandals.bedwars.utils.flowergun.managers.ResourceManager;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;
import org.screamingsandals.bedwars.utils.flowergun.shoputils.PurchasableItem;
import org.screamingsandals.bedwars.utils.flowergun.shoputils.Shop;
import org.screamingsandals.bedwars.utils.flowergun.shoputils.ShopCategory;
import org.screamingsandals.bedwars.game.CurrentTeam;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.LoadedAbility;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.OwnedAbility;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.screamingsandals.bedwars.lib.lang.I.i18n;

public class Action {

    @Setter
    @Getter
    private Player player;

    @Setter
    @Getter
    private String type;

    @Setter
    @Getter
    private String arg;

    @Setter
    @Getter
    private String arg2;
    @Setter
    @Getter
    private String arg3;

    public boolean isShift = false;
    public boolean isRight = false;
    public boolean isLeft = false;

    public Action(Player player, String type, String arg) {
        this.player = player;
        this.type = type;
        this.arg = arg;
    }

    public Action(Player player, String type, String arg, String arg2) {
        this.player = player;
        this.type = type;
        this.arg = arg;
        this.arg2 = arg2;
    }

//    public String getType() {
//        return this.type;
//    }
//
//    public String getArg() {
//        return this.arg;
//    }
//



    public void start() throws SQLException {
        //BlockParty.getInstance().getPlugin().getLogger().info(this.type + " " + this.arg);
        switch (this.type) {
            case "EXIT": {
                player.closeInventory();
                break;
            }
            case "GOGUI": {
                CustomGUI gui = new CustomGUI(player, arg);
                if (arg2 != null) {
                    gui.setArg1(arg2);
                }
                if (arg3 != null) {
                    gui.setArg2(arg3);
                }
                gui.load();
                break;
            }
            case "BUY_ITEM": {

                GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
                Game game = gamePlayer.getGame();
                CurrentTeam team = game.getPlayerTeam(gamePlayer);

                Shop shop = gamePlayer.getCustomGUIShopInstance();

                PurchasableItem item = null;

                for ( ShopCategory category: shop.categories ) {
                    for ( PurchasableItem purchasableItem : category.items ) {
                        if ( purchasableItem.getId() == arg ) {
                            item = purchasableItem;
                            break;
                        }
                    }
                    if ( item != null ) break;
                }


                item.tryToSellTo(player, this.isRight, this.isLeft, this.isShift);

                break;
            }
            case "BUY_UPGRADE": {

                GameFlag tryingToBuy = GameFlag.valueOf(arg);
                int headCost = tryingToBuy.getHeadCost();

                GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
                CurrentTeam team = gamePlayer.getGame().getPlayerTeam(gamePlayer);

                int headsOwned = 0;
                for (ItemStack itemStack: player.getInventory()) {
                    if (itemStack == null) continue;
                    if (itemStack.getType() == Material.PLAYER_HEAD) {
                        headsOwned += itemStack.getAmount();
                    }
                }

                if ( headsOwned >= headCost ) {
                    //process upgrade

                    if ( !team.teamFlags.contains(tryingToBuy) && !team.teamFlags.contains(tryingToBuy.getNeighbourFlag()) )
                    {
                        if ( team.teamFlags.contains(tryingToBuy.getPreviousFlag()) || tryingToBuy.getPreviousFlag() == null)
                        {

                            Main.getStatsManager().addResourceToPlayer(player, ResourceType.PAPER, 10 * headCost);

                            int headsToRemove = headCost;

                            for (ItemStack slot : player.getInventory().getContents()) {
                                if (slot == null) continue;

                                if (slot.getType() == Material.PLAYER_HEAD) {
                                    if (slot.getAmount() > headsToRemove) {
                                        slot.setAmount(slot.getAmount() - headsToRemove);
                                        headsToRemove = 0;
                                    } else {
                                        headsToRemove -= slot.getAmount();
                                        slot.setAmount(0);
                                    }
                                }

                                if (headsToRemove <= 0) break;
                            }

                            team.teamFlags.add(tryingToBuy);


                            String message = i18n("team_upgrade_notification", true).replace("%player%", team.teamInfo.color.chatColor + player.getName()).replace("%upgrade%", tryingToBuy.getName());
                            for ( GamePlayer teamGamePlayer: team.players ) {
                                teamGamePlayer.player.sendMessage(message);
                                teamGamePlayer.player.playSound(teamGamePlayer.player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 0.8F);
                                teamGamePlayer.player.playSound(teamGamePlayer.player.getLocation(), Sound.UI_LOOM_SELECT_PATTERN, 0.8F, 1.0F);
                            }

                            Bukkit.getConsoleSender().sendMessage(message + ChatColor.RESET + " на арене " + gamePlayer.getGame().getName());

                            CustomGUI customGUI = new CustomGUI(player, "TEAM_UPGRADES");
                            customGUI.load();
                            break;
                        }
                    }
                }

                //cant buy
                player.playSound( player.getLocation(), Sound.ENTITY_VILLAGER_HURT, 0.8F, 1.0F );
                CustomGUI customGUI = new CustomGUI(player, "TEAM_UPGRADES");
                customGUI.load();
                break;

            }
            case "JOIN_TEAM": {

                GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
                Game game = gamePlayer.getGame();
                ArrayList<Team> teams = new ArrayList<>(game.getTeams());

                if (game.selectTeam(gamePlayer, arg)) {
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
                }
                else {
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_HURT, 0.2F, 1.0F);
                }

                ArrayList<GamePlayer> gamePlayers = new ArrayList<>(game.getConnectedGamePlayers());

                for ( GamePlayer lobbyWaiter : gamePlayers ) {
//                    Bukkit.getConsoleSender().sendMessage(PlainTextComponentSerializer.plainText().serialize( lobbyWaiter.player.displayName()) + "->" + PlainTextComponentSerializer.plainText().serialize(lobbyWaiter.player.getOpenInventory().title()));
                    if (lobbyWaiter.player.getOpenInventory().getType() == InventoryType.CHEST && lobbyWaiter.getLastMenuVisited() == MenuType.TEAM_SELECT ) {
                        CustomGUI customGUI = new CustomGUI( lobbyWaiter.player, "TEAM_SELECTION" );
                        customGUI.load();
                    }
                }



                break;
            }
            case "SELECT_ABILITY_INTO_SLOT": {

                String id = arg;
                int slot = Integer.parseInt(arg2);

                GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
                Game game = gamePlayer.getGame();
                ArrayList<LoadedAbility> loadedAbilities = gamePlayer.loadedAbilities;
                ArrayList<OwnedAbility> ownedAbilities = gamePlayer.ownedAbilities;

                OwnedAbility chosenAbility = gamePlayer.getOwnedAbilityById(id);
                LoadedAbility currentLoadedAbility = loadedAbilities.get(slot);

                if ( chosenAbility.isAvailable() && (currentLoadedAbility.isEmpty() || currentLoadedAbility.getOwnedAbility() != chosenAbility)) {
                    //SUCCESS

                    gamePlayer.resetAbilitySlot(slot);
                    gamePlayer.resetLoadedAbility( chosenAbility );


                    int effectiveOwnedLevel = chosenAbility.getAbility().isTemporarilyAvailable() ? 3 : chosenAbility.ownedLevel;

                    int level = Math.min( effectiveOwnedLevel, slot + 1 );
                    loadedAbilities.set(slot, new LoadedAbility( chosenAbility, level ));

                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
                    CustomGUI customGUI = new CustomGUI(player, "ABILITIES");
                    customGUI.load();

                    game.updateScoreboard( gamePlayer );

                }
                else {
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_HURT, 0.2F, 1.0F);
                }

                break;

            }
            case "RANDOMIZE_ABILITY_INTO_SLOT": {
                int slot = Integer.parseInt(arg);
                GamePlayer gamePlayer = Main.getPlayerGameProfile(player);

                gamePlayer.loadedAbilities.set(slot, LoadedAbility.getEmptyLoadedAbility());
                gamePlayer.randomlySelectAbilityInSlot(slot);


                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
                CustomGUI customGUI = new CustomGUI(player, "ABILITIES");
                customGUI.load();

                gamePlayer.getGame().updateScoreboard( gamePlayer );
                break;
            }
            case "RANDOMIZE_ALL_ABILITIES": {
                GamePlayer gamePlayer = Main.getPlayerGameProfile(player);

                gamePlayer.loadedAbilities.set(0, LoadedAbility.getEmptyLoadedAbility());
                gamePlayer.loadedAbilities.set(1, LoadedAbility.getEmptyLoadedAbility());
                gamePlayer.loadedAbilities.set(2, LoadedAbility.getEmptyLoadedAbility());
                gamePlayer.randomlySelectAllAbilities();

                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
                CustomGUI customGUI = new CustomGUI(player, "ABILITIES");
                customGUI.load();
                gamePlayer.getGame().updateScoreboard( gamePlayer );
                break;
            }
            case "RANDOMIZE_ALL_ABILITIES_BY_CATEGORY": {
                GamePlayer gamePlayer = Main.getPlayerGameProfile(player);

                gamePlayer.loadedAbilities.set(0, LoadedAbility.getEmptyLoadedAbility());
                gamePlayer.loadedAbilities.set(1, LoadedAbility.getEmptyLoadedAbility());
                gamePlayer.loadedAbilities.set(2, LoadedAbility.getEmptyLoadedAbility());

                gamePlayer.randomlySelectAllAbilitiesFromCategory(AbilityCategory.valueOf(arg));

                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
                CustomGUI customGUI = new CustomGUI(player, "ABILITIES");
                customGUI.load();
                gamePlayer.getGame().updateScoreboard( gamePlayer );
                break;
            }
            case "SWITCH_SETTING": {
                GamePlayer gamePlayer = Main.getPlayerGameProfile(player);

                PlayerConfigType type = PlayerConfigType.valueOf(arg);

                boolean config = gamePlayer.getSetting(type);

                gamePlayer.setSetting( type, !config ? "1" : "0" );

                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
                CustomGUI customGUI = new CustomGUI(player, "SETTINGS");
                customGUI.load();
                break;
            }
            case "SWITCH_SIMPLE_ABILITY_SELECTION": {
                GamePlayer gamePlayer = Main.getPlayerGameProfile(player);


                boolean config = gamePlayer.getSetting(PlayerConfigType.SIMPLIFIED_ABILITY_SELECTION);

                gamePlayer.setSetting( PlayerConfigType.SIMPLIFIED_ABILITY_SELECTION, !config ? "1" : "0" );

                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
                CustomGUI customGUI = new CustomGUI(player, "ABILITIES");
                customGUI.load();
                break;
            }
            case "FORGE_RECIPE_CLAIM": {
                GamePlayer gamePlayer = Main.getPlayerGameProfile(player);

                ArrayList<ActiveForgeRecipe> activeForgeRecipes = gamePlayer.activeForgeRecipes;

                int slot = Integer.parseInt(arg);

                for ( ActiveForgeRecipe activeForgeRecipe : activeForgeRecipes ) {
                    if (slot == activeForgeRecipe.getSlot()) {
                        activeForgeRecipe.setSlot(-1);
                        activeForgeRecipe.setRedeemed(true);
                        activeForgeRecipe.awardOutput(gamePlayer);
                        //TODO redeem time
                        Main.getDatabaseManager().storeDatabaseForge(activeForgeRecipe);
                        break;
                    }
                }

                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
                CustomGUI customGUI = new CustomGUI(player, "FORGE");
                customGUI.load();
                break;
            }
            case "FORGE_RECIPE_SET": {
                GamePlayer gamePlayer = Main.getPlayerGameProfile(player);

                int slot = Integer.parseInt(arg);
                String recipeId = arg2;

                for ( Resource resource : RecipeBook.getRecipeById(recipeId).getInput().resources) {
                    ResourceManager.giveResourcesTo(player.getUniqueId(), resource.getType(), resource.getAmount() * -1, false);
                }

                ActiveForgeRecipe newForgeRecipe = new ActiveForgeRecipe(Main.getDatabaseManager().getMaxForgeEntryId() + 1, recipeId, player.getUniqueId(), slot, Timestamp.valueOf(LocalDateTime.now()));

                gamePlayer.activeForgeRecipes.add(newForgeRecipe);
                Main.getDatabaseManager().storeDatabaseForge(newForgeRecipe);

                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
                CustomGUI customGUI = new CustomGUI(player, "FORGE");
                customGUI.load();
                break;
            }
            case "ABILITY_UPGRADE": {

                GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
                String abilityId = arg;
                OwnedAbility ownedAbility = gamePlayer.getOwnedAbilityById(abilityId);

                for ( Resource resource : ownedAbility.getUpgradeResources().resources ) {
                    ResourceManager.giveResourcesTo(player.getUniqueId(), resource.getType(), resource.getAmount() * -1, false);
                }

                ownedAbility.duplicatesOwned -= ownedAbility.getNextLevelCost();
                ownedAbility.ownedLevel += 1;
                ownedAbility.save();

                NotificationManager.getUpgradedAbilityMessage(ownedAbility);

                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5F, 0.7F);
                player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 0.4F, 1.5F);
                CustomGUI customGUI = new CustomGUI(player, "INVENTORY_ABILITY_INFO");
                customGUI.setArg1(abilityId);
                customGUI.setArg2(arg2);
                customGUI.load();

                break;
            }
            case "ABILITY_DISASSEMBLE": {

                GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
                String abilityId = arg;
                OwnedAbility ownedAbility = gamePlayer.getOwnedAbilityById(abilityId);

                for ( Resource resource : ownedAbility.getDisassembleResources().resources ) {
                    ResourceManager.giveResourcesTo(player.getUniqueId(), resource.getType(), resource.getAmount(), true);
                }

                ownedAbility.duplicatesOwned -= 1;
                ownedAbility.save();

                NotificationManager.getDisassembledAbilityMessage(ownedAbility);

                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5F, 0.7F);
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 0.4F, 1.5F);
                CustomGUI customGUI = new CustomGUI(player, "INVENTORY_ABILITY_INFO");
                customGUI.setArg1(abilityId);
                customGUI.setArg2(arg2);
                customGUI.load();

                break;
            }
            case "ERROR": {
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_HURT, 0.2F, 1.0F);
                break;
            }
            default: {
                //BlockParty.getInstance().getPlugin().getServer().getLogger().info("DEFAULT Action called!");
            }

        }
    }

}
