package org.screamingsandals.bedwars.utils.flowergun.customgui.guiutils;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.serializer.plain.PlainComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.Team;
import org.screamingsandals.bedwars.utils.flowergun.customgui.shoputils.GameFlag;
import org.screamingsandals.bedwars.utils.flowergun.customgui.shoputils.PurchasableItem;
import org.screamingsandals.bedwars.utils.flowergun.customgui.shoputils.Shop;
import org.screamingsandals.bedwars.utils.flowergun.customgui.shoputils.ShopCategory;
import org.screamingsandals.bedwars.game.CurrentTeam;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.LoadedAbility;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.OwnedAbility;

import java.awt.*;
import java.sql.SQLException;
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
                CustomGUI gui = null;
                if (arg2 == null) {
                    gui = new CustomGUI(player, arg);
                }
                else
                {
                    gui = new CustomGUI(player, arg).setArg1(arg2);
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
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.8F, 1.0F);
                }
                else {
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_HURT, 0.8F, 1.0F);
                }

                ArrayList<GamePlayer> gamePlayers = new ArrayList<>(game.getConnectedGamePlayers());

                for ( GamePlayer lobbyWaiter : gamePlayers ) {
                    //Bukkit.getConsoleSender().sendMessage(PlainTextComponentSerializer.plainText().serialize( lobbyWaiter.player.displayName()) + " " + PlainTextComponentSerializer.plainText().serialize(lobbyWaiter.player.getOpenInventory().title()));
                    if (PlainTextComponentSerializer.plainText().serialize(lobbyWaiter.player.getOpenInventory().title()).equals("Выбор Команды")) {
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

                if ( chosenAbility.duplicatesOwned > 0 && (currentLoadedAbility.isEmpty() || currentLoadedAbility.getOwnedAbility() != chosenAbility)) {
                    //SUCCESS

                    gamePlayer.resetAbilitySlot(slot);
                    gamePlayer.resetLoadedAbility( chosenAbility );

                    int level = chosenAbility.ownedLevel > slot + 1 ? slot + 1 : chosenAbility.ownedLevel;
                    loadedAbilities.set(slot, new LoadedAbility( chosenAbility, level ));

                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.8F, 1.0F);
                    CustomGUI customGUI = new CustomGUI(player, "ABILITIES");
                    customGUI.load();
                }
                else {
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_HURT, 0.8F, 1.0F);
                }

                break;

            }
            case "RANDOMIZE_ABILITY_INTO_SLOT": {
                int slot = Integer.parseInt(arg);
                GamePlayer gamePlayer = Main.getPlayerGameProfile(player);

                gamePlayer.randomlySelectAbilityInSlot(slot);

                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.8F, 1.0F);
                CustomGUI customGUI = new CustomGUI(player, "ABILITIES");
                customGUI.load();
                break;
            }
            case "RANDOMIZE_ALL_ABILITIES": {
                GamePlayer gamePlayer = Main.getPlayerGameProfile(player);

                gamePlayer.randomlySelectAllAbilities();

                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.8F, 1.0F);
                CustomGUI customGUI = new CustomGUI(player, "ABILITIES");
                customGUI.load();
                break;
            }
            case "ERROR": {
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_HURT, 0.8F, 1.0F);
                break;
            }
            default: {
                //BlockParty.getInstance().getPlugin().getServer().getLogger().info("DEFAULT Action called!");
            }

        }
    }

}
