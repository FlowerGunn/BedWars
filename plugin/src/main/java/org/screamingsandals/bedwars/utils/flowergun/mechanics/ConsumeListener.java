package org.screamingsandals.bedwars.utils.flowergun.mechanics;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.customgui.shoputils.GameFlag;

public class ConsumeListener implements Listener {

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {

        Player player = event.getPlayer();
        GamePlayer gamePlayer = Main.getPlayerGameProfile(player);

        if ( gamePlayer.hasFlag(GameFlag.INTELLECT_LEVEL_3) )
            if ( FlowerUtils.consumableItems.contains(event.getItem().getType()) ) {

                Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(Main.getInstance(), () -> {
                    for (ItemStack itemStack: player.getInventory()) {
                        if (itemStack == null) continue;
                        if (itemStack.getType() == Material.GLASS_BOTTLE) {
                            itemStack.setAmount( itemStack.getAmount() - 1 );
    //                        Bukkit.getConsoleSender().sendMessage("removed a bottle");
                            break;
                        }
                    }
    //                Bukkit.getConsoleSender().sendMessage("didnt find a bottle");
                }, 1L);

                if ( event.getItem().getAmount() == 1 )
                for (int i = 9; i < player.getInventory().getSize(); i++ ) {

                    ItemStack itemStack = player.getInventory().getItem(i);
                    if (itemStack == null) continue;

    //                Bukkit.getConsoleSender().sendMessage("same item data? " + itemStack.equals(event.getItem()));
    //                Bukkit.getConsoleSender().sendMessage("same item? " + (itemStack == player.getInventory().getItemInMainHand()));
                    ItemStack clone = itemStack.clone();
                    clone.setAmount(1);
                    if (clone.equals(event.getItem()))
                    {
                        event.setReplacement(itemStack.clone());
                        itemStack.setAmount(0);
    //                    Bukkit.getConsoleSender().sendMessage("do replacement");
                        break;
                    }
                }
            }
        if ( gamePlayer.hasFlag(GameFlag.INTELLECT_LEVEL_3) )
            if ( event.getItem().getType() == Material.HONEY_BOTTLE ) {
                player.removePotionEffect(PotionEffectType.WITHER);
                player.removePotionEffect(PotionEffectType.GLOWING);
                player.removePotionEffect(PotionEffectType.SLOW);
                player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
                player.removePotionEffect(PotionEffectType.POISON);
                player.removePotionEffect(PotionEffectType.HUNGER);
                player.removePotionEffect(PotionEffectType.BLINDNESS);
                player.removePotionEffect(PotionEffectType.WEAKNESS);
            }

    }

}
