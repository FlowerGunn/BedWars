package org.screamingsandals.bedwars.utils.flowergun.customobjects;

import org.bukkit.entity.Player;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.utils.flowergun.managers.ColoursManager;
import org.screamingsandals.bedwars.utils.flowergun.other.comparators.ImpactInstanceComparator;
import org.screamingsandals.bedwars.utils.flowergun.other.comparators.ResourceBundleContentsComparator;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

import java.util.ArrayList;
import java.util.Collections;

public class ResourceBundle {

    public ArrayList<Resource> resources;

    public ResourceBundle() {
        this.resources = new ArrayList<>();
    }

    public ResourceBundle addResource(ResourceType resourceType, int amount) {
        return addResource(new Resource(resourceType, amount));
    }

    public ResourceBundle addResource(Resource inputResource) {
        for ( Resource resource : resources) {
            if ( resource.getType() == inputResource.getType() ) {
                resource.amount += inputResource.getAmount();
                return this;
            }
        }
        this.resources.add(inputResource);
        return this;
    }

    public ResourceBundle addAllResources(ArrayList<Resource> resources) {
        for ( Resource resource : resources ) {
            this.resources.add(resource.copy());
        }
        return this;
    }

    public ResourceBundle copy() {
        ResourceBundle resourceBundle = new ResourceBundle();
        resourceBundle.addAllResources(this.resources);
        return resourceBundle;
    }

    public void multiplyResources( double multiplier ) {
        for ( Resource resource : this.resources ) {
            resource.amount = (int) (resource.amount * multiplier);
        }
    }

    public ArrayList<String> parseResourcesForPlayer(Player player, boolean comparisons) {


        Collections.sort(this.resources, new ResourceBundleContentsComparator());

        ArrayList<String> finalList = new ArrayList<>();

        String playerColour;

        for ( int i = 0; i < resources.size(); i++ ) {
            Resource resource = resources.get(i);
            int playerAmount = Main.getPlayerGameProfile(player).ownedResourceBundle.getOwnedResourceAmount(resource.getType());
            if (comparisons)
            playerColour = resource.amount <= playerAmount ? ColoursManager.green + "" : ColoursManager.red + "";
            else playerColour = ColoursManager.gray + "";
            finalList.add(ColoursManager.gray + " " + resource.getResourceWithAmountAndIcon(player) + ColoursManager.gray + " (" + playerColour + playerAmount + ColoursManager.gray + ")");
        }
        return finalList;
    }

}
