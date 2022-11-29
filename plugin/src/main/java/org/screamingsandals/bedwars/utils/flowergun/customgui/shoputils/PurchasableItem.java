package org.screamingsandals.bedwars.utils.flowergun.customgui.shoputils;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CustomItem;
import org.screamingsandals.bedwars.game.*;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ItemCategory;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.Triggers;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Map;

import static org.screamingsandals.bedwars.lib.lang.I.*;
import static org.screamingsandals.bedwars.lib.lang.I.i18nonly;

public class PurchasableItem {

    @Getter
    private GameFlag inclusionFlag;

    @Getter
    private GameFlag exclusionFlag;

    public PurchasableItem setInclusionFlag(GameFlag gameFlag){
        this.inclusionFlag = gameFlag;
        return this;
    }

    public PurchasableItem setExclusionFlag(GameFlag gameFlag){
        this.exclusionFlag = gameFlag;
        return this;
    }

    @Setter
    @Getter
    private String id;

    public CustomItem item;

    @Getter
    @Setter
    private ItemCategory itemCategory;

    private PurchasableItem originalPurchasibleItem = null;

    public ItemSpawnerType priceType1;
    public int priceAmount1;
    public ItemSpawnerType priceType2;
    public int priceAmount2;

    public boolean visible = true;




    public PurchasableItem(String id, CustomItem customItem, ItemSpawnerType type1, int amount1, ItemSpawnerType type2, int amount2) {
        this.priceType1 = type1;
        this.priceType2 = type2;
        this.priceAmount1 = amount1;
        this.priceAmount2 = amount2;

        this.id = id;
        this.item = customItem;

        this.itemCategory = ItemCategory.ITEM;

    }

    public PurchasableItem(String id, CustomItem customItem, ItemSpawnerType type1, int amount1) {
        this.priceType1 = type1;
        this.priceAmount1 = amount1;

        this.id = id;
        this.item = customItem;

        ItemMeta itemMeta = customItem.getItem().getItemMeta();
//        Main.getInstance().getLogger().info("in constructor ? ??? " + ChatColor.RED + id + ": " + itemMeta.getPersistentDataContainer().get(new NamespacedKey( Main.getInstance() , "screaming-bedwars-hidden-api"), PersistentDataType.STRING));
//        Main.getInstance().getLogger().info("in purchasable " + ChatColor.RED + id + ": " + this.item.getItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey( Main.getInstance() , "screaming-bedwars-hidden-api"), PersistentDataType.STRING));
        this.itemCategory = ItemCategory.ITEM;
    }

    public PurchasableItem setCategory(ItemCategory itemCategory ){
        this.itemCategory = itemCategory;
        return this;
    }


    public PurchasableItem setVisibility(boolean visible )
    {
        this.visible = visible;
        return this;
    }

    public PurchasableItem build() {
        return this;
    }

    public void giveForFree(Player player) {
        ItemStack buyWhat = this.item.prepareItem(player);
        player.getInventory().addItem(buyWhat);

    }

