package org.screamingsandals.bedwars.utils.flowergun.customobjects;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.api.APIUtils;
import org.screamingsandals.bedwars.game.CurrentTeam;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.game.TeamColor;
import org.screamingsandals.bedwars.special.Trap;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.gadgets.TrampolineListener;

import java.util.*;


public class CustomItem {

    @Getter
    @Setter
    protected ItemStack item;
    @Getter
    @Setter
    protected ItemMeta itemMeta;
    @Getter
    @Setter
    protected PersistentDataContainer itemData;

    @Getter
    @Setter
    public boolean canBeDyed = false;
    @Getter
    @Setter
    public boolean isFirework = false;
    @Getter
    @Setter
    protected GadgetType gadgetType = null;

    @Getter
    @Setter
    public boolean isLoreRemoved = false;

    public CustomItem setDyable(){
        this.canBeDyed = true;
        return this;
    }

    public CustomItem setFirework() {
        this.isFirework = true;
        return this;
    }

    public CustomItem setCustomModelData(int data){
        ItemMeta im = this.item.getItemMeta();
        im.setCustomModelData(data);
        this.item.setItemMeta(im);
        return this;
    }

    public CustomItem addMovementSpeedModifier(double value, AttributeModifier.Operation attributeModifierOperation, EquipmentSlot equipmentSlot){
        AttributeModifier am = new AttributeModifier(UUID.randomUUID(), "generic.movementSpeed", value, attributeModifierOperation, equipmentSlot);
        this.itemMeta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, am);
        return this;
    }

    public CustomItem addAttackDamageModifier(double value, AttributeModifier.Operation attributeModifierOperation, EquipmentSlot equipmentSlot){
        AttributeModifier am = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", value, attributeModifierOperation, equipmentSlot);
        this.itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, am);
        return this;
    }

    public CustomItem addAttackSpeedModifier(double value, AttributeModifier.Operation attributeModifierOperation, EquipmentSlot equipmentSlot){
        AttributeModifier am = new AttributeModifier(UUID.randomUUID(), "generic.attackSpeed", value, attributeModifierOperation, equipmentSlot);
        this.itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, am);
        return this;
    }

    public CustomItem addArmorPointsModifier(double value, AttributeModifier.Operation attributeModifierOperation, EquipmentSlot equipmentSlot){
        AttributeModifier am = new AttributeModifier(UUID.randomUUID(), "generic.armor", value, attributeModifierOperation, equipmentSlot);
        this.itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, am);
        return this;
    }

    public CustomItem addKnockbackResistanceModifier(double value, AttributeModifier.Operation attributeModifierOperation, EquipmentSlot equipmentSlot){
        AttributeModifier am = new AttributeModifier(UUID.randomUUID(), "generic.knockbackResistance", value, attributeModifierOperation, equipmentSlot);
        this.itemMeta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, am);
        return this;
    }

    public CustomItem setMaterial(Material material ){
        this.item.setType(material);
        this.itemMeta = this.item.getItemMeta();
        return this;
    }

    public CustomItem addHiddenEnchantment() {
        this.itemMeta.addEnchant(Enchantment.LUCK, 1, false);
        this.hideEnchantments();
        return this;
    }

    public CustomItem hideEnchantments() {
        this.itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public CustomItem addEnchantment(Enchantment enchantment, int level) {
        this.itemMeta.addEnchant(enchantment, level, true);
        return this;
    }

    // X starts with 0. from left ===> to right

    // Y starts with 0. from top ||
    //                           \/ to bottom

    public CustomItem setName(String name ){
        this.itemMeta.setDisplayName(name);
        return this;
    }

    public CustomItem setLore(ArrayList<String> lore ){
        this.itemMeta.setLore(lore);
        return this;
    }

    public CustomItem setAmount(int amount){
        this.item.setAmount(amount);
        return this;
    }

    public CustomItem setItemstack(ItemStack itemStack ) {
        this.item = itemStack.clone();
        this.itemMeta = this.item.getItemMeta();
        return this;
    }

    public CustomItem() {
        this.item = new ItemStack(Material.BEDROCK);
        this.itemMeta = this.item.getItemMeta();

        this.item.setItemMeta(itemMeta);
    }

    public CustomItem(CustomItem customItem) {
        this.item = customItem.item;
        this.itemMeta = customItem.itemMeta;
        this.itemData = customItem.itemData;
        this.gadgetType = customItem.gadgetType;
    }

    public CustomItem removeLoreOnPurchase() {
        this.isLoreRemoved = true;
        return this;
    }

    public ItemStack prepareItem(Player player) {

        ItemStack tempItem = this.item.clone();

        if ( isLoreRemoved ) {
            ItemMeta itemMeta = tempItem.getItemMeta();
            itemMeta.setLore(null);
            tempItem.setItemMeta(itemMeta);
        }

        GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
        Game game = gamePlayer.getGame();
        CurrentTeam team = game.getPlayerTeam(gamePlayer);
        TeamColor teamColor = TeamColor.fromApiColor(team.getColor());

        if (gadgetType != null) {
            String property = "";
            switch (this.gadgetType) {
            case TRAP: {

                Map<String, Object> data;

                List<Map<String, Object>> list = new ArrayList<>();

                data = new HashMap<>();
                data.put("sound", Sound.ENTITY_SHEEP_AMBIENT.toString());
                list.add(data);

//                data = new HashMap<>();
//                ArrayList<PotionEffect> potionEffects = new ArrayList<>();
//                potionEffects.add( new PotionEffect( PotionEffectType.BLINDNESS, 100, 2, true, false, true));
//                potionEffects.add( new PotionEffect( PotionEffectType.SLOW_DIGGING, 100, 2, true, false, true));
//                potionEffects.add( new PotionEffect( PotionEffectType.SLOW, 100, 2, true, false, true));
//                data.put("effect", potionEffects);
//                list.add(data);

                data = new HashMap<>();
                data.put("damage", 1.0);
                list.add(data);

//                Main.getInstance().getLogger().info(String.valueOf(data));

                Trap trap = new Trap(game, player,
                        team,
                        list);

                int id = System.identityHashCode(trap);

                property = "Module:Trap:" + id;
                break;
            }
            case SHEEP: {
                property = "Module:TNTSheep:0.25:10.0:32.0:8";
                break;
            }
            case TP: {
                property = "Module:WarpPowder:6:6";
                break;
            }
            case TNT: {
                property = "Module:AutoIgniteableTnt:5:false:4.0";
                break;
            }
            case PLATFORM: {
                property = "Module:RescuePlatform:true:3:-1:1:GLASS";
                break;
            }
            case PLATFORM_SLIME: {
                property = "Module:RescuePlatform:false:5:10:1:SLIME_BLOCK";
                break;
            }
            case WALL: {
                property = "Module:ProtectionWall:true:5:0:5:3:2:SANDSTONE";
                break;
            }
            case ARROW_SHIELD: {
                property = "Module:ArrowBlocker:10:5";
                break;
            }
            case FIREBALL: {
                property = "Module:ThrowableFireball:3.0:true:true";
                break;
            }
            case GOLEM: {
                property = "Module:Golem:0.25:10.0:20.0:true:10:false:%teamcolor%%team%";
                break;
            }
            case ZOGLIN: {
                property = "Module:Zoglin:0.40:10.0:20.0:true:10:false:%teamcolor%%team%";
                break;
            }
            case BLAZE: {
                property = "Module:Blaze:0.40:10.0:20.0:true:10:false:%teamcolor%%team%";
                break;
            }
            case PHANTOM: {
                property = "Module:Phantom:0.40:10.0:20.0:true:10:false:%teamcolor%%team%";
                break;
            }
            case TRACKER: {
                property = "Module:Tracker:";
                break;
            }
            case BOOTS: {
                property = "Module:MagnetShoes:75";
                break;
            }
            case NEW_BOOTS: {
                property = "Module:MagnetShoes:100";
                break;
            }
            case TRAMPOLINE: {
                property = TrampolineListener.TRAMPOLINE_PREFIX;
                break;
            }
            default: {
                break;
            }
        }
            APIUtils.hashIntoInvisibleString(tempItem, property);
        }


        if (this.canBeDyed) {
            LeatherArmorMeta itemMeta = (LeatherArmorMeta) tempItem.getItemMeta();
            itemMeta.setColor(teamColor.leatherColor);
            tempItem.setItemMeta(itemMeta);
        }

        if (this.isFirework) {
            FireworkMeta feuermeta = (FireworkMeta) tempItem.getItemMeta();
            feuermeta.setPower(3);
            feuermeta.addEffect(FireworkEffect.builder().withFade(Color.WHITE).withColor(teamColor.leatherColor).with(FireworkEffect.Type.BALL_LARGE).build());
            tempItem.setItemMeta(feuermeta);
        }

        return tempItem;
    }

    public CustomItem addPotionEffect(PotionEffect potionEffect, Color color) {
        PotionMeta potionMeta = (PotionMeta) this.itemMeta;
        potionMeta.setColor(color);
        potionMeta.addCustomEffect(potionEffect, false);
        this.itemMeta = potionMeta;
        return this;
    }

    public CustomItem addPotionEffect(PotionEffect potionEffect) {
        PotionMeta potionMeta = (PotionMeta) this.itemMeta;
        potionMeta.addCustomEffect(potionEffect, false);
        this.itemMeta = potionMeta;
        return this;
    }

    public CustomItem setBasePotionData(PotionType potionType, boolean extended, boolean upgraded) {
        PotionMeta potionMeta = (PotionMeta) this.itemMeta;
        potionMeta.setBasePotionData(new PotionData(potionType, extended, upgraded));
        this.itemMeta = potionMeta;
        return this;
    }


    public CustomItem setGadgetType( GadgetType gadgetType ) {

//        String property = "";

        this.gadgetType = gadgetType;
//
//        switch (gadgetType) {
//        }
//
//        APIUtils.hashIntoInvisibleString(this.item, property);
//        this.itemData.set(new NamespacedKey( Main.getInstance(), "CustomGadget"), PersistentDataType.STRING, gadgetType.getId());
        return this;
    }

    public GadgetType getGadgetType() {
        return this.gadgetType;
    }

    public CustomItem build(){

        this.item.setItemMeta(this.itemMeta);
        return this;
    }

    public ItemStack getItem() {
        return this.item;
    }

    public CustomItem copy(){
//        if (item.getType() == Material.FIREWORK_ROCKET) {
//            Bukkit.getConsoleSender().sendMessage("fireworks " + this.item.getAmount() + " " + this.hashCode());
//        }
        CustomItem customItem = new CustomItem();
        customItem.setItem(new ItemStack(this.getItem()));
        customItem.setItemMeta(this.getItemMeta());
        customItem.setItemData(this.getItemData());
        customItem.setFirework(this.isFirework());
        customItem.setCanBeDyed(this.isCanBeDyed());
        customItem.setLoreRemoved(this.isLoreRemoved());
        return customItem.build();
    }


}
