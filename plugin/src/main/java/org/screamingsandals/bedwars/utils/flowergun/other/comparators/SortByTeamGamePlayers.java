package org.screamingsandals.bedwars.utils.flowergun.other.comparators;

import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.OwnedAbility;

import java.util.Comparator;

public class SortByTeamGamePlayers implements Comparator<GamePlayer> {

    @Override
    public int compare(GamePlayer gp1, GamePlayer gp2) {
        Game game = gp1.getGame();
        return game.getPlayerTeam(gp1).getName().equals(game.getPlayerTeam(gp2).getName()) ? 1 : 0;
    }
}
