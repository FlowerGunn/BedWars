package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;


import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

public class Enderman extends Ability implements IAbility{


    private boolean used;

    public Enderman(){
        super();
        //legendary
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.ECHO_SHARD, 75).addResource(ResourceType.ECHO_DUST, 20).addResource(ResourceType.MICROSCHEMA, 5).addResource(ResourceType.CATALYST_LEGENDARY, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.ECHO_SHARD, 150).addResource(ResourceType.ECHO_DUST, 50).addResource(ResourceType.MICROSCHEMA, 10).addResource(ResourceType.EXP_CRYSTAL_LVL2, 12);

        this.name = "Эндермэн";
        this.id = "enderman";
        this.item = Material.ENDER_PEARL;
        this.rarity = 5;
        this.icon = IconType.ECHO_SHARD;
        this.description = "Игрок получает 1 эндер сундук при первом спавне.#При первом респавне или убийстве после (values1)#минут игры игрок получит 1 эндер жемчуг.";
        this.used = false;
    }

    @Override
    public int calculateIntValue1(int level) {
        return 720 - 120 * level;
    }

    @Override
    public String formatValue1(int level) {
        return "" + calculateIntValue1(level) / 60;
    }


    @Override
    public void playerKill(int level, Player killer, PlayerDeathEvent event) {

        Game game = Main.getPlayerGameProfile(killer).getGame();

        if (!this.used) {
            if ( game.getGameTime() - game.countdown > calculateIntValue1(level) ) {
                game.shop.findItemById("pearl").giveForFree(killer);
                this.used = true;
            }
        }
    }

    @Override
    public void playerRespawn(int level, GamePlayer gamePlayer) {

        Game game = gamePlayer.getGame();

        if (!this.used) {
            if ( game.getGameTime() - game.countdown > calculateIntValue1(level) ) {
                game.shop.findItemById("pearl").giveForFree(gamePlayer.player);
                this.used = true;
            }
        }

    }

    @Override
    public void playerFirstSpawn(int level, GamePlayer gamePlayer) {

        gamePlayer.getGame().shop.findItemById("ender_chest").giveForFree(gamePlayer.player);

    }
}
