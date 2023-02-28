package org.screamingsandals.bedwars.utils.flowergun.customgui;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CustomItem;

import java.util.ArrayList;


public class CustomGUIButton extends CustomItem {

    private String arg1;
    private String arg2;
    private String arg3;
    private String actionType;
    private int x;
    private int y;

    public CustomGUIButton setCustomModelData(int data){
        ItemMeta im = this.item.getItemMeta();
        im.setCustomModelData(data);
        this.item.setItemMeta(im);
        return this;
    }

    public CustomGUIButton setMaterial( Material material ){
        this.item.setType(material);
        return this;
    }


    public CustomGUIButton addHiddenEnchantment(boolean enchanted) {
        if (!enchanted) return this;
        this.itemMeta.addEnchant(Enchantment.LUCK, 1, false);
        this.hideEnchantments();
        return this;
    }

    public CustomGUIButton setXY(int x, int y ){
        this.x = x;
        this.y = y;
        return this;
    }
    // X starts with 0. from left ===> to right

    // Y starts with 0. from top ||
    //                           \/ to bottom

    public CustomGUIButton setName( String name ){
        this.itemMeta.setDisplayName(name);
        return this;
    }

    public CustomGUIButton setLore( ArrayList<String> lore ){
        this.itemMeta.setLore(lore);
        return this;
    }

    public CustomGUIButton addLore( ArrayList<String> lore ){
        if (this.itemMeta.getLore() != null) {
            ArrayList<String> currentLore = new ArrayList<>(this.itemMeta.getLore());
            currentLore.addAll(lore);
            this.itemMeta.setLore(currentLore);
        } else {
            this.itemMeta.setLore(lore);
        }
        return this;
    }

    public CustomGUIButton setItemstack( ItemStack itemStack ) {
        this.item = itemStack.clone();
        this.itemMeta = itemStack.getItemMeta();
        this.itemData = this.itemMeta.getPersistentDataContainer();
        return this;
    }

    public CustomGUIButton(String actionType) {

        this.item = new ItemStack(Material.BEDROCK);
        this.itemMeta = this.item.getItemMeta();

        this.itemData = this.itemMeta.getPersistentDataContainer();

        this.actionType = actionType;

        this.item.setItemMeta(itemMeta);
    }

    public CustomGUIButton setArgs( String arg ) {
        this.arg1 = arg;
        return this;
    }

    public CustomGUIButton setArgs( String arg1, String arg2 ) {
        this.arg1 = arg1;
        this.arg2 = arg2;
        return this;
    }

    public CustomGUIButton setArgs( String arg1, String arg2, String arg3 ) {
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.arg3 = arg3;
        return this;
    }


    public CustomGUIButton build(){

        this.itemData.set(new NamespacedKey( Main.getInstance(), "GUIActionType"), PersistentDataType.STRING, this.actionType);

        if (arg1 != null)
            itemData.set(new NamespacedKey( Main.getInstance(), "GUIActionArg"), PersistentDataType.STRING, arg1);
        if (arg2 != null)
            itemData.set(new NamespacedKey( Main.getInstance(), "GUIActionArg2"), PersistentDataType.STRING, arg2);
        if (arg3 != null)
            itemData.set(new NamespacedKey( Main.getInstance(), "GUIActionArg3"), PersistentDataType.STRING, arg3);

        super.build();

        return this;
    }

    public Integer getX() {
        return this.x;
    }

    public Integer getY() {
        return this.y;
    }


}
