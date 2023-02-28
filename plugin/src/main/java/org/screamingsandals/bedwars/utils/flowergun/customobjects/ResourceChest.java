package org.screamingsandals.bedwars.utils.flowergun.customobjects;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.screamingsandals.bedwars.utils.flowergun.managers.ColoursManager;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

public class ResourceChest {

    @Getter
    @Setter
    private final Resource resourcePrice;
    @Getter
    @Setter
    protected int coinsPrice;

    @Getter
    @Setter
    protected int gemsPrice;

    @Getter
    @Setter
    protected String id;

    @Getter
    @Setter
    protected String name;

    @Getter
    @Setter
    protected String iaId;

    @Getter
    @Setter
    protected ArrayList<String> description;

    @Getter
    @Setter
    protected Timestamp visibleFrom;

    @Getter
    @Setter
    protected Timestamp visibleTo;

    @Getter
    @Setter
    protected ArrayList<ResourceChestReward> chestRewards;

    @Getter
    @Setter
    protected ArrayList<Integer> rewardCategories;

    @Getter
    @Setter
    protected ArrayList<Integer> rewardWeights;

    @Getter
    @Setter
    protected Material material;

    @Getter
    @Setter
    protected int customModelData;

    @Getter
    @Setter
    protected int isInfinite;

    @Getter
    @Setter
    protected ArrayList<String> categories;

    @Getter
    @Setter
    protected ArrayList<Integer> categoriesWeights;


    public ResourceChest(String id , String name , String description, Resource resourcePrice, Timestamp visibleFrom, Timestamp visibleTo, Material material, String iaId, int isInfinite ){

        this.name = name;
        this.id = id;

        String str[] = description.split("/");
        this.description = new ArrayList<>(Arrays.asList(str));
        this.description.replaceAll(s -> ColoursManager.gray + s);

//        for (int i = 0; i < this.description.size(); i++ ) {
//            this.description.set(i, ColoursManager.gray + this.description.get(i));
//        }

        this.isInfinite = isInfinite;

        if (isInfinite == 0) {
            this.description.add("");
            this.description.add(ChatColor.GRAY + "Сундук активен с:");
            this.description.add(ChatColor.GOLD + visibleFrom.toString() + " по");
            this.description.add(ChatColor.GOLD + visibleTo.toString());
        }
        this.chestRewards = new ArrayList<>();
        this.rewardWeights = new ArrayList<>();
        this.categories = new ArrayList<>();
        this.categoriesWeights = new ArrayList<>();
        this.rewardCategories = new ArrayList<>();

        this.resourcePrice = resourcePrice;


        this.visibleFrom = visibleFrom;
        this.visibleTo = visibleTo;

        this.material = material;
        this.iaId = iaId;

    }


    public void addReward( ResourceChestReward resourceChestReward, int category ) {
        this.chestRewards.add(resourceChestReward);
        this.rewardCategories.add(category);
    }

    public void addCategory( String name, int weight ) {
        this.categories.add(name);
        this.categoriesWeights.add(weight);
    }

}
