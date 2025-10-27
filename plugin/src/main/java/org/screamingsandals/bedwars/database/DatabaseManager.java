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
import org.bukkit.Bukkit;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.OwnedAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ActiveForgeRecipe;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.OwnedResource;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.PlayerConfig;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.PlayerConfigType;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

import java.sql.*;
import java.util.ArrayList;
import java.util.TimeZone;
import java.util.UUID;

public class DatabaseManager {
    private String tablePrefix;
    private String database;
    private HikariDataSource dataSource = null;

    private String host;
    private String password;
    private int port;
    private String user;
    private boolean useSSL;
    public boolean initialized = false;

    public DatabaseManager(String host, int port, String user, String password, String database, String tablePrefix, boolean useSSL) {
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

        this.initialized = true;
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
        Bukkit.getConsoleSender().sendMessage("CREATE TABLE IF NOT EXISTS bw_stats_players");
        return "CREATE TABLE IF NOT EXISTS `" + tablePrefix
                + "stats_players` (`kills` int(11) NOT NULL DEFAULT '0', `wins` int(11) NOT NULL DEFAULT '0', `score` int(11) NOT NULL DEFAULT '0', `loses` int(11) NOT NULL DEFAULT '0', `name` varchar(255) NOT NULL, `destroyedBeds` int(11) NOT NULL DEFAULT '0', `uuid` varchar(255) NOT NULL, `deaths` int(11) NOT NULL DEFAULT '0', PRIMARY KEY (`uuid`))";
    }

    public String getReadObjectSql() {
        return "SELECT * FROM " + tablePrefix + "stats_players WHERE uuid = ? LIMIT 1";
    }

    public String getWriteObjectSql() {
        return "INSERT INTO " + tablePrefix
                + "stats_players(uuid, name, deaths, destroyedBeds, kills, loses, score, wins) VALUES (?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE uuid=VALUES(uuid),name=VALUES(name),deaths=VALUES(deaths),destroyedBeds=VALUES(destroyedBeds),kills=VALUES(kills),loses=VALUES(loses),score=VALUES(score),wins=VALUES(wins)";
    }

    public String getScoresSql() {
        return "SELECT uuid, score FROM " + tablePrefix + "stats_players";
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    // ABILITIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public String getCreateAbilitiesTableSql() {
        Bukkit.getConsoleSender().sendMessage("CREATE TABLE IF NOT EXISTS bw_abilities");
        return "CREATE TABLE IF NOT EXISTS " + tablePrefix
                + "abilities (entry_id int(11) NOT NULL DEFAULT 0, ability_id varchar(255) NOT NULL DEFAULT 'ability', duplicates_owned int(11) NOT NULL DEFAULT 0, amount_crafted int(11) NOT NULL DEFAULT '0', used_in_slot int(11) NOT NULL DEFAULT '-1', available_level int(11) NOT NULL DEFAULT '1', uuid varchar(255) NOT NULL, PRIMARY KEY (entry_id))";
//                + "abilities` (`ability_id` int(11) NOT NULL DEFAULT '0', `wins` int(11) NOT NULL DEFAULT '0', `score` int(11) NOT NULL DEFAULT '0', `loses` int(11) NOT NULL DEFAULT '0', `name` varchar(255) NOT NULL, `destroyedBeds` int(11) NOT NULL DEFAULT '0', `uuid` varchar(255) NOT NULL, `deaths` int(11) NOT NULL DEFAULT '0', PRIMARY KEY (`uuid`))";
    }

    public String getReadAbilityInfoByUUIDandIDSql() {
        return "SELECT * FROM " + tablePrefix + "abilities WHERE uuid = ? and ability_id = ? LIMIT 1";
    }

    public String getReadAllAbilitiesByUUIDSql() {
        return "SELECT * FROM " + tablePrefix + "abilities WHERE uuid = ?";
    }

    public String getWriteAbilityInfoSql() {
        return "INSERT INTO " + tablePrefix + "abilities(entry_id, uuid, ability_id, duplicates_owned, amount_crafted, used_in_slot, available_level) VALUES (?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE entry_id=VALUES(entry_id),uuid=VALUES(uuid),ability_id=VALUES(ability_id),duplicates_owned=VALUES(duplicates_owned),amount_crafted=VALUES(amount_crafted),used_in_slot=VALUES(used_in_slot),available_level=VALUES(available_level)";
    }

    public String getMaxAbilityEntryIdSql() {
        return "SELECT MAX(entry_id) AS max_entry_id FROM " + tablePrefix + "abilities";
    }

    public String getResetAllAbilitiesSlotsSql() {
        return "UPDATE " + tablePrefix + "abilities SET used_in_slot = -1 WHERE uuid = ?";
    }

