package org.screamingsandals.bedwars.utils.flowergun.managers;

import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.statistics.PlayerStatistic;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.LoadedAbility;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.OwnedAbility;
import org.screamingsandals.bedwars.utils.flowergun.abilities_impl.*;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.PlayerConfigType;

import java.util.ArrayList;
import java.util.Collections;

public class NewPlayerExperienceManager {

    public static ArrayList<Class> starterAbilities;
    public static ArrayList<Class> tournamentRewardAbilities;
    public static ArrayList<String> tournamentParticipants;

    public static ArrayList<String> tipsAndTricks;

    static {
        starterAbilities = new ArrayList<>();
        starterAbilities.add(Confidence.class);//melee ranged
        starterAbilities.add(Equipment.class);//scout eco
        starterAbilities.add(Stash.class);//eco melee
//        starterAbilities.add(Appleperson.class); //eco melee ranged
        starterAbilities.add(Endurance.class); //tank melee~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//        starterAbilities.add(Lumberjack.class); //scout
        starterAbilities.add(Cheers.class); //heal~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        starterAbilities.add(Greediness.class); //scout eco
//        starterAbilities.add(DefensiveStance.class); //tank ???
//        starterAbilities.add(Weaponsmaster.class); //ranged
        starterAbilities.add(WoolManiac.class); //eco manipulator~~~~~~~~~~~~~~~~~~~~~~~~
        starterAbilities.add(Miner.class); //scout


        //new
        starterAbilities.add(Compassion.class); //heal~~~~~~~~~~~~~~~~~~~~~
        starterAbilities.add(Tempered.class); //tank scout~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        starterAbilities.add(Friendlyness.class); //eco ranged
        starterAbilities.add(EagleEye.class); //ranged manipulator~~~~~~~~~~~~~~~~~~~~~~~~~
        starterAbilities.add(BigBoy.class); //tank melee~~~~~~~~~~~~~~~~~~~~~
        starterAbilities.add(FrostKiss.class); //ranged manipulator support~~~~~~~~~~~~~~~~~~~~~

//        tournamentRewardAbilities = new ArrayList<>();
//        tournamentRewardAbilities.add( Appleperson.class );
//        tournamentRewardAbilities.add( DefensiveStance.class );
//        tournamentRewardAbilities.add( Endurance.class );
//        tournamentRewardAbilities.add( Friendlyness.class );
//        tournamentRewardAbilities.add( FrostKiss.class );
//        tournamentRewardAbilities.add( Greediness.class );
//        tournamentRewardAbilities.add( Levitator.class );
//        tournamentRewardAbilities.add( Lumberjack.class );
//        tournamentRewardAbilities.add( Miner.class );
//        tournamentRewardAbilities.add( Tempered.class );
//        tournamentRewardAbilities.add( Trader.class );
//        tournamentRewardAbilities.add( Weaponsmaster.class );
//        tournamentRewardAbilities.add( WoolManiac.class );

//        tournamentParticipants = new ArrayList<>();
//        tournamentParticipants.add("_Amitexingg_");
//        tournamentParticipants.add("Myyzar");
//        tournamentParticipants.add("Mats_brony");
//        tournamentParticipants.add("Velvi");
//        tournamentParticipants.add("Goethessa");
//        tournamentParticipants.add("DULEON");
//        tournamentParticipants.add("FlowerGun");
//        tournamentParticipants.add("Dqlloz");
//        tournamentParticipants.add("PLLGG");
//        tournamentParticipants.add("Killer_Z");
//        tournamentParticipants.add("StrombleX");
//        tournamentParticipants.add("Reincarnation");
//        tournamentParticipants.add("NatsuDragneel");
//        tournamentParticipants.add("richardbos95");
//        tournamentParticipants.add("KoCmu4eskuy");
//        tournamentParticipants.add("Kot_is_neolita");
//        tournamentParticipants.add("Crab_Salad");
//        tournamentParticipants.add("PikON190");
//        tournamentParticipants.add("TheLone1yyy");
//        tournamentParticipants.add("Dagerofff");
//        tournamentParticipants.add("Ka4vnik");
//        tournamentParticipants.add("Kabiass");
//        tournamentParticipants.add("SogooD");

        tipsAndTricks = new ArrayList<>();
        tipsAndTricks.add("Все выбрасываемые зелья имеют#ускоренную и более прямую траекторию.");
        tipsAndTricks.add("Стрелы Регенерации и Лечения не влияют#на противников, однако действуют на союзников,#не нанося им урона.");
        tipsAndTricks.add("Фейерверки не сработают для ускорения элитр,#однако могут быть выпущены из арбалета.");
        tipsAndTricks.add("Неломаемые инструменты и броня#не теряют прочность и при смерти.");
        tipsAndTricks.add("При смерти игроки теряют 75->10% прочности брони#и инструментов при 1->5 минутах прожитого времени.#Частые смерти наказываются сильной поломкой.");
        tipsAndTricks.add("Генераторы могут автоматически складывать#ресурсы в сундук над собой.");
        tipsAndTricks.add("Арбалеты на пробивание могут пробивать#не только игроков, но и их щиты.");
        tipsAndTricks.add("Способноости активируются по порядку#от левой/верхней до правой/нижней.#Иногда это приводит к интересным комбинациям.");
        tipsAndTricks.add("3 бонуса на ближний урон +20%, +30% и +3ед.#сработают так: Базовый урон будет увеличен#сначала на +50% а затем на +3 единицы,#всё до учёта сопротивлений, брони и зачарований.");
        tipsAndTricks.add("С усилением урона игрока +40% и сопротивлением#(уменьшением) урона противника -15%#игрок нанесёт 125% от урона оружия.");
        tipsAndTricks.add("Огонь на арене сжигает и распространяется#по поставленным игроками блокам.");
        tipsAndTricks.add("Головы выпадают с убийств в ближнем бою#и могут быть использованы для покупки#командных улучшений.");
        tipsAndTricks.add("Топоры имеют повышенный шанс выпадения голов.");
        tipsAndTricks.add("Обычные взрывные зелья не накладывают#негативные эффекты на союзников,#и не накладывают позитивные на противников.");
        tipsAndTricks.add("Трезубцы вернутся к игроку даже#при падении в бездну или смерти игрока#во время их полёта.");
        tipsAndTricks.add("На арене разрешён верстак и некоторые крафты.#Например: двери, люки, заборы,#различные полублоки, красители...");
        tipsAndTricks.add("Некоторые блоки на карте могут быть#сломаны в любое время игры. Если вы думаете,#что какие-то дополнительные блоки должны#ломаться - свяжитесь с администрацией.");
        tipsAndTricks.add("Удочкой можно притягивать и сбрасывать#противников в бездну.");
        tipsAndTricks.add("Любое восстановление здоровья, голода,#сытости, щитов (поглощения) или накладывание#эффекта Регенерации может быть усилено#или ослаблено соответсвующими способностями и зельями.");
        tipsAndTricks.add("При смерти игрока убийство будет засчитано#последнему игроку который нанёс им урон#за последние 10 секунд.");
        tipsAndTricks.add("Игроку зачтётся помощь в убийстве#при дебаффе цели или баффе/лечении убийцы#за 10 секунд до смерти противника.");
        tipsAndTricks.add("Элитры имеют ограниченный запас времени полёта,#после чего игроку необходимо вернуться на землю,#чтобы начать полёта снова.");
        tipsAndTricks.add("Защита от снарядов помогает и от урона фейерверков,#так как они считаются дальним уроном.");
        tipsAndTricks.add("Блоки стекла полностью невосприимчевы ко взрывам#и защищают блоки по другую сторону.");
        tipsAndTricks.add("Эндер сундук является командным сундуком#и разделяет инвентарь со всеми членами команды.");
        tipsAndTricks.add("Изумруды всегда спавнятся на каждый#второй спавн золота.");
        tipsAndTricks.add("Золото и изумруды появляются#только после 2 минут с начала игры.");
        tipsAndTricks.add("Купленная в магазине броня попытается#атоматически установиться в соответсвующие#пустые слоты брони.");
        tipsAndTricks.add("Выпиваемые зелья и бутылочки мёда#автоматически заменяются на хотбаре#и не оставляют за собой пустые бутылки.");
        tipsAndTricks.add("Фантомы накладывают эффект Темноты#при попадании по жертве.");
        tipsAndTricks.add("Батуты могут быть сильной защитой#против противников, которые находятся#высоко над вашей базой.");
        tipsAndTricks.add("Топоры имеют сниженный урон, однако#дают игроку сопротивление к отбрасыванию#и дополнительные очки брони.");
        tipsAndTricks.add("Критические удары мечами по поднятому щиту#собьют щит на 2 секунды.");
        tipsAndTricks.add("Динамит не наносит урон союзникам,#но наносит урон всем мобам, противникам и владельцу.");
        tipsAndTricks.add("Огненные шары не наносят урон союзникам#и владельцу, а также могут быть использованы#для простых \"фаерболл-джампов\".");
        tipsAndTricks.add("Эффекты зелий и их сила отображаются#над никнеймами игроковю");
        tipsAndTricks.add("Здоровье игроков отображается#цветными ячейками под их никами.#1 ячейка - 1 сердце.");
        tipsAndTricks.add("Способности игроков отображаются перед#их никами в чате. Наведите мышку на иконки#чтобы прочитать описание способностей.");
        tipsAndTricks.add("Снежки имеют повышенный урон и отбрасывание,#однако ограничены кулдауном.");
        tipsAndTricks.add("Рецепты в Кузнице продолжают крафт#даже если игрок не в сети.");
        tipsAndTricks.add("Иногда игроки получат книги с ресурсами#и способностями в конце игры. Шансы увеличиваются#с количествов игроков в игре, со временем игры#и если игрок является новичком.");
        tipsAndTricks.add("Закрытие анимации открытия книги автоматически#выдаст вам ту же награду, как если бы вы дождались конца анимации.");
        tipsAndTricks.add("Дезматч наносит урон, дебаффает и постепенно#толкает игроков к центру арены. После полного#сужения границ игроки стоящие в присяди#в финальной зоне получат эффект Свечения.");

//        tipsAndTricks.add("");
//        tipsAndTricks.add("");
//        tipsAndTricks.add("");
//        tipsAndTricks.add("");
//        tipsAndTricks.add("");
//        tipsAndTricks.add("");
//        tipsAndTricks.add("");
    }

