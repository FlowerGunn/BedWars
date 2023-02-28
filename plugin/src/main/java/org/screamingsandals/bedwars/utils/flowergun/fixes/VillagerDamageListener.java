package org.screamingsandals.bedwars.utils.flowergun.fixes;

import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.api.game.Game;
import org.screamingsandals.bedwars.api.game.GameStatus;
import org.screamingsandals.bedwars.game.GamePlayer;

public class VillagerDamageListener implements Listener {

    @EventHandler (priority = EventPriority.LOWEST)
    public void onShopDamage(EntityDamageEvent event) {

        if (event.getEntity() instanceof Villager) {
            event.setCancelled(true);
        }

    }

}
