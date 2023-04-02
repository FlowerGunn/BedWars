package org.screamingsandals.bedwars.utils.flowergun.customobjects;

import net.royawesome.jlibnoise.module.combiner.Min;
import org.bukkit.Bukkit;
import org.screamingsandals.bedwars.utils.flowergun.abilities_impl.*;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

import java.util.ArrayList;

public class RecipeBook {

    public static ArrayList<ForgeRecipe> globalRecipes = new ArrayList<>();

    static {
        try {
            globalRecipes.add(new ForgeRecipe("copper_ore_refine", "Переплавка Медной Руды", new ResourceBundle().addResource(new Resource(ResourceType.RAW_COPPER, 40)).addResource(new Resource(ResourceType.COAL, 6)), new ResourceBundle().addResource(new Resource(ResourceType.COPPER, 5))).setTimeInSeconds(10));
            globalRecipes.add(new ForgeRecipe("iron_ore_refine", "Переплавка Железной Руды", new ResourceBundle().addResource(new Resource(ResourceType.RAW_IRON, 30)).addResource(new Resource(ResourceType.COAL, 8)), new ResourceBundle().addResource(new Resource(ResourceType.IRON, 5))).setTimeInSeconds(30));
            globalRecipes.add(new ForgeRecipe("gold_ore_refine", "Переплавка Золотой Руды", new ResourceBundle().addResource(new Resource(ResourceType.RAW_GOLD, 20)).addResource(new Resource(ResourceType.BLAZE_POWDER, 10)), new ResourceBundle().addResource(new Resource(ResourceType.GOLD, 5))).setTimeInMinutes(1));
            globalRecipes.add(new ForgeRecipe("magic_dust", "Замешивание Загадочной Пыли", new ResourceBundle().addResource(new Resource(ResourceType.BONE, 10)).addResource(new Resource(ResourceType.QUARTZ, 8)).addResource(new Resource(ResourceType.SCRAP, 10)).addResource(new Resource(ResourceType.GLOW_INK_SAC, 10)), new ResourceBundle().addResource(new Resource(ResourceType.MAGIC_DUST, 10))).setTimeInMinutes(1));
            globalRecipes.add(new ForgeRecipe("bone_plate", "Ковка Костяной Пластины", new ResourceBundle().addResource(new Resource(ResourceType.BONE, 40)).addResource(new Resource(ResourceType.QUARTZ, 20)).addResource(new Resource(ResourceType.SCRAP, 25)).addResource(new Resource(ResourceType.COPPER, 25)), new ResourceBundle().addResource(new Resource(ResourceType.BONE_PLATE, 4))).setTimeInMinutes(3));
            globalRecipes.add(new ForgeRecipe("thick_slime", "Сгущение Слизи", new ResourceBundle().addResource(new Resource(ResourceType.SLIMEBALL, 30)).addResource(new Resource(ResourceType.ICE_POWDER, 10)).addResource(new Resource(ResourceType.MAGIC_DUST, 3)), new ResourceBundle().addResource(new Resource(ResourceType.THICK_SLIME, 8))).setTimeInMinutes(1));
            globalRecipes.add(new ForgeRecipe("cryo_slime", "Сгущение Криослайма", new ResourceBundle().addResource(new Resource(ResourceType.THICK_SLIME, 15)).addResource(new Resource(ResourceType.ICE_POWDER, 50)).addResource(new Resource(ResourceType.ECHO_DUST, 3)).addResource(new Resource(ResourceType.BLAZE_POWDER, 20)), new ResourceBundle().addResource(new Resource(ResourceType.CRYO_SLIME, 6))).setTimeInMinutes(8));
            globalRecipes.add(new ForgeRecipe("magic_silk", "Плетение Загадочной Ткани", new ResourceBundle().addResource(new Resource(ResourceType.PAPER, 8)).addResource(new Resource(ResourceType.SILK_COCOON, 120)).addResource(new Resource(ResourceType.MAGIC_DUST, 8)), new ResourceBundle().addResource(new Resource(ResourceType.MAGIC_SILK, 3))).setTimeInMinutes(3));
            globalRecipes.add(new ForgeRecipe("cursed_silk", "Плетение Пустотной Ткани", new ResourceBundle().addResource(new Resource(ResourceType.MAGIC_SILK, 5)).addResource(new Resource(ResourceType.ECHO_DUST, 10)).addResource(new Resource(ResourceType.AMETHYST_SHARD, 20)), new ResourceBundle().addResource(new Resource(ResourceType.CURSED_SILK, 3))).setTimeInMinutes(8));

            globalRecipes.add(new ForgeRecipe("copper_plate", "Ковка Медной Пластины", new ResourceBundle().addResource(new Resource(ResourceType.BONE_PLATE, 3)).addResource(new Resource(ResourceType.COPPER, 16)).addResource(new Resource(ResourceType.IRON, 6)).addResource(new Resource(ResourceType.BLAZE_POWDER, 8)), new ResourceBundle().addResource(new Resource(ResourceType.COPPER_PLATE, 4))).setTimeInMinutes(4));
            globalRecipes.add(new ForgeRecipe("iron_plate", "Ковка Железной Пластины", new ResourceBundle().addResource(new Resource(ResourceType.BONE_PLATE, 5)).addResource(new Resource(ResourceType.IRON, 12)).addResource(new Resource(ResourceType.BOLT, 4)).addResource(new Resource(ResourceType.COPPER_PLATE, 3)), new ResourceBundle().addResource(new Resource(ResourceType.IRON_PLATE, 4))).setTimeInMinutes(8));
            globalRecipes.add(new ForgeRecipe("bolt", "Ковка Болтов", new ResourceBundle().addResource(new Resource(ResourceType.COPPER, 50)).addResource(new Resource(ResourceType.COPPER_PLATE, 10)).addResource(new Resource(ResourceType.LEATHER, 40)), new ResourceBundle().addResource(new Resource(ResourceType.BOLT, 20))));
            globalRecipes.add(new ForgeRecipe("lapis_sheet", "Ковка Листов Лазурита", new ResourceBundle().addResource(new Resource(ResourceType.QUARTZ, 40)).addResource(new Resource(ResourceType.LAPIS, 20)).addResource(new Resource(ResourceType.AMETHYST_SHARD, 15)), new ResourceBundle().addResource(new Resource(ResourceType.LAPIS_SHEET, 6))));
            globalRecipes.add(new ForgeRecipe("gold_sheet", "Ковка Золотых Листов", new ResourceBundle().addResource(new Resource(ResourceType.GOLD, 20)).addResource(new Resource(ResourceType.IRON_PLATE, 5)).addResource(new Resource(ResourceType.LAPIS_SHEET, 4)).addResource(new Resource(ResourceType.REINFORCED_BONE_PLATE, 1)), new ResourceBundle().addResource(new Resource(ResourceType.GOLD_SHEET, 4))).setTimeInMinutes(12));
            globalRecipes.add(new ForgeRecipe("reinforced_bone_plate", "Ковка Усиленных Костяных Пластин", new ResourceBundle().addResource(new Resource(ResourceType.BONE, 100)).addResource(new Resource(ResourceType.BONE_PLATE, 10)).addResource(new Resource(ResourceType.MAGIC_DUST, 12)).addResource(new Resource(ResourceType.IRON, 32)).addResource(new Resource(ResourceType.IRON_PLATE, 5)).addResource(new Resource(ResourceType.BOLT, 12)), new ResourceBundle().addResource(new Resource(ResourceType.REINFORCED_BONE_PLATE, 4))).setTimeInMinutes(15));

            globalRecipes.add(new ForgeRecipe("void_dust", "Расщепление Камней Пустоты в Пыль", new ResourceBundle().addResource(new Resource(ResourceType.ECHO_SHARD, 10)).addResource(new Resource(ResourceType.GLOW_INK_SAC, 15)).addResource(new Resource(ResourceType.LAPIS, 8)), new ResourceBundle().addResource(new Resource(ResourceType.ECHO_DUST, 5))).setTimeInMinutes(4));
            globalRecipes.add(new ForgeRecipe("emerald_dust", "Расщепление Изумрудов в Пыль", new ResourceBundle().addResource(new Resource(ResourceType.AMETHYST_SHARD, 8)).addResource(new Resource(ResourceType.ECHO_DUST, 8)).addResource(new Resource(ResourceType.EMERALD, 20)), new ResourceBundle().addResource(new Resource(ResourceType.EMERALD_DUST, 5))).setTimeInMinutes(10));
            globalRecipes.add(new ForgeRecipe("emerald_plate", "Ковка Изумрудной Пластины", new ResourceBundle().addResource(new Resource(ResourceType.LAPIS_SHEET, 20)).addResource(new Resource(ResourceType.MAGIC_DUST, 16)).addResource(new Resource(ResourceType.ICE_POWDER, 24)).addResource(new Resource(ResourceType.EMERALD_DUST, 8)).addResource(new Resource(ResourceType.EMERALD, 30)), new ResourceBundle().addResource(new Resource(ResourceType.EMERALD_PLATE, 4))).setTimeInMinutes(15));

            globalRecipes.add(new ForgeRecipe("steel", "Выплавка Стали", new ResourceBundle().addResource(new Resource(ResourceType.IRON, 100)).addResource(new Resource(ResourceType.IRON_PLATE, 10)).addResource(new Resource(ResourceType.COAL, 200)).addResource(new Resource(ResourceType.BLAZE_POWDER, 25)), new ResourceBundle().addResource(new Resource(ResourceType.STEEL_INGOT, 10))).setTimeInMinutes(20));
            globalRecipes.add(new ForgeRecipe("steel_plate", "Ковка Стальной Пластины", new ResourceBundle().addResource(new Resource(ResourceType.STEEL_INGOT, 32)).addResource(new Resource(ResourceType.BLAZE_POWDER, 40)).addResource(new Resource(ResourceType.GOLD_SHEET, 4)).addResource(new Resource(ResourceType.LAPIS_SHEET, 10)), new ResourceBundle().addResource(new Resource(ResourceType.STEEL_PLATE, 4))).setTimeInMinutes(30));
            globalRecipes.add(new ForgeRecipe("component1", "Ковка Странной Детальки", new ResourceBundle().addResource(new Resource(ResourceType.LEATHER, 30)).addResource(new Resource(ResourceType.BOLT, 3)).addResource(new Resource(ResourceType.IRON_PLATE, 1)).addResource(new Resource(ResourceType.MAGIC_SILK, 10)).addResource(new Resource(ResourceType.STEEL_INGOT, 5)).addResource(new Resource(ResourceType.STEEL_PLATE, 1)), new ResourceBundle().addResource(new Resource(ResourceType.COMPONENT1, 1))).setTimeInMinutes(40));
            globalRecipes.add(new ForgeRecipe("chain", "Ковка Цепей", new ResourceBundle().addResource(new Resource(ResourceType.IRON, 50)).addResource(new Resource(ResourceType.QUARTZ, 60)).addResource(new Resource(ResourceType.BLAZE_POWDER, 10)).addResource(new Resource(ResourceType.ICE_POWDER, 30)), new ResourceBundle().addResource(new Resource(ResourceType.CHAIN, 10))).setTimeInMinutes(20));

            globalRecipes.add(new ForgeRecipe("ruby_polish", "Полировка Рубинов", new ResourceBundle().addResource(new Resource(ResourceType.MAGIC_SILK, 3)).addResource(new Resource(ResourceType.BONE_PLATE, 3)).addResource(new Resource(ResourceType.THICK_SLIME, 10)).addResource(new Resource(ResourceType.LEATHER, 40)).addResource(new Resource(ResourceType.RUBY, 20)), new ResourceBundle().addResource(new Resource(ResourceType.POLISHED_RUBY, 5))).setTimeInMinutes(5));

            globalRecipes.add(new ForgeRecipe("exp1-2", "Конденсация Опыта", new ResourceBundle().addResource(new Resource(ResourceType.EXP_CRYSTAL_LVL1, 100)), new ResourceBundle().addResource(new Resource(ResourceType.EXP_CRYSTAL_LVL2, 1))).setTimeInHours(20));
            globalRecipes.add(new ForgeRecipe("exp2-3", "Конденсация Гигантского Опыта", new ResourceBundle().addResource(new Resource(ResourceType.EXP_CRYSTAL_LVL2, 100)), new ResourceBundle().addResource(new Resource(ResourceType.EXP_CRYSTAL_LVL3, 1))).setTimeInHours(72));

            globalRecipes.add(new ForgeRecipe("semiconductors", "Изготовление Полупроводников", new ResourceBundle().addResource(new Resource(ResourceType.LAPIS_SHEET, 40)).addResource(new Resource(ResourceType.GLUE, 4)).addResource(new Resource(ResourceType.RUBY_LAMP, 3)).addResource(new Resource(ResourceType.COMPONENT1, 2)), new ResourceBundle().addResource(new Resource(ResourceType.SEMICONDUCTOR, 3))).setTimeInHours(8));
            globalRecipes.add(new ForgeRecipe("glue", "Изготовление Клея", new ResourceBundle().addResource(new Resource(ResourceType.THICK_SLIME, 20)).addResource(new Resource(ResourceType.PAPER, 40)).addResource(new Resource(ResourceType.MAGIC_DUST, 30)).addResource(new Resource(ResourceType.EMERALD_DUST, 16)), new ResourceBundle().addResource(new Resource(ResourceType.GLUE, 10))).setTimeInHours(2));
            globalRecipes.add(new ForgeRecipe("ruby_lamp", "Изготовление Рубиновых Ламп", new ResourceBundle().addResource(new Resource(ResourceType.COPPER_PLATE, 10)).addResource(new Resource(ResourceType.COMPONENT1, 4)).addResource(new Resource(ResourceType.POLISHED_RUBY, 12)), new ResourceBundle().addResource(new Resource(ResourceType.RUBY_LAMP, 6))).setTimeInHours(4));
            globalRecipes.add(new ForgeRecipe("microprocessor", "Сборка Микропроцессоров", new ResourceBundle().addResource(new Resource(ResourceType.LAPIS_SHEET, 40)).addResource(new Resource(ResourceType.GOLD_SHEET, 10)).addResource(new Resource(ResourceType.SEMICONDUCTOR, 5)).addResource(new Resource(ResourceType.RUBY_LAMP, 1)), new ResourceBundle().addResource(new Resource(ResourceType.MICROSCHEMA, 2))).setTimeInHours(12));
            globalRecipes.add(new ForgeRecipe("processing_unit", "Сборка Вычислительных Единиц", new ResourceBundle().addResource(new Resource(ResourceType.GOLD_SHEET, 50)).addResource(new Resource(ResourceType.NETHERITE_SCRAP, 60)).addResource(new Resource(ResourceType.RUBY_LAMP, 20)).addResource(new Resource(ResourceType.SEMICONDUCTOR, 10)).addResource(new Resource(ResourceType.MICROSCHEMA, 5)), new ResourceBundle().addResource(new Resource(ResourceType.PROCESSING_UNIT, 5))).setTimeInHours(48));
            globalRecipes.add(new ForgeRecipe("netherite_plate", "Ковка Незеритовых Пластин", new ResourceBundle().addResource(new Resource(ResourceType.GOLD_SHEET, 25)).addResource(new Resource(ResourceType.NETHERITE_SCRAP, 100)).addResource(new Resource(ResourceType.REINFORCED_BONE_PLATE, 15)).addResource(new Resource(ResourceType.BOLT, 25)).addResource(new Resource(ResourceType.EMERALD_PLATE, 20)), new ResourceBundle().addResource(new Resource(ResourceType.NETHERITE_PLATE, 4))).setTimeInHours(48));
            globalRecipes.add(new ForgeRecipe("anomaly", "Алхимичсекий Вывод Аномалии", new ResourceBundle().addResource(new Resource(ResourceType.BLAZE_POWDER, 50)).addResource(new Resource(ResourceType.CRYO_SLIME, 20)).addResource(new Resource(ResourceType.THICK_SLIME, 40)).addResource(new Resource(ResourceType.MAGIC_DUST, 35)).addResource(new Resource(ResourceType.ECHO_DUST, 25)), new ResourceBundle().addResource(new Resource(ResourceType.ANOMALY, 8))).setTimeInHours(24));
//            globalRecipes.add(new ForgeRecipe("", "", new ResourceBundle().addResource(new Resource(ResourceType.EXP_CRYSTAL_LVL2, 100)), new ResourceBundle().addResource(new Resource(ResourceType.EXP_CRYSTAL_LVL3, 1))).setTimeInHours(1));

//            globalRecipes.add(new ForgeRecipe("", "", new ResourceBundle().addResource(new Resource(ResourceType.BONE, 25)).addResource(new Resource(ResourceType.QUARTZ, 25)).addResource(new Resource(ResourceType.SCRAP, 25)).addResource(new Resource(ResourceType.COPPER, 25)), new ResourceBundle().addResource(new Resource(ResourceType.BONE_PLATE, 1))));

            globalRecipes.add(new ForgeRecipe("book_ingridient", "Материализация Книг Ингридиентов", new ResourceBundle().addResource(new Resource(ResourceType.LEATHER, 30)).addResource(new Resource(ResourceType.PAPER, 10)).addResource(new Resource(ResourceType.GLOW_INK_SAC, 10)), new ResourceBundle().addResource(new Resource(ResourceType.BOOK, 1))).setTimeInHours(1));
            globalRecipes.add(new ForgeRecipe("book_rare", "Материализация Книг Редких Способностей", new ResourceBundle().addResource(new Resource(ResourceType.BOOK, 1)).addResource(new Resource(ResourceType.MAGIC_SILK, 15)).addResource(new Resource(ResourceType.EMERALD_DUST, 10)).addResource(new Resource(ResourceType.CRYO_SLIME, 15)), new ResourceBundle().addResource(new Resource(ResourceType.WRITABLE_BOOK, 1))).setTimeInHours(2));
            globalRecipes.add(new ForgeRecipe("book_epic", "Материализация Книг Эпических Способностей", new ResourceBundle().addResource(new Resource(ResourceType.WRITABLE_BOOK, 1)).addResource(new Resource(ResourceType.COMPONENT1, 1)).addResource(new Resource(ResourceType.RUBY_LAMP, 1)), new ResourceBundle().addResource(new Resource(ResourceType.BOOK_EVIL1, 1))).setTimeInHours(8));
//            globalRecipes.add(new ForgeRecipe("", "", new ResourceBundle().addResource(new Resource(ResourceType.EXP_CRYSTAL_LVL1, 100)), new ResourceBundle().addResource(new Resource(ResourceType.BOOK, 1))).setTimeInMinutes(60));


//            globalRecipes.add(new ForgeRecipe("sniper",  new ResourceBundle().addResource(new Resource(ResourceType.IRON, 10)).addResource(new Resource(ResourceType.GOLD, 8)).addResource(new Resource(ResourceType.EMERALD, 3)), Appleperson.class));
//            globalRecipes.add(new ForgeRecipe("frost_kiss",  new ResourceBundle().addResource(new Resource(ResourceType.IRON, 20)).addResource(new Resource(ResourceType.GOLD, 5)).addResource(new Resource(ResourceType.EMERALD, 1)), Acrobatics.class));
//            globalRecipes.add(new ForgeRecipe("slimeshell",  new ResourceBundle().addResource(new Resource(ResourceType.IRON, 25)).addResource(new Resource(ResourceType.AMETHYST_SHARD, 2)).addResource(new Resource(ResourceType.EMERALD, 2)), Adrenaline.class));
            globalRecipes.add(new ForgeRecipe("Acrobatics", new ResourceBundle().addResource(ResourceType.MAGIC_DUST, 4).addResource(ResourceType.ICE_POWDER, 10).addResource(ResourceType.SLIMEBALL, 20).addResource(ResourceType.CATALYST_RARE, 1).addResource(ResourceType.EXP_CRYSTAL_LVL2, 3) , Acrobatics.class).setTimeInHours(20));
            globalRecipes.add(new ForgeRecipe("Appleperson", new ResourceBundle().addResource(ResourceType.GOLD, 5).addResource(ResourceType.SILK_COCOON, 100).addResource(ResourceType.THICK_SLIME, 10).addResource(ResourceType.CATALYST_RARE, 1).addResource(ResourceType.EXP_CRYSTAL_LVL2, 3), Appleperson.class).setTimeInHours(20));
            globalRecipes.add(new ForgeRecipe("BigBoy", new ResourceBundle().addResource(ResourceType.SCRAP, 150).addResource(ResourceType.BOLT, 15).addResource(ResourceType.POLISHED_RUBY, 20).addResource(ResourceType.CATALYST_RARE, 1).addResource(ResourceType.EXP_CRYSTAL_LVL2, 3), BigBoy.class).setTimeInHours(20));
            globalRecipes.add(new ForgeRecipe("Cheers", new ResourceBundle().addResource(ResourceType.CHAIN, 10).addResource(ResourceType.POLISHED_RUBY, 10).addResource(ResourceType.RUBY, 20).addResource(ResourceType.CATALYST_RARE, 1).addResource(ResourceType.EXP_CRYSTAL_LVL2, 3), Cheers.class).setTimeInHours(20));
            globalRecipes.add(new ForgeRecipe("Compassion", new ResourceBundle().addResource(ResourceType.LAPIS, 20).addResource(ResourceType.ANOMALY, 4).addResource(ResourceType.RUBY, 50).addResource(ResourceType.CATALYST_RARE, 1).addResource(ResourceType.EXP_CRYSTAL_LVL2, 3), Compassion.class).setTimeInHours(20));
            globalRecipes.add(new ForgeRecipe("DefensiveStance", new ResourceBundle().addResource(ResourceType.LAPIS_SHEET, 10).addResource(ResourceType.POLISHED_RUBY, 15).addResource(ResourceType.THICK_SLIME, 10).addResource(ResourceType.CATALYST_RARE, 1).addResource(ResourceType.EXP_CRYSTAL_LVL2, 3), DefensiveStance.class).setTimeInHours(20));
            globalRecipes.add(new ForgeRecipe("EagleEye", new ResourceBundle().addResource(ResourceType.AMETHYST_SHARD, 35).addResource(ResourceType.RUBY, 20).addResource(ResourceType.ECHO_SHARD, 15).addResource(ResourceType.CATALYST_RARE, 1).addResource(ResourceType.EXP_CRYSTAL_LVL2, 3), EagleEye.class).setTimeInHours(20));
            globalRecipes.add(new ForgeRecipe("Endurance", new ResourceBundle().addResource(ResourceType.NETHERITE_SCRAP, 15).addResource(ResourceType.POLISHED_RUBY, 12).addResource(ResourceType.BLAZE_POWDER, 30).addResource(ResourceType.CATALYST_RARE, 1).addResource(ResourceType.EXP_CRYSTAL_LVL2, 3), Endurance.class).setTimeInHours(20));
            globalRecipes.add(new ForgeRecipe("Evasion", new ResourceBundle().addResource(ResourceType.MAGIC_SILK, 15).addResource(ResourceType.GOLD_SHEET, 3).addResource(ResourceType.SEMICONDUCTOR, 6).addResource(ResourceType.CATALYST_RARE, 1).addResource(ResourceType.EXP_CRYSTAL_LVL2, 3), Evasion.class).setTimeInHours(20));
            globalRecipes.add(new ForgeRecipe("Friendlyness", new ResourceBundle().addResource(ResourceType.IRON, 25).addResource(ResourceType.GOLD, 20).addResource(ResourceType.POLISHED_RUBY, 15).addResource(ResourceType.CATALYST_RARE, 1).addResource(ResourceType.EXP_CRYSTAL_LVL2, 3), Friendlyness.class).setTimeInHours(20));
            globalRecipes.add(new ForgeRecipe("FrostKiss", new ResourceBundle().addResource(ResourceType.IRON, 30).addResource(ResourceType.CRYO_SLIME, 10).addResource(ResourceType.ICE_POWDER, 40).addResource(ResourceType.CATALYST_RARE, 1).addResource(ResourceType.EXP_CRYSTAL_LVL2, 3), FrostKiss.class).setTimeInHours(20));
            globalRecipes.add(new ForgeRecipe("Greediness", new ResourceBundle().addResource(ResourceType.SCRAP, 30).addResource(ResourceType.GOLD, 10).addResource(ResourceType.EMERALD, 10).addResource(ResourceType.CATALYST_RARE, 1).addResource(ResourceType.EXP_CRYSTAL_LVL2, 3), Greediness.class).setTimeInHours(20));
            globalRecipes.add(new ForgeRecipe("GuardianAngel", new ResourceBundle().addResource(ResourceType.NETHERITE_SCRAP, 10).addResource(ResourceType.EMERALD_PLATE, 5).addResource(ResourceType.RUBY, 50).addResource(ResourceType.CATALYST_RARE, 1).addResource(ResourceType.EXP_CRYSTAL_LVL2, 3), GuardianAngel.class).setTimeInHours(20));
            globalRecipes.add(new ForgeRecipe("Levitator", new ResourceBundle().addResource(ResourceType.MAGIC_DUST, 10).addResource(ResourceType.ECHO_DUST, 10).addResource(ResourceType.LAPIS, 40).addResource(ResourceType.CATALYST_RARE, 1).addResource(ResourceType.EXP_CRYSTAL_LVL2, 3), Levitator.class).setTimeInHours(20));
            globalRecipes.add(new ForgeRecipe("Lumberjack", new ResourceBundle().addResource(ResourceType.QUARTZ, 30).addResource(ResourceType.SLIMEBALL, 40).addResource(ResourceType.COPPER_PLATE, 10).addResource(ResourceType.CATALYST_RARE, 1).addResource(ResourceType.EXP_CRYSTAL_LVL2, 3), Lumberjack.class).setTimeInHours(20));
            globalRecipes.add(new ForgeRecipe("MagmaSkin", new ResourceBundle().addResource(ResourceType.NETHERITE_SCRAP, 10).addResource(ResourceType.STEEL_INGOT, 15).addResource(ResourceType.BLAZE_POWDER, 40).addResource(ResourceType.CATALYST_RARE, 1).addResource(ResourceType.EXP_CRYSTAL_LVL2, 3), MagmaSkin.class).setTimeInHours(20));
            globalRecipes.add(new ForgeRecipe("Miner", new ResourceBundle().addResource(ResourceType.RAW_COPPER, 150).addResource(ResourceType.RAW_IRON, 100).addResource(ResourceType.RAW_GOLD, 50).addResource(ResourceType.CATALYST_RARE, 1).addResource(ResourceType.EXP_CRYSTAL_LVL2, 3), Miner.class).setTimeInHours(20));
            globalRecipes.add(new ForgeRecipe("Shrapnel", new ResourceBundle().addResource(ResourceType.SCRAP, 50).addResource(ResourceType.NETHERITE_SCRAP, 8).addResource(ResourceType.BOLT, 12).addResource(ResourceType.CATALYST_RARE, 1).addResource(ResourceType.EXP_CRYSTAL_LVL2, 3), Shrapnel.class).setTimeInHours(20));
            globalRecipes.add(new ForgeRecipe("Squire", new ResourceBundle().addResource(ResourceType.COPPER_PLATE, 10).addResource(ResourceType.LEATHER, 30).addResource(ResourceType.SILK_COCOON, 50).addResource(ResourceType.CATALYST_RARE, 1).addResource(ResourceType.EXP_CRYSTAL_LVL2, 3), Squire.class).setTimeInHours(20));
            globalRecipes.add(new ForgeRecipe("Tempered", new ResourceBundle().addResource(ResourceType.IRON_PLATE, 15).addResource(ResourceType.BLAZE_POWDER, 25).addResource(ResourceType.ICE_POWDER, 30).addResource(ResourceType.CATALYST_RARE, 1).addResource(ResourceType.EXP_CRYSTAL_LVL2, 3), Tempered.class).setTimeInHours(20));
            globalRecipes.add(new ForgeRecipe("Trader", new ResourceBundle().addResource(ResourceType.PAPER, 35).addResource(ResourceType.GLOW_INK_SAC, 20).addResource(ResourceType.GOLD, 30).addResource(ResourceType.CATALYST_RARE, 1).addResource(ResourceType.EXP_CRYSTAL_LVL2, 3), Trader.class).setTimeInHours(20));
            globalRecipes.add(new ForgeRecipe("Weaponsmaster", new ResourceBundle().addResource(ResourceType.BONE, 80).addResource(ResourceType.CHAIN, 5).addResource(ResourceType.IRON, 20).addResource(ResourceType.CATALYST_RARE, 1).addResource(ResourceType.EXP_CRYSTAL_LVL2, 3), Weaponsmaster.class).setTimeInHours(20));
            globalRecipes.add(new ForgeRecipe("WoolManiac", new ResourceBundle().addResource(ResourceType.SILK_COCOON, 250).addResource(ResourceType.SCRAP, 40).addResource(ResourceType.COAL, 50).addResource(ResourceType.CATALYST_RARE, 1).addResource(ResourceType.EXP_CRYSTAL_LVL2, 3), WoolManiac.class).setTimeInHours(20));

        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(e.getMessage());
            e.printStackTrace();
        }
    }

    public static ForgeRecipe getRecipeById( String id ) {
        for ( ForgeRecipe forgeRecipe : globalRecipes ) {
            if ( forgeRecipe.getId().equals(id) ) return forgeRecipe;
        }
        return null;
    }

}
