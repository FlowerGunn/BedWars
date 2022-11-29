package org.screamingsandals.bedwars.utils.flowergun.customobjects.gadgets;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.api.APIUtils;
import org.screamingsandals.bedwars.api.game.GameStatus;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CustomBlock;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CustomItem;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.GadgetType;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.Triggers;

public class TrampolineListener implements Listener {

    public static final String TRAMPOLINE_PREFIX = "Module:Trampoline";

    @EventHandler
    public void onTrampolineUse(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (!Main.isPlayerInGame(player)) {
            return;
        }

        GamePlayer gPlayer = Main.getPlayerGameProfile(player);
        org.screamingsandals.bedwars.game.Game game = gPlayer.getGame();

        if (event.isSneaking()) {
//            Bukkit.getConsoleSender().sendMessage("sneaking cancelled");
            return;
        }

        if (game.getStatus() == GameStatus.RUNNING && !gPlayer.isSpectator) {

//            Bukkit.getConsoleSender().sendMessage("block clicked");

            Block block = player.getWorld().getBlockAt( player.getLocation().add(0 , -0.5, 0));

            CustomBlock customBlock = game.getCustomBlock(block);

            if (customBlock == null) {
//                Bukkit.getConsoleSender().sendMessage("block not found");
                return;
            }

            Bukkit.getConsoleSender().sendMessage(customBlock.getGadgetType().toString());

            if (customBlock.getGadgetType() == GadgetType.TRAMPOLINE) {

                Material doubleCheck = customBlock.getItem().getType();

                if (block.getType() != doubleCheck) {
                    game.tryRemoveCustomBlock(block);
                    Bukkit.getConsoleSender().sendMessage("tramp is wrong material");
                    return;
                }

//                World world = game.getWorld();
                Triggers.gadgetUsed(player, GadgetType.TRAMPOLINE, customBlock);
            }

//                MiscUtils.sendActionBarMessage(player, i18nonly("special_item_delay").replace("%time%", String.valueOf(delay)));
        }
    }

    @EventHandler
    public void onTrampolinePlace(BlockPlaceEvent event) {

        ItemStack trampolineItem = event.getItemInHand();
        Block block = event.getBlock();

        String unhidden = APIUtils.unhashFromInvisibleStringStartsWith(trampolineItem, TRAMPOLINE_PREFIX);
        if (unhidden != null) {

            Player player = event.getPlayer();
            GamePlayer gPlayer = Main.getPlayerGameProfile(player);
            org.screamingsandals.bedwars.game.Game game = gPlayer.getGame();

//            Bukkit.getConsoleSender().sendMessage("tramp placed");

            CustomBlock customBlock = new CustomBlock(new CustomItem().setItemstack(trampolineItem).setAmount(1).setGadgetType(GadgetType.TRAMPOLINE), block);

            game.addCustomBlock( customBlock );


        }

    }

    @EventHandler
    public void onTrampolineBreak(BlockBreakEvent event) {

        //ItemStack trampolineItem = event.getItemInHand();
        Block block = event.getBlock();
        Player player = event.getPlayer();
        GamePlayer gPlayer = Main.getPlayerGameProfile(player);
        org.screamingsandals.bedwars.game.Game game = gPlayer.getGame();

        if (game == null) return;

//        Bukkit.getConsoleSender().sendMessage("passing block type " + block.getType() + " into getCustomBlock");

        CustomBlock customBlock = game.getCustomBlock(block);

        if (customBlock == null) return;

        if (game.getCustomBlock(block).getGadgetType() == GadgetType.TRAMPOLINE) {
            if (game.tryRemoveCustomBlock(block)) {
                if ( block.getType() == customBlock.getOriginalType() ) {
                    game.getWorld().dropItem(block.getLocation(), customBlock.getItem());
//                    Bukkit.getConsoleSender().sendMessage("tramp broken and dropped");
                    event.setDropItems(false);
                }
            }
            else Bukkit.getConsoleSender().sendMessage("removing tramp failed");
        }

    }


}
