package org.screamingsandals.bedwars.utils.flowergun.mechanics;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;

public class ShieldListener implements Listener {

    @EventHandler
    public void onShieldHit(EntityDamageByEntityEvent event) {

//        Bukkit.getConsoleSender().sendMessage("event triggered");

        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player damager = (Player) event.getDamager();
            Player victim = (Player) event.getEntity();


//            Bukkit.getConsoleSender().sendMessage("both are players");
//            Bukkit.getConsoleSender().sendMessage(damager.getInventory().getItemInMainHand().getType() + "");

            if ( FlowerUtils.swords.contains(damager.getInventory().getItemInMainHand().getType()) ) {

//                Bukkit.getConsoleSender().sendMessage("there is a sword");
                if ( victim.isBlocking() ) {

//                    Bukkit.getConsoleSender().sendMessage("victim blocking");
                    victim.setCooldown(Material.SHIELD, FlowerUtils.swordOnShieldCooldown);
                    victim.getWorld().playSound(victim.getLocation(), Sound.BLOCK_LANTERN_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);

//                    Bukkit.getScheduler().runTaskAsynchronously( Main.getInstance(), () -> {
//
//                        boolean isMainHand = false;
//                        boolean isOffHand = false;
//                        PlayerInventory inventory = victim.getInventory();
//                        ItemStack shield;
//                        if (inventory.getItemInMainHand().getType() == Material.SHIELD) isMainHand = false;
//                        if (inventory.getItemInOffHand().getType() == Material.SHIELD) isOffHand = false;
//                        if (isMainHand) {
//                            shield = inventory.getItemInMainHand();
//                            int slot = inventory.getHeldItemSlot();
//                            shield.setAmount(0);
//                            inventory.setItem(slot, shield);
//                            try {
//                                wait(100);
//                            } catch (InterruptedException e) {
//                                throw new RuntimeException(e);
//                            }
//                            shield.setAmount(1);
//                            inventory.setItem(slot, shield);
//
//                        } else if (isOffHand) {
//                            shield = inventory.getItemInOffHand();
//                            shield.setAmount(0);
//                            inventory.setItem(EquipmentSlot.OFF_HAND, shield);
//                            try {
//                                wait(100);
//                            } catch (InterruptedException e) {
//                                throw new RuntimeException(e);
//                            }
//                            shield.setAmount(1);
//                            inventory.setItem(EquipmentSlot.OFF_HAND, shield);
//                        }
//
//                    });

                }
            }

        }
    }

}
