package org.screamingsandals.bedwars.utils.flowergun.fixes;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.api.game.Game;
import org.screamingsandals.bedwars.api.game.GameStatus;
import org.screamingsandals.bedwars.game.GamePlayer;

public class EndgameDamageListener implements Listener {

    @EventHandler (priority = EventPriority.LOWEST)
    public void onPlayerDamage(EntityDamageEvent event) {

        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (Main.isPlayerInGame(player)) {
                GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
                Game game = gamePlayer.getGame();

                if ( game.getStatus() == GameStatus.GAME_END_CELEBRATING ) {
                    event.setCancelled(true);
                }

            }
        }

    }

}
