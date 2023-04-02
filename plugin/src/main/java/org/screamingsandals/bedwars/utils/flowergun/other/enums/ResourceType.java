package org.screamingsandals.bedwars.utils.flowergun.other.enums;

import dev.lone.itemsadder.api.CustomStack;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.screamingsandals.bedwars.utils.flowergun.managers.NotificationManager;
import org.screamingsandals.bedwars.utils.flowergun.managers.RarityManager;

import java.util.ArrayList;
import java.util.Collection;

public enum ResourceType {
    SLIMEBALL( Material.SLIME_BALL, IconType.SLIMEBALL, "Слайм", 2, "Густая, неприятная к прикосновению субстанция./Встречается в густых блоках и субстанциях,/а также в некоторой органике."),
    AMETHYST_SHARD( Material.AMETHYST_SHARD, IconType.AMETHYST_SHARD, "Аметист", 2, "Хрупкие прозрачные фиолетовые камешки,/на первый взгляд не несущие особой пользы."),
    QUARTZ( Material.QUARTZ, IconType.QUARTZ, "Кварц", 2, "Полупрозрачные кристаллы с достаточно/остроыми и выраженными краями."),
    NETHERITE_SCRAP( Material.NETHERITE_SCRAP, IconType.NETHERITE_SCRAP, "Незеритовые Обломки", 3),
    PAPER( Material.PAPER, IconType.PAPER, "Бумага", 2),
    SPYGLASS( Material.SPYGLASS, IconType.SPYGLASS, "Подзорная Труба",  6),
    LEATHER( Material.LEATHER, IconType.LEATHER, "Кожа", 1, "Простой материал, награждающий жестокость/игроков арены против друг друга."),
    RAW_IRON( Material.RAW_IRON, IconType.RAW_IRON, "Железная Руда", 1),
    RAW_COPPER( Material.RAW_COPPER, IconType.RAW_COPPER, "Медная Руда", 1),
    RAW_GOLD( Material.RAW_GOLD, IconType.RAW_GOLD, "Золотая Руда", 2),
    GLOW_INK_SAC( Material.GLOW_INK_SAC, IconType.GLOW_INK_SAC, "Загадочные Чернила", 2, "Аморфный краситель, встречающийся в ярких/разноцветных товарах магазина./Возможно эти чернила можно использовать/в качестве красителя, однако данная/теория не была доказана или опровержена."),
    ENDER_EYE( Material.ENDER_EYE, IconType.ENDER_EYE, "Глаз Эндера", 4),
    CAKE( Material.CAKE, IconType.CAKE, "Торт", 4),
    BONE( Material.BONE, IconType.BONE, "Кость", 1, "Груда костей, оставшихся у вас/после ваших смертей на арене./Возможно существует способ собрать/и вражеские кости..."),
    GOLD( Material.GOLD_INGOT, IconType.GOLD_INGOT, "Золото", 3),
    IRON( Material.IRON_INGOT, IconType.IRON_INGOT, "Железо", 2),
    COPPER( Material.COPPER_INGOT, IconType.COPPER_INGOT, "Медь", 2),
    EMERALD(Material.EMERALD, IconType.EMERALD, "Изумруд", 3),
    COAL(Material.COAL, IconType.COAL, "Уголь", 2, "Важный материал для обработки руд,/награждаемый за покупку горящих и термически/обработанных предметов и блоков."),

