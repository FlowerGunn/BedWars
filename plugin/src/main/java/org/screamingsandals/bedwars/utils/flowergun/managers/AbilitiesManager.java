package org.screamingsandals.bedwars.utils.flowergun.managers;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.LoadedAbility;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.OwnedAbility;
import org.screamingsandals.bedwars.utils.flowergun.abilities_impl.*;
import org.screamingsandals.bedwars.utils.flowergun.other.comparators.SortByRarityOwnedAbility;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class AbilitiesManager {

    @Getter
    private ArrayList<Class> allAbilitiesClasses = new ArrayList<>();
    @Getter
    private ArrayList<IAbility> allAbilities = new ArrayList<>();

    public AbilitiesManager() {

        this.allAbilitiesClasses.add(CopperManiac.class);
        this.allAbilitiesClasses.add(Trader.class);
        this.allAbilitiesClasses.add(Levitator.class);
        this.allAbilitiesClasses.add( Tempered.class );
        this.allAbilitiesClasses.add(Friendlyness.class);
        this.allAbilitiesClasses.add(Willpower.class);
        this.allAbilitiesClasses.add(Sniper.class);
        this.allAbilitiesClasses.add(ForceField.class);
        this.allAbilitiesClasses.add(Slingshooter.class);
        this.allAbilitiesClasses.add(Endurance.class);
        this.allAbilitiesClasses.add(Slimy.class);
        this.allAbilitiesClasses.add(FrostKiss.class);
        this.allAbilitiesClasses.add(Ultimatum.class);
        this.allAbilitiesClasses.add(Pickpocket.class);
        this.allAbilitiesClasses.add(Greediness.class);
        this.allAbilitiesClasses.add(Aromatherapy.class);
        this.allAbilitiesClasses.add(MagmaSkin.class);
        this.allAbilitiesClasses.add(WoolManiac.class);
        this.allAbilitiesClasses.add(BloodPact.class);
        this.allAbilitiesClasses.add(Lumberjack.class);
        this.allAbilitiesClasses.add(Appleperson.class);
        this.allAbilitiesClasses.add(Weaponsmaster.class);
        this.allAbilitiesClasses.add(DefensiveStance.class);
        this.allAbilitiesClasses.add(Miner.class);
        this.allAbilitiesClasses.add(Gilded.class);
        this.allAbilitiesClasses.add(Starling.class);
        this.allAbilitiesClasses.add(Mirroring.class);

        this.allAbilitiesClasses.add(Cheers.class);
        this.allAbilitiesClasses.add(GuardianAngel.class);
        this.allAbilitiesClasses.add(Squire.class);
        this.allAbilitiesClasses.add(Training.class);
        this.allAbilitiesClasses.add(Weightless.class);

        this.allAbilitiesClasses.add(Berserk.class);
        this.allAbilitiesClasses.add(BigBoy.class);
        this.allAbilitiesClasses.add(Butcher.class);
        this.allAbilitiesClasses.add(Compassion.class);
        this.allAbilitiesClasses.add(EagleEye.class);
        this.allAbilitiesClasses.add(Enderman.class);
        this.allAbilitiesClasses.add(Radar.class);

        this.allAbilitiesClasses.add(Acrobatics.class);
        this.allAbilitiesClasses.add(Adrenaline.class);
        this.allAbilitiesClasses.add(HomeSweetHome.class);
        this.allAbilitiesClasses.add(LuckyPiere.class);

        this.allAbilitiesClasses.add(Evasion.class);
        this.allAbilitiesClasses.add(Shrapnel.class);

        for (Class clazz : this.allAbilitiesClasses) {
            this.allAbilities.add(Ability.generateAbility(clazz));
        }

    }


    public boolean isIdValid(String id) {
        if (findAbiilityInfoById(id) != null) return true;
        else return false;
    }

    public IAbility findAbiilityInfoById(String id) {
        for (IAbility ability : this.allAbilities) {
            if (ability.getId().equals(id)) return ability;
        }
        return null;
    }


    public static String formatLoadedAbilityNameInSlot(GamePlayer gPlayer, int slot) {
        ArrayList<LoadedAbility> loadedAbilities = gPlayer.loadedAbilities;
        if (loadedAbilities.size() <= slot) return "Error";
        LoadedAbility loadedAbility = loadedAbilities.get(slot);
        if (loadedAbility.isEmpty()) return ColoursManager.darkGray +  "- " + ChatColor.WHITE + IconsManager.requestIcon(IconType.SLOT_LOCKED, gPlayer.player) + ColoursManager.darkGray + " -";
            return loadedAbility.getOwnedAbility().getAbility().getNameWithIcon(gPlayer.player) + ChatColor.GRAY +  " Ур." + loadedAbility.activeLevel;
    }

    public void initializeDatabase() {

        Main.getInstance().getLogger().info("Loading abilities from database ...");

        try {
            if (!Main.getDatabaseManager().initialized) Main.getDatabaseManager().initialize();

            try (Connection connection = Main.getDatabaseManager().getConnection()) {
                connection.setAutoCommit(false);
                PreparedStatement preparedStatement = connection
                        .prepareStatement(Main.getDatabaseManager().getCreateAbilitiesTableSql());
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

    public ArrayList<OwnedAbility> getAllAbilitiesByUUID(UUID uuid) {
        return Main.getDatabaseManager().readAllAbilitiesByUUID(uuid);
    }

    public static OwnedAbility getAbilityById( ArrayList<OwnedAbility> ownedAbilities,String id) {
        for ( OwnedAbility ownedAbility : ownedAbilities ) {
            if ( ownedAbility.getAbility().getId().equals(id) ) return  ownedAbility;
        }
        return null;
    }

    public void giveAllAbilitiesTo(UUID uuid) {

    }

    public void giveAbilityToById(UUID uniqueId, String abilityId, int amount) {
        giveAbilityToById(uniqueId, abilityId, amount, false);
    }

    public void giveAbilityToById(UUID uniqueId, String abilityId, int amount, boolean crafted) {
        OwnedAbility ownedAbility;
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uniqueId);

        if (offlinePlayer.isOnline()) {
            ownedAbility = Main.getPlayerGameProfile(Bukkit.getPlayer(uniqueId)).getOwnedAbilityById(abilityId);
//            Bukkit.getConsoleSender().sendMessage("loading ability from an online player");
        } else {
            ownedAbility = Main.getDatabaseManager().readAbilityByUUIDandId(offlinePlayer.getUniqueId(), abilityId);
//            Bukkit.getConsoleSender().sendMessage("loading ability from an offline player");
        }

        if ( amount <= 0 ) return;

        if ( ownedAbility != null && ownedAbility.ownedLevel != 0 ) {
            ownedAbility.duplicatesOwned += amount;
            ownedAbility.instancesCrafted += amount;
            Main.getDatabaseManager().storeDatabaseAbility(ownedAbility);
            if (offlinePlayer.isOnline()) {
                Collections.sort(Main.getPlayerGameProfile(offlinePlayer.getPlayer()).ownedAbilities, new SortByRarityOwnedAbility());
                NotificationManager.getAbilityMessage(offlinePlayer.getPlayer(), ownedAbility);
            }
            return;
        }

        OwnedAbility newOwnedAbility = new OwnedAbility(Main.getDatabaseManager().getMaxAbilityEntryId() + 1, uniqueId, abilityId, 1, amount - 1, crafted ? amount : 0, -1);
        if (offlinePlayer.isOnline()) {
            replaceAbilityWithANewOne(Main.getPlayerGameProfile(Bukkit.getPlayer(uniqueId)).ownedAbilities, newOwnedAbility);
            NotificationManager.getAbilityMessage(offlinePlayer.getPlayer(), ownedAbility);
        }
        Main.getDatabaseManager().storeDatabaseAbility(newOwnedAbility);

    }

    public void removeAllAbilitiesFrom(UUID uniqueId) {
    }

    public boolean removeAbilityFromById(UUID uniqueId, String abilityId, int amount) {

        OwnedAbility ownedAbility;
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uniqueId);

        if (offlinePlayer.isOnline()) {
            ownedAbility = Main.getPlayerGameProfile(Bukkit.getPlayer(uniqueId)).getOwnedAbilityById(abilityId);
//            Bukkit.getConsoleSender().sendMessage("loading ability from an online player");
        } else {
            ownedAbility = Main.getDatabaseManager().readAbilityByUUIDandId(offlinePlayer.getUniqueId(), abilityId);
//            Bukkit.getConsoleSender().sendMessage("loading ability from an offline player");
        }

        if ( amount <= 0 ) return false;

        if ( ownedAbility == null || ownedAbility.ownedLevel == 0 || ownedAbility.duplicatesOwned - amount < 0 ) {
            return false;
        } else {
            ownedAbility.duplicatesOwned -= amount;
            Main.getDatabaseManager().storeDatabaseAbility(ownedAbility);
            return true;
        }
    }

    public static boolean abilitiesContainId( ArrayList<OwnedAbility> ownedAbilities, String id ) {
        for ( OwnedAbility ability: ownedAbilities ) {
            if ( ability.getAbility().getId().equals(id) ) return true;
        }
        return false;
    }

    public static boolean replaceAbilityWithANewOne( ArrayList<OwnedAbility> ownedAbilities, OwnedAbility ownedAbility ) {
        for ( OwnedAbility ability: ownedAbilities ) {
            if ( ability.getAbility().getId().equals(ownedAbility.getAbility().getId()) ) {
                boolean available = ability.isOwned();
                ability.dbEntryId = ownedAbility.dbEntryId;
                ability.ownedLevel = ownedAbility.ownedLevel;
                ability.lastEquippedSlot = ownedAbility.lastEquippedSlot;
                ability.instancesCrafted = ownedAbility.instancesCrafted;
                return available;
            }
        }
        return false;
    }

    public void saveChosenAbilitiesSlots(GamePlayer gamePlayer) {

        Main.getDatabaseManager().resetAllAbilitiesSlotsByUUID(gamePlayer.player.getUniqueId());

        for ( int i = 0; i < gamePlayer.loadedAbilities.size(); i++ ) {
            LoadedAbility loadedAbility = gamePlayer.loadedAbilities.get(i);
            if ( loadedAbility.isEmpty() || !loadedAbility.getOwnedAbility().isOwned() ) continue;
//            Bukkit.getConsoleSender().sendMessage("saving ability " + loadedAbility.getOwnedAbility().getAbility().getName() + " into slot " + i);
            loadedAbility.getOwnedAbility().lastEquippedSlot = i;

            Main.getDatabaseManager().setAbilitySlotByEntryId(loadedAbility.getOwnedAbility().dbEntryId, loadedAbility.getOwnedAbility().lastEquippedSlot);

        }

    }

}
