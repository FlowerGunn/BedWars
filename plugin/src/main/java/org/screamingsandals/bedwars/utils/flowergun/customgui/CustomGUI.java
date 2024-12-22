package org.screamingsandals.bedwars.utils.flowergun.customgui;


import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.FontImages.FontImageWrapper;
import dev.lone.itemsadder.api.FontImages.TexturedInventoryWrapper;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.Team;
import org.screamingsandals.bedwars.game.CurrentTeam;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.statistics.PlayerStatistic;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.LoadedAbility;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.OwnedAbility;
import org.screamingsandals.bedwars.utils.flowergun.cases.AnimationGUIRoll;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.*;
import org.screamingsandals.bedwars.utils.flowergun.managers.ColoursManager;
import org.screamingsandals.bedwars.utils.flowergun.managers.IconsManager;
import org.screamingsandals.bedwars.utils.flowergun.managers.NotificationManager;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;
import org.screamingsandals.bedwars.utils.flowergun.shoputils.PurchasableItem;
import org.screamingsandals.bedwars.utils.flowergun.shoputils.ShopCategory;
import org.screamingsandals.bedwars.utils.flowergun.shoputils.ShopInstance;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    public static ArrayList<CustomGUIButton> simpleAbilitiesHoppers;
    public static ArrayList<CustomGUIButton> abilities;

    static {
        abilitiesRedPanes = new ArrayList<>();
        abilitiesGrayPanes1 = new ArrayList<>();
        abilitiesGrayPanes2 = new ArrayList<>();
        abilitiesOrangePanes = new ArrayList<>();
        abilitiesYellowPanes = new ArrayList<>();
        abilitiesHoppers = new ArrayList<>();
        simpleAbilitiesHoppers = new ArrayList<>();
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

        simpleAbilitiesHoppers.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(1,3).setMaterial(Material.HOPPER).setName(" ").build());
        simpleAbilitiesHoppers.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(2,3).setMaterial(Material.HOPPER).setName(" ").build());
        simpleAbilitiesHoppers.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(3,3).setMaterial(Material.HOPPER).setName(" ").build());
        simpleAbilitiesHoppers.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(4,3).setMaterial(Material.HOPPER).setName(" ").build());
        simpleAbilitiesHoppers.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(5,3).setMaterial(Material.HOPPER).setName(" ").build());
        simpleAbilitiesHoppers.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(6,3).setMaterial(Material.HOPPER).setName(" ").build());
        simpleAbilitiesHoppers.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(7,3).setMaterial(Material.HOPPER).setName(" ").build());

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
                int page = 0;

                if ( arg2 != null ) {
                    page = Integer.parseInt(arg2);
                    category = customGUIShop.categories.get(page);
                }
                else
                for ( int i = 0; i < customGUIShop.categories.size(); i++ ) {
                    ShopCategory cat = customGUIShop.categories.get(i);
                    if ( cat.getId() == arg ) {
                        category = cat;
                        page = i;
                        break;
                    }
                }

                int length = category.items.size();

                for ( int i = 0; i < length; i++ ) {
                    PurchasableItem item = category.items.get(i);

                    ArrayList<String> lore = item.generateDescription();

                    this.buttons.add(new CustomGUIButton("BUY_ITEM").setArgs( item.getId() ).setXY(1 + i % 7, 1 + (i / 7)).setItemstack( item.item.getItem() ).addLore(lore).build());
                }



                if ( page < customGUIShop.categories.size() - 1 ) {
//                    this.buttons.add(new CustomGUIButton("GOGUI").setArgs("SHOP_CATEGORY", "", page + 1 + "").setXY(8, 1).setItemstack(CustomStack.getInstance("_iainternal:icon_right_blue").getItemStack()).setName(ColoursManager.gray + " ").build());
                    this.buttons.add(new CustomGUIButton("GOGUI").setArgs("SHOP_CATEGORY", "", page + 1 + "").setXY(8, 2).setItemstack(CustomStack.getInstance("_iainternal:icon_right_blue").getItemStack()).setName(ColoursManager.gray + " ").build());
//                    this.buttons.add(new CustomGUIButton("GOGUI").setArgs("SHOP_CATEGORY", "", page + 1 + "").setXY(8, 3).setItemstack(CustomStack.getInstance("_iainternal:icon_right_blue").getItemStack()).setName(ColoursManager.gray + " ").build());
                }
                if ( page >= 1 ) {
//                    this.buttons.add(new CustomGUIButton("GOGUI").setArgs("SHOP_CATEGORY", "", page - 1 + "").setXY(0, 1).setItemstack(CustomStack.getInstance("_iainternal:icon_left_blue").getItemStack()).setName(ColoursManager.gray + " ").build());
                    this.buttons.add(new CustomGUIButton("GOGUI").setArgs("SHOP_CATEGORY", "", page - 1 + "").setXY(0, 2).setItemstack(CustomStack.getInstance("_iainternal:icon_left_blue").getItemStack()).setName(ColoursManager.gray + " ").build());
//                    this.buttons.add(new CustomGUIButton("GOGUI").setArgs("SHOP_CATEGORY", "", page - 1 + "").setXY(0, 3).setItemstack(CustomStack.getInstance("_iainternal:icon_left_blue").getItemStack()).setName(ColoursManager.gray + " ").build());
                }

                backButtonPrepare("SHOP", 8, 4);

                loadButtons(ChatColor.DARK_GRAY + "Магазин", "anicloud:bw_menu_5core", buttons, player, 45);
                break;
            }
            case "TEAM_UPGRADES": {

                GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
                CurrentTeam team = gamePlayer.getGame().getPlayerTeam(gamePlayer);


                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_CARTOGRAPHER, 0.2F, 0.8F);

                GameFlag temp;
                boolean available;
                boolean bought;

                temp = GameFlag.INTELLECT_LEVEL_1;
                bought = team.teamFlags.contains(temp);
                this.buttons.add(new CustomGUIButton("BUY_UPGRADE").setArgs(temp.name()).setXY(2, 1).setMaterial(Material.RAW_COPPER).setName(temp.getName()).setLore(temp.getDescription()).addHiddenEnchantment(bought).build());

                temp = GameFlag.INTELLECT_LEVEL_2;
                available = (team.teamFlags.contains(temp.getPreviousFlag()) || temp.getPreviousFlag() == null) && !team.teamFlags.contains(temp.getNeighbourFlag());
                bought = team.teamFlags.contains(temp);
                this.buttons.add(new CustomGUIButton("BUY_UPGRADE").setArgs(temp.name()).setXY(2, 2).setMaterial(available ? Material.GOLD_INGOT : Material.GRAY_DYE).setName(temp.getName()).setLore(temp.getDescription()).addHiddenEnchantment(bought).build());

                temp = GameFlag.INTELLECT_LEVEL_3;
                available = (team.teamFlags.contains(temp.getPreviousFlag()) || temp.getPreviousFlag() == null) && !team.teamFlags.contains(temp.getNeighbourFlag());
                bought = team.teamFlags.contains(temp);
                this.buttons.add(new CustomGUIButton("BUY_UPGRADE").setArgs(temp.name()).setXY(2, 3).setMaterial(available ? Material.HONEY_BOTTLE : Material.GRAY_DYE).setName(temp.getName()).setLore(temp.getDescription()).addHiddenEnchantment(bought).build());

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
                this.buttons.add(new CustomGUIButton("BUY_UPGRADE").setArgs(temp.name()).setXY(6, 2).setMaterial(available ? Material.GOLDEN_APPLE : Material.GRAY_DYE).setName(temp.getName()).setLore(temp.getDescription()).addHiddenEnchantment(bought).build());

                temp = GameFlag.VITALITY_LEVEL_3;
                available = (team.teamFlags.contains(temp.getPreviousFlag()) || temp.getPreviousFlag() == null) && !team.teamFlags.contains(temp.getNeighbourFlag());
                bought = team.teamFlags.contains(temp);
                this.buttons.add(new CustomGUIButton("BUY_UPGRADE").setArgs(temp.name()).setXY(6, 3).setMaterial(available ? Material.FEATHER : Material.GRAY_DYE).setName(temp.getName()).setLore(temp.getDescription()).addHiddenEnchantment(bought).build());

                temp = GameFlag.VITALITY_LEVEL_4A;
                available = (team.teamFlags.contains(temp.getPreviousFlag()) || temp.getPreviousFlag() == null) && !team.teamFlags.contains(temp.getNeighbourFlag());
                bought = team.teamFlags.contains(temp);
                this.buttons.add(new CustomGUIButton("BUY_UPGRADE").setArgs(temp.name()).setXY(5, 4).setMaterial(available ? Material.LEATHER : Material.GRAY_DYE).setName(temp.getName()).setLore(temp.getDescription()).addHiddenEnchantment(bought).build());

                temp = GameFlag.VITALITY_LEVEL_4B;
                available = (team.teamFlags.contains(temp.getPreviousFlag()) || temp.getPreviousFlag() == null) && !team.teamFlags.contains(temp.getNeighbourFlag());
                bought = team.teamFlags.contains(temp);
                this.buttons.add(new CustomGUIButton("BUY_UPGRADE").setArgs(temp.name()).setXY(7, 4).setMaterial(available ? Material.PHANTOM_MEMBRANE : Material.GRAY_DYE).setName(temp.getName()).setLore(temp.getDescription()).addHiddenEnchantment(bought).build());


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

                    lore.addAll(loadedAbility.getOwnedAbility().parseDescription(i + 1));
                    lore.addAll(loadedAbility.getOwnedAbility().generateInventoryInfo(player, false, false, false, true));

