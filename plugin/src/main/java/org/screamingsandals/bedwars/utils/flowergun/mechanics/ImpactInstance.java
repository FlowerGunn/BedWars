package org.screamingsandals.bedwars.utils.flowergun.mechanics;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;

public class ImpactInstance {

    public int countdownTimestamp;
    public int powerPoints;


    public ImpactType impactType;
    public ImpactPolarity impactPolarity;

    public GamePlayer owner;

    public ImpactInstance(GamePlayer player, ImpactType impactType, ImpactPolarity impactPolarity, int countdownTimestamp, int powerPoints) {
        this.impactPolarity = impactPolarity;
        this.impactType = impactType;
        this.countdownTimestamp = countdownTimestamp;
        this.powerPoints = powerPoints;
        this.owner = player;
    }

}
