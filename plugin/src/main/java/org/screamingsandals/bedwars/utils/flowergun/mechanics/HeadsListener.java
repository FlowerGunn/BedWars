package org.screamingsandals.bedwars.utils.flowergun.mechanics;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.CurrentTeam;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;

import java.util.Random;

import static org.screamingsandals.bedwars.lib.lang.I.i18n;
import static org.screamingsandals.bedwars.lib.lang.I.m;

public class HeadsListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity();


        if (Main.isPlayerInGame(player)) {

            GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
            CurrentTeam team = gamePlayer.getGame().getPlayerTeam(gamePlayer);

            Entity entityKiller = player.getKiller();
//            Bukkit.getConsoleSender().sendMessage("cause " + player.getLastDamageCause().getCause() + " " + (entityKiller instanceof Player));
            if ( player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK && entityKiller instanceof Player) {
                Player killer = (Player) entityKiller;

                ItemStack weapon = killer.getInventory().getItemInMainHand();

                Random random = new Random();
                int a = random.nextInt(100);
//                Bukkit.getConsoleSender().sendMessage("a = " + a);
//                Bukkit.getConsoleSender().sendMessage("axes = " + FlowerUtils.axesChance);
//                Bukkit.getConsoleSender().sendMessage("swords = " + FlowerUtils.swords);
                if ((FlowerUtils.axes.contains(weapon.getType()) && a <= FlowerUtils.axesChance) || (FlowerUtils.swords.contains(weapon.getType()) && a <= FlowerUtils.swordsChance))
                {
//                    Bukkit.getConsoleSender().sendMessage("dropping head");
                    ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
                    SkullMeta meta = (SkullMeta) skull.getItemMeta();
                    meta.setOwningPlayer(player);
                    meta.setDisplayName(i18n("skull_name", "Player Skull", false).replace("%name%", player.getName()).replace("%teamcolor%", team.teamInfo.color.chatColor + ""));
                    skull.setItemMeta(meta);
                    player.getWorld().dropItem(player.getLocation(), skull);
                }
            }

        }
    }

}
