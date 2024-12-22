package org.screamingsandals.bedwars.utils.flowergun.mechanics;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.managers.ColoursManager;

public class ElytraBlocker extends BukkitRunnable {

    private int timer;

    private Player player;

    private GamePlayer gamePlayer;

    private int allowedDuration;

    public ElytraBlocker(GamePlayer gamePlayer, int allowedDuration) {

        this.gamePlayer = gamePlayer;
        this.player = gamePlayer.player;
        this.allowedDuration = allowedDuration;
        this.timer = 0;

    }

    @Override
    public void run() {


//        Bukkit.getConsoleSender().sendMessage("elytra blocked (blocker):" + gamePlayer.blockElytra);

        if ( !gamePlayer.blockElytra ) {

            if ( !player.isGliding() ) this.cancel();

            String durationString = "";

            for ( int i = 0; i < allowedDuration; i++) {
                if ( i < allowedDuration - timer) durationString += ColoursManager.elytra + "|";
                else durationString += ColoursManager.darkGray + "|";
            }

            player.sendTitle("", durationString, 0, 6, 1);
            player.getLocation().getWorld().spawnParticle(Particle.END_ROD, player.getLocation().add(0, -1, 0), 10, 0.5, 0.5, 0.5, 0.05);
            player.getLocation().getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, player.getLocation(), 5, 1, 1, 1, 0);


            if (timer >= allowedDuration) {
                player.setGliding(false);
                gamePlayer.blockElytra = true;
                player.sendTitle("", net.md_5.bungee.api.ChatColor.GRAY + "...энергия иссякла...", 5, 20, 5);
            }


        }
        else {

//            Location location = player.getLocation().clone();
//            location.add(0, -1, 0);

//            if (location.getBlock().getType() == Material.AIR) {
            if (!player.isOnGround()) {
                player.sendTitle("", net.md_5.bungee.api.ChatColor.RED + "Вернитесь на землю!", 0, 20, 5);
            } else {
                player.sendTitle("", net.md_5.bungee.api.ChatColor.GOLD + "...энергия восполнена...", 0, 20, 5);
                gamePlayer.blockElytra = false;
                this.cancel();
            }

            //flight ends or timer ends and forces the fly to end
        }

        timer++;
    }
}
