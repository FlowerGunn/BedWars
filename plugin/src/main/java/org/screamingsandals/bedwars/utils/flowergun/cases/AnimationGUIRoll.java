package org.screamingsandals.bedwars.utils.flowergun.cases;


import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.utils.flowergun.customgui.CustomGUI;
import org.screamingsandals.bedwars.utils.flowergun.customgui.CustomGUIButton;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceChest;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceChestReward;
import org.screamingsandals.bedwars.utils.flowergun.managers.ColoursManager;
import org.screamingsandals.bedwars.utils.flowergun.managers.RarityManager;
import org.screamingsandals.bedwars.utils.flowergun.managers.ResourceManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class AnimationGUIRoll extends BukkitRunnable {

    private int ticksDuration;
    private int ticksDelay;
    private int maxTicksDelay;
    private int ticksDelayCounter;
    private int ticksCounter;
    private Player player;
    private ResourceChest resourceChest;
    private Inventory gui;
    private Sound sound;

    private int finalRollCounter;
    private String guiName;

    private ArrayList<ResourceChestReward> resourceChestRewards;
    private ArrayList<ResourceChestReward> resourceChestRewardsQueue;
    private ArrayList<ItemStack> itemStackQueue;
    private ArrayList<Integer> categoriesWeights;
    private ArrayList<Integer> rewardCategories;

    private int totalWeight;

    public AnimationGUIRoll(Player player, int ticksDuration, int ticksDelay, int maxTicksDelay, ResourceChest resourceChest, Sound sound, String guiName) {
        this.player = player;
        this.ticksDuration = ticksDuration;
        this.ticksDelay = ticksDelay;
        this.maxTicksDelay = maxTicksDelay;
        this.resourceChest = resourceChest;
        this.sound = sound;
        this.ticksCounter = 0;
        this.ticksDelayCounter = ticksDelay;
        this.guiName = guiName;

        this.resourceChestRewards = new ArrayList<>(this.resourceChest.getChestRewards());
        this.categoriesWeights = new ArrayList<>(this.resourceChest.getCategoriesWeights());
        this.rewardCategories = new ArrayList<>(this.resourceChest.getRewardCategories());

        this.resourceChestRewardsQueue = new ArrayList<>();
        this.itemStackQueue = new ArrayList<>();

        this.totalWeight = 0;

        int i;
        for (i = 0; i < categoriesWeights.size(); i++) {
            totalWeight += categoriesWeights.get(i);
        }

        for ( int j = 0; j < 9; j++ ) {
            this.resourceChestRewardsQueue.add(this.rollSingleReward());
            this.itemStackQueue.add(this.resourceChestRewardsQueue.get(j).getGuiItem());
        }

//        Bukkit.getConsoleSender().sendMessage("total weight = " + totalWeight);

        this.gui = player.getOpenInventory().getTopInventory();

    }

    private Integer rollSingleCategory() {
        Random random = new Random();
        int roll = random.nextInt(this.totalWeight) + 1;
        int i;
        int ongoingWeight = 0;

        for (i = 0; i < categoriesWeights.size(); i++) {
            ongoingWeight += categoriesWeights.get(i);
            if ( ongoingWeight >= roll ) return i;
        }
        return 0;
    }

    private ResourceChestReward rollSingleReward() {
        Random random = new Random();
        int categoryRoll = rollSingleCategory();
//        Bukkit.getConsoleSender().sendMessage("categoryRoll = " + categoryRoll);

        ArrayList<ResourceChestReward> rolledCategoryRewards = new ArrayList<>();

        for ( int i = 0; i < resourceChestRewards.size(); i++ )
        {
            if (rewardCategories.get(i) == categoryRoll) {
                rolledCategoryRewards.add(resourceChestRewards.get(i));
//                Bukkit.getConsoleSender().sendMessage("receiving " + resourceChestRewards.get(i).getName());
            }
        }
//        Bukkit.getConsoleSender().sendMessage("rolledCategoryRewards size = " + rolledCategoryRewards.size());
        return rolledCategoryRewards.get(random.nextInt(rolledCategoryRewards.size()));
    }

    @Override
    public void run() {

        if ( ticksDelayCounter == 0 ) {

            gui = player.getOpenInventory().getTopInventory();
            if ( player.getOpenInventory().getType() != InventoryType.CHEST) {

                while ( ticksCounter <= ticksDuration ) {
                    if ( ticksDelayCounter == 0 ) {
                        this.move(false);
                    }
                    ticksDelayCounter--;
                    ticksCounter++;
                }

                this.finish(false);

                return;
            }

            this.move(true);

            if ( ticksCounter > ticksDuration ) {
                this.finish(true);
            }

        }
        //BlockParty.getInstance().getPlugin().getLogger().info("ticksCounter " + ticksCounter + " > " + ticksDuration + " ticks duration");


        ticksDelayCounter--;
        ticksCounter++;
    }

    private void finish(boolean doVisuals) {
        this.cancel();

        ResourceChestReward rolledReward = resourceChestRewardsQueue.get(4);

        ResourceManager.giveResourcesTo( player.getUniqueId(), resourceChest.getResourcePrice().getType(), resourceChest.getResourcePrice().getAmount() * -1, false );

        rolledReward.giveRewardTo(player);

        int rarity = rolledReward.getRarity();

        if (rarity <= 2) player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN , 3.0F, 1.0F);
        if (rarity == 3) player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP , 1.0F, 1.0F);
        if (rarity == 4) player.getWorld().playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER , 1.0F, 1.0F);
        if (rarity == 5) player.getWorld().playSound(player.getLocation(), Sound.ITEM_TOTEM_USE , 0.4F, 1.0F);
        if (rarity == 6) player.getWorld().playSound(player.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN , 3.0F, 1.0F);
        if (rarity == 7) player.getWorld().playSound(player.getLocation(), Sound.BLOCK_PORTAL_TRAVEL , 0.2F, 1.0F);


        if (doVisuals) {
            ItemStack middleItem = itemStackQueue.get(4).clone();
            ItemMeta itemMeta = middleItem.getItemMeta();
            itemMeta.addEnchant(Enchantment.LUCK, 1, false);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            middleItem.setItemMeta(itemMeta);
            gui.setItem(9 + 4, middleItem);
            CustomGUIButton button = new CustomGUIButton("GOGUI").setArgs(guiName).setXY(4, 3).setItemstack(CustomGUI.getBackItem()).setName(ColoursManager.red + "Назад").build();
            gui.setItem(4 + 3 * 9, button.getItem());
        }
    }


    private void move( boolean doVisuals ) {
        ResourceChestReward newReward = rollSingleReward();

        //gui.setItem( slot, null);

        ItemStack item = newReward.getGuiItem();

        this.resourceChestRewardsQueue.remove(0);
        this.resourceChestRewardsQueue.add(newReward);
        this.itemStackQueue.remove(0);
        this.itemStackQueue.add(item);


//            CosmeticsInfo middleCosmetic = cosmeticsInfosQueue.get(4);

        if (doVisuals) {

            for (int i = 0; i < 9; i++) {
                gui.setItem(9 + i, itemStackQueue.get(i));
                gui.setItem(i, RarityManager.rarityGlassPanes.get(this.resourceChestRewardsQueue.get(i).getRarity()));
                gui.setItem(18 + i, RarityManager.rarityGlassPanes.get(this.resourceChestRewardsQueue.get(i).getRarity()));
            }
            CustomGUIButton hopperbutton = new CustomGUIButton("BLOCKER").setArgs("").setXY(0, 0).setMaterial(Material.HOPPER).setName(ColoursManager.darkGray + "\\/").build();
            gui.setItem(4, hopperbutton.getItem());

            player.playSound(player.getLocation(), sound, 3F, 1F);

        }

        if ( ticksDelay < maxTicksDelay ) ticksDelay++;

        ticksDelayCounter = ticksDelay;
    }


}