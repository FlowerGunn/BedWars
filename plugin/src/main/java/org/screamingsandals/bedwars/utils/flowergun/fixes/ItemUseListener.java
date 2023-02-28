package org.screamingsandals.bedwars.utils.flowergun.fixes;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.api.game.GameStatus;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.customgui.CustomGUI;

public class ItemUseListener implements Listener {

    @EventHandler (priority = EventPriority.LOWEST)
    public void onInteract(PlayerInteractEvent event) {

//        Bukkit.getConsoleSender().sendMessage("event triggered");

        Player player = event.getPlayer();
        GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
        Game game = gamePlayer.getGame();
        if (Main.isPlayerInGame(player))

            if (event.getMaterial() == Material
                    .valueOf(Main.getConfigurator().config.getString("items.jointeam", "COMPASS"))) {
                if (game.getStatus() == GameStatus.WAITING) {
                    // DONE WAYPOINT Team selection
                    //game.openTeamSelectorInventory(player);
                    CustomGUI customGUI = new CustomGUI(player, "TEAM_SELECTION");
                    customGUI.load();
                    event.setCancelled(true);
                }

            }
    }

}
