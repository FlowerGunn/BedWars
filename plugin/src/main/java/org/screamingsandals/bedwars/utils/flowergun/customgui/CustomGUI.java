package org.screamingsandals.bedwars.utils.flowergun.customgui;


import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.FontImages.FontImageWrapper;
import dev.lone.itemsadder.api.FontImages.TexturedInventoryWrapper;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.Team;
import org.screamingsandals.bedwars.game.CurrentTeam;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.LoadedAbility;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.OwnedAbility;
import org.screamingsandals.bedwars.utils.flowergun.cases.AnimationGUIRoll;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.*;
import org.screamingsandals.bedwars.utils.flowergun.managers.ColoursManager;
import org.screamingsandals.bedwars.utils.flowergun.managers.IconsManager;
import org.screamingsandals.bedwars.utils.flowergun.managers.NotificationManager;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.GameFlag;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.MenuType;
import org.screamingsandals.bedwars.utils.flowergun.shoputils.PurchasableItem;
import org.screamingsandals.bedwars.utils.flowergun.shoputils.ShopCategory;
import org.screamingsandals.bedwars.utils.flowergun.shoputils.ShopInstance;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.screamingsandals.bedwars.game.Game.ALL_ABILITIES_MODE;
import static org.screamingsandals.bedwars.inventories.TeamSelectorInventory.formatLore;
import static org.screamingsandals.bedwars.lib.lang.I.i18n;
import static org.screamingsandals.bedwars.lib.lang.I.i18nonly;

public class CustomGUI {

    private ArrayList<CustomGUIButton> buttons;
    private String guiId;
    private String arg;
    private String arg2;
    private String arg3;
    private Player player;
    private final int nextPageX = 2;
    private final int nextPageY = 4;
    private final int prevPageX = 0;
    private final int prevPageY = 4;
    public static final int gobackButtonX = 8;
    public static final int gobackButtonY = 5;
    public static final int cosmeticRows = 4;

    public static ArrayList<CustomGUIButton> abilitiesRedPanes;
    public static ArrayList<CustomGUIButton> abilitiesGrayPanes1;
    public static ArrayList<CustomGUIButton> abilitiesGrayPanes2;
    public static ArrayList<CustomGUIButton> abilitiesOrangePanes;
    public static ArrayList<CustomGUIButton> abilitiesYellowPanes;
    public static ArrayList<CustomGUIButton> abilitiesHoppers;
    public static ArrayList<CustomGUIButton> abilities;

    static {
        abilitiesRedPanes = new ArrayList<>();
        abilitiesGrayPanes1 = new ArrayList<>();
        abilitiesGrayPanes2 = new ArrayList<>();
        abilitiesOrangePanes = new ArrayList<>();
        abilitiesYellowPanes = new ArrayList<>();
        abilitiesHoppers = new ArrayList<>();
        abilities = new ArrayList<>();

        abilitiesRedPanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(0,0).setMaterial(Material.RED_STAINED_GLASS_PANE).setName(" ").build());
        abilitiesRedPanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(8,0).setMaterial(Material.RED_STAINED_GLASS_PANE).setName(" ").build());
        abilitiesRedPanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(1,1).setMaterial(Material.RED_STAINED_GLASS_PANE).setName(" ").build());
        abilitiesRedPanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(2,1).setMaterial(Material.RED_STAINED_GLASS_PANE).setName(" ").build());
        abilitiesRedPanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(6,1).setMaterial(Material.RED_STAINED_GLASS_PANE).setName(" ").build());
        abilitiesRedPanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(7,1).setMaterial(Material.RED_STAINED_GLASS_PANE).setName(" ").build());
        abilitiesRedPanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(1,3).setMaterial(Material.RED_STAINED_GLASS_PANE).setName(" ").build());
        abilitiesRedPanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(2,3).setMaterial(Material.RED_STAINED_GLASS_PANE).setName(" ").build());
        abilitiesRedPanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(6,3).setMaterial(Material.RED_STAINED_GLASS_PANE).setName(" ").build());
        abilitiesRedPanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(7,3).setMaterial(Material.RED_STAINED_GLASS_PANE).setName(" ").build());
        abilitiesRedPanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(0,4).setMaterial(Material.RED_STAINED_GLASS_PANE).setName(" ").build());
        abilitiesRedPanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(8,4).setMaterial(Material.RED_STAINED_GLASS_PANE).setName(" ").build());

        abilitiesOrangePanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(8,1).setMaterial(Material.ORANGE_STAINED_GLASS_PANE).setName(" ").build());
        abilitiesOrangePanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(0,1).setMaterial(Material.ORANGE_STAINED_GLASS_PANE).setName(" ").build());
        abilitiesOrangePanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(8,3).setMaterial(Material.ORANGE_STAINED_GLASS_PANE).setName(" ").build());
        abilitiesOrangePanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(0,3).setMaterial(Material.ORANGE_STAINED_GLASS_PANE).setName(" ").build());
        abilitiesOrangePanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(7,2).setMaterial(Material.ORANGE_STAINED_GLASS_PANE).setName(" ").build());
        abilitiesOrangePanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(1,2).setMaterial(Material.ORANGE_STAINED_GLASS_PANE).setName(" ").build());

        abilitiesYellowPanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(0,2).setMaterial(Material.YELLOW_STAINED_GLASS_PANE).setName(" ").build());
        abilitiesYellowPanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(8,2).setMaterial(Material.YELLOW_STAINED_GLASS_PANE).setName(" ").build());

        abilitiesHoppers.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(3,1).setMaterial(Material.HOPPER).setName(" ").build());
        abilitiesHoppers.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(4,1).setMaterial(Material.HOPPER).setName(" ").build());
        abilitiesHoppers.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(5,1).setMaterial(Material.HOPPER).setName(" ").build());

        abilitiesGrayPanes2.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(2,2).setMaterial(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build());
        abilitiesGrayPanes2.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(6,2).setMaterial(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build());
        abilitiesGrayPanes2.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(3,3).setMaterial(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build());
        abilitiesGrayPanes2.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(4,3).setMaterial(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build());
        abilitiesGrayPanes2.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(5,3).setMaterial(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build());
        abilitiesGrayPanes2.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(3,1).setMaterial(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build());
        abilitiesGrayPanes2.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(4,1).setMaterial(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build());
        abilitiesGrayPanes2.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(5,1).setMaterial(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build());

