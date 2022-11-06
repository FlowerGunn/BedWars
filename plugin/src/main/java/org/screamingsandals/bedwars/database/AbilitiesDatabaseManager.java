/*
 * Copyright (C) 2022 ScreamingSandals
 *
 * This file is part of Screaming BedWars.
 *
 * Screaming BedWars is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Screaming BedWars is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Screaming BedWars. If not, see <https://www.gnu.org/licenses/>.
 */

package org.screamingsandals.bedwars.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.entity.Player;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.statistics.PlayerStatistic;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.OwnedAbility;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.UUID;

public class AbilitiesDatabaseManager {
    private String tablePrefix;
    private String database;
    private HikariDataSource dataSource = null;
    private String host;
    private String password;
    private int port;
    private String user;
    private boolean useSSL;

    public void initializeDatabase() {
        Main.getInstance().getLogger().info("Loading statistics from database ...");

        try {
            Main.getDatabaseManager().initialize();

            try (Connection connection = Main.getDatabaseManager().getConnection()) {
                connection.setAutoCommit(false);
                PreparedStatement preparedStatement = connection
                        .prepareStatement(Main.getDatabaseManager().getCreateTableSql());
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

    public AbilitiesDatabaseManager(String host, int port, String user, String password, String database, String tablePrefix, boolean useSSL) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.database = database;
        this.tablePrefix = tablePrefix;
        this.useSSL = useSSL;
    }

    public void initialize() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database
                + "?autoReconnect=true&serverTimezone=" + TimeZone.getDefault().getID() + "&useSSL=" + useSSL);
        config.setUsername(this.user);
        config.setPassword(this.password);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        this.dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() {
        try {
            return this.dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCreateTableSql() {
        return "CREATE TABLE IF NOT EXISTS `" + tablePrefix
                + "abilities` ('entry_id' int(11) NOT NULL DEFAULT '0' AUTO_INCREMENT, `ability_id` varchar(255) NOT NULL DEFAULT 'ability', `duplicates_owned` int(11) NOT NULL DEFAULT '0', `amount_crafted` int(11) NOT NULL DEFAULT '0', `used_in_slot` int(11) NOT NULL DEFAULT '-1', `available_level` int(11) NOT NULL DEFAULT '1', `uuid` varchar(255) NOT NULL, PRIMARY KEY (`entry_id`))";
//                + "abilities` (`ability_id` int(11) NOT NULL DEFAULT '0', `wins` int(11) NOT NULL DEFAULT '0', `score` int(11) NOT NULL DEFAULT '0', `loses` int(11) NOT NULL DEFAULT '0', `name` varchar(255) NOT NULL, `destroyedBeds` int(11) NOT NULL DEFAULT '0', `uuid` varchar(255) NOT NULL, `deaths` int(11) NOT NULL DEFAULT '0', PRIMARY KEY (`uuid`))";
    }

    public String getReadObjectSql() {
        return "SELECT * FROM " + tablePrefix + "abilities WHERE uuid = ? and ability_id = ? LIMIT 1";
    }

    public String getReadObjectsSql() {
        return "SELECT * FROM " + tablePrefix + "abilities WHERE uuid = ?";
    }

    public String getWriteObjectSql() {
            return "INSERT INTO " + tablePrefix
                + "abilities(entry_id, uuid, ability_id, duplicates_owned, amount_crafted, used_in_slot, available_level) VALUES (?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE entry_id=VALUES(entry_id),uuid=VALUES(uuid),ability_id=VALUES(ability_id),duplicates_owned=VALUES(duplicates_owned),amount_crafted=VALUES(amount_crafted),used_in_slot=VALUES(used_in_slot),available_level=VALUES(available_level)";
    }

    public String getScoresSql() {
        return "SELECT uuid FROM " + tablePrefix + "abilities";
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    private void storeDatabaseAbility(OwnedAbility ownedAbility) {
        try (Connection connection = this.getConnection()) {
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection
                    .prepareStatement(this.getWriteObjectSql());

            preparedStatement.setInt(1, ownedAbility.dbEntryId);
            preparedStatement.setString(2, ownedAbility.ownerUUID.toString());
            preparedStatement.setString(3, ownedAbility.getAbility().getId());
            preparedStatement.setInt(4, ownedAbility.duplicatesOwned);
            preparedStatement.setInt(5, ownedAbility.instancesCrafted);
            preparedStatement.setInt(6, ownedAbility.lastEquippedSlot);
            preparedStatement.setInt(7, ownedAbility.ownedLevel);
            preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<OwnedAbility> loadDatabaseAbilities(UUID uuid) {
//        if (this.playerStatistic.containsKey(uuid)) {
//            return this.playerStatistic.get(uuid);
//        }
        ArrayList<OwnedAbility> abilities = new ArrayList<>();

        try (Connection connection = this.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(this.getReadObjectSql());
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

//            ResultSetMetaData meta = resultSet.getMetaData();
            while (resultSet.next()) {
                abilities.add( new OwnedAbility( resultSet.getInt("entry_id"), UUID.fromString(resultSet.getString("uuid")) ,resultSet.getString("ability_id"),resultSet.getInt("available_level"),resultSet.getInt("duplicates_owned"),resultSet.getInt("amount_crafted"),resultSet.getInt("used_in_slot")) );
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        PlayerStatistic playerStatistic;
//
//        if (deserialize.isEmpty()) {
//            playerStatistic = new PlayerStatistic(uuid);
//        } else {
//            playerStatistic = new PlayerStatistic(deserialize);
//        }
//        Player player = Main.getInstance().getServer().getPlayer(uuid);
//        if (player != null && !playerStatistic.getName().equals(player.getName())) {
//            playerStatistic.setName(player.getName());
//        }
//        allScores.put(uuid, playerStatistic.getScore());

//        this.playerStatistic.put(playerStatistic.getId(), playerStatistic);
        return abilities;
    }


}