    public String getSetAbilitySlotSql() {
        return "UPDATE " + tablePrefix + "abilities SET used_in_slot = ? WHERE entry_id = ?";
    }


    public String getAllAbilityOwnersSql() {
        return "SELECT uuid FROM " + tablePrefix + "abilities";
    }


    public void storeDatabaseAbility(OwnedAbility ownedAbility) {
        try (Connection connection = this.getConnection()) {
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection
                    .prepareStatement(this.getWriteAbilityInfoSql());
//            Bukkit.getConsoleSender().sendMessage("saving entry " + ownedAbility.dbEntryId);
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

    public ArrayList<OwnedAbility> readAllAbilitiesByUUID(UUID uuid) {

        ArrayList<OwnedAbility> abilities = new ArrayList<>();

        try (Connection connection = this.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(this.getReadAllAbilitiesByUUIDSql());
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                abilities.add(new OwnedAbility(resultSet.getInt("entry_id"), UUID.fromString(resultSet.getString("uuid")), resultSet.getString("ability_id"), resultSet.getInt("available_level"), resultSet.getInt("duplicates_owned"), resultSet.getInt("amount_crafted"), resultSet.getInt("used_in_slot")));
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return abilities;
    }

    public void resetAllAbilitiesSlotsByUUID(UUID uuid) {

        try (Connection connection = this.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(this.getResetAllAbilitiesSlotsSql());
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setAbilitySlotByEntryId(int entry_id, int slot) {

        try (Connection connection = this.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(this.getSetAbilitySlotSql());
            preparedStatement.setInt(2, entry_id);
            preparedStatement.setInt(1, slot);
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getMaxAbilityEntryId() {

        try (Connection connection = this.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(this.getMaxAbilityEntryIdSql());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int result = resultSet.getInt("max_entry_id");

                resultSet.close();
                preparedStatement.close();

                return result;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public OwnedAbility readAbilityByUUIDandId(UUID uuid, String abilityId) {

        OwnedAbility ownedAbility = null;

        try (Connection connection = this.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(this.getReadAbilityInfoByUUIDandIDSql());
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2, abilityId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                ownedAbility = new OwnedAbility(resultSet.getInt("entry_id"), UUID.fromString(resultSet.getString("uuid")), resultSet.getString("ability_id"), resultSet.getInt("available_level"), resultSet.getInt("duplicates_owned"), resultSet.getInt("amount_crafted"), resultSet.getInt("used_in_slot"));
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ownedAbility;
    }

    // RESOURCES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    private String getReadResourceByUUIDandIdSql() {
        return "SELECT * FROM " + tablePrefix + "resources WHERE uuid = ? and resource_id = ?";
    }

    private String getReadAllResourcesByUUIDSql() {
        return "SELECT * FROM " + tablePrefix + "resources WHERE uuid = ?";
    }

    public String getCreateResourcesTableSql() {
        return "CREATE TABLE IF NOT EXISTS " + tablePrefix + "resources (entry_id int(11) NOT NULL DEFAULT 0, resource_id varchar(255) NOT NULL DEFAULT 'resource', amount int(11) NOT NULL DEFAULT 0, uuid varchar(255) NOT NULL, PRIMARY KEY (entry_id))";
    }

    private String getMaxResourceEntryIdSql() {
        return "SELECT MAX(entry_id) AS max_entry_id FROM " + tablePrefix + "resources";
    }

    private String getWriteResourceInfoSql() {
        return "INSERT INTO " + tablePrefix + "resources(entry_id, resource_id, amount, uuid) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE entry_id=VALUES(entry_id),resource_id=VALUES(resource_id),amount=VALUES(amount),uuid=VALUES(uuid)";
    }

    public int getMaxResourceEntryId() {

        try (Connection connection = this.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(this.getMaxResourceEntryIdSql());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int result = resultSet.getInt("max_entry_id");

                resultSet.close();
                preparedStatement.close();

                return result;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void storeDatabaseResource(OwnedResource ownedResource) {
        try (Connection connection = this.getConnection()) {
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(this.getWriteResourceInfoSql());
            preparedStatement.setInt(1, ownedResource.getEntry_id());
            preparedStatement.setString(4, ownedResource.getOwner().toString());
            preparedStatement.setString(2, ownedResource.getType().toString());
            preparedStatement.setInt(3, ownedResource.getAmount());
            preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }


    public ArrayList<OwnedResource> readAllResourcesByUUID(UUID uuid) {

        ArrayList<OwnedResource> ownedResources = new ArrayList<>();

        try (Connection connection = this.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(this.getReadAllResourcesByUUIDSql());
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ownedResources.add(new OwnedResource(resultSet.getString("resource_id"), resultSet.getInt("amount"), resultSet.getInt("entry_id"), UUID.fromString(resultSet.getString("uuid"))));
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ownedResources;
    }

    public OwnedResource readResourceByUUIDandId(UUID uuid, ResourceType resourceType) {

        OwnedResource ownedResource = null;

        try (Connection connection = this.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(this.getReadResourceByUUIDandIdSql());
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2, resourceType.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                ownedResource = new OwnedResource(resultSet.getString("resource_id"), resultSet.getInt("amount"), resultSet.getInt("entry_id"), UUID.fromString(resultSet.getString("uuid")));
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ownedResource;
    }

    // FORGE ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


//    private String getReadForgeByUUIDandIdSql() {
//        return "SELECT * FROM " + tablePrefix + "forge WHERE uuid = ? and resource_id = ?";
//    }

    private String getReadAllForgesByUUIDSql() {
        return "SELECT * FROM " + tablePrefix + "forge WHERE uuid = ?";
    }

    public String getCreateForgeTableSql() {
        return "CREATE TABLE IF NOT EXISTS " + tablePrefix + "forge (entry_id int(11) NOT NULL DEFAULT 0, recipe_id varchar(255), slot int(11) NOT NULL DEFAULT 0, start_time timestamp, uuid varchar(255) NOT NULL, redeemed boolean, PRIMARY KEY (entry_id))";
    }

    private String getMaxForgeEntryIdSql() {
        return "SELECT MAX(entry_id) AS max_entry_id FROM " + tablePrefix + "forge";
    }

    private String getWriteForgeInfoSql() {
        return "INSERT INTO " + tablePrefix + "forge(entry_id, recipe_id, slot, start_time, uuid, redeemed ) VALUES (?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE entry_id=VALUES(entry_id),recipe_id=VALUES(recipe_id),slot=VALUES(slot),start_time=VALUES(start_time),uuid=VALUES(uuid),redeemed=VALUES(redeemed)";
    }

    public int getMaxForgeEntryId() {

        try (Connection connection = this.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(this.getMaxForgeEntryIdSql());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int result = resultSet.getInt("max_entry_id");

                resultSet.close();
                preparedStatement.close();

                return result;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void storeDatabaseForge(ActiveForgeRecipe activeForgeRecipe) {
        try (Connection connection = this.getConnection()) {
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(this.getWriteForgeInfoSql());
            preparedStatement.setInt(1, activeForgeRecipe.getEntry_id());
            preparedStatement.setString(5, activeForgeRecipe.getUuid().toString());
            preparedStatement.setString(2, activeForgeRecipe.getRecipe().getId());
            preparedStatement.setInt(3, activeForgeRecipe.getSlot());
            preparedStatement.setBoolean(6, activeForgeRecipe.isRedeemed());
            preparedStatement.setTimestamp(4, activeForgeRecipe.getStartTime());
            preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }


    public ArrayList<ActiveForgeRecipe> readAllForgesByUUID(UUID uuid) {

        ArrayList<ActiveForgeRecipe> activeForgeRecipes = new ArrayList<>();

        try (Connection connection = this.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(this.getReadAllForgesByUUIDSql());
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                activeForgeRecipes.add(new ActiveForgeRecipe(resultSet.getInt("entry_id"), resultSet.getString("recipe_id"), UUID.fromString(resultSet.getString("uuid")), resultSet.getInt("slot"), resultSet.getTimestamp("start_time")));
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return activeForgeRecipes;
    }

//    public ActiveForgeRecipe readForgeByUUIDandSlot(UUID uuid, int slot) {
//
//        OwnedForge ownedForge = null;
//
//        try (Connection connection = this.getConnection()) {
//            PreparedStatement preparedStatement = connection
//                    .prepareStatement(this.getReadForgeByUUIDandIdSql());
//            preparedStatement.setString(1, uuid.toString());
//            preparedStatement.setString(2, resourceType.toString());
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            if (resultSet.next()) {
//                ownedForge = new OwnedForge(resultSet.getString("recipe_id"), resultSet.getInt("amount"), resultSet.getInt("entry_id"), UUID.fromString(resultSet.getString("uuid")));
//            }
//
//            resultSet.close();
//            preparedStatement.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return ownedForge;
//    }

    // LOGS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


//    private String getReadForgeByUUIDandIdSql() {
//        return "SELECT * FROM " + tablePrefix + "forge WHERE uuid = ? and resource_id = ?";
//    }

    public String getCreateLogTableSql() {
        return "CREATE TABLE IF NOT EXISTS " + tablePrefix + "logs (entry_id int(11) NOT NULL AUTO_INCREMENT, log_type varchar(255), log_player varchar(255), log_info1 varchar(255), log_info2 varchar(255), log_info3 varchar(255), log_info4 varchar(255), log_info5 varchar(255), log_time timestamp, plugin_version varchar(255),  PRIMARY KEY (entry_id))";
    }


    private String getWriteLogInfoSql() {
        return "INSERT INTO " + tablePrefix + "logs(log_type, log_player, log_info1, log_info2, log_info3, log_info4, log_info5, log_time, plugin_version ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }


    public void storeDatabaseLog(String log_type, String plugin_version, String log_player, String log_info1, String log_info2, String log_info3, String log_info4, String log_info5, Timestamp log_time) {
        try (Connection connection = this.getConnection()) {
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(this.getWriteLogInfoSql());
            preparedStatement.setString(1, log_type);
            preparedStatement.setString(2, log_player);
            preparedStatement.setString(3, log_info1);
            preparedStatement.setString(4, log_info2);
            preparedStatement.setString(5, log_info3);
            preparedStatement.setString(6, log_info4);
            preparedStatement.setString(7, log_info5);
            preparedStatement.setTimestamp(8, log_time);
            preparedStatement.setString(9, plugin_version);
            preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ CONFIG


    public String getCreateConfigTableSql() {
        return "CREATE TABLE IF NOT EXISTS " + tablePrefix + "configs (entry_id int(11) NOT NULL AUTO_INCREMENT, config_type varchar(255), config_player varchar(255), config_param1 varchar(255), config_param2 varchar(255), config_param3 varchar(255), config_param4 varchar(255), config_param5 varchar(255), config_param6 varchar(255), config_param7 varchar(255), config_param8 varchar(255), config_change_time timestamp, PRIMARY KEY (entry_id))";
    }

//
//    private String getWriteConfigSql() {
//        return "INSERT INTO " + tablePrefix + "configs(config_type, config_player, config_param1, config_param2, config_param3, config_change_time ) VALUES (?, ?, ?, ?, ?, ?)";
//    }


    public void storeDatabaseConfig(PlayerConfig config) {
        try (Connection connection = this.getConnection()) {
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(this.getWriteConfigSql());
            preparedStatement.setInt(1, config.getEntryId());
            preparedStatement.setString(2, config.getPlayerConfigType().toString());
            preparedStatement.setString(3, config.getUuid().toString());
            preparedStatement.setString(4, config.getParameter1());
            preparedStatement.setString(5, config.getParameter2());
            preparedStatement.setString(6, config.getParameter3());
            preparedStatement.setString(7, config.getParameter4());
            preparedStatement.setString(8, config.getParameter5());
            preparedStatement.setString(9, config.getParameter6());
            preparedStatement.setString(10, config.getParameter7());
            preparedStatement.setString(11, config.getParameter8());
            preparedStatement.setTimestamp(12, config.getLastEdit());
            preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }


    private String getWriteConfigSql() {
        return "INSERT INTO " + tablePrefix + "configs( entry_id, config_type, config_player, config_param1, config_param2, config_param3, config_param4, config_param5, config_param6, config_param7, config_param8, config_change_time ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE entry_id=VALUES(entry_id),config_type=VALUES(config_type),config_player=VALUES(config_player),config_param1=VALUES(config_param1),config_param2=VALUES(config_param2),config_param3=VALUES(config_param3),config_param4=VALUES(config_param4),config_param5=VALUES(config_param5),config_param6=VALUES(config_param6),config_param7=VALUES(config_param7),config_param8=VALUES(config_param8),config_change_time=VALUES(config_change_time)";
    }

    private String getMaxConfigEntryIdSql() {
        return "SELECT MAX(entry_id) AS max_entry_id FROM " + tablePrefix + "configs";
    }
    public int getMaxConfigEntryId() {

        try (Connection connection = this.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(this.getMaxConfigEntryIdSql());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int result = resultSet.getInt("max_entry_id");

                resultSet.close();
                preparedStatement.close();

                return result;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private String getReadAllConfigsByUUIDSql() {
        return "SELECT * FROM " + tablePrefix + "configs WHERE config_player = ?";
    }
    public ArrayList<PlayerConfig> readAllConfigsByUUID(UUID uuid) {

        ArrayList<PlayerConfig> activeForgeRecipes = new ArrayList<>();

        try (Connection connection = this.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(this.getReadAllConfigsByUUIDSql());
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                activeForgeRecipes.add(new PlayerConfig(resultSet.getInt("entry_id"), UUID.fromString(resultSet.getString("config_player")), PlayerConfigType.valueOf(resultSet.getString("config_type")), resultSet.getString("config_param1"), resultSet.getString("config_param2"), resultSet.getString("config_param3"), resultSet.getString("config_param4"), resultSet.getString("config_param5"), resultSet.getString("config_param6"), resultSet.getString("config_param7"), resultSet.getString("config_param8"), resultSet.getTimestamp("config_change_time")));
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return activeForgeRecipes;
    }

}