package org.screamingsandals.bedwars.utils.flowergun.managers;

import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.database.DatabaseManager;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ActiveForgeRecipe;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.PlayerConfig;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.PlayerConfigType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class PlayerConfigurationManager {

    public PlayerConfigurationManager() {

    }

    public void initializeDatabase() {

        Main.getInstance().getLogger().info("Loading configs from database ...");

        try {
            if (!Main.getDatabaseManager().initialized) Main.getDatabaseManager().initialize();

            try (Connection connection = Main.getDatabaseManager().getConnection()) {
                connection.setAutoCommit(false);
                PreparedStatement preparedStatement = connection
                        .prepareStatement(Main.getDatabaseManager().getCreateConfigTableSql());
                preparedStatement.executeUpdate();
                connection.commit();
                preparedStatement.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadPlayerConfigs(GamePlayer gamePlayer) {

        ArrayList<PlayerConfig> playerConfigs = Main.getDatabaseManager().readAllConfigsByUUID(gamePlayer.player.getUniqueId());

        gamePlayer.customBuilds = new ArrayList<>();
        gamePlayer.settings = new ArrayList<>();
        gamePlayer.quests = new ArrayList<>();
        gamePlayer.eventRewards = new ArrayList<>();

        for ( PlayerConfig config : playerConfigs ) {
            if ( config.getPlayerConfigType() == PlayerConfigType.CUSTOM_PLAYER_ABILITIES_BUILD ) {
                gamePlayer.customBuilds.add(config);
            } else if ( config.getPlayerConfigType() == PlayerConfigType.EVENT_REWARD ) {
                gamePlayer.eventRewards.add(config);
            } else if ( config.getPlayerConfigType() == PlayerConfigType.QUEST ) {
                gamePlayer.quests.add(config);
            } else {
                gamePlayer.settings.add(config);
            }
        }

    }

    public void savePlayerConfig( PlayerConfig config ) {
        Main.getDatabaseManager().storeDatabaseConfig( config );
    }


}
