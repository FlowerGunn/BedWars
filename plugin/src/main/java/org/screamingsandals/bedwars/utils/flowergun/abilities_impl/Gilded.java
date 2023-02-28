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

    public class Gilded extends Ability implements IAbility{


        private int actualCooldown;

        public Gilded(){//epic
            this.disassembleResources = new ResourceBundle().addResource(ResourceType.CURSED_SILK, 10).addResource(ResourceType.COMPONENT1, 1).addResource(ResourceType.GOLD_SHEET, 5).addResource(ResourceType.CATALYST_EPIC, 1);
            this.upgradeResources = new ResourceBundle().addResource(ResourceType.CURSED_SILK, 30).addResource(ResourceType.COMPONENT1, 4).addResource(ResourceType.GOLD_SHEET, 15).addResource(ResourceType.EXP_CRYSTAL_LVL2, 5);

            this.name = "Позолота";
            this.id = "gilded";
            this.item = Material.GOLDEN_CHESTPLATE;
            //this.loadCustomItem("anicloud:gold_sheet");
            this.rarity = 4;
            this.icon = IconType.DAMAGE_RESISTANCE;
            this.description = "Игрок получает неломаемый золотой нагрудник#на Защиту от Снарядов (values1)&7#при первом спавне.";
        }

        @Override
        public int calculateIntValue1(int level) {
            return level;
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
//            itemMeta2.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
            itemMeta2.addEnchant(Enchantment.PROTECTION_PROJECTILE, calculateIntValue1(level), true);
    //        itemMeta2.addEnchant(Enchantment.BINDING_CURSE, 1, true);
            kit2.setItemMeta(itemMeta2);
            gamePlayer.player.getInventory().addItem(kit2);

        }
    }