//        abilitiesGrayPanes1.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(4,4).setMaterial(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build());
        abilitiesGrayPanes1.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(2,5).setMaterial(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build());
        abilitiesGrayPanes1.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(3,5).setMaterial(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build());
        abilitiesGrayPanes1.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(5,5).setMaterial(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build());
        abilitiesGrayPanes1.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(6,5).setMaterial(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build());

        abilities.addAll(abilitiesRedPanes);
        abilities.addAll(abilitiesYellowPanes);
        abilities.addAll(abilitiesOrangePanes);
        abilities.addAll(abilitiesGrayPanes2);

    }

    public CustomGUI(Player player, String guiId ) {

        this.buttons = new ArrayList<CustomGUIButton>();
        this.guiId = guiId;
        this.player = player;

    }

    public CustomGUI setArg1( String arg ) {
        this.arg = arg;
        return this;
    }
    public CustomGUI setArg2( String arg ) {
        this.arg2 = arg;
        return this;
    }

    public CustomGUI setArg3( String arg ) {
        this.arg3 = arg;
        return this;
    }

    public void load(){

        if ( player.getGameMode() == GameMode.SPECTATOR ) return;

        switch (guiId) {
            case "VOTE_MODE_MENU": {

                this.buttons.add(new CustomGUIButton("VOTE_TEAMS").setArgs("").setXY(5, 1).setMaterial(Material.LEATHER_HELMET).setName(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Командная игра").setLore(null).build());

                loadButtons("§8Проголосуйте за сложность", buttons, player, 27);

                break;
            }
            case "SHOP":{

                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_TRADE, 0.7F, 1.2F);
                player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 0.2F, 1.0F);
//                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_LIBRARIAN, 1.0F, 1.0F);

                GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
                Game game = gamePlayer.getGame();
                CurrentTeam team = game.getPlayerTeam(gamePlayer);

                ShopInstance shop = gamePlayer.getCustomGUIShopInstance();

                int length = shop.categories.size();

                for ( int i = 0; i < length; i++ ) {
                    ShopCategory category = shop.categories.get(i);
                    this.buttons.add(new CustomGUIButton("GOGUI").setArgs("SHOP_CATEGORY",category.getId()).setXY(1 + i % 7, 1 + (i / 7) ).setItemstack(category.visualItem.getItem()).build());
                }

                ArrayList<String> upgradesDescription = new ArrayList<>();
                upgradesDescription.add("");
                upgradesDescription.add(i18nonly("team_upgrades_description1"));
                upgradesDescription.add(i18nonly("team_upgrades_description2").replace("%chance%", FlowerUtils.swordsChance + ""));
                upgradesDescription.add(i18nonly("team_upgrades_description3").replace("%chance%", FlowerUtils.axesChance + ""));

                this.buttons.add(new CustomGUIButton("GOGUI").setArgs("TEAM_UPGRADES").setXY(7, 3).setName(i18nonly("team_upgrades_name")).setMaterial(Material.BLAZE_POWDER).setLore(upgradesDescription).build());

                loadButtons(ChatColor.DARK_GRAY + "Магазин", "anicloud:bw_menu_5core", buttons, player, 45);
                break;
            }
            case "SHOP_CATEGORY": {

                player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 0.2F, 1.0F);
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_LIBRARIAN, 1.0F, 1.0F);

                GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
                Game game = gamePlayer.getGame();
                    CurrentTeam team = game.getPlayerTeam(gamePlayer);

                ShopInstance customGUIShop = gamePlayer.getCustomGUIShopInstance();

                ShopCategory category = customGUIShop.categories.get(0);

                for ( ShopCategory cat : customGUIShop.categories ) {
                    if ( cat.getId() == arg ) {
                        category = cat;
                        break;
                    }
                }

                int length = category.items.size();

                for ( int i = 0; i < length; i++ ) {
                    PurchasableItem item = category.items.get(i);

                    ArrayList<String> lore = item.generateDescription(null);

                    this.buttons.add(new CustomGUIButton("BUY_ITEM").setArgs( item.getId() ).setXY(1 + i % 7, 1 + (i / 7)).setItemstack( item.item.getItem() ).addLore(lore).build());
                }

                backButtonPrepare("SHOP", 8, 4);

                loadButtons(ChatColor.DARK_GRAY + "Магазин", "anicloud:bw_menu_5core", buttons, player, 45);
                break;
            }
            case "TEAM_UPGRADES": {

                GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
                CurrentTeam team = gamePlayer.getGame().getPlayerTeam(gamePlayer);

                GameFlag temp;
                boolean available;
                boolean bought;

                temp = GameFlag.INTELLECT_LEVEL_1;
                bought = team.teamFlags.contains(temp);
                this.buttons.add(new CustomGUIButton("BUY_UPGRADE").setArgs(temp.name()).setXY(2, 1).setMaterial(Material.valueOf("COPPER_INGOT")).setName(temp.getName()).setLore(temp.getDescription()).addHiddenEnchantment(bought).build());

                temp = GameFlag.INTELLECT_LEVEL_2;
                available = (team.teamFlags.contains(temp.getPreviousFlag()) || temp.getPreviousFlag() == null) && !team.teamFlags.contains(temp.getNeighbourFlag());
                bought = team.teamFlags.contains(temp);
                this.buttons.add(new CustomGUIButton("BUY_UPGRADE").setArgs(temp.name()).setXY(2, 2).setMaterial(available ? Material.IRON_INGOT : Material.GRAY_DYE).setName(temp.getName()).setLore(temp.getDescription()).addHiddenEnchantment(bought).build());

                temp = GameFlag.INTELLECT_LEVEL_3;
                available = (team.teamFlags.contains(temp.getPreviousFlag()) || temp.getPreviousFlag() == null) && !team.teamFlags.contains(temp.getNeighbourFlag());
                bought = team.teamFlags.contains(temp);
                this.buttons.add(new CustomGUIButton("BUY_UPGRADE").setArgs(temp.name()).setXY(2, 3).setMaterial(available ? Material.GLASS_BOTTLE : Material.GRAY_DYE).setName(temp.getName()).setLore(temp.getDescription()).addHiddenEnchantment(bought).build());

                temp = GameFlag.INTELLECT_LEVEL_4A;
                available = (team.teamFlags.contains(temp.getPreviousFlag()) || temp.getPreviousFlag() == null) && !team.teamFlags.contains(temp.getNeighbourFlag());
                bought = team.teamFlags.contains(temp);
                this.buttons.add(new CustomGUIButton("BUY_UPGRADE").setArgs(temp.name()).setXY(1, 4).setMaterial(available ? Material.IRON_SWORD : Material.GRAY_DYE).setName(temp.getName()).setLore(temp.getDescription()).addHiddenEnchantment(bought).build());

                temp = GameFlag.INTELLECT_LEVEL_4B;
                available = (team.teamFlags.contains(temp.getPreviousFlag()) || temp.getPreviousFlag() == null) && !team.teamFlags.contains(temp.getNeighbourFlag());
                bought = team.teamFlags.contains(temp);
                this.buttons.add(new CustomGUIButton("BUY_UPGRADE").setArgs(temp.name()).setXY(3, 4).setMaterial(available ? Material.SHIELD : Material.GRAY_DYE).setName(temp.getName()).setLore(temp.getDescription()).addHiddenEnchantment(bought).build());

                //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

                temp = GameFlag.AGILITY_LEVEL_1;
                bought = team.teamFlags.contains(temp);
                this.buttons.add(new CustomGUIButton("BUY_UPGRADE").setArgs(temp.name()).setXY(4, 4).setMaterial(Material.SUGAR).setName(temp.getName()).setLore(temp.getDescription()).addHiddenEnchantment(bought).build());

                temp = GameFlag.AGILITY_LEVEL_2;
                available = (team.teamFlags.contains(temp.getPreviousFlag()) || temp.getPreviousFlag() == null) && !team.teamFlags.contains(temp.getNeighbourFlag());
                bought = team.teamFlags.contains(temp);
                this.buttons.add(new CustomGUIButton("BUY_UPGRADE").setArgs(temp.name()).setXY(4, 3).setMaterial(available ? Material.BOW : Material.GRAY_DYE).setName(temp.getName()).setLore(temp.getDescription()).addHiddenEnchantment(bought).build());

                temp = GameFlag.AGILITY_LEVEL_3;
                available = (team.teamFlags.contains(temp.getPreviousFlag()) || temp.getPreviousFlag() == null) && !team.teamFlags.contains(temp.getNeighbourFlag());
                bought = team.teamFlags.contains(temp);
                this.buttons.add(new CustomGUIButton("BUY_UPGRADE").setArgs(temp.name()).setXY(4, 2).setMaterial(available ? Material.SNOWBALL : Material.GRAY_DYE).setName(temp.getName()).setLore(temp.getDescription()).addHiddenEnchantment(bought).build());

                temp = GameFlag.AGILITY_LEVEL_4A;
                available = (team.teamFlags.contains(temp.getPreviousFlag()) || temp.getPreviousFlag() == null) && !team.teamFlags.contains(temp.getNeighbourFlag());
                bought = team.teamFlags.contains(temp);
                this.buttons.add(new CustomGUIButton("BUY_UPGRADE").setArgs(temp.name()).setXY(3, 1).setMaterial(available ? Material.WITHER_ROSE : Material.GRAY_DYE).setName(temp.getName()).setLore(temp.getDescription()).addHiddenEnchantment(bought).build());

                temp = GameFlag.AGILITY_LEVEL_4B;
                available = (team.teamFlags.contains(temp.getPreviousFlag()) || temp.getPreviousFlag() == null) && !team.teamFlags.contains(temp.getNeighbourFlag());
                bought = team.teamFlags.contains(temp);
                this.buttons.add(new CustomGUIButton("BUY_UPGRADE").setArgs(temp.name()).setXY(5, 1).setMaterial(available ? Material.SPIDER_EYE : Material.GRAY_DYE).setName(temp.getName()).setLore(temp.getDescription()).addHiddenEnchantment(bought).build());

                //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

                temp = GameFlag.VITALITY_LEVEL_1;
                bought = team.teamFlags.contains(temp);
                this.buttons.add(new CustomGUIButton("BUY_UPGRADE").setArgs(temp.name()).setXY(6, 1).setMaterial(Material.LEATHER_CHESTPLATE).setName(temp.getName()).setLore(temp.getDescription()).addHiddenEnchantment(bought).build());

                temp = GameFlag.VITALITY_LEVEL_2;
                available = (team.teamFlags.contains(temp.getPreviousFlag()) || temp.getPreviousFlag() == null) && !team.teamFlags.contains(temp.getNeighbourFlag());
                bought = team.teamFlags.contains(temp);
                this.buttons.add(new CustomGUIButton("BUY_UPGRADE").setArgs(temp.name()).setXY(6, 2).setMaterial(available ? Material.LEATHER : Material.GRAY_DYE).setName(temp.getName()).setLore(temp.getDescription()).addHiddenEnchantment(bought).build());

                temp = GameFlag.VITALITY_LEVEL_3;
                available = (team.teamFlags.contains(temp.getPreviousFlag()) || temp.getPreviousFlag() == null) && !team.teamFlags.contains(temp.getNeighbourFlag());
                bought = team.teamFlags.contains(temp);
                this.buttons.add(new CustomGUIButton("BUY_UPGRADE").setArgs(temp.name()).setXY(6, 3).setMaterial(available ? Material.FEATHER : Material.GRAY_DYE).setName(temp.getName()).setLore(temp.getDescription()).addHiddenEnchantment(bought).build());

                temp = GameFlag.VITALITY_LEVEL_4A;
                available = (team.teamFlags.contains(temp.getPreviousFlag()) || temp.getPreviousFlag() == null) && !team.teamFlags.contains(temp.getNeighbourFlag());
                bought = team.teamFlags.contains(temp);
                this.buttons.add(new CustomGUIButton("BUY_UPGRADE").setArgs(temp.name()).setXY(5, 4).setMaterial(available ? Material.GOLDEN_APPLE : Material.GRAY_DYE).setName(temp.getName()).setLore(temp.getDescription()).addHiddenEnchantment(bought).build());

                temp = GameFlag.VITALITY_LEVEL_4B;
                available = (team.teamFlags.contains(temp.getPreviousFlag()) || temp.getPreviousFlag() == null) && !team.teamFlags.contains(temp.getNeighbourFlag());
                bought = team.teamFlags.contains(temp);
                this.buttons.add(new CustomGUIButton("BUY_UPGRADE").setArgs(temp.name()).setXY(7, 4).setMaterial(available ? Material.GOLDEN_CARROT : Material.GRAY_DYE).setName(temp.getName()).setLore(temp.getDescription()).addHiddenEnchantment(bought).build());


                backButtonPrepare("SHOP");

                loadButtons("Командные улучшения","anicloud:bw_menu_6corecorepaths", buttons, player,  54);

                break;
            }
            case "TEAM_SELECTION": {

                GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
                Game game = gamePlayer.getGame();

                gamePlayer.setLastMenuVisited(MenuType.TEAM_SELECT);

                ArrayList<Team> teams = new ArrayList<>(game.getTeams());

                int i = 0;

                for ( i = 0; i < teams.size(); i++ ) {
                    Team team = teams.get(i);
                    ItemStack stack = Main.getConfigurator().readDefinedItem("team-select", Main.isLegacy() ? "WOOL" : "WHITE_WOOL");
                    ItemStack teamStack = Main.applyColor(team.color, stack, true);
                    List<GamePlayer> playersInTeam = game.getPlayersInTeam(team);
                    int playersInTeamCount = playersInTeam.size();
                    ArrayList<String> lore = new ArrayList<>(formatLore(team, game));
                    this.buttons.add(new CustomGUIButton("JOIN_TEAM").setArgs(team.getName()).setXY(1 + i % 7, 1 + i / 7 ).setItemstack(teamStack).setName(
                            i18nonly("team_select_item")
                                    .replace("%teamName%", team.color.chatColor + team.getName())
                                    .replace("%inTeam%", String.valueOf(playersInTeamCount))
                                    .replace("%maxInTeam%", String.valueOf(team.maxPlayers))
                    ).setLore(lore).build());
                }

                loadButtons( FlowerUtils.teamSelectionMenuName,"anicloud:bw_menu_3core", buttons, player, 27);
                break;


            }
            case "ABILITIES_READONLY": {

                Player target = Bukkit.getPlayer(arg);

                GamePlayer gamePlayer = Main.getPlayerGameProfile(target);
                Game game = gamePlayer.getGame();
                ArrayList<LoadedAbility> loadedAbilities = gamePlayer.loadedAbilities;

                ArrayList lore;

                LoadedAbility loadedAbility;

                for (int i = 0; i < loadedAbilities.size(); i++) {
                    loadedAbility = loadedAbilities.get(i);
                    lore = new ArrayList<>();
                    ItemStack item = CustomStack.getInstance("anicloud:slot_locked").getItemStack();
                    String name = ColoursManager.gray + "Пустой слот";

                    lore.add("");
//                    lore.add(ChatColor.GRAY + "Максимальная уровень способности");
//                    lore.add(ChatColor.GRAY + "в этом слоте - " + ChatColor.WHITE + "" + ChatColor.BOLD + (i + 1) );
//                    lore.add("");

                    if (!loadedAbility.isEmpty()) {
                        name = loadedAbility.getOwnedAbility().getAbility().getName();
                        item = loadedAbility.getOwnedAbility().getAbility().getAbilityGuiItem();
                        lore.addAll(loadedAbility.getOwnedAbility().parseDescription( i + 1));
                    }
//                    else {
//                        lore.add("");
//                        lore.add(ChatColor.GRAY + "Кликните, чтобы выбрать способность...");
//                    }
                    if ( game.getAllAbilitiesMode() ) {
                        lore.add("");
                        lore.addAll(FlowerUtils.alphaWarning);
                    }
                    this.buttons.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(3 + i, 2).setItemstack(item).setName(name).setLore(lore).build());
                }

                this.buttons.addAll(abilities);

                backButtonPrepare("EXIT");

                loadButtons( "Способности игрока " + arg, "anicloud:bw_menu_6empty", buttons, player, 54);

                break;
            }
            case "ABILITIES": {


                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_LIBRARIAN, 1.0F, 1.0F);

                GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
                Game game = gamePlayer.getGame();
                ArrayList<LoadedAbility> loadedAbilities = gamePlayer.loadedAbilities;
                gamePlayer.setLastMenuVisited(MenuType.ABILITIES_SELECT);
                ArrayList lore;

                LoadedAbility loadedAbility;

                for (int i = 0; i < loadedAbilities.size(); i++) {
                    loadedAbility = loadedAbilities.get(i);
                    lore = new ArrayList<>();
                    ItemStack item = new ItemStack(Material.LIME_DYE);
                    String name = ColoursManager.green + "Пустой слот";

                    lore.add("");
                    lore.add(ChatColor.GRAY + "Максимальная уровень способности");
                    lore.add(ChatColor.GRAY + "в этом слоте - " + ChatColor.WHITE + "" + ChatColor.BOLD + (i + 1) );

                    if (!loadedAbility.isEmpty()) {
                        name = loadedAbility.getOwnedAbility().getAbility().getName();
                        item = loadedAbility.getOwnedAbility().getAbility().getAbilityGuiItem();//loadedAbility.getOwnedAbility().ownedLevel, Math.min(loadedAbility.getOwnedAbility().ownedLevel, i + 1),
                        lore.addAll(loadedAbility.getOwnedAbility().parseDescription( i + 1 ));
                        lore.addAll(loadedAbility.getOwnedAbility().generateInventoryInfo(player, true, false ,false ,true));
                    }
                    else {
                        lore.add("");
                        lore.add(ColoursManager.green + "Кликните, чтобы выбрать способность...");
                    }

                    this.buttons.add(new CustomGUIButton("GOGUI").setArgs("SELECT_ABILITY", i + "").setXY(3 + i, 2).setItemstack(item).setName(name).setLore(lore).build());
                }


                this.buttons.add(new CustomGUIButton("RANDOMIZE_ALL_ABILITIES").setArgs("").setXY( 4, 5 ).setMaterial(Material.RECOVERY_COMPASS).setName(ColoursManager.purple + "Выбрать ВСЕ способности случайно...").build());

                this.buttons.addAll(abilities);
                this.buttons.addAll(abilitiesHoppers);
                this.buttons.addAll(abilitiesGrayPanes1);

                backButtonPrepare("EXIT");

                loadButtons( "Выберите Способности...", "anicloud:bw_menu_6empty", buttons, player, 54);

                break;
            }
            case "SELECT_ABILITY": {

                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);

                GamePlayer gamePlayer = Main.getPlayerGameProfile(player);

                int page = arg2 == null ? 1 : Integer.parseInt(arg2);

                int slot = Integer.parseInt(arg);

                ArrayList<OwnedAbility> ownedAbilities = gamePlayer.ownedAbilities;

                ArrayList<String> lore;


                for ( int i = FlowerUtils.bwInventoryRows * ( page - 1 ) * 9; i < Math.min(ownedAbilities.size(), FlowerUtils.bwInventoryRows * 9 * page); i++  ) {
                    OwnedAbility ownedAbility = ownedAbilities.get(i);
                    lore = ownedAbility.parseDescription(slot + 1 );

                    lore.addAll(ownedAbility.generateInventoryInfo(player, true, false, false, true));

                    int j = i - FlowerUtils.bwInventoryRows * ( page - 1 ) * 9;

                    if (ownedAbility.isAvailable()) {
                        this.buttons.add(new CustomGUIButton("SELECT_ABILITY_INTO_SLOT").setArgs(ownedAbility.getAbility().getId(), arg, arg2).setXY( j % 9, j / 9 ).setItemstack(ownedAbility.getAbility().getAbilityGuiItem()).setName(ownedAbility.getAbility().getName()).setLore(lore).build());
                    }
                    else {
                        buttons.add(new CustomGUIButton("ERROR").setArgs("").setXY(j % 9, j / 9).setMaterial(Material.GRAY_DYE).setName(ownedAbility.getAbility().getName()).setLore(lore).build());
                    }
                }

                if ( FlowerUtils.bwInventoryRows * ( page ) * 9 < ownedAbilities.size() )
                    this.buttons.add(new CustomGUIButton("GOGUI").setArgs("SELECT_ABILITY", slot + "", page + 1 + "").setXY( 1, 5 ).setItemstack(CustomStack.getInstance("anicloud:button_next").getItemStack()).setName( ColoursManager.gray + "Следующая страница").build());

                if ( page > 1 )
                    this.buttons.add(new CustomGUIButton("GOGUI").setArgs("SELECT_ABILITY", slot + "", page - 1 + "").setXY( 0, 5 ).setItemstack(CustomStack.getInstance("anicloud:button_back").getItemStack()).setName( ColoursManager.gray + "Предыдущая страница").build());


                this.buttons.add(new CustomGUIButton("RANDOMIZE_ABILITY_INTO_SLOT").setArgs(arg).setXY( 4, 5 ).setMaterial(Material.RECOVERY_COMPASS).setName(ColoursManager.purple + "Выбрать случайно...").build());
                this.buttons.addAll(abilitiesGrayPanes1);

                backButtonPrepare("ABILITIES");

                loadButtons("Выберите Способности...", "anicloud:bw_menu_6empty", buttons, player, 54);

                break;
            }
            case "FORGE": {

                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_TOOLSMITH, 0.3F, 1.0F);

                backButtonPrepare("INVENTORY", 8, 4);

                GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
                ArrayList<ActiveForgeRecipe> activeForgeRecipes = gamePlayer.activeForgeRecipes;

                gamePlayer.setLastMenuVisited(MenuType.FORGE);

                int allowedSlots = FlowerUtils.defaultForgeSlots;

                if (player.hasPermission("bw.forge.extraslots.4")) allowedSlots += 4;
                else
                if (player.hasPermission("bw.forge.extraslots.3")) allowedSlots += 3;
                else
                if (player.hasPermission("bw.forge.extraslots.2")) allowedSlots += 2;
                else
                if (player.hasPermission("bw.forge.extraslots.1")) allowedSlots += 1;


                for ( int i = 0; i < FlowerUtils.maxForgeSlots; i++ ) {
                    if (i < allowedSlots) {
                        boolean found = false;
                        for (ActiveForgeRecipe activeForgeRecipe : activeForgeRecipes) {
                            if (activeForgeRecipe.getSlot() == i) {
                                found = true;
                                ItemStack itemStack = activeForgeRecipe.getRecipe().getGuiItem();

                                Timestamp timeNow = Timestamp.valueOf(LocalDateTime.now());

                                ArrayList<String> lore = new ArrayList<>(activeForgeRecipe.getRecipe().getRecipeProgress(gamePlayer));

                                Timestamp st = activeForgeRecipe.getStartTime();
                                long overallSeconds = TimeUnit.SECONDS.convert(timeNow.getTime() - st.getTime(), TimeUnit.MILLISECONDS);
                                //Bukkit.getConsoleSender().sendMessage("secondsPassed:" + overallSeconds + "    recipetime:" + activeForgeRecipe.getRecipe().getTimeInSeconds());
                                if (overallSeconds < activeForgeRecipe.getRecipe().getTimeInSeconds()) {

                                    overallSeconds = activeForgeRecipe.getRecipe().getTimeInSeconds() - overallSeconds;
//                                    lore.add("");
//                                    lore.add(ColoursManager.gray + "Начало:");
//                                    lore.add(ColoursManager.gray + "" + st.getDate() + "." + (st.getMonth() + 1) + "." + (1900 + st.getYear()) + " " + st.getHours() + ":" + st.getMinutes());
                                    lore.add("");
                                    lore.add(ColoursManager.gray + "Конец через:");
                                    lore.addAll(NotificationManager.parseTime(overallSeconds));
                                    lore.add("");

                                    //TODO recipe cancellation
                                    buttons.add(new CustomGUIButton("GOGUI").setArgs("FORGE", i + "").setXY(i + 1, 2).setItemstack(itemStack).setLore(lore).build());
                                    buttons.add(new CustomGUIButton("ERROR").setXY(i + 1, 1).setMaterial(Material.YELLOW_STAINED_GLASS_PANE).setName(" ").build());
                                    buttons.add(new CustomGUIButton("ERROR").setXY(i + 1, 3).setMaterial(Material.YELLOW_STAINED_GLASS_PANE).setName(" ").build());
                                } else {
                                    lore.add("");
                                    lore.add(ColoursManager.green + "Рецепт завершён.");
                                    buttons.add(new CustomGUIButton("FORGE_RECIPE_CLAIM").setArgs(i + "").setXY(i + 1, 2).setItemstack(itemStack).setLore(lore).build());
                                    buttons.add(new CustomGUIButton("ERROR").setXY(i + 1, 1).setMaterial(Material.LIME_STAINED_GLASS_PANE).setName(" ").build());
                                    buttons.add(new CustomGUIButton("ERROR").setXY(i + 1, 3).setMaterial(Material.LIME_STAINED_GLASS_PANE).setName(" ").build());

                                }
                                break;
                            }
                        }
                        if (found) continue;
                        buttons.add(new CustomGUIButton("GOGUI").setArgs("CHOOSE_FORGE_RECIPE", i + "").setXY(i + 1, 2).setMaterial(Material.LIME_DYE).setName(ColoursManager.green + "Запустить новый крафт...").build());
                        buttons.add(new CustomGUIButton("ERROR").setXY(i + 1, 1).setMaterial(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build());
                        buttons.add(new CustomGUIButton("ERROR").setXY(i + 1, 3).setMaterial(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build());
                    }
                    else {
                        ArrayList<String> lore = new ArrayList<>();
                        lore.add("");
                        switch (i) {
                            case 3: {
                                lore.add(ColoursManager.blue + "Доступно с привилегии " + ChatColor.WHITE + IconsManager.requestIcon(IconType.VIP, player));
                                break;
                            }
                            case 4: {
                                lore.add(ColoursManager.orange + "Доступно с привилегии " + ChatColor.WHITE + IconsManager.requestIcon(IconType.PREMIUM, player));
                                break;
                            }
                            case 5: {
                                lore.add(ColoursManager.yellow + "Доступно с привилегии " + ChatColor.WHITE + IconsManager.requestIcon(IconType.LEGEND, player));
                                break;
                            }
                            case 6: {
                                lore.add(ColoursManager.red + "Доступно с привилегии " + ChatColor.WHITE + IconsManager.requestIcon(IconType.TITAN, player));
                                break;
                            }
                        }
                        buttons.add(new CustomGUIButton("ERROR").setArgs("").setXY(i + 1, 2).setItemstack(CustomStack.getInstance("anicloud:slot_locked").getItemStack()).setName(ColoursManager.red + "Слот заблокирован").setLore(lore).build());
                        buttons.add(new CustomGUIButton("ERROR").setXY(i + 1, 1).setMaterial(Material.BLACK_STAINED_GLASS_PANE).setName(" ").build());
                        buttons.add(new CustomGUIButton("ERROR").setXY(i + 1, 3).setMaterial(Material.BLACK_STAINED_GLASS_PANE).setName(" ").build());
                    }
                }
                loadButtons( "Кузница", "anicloud:bw_menu_5core", buttons, player, 45);

                break;
            }
            case "CHOOSE_FORGE_RECIPE": {

                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);

                backButtonPrepare("FORGE");

                GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
                ArrayList<ForgeRecipe> forgeRecipes = gamePlayer.availableRecipes;


//                Bukkit.getConsoleSender().sendMessage("arg = " + arg);
//                Bukkit.getConsoleSender().sendMessage("arg2 = " + arg2);
//                Bukkit.getConsoleSender().sendMessage("arg3 = " + arg3);

                int page = arg2 == null ? 1 : Integer.parseInt(arg2);


                for ( int i = FlowerUtils.bwInventoryRows * ( page - 1 ) * 9; i < Math.min(forgeRecipes.size(), FlowerUtils.bwInventoryRows * 9 * page); i++  ) {
                    ForgeRecipe recipe = forgeRecipes.get(i);
                    ItemStack itemStack = recipe.getGuiItem();

                    ArrayList<String> lore = new ArrayList<>(recipe.getRecipeProgress(gamePlayer));
                    lore.addAll(recipe.getRecipeTime());

                    boolean canAfford = gamePlayer.ownedResourceBundle.isContainingBundle(recipe.getInput());


                    int j = i - FlowerUtils.bwInventoryRows * ( page - 1 ) * 9;

                    if (canAfford) {
                        buttons.add(new CustomGUIButton("FORGE_RECIPE_SET").setArgs(arg, recipe.getId(), page + "").setXY(j % 9, j / 9).setItemstack(itemStack).setName(ColoursManager.green + recipe.getName()).setLore(lore).build());
                    }
                    else {
                        buttons.add(new CustomGUIButton("ERROR").setArgs("").setXY(j % 9, j / 9).setMaterial(Material.GRAY_DYE).setName(ColoursManager.red + recipe.getName()).setLore(lore).build());
                    }
                }

                if ( FlowerUtils.bwInventoryRows * ( page ) * 9 < forgeRecipes.size() )
                    this.buttons.add(new CustomGUIButton("GOGUI").setArgs("CHOOSE_FORGE_RECIPE", arg, page + 1 + "").setXY( 1, 5 ).setItemstack(CustomStack.getInstance("anicloud:button_next").getItemStack()).setName( ColoursManager.gray + "Следующая страница").build());

                if ( page > 1 )
                    this.buttons.add(new CustomGUIButton("GOGUI").setArgs("CHOOSE_FORGE_RECIPE", arg, page - 1 + "").setXY( 0, 5 ).setItemstack(CustomStack.getInstance("anicloud:button_back").getItemStack()).setName( ColoursManager.gray + "Предыдущая страница").build());


                loadButtons( " ", "anicloud:bw_menu_6empty", buttons, player, 54);

                break;
            }
            case "INVENTORY": {

                backButtonPrepare("EXIT", 8, 2);

                ItemStack head = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta meta = (SkullMeta) head.getItemMeta();
                meta.setOwningPlayer(player);
                head.setItemMeta(meta);

                Main.getPlayerGameProfile(player).setLastMenuVisited(MenuType.INVENTORY);

                buttons.add(new CustomGUIButton("BLOCK").setArgs("").setXY( 1, 1 ).setItemstack(head).setName( ColoursManager.gray + "Игрок " + player.getDisplayName()).build());
                buttons.add(new CustomGUIButton("GOGUI").setArgs("INVENTORY_ABILITIES").setXY( 3, 1 ).setMaterial(Material.BLAZE_POWDER).setName( ColoursManager.blue + "Способности").build());
                buttons.add(new CustomGUIButton("GOGUI").setArgs("INVENTORY_RESOURCES").setXY( 4, 1 ).setItemstack(CustomStack.getInstance("anicloud:catalyst_epic").getItemStack()).setName( ColoursManager.blue + "Ресурсы").build());
                buttons.add(new CustomGUIButton("GOGUI").setArgs("CASES").setXY( 6, 1 ).setItemstack(CustomStack.getInstance("anicloud:book_evil2").getItemStack()).setName( ColoursManager.blue + "Сундуки").build());
                buttons.add(new CustomGUIButton("GOGUI").setArgs("FORGE").setXY( 7, 1 ).setMaterial(Material.ANVIL).setName( ColoursManager.blue + "Кузница").build());
//                buttons.add(new CustomGUIButton("GOGUI").setArgs("FORGE").setXY( 5, 1 ).setItemstack(CustomStack.getInstance("anicloud:motherboard").getItemStack()).setName( ColoursManager.blue + "Рецепты").build());
//                buttons.add(new CustomGUIButton("GOGUI").setArgs("INVENTORY_STATS").setXY( 6, 1 ).setMaterial(Material.IRON_SWORD).setName( ColoursManager.blue + "Статистика").build());
//                buttons.add(new CustomGUIButton("ERROR").setXY( 4, 2 ).setItemstack(CustomStack.getInstance("anicloud:button_tick").getItemStack()).setName( ChatColor.RED + "ЗАБЛОКИРОВАНО").build());
//                buttons.add(new CustomGUIButton("ERROR").setXY( 5, 2 ).setItemstack(CustomStack.getInstance("_iainternal:icon_cancel").getItemStack()).setName( ChatColor.RED + "ЗАБЛОКИРОВАНО").build());

                loadButtons( "Инвентарь", "anicloud:bw_menu_3core", buttons, player, 27);
                break;
            }
            case "INVENTORY_ABILITIES": {

                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);

                GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
                ArrayList<OwnedAbility> ownedAbilities = gamePlayer.ownedAbilities;

                ArrayList<String> lore;
                int page = arg == null ? 1 : Integer.parseInt(arg);

                for ( int i = FlowerUtils.bwInventoryRows * ( page - 1 ) * 9; i < Math.min(ownedAbilities.size(), FlowerUtils.bwInventoryRows * 9 * page); i++ ) {
                    OwnedAbility ownedAbility = ownedAbilities.get(i);
                    lore = ownedAbility.parseDescription( ownedAbility.ownedLevel );

                    lore.addAll(ownedAbility.generateInventoryInfo(player, true, ownedAbility.isOwned(), true, true));

                    ItemStack item;
                    String action;
                    String arg;
                    if ( ownedAbility.isAvailable() ) {
                        item = ownedAbility.getAbility().getAbilityGuiItem();
                        action = "GOGUI";
                        arg = "INVENTORY_ABILITY_INFO";
                    } else {
                        item = new ItemStack(Material.GRAY_DYE);
                        action = "ERROR";
                        arg = "";
                    }

                    int j = i - FlowerUtils.bwInventoryRows * ( page - 1 ) * 9;

                    this.buttons.add(new CustomGUIButton(action).setArgs(arg, ownedAbility.getAbility().getId(), page + "").setXY( j % 9, j / 9 ).setItemstack(item).setName(ownedAbility.getAbility().getName()).setLore(lore).build());
                }

                if ( FlowerUtils.bwInventoryRows * ( page ) * 9 < ownedAbilities.size() )
                    this.buttons.add(new CustomGUIButton("GOGUI").setArgs("INVENTORY_ABILITIES", page + 1 + "").setXY( 1, 5 ).setItemstack(CustomStack.getInstance("anicloud:button_next").getItemStack()).setName( ColoursManager.gray + "Следующая страница").build());

                if ( page > 1 )
                    this.buttons.add(new CustomGUIButton("GOGUI").setArgs("INVENTORY_ABILITIES", page - 1 + "").setXY( 0, 5 ).setItemstack(CustomStack.getInstance("anicloud:button_back").getItemStack()).setName( ColoursManager.gray + "Предыдущая страница").build());


                backButtonPrepare("INVENTORY");

                loadButtons( "Инвентарь","anicloud:bw_menu_6empty", buttons, player, 54);
                break;
            }
            case "INVENTORY_ABILITY_INFO": {

                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);

                String abilityId = arg;

