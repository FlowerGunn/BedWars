package org.screamingsandals.bedwars.utils.flowergun.mechanics;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;

public class PlayerDeathTridentListener implements Listener {

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent event){

        Player player = event.getEntity();

        ArrayList<Entity> sorroundingEntities = new ArrayList<>(player.getNearbyEntities( 200, 200, 200));

//        Bukkit.getConsoleSender().sendMessage(player.getName() + " has " + sorroundingEntities.size());

        for (Entity entity : sorroundingEntities) {
            if (entity.getType() == EntityType.TRIDENT) {
//                Bukkit.getConsoleSender().sendMessage("Found trident!");

                Trident trident = (Trident) entity;

                if (trident.getShooter() == player) {
//                    Bukkit.getConsoleSender().sendMessage("Found trident of " + player.getName() + "!");
                    entity.remove();
                    player.getInventory().addItem(trident.getItem());
                }

            }
        }



    }

}
