package org.screamingsandals.bedwars.utils.flowergun.customobjects;

import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

import java.util.ArrayList;
import java.util.UUID;

public class OwnedResourceBundle {

    public ArrayList<OwnedResource> resources;

    public OwnedResourceBundle() {
        this.resources = new ArrayList<>();
    }

    public OwnedResourceBundle addResource(OwnedResource resource) {
        this.resources.add(resource);
        return this;
    }

    public OwnedResourceBundle addAllResources(ArrayList<OwnedResource> resources) {
        this.resources.addAll(resources);
        return this;
    }

    public boolean isContainingBundle( ResourceBundle resourceBundle ) {
        for ( Resource resource : resourceBundle.resources ) {
            boolean isFound = false;
            for (OwnedResource ownedResource : this.resources) {
                if ( resource.getType() == ownedResource.getType() ) {
                    if (resource.getAmount() > ownedResource.getAmount()) return false;
                    isFound = true;
                    break;
                }
            }
            if ( !isFound ) return false;
        }
        return true;
    }


    public boolean isContainingResource( Resource resource ) {
        for (OwnedResource ownedResource : this.resources) {
            if ( resource.getType() == ownedResource.getType() ) {
                if (resource.getAmount() > ownedResource.getAmount()) return false;
                return true;
            }
        }
        return false;
    }

    public OwnedResource getOwnedResource( ResourceType resourceType ) {
        for (OwnedResource ownedResource : this.resources) {
            if (resourceType == ownedResource.getType()) {
                return ownedResource;
            }
        }
        return null;
    }

    public int getOwnedResourceAmount( ResourceType resourceType ) {
        for (OwnedResource ownedResource : this.resources) {
            if (resourceType == ownedResource.getType()) {
                return ownedResource.getAmount();
            }
        }
        return 0;
    }

    public boolean updateOwnedResource( OwnedResource newOwnedResource ) {
        for (OwnedResource ownedResource : this.resources) {
            if (newOwnedResource.getType() == ownedResource.getType()) {
                ownedResource.amount = newOwnedResource.getAmount();
                return true;
            }
        }
        this.addResource(newOwnedResource);
        return false;
    }


}
