package org.screamingsandals.bedwars.utils.flowergun.mechanics;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.MiscUtils;

public class ElytraListener implements Listener {

    @EventHandler
    public void onGlidingStart(EntityToggleGlideEvent event) {

        Player player = (Player) event.getEntity();

        Location location = player.getLocation().clone();
        location.add(0, -1, 0);

        if (Main.isPlayerInGame(player)) {

            GamePlayer gamePlayer = Main.getPlayerGameProfile(player);

//            Bukkit.getConsoleSender().sendMessage("elytra blocked:" + gamePlayer.blockElytra);

            if (!event.isGliding()) {
//                Bukkit.getConsoleSender().sendMessage("player stopped the fly");
                if (location.getBlock().getType() == Material.AIR) {
                    gamePlayer.blockElytra = true;
                    Bukkit.getConsoleSender().sendMessage("AIR STOP!");
                }
            }
            else {
                if (gamePlayer.blockElytra) {



                    if (location.getBlock().getType() != Material.AIR) {
                        gamePlayer.blockElytra = false;
//                        Bukkit.getConsoleSender().sendMessage("bug prevented. elytra unlocked");
                    }

//                    Bukkit.getConsoleSender().sendMessage("elytra blocked");
                    event.setCancelled(true);
                    MiscUtils.sendActionBarMessage(player, ChatColor.RED + "Элитры заблокированы!");
                } else {
//                    Bukkit.getConsoleSender().sendMessage("blocker started");
                    ElytraBlocker elytraBlocker = new ElytraBlocker(gamePlayer, 12);
                    elytraBlocker.runTaskTimerAsynchronously(Main.getInstance(), 0L, 5L);
                }
            }

//        boolean startGliding = event.isGliding(); //false - starting to glide; true - ending the glide???



        }

    }

}
