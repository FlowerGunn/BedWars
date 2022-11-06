package org.screamingsandals.bedwars.utils.flowergun.fixes;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockSpreadEvent;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.api.game.GameStatus;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GameCreator;

public class VineListener implements Listener {

    @EventHandler
    public void onVineSpread(BlockSpreadEvent event) {

        Block block = event.getBlock();


        if ( event.getSource().getType() == Material.VINE ) {
            for (String s : Main.getGameNames()) {
                Game game = Main.getGame(s);

                if (game.getStatus() == GameStatus.RUNNING || game.getStatus() == GameStatus.GAME_END_CELEBRATING) {
                    if (GameCreator.isInArea(block.getLocation(), game.getPos1(), game.getPos2())) {
                        game.getRegion().addBuiltDuringGame(block.getLocation());
                    }
                }

            }
        }

    }

}
