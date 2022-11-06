package org.screamingsandals.bedwars.utils.flowergun.gameplay;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.abilities.*;

import java.util.ArrayList;

public class AbilitiesManager {

    @Getter
    private ArrayList<Class> allAbilities = new ArrayList<>();

    public AbilitiesManager() {

        this.allAbilities.add( CopperManiac.class );
        this.allAbilities.add( Clemptomaniac.class );
        this.allAbilities.add( Levitator.class );
        this.allAbilities.add( Tempered.class );
        this.allAbilities.add( Friendlyness.class );
        this.allAbilities.add( Willpower.class );
        this.allAbilities.add( Sniper.class );
        this.allAbilities.add( ForceField.class );
        this.allAbilities.add( Slingshooter.class );
        this.allAbilities.add( Posture.class );
        this.allAbilities.add( PreciseStrike.class );
        this.allAbilities.add( FrostKiss.class );
        this.allAbilities.add( Ultimatum.class );
        this.allAbilities.add( Pickpocket.class );
        this.allAbilities.add( Greediness.class );

    }


    public static String formatLoadedAbilityNameInSlot(GamePlayer gPlayer, int slot) {
        ArrayList<LoadedAbility> loadedAbilities = gPlayer.loadedAbilities;
        LoadedAbility loadedAbility = loadedAbilities.get(slot);
        if (loadedAbility == null) return ChatColor.GRAY +  "-- " + ChatColor.WHITE + ChatColor.BOLD +  "Пусто" + ChatColor.GRAY + " --";
            return loadedAbility.getOwnedAbility().getAbility().getName() + ChatColor.GRAY +  " Ур." + loadedAbility.activeLevel;
    }
}
