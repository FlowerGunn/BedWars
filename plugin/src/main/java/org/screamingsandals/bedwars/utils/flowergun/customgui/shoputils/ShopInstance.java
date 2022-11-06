package org.screamingsandals.bedwars.utils.flowergun.customgui.shoputils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.CurrentTeam;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.LoadedAbility;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.Triggers;

import java.util.ArrayList;

public class ShopInstance extends Shop {

    private Player player;
    private GamePlayer gamePlayer;
    private CurrentTeam team;
    private Game game;

    public ShopInstance(Player player, Shop shop) {
        super(shop);

        this.player = player;
        this.gamePlayer = Main.getPlayerGameProfile(player);
        this.game = this.gamePlayer.getGame();
        this.team = this.game.getPlayerTeam(this.gamePlayer);

        ArrayList<GameFlag> flags = new ArrayList<>(gamePlayer.getAllPlayerFlags());

        Bukkit.getConsoleSender().sendMessage("shop instance generated with flags " + flags.size() + " " + flags);

        for ( int i = 0; i < this.categories.size(); i++ ) {
            ShopCategory category = this.categories.get(i);
            for ( int j = 0; j < category.items.size(); j++ ) {
                PurchasableItem item = category.items.get(j);

                if ( flags.contains(item.getInclusionFlag()) ) {
                    item.setVisibility(true);
//                    Bukkit.getConsoleSender().sendMessage("visibility turned on");
                }
                if ( flags.contains(item.getExclusionFlag()) ) {
                    item.setVisibility(false);
//                    Bukkit.getConsoleSender().sendMessage("visibility turned off");
                }


                Triggers.processPurchasibleItem(gamePlayer, item);

                if ( !item.visible ) {
                    j--;
                    category.items.remove(item);
//                    Bukkit.getConsoleSender().sendMessage("item removed");
                }
            }
        }


    }

}
