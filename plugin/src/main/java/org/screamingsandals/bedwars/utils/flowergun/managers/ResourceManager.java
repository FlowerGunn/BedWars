package org.screamingsandals.bedwars.utils.flowergun.managers;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.OwnedResource;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.Resource;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.UUID;

public class ResourceManager {


    public ResourceManager() {

    }


    public void initializeDatabase() {

        Main.getInstance().getLogger().info("Loading abilities from database ...");

        try {
            if (!Main.getDatabaseManager().initialized) Main.getDatabaseManager().initialize();

            try (Connection connection = Main.getDatabaseManager().getConnection()) {
                connection.setAutoCommit(false);
                PreparedStatement preparedStatement = connection
                        .prepareStatement(Main.getDatabaseManager().getCreateResourcesTableSql());
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

    public ArrayList<OwnedResource> getAllResourcesByUUID(UUID uuid) {
        return Main.getDatabaseManager().readAllResourcesByUUID(uuid);
    }

    public OwnedResource getResourceByUUIDandId(UUID uuid, ResourceType resourceType) {
        return Main.getDatabaseManager().readResourceByUUIDandId( uuid, resourceType );
    }

    public static boolean giveResourcesTo(UUID uniqueId, Resource resource) {
        return giveResourcesTo(uniqueId, resource.getType(), resource.getAmount());

    }

    public static boolean giveResourcesTo(UUID uniqueId, ResourceType resourceType, int addition) {
        return giveResourcesTo(uniqueId, resourceType, addition, true);
    }

    public static boolean giveResourcesTo(UUID uniqueId, ResourceType resourceType, int addition, boolean notify) {

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uniqueId);

//        Resource resource;
        OwnedResource dbOwnedResource = Main.getDatabaseManager().readResourceByUUIDandId( uniqueId, resourceType );;

        if ( offlinePlayer.isOnline() && dbOwnedResource != null ) {
            GamePlayer gamePlayer = Main.getPlayerGameProfile(Bukkit.getPlayer(uniqueId));
            OwnedResource preloadedOwnedResource = gamePlayer.ownedResourceBundle.getOwnedResource(resourceType);
            if ( preloadedOwnedResource == null || preloadedOwnedResource.getAmount() != dbOwnedResource.getAmount() ) {
                Bukkit.getConsoleSender().sendMessage("DB resource amount mismatch - " + dbOwnedResource.getType() + " - db=" + dbOwnedResource.getAmount() + " gp=" + preloadedOwnedResource.getAmount());
                gamePlayer.ownedResourceBundle.getOwnedResource(resourceType).amount = dbOwnedResource.getAmount();
            }
        }

        if ( dbOwnedResource != null ) {

            if ( dbOwnedResource.amount + addition < 0 ) return false;
            dbOwnedResource.amount += addition;
            Main.getDatabaseManager().storeDatabaseResource(dbOwnedResource);
            if (offlinePlayer.isOnline()) {
                Main.getPlayerGameProfile(Bukkit.getPlayer(uniqueId)).ownedResourceBundle.getOwnedResource(resourceType).amount += addition;
                if (notify)
                NotificationManager.getResourcesMessage(offlinePlayer.getPlayer(), new Resource(resourceType, addition));
            }

        } else {
            if ( addition < 0 ) return false;
            dbOwnedResource = new OwnedResource(resourceType, addition, Main.getDatabaseManager().getMaxResourceEntryId() + 1, uniqueId);
            if ( offlinePlayer.isOnline() ) {
                NotificationManager.unlockResourceMessage(offlinePlayer.getPlayer(), new Resource(resourceType, addition));
                Main.getPlayerGameProfile(Bukkit.getPlayer(uniqueId)).ownedResourceBundle.addResource(dbOwnedResource);
                ForgeManager.loadAvailableRecipes(Main.getPlayerGameProfile(Bukkit.getPlayer(uniqueId)));
            }
            Main.getDatabaseManager().storeDatabaseResource(dbOwnedResource);
        }

        return true;
    }

    public boolean isIdValid(String resourceId) {
        return ResourceType.valueOf(resourceId) == null ? false : true;
    }
}
