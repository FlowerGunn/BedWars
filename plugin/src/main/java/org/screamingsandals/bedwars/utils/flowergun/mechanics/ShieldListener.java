package org.screamingsandals.bedwars.utils.flowergun.mechanics;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;
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
                if ( victim.isBlocking() && !damager.hasPotionEffect(PotionEffectType.BLINDNESS) && !damager.isSprinting() && damager.getFallDistance() > 0 && !damager.isOnGround() && FlowerUtils.isPlayersWeaponFullyCharged(damager) ) {

//                    Bukkit.getConsoleSender().sendMessage("victim blocking");
//                    victim.setCooldown(Material.SHIELD, FlowerUtils.swordOnShieldCooldown);
                    victim.getWorld().playSound(victim.getLocation(), Sound.BLOCK_LANTERN_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);


                    new BukkitRunnable() {
                        int i = 1;

                        boolean isMainHand = false;
                        boolean isOffHand = false;

                        PlayerInventory inventory = victim.getInventory();
                        ItemStack shield;
                        int slot;
                        @Override
                        public void run() {
                            if (i > 0) {

                                if (inventory.getItemInMainHand().getType() == Material.SHIELD) isMainHand = true;
                                if (inventory.getItemInOffHand().getType() == Material.SHIELD) isOffHand = true;
                                if (isMainHand) {
                                    shield = inventory.getItemInMainHand();
                                    slot = inventory.getHeldItemSlot();
                                    inventory.setItem(slot, null);
                                } else if (isOffHand) {
                                    shield = inventory.getItemInOffHand();
                                    inventory.setItem(EquipmentSlot.OFF_HAND, null);
                                }

                                victim.setCooldown(Material.SHIELD, FlowerUtils.swordOnShieldCooldown);

                                i--;
                            }
                            else {
                                if (isMainHand) {
                                    shield.setAmount(1);
                                    inventory.setItem(slot, shield);
                                } else if (isOffHand) {
                                    shield.setAmount(1);
                                    inventory.setItem(EquipmentSlot.OFF_HAND, shield);
                                }
                                this.cancel();
                            }
                        }
                    }.runTaskTimer(Main.getInstance(), 0L, 1L);


                }
            }

        }
    }

}
