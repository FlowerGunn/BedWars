package org.screamingsandals.bedwars.utils.flowergun.shoputils;

import lombok.Getter;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CustomItem;

import java.util.ArrayList;

public class ShopCategory {

    public ArrayList<PurchasableItem> items;

    public CustomItem visualItem;

//    private int priority;
//    private boolean enabled;

    @Getter
    private String id;

    public ShopCategory(String id, CustomItem customItem) {

        this.id = id;
        this.items = new ArrayList<>();

        this.visualItem = customItem;

    }

    public ShopCategory build(){
        return this;
    }


    public ShopCategory copy() {
        ShopCategory shopCategory = new ShopCategory(id, visualItem);
        for ( PurchasableItem item: this.items ) {
            shopCategory.items.add(item.copy());
        }
        return shopCategory;
    }
}
