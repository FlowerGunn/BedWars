package org.screamingsandals.bedwars.utils.flowergun.customobjects;

import org.bukkit.Material;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.abilities_impl.Appleperson;
import org.screamingsandals.bedwars.utils.flowergun.abilities_impl.Aromatherapy;
import org.screamingsandals.bedwars.utils.flowergun.abilities_impl.Starling;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

import java.sql.Timestamp;
import java.util.ArrayList;

public class ResourceChestBook {


    public static ArrayList<ResourceChest> resourceChests;

    static {
        resourceChests = new ArrayList<>();

//        ResourceChest resourceChest1 = new ResourceChest( "test1" ,"Обычный сундук", "Сундук с обычной косметикой.", new Resource(ResourceType.CATALYST_LEGENDARY, 1), new Timestamp( 122, 4, 10, 0, 0, 0, 0), new Timestamp( 123, 4, 28, 0, 0, 0, 0), Material.BLUE_DYE, "anicloud:gold_sheet", 1);
//        resourceChest1.addCategory("commons", 80);
//        resourceChest1.addCategory("uncommons", 15);
//        resourceChest1.addCategory("rares", 5);
//
//        resourceChest1.addReward( new ResourceChestReward(new Resource(ResourceType.COAL, 4)), 0 ); //1
//        resourceChest1.addReward( new ResourceChestReward(new Resource(ResourceType.SLIMEBALL, 8)), 0 ); //2
//        resourceChest1.addReward( new ResourceChestReward(new Resource(ResourceType.RAW_IRON, 3)), 0 ); //1
//        resourceChest1.addReward( new ResourceChestReward(new Resource(ResourceType.RAW_COPPER, 5)), 0 ); //1
//        resourceChest1.addReward( new ResourceChestReward(new Resource(ResourceType.RAW_GOLD, 10)), 1 ); //2
//        resourceChest1.addReward( new ResourceChestReward(new Resource(ResourceType.BLAZE_POWDER, 10)), 1 ); //3
//        resourceChest1.addReward( new ResourceChestReward(new Resource(ResourceType.LAPIS, 60)), 2 ); //2
//        resourceChest1.addReward( new ResourceChestReward(new Resource(ResourceType.PAPER, 60)), 2 ); //miss
//        resourceChests.add(resourceChest1);
//
        ResourceChest resourceChest2 = new ResourceChest( "resource_chest" ,"Сундук ингридиентов", " /Сундук с множеством ресурсов (95%)/и малым шансом на редкую (4%)/или эпическую (1%) способность./ ", new Resource(ResourceType.BOOK, 1), new Timestamp( 122, 4, 10, 0, 0, 0, 0), new Timestamp( 123, 4, 28, 0, 0, 0, 0), Material.BOOK, null, 1);
        resourceChest2.addCategory("resources", 95);
        resourceChest2.addCategory("rares", 4);
        resourceChest2.addCategory("epics", 1);

        resourceChest2.addReward( new ResourceChestReward(new Resource(ResourceType.COAL, 40)), 0 );//1
        resourceChest2.addReward( new ResourceChestReward(new Resource(ResourceType.RAW_COPPER, 60)), 0 );//1
        resourceChest2.addReward( new ResourceChestReward(new Resource(ResourceType.RAW_IRON, 30)), 0 );//1
        resourceChest2.addReward( new ResourceChestReward(new Resource(ResourceType.BONE, 40)), 0 );//1
        resourceChest2.addReward( new ResourceChestReward(new Resource(ResourceType.SLIMEBALL, 30)), 0 );//1
        resourceChest2.addReward( new ResourceChestReward(new Resource(ResourceType.QUARTZ, 30)), 0 );//1
        resourceChest2.addReward( new ResourceChestReward(new Resource(ResourceType.LEATHER, 40)), 0 );//1

        resourceChest2.addReward( new ResourceChestReward(new Resource(ResourceType.GLOW_INK_SAC, 20)), 0 );//2
        resourceChest2.addReward( new ResourceChestReward(new Resource(ResourceType.PAPER, 20)), 0 );//2
        resourceChest2.addReward( new ResourceChestReward(new Resource(ResourceType.LAPIS, 20)), 0 );//2
        resourceChest2.addReward( new ResourceChestReward(new Resource(ResourceType.RAW_GOLD, 20 )), 0 );//2

        resourceChest2.addReward( new ResourceChestReward(new Resource(ResourceType.ECHO_SHARD, 5)), 0 );//3
        resourceChest2.addReward( new ResourceChestReward(new Resource(ResourceType.NETHERITE_SCRAP, 5)), 0 );//3
        resourceChest2.addReward( new ResourceChestReward(new Resource(ResourceType.EMERALD, 5 )), 0 );//3
        resourceChest2.addReward( new ResourceChestReward(new Resource(ResourceType.BLAZE_POWDER, 5)), 0 );//3
        resourceChest2.addReward( new ResourceChestReward(new Resource(ResourceType.ICE_POWDER, 5)), 0 );//3

        for (IAbility ability : Main.getAbilitiesManager().getAllAbilities()) {
            if (ability.getRarity() == 3) {
                resourceChest2.addReward( new ResourceChestReward(ability.getClass()), 1 );
            }
            if (ability.getRarity() == 4) {
                resourceChest2.addReward( new ResourceChestReward(ability.getClass()), 2 );
            }
        }

        resourceChest2.addReward( new ResourceChestReward(new Resource(ResourceType.CATALYST_RARE, 1 )), 0 );

        resourceChests.add(resourceChest2);



        ResourceChest rareAbilitiesChest = new ResourceChest( "rare_abilities" ,"Сундук редких способностей", " /Сундук со всеми редкими способностями (85%)/и шансом на выпадение эпических (15%) способностей./ ", new Resource(ResourceType.WRITABLE_BOOK, 1), new Timestamp( 122, 4, 10, 0, 0, 0, 0), new Timestamp( 123, 4, 28, 0, 0, 0, 0), Material.WRITABLE_BOOK, null, 1);

        rareAbilitiesChest.addCategory("rares", 85);
        rareAbilitiesChest.addCategory("epics", 15);

        for (IAbility ability : Main.getAbilitiesManager().getAllAbilities()) {
            if (ability.getRarity() == 3) {
                rareAbilitiesChest.addReward( new ResourceChestReward(ability.getClass()), 0 );
            }
            if (ability.getRarity() == 4) {
                rareAbilitiesChest.addReward( new ResourceChestReward(ability.getClass()), 1 );
            }
        }

        rareAbilitiesChest.addReward( new ResourceChestReward(new Resource(ResourceType.CATALYST_RARE, 1 )), 1 );

        resourceChests.add(rareAbilitiesChest);

        ResourceChest epicAbilitiesChest = new ResourceChest( "epic_abilities" ,"Сундук эпических способностей", " /Сундук со всеми эпическими способностями (90%)/и шансом на выпадение легендарных (10%) способностей./ ", new Resource(ResourceType.BOOK_EVIL1, 1), new Timestamp( 122, 4, 10, 0, 0, 0, 0), new Timestamp( 123, 4, 28, 0, 0, 0, 0), Material.RED_DYE, "anicloud:book_evil1", 1);

        epicAbilitiesChest.addCategory("epics", 90);
        epicAbilitiesChest.addCategory("legendaries", 10);

        for (IAbility ability : Main.getAbilitiesManager().getAllAbilities()) {
            if (ability.getRarity() == 4) {
                epicAbilitiesChest.addReward( new ResourceChestReward(ability.getClass()), 0 );
            }
            if (ability.getRarity() == 5) {
                epicAbilitiesChest.addReward( new ResourceChestReward(ability.getClass()), 1 );
            }
        }
        resourceChests.add(epicAbilitiesChest);

    }

    public static ResourceChest getChestById( String id ) {
        for (ResourceChest resourceChest : resourceChests ) {
            if ( resourceChest.getId().equals(id) ) return resourceChest;
        }
        return null;
    }

}
