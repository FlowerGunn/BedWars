    package org.screamingsandals.bedwars.utils.flowergun.gameplay.abilities;


import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.Ability;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.tools.IconType;

    public class Miner extends Ability implements IAbility{


        private int actualCooldown;

        public Miner(){
            super();
            this.name = "Шахтёр";
            this.id = "miner";
            this.item = Material.STONE_PICKAXE;
            this.rarity = 3;
            this.icon = IconType.FAST_DIGGING;
            this.description = "Вы получаете неломаемую каменную#кирку на Эффективность (values1)&7#и (values2)&7 булыжника при первом спавне";
        }

        @Override
        public int calculateIntValue1(int level) {
            return level;
        }

        @Override
        public int calculateIntValue2(int level) {
            return 8 + 8 * level;
        }


    //    @Override
    //    public void playerRespawn(int level, GamePlayer gamePlayer) {
    //
    //    }

        @Override
        public void playerFirstSpawn(int level, GamePlayer gamePlayer) {

            ItemStack kit2 = new ItemStack(Material.STONE_PICKAXE);
            ItemMeta itemMeta2 = kit2.getItemMeta();
            itemMeta2.setUnbreakable(true);
            itemMeta2.addEnchant(Enchantment.DIG_SPEED, calculateIntValue1(level), true);
    //        itemMeta2.addEnchant(Enchantment.BINDING_CURSE, 1, true);
            kit2.setItemMeta(itemMeta2);
            gamePlayer.player.getInventory().addItem(kit2);

            ItemStack kit = new ItemStack(Material.COBBLESTONE);
            kit.setAmount( calculateIntValue2(level) );
            gamePlayer.player.getInventory().addItem(kit);

        }
    }
