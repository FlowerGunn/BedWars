package org.screamingsandals.bedwars.utils.flowergun.fixes;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.api.game.GameStatus;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GameCreator;

public class FireListener implements Listener {

    @EventHandler
    public void onFireSpread(BlockSpreadEvent event) {

        Block block = event.getBlock();


//        Bukkit.getConsoleSender().sendMessage(event.getBlock().getType() + "   " + event.getSource().getType() );

        if ( event.getSource().getType() == Material.FIRE ) {
            for (String s : Main.getGameNames()) {
                Game game = Main.getGame(s);

                if (game.getStatus() == GameStatus.RUNNING || game.getStatus() == GameStatus.GAME_END_CELEBRATING) {
                    if (GameCreator.isInArea(block.getLocation(), game.getPos1(), game.getPos2())) {
//                        Bukkit.getConsoleSender().sendMessage("new state location -> " + event.getNewState().getBlock().getLocation());
//                        Bukkit.getConsoleSender().sendMessage("source location -> " + event.getSource().getLocation());
//                        Bukkit.getConsoleSender().sendMessage("block location -> " + block.getLocation());
//                        Bukkit.getConsoleSender().sendMessage("=========================");
                        game.getRegion().addBuiltDuringGame(block.getLocation());
                    }
                }

            }
        }

    }

    @EventHandler
    public void onBlockBurn(BlockBurnEvent event) {
        Block source = event.getIgnitingBlock();

        Block target = event.getBlock();

        for (String s : Main.getGameNames()) {
            Game game = Main.getGame(s);

            if (game.getStatus() == GameStatus.RUNNING || game.getStatus() == GameStatus.GAME_END_CELEBRATING) {
                if (GameCreator.isInArea(source.getLocation(), game.getPos1(), game.getPos2())) {
//                    Bukkit.getConsoleSender().sendMessage("block burned");
                    game.getRegion().addBuiltDuringGame(source.getLocation());

//                    if ( !game.getRegion().isBlockAddedDuringGame( target.getLocation() ) && game.getRegion().isBlockAddedDuringGame( source.getLocation() ) ) {
//                        source.setType(Material.AIR);
//                    }

                    if ( !game.getRegion().isBlockAddedDuringGame( target.getLocation()) ) {
                        World world = target.getWorld();
                        int radius = 1;
                        for (int i = radius * -1; i <= radius; i++ ) {
                            for (int j = radius * -1; j <= radius; j++ ) {
                                for (int k = radius * -1; k <= radius; k++ ) {
                                    Location location = target.getLocation().clone().add(i,j,k);
                                    Block block = world.getBlockAt(location);
                                    if ( block.getType() != Material.FIRE ) continue;
                                    if ( game.getRegion().isBlockAddedDuringGame( location ) ) {
                                        block.setType(Material.AIR);
                                    }
                                }
                            }
                        }
                    }

                }
            }

        }
    }

}
