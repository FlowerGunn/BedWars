    package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;


import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

    public class Miner extends Ability implements IAbility{


        private int actualCooldown;

        public Miner(){//rare
            this.disassembleResources = new ResourceBundle().addResource(ResourceType.RAW_COPPER, 150).addResource(ResourceType.RAW_IRON, 100).addResource(ResourceType.RAW_GOLD, 50).addResource(ResourceType.CATALYST_RARE, 1);
            this.upgradeResources = new ResourceBundle().addResource(ResourceType.RAW_COPPER, 300).addResource(ResourceType.RAW_IRON, 200).addResource(ResourceType.RAW_GOLD, 100).addResource(ResourceType.EXP_CRYSTAL_LVL2, 2);


            this.name = "Шахтёр";
            this.id = "miner";
            this.item = Material.STONE_PICKAXE;
            this.rarity = 3;
            this.icon = IconType.FAST_DIGGING;
            this.description = "Игрок получает неломаемую каменную кирку#на Эффективность (values1) и Остроту (values3),#и (values2) булыжника при первом спавне.";
        }

        @Override
        public int calculateIntValue1(int level) {
            return 3 + level;
        }

        @Override
        public int calculateIntValue2(int level) {
            return 8 + 8 * level;
        }

        @Override
        public int calculateIntValue3(int level) {
            return 1 + level;
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
            itemMeta2.addEnchant(Enchantment.DAMAGE_ALL, calculateIntValue3(level), true);
    //        itemMeta2.addEnchant(Enchantment.BINDING_CURSE, 1, true);
            kit2.setItemMeta(itemMeta2);
            gamePlayer.player.getInventory().addItem(kit2);

            ItemStack kit = new ItemStack(Material.COBBLESTONE);
            kit.setAmount( calculateIntValue2(level) );
            gamePlayer.player.getInventory().addItem(kit);

        }
    }
