package org.screamingsandals.bedwars.utils.flowergun.mechanics;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.tools.comparators.ImpactInstanceComparator;

import java.util.ArrayList;
import java.util.Collections;

public class ImpactLog {

    public static int killDecayTimer = 10;
    public static int assistDecayTimer = 5;
    public static int assistersLimit = 3;

    public GamePlayer killer;
    public ArrayList<GamePlayer> assisters;


    @Getter
    ArrayList<ImpactInstance> impactInstances;

    public ImpactLog() {

        this.impactInstances = new ArrayList<>();
        this.assisters = new ArrayList<>();

    }

    public GamePlayer getKiller(int currentTimestamp) {
        for ( ImpactInstance impactInstance : this.impactInstances ) {
//            Bukkit.getConsoleSender().sendMessage("impact instance " + impactInstance.impactType.toString() + " countdown:" + currentTimestamp + " timestamp:" + impactInstance.countdownTimestamp);
            if ( impactInstance.impactType == ImpactType.DAMAGE && (impactInstance.countdownTimestamp - currentTimestamp) <= killDecayTimer ) {
                this.killer = impactInstance.owner;
//                Bukkit.getConsoleSender().sendMessage("found killer!");
                return impactInstance.owner;
            }
        }
        return null;
    }

    public ArrayList<ImpactInstance> getDefensiveAssistInstances(int currentTimestamp) {
        ArrayList<ImpactInstance> allDefensiveInstances = new ArrayList<>();

        for ( ImpactInstance impactInstance : this.impactInstances ) {
            if ( impactInstance.impactPolarity == ImpactPolarity.ALLY && (impactInstance.countdownTimestamp - currentTimestamp) <= assistDecayTimer ) allDefensiveInstances.add(impactInstance);
        }

        return allDefensiveInstances;
    }

    public ArrayList<GamePlayer> getAssisters(int currentTimestamp) {

        ArrayList<ImpactInstance> allImpactInstances = new ArrayList<>();

        ArrayList<GamePlayer> assistors = new ArrayList<>();

        int uniqueAssistersCounter = 0;

        for ( ImpactInstance impactInstance : this.impactInstances ) {
            if ( impactInstance.impactPolarity == ImpactPolarity.ENEMY && (impactInstance.countdownTimestamp - currentTimestamp) <= assistDecayTimer && impactInstance.owner != killer) allImpactInstances.add(impactInstance);
        }

        if (this.killer != null) {
            allImpactInstances.addAll(this.killer.getImpactLog().getDefensiveAssistInstances(currentTimestamp));
        }

        Collections.sort(allImpactInstances, new ImpactInstanceComparator());

        for ( ImpactInstance impactInstance : allImpactInstances ) {
            if (!assistors.contains(impactInstance.owner)) {
                assistors.add(impactInstance.owner);
            }
            if (assistors.size() >= assistersLimit) break;
        }

        return assistors;
    }

}
