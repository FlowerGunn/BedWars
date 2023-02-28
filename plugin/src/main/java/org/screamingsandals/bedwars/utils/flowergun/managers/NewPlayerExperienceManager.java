package org.screamingsandals.bedwars.utils.flowergun.managers;

import net.royawesome.jlibnoise.module.combiner.Min;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.abilities_impl.*;

import java.util.ArrayList;

public class NewPlayerExperienceManager {

    public static ArrayList<Class> starterAbilities;
    public static ArrayList<Class> tournamentRewardAbilities;
    public static ArrayList<String> tournamentParticipants;

    static {
        starterAbilities = new ArrayList<>();
        starterAbilities.add(Appleperson.class); //economy
        starterAbilities.add(Endurance.class); //tank
        starterAbilities.add(Lumberjack.class); //haste and economy
        starterAbilities.add(Cheers.class); //heal
        starterAbilities.add(Greediness.class); //speed
        starterAbilities.add(DefensiveStance.class); //tank
        starterAbilities.add(Weaponsmaster.class); //damage
        starterAbilities.add(WoolManiac.class); //economy
        starterAbilities.add(Miner.class); //economy

        tournamentRewardAbilities = new ArrayList<>();
        tournamentRewardAbilities.add( Appleperson.class );
        tournamentRewardAbilities.add( DefensiveStance.class );
        tournamentRewardAbilities.add( Endurance.class );
        tournamentRewardAbilities.add( Friendlyness.class );
        tournamentRewardAbilities.add( FrostKiss.class );
        tournamentRewardAbilities.add( Greediness.class );
        tournamentRewardAbilities.add( Levitator.class );
        tournamentRewardAbilities.add( Lumberjack.class );
        tournamentRewardAbilities.add( Miner.class );
        tournamentRewardAbilities.add( Tempered.class );
        tournamentRewardAbilities.add( Trader.class );
        tournamentRewardAbilities.add( Weaponsmaster.class );
        tournamentRewardAbilities.add( WoolManiac.class );

        tournamentParticipants = new ArrayList<>();
        tournamentParticipants.add("_Amitexingg_");
        tournamentParticipants.add("Myyzar");
        tournamentParticipants.add("Mats_brony");
        tournamentParticipants.add("Velvi");
        tournamentParticipants.add("Goethessa");
        tournamentParticipants.add("DULEON");
        tournamentParticipants.add("FlowerGun");
        tournamentParticipants.add("Dqlloz");
        tournamentParticipants.add("PLLGG");
        tournamentParticipants.add("Killer_Z");
        tournamentParticipants.add("StrombleX");
        tournamentParticipants.add("Reincarnation");
        tournamentParticipants.add("NatsuDragneel");
        tournamentParticipants.add("richardbos95");
        tournamentParticipants.add("KoCmu4eskuy");
        tournamentParticipants.add("Kot_is_neolita");
        tournamentParticipants.add("Crab_Salad");
        tournamentParticipants.add("PikON190");
        tournamentParticipants.add("TheLone1yyy");
        tournamentParticipants.add("Dagerofff");
        tournamentParticipants.add("Ka4vnik");
        tournamentParticipants.add("Kabiass");
        tournamentParticipants.add("SogooD");
    }

    public static void loadPlayerStarterGoodies(GamePlayer gamePlayer) {

        for ( Class clazz : starterAbilities ) {
            IAbility ability = Ability.generateAbility(clazz);

//            Bukkit.getConsoleSender().sendMessage("starter abilities " + ability.getName() +  "  " + ability.getId());
            if (!gamePlayer.getOwnedAbilityById(ability.getId()).isOwned()) {
//                Bukkit.getConsoleSender().sendMessage("starter abilities not owned");
                Main.getAbilitiesManager().giveAbilityToById(gamePlayer.player.getUniqueId(), ability.getId(), 1);
            }

        }

        if (tournamentParticipants.contains(gamePlayer.player.getName()))
        for ( Class clazz : tournamentRewardAbilities ) {
            IAbility ability = Ability.generateAbility(clazz);

//            Bukkit.getConsoleSender().sendMessage("starter abilities " + ability.getName() +  "  " + ability.getId());
            if (!gamePlayer.getOwnedAbilityById(ability.getId()).isOwned()) {
//                Bukkit.getConsoleSender().sendMessage("starter abilities not owned");
                Main.getAbilitiesManager().giveAbilityToById(gamePlayer.player.getUniqueId(), ability.getId(), 1);
            }

        }

    }

}
