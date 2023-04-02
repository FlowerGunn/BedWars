package org.screamingsandals.bedwars.utils.flowergun.other.enums;

import net.md_5.bungee.api.ChatColor;
import org.screamingsandals.bedwars.utils.flowergun.managers.ColoursManager;

public enum AbilityCategory {

    FIGHTER( "Боец", "Сражения на ближней дистанции, в самом центре передряги.", ColoursManager.red ), //MELEE
    KNIGHT( "Рыцарь", "Ближний бой с мечами.", ColoursManager.red ), //sword combat, damage dealer
    VIKING( "Викинг", "Ближний бой с топорами.", ColoursManager.red ), //axe combat

    TANK( "Танк", "Поглощение урона и контроль боевых ситуаций.", ColoursManager.yellow ), //MITIGATION
    BULLDOZER( "Будьдозер", "Контроль территории и инциация.", ColoursManager.yellow ), //

    RANGER( "Рейнджер", "Дальний бой на безопасном расстоянии.", ColoursManager.red ), //RANGED damage
    ARCHER( "Лучник", "Стрельба из луков и арбалетов.", ColoursManager.red ), //bow damage

    SUPPORT( "Поддержка", "Помощь команде как в сражениях, так и вне боя.", ColoursManager.green ), //SUPPORTING
    HEALER( "Лекарь", "Лечение союзников в те моменты когда еда им не поможет.", ColoursManager.green ), //team healing
    MANIPULATOR( "Манипулятор", "Усилиние боевых способностей союзников и издевательства над противниками.", ColoursManager.light_blue ), //buffing and debuffing
    MADMAN( "Бузумец", "Агрессивные сильные эффекты с риском для игрока.", ColoursManager.light_blue ), //sacrificial abilities

    ECONOMIST( "Экономист", "Управление потоком ресурсов в матче.", ColoursManager.light_blue ), //resource management

    SCOUT( "Скаут", "Передвижение по карте и давление на кровати противников.", ColoursManager.light_blue ), //pressure, movement and breaking beds

    DEMOLITIONIST( "Подрывник", "Взрывной урон и тотальное разрушение построек противников.", ColoursManager.red ), //explosive damage
    SNOWMAN( "Снеговик", "Дальний урон снежками.", ColoursManager.red ), //snowball damage
    PYROTECHNIC( "Пиротехник", "Взрывной дальний урон фейерверками.", ColoursManager.red ), //firework damage

    POSEIDON( "Посейдон", "Трезубцы и всё что можно с ними проворачивать.", ColoursManager.red ), //trident damage

    GUARDIAN( "Страж", "Контроль территории и защита позиции.", ColoursManager.yellow ), //AREA DEFENCE
    BUILDER( "Строитель", "Возведение тактических построек из блоков и гаджетов.", ColoursManager.light_blue ), //gadgets and blocks
    TRICKSTER( "Ловкач", "Погдотовка ловушек и гаджетов для неподозревающих противников.", ColoursManager.light_blue ), // gadgets, traps, setups

    ALCHEMIST( "Алхимик", "Зелья, зелья, зелья...", ColoursManager.light_blue ), // gadgets, traps, setups

    CLOWN( "Клоун", "Странные способности со спорной эффективностью."); // gadgets, traps, setups

    private final ChatColor color;
    private final String description;
    private final String name;

    AbilityCategory(String name, String description) {
        this.name = name;
        this.description = description;
        this.color = ColoursManager.orange;
    }

    AbilityCategory(String name, String description, ChatColor color ){
        this.name = name;
        this.description = description;
        this.color = color;
    }


    public ChatColor getColor() {
        return this.color;
    }

    public String getName() {
        return this.name;
    }
}