//                Bukkit.getConsoleSender().sendMessage("arg = " + arg);
//                Bukkit.getConsoleSender().sendMessage("arg2 = " + arg2);
//                Bukkit.getConsoleSender().sendMessage("arg3 = " + arg3);

                GamePlayer gamePlayer = Main.getPlayerGameProfile(player);

                ArrayList<String> lore;

                OwnedAbility ownedAbility = gamePlayer.getOwnedAbilityById(abilityId);
                lore = ownedAbility.parseDescription( FlowerUtils.maxAbilityLevel );

                lore.addAll(ownedAbility.generateInventoryInfo(player, true, false, false ,true));

                ItemStack item = ownedAbility.getAbility().getAbilityGuiItem();

                this.buttons.add(new CustomGUIButton("ERROR").setArgs("").setXY( 1, 1 ).setItemstack(item).setLore(lore).build());

                ArrayList<String> upgradeLore = new ArrayList<>();
                upgradeLore.addAll(ownedAbility.generateInventoryInfo(player, false, true, false, false));
                if (ownedAbility.isEnoughToUpgrade()) {
                    upgradeLore.addAll(NotificationManager.getSufficientMaterialsMessage());
                    this.buttons.add(new CustomGUIButton("GOGUI").setArgs("INVENTORY_ABILITY_INFO_UPGRADE_CONFIRMATION", abilityId, arg2).setXY(3, 1).setItemstack(CustomStack.getInstance("anicloud:emerald_plate").getItemStack()).setName(ColoursManager.green + "Улучшить").setLore(upgradeLore).build());
                }
                else {
                    upgradeLore.addAll(NotificationManager.getInsufficientMaterialsMessage());
                    this.buttons.add(new CustomGUIButton("ERROR").setArgs("").setXY(3, 1).setItemstack(CustomStack.getInstance("anicloud:slot_locked").getItemStack()).setName( ChatColor.STRIKETHROUGH + "" + ColoursManager.red + "Улучшить").setLore(upgradeLore).build());
                }

                ArrayList<String> disassembleLore = new ArrayList<>();
                disassembleLore.addAll(ownedAbility.generateInventoryInfo(player, false, false, true, false));
                if (ownedAbility.duplicatesOwned > 0) {
                    disassembleLore.addAll(NotificationManager.getSufficientMaterialsMessage());
                    this.buttons.add(new CustomGUIButton("GOGUI").setArgs("INVENTORY_ABILITY_INFO_DISASSEMBLE_CONFIRMATION", abilityId, arg2).setXY( 4, 1 ).setItemstack( CustomStack.getInstance("anicloud:raw_iron_wood").getItemStack() ).setName( ColoursManager.green + "Разобрать").setLore(disassembleLore).build());
                }
                else {
                    disassembleLore.addAll(NotificationManager.getInsufficientMaterialsMessage());
                    this.buttons.add(new CustomGUIButton("ERROR").setArgs("").setXY( 4, 1 ).setItemstack( CustomStack.getInstance("anicloud:slot_locked").getItemStack() ).setName( ChatColor.STRIKETHROUGH + "" + ColoursManager.red + "Разобрать").setLore(disassembleLore).build());
                }

                backButtonPrepare("INVENTORY_ABILITIES", 8, 2, arg2);

                loadButtons( "Инвентарь","anicloud:bw_menu_3core", buttons, player, 27);
                break;
            }
            case "INVENTORY_ABILITY_INFO_UPGRADE_CONFIRMATION": {

                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);


                String abilityId = arg;
                GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
                ArrayList<String> loreInfo = new ArrayList<>();
                ArrayList<String> loreConfirm = new ArrayList<>();
                OwnedAbility ownedAbility = gamePlayer.getOwnedAbilityById(abilityId);

                loreInfo.addAll(ownedAbility.parseDescription(3));
                loreConfirm.addAll(ownedAbility.generateInventoryInfo(player, false, true, false, false));

                this.buttons.add(new CustomGUIButton("ERROR").setArgs("").setXY( 2, 1 ).setItemstack( ownedAbility.getAbility().getAbilityGuiItem() ).setName( ColoursManager.blue + "Информация").setLore(loreInfo).build());
                this.buttons.add(new CustomGUIButton("ABILITY_UPGRADE").setArgs(abilityId).setXY( 4, 1 ).setItemstack( CustomStack.getInstance("anicloud:button_tick").getItemStack() ).setName( ColoursManager.green + "Улучшить").setLore(loreConfirm).build());
                this.buttons.add(new CustomGUIButton("GOGUI").setArgs("INVENTORY_ABILITY_INFO",abilityId).setXY( 6, 1 ).setItemstack( CustomStack.getInstance("_iainternal:icon_cancel").getItemStack() ).setName( ColoursManager.red + "Отменить").build());


                loadButtons( ColoursManager.darkGray + "Улучшить " + ownedAbility.getAbility().getNameWithIcon(player) + ColoursManager.darkGray + " ?","anicloud:bw_menu_3core", buttons, player, 27);
                break;
            }
            case "INVENTORY_ABILITY_INFO_DISASSEMBLE_CONFIRMATION": {

                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);

                String abilityId = arg;
                GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
                ArrayList<String> loreInfo = new ArrayList<>();
                ArrayList<String> loreConfirm = new ArrayList<>();
                OwnedAbility ownedAbility = gamePlayer.getOwnedAbilityById(abilityId);

                loreInfo.addAll(ownedAbility.parseDescription(3));
                loreConfirm.addAll(ownedAbility.generateInventoryInfo(player, false, false, true, false));

                this.buttons.add(new CustomGUIButton("ERROR").setArgs("").setXY( 2, 1 ).setItemstack( ownedAbility.getAbility().getAbilityGuiItem() ).setName( ColoursManager.blue + "Информация").setLore(loreInfo).build());
                this.buttons.add(new CustomGUIButton("ABILITY_DISASSEMBLE").setArgs(abilityId).setXY( 4, 1 ).setItemstack( CustomStack.getInstance("anicloud:button_tick").getItemStack() ).setName( ColoursManager.green + "Разобрать").setLore(loreConfirm).build());
                this.buttons.add(new CustomGUIButton("GOGUI").setArgs("INVENTORY_ABILITY_INFO", abilityId).setXY( 6, 1 ).setItemstack( CustomStack.getInstance("_iainternal:icon_cancel").getItemStack() ).setName( ColoursManager.red + "Отменить").build());


                loadButtons( ColoursManager.darkGray + "Разобрать " + ownedAbility.getAbility().getNameWithIcon(player) + ColoursManager.darkGray + " ?","anicloud:bw_menu_3core", buttons, player, 27);
                break;
            }
            case "INVENTORY_RESOURCES": {

                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);

                GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
                ArrayList<OwnedResource> ownedResources = gamePlayer.ownedResourceBundle.resources;

                int page = arg == null ? 1 : Integer.parseInt(arg);

                for ( int i = FlowerUtils.bwInventoryRows * ( page - 1 ) * 9; i < Math.min(ownedResources.size(), FlowerUtils.bwInventoryRows * 9 * page); i++ ) {
                    OwnedResource ownedResource = ownedResources.get(i);

                    int j = i - FlowerUtils.bwInventoryRows * ( page - 1 ) * 9;

                    ArrayList<String> lore = new ArrayList<>();

                    lore.add("");
                    lore.addAll(ownedResource.generateInventoryLore());

                    this.buttons.add(new CustomGUIButton("BLOCK").setArgs(arg, ownedResource.getType().toString(), page + "").setXY( j % 9, j / 9 ).setItemstack(ownedResource.getType().getGuiItem()).setName(ownedResource.getType().getName()).setLore(lore).build());
                }

                if ( FlowerUtils.bwInventoryRows * ( page ) * 9 < ownedResources.size() )
                    this.buttons.add(new CustomGUIButton("GOGUI").setArgs("INVENTORY_RESOURCES", page + 1 + "").setXY( 1, 5 ).setItemstack(CustomStack.getInstance("anicloud:button_next").getItemStack()).setName( ColoursManager.gray + "Следующая страница").build());

                if ( page > 1 )
                    this.buttons.add(new CustomGUIButton("GOGUI").setArgs("INVENTORY_RESOURCES", page - 1 + "").setXY( 0, 5 ).setItemstack(CustomStack.getInstance("anicloud:button_back").getItemStack()).setName( ColoursManager.gray + "Предыдущая страница").build());


                backButtonPrepare("INVENTORY");

                loadButtons( "Инвентарь","anicloud:bw_menu_6empty", buttons, player, 54);
                break;
            }
            case "CASES": {

                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);

                ItemStack locked = CustomStack.getInstance("anicloud:slot_locked").getItemStack();

                for ( int i = 0; i < ResourceChestBook.resourceChests.size(); i++ ) {
                    ResourceChest resourceChest = ResourceChestBook.resourceChests.get(i);
                    ArrayList<String> lore = new ArrayList<>();

                    lore.addAll(resourceChest.getDescription());

                    ResourceBundle resourceBundle = new ResourceBundle();
                    resourceBundle.addResource(resourceChest.getResourcePrice());
                    lore.addAll(resourceBundle.parseResourcesForPlayer(player, true));

                    ItemStack itemStack = new ItemStack(resourceChest.getMaterial());
                    if (resourceChest.getIaId() != "" && resourceChest.getIaId() != null) itemStack = CustomStack.getInstance(resourceChest.getIaId()).getItemStack();

                    if (Main.getPlayerGameProfile(player).ownedResourceBundle.isContainingResource(resourceChest.getResourcePrice())) {
                        lore.addAll(NotificationManager.getSufficientMaterialsMessage());
                        this.buttons.add(new CustomGUIButton("GOGUI").setArgs("OPEN_CASE", resourceChest.getId()).setXY(i % 7 + 1, i / 7 + 1).setItemstack(itemStack).setName(ColoursManager.blue + resourceChest.getName()).setLore(lore).build());
                    }
                    else {
                        lore.addAll(NotificationManager.getInsufficientMaterialsMessage());
                        this.buttons.add(new CustomGUIButton("ERROR").setArgs("").setXY(i % 7 + 1, i / 7 + 1).setItemstack(locked).setName(ColoursManager.red + resourceChest.getName()).setLore(lore).build());
                    }
                }

                backButtonPrepare("INVENTORY", 8, 2);
                loadButtons( "Инвентарь","anicloud:bw_menu_3empty", buttons, player, 27);
                break;
            }
            case "OPEN_CASE": {

                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);

                ResourceChest resourceChest = ResourceChestBook.getChestById(arg);
                int ticksDuration = 200;
                int ticksPerMove = 3;

                loadButtons( "Инвентарь","anicloud:bw_menu_4empty", buttons, player, 36);
                AnimationGUIRoll animationGUIRoll = new AnimationGUIRoll( player, ticksDuration, ticksPerMove, 40, resourceChest, Sound.ITEM_LODESTONE_COMPASS_LOCK, "CASES" );
                animationGUIRoll.runTaskTimer(Main.getInstance(), 1, 1);

                break;
            }
            case "EXIT": {
                player.closeInventory();
                break;
            }
            default: {
                break;
            }
        }
    }

    void backButtonPrepare(String guiName) {
        backButtonPrepare(guiName, gobackButtonX, gobackButtonY);
    }

    void backButtonPrepare(String guiName, int x, int y) {
        backButtonPrepare(guiName, x, y, "");
    }

    void backButtonPrepare(String guiName, int x, int y, String arg) {
        ItemStack itemStack = getBackItem();
        buttons.add( new CustomGUIButton( "GOGUI").setArgs(guiName, arg).setXY(x, y).setItemstack(itemStack).setName( ChatColor.RED + "Назад").build());
    }

    public static ItemStack getBackItem() {
        return CustomStack.getInstance("_iainternal:icon_cancel").getItemStack();
    }

    private ItemStack getNextItem() {
        return CustomStack.getInstance("anicloud:button_next").getItemStack();
    }

    static void loadButtons(String title, String guiId,ArrayList<CustomGUIButton> buttons, Player player, int area) {
        //Inventory gui = Bukkit.createInventory(null, area, title);

        TexturedInventoryWrapper inventory = new TexturedInventoryWrapper(
                player,
                area,
                title, 100, -12,
                new FontImageWrapper(guiId)
        );
        inventory.showInventory(player);

        Inventory gui = player.getOpenInventory().getTopInventory();

        Integer buttons_length = buttons.size();
        for (Integer i = 0; i < buttons_length; i++)
        {
            CustomGUIButton button = buttons.get(i);
            gui.setItem( button.getX() + button.getY() * 9, button.getItem());
        }
//        player.openInventory(gui);
    }

    static void loadButtons(String title,ArrayList<CustomGUIButton> buttons, Player player, int area) {
        loadButtons(title, "anicloud:auction_options",buttons, player, area);
    }


    static void softLoadButtons(ArrayList<CustomGUIButton> buttons, Player player) {

        clearButtons(player);
        Inventory gui = player.getOpenInventory().getTopInventory();

        Integer buttons_length = buttons.size();
        for (Integer i = 0; i < buttons_length; i++)
        {
            CustomGUIButton button = buttons.get(i);

            gui.setItem( button.getX() + button.getY() * 9, button.getItem());
        }
    }

    public static void clearButtons( Player player ) {

        Inventory gui = player.getOpenInventory().getTopInventory();


        for (int i = 0; i < gui.getSize(); i++)
        {
            gui.setItem(i, null);
        }

        player.openInventory(gui);
    }


}
