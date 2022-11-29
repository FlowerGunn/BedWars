    package org.screamingsandals.bedwars.utils.flowergun.gameplay.abilities;


import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.Ability;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.tools.IconType;

    public class Gilded extends Ability implements IAbility{


        private int actualCooldown;

        public Gilded(){
            super();
            this.name = "Позолота";
            this.id = "gilded";
            this.item = Material.GOLDEN_CHESTPLATE;
            this.rarity = 4;
            this.icon = IconType.DAMAGE_RESISTANCE;
            this.description = "Вы получаете неломаемый золотой нагрудник#на Защиту 2 и Защиту от Снарядов (values1)&7#при первом спавне";
        }

        @Override
        public int calculateIntValue1(int level) {
            return 1 + level;
        }


    //    @Override
    //    public void playerRespawn(int level, GamePlayer gamePlayer) {
    //
    //    }

        @Override
        public void playerFirstSpawn(int level, GamePlayer gamePlayer) {

            ItemStack kit2 = new ItemStack(Material.GOLDEN_CHESTPLATE);
            ItemMeta itemMeta2 = kit2.getItemMeta();
            itemMeta2.setUnbreakable(true);
            itemMeta2.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
            itemMeta2.addEnchant(Enchantment.PROTECTION_PROJECTILE, calculateIntValue1(level), true);
    //        itemMeta2.addEnchant(Enchantment.BINDING_CURSE, 1, true);
            kit2.setItemMeta(itemMeta2);
            gamePlayer.player.getInventory().addItem(kit2);

        }
    }
