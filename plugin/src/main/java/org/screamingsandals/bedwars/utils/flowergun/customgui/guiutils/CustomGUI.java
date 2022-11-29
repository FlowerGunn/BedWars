package org.screamingsandals.bedwars.utils.flowergun.customgui.guiutils;


import dev.lone.itemsadder.api.CustomStack;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.Team;
import org.screamingsandals.bedwars.utils.flowergun.customgui.shoputils.*;
import org.screamingsandals.bedwars.game.CurrentTeam;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.LoadedAbility;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.OwnedAbility;

import java.util.ArrayList;
import java.util.List;

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

        abilitiesRedPanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(0,0).setMaterial(Material.RED_STAINED_GLASS_PANE).setName("").build());
        abilitiesRedPanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(8,0).setMaterial(Material.RED_STAINED_GLASS_PANE).setName("").build());
        abilitiesRedPanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(1,1).setMaterial(Material.RED_STAINED_GLASS_PANE).setName("").build());
        abilitiesRedPanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(2,1).setMaterial(Material.RED_STAINED_GLASS_PANE).setName("").build());
        abilitiesRedPanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(6,1).setMaterial(Material.RED_STAINED_GLASS_PANE).setName("").build());
        abilitiesRedPanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(7,1).setMaterial(Material.RED_STAINED_GLASS_PANE).setName("").build());
        abilitiesRedPanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(1,3).setMaterial(Material.RED_STAINED_GLASS_PANE).setName("").build());
        abilitiesRedPanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(2,3).setMaterial(Material.RED_STAINED_GLASS_PANE).setName("").build());
        abilitiesRedPanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(6,3).setMaterial(Material.RED_STAINED_GLASS_PANE).setName("").build());
        abilitiesRedPanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(7,3).setMaterial(Material.RED_STAINED_GLASS_PANE).setName("").build());
        abilitiesRedPanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(0,4).setMaterial(Material.RED_STAINED_GLASS_PANE).setName("").build());
        abilitiesRedPanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(8,4).setMaterial(Material.RED_STAINED_GLASS_PANE).setName("").build());

        abilitiesOrangePanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(8,1).setMaterial(Material.ORANGE_STAINED_GLASS_PANE).setName("").build());
        abilitiesOrangePanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(0,1).setMaterial(Material.ORANGE_STAINED_GLASS_PANE).setName("").build());
        abilitiesOrangePanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(8,3).setMaterial(Material.ORANGE_STAINED_GLASS_PANE).setName("").build());
        abilitiesOrangePanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(0,3).setMaterial(Material.ORANGE_STAINED_GLASS_PANE).setName("").build());
        abilitiesOrangePanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(7,2).setMaterial(Material.ORANGE_STAINED_GLASS_PANE).setName("").build());
        abilitiesOrangePanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(1,2).setMaterial(Material.ORANGE_STAINED_GLASS_PANE).setName("").build());

        abilitiesYellowPanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(0,2).setMaterial(Material.YELLOW_STAINED_GLASS_PANE).setName("").build());
        abilitiesYellowPanes.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(8,2).setMaterial(Material.YELLOW_STAINED_GLASS_PANE).setName("").build());

        abilitiesHoppers.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(3,1).setMaterial(Material.HOPPER).setName("").build());
        abilitiesHoppers.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(4,1).setMaterial(Material.HOPPER).setName("").build());
        abilitiesHoppers.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(5,1).setMaterial(Material.HOPPER).setName("").build());

        abilitiesGrayPanes2.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(2,2).setMaterial(Material.GRAY_STAINED_GLASS_PANE).setName("").build());
        abilitiesGrayPanes2.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(6,2).setMaterial(Material.GRAY_STAINED_GLASS_PANE).setName("").build());
        abilitiesGrayPanes2.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(3,3).setMaterial(Material.GRAY_STAINED_GLASS_PANE).setName("").build());
        abilitiesGrayPanes2.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(4,3).setMaterial(Material.GRAY_STAINED_GLASS_PANE).setName("").build());
        abilitiesGrayPanes2.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(5,3).setMaterial(Material.GRAY_STAINED_GLASS_PANE).setName("").build());
        abilitiesGrayPanes2.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(3,1).setMaterial(Material.GRAY_STAINED_GLASS_PANE).setName("").build());
        abilitiesGrayPanes2.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(4,1).setMaterial(Material.GRAY_STAINED_GLASS_PANE).setName("").build());
        abilitiesGrayPanes2.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(5,1).setMaterial(Material.GRAY_STAINED_GLASS_PANE).setName("").build());

        abilitiesGrayPanes1.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(4,4).setMaterial(Material.GRAY_STAINED_GLASS_PANE).setName("").build());
        abilitiesGrayPanes1.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(2,5).setMaterial(Material.GRAY_STAINED_GLASS_PANE).setName("").build());
        abilitiesGrayPanes1.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(3,5).setMaterial(Material.GRAY_STAINED_GLASS_PANE).setName("").build());
        abilitiesGrayPanes1.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(5,5).setMaterial(Material.GRAY_STAINED_GLASS_PANE).setName("").build());
        abilitiesGrayPanes1.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(6,5).setMaterial(Material.GRAY_STAINED_GLASS_PANE).setName("").build());

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
        switch (guiId) {
            case "VOTE_MODE_MENU": {

                this.buttons.add(new CustomGUIButton("VOTE_TEAMS").setArgs("").setXY(5, 1).setMaterial(Material.LEATHER_HELMET).setName(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Командная игра").setLore(null).build());

                loadButtons("§8Проголосуйте за сложность", buttons, player, 27);

                break;
            }
            case "SHOP":{

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

                loadButtons(ChatColor.DARK_GRAY + "Магазин", buttons, player, 54);
                break;
            }
            case "SHOP_CATEGORY": {

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

                backButtonPrepare("SHOP");

                softLoadButtons( buttons, player );
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
                this.buttons.add(new CustomGUIButton("BUY_UPGRADE").setArgs(temp.name()).setXY(1, 0).setMaterial(Material.valueOf("COPPER_INGOT")).setName(temp.getName()).setLore(temp.getDescription()).addHiddenEnchantment(bought).build());

                temp = GameFlag.INTELLECT_LEVEL_2;
                available = (team.teamFlags.contains(temp.getPreviousFlag()) || temp.getPreviousFlag() == null) && !team.teamFlags.contains(temp.getNeighbourFlag());
                bought = team.teamFlags.contains(temp);
                this.buttons.add(new CustomGUIButton("BUY_UPGRADE").setArgs(temp.name()).setXY(1, 1).setMaterial(available ? Material.IRON_INGOT : Material.GRAY_DYE).setName(temp.getName()).setLore(temp.getDescription()).addHiddenEnchantment(bought).build());

                temp = GameFlag.INTELLECT_LEVEL_3;
                available = (team.teamFlags.contains(temp.getPreviousFlag()) || temp.getPreviousFlag() == null) && !team.teamFlags.contains(temp.getNeighbourFlag());
                bought = team.teamFlags.contains(temp);
                this.buttons.add(new CustomGUIButton("BUY_UPGRADE").setArgs(temp.name()).setXY(1, 2).setMaterial(available ? Material.GLASS_BOTTLE : Material.GRAY_DYE).setName(temp.getName()).setLore(temp.getDescription()).addHiddenEnchantment(bought).build());

                temp = GameFlag.INTELLECT_LEVEL_4A;
                available = (team.teamFlags.contains(temp.getPreviousFlag()) || temp.getPreviousFlag() == null) && !team.teamFlags.contains(temp.getNeighbourFlag());
                bought = team.teamFlags.contains(temp);
                this.buttons.add(new CustomGUIButton("BUY_UPGRADE").setArgs(temp.name()).setXY(0, 3).setMaterial(available ? Material.IRON_SWORD : Material.GRAY_DYE).setName(temp.getName()).setLore(temp.getDescription()).addHiddenEnchantment(bought).build());

                temp = GameFlag.INTELLECT_LEVEL_4B;
                available = (team.teamFlags.contains(temp.getPreviousFlag()) || temp.getPreviousFlag() == null) && !team.teamFlags.contains(temp.getNeighbourFlag());
                bought = team.teamFlags.contains(temp);
                this.buttons.add(new CustomGUIButton("BUY_UPGRADE").setArgs(temp.name()).setXY(2, 3).setMaterial(available ? Material.SHIELD : Material.GRAY_DYE).setName(temp.getName()).setLore(temp.getDescription()).addHiddenEnchantment(bought).build());

                //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

                temp = GameFlag.AGILITY_LEVEL_1;
                bought = team.teamFlags.contains(temp);
                this.buttons.add(new CustomGUIButton("BUY_UPGRADE").setArgs(temp.name()).setXY(4, 3).setMaterial(Material.SUGAR).setName(temp.getName()).setLore(temp.getDescription()).addHiddenEnchantment(bought).build());

                temp = GameFlag.AGILITY_LEVEL_2;
                available = (team.teamFlags.contains(temp.getPreviousFlag()) || temp.getPreviousFlag() == null) && !team.teamFlags.contains(temp.getNeighbourFlag());
                bought = team.teamFlags.contains(temp);
                this.buttons.add(new CustomGUIButton("BUY_UPGRADE").setArgs(temp.name()).setXY(4, 2).setMaterial(available ? Material.BOW : Material.GRAY_DYE).setName(temp.getName()).setLore(temp.getDescription()).addHiddenEnchantment(bought).build());

                temp = GameFlag.AGILITY_LEVEL_3;
                available = (team.teamFlags.contains(temp.getPreviousFlag()) || temp.getPreviousFlag() == null) && !team.teamFlags.contains(temp.getNeighbourFlag());
                bought = team.teamFlags.contains(temp);
                this.buttons.add(new CustomGUIButton("BUY_UPGRADE").setArgs(temp.name()).setXY(4, 1).setMaterial(available ? Material.SNOWBALL : Material.GRAY_DYE).setName(temp.getName()).setLore(temp.getDescription()).addHiddenEnchantment(bought).build());

                temp = GameFlag.AGILITY_LEVEL_4A;
                available = (team.teamFlags.contains(temp.getPreviousFlag()) || temp.getPreviousFlag() == null) && !team.teamFlags.contains(temp.getNeighbourFlag());
                bought = team.teamFlags.contains(temp);
                this.buttons.add(new CustomGUIButton("BUY_UPGRADE").setArgs(temp.name()).setXY(3, 0).setMaterial(available ? Material.WITHER_ROSE : Material.GRAY_DYE).setName(temp.getName()).setLore(temp.getDescription()).addHiddenEnchantment(bought).build());

                temp = GameFlag.AGILITY_LEVEL_4B;
                available = (team.teamFlags.contains(temp.getPreviousFlag()) || temp.getPreviousFlag() == null) && !team.teamFlags.contains(temp.getNeighbourFlag());
                bought = team.teamFlags.contains(temp);
                this.buttons.add(new CustomGUIButton("BUY_UPGRADE").setArgs(temp.name()).setXY(5, 0).setMaterial(available ? Material.SPIDER_EYE : Material.GRAY_DYE).setName(temp.getName()).setLore(temp.getDescription()).addHiddenEnchantment(bought).build());

                //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

                temp = GameFlag.VITALITY_LEVEL_1;
                bought = team.teamFlags.contains(temp);
                this.buttons.add(new CustomGUIButton("BUY_UPGRADE").setArgs(temp.name()).setXY(7, 0).setMaterial(Material.LEATHER_CHESTPLATE).setName(temp.getName()).setLore(temp.getDescription()).addHiddenEnchantment(bought).build());

                temp = GameFlag.VITALITY_LEVEL_2;
                available = (team.teamFlags.contains(temp.getPreviousFlag()) || temp.getPreviousFlag() == null) && !team.teamFlags.contains(temp.getNeighbourFlag());
                bought = team.teamFlags.contains(temp);
                this.buttons.add(new CustomGUIButton("BUY_UPGRADE").setArgs(temp.name()).setXY(7, 1).setMaterial(available ? Material.LEATHER : Material.GRAY_DYE).setName(temp.getName()).setLore(temp.getDescription()).addHiddenEnchantment(bought).build());

                temp = GameFlag.VITALITY_LEVEL_3;
                available = (team.teamFlags.contains(temp.getPreviousFlag()) || temp.getPreviousFlag() == null) && !team.teamFlags.contains(temp.getNeighbourFlag());
                bought = team.teamFlags.contains(temp);
                this.buttons.add(new CustomGUIButton("BUY_UPGRADE").setArgs(temp.name()).setXY(7, 2).setMaterial(available ? Material.FEATHER : Material.GRAY_DYE).setName(temp.getName()).setLore(temp.getDescription()).addHiddenEnchantment(bought).build());

                temp = GameFlag.VITALITY_LEVEL_4A;
                available = (team.teamFlags.contains(temp.getPreviousFlag()) || temp.getPreviousFlag() == null) && !team.teamFlags.contains(temp.getNeighbourFlag());
                bought = team.teamFlags.contains(temp);
                this.buttons.add(new CustomGUIButton("BUY_UPGRADE").setArgs(temp.name()).setXY(6, 3).setMaterial(available ? Material.GOLDEN_APPLE : Material.GRAY_DYE).setName(temp.getName()).setLore(temp.getDescription()).addHiddenEnchantment(bought).build());

                temp = GameFlag.VITALITY_LEVEL_4B;
                available = (team.teamFlags.contains(temp.getPreviousFlag()) || temp.getPreviousFlag() == null) && !team.teamFlags.contains(temp.getNeighbourFlag());
                bought = team.teamFlags.contains(temp);
                this.buttons.add(new CustomGUIButton("BUY_UPGRADE").setArgs(temp.name()).setXY(8, 3).setMaterial(available ? Material.GOLDEN_CARROT : Material.GRAY_DYE).setName(temp.getName()).setLore(temp.getDescription()).addHiddenEnchantment(bought).build());


                backButtonPrepare("SHOP");

                loadButtons("Командные улучшения", buttons, player,  54);

                break;
            }
            case "TEAM_SELECTION": {

                GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
                Game game = gamePlayer.getGame();

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

                loadButtons( "Выбор Команды", buttons, player, 27);
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
                    Material icon = Material.LIME_STAINED_GLASS_PANE;
                    String name = ChatColor.WHITE + "Пустой слот";

                    lore.add("");
//                    lore.add(ChatColor.GRAY + "Максимальная уровень способности");
//                    lore.add(ChatColor.GRAY + "в этом слоте - " + ChatColor.WHITE + "" + ChatColor.BOLD + (i + 1) );
//                    lore.add("");

                    if (!loadedAbility.isEmpty()) {
                        name = loadedAbility.getOwnedAbility().getAbility().getName();
                        icon = loadedAbility.getOwnedAbility().getAbility().getItem();
                        lore.addAll(loadedAbility.getOwnedAbility().getAbility().parseDescription(loadedAbility.getOwnedAbility().ownedLevel, Math.min(loadedAbility.getOwnedAbility().ownedLevel, i + 1), i + 1 ));
                    }
//                    else {
//                        lore.add("");
//                        lore.add(ChatColor.GRAY + "Кликните, чтобы выбрать способность...");
//                    }
                    lore.add("");
                    lore.addAll(FlowerUtils.alphaWarning);
                    this.buttons.add(new CustomGUIButton("BLOCKER").setArgs("").setXY(3 + i, 2).setMaterial(icon).setName(name).setLore(lore).build());
                }

                this.buttons.addAll(abilities);

                backButtonPrepare("EXIT");

                loadButtons( "Способности игрока " + arg, buttons, player, 54);

                break;
            }
            case "ABILITIES": {

                GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
                Game game = gamePlayer.getGame();
                ArrayList<LoadedAbility> loadedAbilities = gamePlayer.loadedAbilities;

                ArrayList lore;

                LoadedAbility loadedAbility;

                for (int i = 0; i < loadedAbilities.size(); i++) {
                    loadedAbility = loadedAbilities.get(i);
                    lore = new ArrayList<>();
                    Material icon = Material.LIME_STAINED_GLASS_PANE;
                    String name = ChatColor.WHITE + "Пустой слот";

                    lore.add("");
                    lore.add(ChatColor.GRAY + "Максимальная уровень способности");
                    lore.add(ChatColor.GRAY + "в этом слоте - " + ChatColor.WHITE + "" + ChatColor.BOLD + (i + 1) );
                    lore.add("");

                    if (!loadedAbility.isEmpty()) {
                        name = loadedAbility.getOwnedAbility().getAbility().getName();
                        icon = loadedAbility.getOwnedAbility().getAbility().getItem();
                        lore.addAll(loadedAbility.getOwnedAbility().getAbility().parseDescription(loadedAbility.getOwnedAbility().ownedLevel, Math.min(loadedAbility.getOwnedAbility().ownedLevel, i + 1), i + 1 ));
                    }
                    else {
                        lore.add("");
                        lore.add(ChatColor.GRAY + "Кликните, чтобы выбрать способность...");
                    }
                    lore.add("");
                    lore.addAll(FlowerUtils.alphaWarning);
                    this.buttons.add(new CustomGUIButton("GOGUI").setArgs("SELECT_ABILITY", i + "").setXY(3 + i, 2).setMaterial(icon).setName(name).setLore(lore).build());
                }


                this.buttons.add(new CustomGUIButton("RANDOMIZE_ALL_ABILITIES").setArgs("").setXY( 4, 5 ).setMaterial(Material.RECOVERY_COMPASS).setName(ChatColor.GOLD + "Выбрать ВСЕ способности случайно...").build());

                this.buttons.addAll(abilities);
                this.buttons.addAll(abilitiesHoppers);
                this.buttons.addAll(abilitiesGrayPanes1);

                backButtonPrepare("EXIT");

                loadButtons( "Выберите Способности...", buttons, player, 54);

                break;
            }
            case "SELECT_ABILITY": {

                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);

                int slot = Integer.parseInt(arg);

                GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
                Game game = gamePlayer.getGame();
                ArrayList<LoadedAbility> loadedAbilities = gamePlayer.loadedAbilities;
                ArrayList<OwnedAbility> ownedAbilities = gamePlayer.ownedAbilities;

                ArrayList<String> lore;


                for ( int i = 0; i < ownedAbilities.size(); i++ ) {
                    lore = ownedAbilities.get(i).getAbility().parseDescription(ownedAbilities.get(i).ownedLevel, Math.min(ownedAbilities.get(i).ownedLevel, slot + 1), slot + 1 );
                    lore.add("");
                    lore.addAll(FlowerUtils.alphaWarning);
                    this.buttons.add(new CustomGUIButton("SELECT_ABILITY_INTO_SLOT").setArgs(ownedAbilities.get(i).getAbility().getId(), arg).setXY( i % 9, i / 9 ).setMaterial(ownedAbilities.get(i).getAbility().getItem()).setName(ownedAbilities.get(i).getAbility().getName()).setLore(lore).build());
                }

                this.buttons.add(new CustomGUIButton("RANDOMIZE_ABILITY_INTO_SLOT").setArgs(arg).setXY( 4, 5 ).setMaterial(Material.RECOVERY_COMPASS).setName(ChatColor.GOLD + "Выбрать случайно...").build());
                this.buttons.addAll(abilitiesGrayPanes1);

                backButtonPrepare("ABILITIES");

                softLoadButtons(this.buttons, player);

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
        ItemStack itemStack = getBackItem();
        buttons.add( new CustomGUIButton( "GOGUI").setArgs(guiName).setXY(gobackButtonX, gobackButtonY).setItemstack(itemStack).setName( ChatColor.RED + "Назад").build());
    }

    private ItemStack getBackItem() {
        return CustomStack.getInstance("anicloud:button_back").getItemStack();
    }

    private ItemStack getNextItem() {
        return CustomStack.getInstance("anicloud:button_next").getItemStack();
    }

    static void loadButtons(String title,ArrayList<CustomGUIButton> buttons, Player player, int area) {
        Inventory gui = Bukkit.createInventory(null, area, title);

        Integer buttons_length = buttons.size();
        for (Integer i = 0; i < buttons_length; i++)
        {
            CustomGUIButton button = buttons.get(i);
            gui.setItem( button.getX() + button.getY() * 9, button.getItem());
        }

        player.openInventory(gui);
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