    public boolean tryToSellTo(Player player, boolean isRight, boolean isLeft, boolean isShift) {

        //TODO implement buying items

        PlayerInventory inventory = player.getInventory();

        ItemStack buyFor1 = this.priceType1.getStack().clone();
        int buyForAmount1base = this.priceAmount1;
        int buyForAmount1 = buyForAmount1base;
        ItemStack buyFor2 = null;
        int buyForAmount2base = 0;
        int buyForAmount2 = buyForAmount2base;

        buyFor1.setAmount(buyForAmount1);

        boolean use2currencies = false;
        if (priceType2 != null) use2currencies = true;

        if (use2currencies)
        {
            buyFor2 = this.priceType2.getStack();
            buyForAmount2base = this.priceAmount2;
            buyForAmount2 = buyForAmount2base;
            buyFor2.setAmount(buyForAmount2);
        }

        int buyForAmountPresent1 = 0;
        int buyForAmountPresent2 = 0;

        for ( ItemStack slot: inventory.getContents() )
            if (slot != null) {
                if ( slot.getType() == buyFor1.getType() ) buyForAmountPresent1 += slot.getAmount();
                if (use2currencies)
                if ( slot.getType() == buyFor2.getType() ) buyForAmountPresent2 += slot.getAmount();
            }

        if ( buyForAmountPresent1 >= this.priceAmount1 && buyForAmountPresent2 >= this.priceAmount2 ) {


            ItemStack buyWhat = this.item.getItem().clone();

            Material material = buyWhat.getType();

            int maxAffordableRepeats;
            if (use2currencies)
            maxAffordableRepeats = Math.min(buyForAmountPresent1 / priceAmount1, buyForAmountPresent2 / priceAmount2 );
            else maxAffordableRepeats = buyForAmountPresent1 / priceAmount1;
            int maxStackRepeats = buyWhat.getMaxStackSize() / buyWhat.getAmount();
            int finalRepeats = 0;

            if ( !isShift ) finalRepeats = 1;
            else if ( isShift && isLeft ) {
                finalRepeats = Math.min( maxStackRepeats , maxAffordableRepeats );
            } else if ( isShift && isRight ) {
                finalRepeats = maxAffordableRepeats;
                if ( FlowerUtils.helmets.contains(material) ||
                        FlowerUtils.chestplates.contains(material) ||
                        FlowerUtils.leggins.contains(material) ||
                        FlowerUtils.boots.contains(material)) finalRepeats = 1;
            }



//            if (this.canBeDyed) {
//                LeatherArmorMeta itemMeta = (LeatherArmorMeta) buyWhat.getItemMeta();
//                itemMeta.setColor(teamColor.leatherColor);
//                buyWhat.setItemMeta(itemMeta);
//            }
//
//            if (this.isFirework) {
//                FireworkMeta feuermeta = (FireworkMeta) buyWhat.getItemMeta();
//                feuermeta.setPower(3);
//                feuermeta.addEffect(FireworkEffect.builder().withFade(Color.WHITE).withColor(teamColor.leatherColor).with(FireworkEffect.Type.BALL_LARGE).build());
//                buyWhat.setItemMeta(feuermeta);
//            }

            boolean armorAutoEquip = Main.getPlayerGameProfile(player).hasFlag(GameFlag.VITALITY_LEVEL_1);

            Triggers.shopPurchase(Main.getPlayerGameProfile(player).getGame(), player, this, finalRepeats);

            while (finalRepeats > 0) {
                //exchange

                buyWhat = this.item.prepareItem(player);

                buyForAmount1 = buyForAmount1base;
                buyForAmount2 = buyForAmount2base;

                for ( ItemStack slot: inventory.getContents()) {
                    if (slot == null) continue;

                    if (slot.getType() == buyFor1.getType()) {
                        if (buyForAmountPresent1 == 0) continue;
                        if (slot.getAmount() > buyForAmount1) {
                            slot.setAmount(slot.getAmount() - buyForAmount1);
                            buyForAmount1 = 0;
                        } else {
                            buyForAmount1 -= slot.getAmount();
                            slot.setAmount(0);
                        }
                    }
                    if (use2currencies)
                        if (slot.getType() == buyFor2.getType()) {
                            if (buyForAmountPresent2 == 0) continue;
                            if (slot.getAmount() > buyForAmount2) {
                                slot.setAmount(slot.getAmount() - buyForAmount2);
                                buyForAmount2 = 0;
                            } else {
                                buyForAmount2 -= slot.getAmount();
                                slot.setAmount(0);
                            }
                        }

                    if ( buyForAmount1 <= 0 && buyForAmount2 <= 0 ) break;
                }

                //player.getInventory().addItem(buyFor);


                if (armorAutoEquip) {
                    if (inventory.getHelmet() == null && FlowerUtils.helmets.contains(material)) inventory.setHelmet(buyWhat);
                    else if (inventory.getChestplate() == null && FlowerUtils.chestplates.contains(material)) inventory.setChestplate(buyWhat);
                    else if (inventory.getLeggings() == null && FlowerUtils.leggins.contains(material)) inventory.setLeggings(buyWhat);
                    else if (inventory.getBoots() == null && FlowerUtils.boots.contains(material)) inventory.setBoots(buyWhat);
                    else {
                        Map<Integer, ItemStack> map = player.getInventory().addItem(buyWhat);
                        map.forEach((integer, itemStack) -> player.getLocation().getWorld().dropItem(player.getLocation(), itemStack));
                    }
                }
                else {
                    Map<Integer, ItemStack> map = player.getInventory().addItem(buyWhat);
                    map.forEach((integer, itemStack) -> player.getLocation().getWorld().dropItem(player.getLocation(), itemStack));
                }
                finalRepeats--;
            }

            player.playSound( player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F );

            if ( Main.getPlayerGameProfile(player).hasFlag(GameFlag.AGILITY_LEVEL_1) ) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 600, 0, false, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, 0, false, false));
            }
            if ( Main.getPlayerGameProfile(player).hasFlag(GameFlag.AGILITY_LEVEL_3) )
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 200, 3, false, false));

            return true;