//
//                    if (!loadedAbility.isEmpty()) {
//                        name = loadedAbility.getOwnedAbility().getAbility().getName();
//                        item = loadedAbility.getOwnedAbility().getAbility().getAbilityGuiItem();
//                        lore.addAll(loadedAbility.getOwnedAbility().parseDescription( i + 1));
//                    }
//
//                    boolean enchanted = false;
//                    if ( loadedAbility.getOwnedAbility().getAbility().isPublicTesting() ) {
//                        lore.add("");
//                        lore.addAll(FlowerUtils.alphaWarning);
//                    } else if ( loadedAbility.getOwnedAbility().getAbility().isDailyTrial() ) {
//                        lore.add("");
//                        lore.addAll(FlowerUtils.trialMessage);
//                        enchanted = true;
//                    }
                    this.buttons.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(3 + i, 2).setItemstack(item).setName(name).setLore(lore).addHiddenEnchantment(loadedAbility.getOwnedAbility().getAbility().isDailyTrial()).build());
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

                boolean simpleMode = gamePlayer.getSetting(PlayerConfigType.SIMPLIFIED_ABILITY_SELECTION);



                if ( simpleMode ) {

                } else {

                }

                for (int i = 0; i < loadedAbilities.size(); i++) {
                    loadedAbility = loadedAbilities.get(i);
                    lore = new ArrayList<>();
                    ItemStack item = new ItemStack(Material.LIME_DYE);
                    String name = simpleMode ? ColoursManager.darkGray + "- Пустой слот" : ColoursManager.green + "> Пустой слот";

                    lore.add("");
                    lore.add(ChatColor.GRAY + "Максимальная уровень способности");
                    lore.add(ChatColor.GRAY + "в этом слоте - " + ChatColor.WHITE + "" + ChatColor.BOLD + (i + 1));

                    if (!loadedAbility.isEmpty()) {
                        name = loadedAbility.getOwnedAbility().getAbility().getName();
                        item = loadedAbility.getOwnedAbility().getAbility().getAbilityGuiItem();//loadedAbility.getOwnedAbility().ownedLevel, Math.min(loadedAbility.getOwnedAbility().ownedLevel, i + 1),
                        lore.addAll(loadedAbility.getOwnedAbility().parseDescription(i + 1));
                        lore.addAll(loadedAbility.getOwnedAbility().generateInventoryInfo(player, true, false, false, true));
                    } else if ( simpleMode ) {
                        lore.add("");
                        lore.add(ColoursManager.orange + "Выберите категорию для автоустановки способностей");
                        lore.add(ColoursManager.orange + "или отключите автоустановку в левом нижнем углу.");
                    } else {
                        lore.add("");
                        lore.add(ColoursManager.green + "Кликните, чтобы выбрать способность...");
                    }

                    this.buttons.add(new CustomGUIButton(simpleMode ? "ERROR" : "GOGUI").setArgs("SELECT_ABILITY", i + "").setXY(3 + i, 2).setItemstack(item).setName(name).setLore(lore).build());
                    if ( simpleMode ) {
                        this.buttons.add(new CustomGUIButton("RANDOMIZE_ALL_ABILITIES_BY_CATEGORY").setArgs(AbilityCategory.FIGHTER.toString()).setXY(1 , 4).setMaterial(Material.IRON_SWORD).setName(AbilityCategory.FIGHTER.getColor() + "> Я хочу играть Мечником").setLore(AbilityCategory.FIGHTER.generateFullDescription()).build());
                        this.buttons.add(new CustomGUIButton("RANDOMIZE_ALL_ABILITIES_BY_CATEGORY").setArgs(AbilityCategory.ARCHER.toString()).setXY(2 , 4).setMaterial(Material.BOW).setName(AbilityCategory.ARCHER.getColor() + "> Я хочу играть Лучником").setLore(AbilityCategory.ARCHER.generateFullDescription()).build());
                        this.buttons.add(new CustomGUIButton("RANDOMIZE_ALL_ABILITIES_BY_CATEGORY").setArgs(AbilityCategory.TANK.toString()).setXY(3 , 4).setMaterial(Material.IRON_AXE).setName(AbilityCategory.TANK.getColor() + "> Я хочу играть Танком").setLore(AbilityCategory.TANK.generateFullDescription()).build());
                        this.buttons.add(new CustomGUIButton("RANDOMIZE_ALL_ABILITIES_BY_CATEGORY").setArgs(AbilityCategory.SCOUT.toString()).setXY(4 , 4).setMaterial(Material.IRON_PICKAXE).setName(AbilityCategory.SCOUT.getColor() + "> Я хочу играть Скаутом").setLore(AbilityCategory.SCOUT.generateFullDescription()).build());
                        this.buttons.add(new CustomGUIButton("RANDOMIZE_ALL_ABILITIES_BY_CATEGORY").setArgs(AbilityCategory.MANIPULATOR.toString()).setXY(5 , 4).setMaterial(Material.FLINT_AND_STEEL).setName(AbilityCategory.MANIPULATOR.getColor() + "> Я хочу играть Манипулятором").setLore(AbilityCategory.MANIPULATOR.generateFullDescription()).build());
                        this.buttons.add(new CustomGUIButton("RANDOMIZE_ALL_ABILITIES_BY_CATEGORY").setArgs(AbilityCategory.ECONOMIST.toString()).setXY(6 , 4).setMaterial(Material.IRON_INGOT).setName(AbilityCategory.ECONOMIST.getColor() + "> Я хочу играть Экономистом").setLore(AbilityCategory.ECONOMIST.generateFullDescription()).build());
                        this.buttons.add(new CustomGUIButton("RANDOMIZE_ALL_ABILITIES_BY_CATEGORY").setArgs(AbilityCategory.HEALER.toString()).setXY(7 , 4).setMaterial(Material.GOLDEN_APPLE).setName(AbilityCategory.HEALER.getColor() + "> Я хочу играть Лекарем").setLore(AbilityCategory.HEALER.generateFullDescription()).build());
                    }

                }
                this.buttons.addAll(abilities);
                if ( !simpleMode ) {
                    this.buttons.addAll(abilitiesHoppers);
                    this.buttons.add(new CustomGUIButton("RANDOMIZE_ALL_ABILITIES").setArgs("").setXY( 4, 5 ).setMaterial(Material.RECOVERY_COMPASS).setName(ColoursManager.purple + "> Выбрать ВСЕ способности случайно...").build());
                }

                boolean value = gamePlayer.getSetting(PlayerConfigType.SIMPLIFIED_ABILITY_SELECTION);
                this.buttons.add(new CustomGUIButton("SWITCH_SIMPLE_ABILITY_SELECTION").setArgs("").setXY( 0, 5 ).setItemstack( value ? new ItemStack(Material.LIME_DYE) : new ItemStack( Material.GRAY_DYE )  ).setName(  value ? ColoursManager.red + "> Упрощённые способности [ВКЛЮЧЕНО]" : ColoursManager.green + "> Упрощённые способности [ОТКЛЮЧЕНО]"  ).build());

                this.buttons.addAll(abilitiesGrayPanes1);

                if (simpleMode)
                    this.buttons.addAll(simpleAbilitiesHoppers);

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
                        this.buttons.add(new CustomGUIButton("SELECT_ABILITY_INTO_SLOT").setArgs(ownedAbility.getAbility().getId(), arg, arg2).setXY( j % 9, j / 9 ).setItemstack(ownedAbility.getAbility().getAbilityGuiItem()).setName(ownedAbility.getAbility().getName()).setLore(lore).addHiddenEnchantment(ownedAbility.getAbility().isDailyTrial()).build());
                    }
                    else {
                        buttons.add(new CustomGUIButton("ERROR").setArgs("").setXY(j % 9, j / 9).setMaterial(Material.GRAY_DYE).setName(ownedAbility.getAbility().getName()).setLore(lore).build());
                    }
                }

                if ( FlowerUtils.bwInventoryRows * ( page ) * 9 < ownedAbilities.size() )
                    this.buttons.add(new CustomGUIButton("GOGUI").setArgs("SELECT_ABILITY", slot + "", page + 1 + "").setXY( 1, 5 ).setItemstack(CustomStack.getInstance("anicloud:button_next").getItemStack()).setName( ColoursManager.gray + "Следующая страница").build());

                if ( page > 1 )
                    this.buttons.add(new CustomGUIButton("GOGUI").setArgs("SELECT_ABILITY", slot + "", page - 1 + "").setXY( 0, 5 ).setItemstack(CustomStack.getInstance("anicloud:button_back").getItemStack()).setName( ColoursManager.gray + "Предыдущая страница").build());


                this.buttons.add(new CustomGUIButton("RANDOMIZE_ABILITY_INTO_SLOT").setArgs(arg).setXY( 4, 5 ).setMaterial(Material.RECOVERY_COMPASS).setName(ColoursManager.purple + "> Выбрать случайно...").build());
                this.buttons.addAll(abilitiesGrayPanes1);

                backButtonPrepare("ABILITIES");

                loadButtons("Выберите Способности...", "anicloud:bw_menu_6empty", buttons, player, 54);

                break;
            }
            case "FORGE": {

//                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
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
                                    lore.add(ColoursManager.green + "> Рецепт завершён.");
                                    buttons.add(new CustomGUIButton("FORGE_RECIPE_CLAIM").setArgs(i + "").setXY(i + 1, 2).setItemstack(itemStack).setLore(lore).build());
                                    buttons.add(new CustomGUIButton("ERROR").setXY(i + 1, 1).setMaterial(Material.LIME_STAINED_GLASS_PANE).setName(" ").build());
                                    buttons.add(new CustomGUIButton("ERROR").setXY(i + 1, 3).setMaterial(Material.LIME_STAINED_GLASS_PANE).setName(" ").build());

                                }
                                break;
                            }
                        }
                        if (found) continue;
                        buttons.add(new CustomGUIButton("GOGUI").setArgs("CHOOSE_FORGE_RECIPE", i + "").setXY(i + 1, 2).setMaterial(Material.LIME_DYE).setName(ColoursManager.green + "> Запустить новый крафт...").build());
                        buttons.add(new CustomGUIButton("ERROR").setXY(i + 1, 1).setMaterial(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build());
                        buttons.add(new CustomGUIButton("ERROR").setXY(i + 1, 3).setMaterial(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build());
                    }
                    else {
                        ArrayList<String> lore = new ArrayList<>();
                        lore.add("");
                        switch (i) {
                            case 3: {
                                lore.add(ColoursManager.light_blue + "Доступно с привилегии " + ChatColor.WHITE + IconsManager.requestIcon(IconType.VIP, player));
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

                player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_LEATHER, 0.8F, 0.7F);


                backButtonPrepare("EXIT", 8, 2);

                ItemStack head = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta meta = (SkullMeta) head.getItemMeta();
                meta.setOwningPlayer(player);
                head.setItemMeta(meta);

                GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
                gamePlayer.setLastMenuVisited(MenuType.INVENTORY);

                PlayerStatistic statistic = Main.getPlayerStatisticsManager().getStatistic(player);

                ArrayList<String> lore = new ArrayList<>();
                lore.add("");
                lore.add( ColoursManager.darkGray + " Ваша статистика: ");
                lore.add("");
                lore.add( ColoursManager.gray + " Игр сыграно: " + ColoursManager.white + statistic.getGames());
                lore.add( ColoursManager.gray + " Побед: " + ColoursManager.green + statistic.getWins());
                lore.add( ColoursManager.gray + " Проигрышей: " + ColoursManager.red + statistic.getLoses());
                lore.add("");
                lore.add( ColoursManager.gray + " Убийств: " + ColoursManager.red + statistic.getKills());
                lore.add( ColoursManager.gray + " Смертей: " + ColoursManager.darkRed + statistic.getDeaths());
                lore.add( ColoursManager.gray + " У/С: " + ColoursManager.orange + statistic.getKD());
                lore.add("");
                lore.add( ColoursManager.gray + " Сломано кроватей: " + ColoursManager.elytra + statistic.getDestroyedBeds());
                lore.add("");
                lore.add( ColoursManager.gray + " Рейтинг: " + ColoursManager.light_blue + statistic.getScore());
                lore.add("");
                buttons.add(new CustomGUIButton("GOGUI").setArgs("SETTINGS").setXY( 1, 1 ).setItemstack(head).setName( ColoursManager.light_blue + "" + ChatColor.BOLD + "> Настройки").setLore(lore).build());

                lore = new ArrayList<>();
                lore.add("");
                lore.add(ColoursManager.darkGray + " Инвентарь способностей игрока. Способности");
                lore.add(ColoursManager.darkGray + " устанавливаются в 3 слота перед началом");
                lore.add(ColoursManager.darkGray + " матча и влияют на стиль игры игрока.");
                lore.add("");
                boolean locked = gamePlayer.getSetting( PlayerConfigType.DEFAULT_ABILITIES_AUTOSELECT );
                if ( locked ) {
                    lore.add(ColoursManager.yellow + " Сыграйте ещё " + ColoursManager.white + Math.max( FlowerUtils.unlockAbilitySelectionGamesPlayedRequirement - statistic.getGames(), 1 ) + ColoursManager.yellow + " игр," );
                    lore.add(ColoursManager.yellow + " чтобы разблокировать меню способностей." );
                    lore.add("");
                }
                buttons.add(new CustomGUIButton(!locked ? "GOGUI" : "ERROR").setArgs("INVENTORY_ABILITIES").setXY( 3, 1 ).setItemstack( !locked ? new ItemStack(Material.BLAZE_POWDER) : CustomStack.getInstance("anicloud:slot_locked").getItemStack() ).setName( locked ? ColoursManager.gray + "> Способности [ЗАБЛОКИРОВАНО]" : ColoursManager.light_blue + "" + ChatColor.BOLD + "> Способности").setLore(lore).build());

                lore = new ArrayList<>();
                lore.add("");
                lore.add(ColoursManager.darkGray + " Инвентарь ресурсов игрока. Ресурсы");
                lore.add(ColoursManager.darkGray + " добываются за различные действия ");
                lore.add(ColoursManager.darkGray + " в игре и используются для крафта ");
                lore.add(ColoursManager.darkGray + " или улучшения способностей и косметики.");
                lore.add("");
                locked = gamePlayer.getSetting( PlayerConfigType.HIDE_RESOURCES );
                if ( locked ) {
                    lore.add(ColoursManager.yellow + " Сыграйте ещё " + ColoursManager.white + Math.max( FlowerUtils.unlockResourcesGamesPlayedRequirement - statistic.getGames(), 1 ) + ColoursManager.yellow + " игр," );
                    lore.add(ColoursManager.yellow + " чтобы разблокировать меню ресурсов." );
                    lore.add("");
                }
                buttons.add(new CustomGUIButton(!locked ? "GOGUI" : "ERROR").setArgs("INVENTORY_RESOURCES").setXY( 4, 1 ).setItemstack( !locked ? CustomStack.getInstance("anicloud:catalyst_epic").getItemStack() : CustomStack.getInstance("anicloud:slot_locked").getItemStack()).setName(  locked ? ColoursManager.gray + "> Ресурсы [ЗАБЛОКИРОВАНО]" : ColoursManager.light_blue + "" + ChatColor.BOLD + "> Ресурсы").setLore(lore).build());

                lore = new ArrayList<>();
                lore.add("");
                lore.add(ColoursManager.darkGray + " Меню чтения книг. Книги раскрывают");
                lore.add(ColoursManager.darkGray + " секреты легендарных бойцов BedWars'a");
                lore.add(ColoursManager.darkGray + " и обучают игрока новым навыкам. ");
                lore.add("");
                locked = gamePlayer.getSetting( PlayerConfigType.HIDE_RESOURCES );
                if ( locked ) {
                    lore.add(ColoursManager.yellow + " Сыграйте ещё " + ColoursManager.white + Math.max( FlowerUtils.unlockResourcesGamesPlayedRequirement - statistic.getGames(), 1 ) + ColoursManager.yellow + " игр," );
                    lore.add(ColoursManager.yellow + " чтобы начать расшифровывать" );
                    lore.add(ColoursManager.yellow + " потерянные знания." );
                    lore.add("");
                }
                buttons.add(new CustomGUIButton(!locked ? "GOGUI" : "ERROR").setArgs("CASES").setXY( 6, 1 ).setItemstack ( !locked ? CustomStack.getInstance("anicloud:book_evil2").getItemStack() : CustomStack.getInstance("anicloud:slot_locked").getItemStack()).setName(  locked ? ColoursManager.gray + "> Книги [ЗАБЛОКИРОВАНО]" : ColoursManager.light_blue + "" + ChatColor.BOLD + "> Книги").setLore(lore).build());

                lore = new ArrayList<>();
                lore.add("");
                lore.add(ColoursManager.darkGray + " Открыть интерфейс Кузницы. Кузница позволяет");
                lore.add(ColoursManager.darkGray + " объединять простые ресурсы в более редкие и изменять");
                lore.add(ColoursManager.darkGray + " одни способности на другие с помощью катализаторов.");
                lore.add("");
                locked = gamePlayer.getSetting( PlayerConfigType.HIDE_FORGE );
                if ( locked ) {
                    lore.add(ColoursManager.yellow + " Сыграйте ещё " + ColoursManager.white + Math.max( FlowerUtils.unlockForgeGamesPlayedRequirement - statistic.getGames(), 1 ) + ColoursManager.yellow + " игр," );
                    lore.add(ColoursManager.yellow + " чтобы разблокировать Кузницу." );
                    lore.add("");
                }
                buttons.add(new CustomGUIButton(!locked ? "GOGUI" : "ERROR").setArgs("FORGE").setXY( 7, 1 ).setItemstack( !locked ? new ItemStack(Material.ANVIL) : CustomStack.getInstance("anicloud:slot_locked").getItemStack() ).setName( locked ? ColoursManager.gray + "> Кузница [ЗАБЛОКИРОВАНО]" : ColoursManager.light_blue + "" + ChatColor.BOLD + "> Кузница").setLore(lore).build());

//                buttons.add(new CustomGUIButton("GOGUI").setArgs("FORGE").setXY( 5, 1 ).setItemstack(CustomStack.getInstance("anicloud:motherboard").getItemStack()).setName( ColoursManager.light_blue + "Рецепты").build());
//                buttons.add(new CustomGUIButton("GOGUI").setArgs("INVENTORY_STATS").setXY( 6, 1 ).setMaterial(Material.IRON_SWORD).setName( ColoursManager.light_blue + "Статистика").build());
//                buttons.add(new CustomGUIButton("ERROR").setXY( 4, 2 ).setItemstack(CustomStack.getInstance("anicloud:button_tick").getItemStack()).setName( ChatColor.RED + "ЗАБЛОКИРОВАНО").build());
//                buttons.add(new CustomGUIButton("ERROR").setXY( 5, 2 ).setItemstack(CustomStack.getInstance("_iainternal:icon_cancel").getItemStack()).setName( ChatColor.RED + "ЗАБЛОКИРОВАНО").build());

                loadButtons( "Инвентарь", "anicloud:bw_menu_3core", buttons, player, 27);
                break;
            }
            case "SETTINGS": {

                player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_LEATHER, 0.8F, 0.7F);

                backButtonPrepare("INVENTORY", 8, 2);

                GamePlayer gamePlayer = Main.getPlayerGameProfile(player);

                boolean exists;
                boolean value;
                exists = gamePlayer.settingExists(PlayerConfigType.DEFAULT_ABILITIES_AUTOSELECT);
                value = gamePlayer.getSetting(PlayerConfigType.DEFAULT_ABILITIES_AUTOSELECT);
                buttons.add(new CustomGUIButton(exists ? "SWITCH_SETTING" : "ERROR").setArgs(PlayerConfigType.DEFAULT_ABILITIES_AUTOSELECT.toString()).setXY( 1, 1 ).setItemstack( exists ? ( value ? new ItemStack(Material.LIME_DYE) : new ItemStack( Material.GRAY_DYE ) ) : CustomStack.getInstance("anicloud:slot_locked").getItemStack() ).setName( exists ? ( value ? ColoursManager.red + "> Начальные способности [ВКЛЮЧЕНО]" : ColoursManager.green + "> Начальные способности [ОТКЛЮЧЕНО]" ) : ( ColoursManager.darkGray + "> Начальные способности [ЗАБЛОКИРОВАНО]" ) ).build());
                exists = gamePlayer.settingExists(PlayerConfigType.SIMPLIFIED_ABILITY_SELECTION);
                value = gamePlayer.getSetting(PlayerConfigType.SIMPLIFIED_ABILITY_SELECTION);
                buttons.add(new CustomGUIButton(exists ? "SWITCH_SETTING" : "ERROR").setArgs(PlayerConfigType.SIMPLIFIED_ABILITY_SELECTION.toString()).setXY( 2, 1 ).setItemstack( exists ? ( value ? new ItemStack(Material.LIME_DYE) : new ItemStack( Material.GRAY_DYE ) ) : CustomStack.getInstance("anicloud:slot_locked").getItemStack() ).setName( exists ? ( value ? ColoursManager.red + "> Упрощённые способности [ВКЛЮЧЕНО]" : ColoursManager.green + "> Упрощённые способности [ОТКЛЮЧЕНО]" ) : ( ColoursManager.darkGray + "> Упрощённые способности [ЗАБЛОКИРОВАНО]" ) ).build());
                exists = gamePlayer.settingExists(PlayerConfigType.HIDE_RESOURCES);
                value = gamePlayer.getSetting(PlayerConfigType.HIDE_RESOURCES);
                buttons.add(new CustomGUIButton(exists ? "SWITCH_SETTING" : "ERROR").setArgs(PlayerConfigType.HIDE_RESOURCES.toString()).setXY( 3, 1 ).setItemstack( exists ? ( value ? new ItemStack(Material.LIME_DYE) : new ItemStack( Material.GRAY_DYE ) ) : CustomStack.getInstance("anicloud:slot_locked").getItemStack() ).setName( exists ? ( value ? ColoursManager.red + "> Скрыть ресурсы [ВКЛЮЧЕНО]" : ColoursManager.green + "> Скрыть ресурсы [ОТКЛЮЧЕНО]" ) : ( ColoursManager.darkGray + "> Скрыть ресурсы [ЗАБЛОКИРОВАНО]" ) ).build());
                exists = gamePlayer.settingExists(PlayerConfigType.HIDE_FORGE);
                value = gamePlayer.getSetting(PlayerConfigType.HIDE_FORGE);
                buttons.add(new CustomGUIButton(exists ? "SWITCH_SETTING" : "ERROR").setArgs(PlayerConfigType.HIDE_FORGE.toString()).setXY( 4, 1 ).setItemstack( exists ? ( value ? new ItemStack(Material.LIME_DYE) : new ItemStack( Material.GRAY_DYE ) ) : CustomStack.getInstance("anicloud:slot_locked").getItemStack() ).setName( exists ? ( value ? ColoursManager.red + "> Скрыть кузницу [ВКЛЮЧЕНО]" : ColoursManager.green + "> Скрыть кузницу [ОТКЛЮЧЕНО]" ) : ( ColoursManager.darkGray + "> Скрыть кузницу [ЗАБЛОКИРОВАНО]" ) ).build());
                exists = gamePlayer.settingExists(PlayerConfigType.ENABLE_TIPS);
                value = gamePlayer.getSetting(PlayerConfigType.ENABLE_TIPS);
                buttons.add(new CustomGUIButton(exists ? "SWITCH_SETTING" : "ERROR").setArgs(PlayerConfigType.ENABLE_TIPS.toString()).setXY( 5, 1 ).setItemstack( exists ? ( value ? new ItemStack(Material.LIME_DYE) : new ItemStack( Material.GRAY_DYE ) ) : CustomStack.getInstance("anicloud:slot_locked").getItemStack() ).setName( exists ? ( value ? ColoursManager.green + "> Показывать подсказки [ВКЛЮЧЕНО]" : ColoursManager.red + "> Показывать подсказки [ОТКЛЮЧЕНО]" ) : ( ColoursManager.darkGray + "> Показывать подсказки [ЗАБЛОКИРОВАНО]" ) ).build());

                loadButtons( "Настройки", "anicloud:bw_menu_3core", buttons, player, 27);
                break;
            }
            case "INVENTORY_ABILITIES": {

                player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 0.3F, 1.5F);

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
                    if ( ownedAbility.isOwned() ) {
                        item = ownedAbility.getAbility().getAbilityGuiItem();
                        action = "GOGUI";
                        arg = "INVENTORY_ABILITY_INFO";
                    } else {
                        if ( ownedAbility.getAbility().isDailyTrial() )
                            item = ownedAbility.getAbility().getAbilityGuiItem();
                        else
                            item = new ItemStack(Material.GRAY_DYE);
                        action = "ERROR";
                        arg = "";
                    }

                    int j = i - FlowerUtils.bwInventoryRows * ( page - 1 ) * 9;

                    this.buttons.add(new CustomGUIButton(action).setArgs(arg, ownedAbility.getAbility().getId(), page + "").setXY( j % 9, j / 9 ).setItemstack(item).setName(ownedAbility.getAbility().getName()).setLore(lore).addHiddenEnchantment(ownedAbility.getAbility().isDailyTrial()).build());
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
                    this.buttons.add(new CustomGUIButton("GOGUI").setArgs("INVENTORY_ABILITY_INFO_UPGRADE_CONFIRMATION", abilityId, arg2).setXY(3, 1).setItemstack(CustomStack.getInstance("anicloud:emerald_plate").getItemStack()).setName(ColoursManager.green + "> Улучшить").setLore(upgradeLore).build());
                }
                else {
                    upgradeLore.addAll(NotificationManager.getInsufficientMaterialsMessage());
                    this.buttons.add(new CustomGUIButton("ERROR").setArgs("").setXY(3, 1).setItemstack(CustomStack.getInstance("anicloud:slot_locked").getItemStack()).setName( ChatColor.STRIKETHROUGH + "" + ColoursManager.red + "> Улучшить").setLore(upgradeLore).build());
                }

                ArrayList<String> disassembleLore = new ArrayList<>();
                disassembleLore.addAll(ownedAbility.generateInventoryInfo(player, false, false, true, false));
                if (ownedAbility.duplicatesOwned > 0) {
                    disassembleLore.addAll(NotificationManager.getSufficientMaterialsMessage());
                    this.buttons.add(new CustomGUIButton("GOGUI").setArgs("INVENTORY_ABILITY_INFO_DISASSEMBLE_CONFIRMATION", abilityId, arg2).setXY( 4, 1 ).setItemstack( CustomStack.getInstance("anicloud:raw_iron_wood").getItemStack() ).setName( ColoursManager.green + "> Разобрать").setLore(disassembleLore).build());
                }
                else {
                    disassembleLore.addAll(NotificationManager.getInsufficientMaterialsMessage());
                    this.buttons.add(new CustomGUIButton("ERROR").setArgs("").setXY( 4, 1 ).setItemstack( CustomStack.getInstance("anicloud:slot_locked").getItemStack() ).setName( ChatColor.STRIKETHROUGH + "" + ColoursManager.red + "> Разобрать").setLore(disassembleLore).build());
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

                this.buttons.add(new CustomGUIButton("ERROR").setArgs("").setXY( 2, 1 ).setItemstack( ownedAbility.getAbility().getAbilityGuiItem() ).setName( ColoursManager.light_blue + "Информация").setLore(loreInfo).build());
                this.buttons.add(new CustomGUIButton("ABILITY_UPGRADE").setArgs(abilityId).setXY( 4, 1 ).setItemstack( CustomStack.getInstance("anicloud:button_tick").getItemStack() ).setName( ColoursManager.green + "> Улучшить").setLore(loreConfirm).build());
                this.buttons.add(new CustomGUIButton("GOGUI").setArgs("INVENTORY_ABILITY_INFO",abilityId).setXY( 6, 1 ).setItemstack( CustomStack.getInstance("_iainternal:icon_cancel").getItemStack() ).setName( ColoursManager.red + "> Отменить").build());


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

                this.buttons.add(new CustomGUIButton("ERROR").setArgs("").setXY( 2, 1 ).setItemstack( ownedAbility.getAbility().getAbilityGuiItem() ).setName( ColoursManager.light_blue + "Информация").setLore(loreInfo).build());
                this.buttons.add(new CustomGUIButton("ABILITY_DISASSEMBLE").setArgs(abilityId).setXY( 4, 1 ).setItemstack( CustomStack.getInstance("anicloud:button_tick").getItemStack() ).setName( ColoursManager.green + "> Разобрать").setLore(loreConfirm).build());
                this.buttons.add(new CustomGUIButton("GOGUI").setArgs("INVENTORY_ABILITY_INFO", abilityId).setXY( 6, 1 ).setItemstack( CustomStack.getInstance("_iainternal:icon_cancel").getItemStack() ).setName( ColoursManager.red + "> Отменить").build());


                loadButtons( ColoursManager.darkGray + "Разобрать " + ownedAbility.getAbility().getNameWithIcon(player) + ColoursManager.darkGray + " ?","anicloud:bw_menu_3core", buttons, player, 27);
                break;
            }
            case "INVENTORY_RESOURCES": {

                player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_NETHERITE, 0.5F, 0.8F);

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

                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_CARTOGRAPHER, 0.2F, 0.8F);
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_LIBRARIAN, 0.5F, 0.8F);
//                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);

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
                        this.buttons.add(new CustomGUIButton("GOGUI").setArgs("OPEN_CASE", resourceChest.getId()).setXY(i % 7 + 1, i / 7 + 1).setItemstack(itemStack).setName(ColoursManager.light_blue + resourceChest.getName()).setLore(lore).build());
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
        buttons.add( new CustomGUIButton( "GOGUI").setArgs(guiName, arg).setXY(x, y).setItemstack(itemStack).setName( ChatColor.RED + "> Назад").build());
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
