package org.screamingsandals.bedwars.utils.flowergun.customgui.shoputils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CustomItem;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.Team;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.GadgetType;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ItemCategory;

import java.util.ArrayList;

import static org.screamingsandals.bedwars.lib.lang.I.i18n;

public class Shop {

    public ArrayList<ShopCategory> categories;

    private Player player;
    private Team team;
    private Game game;

    public Shop(Game game) {

        this.categories = new ArrayList<>();
        this.game = game;

        loadAllItems();
    }

    public Shop(Shop shop) {

        this.categories = new ArrayList<>();
        for (ShopCategory shopCategory: shop.categories) {
            this.categories.add(shopCategory.copy());
        }
        this.game = shop.game;

    }

    public void loadAllItems() {

        ArrayList<String> loreWeapons = new ArrayList<>();
        loreWeapons.add(ChatColor.GRAY + "Test");

        ShopCategory weapons = new ShopCategory("weapons", new CustomItem().setMaterial(Material.WOODEN_SWORD).setName(ChatColor.BLUE + "" + ChatColor.BOLD + i18n("shop_category_weapons", "Weapons", false)).setLore(null).build());
//        CustomItem woodSword = new CustomItem().setMaterial(Material.WOODEN_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 1).build();
//        CustomItem stoneSword = new CustomItem().setMaterial(Material.STONE_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 2).build();
//        CustomItem ironSword = new CustomItem().setMaterial(Material.IRON_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 3).build();
//        CustomItem diamondSword = new CustomItem().setMaterial(Material.DIAMOND_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 3).build();
//        weapons.items.add(new CustomGUIPurchasableItem("woodSword" , woodSword, Main.getSpawnerType("bronze"), 10).setCategory(ItemCategory.SWORD).build());
//        weapons.items.add(new CustomGUIPurchasableItem("stoneSword", stoneSword, Main.getSpawnerType("iron"), 15).setCategory(ItemCategory.SWORD).build());
//        weapons.items.add(new CustomGUIPurchasableItem("ironSword", ironSword, Main.getSpawnerType("gold"), 20).setCategory(ItemCategory.SWORD).build());
//        weapons.items.add(new CustomGUIPurchasableItem("diamondSword", diamondSword, Main.getSpawnerType("emerald"), 5).setCategory(ItemCategory.SWORD).build());

        CustomItem goldenSword1 = new CustomItem().setMaterial(Material.GOLDEN_SWORD).build();
        weapons.items.add(new PurchasableItem("goldenSword1" , goldenSword1, Main.getSpawnerType("bronze"), 4).setCategory(ItemCategory.SWORD).build());
        CustomItem goldenSword2 = new CustomItem().setMaterial(Material.GOLDEN_SWORD).addEnchantment(Enchantment.DURABILITY, 1).addEnchantment(Enchantment.DAMAGE_ALL, 1).build();
        weapons.items.add(new PurchasableItem("goldenSword2" , goldenSword2, Main.getSpawnerType("bronze"), 8).setCategory(ItemCategory.SWORD).build());
        CustomItem goldenSword3 = new CustomItem().setMaterial(Material.GOLDEN_SWORD).addEnchantment(Enchantment.DURABILITY, 2).addEnchantment(Enchantment.DAMAGE_ALL, 2).build();
        weapons.items.add(new PurchasableItem("goldenSword3" , goldenSword3, Main.getSpawnerType("bronze"), 12).setCategory(ItemCategory.SWORD).build());
//        CustomItem goldenSword4 = new CustomItem().setMaterial(Material.GOLDEN_SWORD).addEnchantment(Enchantment.DURABILITY, 3).addEnchantment(Enchantment.DAMAGE_ALL, 3).build();
//        weapons.items.add(new CustomGUIPurchasableItem("goldenSword4" , goldenSword4, Main.getSpawnerType("bronze"), 40).setCategory(ItemCategory.SWORD).build());

//        CustomItem stoneSword1 = new CustomItem().setMaterial(Material.STONE_SWORD).build();
//        weapons.items.add(new PurchasableItem("stoneSword1" , stoneSword1, Main.getSpawnerType("iron"), 1).setCategory(ItemCategory.SWORD).build());
//        CustomItem stoneSword5 = new CustomItem().setMaterial(Material.STONE_SWORD).addEnchantment(Enchantment.KNOCKBACK, 1).build();
//        weapons.items.add(new PurchasableItem("stoneSword5" , stoneSword5, Main.getSpawnerType("iron"), 3).setCategory(ItemCategory.SWORD).build());
        CustomItem stoneSword2 = new CustomItem().setMaterial(Material.STONE_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 1).build();
        weapons.items.add(new PurchasableItem("stoneSword2" , stoneSword2, Main.getSpawnerType("iron"), 3).setCategory(ItemCategory.SWORD).build());
        CustomItem stoneSword6 = new CustomItem().setMaterial(Material.STONE_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 2).build();
        weapons.items.add(new PurchasableItem("stoneSword6" , stoneSword6, Main.getSpawnerType("iron"), 8).setCategory(ItemCategory.SWORD).build());
//        CustomItem stoneSword3 = new CustomItem().setMaterial(Material.STONE_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 2).build();
//        weapons.items.add(new CustomGUIPurchasableItem("stoneSword3" , stoneSword3, Main.getSpawnerType("iron"), 5).setCategory(ItemCategory.SWORD).build());
//        CustomItem stoneSword4 = new CustomItem().setMaterial(Material.STONE_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 3).build();
//        weapons.items.add(new CustomGUIPurchasableItem("stoneSword4" , stoneSword4, Main.getSpawnerType("iron"), 12).setCategory(ItemCategory.SWORD).build());

//        CustomItem ironSword1 = new CustomItem().setMaterial(Material.IRON_SWORD).build();
//        weapons.items.add(new CustomGUIPurchasableItem("ironSword1" , ironSword1, Main.getSpawnerType("gold"), 1).setCategory(ItemCategory.SWORD).build());
        CustomItem ironSword2 = new CustomItem().setMaterial(Material.IRON_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 1).addEnchantment(Enchantment.FIRE_ASPECT, 1).build();
        weapons.items.add(new PurchasableItem("ironSword2" , ironSword2, Main.getSpawnerType("gold"), 3).setCategory(ItemCategory.SWORD).build());
        CustomItem ironSword3 = new CustomItem().setMaterial(Material.IRON_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 2).addEnchantment(Enchantment.FIRE_ASPECT, 1).build();
        weapons.items.add(new PurchasableItem("ironSword3" , ironSword3, Main.getSpawnerType("gold"), 6).setCategory(ItemCategory.SWORD).build());
//        CustomItem ironSword4 = new CustomItem().setMaterial(Material.IRON_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 3).build();
//        weapons.items.add(new CustomGUIPurchasableItem("ironSword4" , ironSword4, Main.getSpawnerType("gold"), 12).setCategory(ItemCategory.SWORD).build());

//        CustomItem diamondSword1 = new CustomItem().setMaterial(Material.DIAMOND_SWORD).build();
//        weapons.items.add(new CustomGUIPurchasableItem("diamondSword1" , diamondSword1, Main.getSpawnerType("emerald"), 1, Main.getSpawnerType("gold"), 5).setCategory(ItemCategory.SWORD).build());
        CustomItem diamondSword2 = new CustomItem().setMaterial(Material.DIAMOND_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 1).addEnchantment(Enchantment.KNOCKBACK, 1).build();
        weapons.items.add(new PurchasableItem("diamondSword2" , diamondSword2, Main.getSpawnerType("emerald"), 2, Main.getSpawnerType("gold"), 12).setCategory(ItemCategory.SWORD).build());
        CustomItem diamondSword3 = new CustomItem().setMaterial(Material.DIAMOND_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 2).addEnchantment(Enchantment.KNOCKBACK, 1).build();
        weapons.items.add(new PurchasableItem("diamondSword3" , diamondSword3, Main.getSpawnerType("emerald"), 4, Main.getSpawnerType("gold"), 20).setCategory(ItemCategory.SWORD).build());
//        CustomItem diamondSword4 = new CustomItem().setMaterial(Material.DIAMOND_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 3).build();
//        weapons.items.add(new CustomGUIPurchasableItem("diamondSword4" , diamondSword4, Main.getSpawnerType("emerald"), 7, Main.getSpawnerType("gold"), 20).setCategory(ItemCategory.SWORD).build());



        ShopCategory armour = new ShopCategory("armour", new CustomItem().setMaterial(Material.LEATHER_CHESTPLATE).setName(ChatColor.BLUE + "" + ChatColor.BOLD + i18n("shop_category_armour", "Armour", false)).setLore(null).build());
        CustomItem leatherBoots = new CustomItem().setMaterial(Material.LEATHER_BOOTS).setDyable().build();
        CustomItem featherFallingLeatherBoots = new CustomItem().setMaterial(Material.LEATHER_BOOTS).addEnchantment(Enchantment.PROTECTION_FALL, 2).setDyable().build();
        CustomItem leatherHelmet = new CustomItem().setMaterial(Material.LEATHER_HELMET).setDyable().build();
        CustomItem leatherLeggins = new CustomItem().setMaterial(Material.LEATHER_LEGGINGS).setDyable().build();
        CustomItem leatherChestPlate = new CustomItem().setMaterial(Material.LEATHER_CHESTPLATE).setDyable().build();
        armour.items.add(new PurchasableItem("leatherBoots", leatherBoots, Main.getSpawnerType("bronze"), 1).setCategory(ItemCategory.BOOTS).build());
        armour.items.add(new PurchasableItem("featherFallingLeatherBoots", featherFallingLeatherBoots, Main.getSpawnerType("gold"), 1).setCategory(ItemCategory.BOOTS).setVisibility(false).setInclusionFlag(GameFlag.VITALITY_LEVEL_3).build());
        armour.items.add(new PurchasableItem("leatherHelmet", leatherHelmet, Main.getSpawnerType("bronze"), 1).setCategory(ItemCategory.HELMET).build());
        armour.items.add(new PurchasableItem("leatherLeggins", leatherLeggins, Main.getSpawnerType("bronze"), 1).setCategory(ItemCategory.LEGGINS).build());
        armour.items.add(new PurchasableItem("leatherChestPlate", leatherChestPlate, Main.getSpawnerType("bronze"), 3).setCategory(ItemCategory.CHESTPLATE).setVisibility(false).setInclusionFlag(GameFlag.VITALITY_LEVEL_1).build());

        CustomItem chainmailChestPlate1 = new CustomItem().setMaterial(Material.CHAINMAIL_CHESTPLATE).build();
        armour.items.add(new PurchasableItem("chainmailChestPlate1", chainmailChestPlate1, Main.getSpawnerType("iron"), 1).setCategory(ItemCategory.CHESTPLATE).build());
        CustomItem chainmailChestPlate2 = new CustomItem().setMaterial(Material.CHAINMAIL_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build();
        armour.items.add(new PurchasableItem("chainmailChestPlate2", chainmailChestPlate2, Main.getSpawnerType("iron"), 3).setCategory(ItemCategory.CHESTPLATE).build());
        CustomItem chainmailChestPlate3 = new CustomItem().setMaterial(Material.CHAINMAIL_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).addEnchantment(Enchantment.PROTECTION_PROJECTILE, 1).build();
        armour.items.add(new PurchasableItem("chainmailChestPlate3", chainmailChestPlate3, Main.getSpawnerType("iron"), 6).setCategory(ItemCategory.CHESTPLATE).build());
        CustomItem chainmailChestPlate4 = new CustomItem().setMaterial(Material.CHAINMAIL_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.PROTECTION_PROJECTILE, 1).build();
        armour.items.add(new PurchasableItem("chainmailChestPlate4", chainmailChestPlate4, Main.getSpawnerType("iron"), 12).setCategory(ItemCategory.CHESTPLATE).build());

        CustomItem ironChestPlate1 = new CustomItem().setMaterial(Material.IRON_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchantment(Enchantment.PROTECTION_PROJECTILE, 1).build();
        armour.items.add(new PurchasableItem("ironChestPlate1", ironChestPlate1, Main.getSpawnerType("gold"), 3).setCategory(ItemCategory.CHESTPLATE).build());
//        CustomItem ironChestPlate2 = new CustomItem().setMaterial(Material.IRON_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build();
//        armour.items.add(new CustomGUIPurchasableItem("ironChestPlate2", ironChestPlate2, Main.getSpawnerType("gold"), 2).setCategory(ItemCategory.CHESTPLATE).build());
        CustomItem ironChestPlate3 = new CustomItem().setMaterial(Material.IRON_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.PROTECTION_PROJECTILE, 2).build();
        armour.items.add(new PurchasableItem("ironChestPlate3", ironChestPlate3, Main.getSpawnerType("gold"), 6).setCategory(ItemCategory.CHESTPLATE).build());
        CustomItem ironChestPlate4 = new CustomItem().setMaterial(Material.IRON_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5).addEnchantment(Enchantment.PROTECTION_PROJECTILE, 3).build();
        armour.items.add(new PurchasableItem("ironChestPlate4", ironChestPlate4, Main.getSpawnerType("gold"), 12).setCategory(ItemCategory.CHESTPLATE).build());

        CustomItem diamondChestPlate1 = new CustomItem().setMaterial(Material.DIAMOND_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.PROTECTION_PROJECTILE, 2).build();
        armour.items.add(new PurchasableItem("diamondChestPlate1", diamondChestPlate1, Main.getSpawnerType("emerald"), 1, Main.getSpawnerType("gold"), 6).setCategory(ItemCategory.CHESTPLATE).build());
//        CustomItem diamondChestPlate2 = new CustomItem().setMaterial(Material.DIAMOND_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build();
//        armour.items.add(new CustomGUIPurchasableItem("diamondChestPlate2", diamondChestPlate2, Main.getSpawnerType("emerald"), 2, Main.getSpawnerType("gold"), 5).setCategory(ItemCategory.CHESTPLATE).build());
        CustomItem diamondChestPlate3 = new CustomItem().setMaterial(Material.DIAMOND_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 6).addEnchantment(Enchantment.PROTECTION_PROJECTILE, 4).build();
        armour.items.add(new PurchasableItem("diamondChestPlate3", diamondChestPlate3, Main.getSpawnerType("emerald"), 3, Main.getSpawnerType("gold"), 12).setCategory(ItemCategory.CHESTPLATE).build());
//        CustomItem diamondChestPlate4 = new CustomItem().setMaterial(Material.DIAMOND_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build();
//        armour.items.add(new CustomGUIPurchasableItem("diamondChestPlate4", diamondChestPlate4, Main.getSpawnerType("emerald"), 4, Main.getSpawnerType("gold"), 20).setCategory(ItemCategory.CHESTPLATE).build());

        CustomItem shield = new CustomItem().setMaterial(Material.SHIELD).build();
        armour.items.add(new PurchasableItem("shield", shield, Main.getSpawnerType("iron"), 3).setCategory(ItemCategory.ITEM).build());

        CustomItem boots = new CustomItem().setMaterial(Material.IRON_BOOTS).setName(ChatColor.BLUE + i18n("item_name_magnet_boots", "Magnet Boots", false)).addKnockbackResistanceModifier(0.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET).addArmorPointsModifier(3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET).build();
        armour.items.add(new PurchasableItem("boots", boots, Main.getSpawnerType("emerald"), 1,Main.getSpawnerType("gold"), 6).setCategory(ItemCategory.GADGET).build());
        CustomItem boots_new = new CustomItem().setMaterial(Material.IRON_BOOTS).setName(ChatColor.BLUE + i18n("item_name_magnet_boots_new", "Magnet Boots", false)).addEnchantment(Enchantment.DURABILITY, 2).addKnockbackResistanceModifier(1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET).addMovementSpeedModifier(-0.3, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.FEET).addArmorPointsModifier(1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET).build();
//        CustomItem boots_new = new CustomItem().setMaterial(Material.IRON_BOOTS).setName(ChatColor.BLUE + i18n("item_name_magnet_boots_new", "Magnet Boots", false)).setGadgetType(GadgetType.NEW_BOOTS).build();
        armour.items.add(new PurchasableItem("boots_new", boots_new, Main.getSpawnerType("emerald"), 2,Main.getSpawnerType("gold"), 10).setCategory(ItemCategory.GADGET).build());
        CustomItem boots_gymnast = new CustomItem().setMaterial(Material.GOLDEN_BOOTS).setName(ChatColor.BLUE + i18n("item_name_magnet_boots_gymnast", "Gymnast Boots", false)).addEnchantment(Enchantment.DURABILITY, 5).addMovementSpeedModifier(0.5, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.FEET).addKnockbackResistanceModifier(-0.5, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.FEET).build();
        armour.items.add(new PurchasableItem("boots_gymnast", boots_gymnast, Main.getSpawnerType("emerald"), 2,Main.getSpawnerType("gold"), 4).setCategory(ItemCategory.GADGET).build());


        ShopCategory blocks = new ShopCategory("blocks", new CustomItem().setMaterial(Material.SANDSTONE).setName(ChatColor.BLUE + "" + ChatColor.BOLD + i18n("shop_category_blocks", "Blocks", false)).setLore(null).build());
        CustomItem wool = new CustomItem().setMaterial(Material.WHITE_WOOL).setAmount(16).build();
        blocks.items.add(new PurchasableItem("wool", wool, Main.getSpawnerType("bronze"), 3).setCategory(ItemCategory.ITEM).build());
        CustomItem sandstone = new CustomItem().setMaterial(Material.SANDSTONE).setAmount(16).build();
        blocks.items.add(new PurchasableItem("sandstone", sandstone, Main.getSpawnerType("bronze"), 4).setCategory(ItemCategory.ITEM).build());
        CustomItem gold_block = new CustomItem().setMaterial(Material.GOLD_BLOCK).setAmount(3).build();
        blocks.items.add(new PurchasableItem("gold_block", gold_block, Main.getSpawnerType("iron"), 1).setCategory(ItemCategory.ITEM).build());
        CustomItem endstone = new CustomItem().setMaterial(Material.END_STONE).setAmount(3).build();
        blocks.items.add(new PurchasableItem("endstone", endstone, Main.getSpawnerType("iron"), 1).setCategory(ItemCategory.ITEM).build());
        CustomItem planks = new CustomItem().setMaterial(Material.DARK_OAK_PLANKS).setAmount(4).build();
        blocks.items.add(new PurchasableItem("planks", planks, Main.getSpawnerType("bronze"), 3).setCategory(ItemCategory.ITEM).build());
        CustomItem glass = new CustomItem().setMaterial(Material.GLASS).setAmount(16).build();
        blocks.items.add(new PurchasableItem("glass", glass, Main.getSpawnerType("bronze"), 12).setCategory(ItemCategory.ITEM).build());
        CustomItem netherite_ore = new CustomItem().setMaterial(Material.NETHERITE_SCRAP).build();
        blocks.items.add(new PurchasableItem("netherite_ore", netherite_ore, Main.getSpawnerType("emerald"), 1,Main.getSpawnerType("gold"), 4).setCategory(ItemCategory.ITEM).build());
        CustomItem chest = new CustomItem().setMaterial(Material.CHEST).setAmount(1).build();
        blocks.items.add(new PurchasableItem("chest", chest, Main.getSpawnerType("iron"), 2).setCategory(ItemCategory.ITEM).build());
        CustomItem ender_chest = new CustomItem().setMaterial(Material.ENDER_CHEST).setAmount(1).build();
        blocks.items.add(new PurchasableItem("ender_chest", ender_chest, Main.getSpawnerType("gold"), 4).setCategory(ItemCategory.ITEM).build());


        ArrayList<String> trampolineLore = new ArrayList<>();
        trampolineLore.add("");
        trampolineLore.add(i18n("item_description_trampoline1", "--", false));
        trampolineLore.add(i18n("item_description_trampoline2", "--", false));
        trampolineLore.add(i18n("item_description_trampoline3", "--", false));
        trampolineLore.add("");
        trampolineLore.add(i18n("item_description_trampoline4", "--", false));
        trampolineLore.add(i18n("item_description_trampoline5", "--", false));
        CustomItem slime = new CustomItem().setMaterial(Material.SLIME_BLOCK).setAmount(3).setName(ChatColor.BLUE + i18n("item_name_trampoline", "Trampoline", false)).setLore(trampolineLore).setGadgetType(GadgetType.TRAMPOLINE).build();
        blocks.items.add(new PurchasableItem("slime", slime, Main.getSpawnerType("iron"), 1).setCategory(ItemCategory.GADGET).build());
        CustomItem cobweb = new CustomItem().setMaterial(Material.COBWEB).setAmount(8).build();
        blocks.items.add(new PurchasableItem("cobweb", cobweb, Main.getSpawnerType("bronze"), 8).setCategory(ItemCategory.GADGET).build());
        CustomItem campfire = new CustomItem().setMaterial(Material.CAMPFIRE).setAmount(4).build();
        blocks.items.add(new PurchasableItem("campfire", campfire, Main.getSpawnerType("bronze"), 6).setCategory(ItemCategory.GADGET).build());
        CustomItem scaffolding = new CustomItem().setMaterial(Material.SCAFFOLDING).setAmount(16).build();
        blocks.items.add(new PurchasableItem("scaffolding", scaffolding, Main.getSpawnerType("bronze"), 8).setCategory(ItemCategory.GADGET).build());
        CustomItem vines = new CustomItem().setMaterial(Material.VINE).setAmount(16).build();
        blocks.items.add(new PurchasableItem("vines", vines, Main.getSpawnerType("bronze"), 8).setCategory(ItemCategory.GADGET).build());


        ShopCategory gadgets = new ShopCategory("gadgets", new CustomItem().setMaterial(Material.TNT).setName(ChatColor.BLUE + "" + ChatColor.BOLD + i18n("shop_category_gadgets", "Gadgets", false)).setLore(null).build());
        CustomItem trap = new CustomItem().setMaterial(Material.STRING).setName(ChatColor.BLUE + i18n("item_name_trap", "Trap", false)).setGadgetType(GadgetType.TRAP).build();
        gadgets.items.add(new PurchasableItem("trap", trap, Main.getSpawnerType("iron"), 5).setCategory(ItemCategory.GADGET).build());
        CustomItem tnt = new CustomItem().setMaterial(Material.TNT).setName(ChatColor.BLUE + i18n("item_name_tnt", "Tnt", false)).setGadgetType(GadgetType.TNT).build();
        gadgets.items.add(new PurchasableItem("tnt", tnt, Main.getSpawnerType("iron"), 10).setCategory(ItemCategory.GADGET).build());
        CustomItem golem = new CustomItem().setMaterial(Material.WOLF_SPAWN_EGG).setName(ChatColor.BLUE + i18n("item_name_golem", "Golem", false)).setGadgetType(GadgetType.GOLEM).build();
        gadgets.items.add(new PurchasableItem("golem", golem, Main.getSpawnerType("emerald"), 1, Main.getSpawnerType("gold"), 4).setCategory(ItemCategory.GADGET).build());
        CustomItem zoglin = new CustomItem().setMaterial(Material.ZOGLIN_SPAWN_EGG).setName(ChatColor.BLUE + i18n("item_name_zoglin", "Zoglin", false)).setGadgetType(GadgetType.ZOGLIN).build();
        gadgets.items.add(new PurchasableItem("zoglin", zoglin, Main.getSpawnerType("gold"), 4).setCategory(ItemCategory.GADGET).build());
        CustomItem blaze = new CustomItem().setMaterial(Material.BLAZE_SPAWN_EGG).setName(ChatColor.BLUE + i18n("item_name_blaze", "Blaze", false)).setGadgetType(GadgetType.BLAZE).build();
        gadgets.items.add(new PurchasableItem("blaze", blaze,Main.getSpawnerType("emerald"), 1, Main.getSpawnerType("gold"), 3).setCategory(ItemCategory.GADGET).build());
        CustomItem phantom = new CustomItem().setMaterial(Material.PHANTOM_SPAWN_EGG).setName(ChatColor.BLUE + i18n("item_name_phantom", "Phantom", false)).setGadgetType(GadgetType.PHANTOM).build();
        gadgets.items.add(new PurchasableItem("phantom", phantom, Main.getSpawnerType("gold"), 3).setCategory(ItemCategory.GADGET).build());
        CustomItem sheep = new CustomItem().setMaterial(Material.SHEEP_SPAWN_EGG).setName(ChatColor.BLUE + i18n("item_name_explosive_sheep", "Explosive Sheep", false)).setGadgetType(GadgetType.SHEEP).build();
        gadgets.items.add(new PurchasableItem("sheep", sheep, Main.getSpawnerType("gold"), 4).setCategory(ItemCategory.GADGET).build());
        //TODO add arrow shield description
        ArrayList<String> arrowShieldLore = new ArrayList<>();
        arrowShieldLore.add("");
        arrowShieldLore.add(i18n("description_arrow_shield1", "Blocks all incoming arrows", false));
        arrowShieldLore.add(i18n("description_arrow_shield2", "on the user for 10 seconds.", false));
        CustomItem arrow_shield = new CustomItem().setMaterial(Material.ENDER_EYE).setName(ChatColor.BLUE + i18n("item_name_arrow_shield", "Arrow Shield", false)).setGadgetType(GadgetType.ARROW_SHIELD).setLore(arrowShieldLore).build();
        gadgets.items.add(new PurchasableItem("arrow_shield", arrow_shield, Main.getSpawnerType("iron"), 6).setCategory(ItemCategory.GADGET).build());
        CustomItem slime_platform = new CustomItem().setMaterial(Material.BLAZE_ROD).setName(ChatColor.BLUE + i18n("item_name_slime_platform", "Slime Platform", false)).setGadgetType(GadgetType.PLATFORM_SLIME).build();
        gadgets.items.add(new PurchasableItem("slime_platform", slime_platform, Main.getSpawnerType("gold"), 1).setCategory(ItemCategory.GADGET).build());
        CustomItem platform = new CustomItem().setMaterial(Material.BLAZE_ROD).setName(ChatColor.BLUE + i18n("item_name_platform", "Platform", false)).setGadgetType(GadgetType.PLATFORM).build();
        gadgets.items.add(new PurchasableItem("platform", platform, Main.getSpawnerType("iron"), 1).setCategory(ItemCategory.GADGET).build());
        CustomItem tp = new CustomItem().setMaterial(Material.MAGMA_CREAM).setName(ChatColor.BLUE + i18n("item_name_teleport", "Teleport", false)).setGadgetType(GadgetType.TP).build();
        gadgets.items.add(new PurchasableItem("tp", tp, Main.getSpawnerType("gold"), 1).setCategory(ItemCategory.GADGET).build());
        CustomItem fireball = new CustomItem().setMaterial(Material.FIRE_CHARGE).setName(ChatColor.BLUE + i18n("item_name_fireball", "Fireball", false)).setGadgetType(GadgetType.FIREBALL).build();
        gadgets.items.add(new PurchasableItem("fireball", fireball, Main.getSpawnerType("bronze"), 96).setCategory(ItemCategory.GADGET).build());
        CustomItem tracker = new CustomItem().setMaterial(Material.COMPASS).setName(ChatColor.BLUE + i18n("item_name_tracker", "Tracker", false)).setGadgetType(GadgetType.TRACKER).build();
        gadgets.items.add(new PurchasableItem("tracker", tracker, Main.getSpawnerType("emerald"), 1).setCategory(ItemCategory.GADGET).build());
        CustomItem wall = new CustomItem().setMaterial(Material.SMOOTH_SANDSTONE).setName(ChatColor.BLUE + i18n("item_name_wall", "Auto-wall", false)).setGadgetType(GadgetType.WALL).build();
        gadgets.items.add(new PurchasableItem("wall", wall, Main.getSpawnerType("bronze"), 16).setCategory(ItemCategory.GADGET).build());

        CustomItem elytra = new CustomItem().setMaterial(Material.ELYTRA).build();
        gadgets.items.add(new PurchasableItem("elytra", elytra, Main.getSpawnerType("emerald"), 5).setCategory(ItemCategory.GADGET).build());
        CustomItem pearl = new CustomItem().setMaterial(Material.ENDER_PEARL).build();
        gadgets.items.add(new PurchasableItem("pearl", pearl, Main.getSpawnerType("emerald"), 3).setCategory(ItemCategory.GADGET).build());
        CustomItem chorus = new CustomItem().setMaterial(Material.CHORUS_FRUIT).setAmount(16).build();
        gadgets.items.add(new PurchasableItem("chorus", chorus, Main.getSpawnerType("iron"), 1).setCategory(ItemCategory.GADGET).build());



        ShopCategory food = new ShopCategory("food", new CustomItem().setMaterial(Material.APPLE).setName(ChatColor.BLUE + "" + ChatColor.BOLD + i18n("shop_category_food", "Food", false)).setLore(null).build());
        CustomItem honey = new CustomItem().setMaterial(Material.HONEY_BOTTLE).setAmount(16).build();
        food.items.add(new PurchasableItem("honey", honey, Main.getSpawnerType("bronze"), 1).setCategory(ItemCategory.ITEM).build());
        CustomItem apple = new CustomItem().setMaterial(Material.APPLE).setAmount(5).build();
        food.items.add(new PurchasableItem("apple", apple, Main.getSpawnerType("bronze"), 2).setCategory(ItemCategory.ITEM).build());
        CustomItem bread = new CustomItem().setMaterial(Material.BREAD).setAmount(1).build();
        food.items.add(new PurchasableItem("bread", bread, Main.getSpawnerType("bronze"), 3).setCategory(ItemCategory.ITEM).build());
        CustomItem ham = new CustomItem().setMaterial(Material.COOKED_MUTTON).setAmount(5).build();
        food.items.add(new PurchasableItem("ham", ham, Main.getSpawnerType("iron"), 2).setCategory(ItemCategory.ITEM).build());
        CustomItem steak = new CustomItem().setMaterial(Material.COOKED_BEEF).setAmount(8).build();
        food.items.add(new PurchasableItem("steak", steak, Main.getSpawnerType("iron"), 4).setCategory(ItemCategory.ITEM).build());
        CustomItem golden_apple = new CustomItem().setMaterial(Material.GOLDEN_APPLE).setAmount(1).build();
        food.items.add(new PurchasableItem("golden_apple", golden_apple, Main.getSpawnerType("gold"), 3).setCategory(ItemCategory.ITEM).build());
        CustomItem golden_carrot = new CustomItem().setMaterial(Material.GOLDEN_CARROT).setAmount(5).build();
        food.items.add(new PurchasableItem("golden_carrot", golden_carrot, Main.getSpawnerType("gold"), 1).setCategory(ItemCategory.ITEM).build());

        ShopCategory tools = new ShopCategory("tools", new CustomItem().setMaterial(Material.WOODEN_PICKAXE).setName(ChatColor.BLUE + "" + ChatColor.BOLD + i18n("shop_category_tools", "Tools", false)).setLore(null).build());
        CustomItem woodenPick = new CustomItem().setMaterial(Material.WOODEN_PICKAXE).build();
        tools.items.add(new PurchasableItem("woodenPick", woodenPick, Main.getSpawnerType("bronze"), 3).setCategory(ItemCategory.ITEM).build());
        CustomItem woodenAxe = new CustomItem().setMaterial(Material.WOODEN_AXE).addAttackDamageModifier(5.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND).addAttackSpeedModifier(-4.0 + 0.7, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND).addMovementSpeedModifier(-0.15, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.HAND).build();
        tools.items.add(new PurchasableItem("woodenAxe", woodenAxe, Main.getSpawnerType("bronze"), 8).setCategory(ItemCategory.ITEM).build());
        CustomItem stonePick = new CustomItem().setMaterial(Material.STONE_PICKAXE).addEnchantment(Enchantment.DURABILITY, 2).addEnchantment(Enchantment.DIG_SPEED, 1).build();
        tools.items.add(new PurchasableItem("stonePick", stonePick, Main.getSpawnerType("iron"), 2).setCategory(ItemCategory.ITEM).build());
        CustomItem stoneAxe = new CustomItem().setMaterial(Material.STONE_AXE).addEnchantment(Enchantment.DIG_SPEED, 1).addAttackDamageModifier(6.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND).addAttackSpeedModifier(-4.0 + 0.65, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND).addMovementSpeedModifier(-0.10, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.HAND).addArmorPointsModifier( 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND).build();
        tools.items.add(new PurchasableItem("stoneAxe", stoneAxe, Main.getSpawnerType("iron"), 4).setCategory(ItemCategory.ITEM).build());
        CustomItem ironPick = new CustomItem().setMaterial(Material.IRON_PICKAXE).addEnchantment(Enchantment.DURABILITY, 3).addEnchantment(Enchantment.DIG_SPEED, 2).build();
        tools.items.add(new PurchasableItem("ironPick", ironPick, Main.getSpawnerType("gold"), 1).setCategory(ItemCategory.ITEM).build());
        CustomItem ironAxe = new CustomItem().setMaterial(Material.IRON_AXE).addEnchantment(Enchantment.DIG_SPEED, 2).addAttackDamageModifier(6.5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND).addAttackSpeedModifier(-4.0 + 0.60, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND).addMovementSpeedModifier(-0.05, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.HAND).addArmorPointsModifier( 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND).build();
        tools.items.add(new PurchasableItem("ironAxe", ironAxe, Main.getSpawnerType("gold"), 3).setCategory(ItemCategory.ITEM).build());
        CustomItem diamondPick = new CustomItem().setMaterial(Material.DIAMOND_PICKAXE).addEnchantment(Enchantment.DURABILITY, 5).addEnchantment(Enchantment.DIG_SPEED, 3).build();
        tools.items.add(new PurchasableItem("diamondPick", diamondPick, Main.getSpawnerType("emerald"), 1, Main.getSpawnerType("gold"), 3).setCategory(ItemCategory.ITEM).build());
        CustomItem diamondAxe = new CustomItem().setMaterial(Material.DIAMOND_AXE).addEnchantment(Enchantment.DIG_SPEED, 3).addAttackDamageModifier(7.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND).addAttackSpeedModifier(-4.0 + 0.55, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND).addArmorPointsModifier( 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND).build();
        tools.items.add(new PurchasableItem("diamondAxe", diamondAxe, Main.getSpawnerType("emerald"), 1, Main.getSpawnerType("gold"), 5).setCategory(ItemCategory.ITEM).build());
        CustomItem shears = new CustomItem().setMaterial(Material.SHEARS).addEnchantment(Enchantment.DIG_SPEED, 2).build();
        tools.items.add(new PurchasableItem("shears", shears, Main.getSpawnerType("iron"), 1).setCategory(ItemCategory.ITEM).build());
        CustomItem shears2 = new CustomItem().setMaterial(Material.SHEARS).addEnchantment(Enchantment.DIG_SPEED, 5).build();
        tools.items.add(new PurchasableItem("shears2", shears2, Main.getSpawnerType("gold"), 1).setCategory(ItemCategory.ITEM).build());
        CustomItem flintnsteel = new CustomItem().setMaterial(Material.FLINT_AND_STEEL).build();
        tools.items.add(new PurchasableItem("flintnsteel", flintnsteel, Main.getSpawnerType("iron"), 1).setCategory(ItemCategory.ITEM).build());

        CustomItem shovel = new CustomItem().setMaterial(Material.WOODEN_SHOVEL).addEnchantment(Enchantment.SILK_TOUCH, 1).addEnchantment(Enchantment.DIG_SPEED, 3).build();
        tools.items.add(new PurchasableItem("shovel", shovel, Main.getSpawnerType("bronze"), 1).setCategory(ItemCategory.ITEM).build());
        CustomItem magic_shovel = new CustomItem().setMaterial(Material.GOLDEN_SHOVEL).addEnchantment(Enchantment.DAMAGE_ALL, 7).addEnchantment(Enchantment.FIRE_ASPECT, 3 ).build();
        tools.items.add(new PurchasableItem("magic_shovel", magic_shovel, Main.getSpawnerType("gold"), 3).setCategory(ItemCategory.ITEM).build());
        CustomItem fishing = new CustomItem().setMaterial(Material.FISHING_ROD).build();
        tools.items.add(new PurchasableItem("fishing", fishing, Main.getSpawnerType("iron"), 1).setCategory(ItemCategory.ITEM).build());


        ShopCategory ranged = new ShopCategory("ranged", new CustomItem().setMaterial(Material.BOW).setName(ChatColor.BLUE + "" + ChatColor.BOLD + i18n("shop_category_ranged", "Ranged", false)).setLore(null).build());
        CustomItem bow1 = new CustomItem().setMaterial(Material.BOW).build();
        ranged.items.add(new PurchasableItem("bow1", bow1, Main.getSpawnerType("iron"), 15).setCategory(ItemCategory.BOW).build());
        CustomItem bow2 = new CustomItem().setMaterial(Material.BOW).addEnchantment(Enchantment.DURABILITY, 2).build();
        ranged.items.add(new PurchasableItem("bow2", bow2, Main.getSpawnerType("gold"), 3).setCategory(ItemCategory.BOW).build());
        CustomItem bow5 = new CustomItem().setMaterial(Material.BOW).addEnchantment(Enchantment.ARROW_DAMAGE, 1).addEnchantment(Enchantment.ARROW_KNOCKBACK, 1).build();
        ranged.items.add(new PurchasableItem("bow5", bow5, Main.getSpawnerType("gold"), 12).setCategory(ItemCategory.BOW).build());
        CustomItem bow3 = new CustomItem().setMaterial(Material.BOW).addEnchantment(Enchantment.ARROW_DAMAGE, 2).build();
        ranged.items.add(new PurchasableItem("bow3", bow3, Main.getSpawnerType("emerald"), 3, Main.getSpawnerType("gold"), 20).setCategory(ItemCategory.BOW).build());
        CustomItem bow4 = new CustomItem().setMaterial(Material.BOW).addEnchantment(Enchantment.ARROW_FIRE, 1).addEnchantment(Enchantment.KNOCKBACK, 1).build();
        ranged.items.add(new PurchasableItem("bow4", bow4, Main.getSpawnerType("emerald"), 2, Main.getSpawnerType("gold"), 12).setCategory(ItemCategory.BOW).build());

        CustomItem crossbow1 = new CustomItem().setMaterial(Material.CROSSBOW).build();
        ranged.items.add(new PurchasableItem("crossbow1", crossbow1, Main.getSpawnerType("iron"), 8).setCategory(ItemCategory.CROSSBOW).build());
//        CustomItem crossbow2 = new CustomItem().setMaterial(Material.CROSSBOW).addEnchantment(Enchantment.QUICK_CHARGE, 1).build();
//        ranged.items.add(new PurchasableItem("crossbow2", crossbow2, Main.getSpawnerType("gold"), 3).setCategory(ItemCategory.CROSSBOW).build());
//        CustomItem crossbow3 = new CustomItem().setMaterial(Material.CROSSBOW).addEnchantment(Enchantment.QUICK_CHARGE, 2).build();
//        ranged.items.add(new PurchasableItem("crossbow3", crossbow3, Main.getSpawnerType("gold"), 7).setCategory(ItemCategory.CROSSBOW).build());
        CustomItem crossbow4 = new CustomItem().setMaterial(Material.CROSSBOW).addEnchantment(Enchantment.QUICK_CHARGE, 3).build();
        ranged.items.add(new PurchasableItem("crossbow4", crossbow4,  Main.getSpawnerType("gold"), 12).setCategory(ItemCategory.CROSSBOW).build());
        CustomItem crossbow5 = new CustomItem().setMaterial(Material.CROSSBOW).addEnchantment(Enchantment.QUICK_CHARGE, 3).addEnchantment(Enchantment.PIERCING, 10).build();
        ranged.items.add(new PurchasableItem("crossbow5", crossbow5, Main.getSpawnerType("emerald"), 1, Main.getSpawnerType("gold"), 12).setCategory(ItemCategory.CROSSBOW).build());
        CustomItem crossbow6 = new CustomItem().setMaterial(Material.CROSSBOW).addEnchantment(Enchantment.QUICK_CHARGE, 3).addEnchantment(Enchantment.MULTISHOT, 1).build();
        ranged.items.add(new PurchasableItem("crossbow6", crossbow6, Main.getSpawnerType("emerald"), 1, Main.getSpawnerType("gold"), 12).setCategory(ItemCategory.CROSSBOW).build());

        CustomItem trident1 = new CustomItem().setMaterial(Material.TRIDENT).addEnchantment(Enchantment.LOYALTY, 2).build();
        ranged.items.add(new PurchasableItem("trident1", trident1, Main.getSpawnerType("gold"), 8).setCategory(ItemCategory.ITEM).build());
        CustomItem trident2 = new CustomItem().setMaterial(Material.TRIDENT).addEnchantment(Enchantment.LOYALTY, 5).addEnchantment(Enchantment.DAMAGE_ALL, 1).build();
        ranged.items.add(new PurchasableItem("trident2", trident2, Main.getSpawnerType("emerald"), 1, Main.getSpawnerType("gold"), 16).setCategory(ItemCategory.ITEM).build());
//        CustomItem trident3 = new CustomItem().setMaterial(Material.TRIDENT).addEnchantment(Enchantment.LOYALTY, 4).addEnchantment(Enchantment.DAMAGE_ALL, 2).build();
//        ranged.items.add(new PurchasableItem("trident3", trident3, Main.getSpawnerType("emerald"), 1, Main.getSpawnerType("gold"), 16).setCategory(ItemCategory.ITEM).build());
//        CustomItem trident4 = new CustomItem().setMaterial(Material.TRIDENT).addEnchantment(Enchantment.LOYALTY, 5).addEnchantment(Enchantment.DAMAGE_ALL, 3).build();
//        ranged.items.add(new PurchasableItem("trident4", trident4, Main.getSpawnerType("emerald"), 2, Main.getSpawnerType("gold"), 20).setCategory(ItemCategory.ITEM).build());


        CustomItem arrow = new CustomItem().setMaterial(Material.ARROW).setAmount(2).build();
        ranged.items.add(new PurchasableItem("arrow", arrow, Main.getSpawnerType("iron"), 1).setCategory(ItemCategory.ITEM).setExclusionFlag(GameFlag.AGILITY_LEVEL_3).build());
        CustomItem arrowDiscounted = new CustomItem().setMaterial(Material.ARROW).setAmount(3).build();
        ranged.items.add(new PurchasableItem("arrowDiscounted", arrowDiscounted, Main.getSpawnerType("iron"), 1).setCategory(ItemCategory.ITEM).setVisibility(false).setInclusionFlag(GameFlag.AGILITY_LEVEL_3).build());
        CustomItem arrow2 = new CustomItem().setMaterial(Material.SPECTRAL_ARROW).setAmount(8).build();
        ranged.items.add(new PurchasableItem("arrow2", arrow2, Main.getSpawnerType("gold"), 1).setCategory(ItemCategory.ITEM).setExclusionFlag(GameFlag.AGILITY_LEVEL_3).build());
        CustomItem arrow2Discounted = new CustomItem().setMaterial(Material.SPECTRAL_ARROW).setAmount(12).build();
        ranged.items.add(new PurchasableItem("arrow2Discounted", arrow2Discounted, Main.getSpawnerType("gold"), 1).setCategory(ItemCategory.ITEM).setVisibility(false).setInclusionFlag(GameFlag.AGILITY_LEVEL_3).build());


        CustomItem arrowwither = new CustomItem().setMaterial(Material.TIPPED_ARROW).addPotionEffect( new PotionEffect(PotionEffectType.WITHER, 100, 1), Color.fromRGB(38, 38, 38)).setAmount(1).build();
        ranged.items.add(new PurchasableItem("arrowwither", arrowwither, Main.getSpawnerType("iron"), 1).setCategory(ItemCategory.ITEM).setVisibility(false).setInclusionFlag(GameFlag.AGILITY_LEVEL_4A).build());
        CustomItem arrowpoison = new CustomItem().setMaterial(Material.TIPPED_ARROW).addPotionEffect( new PotionEffect(PotionEffectType.POISON, 160, 0), Color.fromRGB(64, 112, 33)).setAmount(1).build();
        ranged.items.add(new PurchasableItem("arrowpoison", arrowpoison, Main.getSpawnerType("iron"), 1).setCategory(ItemCategory.ITEM).setVisibility(false).setInclusionFlag(GameFlag.AGILITY_LEVEL_4B).build());


        ArrayList<String> supportArrow = new ArrayList<>();
        supportArrow.add("");
        supportArrow.add(i18n("description_support_arrow1", "These arrows have", false));
        supportArrow.add(i18n("description_support_arrow2", "special functionality and don\'t", false));
        supportArrow.add(i18n("description_support_arrow3", "work on enemy players.", false));

        CustomItem arrowheal = new CustomItem().setMaterial(Material.TIPPED_ARROW).setBasePotionData(PotionType.INSTANT_HEAL, false, false).setAmount(8).setLore(supportArrow).removeLoreOnPurchase().build();
        ranged.items.add(new PurchasableItem("arrowheal", arrowheal, Main.getSpawnerType("gold"), 1).setCategory(ItemCategory.ITEM).setExclusionFlag(GameFlag.AGILITY_LEVEL_3).build());
        CustomItem arrowhealdiscounted = new CustomItem().setMaterial(Material.TIPPED_ARROW).setBasePotionData(PotionType.INSTANT_HEAL, false, false).setAmount(12).setLore(supportArrow).removeLoreOnPurchase().build();
        ranged.items.add(new PurchasableItem("arrowhealdiscounted", arrowhealdiscounted, Main.getSpawnerType("gold"), 1).setCategory(ItemCategory.ITEM).setVisibility(false).setInclusionFlag(GameFlag.AGILITY_LEVEL_3).build());
        CustomItem arrowregen = new CustomItem().setMaterial(Material.TIPPED_ARROW).setBasePotionData(PotionType.REGEN, false, true).setAmount(8).setLore(supportArrow).removeLoreOnPurchase().build();
        ranged.items.add(new PurchasableItem("arrowregen", arrowregen, Main.getSpawnerType("iron"), 1).setCategory(ItemCategory.ITEM).setExclusionFlag(GameFlag.AGILITY_LEVEL_3).build());
        CustomItem arrowregendiscounted = new CustomItem().setMaterial(Material.TIPPED_ARROW).setBasePotionData(PotionType.REGEN, false, true).setAmount(12).setLore(supportArrow).removeLoreOnPurchase().build();
        ranged.items.add(new PurchasableItem("arrowregendiscounted", arrowregendiscounted, Main.getSpawnerType("iron"), 1).setCategory(ItemCategory.ITEM).setVisibility(false).setInclusionFlag(GameFlag.AGILITY_LEVEL_3).build());

        ArrayList<String> shootableFirework = new ArrayList<>();
        shootableFirework.add("");
        shootableFirework.add(i18n("description_ammo_firework1", "--", false));
        shootableFirework.add(i18n("description_ammo_firework2", "--", false));
        shootableFirework.add("");
        shootableFirework.add(i18n("description_ammo_firework3", "--", false));
        shootableFirework.add(i18n("description_ammo_firework4", "--", false));

        CustomItem firework = new CustomItem().setMaterial(Material.FIREWORK_ROCKET).setAmount(8).setFirework().setLore(shootableFirework).removeLoreOnPurchase().build();
        ranged.items.add(new PurchasableItem("firework", firework, Main.getSpawnerType("gold"), 1).setCategory(ItemCategory.FIREWORK).setExclusionFlag(GameFlag.AGILITY_LEVEL_3).build());
        gadgets.items.add(new PurchasableItem("fireworkalt", firework, Main.getSpawnerType("gold"), 1).setCategory(ItemCategory.FIREWORK).setExclusionFlag(GameFlag.AGILITY_LEVEL_3).build());

        CustomItem fireworkdiscounted = new CustomItem().setMaterial(Material.FIREWORK_ROCKET).setAmount(12).setFirework().setLore(shootableFirework).removeLoreOnPurchase().build();
        ranged.items.add(new PurchasableItem("fireworkdiscounted", fireworkdiscounted, Main.getSpawnerType("gold"), 1).setCategory(ItemCategory.FIREWORK).setVisibility(false).setInclusionFlag(GameFlag.AGILITY_LEVEL_3).build());
        gadgets.items.add(new PurchasableItem("fireworkdiscountedalt", fireworkdiscounted, Main.getSpawnerType("gold"), 1).setCategory(ItemCategory.FIREWORK).setVisibility(false).setInclusionFlag(GameFlag.AGILITY_LEVEL_3).build());


        CustomItem snow = new CustomItem().setMaterial(Material.SNOWBALL).setAmount(8).build();
        ranged.items.add(new PurchasableItem("snow", snow, Main.getSpawnerType("iron"), 1).setCategory(ItemCategory.ITEM).setExclusionFlag(GameFlag.AGILITY_LEVEL_3).build());
        CustomItem snowdiscounted = new CustomItem().setMaterial(Material.SNOWBALL).setAmount(12).build();
        ranged.items.add(new PurchasableItem("snowdiscounted", snowdiscounted, Main.getSpawnerType("iron"), 1).setCategory(ItemCategory.ITEM).setVisibility(false).setInclusionFlag(GameFlag.AGILITY_LEVEL_3).build());

        ShopCategory potions = new ShopCategory("potions", new CustomItem().setMaterial(Material.BREWING_STAND).setName(ChatColor.BLUE + "" + ChatColor.BOLD + i18n("shop_category_potions", "Potions", false)).setLore(null).build());
        CustomItem pot1 = new CustomItem().setMaterial(Material.LINGERING_POTION).addPotionEffect( new PotionEffect(PotionEffectType.HUNGER, 1200, 100, true, false), Color.GREEN).setName(ChatColor.BLUE + "" + ChatColor.BOLD + i18n("item_potion_hunger", "Potion of hunger", false)).build();
        potions.items.add(new PurchasableItem("pot1", pot1, Main.getSpawnerType("iron"), 2).setCategory(ItemCategory.ITEM).build());

        CustomItem speed1 = new CustomItem().setMaterial(Material.POTION).addPotionEffect( new PotionEffect(PotionEffectType.SPEED, 1200, 1, true, false), Color.fromRGB(158, 134, 203)).setName(ChatColor.BLUE + "" + ChatColor.BOLD + i18n("item_potion_speed", "Potion of Swiftness", false)).build();
        potions.items.add(new PurchasableItem("speed1", speed1, Main.getSpawnerType("iron"), 2).setCategory(ItemCategory.ITEM).build());
        CustomItem speed2 = new CustomItem().setMaterial(Material.POTION).addPotionEffect( new PotionEffect(PotionEffectType.JUMP, 600, 2, true, false)).addPotionEffect( new PotionEffect(PotionEffectType.SPEED, 600, 3, true, false), Color.fromRGB(158, 134, 203)).setName(ChatColor.BLUE + "" + ChatColor.BOLD + i18n("item_potion_speed2", "Potion of Swiftness", false)).build();
        potions.items.add(new PurchasableItem("speed2", speed2, Main.getSpawnerType("emerald"), 1).setCategory(ItemCategory.ITEM).build());
        CustomItem heal1 = new CustomItem().setMaterial(Material.POTION).addPotionEffect( new PotionEffect(PotionEffectType.HEAL, 0, 1, true, false), Color.RED).setName(ChatColor.BLUE + "" + ChatColor.BOLD + i18n("item_potion_healing", "Potion of Healing", false)).build();
        potions.items.add(new PurchasableItem("heal1", heal1, Main.getSpawnerType("iron"), 1).setCategory(ItemCategory.ITEM).build());
        CustomItem heal2 = new CustomItem().setMaterial(Material.POTION).addPotionEffect( new PotionEffect(PotionEffectType.HEAL, 0, 2, true, false), Color.RED).setName(ChatColor.BLUE + "" + ChatColor.BOLD + i18n("item_potion_healing2", "Potion of Healing 2", false)).build();
        potions.items.add(new PurchasableItem("heal2", heal2, Main.getSpawnerType("gold"), 1).setCategory(ItemCategory.ITEM).build());
        CustomItem inv1 = new CustomItem().setMaterial(Material.POTION).addPotionEffect( new PotionEffect(PotionEffectType.INVISIBILITY, 240, 0, true, false), Color.SILVER).addPotionEffect( new PotionEffect(PotionEffectType.SLOW, 250, 3, true, false)).addPotionEffect( new PotionEffect(PotionEffectType.SLOW_DIGGING, 250, 5, true, false)).setName(ChatColor.BLUE + "" + ChatColor.BOLD + i18n("item_potion_invisibility", "Potion of Invisibility", false)).build();
        potions.items.add(new PurchasableItem("inv1", inv1, Main.getSpawnerType("iron"), 6).setCategory(ItemCategory.ITEM).build());
        CustomItem inv2 = new CustomItem().setMaterial(Material.POTION).addPotionEffect( new PotionEffect(PotionEffectType.INVISIBILITY, 480, 0, true, false), Color.SILVER).addPotionEffect( new PotionEffect(PotionEffectType.SLOW, 480, 2, true, false)).addPotionEffect( new PotionEffect(PotionEffectType.SLOW_DIGGING, 480, 5, true, false)).setName(ChatColor.BLUE + "" + ChatColor.BOLD + i18n("item_potion_invisibility2", "Potion of Invisibility", false)).build();
        potions.items.add(new PurchasableItem("inv2", inv2, Main.getSpawnerType("emerald"), 2, Main.getSpawnerType("gold"), 8).setCategory(ItemCategory.ITEM).build());
        CustomItem inv3 = new CustomItem().setMaterial(Material.POTION).addPotionEffect( new PotionEffect(PotionEffectType.INVISIBILITY, 720, 0, true, false), Color.SILVER).addPotionEffect( new PotionEffect(PotionEffectType.SLOW, 720, 1, true, false)).addPotionEffect( new PotionEffect(PotionEffectType.SLOW_DIGGING, 720, 2, true, false)).addPotionEffect( new PotionEffect(PotionEffectType.SLOW_FALLING, 720, 0, true, false)).setName(ChatColor.BLUE + "" + ChatColor.BOLD + i18n("item_potion_invisibility3", "Potion of Invisibility", false)).build();
        potions.items.add(new PurchasableItem("inv3", inv3, Main.getSpawnerType("emerald"), 4,Main.getSpawnerType("gold"), 20).setCategory(ItemCategory.ITEM).build());

        CustomItem strength = new CustomItem().setMaterial(Material.POTION).addPotionEffect( new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 340, 0, true, false), Color.fromRGB(99, 5, 19)).setName(ChatColor.BLUE + "" + ChatColor.BOLD + i18n("item_potion_strength", "Potion of Strength", false)).build();
        potions.items.add(new PurchasableItem("strength", strength, Main.getSpawnerType("gold"), 4).setCategory(ItemCategory.ITEM).setVisibility(false).setInclusionFlag(GameFlag.INTELLECT_LEVEL_4A).build());
        CustomItem resist = new CustomItem().setMaterial(Material.POTION).addPotionEffect( new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 400, 0, true, false), Color.fromRGB(7, 109, 148)).setName(ChatColor.BLUE + "" + ChatColor.BOLD + i18n("item_potion_resistance", "Potion of Damage Resistance", false)).build();
        potions.items.add(new PurchasableItem("resist", resist, Main.getSpawnerType("gold"), 4).setCategory(ItemCategory.ITEM).setVisibility(false).setInclusionFlag(GameFlag.INTELLECT_LEVEL_4B).build());


        CustomItem slow1 = new CustomItem().setMaterial(Material.SPLASH_POTION).addPotionEffect( new PotionEffect(PotionEffectType.SLOW, 300, 1, true, false), Color.GRAY).setName(ChatColor.BLUE + "" + ChatColor.BOLD + i18n("item_potion_slow", "Potion of Slowness", false)).build();
        potions.items.add(new PurchasableItem("slow1", slow1, Main.getSpawnerType("iron"), 2).setCategory(ItemCategory.ITEM).build());
        CustomItem slow2 = new CustomItem().setMaterial(Material.LINGERING_POTION).addPotionEffect( new PotionEffect(PotionEffectType.SLOW_DIGGING, 240, 2, true, false)).addPotionEffect( new PotionEffect(PotionEffectType.SLOW, 240, 3, true, false), Color.GRAY).setName(ChatColor.BLUE + "" + ChatColor.BOLD + i18n("item_potion_slow2", "Potion of Slowness 2", false)).build();
        potions.items.add(new PurchasableItem("slow2", slow2, Main.getSpawnerType("gold"), 1).setCategory(ItemCategory.ITEM).build());

        CustomItem poison1 = new CustomItem().setMaterial(Material.SPLASH_POTION).addPotionEffect( new PotionEffect(PotionEffectType.POISON, 200, 1, true, false), Color.fromRGB(136, 204, 0)).setName(ChatColor.BLUE + "" + ChatColor.BOLD + i18n("item_potion_poison", "Potion of Poison", false)).build();
        potions.items.add(new PurchasableItem("poison1", poison1, Main.getSpawnerType("iron"), 2).setCategory(ItemCategory.ITEM).build());
        CustomItem poison2 = new CustomItem().setMaterial(Material.LINGERING_POTION).addPotionEffect( new PotionEffect(PotionEffectType.POISON, 160, 3, true, false), Color.fromRGB(136, 204, 0)).setName(ChatColor.BLUE + "" + ChatColor.BOLD + i18n("item_potion_poison2", "Potion of Poison 2", false)).build();
        potions.items.add(new PurchasableItem("poison2", poison2, Main.getSpawnerType("gold"), 1).setCategory(ItemCategory.ITEM).build());




        ShopCategory special = new ShopCategory("special", new CustomItem().setMaterial(Material.NETHER_STAR).setName(ChatColor.BLUE + "" + ChatColor.BOLD + i18n("shop_category_special", "Special", false)).setLore(null).build());
        CustomItem bronze_for_iron = new CustomItem().setItemstack(Main.getSpawnerType("bronze").getStack()).setAmount(10).build();
        special.items.add(new PurchasableItem("bronze_for_iron", bronze_for_iron, Main.getSpawnerType("iron"), 1).setCategory(ItemCategory.ITEM).setInclusionFlag(GameFlag.GOLD_AND_EMERALDS_SPAWN).setVisibility(false).build());
        CustomItem iron_for_gold = new CustomItem().setItemstack(Main.getSpawnerType("iron").getStack()).setAmount(6).build();
        special.items.add(new PurchasableItem("iron_for_gold", iron_for_gold, Main.getSpawnerType("gold"), 1).setCategory(ItemCategory.ITEM).setInclusionFlag(GameFlag.GOLD_AND_EMERALDS_SPAWN).setVisibility(false).build());
        CustomItem gold_for_emeralds = new CustomItem().setItemstack(Main.getSpawnerType("gold").getStack()).setAmount(3).build();
        special.items.add(new PurchasableItem("gold_for_emeralds", gold_for_emeralds, Main.getSpawnerType("emerald"), 1).setCategory(ItemCategory.ITEM).setInclusionFlag(GameFlag.GOLD_AND_EMERALDS_SPAWN).setVisibility(false).build());

        ArrayList<String> roleplayLore = new ArrayList<>();
        roleplayLore.add("");
        roleplayLore.add(ChatColor.GRAY + i18n("shop_category_roleplay_description", "Crafting out of those items is partially enabled.", false));
        ShopCategory roleplay = new ShopCategory("roleplay", new CustomItem().setMaterial(Material.LANTERN).setName(ChatColor.BLUE + "" + ChatColor.BOLD + i18n("shop_category_roleplay", "Weird", false)).setLore(roleplayLore).build());
        CustomItem log_dark_oak = new CustomItem().setMaterial(Material.DARK_OAK_LOG).build();
        roleplay.items.add(new PurchasableItem("log_dark_oak", log_dark_oak, Main.getSpawnerType("bronze"), 3).setCategory(ItemCategory.ITEM).build());
        CustomItem log_spruce = new CustomItem().setMaterial(Material.SPRUCE_LOG).build();
        roleplay.items.add(new PurchasableItem("log_spruce", log_spruce, Main.getSpawnerType("bronze"), 3).setCategory(ItemCategory.ITEM).build());
        CustomItem log_birch = new CustomItem().setMaterial(Material.BIRCH_LOG).build();
        roleplay.items.add(new PurchasableItem("log_birch", log_birch, Main.getSpawnerType("bronze"), 3).setCategory(ItemCategory.ITEM).build());
        CustomItem stone = new CustomItem().setMaterial(Material.STONE).build();
        roleplay.items.add(new PurchasableItem("stone", stone, Main.getSpawnerType("bronze"), 4).setCategory(ItemCategory.ITEM).build());
        CustomItem cobblestone = new CustomItem().setMaterial(Material.COBBLESTONE).build();
        roleplay.items.add(new PurchasableItem("cobblestone", cobblestone, Main.getSpawnerType("bronze"), 4).setCategory(ItemCategory.ITEM).build());
        CustomItem torch = new CustomItem().setMaterial(Material.TORCH).build();
        roleplay.items.add(new PurchasableItem("torch", torch, Main.getSpawnerType("bronze"), 1).setCategory(ItemCategory.ITEM).build());
        CustomItem lantern = new CustomItem().setMaterial(Material.LANTERN).build();
        roleplay.items.add(new PurchasableItem("lantern", lantern, Main.getSpawnerType("bronze"), 1).setCategory(ItemCategory.ITEM).build());
        CustomItem dirt = new CustomItem().setMaterial(Material.DIRT).build();
        roleplay.items.add(new PurchasableItem("dirt", dirt, Main.getSpawnerType("bronze"), 1).setCategory(ItemCategory.ITEM).build());
        CustomItem coarse = new CustomItem().setMaterial(Material.COARSE_DIRT).build();
        roleplay.items.add(new PurchasableItem("coarse", coarse, Main.getSpawnerType("bronze"), 1).setCategory(ItemCategory.ITEM).build());
        CustomItem grass_block = new CustomItem().setMaterial(Material.GRASS_BLOCK).build();
        roleplay.items.add(new PurchasableItem("grass_block", grass_block, Main.getSpawnerType("bronze"), 1).setCategory(ItemCategory.ITEM).build());
        //        CustomItem mossy_cobblestone = new CustomItem().setMaterial(Material.MOSSY_COBBLESTONE).build();
//        roleplay.items.add(new CustomGUIPurchasableItem("mossy_cobblestone", mossy_cobblestone, Main.getSpawnerType("bronze"), 1).setCategory(ItemCategory.ITEM).build());
//        CustomItem mossy_bricks = new CustomItem().setMaterial(Material.MOSSY_STONE_BRICKS).build();
//        roleplay.items.add(new CustomGUIPurchasableItem("mossy_bricks", mossy_bricks, Main.getSpawnerType("bronze"), 1).setCategory(ItemCategory.ITEM).build());
        CustomItem frame = new CustomItem().setMaterial(Material.ITEM_FRAME).build();
        roleplay.items.add(new PurchasableItem("frame", frame, Main.getSpawnerType("bronze"), 1).setCategory(ItemCategory.ITEM).build());
        CustomItem bone_block = new CustomItem().setMaterial(Material.BONE_BLOCK).build();
        roleplay.items.add(new PurchasableItem("bone_block", bone_block, Main.getSpawnerType("bronze"), 1).setCategory(ItemCategory.ITEM).build());
        CustomItem bricks = new CustomItem().setMaterial(Material.BRICK).setAmount(4).build();
        roleplay.items.add(new PurchasableItem("bricks", bricks, Main.getSpawnerType("bronze"), 1).setCategory(ItemCategory.ITEM).build());
        CustomItem rose_bush = new CustomItem().setMaterial(Material.ROSE_BUSH).build();
        roleplay.items.add(new PurchasableItem("rose_bush", rose_bush, Main.getSpawnerType("bronze"), 1).setCategory(ItemCategory.ITEM).build());
        CustomItem tall_grass = new CustomItem().setMaterial(Material.TALL_GRASS).build();
        roleplay.items.add(new PurchasableItem("tall_grass", tall_grass, Main.getSpawnerType("bronze"), 1).setCategory(ItemCategory.ITEM).build());
        CustomItem sunflower = new CustomItem().setMaterial(Material.SUNFLOWER).build();
        roleplay.items.add(new PurchasableItem("sunflower", sunflower, Main.getSpawnerType("bronze"), 1).setCategory(ItemCategory.ITEM).build());
        CustomItem blue_orchid = new CustomItem().setMaterial(Material.BLUE_ORCHID).build();
        roleplay.items.add(new PurchasableItem("blue_orchid", blue_orchid, Main.getSpawnerType("bronze"), 1).setCategory(ItemCategory.ITEM).build());
        CustomItem cornflower = new CustomItem().setMaterial(Material.CORNFLOWER).build();
        roleplay.items.add(new PurchasableItem("cornflower", cornflower, Main.getSpawnerType("bronze"), 1).setCategory(ItemCategory.ITEM).build());
        CustomItem green_dye = new CustomItem().setMaterial(Material.GREEN_DYE).build();
        roleplay.items.add(new PurchasableItem("green_dye", green_dye, Main.getSpawnerType("bronze"), 1).setCategory(ItemCategory.ITEM).build());
        CustomItem wither_rose = new CustomItem().setMaterial(Material.WITHER_ROSE).build();
        roleplay.items.add(new PurchasableItem("wither_rose", wither_rose, Main.getSpawnerType("bronze"), 1).setCategory(ItemCategory.ITEM).build());
        CustomItem cocoa = new CustomItem().setMaterial(Material.COCOA_BEANS).build();
        roleplay.items.add(new PurchasableItem("cocoa", cocoa, Main.getSpawnerType("bronze"), 1).setCategory(ItemCategory.ITEM).build());



        this.categories.add(tools);
        this.categories.add(weapons);
        this.categories.add(ranged);
        this.categories.add(armour);
        this.categories.add(blocks);
        this.categories.add(food);
        this.categories.add(gadgets);
        this.categories.add(potions);
        this.categories.add(special);
        this.categories.add(roleplay);

    }

}