    public static void loadPlayerStarterGoodies(GamePlayer gamePlayer) {


        PlayerStatistic statistic = Main.getPlayerStatisticsManager().getStatistic(gamePlayer.player);

        if ( statistic.getGames() > 5 ) {

        }

        for ( Class clazz : starterAbilities ) {
            IAbility ability = Ability.generateAbility(clazz);

//            Bukkit.getConsoleSender().sendMessage("starter abilities " + ability.getName() +  "  " + ability.getId());
            if (!gamePlayer.getOwnedAbilityById(ability.getId()).isOwned()) {
//                Bukkit.getConsoleSender().sendMessage("starter abilities not owned");
                Main.getAbilitiesManager().giveAbilityToById(gamePlayer.player.getUniqueId(), ability.getId(), 1, false, false);
            }

        }

//        if (tournamentParticipants.contains(gamePlayer.player.getName()))
//        for ( Class clazz : tournamentRewardAbilities ) {
//            IAbility ability = Ability.generateAbility(clazz);
//
////            Bukkit.getConsoleSender().sendMessage("starter abilities " + ability.getName() +  "  " + ability.getId());
//            if (!gamePlayer.getOwnedAbilityById(ability.getId()).isOwned()) {
////                Bukkit.getConsoleSender().sendMessage("starter abilities not owned");
//                Main.getAbilitiesManager().giveAbilityToById(gamePlayer.player.getUniqueId(), ability.getId(), 1);
//            }
//
//        }

//        if ( gamePlayer.getTrulyOwnedAbilityById("amogus") == null ) {
//            NotificationManager.sendEventRewardMessage( "\"1 апреля\"", "Заход на сервер", gamePlayer.player);
//            for ( int i = 0; i < 6; i++ )
//                Main.getAbilitiesManager().giveAbilityToById(gamePlayer.player.getUniqueId(), "amogus", 1);
//            ResourceManager.giveResourcesTo(gamePlayer.player.getUniqueId(), ResourceType.BOOK_EVIL1, 1, true);
//        }

    }