//            Main.getInstance().getLogger().info( ChatColor.GREEN + i18n("new_buy_success", "You bought:", false));
        } else {
            //error
            player.playSound( player.getLocation(), Sound.ENTITY_VILLAGER_HURT, 1.0F, 1.0F );
            return false;
//            player.sendTitle("", ChatColor.RED + i18n("new_buy_fail", "Not enough resources.", false), 5, 10, 5);
        }

    }

    public PurchasableItem copy() {
        PurchasableItem purchasableItem;
        if (this.priceType2 != null)
            purchasableItem = new PurchasableItem(this.id, this.item.copy(), this.priceType1, this.priceAmount1, this.priceType2, this.priceAmount2);
        else
            purchasableItem = new PurchasableItem(this.id, this.item.copy(), this.priceType1, this.priceAmount1);

        purchasableItem.visible = this.visible;
        purchasableItem.inclusionFlag = this.inclusionFlag;
        purchasableItem.exclusionFlag = this.exclusionFlag;
        purchasableItem.itemCategory = this.itemCategory;

        return purchasableItem;
    }

//    public void updateDescriptionComparingTo(PurchasableItem originalItem) {
//        this.item.setLore(generateDescription(originalItem));
//    }

    public ArrayList<String> generateDescription(@Nullable PurchasableItem originalItem) {
        ArrayList<String> price = new ArrayList<>();
        price.add("");

        ItemStack buyFor1 = this.priceType1.getStack();
        ItemStack buyWhat = this.item.getItem();
        int buyForAmount1 = this.priceAmount1;

        String icon;

        switch (net.md_5.bungee.api.ChatColor.stripColor(buyFor1.getItemMeta().getDisplayName())) {
            case "Медь": {
                icon = i18nonly("resource_bronze_icon");
                break;
            }
            case "Железо": {
                icon = i18nonly("resource_iron_icon");
                break;
            }
            case "Золото": {
                icon = i18nonly("resource_gold_icon");
                break;
            }
            case "Изумруд": {
                icon = i18nonly("resource_emerald_icon");
                break;
            }
            default: {
                icon = "?";
                break;
            }
        }

        if (originalPurchasibleItem != null && originalPurchasibleItem.item.getItem().getAmount() != buyWhat.getAmount()) {
            price.add(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "" + originalPurchasibleItem.item.getItem().getAmount() + net.md_5.bungee.api.ChatColor.WHITE + " " + buyWhat.getAmount() + " за");
        }
        else {
            price.add(net.md_5.bungee.api.ChatColor.WHITE + "" + buyWhat.getAmount() + ChatColor.GRAY + " за");
        }

        if (originalPurchasibleItem != null && buyForAmount1 != originalPurchasibleItem.priceAmount1) {
            price.add(net.md_5.bungee.api.ChatColor.WHITE + icon + " *" + buyForAmount1 + " " + ChatColor.GRAY + " " + ChatColor.STRIKETHROUGH + "" + originalPurchasibleItem.priceAmount1);
        }
        else {
            price.add(net.md_5.bungee.api.ChatColor.WHITE + icon + " *" + buyForAmount1);
        }




        if (this.priceType2 != null) {
            ItemStack buyFor2 = this.priceType2.getStack();
            int buyForAmount2 = this.priceAmount2;

            switch (net.md_5.bungee.api.ChatColor.stripColor(buyFor2.getItemMeta().getDisplayName())) {
                case "Медь": {
                    icon = i18nonly("resource_bronze_icon");
                    break;
                }
                case "Железо": {
                    icon = i18nonly("resource_iron_icon");
                    break;
                }
                case "Золото": {
                    icon = i18nonly("resource_gold_icon");
                    break;
                }
                case "Изумруд": {
                    icon = i18nonly("resource_emerald_icon");
                    break;
                }
                default: {
                    icon = "?";
                    break;
                }
            }
            if (originalPurchasibleItem != null && buyForAmount2 != originalPurchasibleItem.priceAmount2) {
                price.add(net.md_5.bungee.api.ChatColor.WHITE + icon + " *" + buyForAmount2 + " " + ChatColor.GRAY + " " + ChatColor.STRIKETHROUGH + "" + originalPurchasibleItem.priceAmount2);
            } else {
                price.add(ChatColor.WHITE + icon + " *" + buyForAmount2);
            }
        }

        return price;
    }

    public void changeDeal(int output,int newPrice1, int newPrice2) {

        if ( originalPurchasibleItem == null ) originalPurchasibleItem = this.copy();

        if (output > 0) this.item.getItem().setAmount(output);

        if (this.priceType1 != null && newPrice1 > 0 ) priceAmount1 = newPrice1;

        if (this.priceType2 != null && newPrice2 > 0 ) priceAmount2 = newPrice2;

    }

//    public PurchasableItem copy() {
//        PurchasableItem item = new PurchasableItem(this.id, this.item, )
//    }
}
