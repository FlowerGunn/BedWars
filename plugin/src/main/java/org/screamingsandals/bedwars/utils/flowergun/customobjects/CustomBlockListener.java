package org.screamingsandals.bedwars.utils.flowergun.customobjects;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.api.game.Game;
import org.screamingsandals.bedwars.game.GameCreator;

import java.util.ArrayList;
import java.util.List;

public class CustomBlockListener implements Listener {

    @EventHandler
    public void onCustomBlockExplode(BlockExplodeEvent event) {

        Block sourceBlock = event.getBlock();

        List<Block> blocks = new ArrayList<>(event.blockList());

        Bukkit.getConsoleSender().sendMessage("block explode event");

        for (Game game : Main.getInstance().getGames()) {

            if (GameCreator.isInArea( sourceBlock.getLocation(), game.getPos1(), game.getPos2() ))
            {
                Bukkit.getConsoleSender().sendMessage("found a game in bounds");
                for ( Block block : blocks ) {
                    if (((org.screamingsandals.bedwars.game.Game) game).tryRemoveCustomBlock(block))
                    {
                        Bukkit.getConsoleSender().sendMessage("removed a custom block");
                        event.blockList().remove(block);
                        block.setType(Material.AIR);
                    }
                }
            }


        }

    }

}
