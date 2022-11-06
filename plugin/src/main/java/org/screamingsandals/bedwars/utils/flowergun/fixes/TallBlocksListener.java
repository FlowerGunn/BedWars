package org.screamingsandals.bedwars.utils.flowergun.fixes;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.api.game.GameStatus;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GameCreator;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;

public class TallBlocksListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {

        Block block = event.getBlock();

//        Bukkit.getConsoleSender().sendMessage(event.getBlock().getType() + "   " + event.getSource().getType() );

        if ( FlowerUtils.doubleBlocks.contains(event.getBlockPlaced().getType()) ) {
            for (String s : Main.getGameNames()) {
                Game game = Main.getGame(s);

                if (game.getStatus() == GameStatus.RUNNING || game.getStatus() == GameStatus.GAME_END_CELEBRATING) {
                    if (GameCreator.isInArea(block.getLocation(), game.getPos1(), game.getPos2())) {
                        if (GameCreator.isInArea(block.getLocation().clone().add(0, 1, 0), game.getPos1(), game.getPos2())) {
                            game.getRegion().addBuiltDuringGame(block.getLocation().clone().add(0, 1, 0));
                        }
                    }
                }

            }
        }

    }

}