    ENDER_PEARL(Material.ENDER_PEARL, IconType.ENDER_PEARL, "Эндер Жемчуг", 4),
    ECHO_SHARD(Material.ECHO_SHARD, IconType.ECHO_SHARD, "Осколок Пустоты", 3, "Смотря сквозь эти кристаллы,/каждый человек может увидеть отражения/их прошлого, мест где они бывали раньше,/медленно теряя запасы своего рассудка."),
    LAPIS(Material.LAPIS_LAZULI, IconType.LAPIS, "Лазурит", 2, "Исток этого камня остаётся загадкой..."),
    THICK_SLIME("anicloud:thick_slime", IconType.THICK_SLIME, "Густая Слизь", 2),
    STEEL_PLATE("anicloud:steel_plate", IconType.STEEL_PLATE, "Стальная Пластина", 4),
    STEEL_INGOT("anicloud:steel_ingot", IconType.STEEL_INGOT, "Стальной Слиток", 2),
    SILK_COCOON("anicloud:silk_cocoon", IconType.SILK_COCOON, "Пряжа", 1, "Находится"),
    RUBY("anicloud:ruby", IconType.RUBY, "Грубый Рубин", 2),
    RUBY_LAMP("anicloud:ruby_lamp", IconType.RUBY_LAMP, "Рубиновая Лампа", 5),
    REINFORCED_BONE_PLATE("anicloud:reinforced_bone_plate", IconType.REINFORCED_BONE_PLATE, "Укреплённая Костяная Пластина", 4),
    POLISHED_RUBY("anicloud:polished_ruby", IconType.POLISHED_RUBY, "Полированный Рубин", 2),
    NETHERITE_PLATE("anicloud:netherite_plate", IconType.NETHERITE_PLATE, "Незеритовая Пластина", 6),

    NETHERITE_INGOT( Material.NETHERITE_INGOT, IconType.NETHERITE_INGOT, "Незеритовый Слиток", 5),
    PROCESSING_UNIT("anicloud:motherboard", IconType.PROCESSING_UNIT, "Вычислительная Единица", 6),
    MICROSCHEMA("anicloud:microschema", IconType.MICROSCHEMA, "Микропроцессор", 5),
    MAGIC_SILK("anicloud:magic_silk", IconType.MAGIC_SILK, "Загадочная Ткань", 2),
    MAGIC_DUST("anicloud:magic_dust", IconType.MAGIC_DUST, "Загадочная Пыль", 2),
    //LOCK("anicloud:lock", IconType.LOCK, "Замок", 3),
    LAPIS_SHEET("anicloud:lapis_sheet", IconType.LAPIS_SHEET, "Лист из Лазурита", 2),
    SCRAP("anicloud:raw_iron_wood", IconType.SCRAP, "Металлолом", 1),
    IRON_PLATE("anicloud:iron_plate", IconType.IRON_PLATE, "Железная Пластина", 3),
    ICE_POWDER("anicloud:ice_powder", IconType.ICE_POWDER, "Ледяная Пыль", 3),
    GLUE("anicloud:glue", IconType.GLUE, "Клей", 5),
    EXP_CRYSTAL_LVL1("anicloud:exp_crystal_lvl1", IconType.EXP_CRYSTAL_LVL1, "Знания Воинов", 3),
    EXP_CRYSTAL_LVL2("anicloud:exp_crystal_lvl2", IconType.EXP_CRYSTAL_LVL2, "Знания Героев", 4),
    EXP_CRYSTAL_LVL3("anicloud:exp_crystal_lvl3", IconType.EXP_CRYSTAL_LVL3, "Знания Легенд", 5),
    EXOPLATE("anicloud:exoplate", IconType.EXOPLATE, "Экзопластина", 3),
    ECHO_DUST("anicloud:ender_pearl_dust", IconType.ENDER_PEARL_DUST, "Пустотная Пыль", 4),
    EMERALD_PLATE("anicloud:emerald_plate", IconType.EMERALD_PLATE, "Изумрудная Пластина", 5),
    EMERALD_DUST("anicloud:emerald_dust", IconType.EMERALD_DUST, "Изумрудная Пыль", 4),
    CURSED_SILK("anicloud:cursed_silk", IconType.CURSED_SILK, "Пустотная Ткань", 3),
    CRYO_SLIME("anicloud:cryo_slime", IconType.CRYO_SLIME, "Криослайм", 3),
    COPPER_PLATE("anicloud:copper_plate", IconType.COPPER_PLATE, "Медная Пластина", 3),
    COMPONENT1("anicloud:component1", IconType.COMPONENT1, "Странная Деталька", 4),
    CHAIN("anicloud:chain", IconType.CHAIN, "Цепь", 3),
    CATALYST_RARE("anicloud:catalyst_rare", IconType.CATALYST_RARE, "Редкий Катализатор", 3),
    CATALYST_EPIC("anicloud:catalyst_epic", IconType.CATALYST_EPIC, "Эпический Катализатор", 4),
    CATALYST_LEGENDARY("anicloud:catalyst_legendary", IconType.CATALYST_LEGENDARY, "Легендарный Катализатор", 5),
    BOOK_SAFE1("anicloud:book_safe1", IconType.BOOK_SAFE1, "Книга Загадок", 4),
    BOOK_SAFE2("anicloud:book_safe2", IconType.BOOK_SAFE2, "Книга Секретов", 5),
    BOOK_EVIL1("anicloud:book_evil1", IconType.BOOK_EVIL1, "Древняя Книга", 4),
    BOOK_EVIL2("anicloud:book_evil2", IconType.BOOK_EVIL2, "Книга Хаоса", 5),
    BOOK(Material.BOOK, IconType.BOOK, "Обычная книга", 2),
    WRITABLE_BOOK(Material.WRITABLE_BOOK, IconType.WRITABLE_BOOK, "Научная книга", 3),
    BONE_PLATE("anicloud:bone_plate", IconType.BONE_PLATE, "Костяная Пластина", 2),
    BOLT("anicloud:bolt", IconType.BOLT, "Болт", 3),
    ANOMALY("anicloud:anomaly", IconType.ANOMALY, "Аномалия", 4),
    SEMICONDUCTOR("anicloud:semiconductor", IconType.SEMICONDUCTOR, "Полупроводник", 5),
    GOLD_SHEET("anicloud:gold_sheet", IconType.GOLD_SHEET, "Золотой Лист", 4),
    BLAZE_POWDER( Material.BLAZE_POWDER, IconType.BLAZE_POWDER , "Огненный Порошок", 3 );

