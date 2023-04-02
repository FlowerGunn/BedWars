package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CustomStatusEffect;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.AbilityCategory;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

import java.util.ArrayList;

public class Radar extends Ability implements IAbility {

    public Radar(){//epic
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.COMPONENT1, 5).addResource(ResourceType.AMETHYST_SHARD, 30).addResource(ResourceType.BLAZE_POWDER, 20).addResource(ResourceType.CATALYST_EPIC, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.COMPONENT1, 15).addResource(ResourceType.AMETHYST_SHARD, 100).addResource(ResourceType.BLAZE_POWDER, 60).addResource(ResourceType.EXP_CRYSTAL_LVL2, 5);

        this.name = "Радар";
        this.id = "radar";
        this.item = Material.COMPASS;
        this.rarity = 4;
        this.icon = IconType.GLOWING;

        this.abilityCategories.add(AbilityCategory.MANIPULATOR);
        this.abilityCategories.add(AbilityCategory.FIGHTER);

        this.description = "Убийства и помощи в убийстве накладывают#Свечение и снижают макс.здоровье на 4 ед.#на (values1) секунд всем врагам#в радиусе (values2) блоков вокруг игрока.";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 60 + 60 * level;
    }

    @Override
    public int calculateIntValue2(int level) {
        return 20 + 5 * level;
    }


    @Override
    public String formatValue1(int level) {
        return "" + calculateIntValue1(level) / 20;
    }

    @Override
    public void playerKill(int level, Player victim, Player killer, PlayerDeathEvent event) {

        notifyPlayerOnAbilityActivation(killer);
        applyGlowAround(killer, level);

    };

    @Override
    public void playerKillAssist(int level, Player killer, Player victim, Player assistant) {

        notifyPlayerOnAbilityActivation(assistant);
        applyGlowAround(assistant, level);

    };

    private void applyGlowAround( Player player, int level ) {

        playFXBuff(player, 3);

        Game game = Main.getPlayerGameProfile(player).getGame();
        if ( game == null ) return;
        ArrayList<GamePlayer> players = new ArrayList<>(game.getConnectedGamePlayers());
        GamePlayer gPlayer = Main.getPlayerGameProfile(player);

        boolean playerFound = false;

        for ( GamePlayer gamePlayer : players ) {
            if ( !gamePlayer.isSpectator && game.getPlayerTeam(gamePlayer) != game.getPlayerTeam(gPlayer)) {
                double distance = gamePlayer.player.getLocation().distance(gPlayer.player.getLocation());
                if ( distance < calculateIntValue2(level) ) {
                    gamePlayer.player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, calculateIntValue1(level), 0, false, false ));
                    gamePlayer.addCustomStatusEffect(new CustomStatusEffect("radar_hp", gamePlayer, gamePlayer, Attribute.GENERIC_MAX_HEALTH, new CompoundValueModifier( -4, 0, 0), calculateIntValue1(level), true));
                    playFXDebuff(gamePlayer.player, 3);
                    playerFound = true;
                }
            }
        }

        if (playerFound) notifyPlayerOnAbilityActivation(player);


    }

}