    public static void setDefaultAbilities(GamePlayer gamePlayer) {
        if ( gamePlayer.getSetting( PlayerConfigType.DEFAULT_ABILITIES_AUTOSELECT ) ) {
            IAbility ability;
            OwnedAbility ownedAbility;

            ability = Ability.generateAbility(Confidence.class);
            ownedAbility = gamePlayer.getTrulyOwnedAbilityById( ability.getId() );
            gamePlayer.loadedAbilities.set(0, new LoadedAbility( ownedAbility, Math.min( ownedAbility.getOwnedLevel(), 1)  ));
            ability = Ability.generateAbility(Equipment.class);
            ownedAbility = gamePlayer.getTrulyOwnedAbilityById( ability.getId() );
            gamePlayer.loadedAbilities.set(1, new LoadedAbility( ownedAbility, Math.min( ownedAbility.getOwnedLevel(), 2)  ));
            ability = Ability.generateAbility(Stash.class);
            ownedAbility = gamePlayer.getTrulyOwnedAbilityById( ability.getId() );
            gamePlayer.loadedAbilities.set(2, new LoadedAbility( ownedAbility, Math.min( ownedAbility.getOwnedLevel(), 3)  ));

            gamePlayer.getGame().updateScoreboard(gamePlayer);
        } else {
//            Bukkit.getConsoleSender().sendMessage("Player " + gamePlayer.player.getDisplayName() + " don't have default abilities, and thus loaded from DB.");
            for ( OwnedAbility ownedAbility : gamePlayer.ownedAbilities ) {
                if ( ownedAbility.lastEquippedSlot >= 0 ) {
                    int slot = ownedAbility.lastEquippedSlot;
                    int level = ownedAbility.ownedLevel;
                    if (slot + 1 < level || (ownedAbility.getAbility().isTemporarilyAvailable())) level = slot + 1;
                    gamePlayer.loadedAbilities.set(slot, new LoadedAbility(ownedAbility, level));
                }
            }
        }
    }

    public static void sendOutATip(GamePlayer gamePlayer) {
        if ( !gamePlayer.settingExists(PlayerConfigType.ENABLE_TIPS) ) gamePlayer.setSetting(PlayerConfigType.ENABLE_TIPS, "1");

        if ( gamePlayer.getSetting(PlayerConfigType.ENABLE_TIPS) ) {
            ArrayList<String> allTips = new ArrayList<>(tipsAndTricks);
            Collections.shuffle(allTips);

            ArrayList<String> myTip = NotificationManager.divideAndParseLines(allTips.get(0), "#");

            gamePlayer.player.sendMessage("");
            gamePlayer.player.sendMessage( IconsManager.green_excl + ColoursManager.green + " Подсказка!");
            for ( String line : myTip ) {
                gamePlayer.player.sendMessage(ColoursManager.gray + "   " + line);
            }
            gamePlayer.player.sendMessage("");

        }

    }
}