    @Getter
    private Material material;

    private String name;

    private String iaId;
    @Getter
    private IconType iconType;
    @Getter
    private int rarity;
    @Getter
    private int customModelData;
    private ArrayList<String> description;


    ResourceType(Material material, IconType iconType, String name, int rarity) {
        this(material, iconType, name, rarity, "");
    }

    ResourceType(Material material, IconType iconType, String name, int rarity, String description) {
        this.material = material;
        this.iconType = iconType;
        this.customModelData = 0;
        this.name = name;
        this.rarity = rarity;
        this.setupDescription( description );
    }


    ResourceType(String iaId, IconType iconType, String name, int rarity) {
//        CustomStack customStack = CustomStack.getInstance(iaId);
//        if (customStack != null) {
//            Bukkit.getConsoleSender().sendMessage("Found an item with iaId=" + iaId + " iconType=" + iconType.toString() );
//            this.material = customStack.getItemStack().getType();
//            this.customModelData = customStack.getItemStack().getItemMeta().getCustomModelData();
//        }
//        else {
//            Bukkit.getConsoleSender().sendMessage("Couldnt find an item with iaId=" + iaId + " iconType=" + iconType.toString() );
//            this.material = Material.WHITE_DYE;
//            this.customModelData = 0;
//        }

        this(iaId, iconType, name, rarity, "");
    }

    ResourceType(String iaId, IconType iconType, String name, int rarity, String description) {

        this.iaId = iaId;
        this.material = Material.WHITE_DYE;
        this.customModelData = 0;
        this.iconType = iconType;
        this.name = name;
        this.rarity = rarity;
        this.setupDescription(description);
    }

    private void setupDescription(String description) {
        this.description = new ArrayList<>();
        this.description.addAll(NotificationManager.divideAndParseLines(description, "/"));
    }

    public ItemStack getGuiItem() {

        ItemStack itemStack;
        if ( iaId != null ) {
            CustomStack customStack = CustomStack.getInstance(iaId);
            if ( customStack != null ) {
                itemStack = customStack.getItemStack();
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(this.getName());
                itemStack.setItemMeta(itemMeta);

                return itemStack;
            }
        }

        itemStack = new ItemStack(this.material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setCustomModelData(this.customModelData);
        itemMeta.setDisplayName(this.getName());
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public String getName() {
        return RarityManager.getRarityColour(this.rarity) + this.name;
    }

    public ArrayList<String> getDescription() {
        return this.description;
    }
}