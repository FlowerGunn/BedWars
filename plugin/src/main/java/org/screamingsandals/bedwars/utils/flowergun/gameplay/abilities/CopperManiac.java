package org.screamingsandals.bedwars.utils.flowergun.gameplay.abilities;


import org.bukkit.Material;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.Ability;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.IAbility;

public class CopperManiac extends Ability implements IAbility{


    private int actualCooldown;

    public CopperManiac(){
        super();
        this.name = "Медный Маньяк";
        this.id = "coppermaniac";
        this.icon = Material.valueOf("COPPER_INGOT");
        this.cooldownMilliseconds = 20000;
        this.rarity = 4;
        this.description = "Вы получаете (values1)&7 меди при первом спавне.#(values2)&7 при каждом последующем и#(values3)&7 за каждое убийство игрока.";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 16 + level * 8;
    }

    @Override
    public int calculateIntValue2(int level) {
        return 10 + level * 2;
    }

    @Override
    public int calculateIntValue3(int level) {
        return 10 + level * 2;
    }

    @Override
    public void playerKill(int level, PlayerDeathEvent event) {
        ItemStack reward = Main.getSpawnerType("bronze").getStack();
        reward.setAmount( calculateIntValue3(level) );
        event.getEntity().getKiller().getInventory().addItem(reward);
    }

    @Override
    public void playerRespawn(int level, GamePlayer gamePlayer) {

        ItemStack kit = Main.getSpawnerType("bronze").getStack();
        kit.setAmount( calculateIntValue2(level) );
        gamePlayer.player.getInventory().addItem(kit);

    }

    @Override
    public void playerFirstSpawn(int level, GamePlayer gamePlayer) {

        ItemStack kit = Main.getSpawnerType("bronze").getStack();
        kit.setAmount( calculateIntValue1(level) );
        gamePlayer.player.getInventory().addItem(kit);

    }
}
