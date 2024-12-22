/*
 * Copyright (C) 2022 ScreamingSandals
 *
 * This file is part of Screaming BedWars.
 *
 * Screaming BedWars is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Screaming BedWars is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Screaming BedWars. If not, see <https://www.gnu.org/licenses/>.
 */

package org.screamingsandals.bedwars.special;

import org.bukkit.plugin.Plugin;
import org.screamingsandals.bedwars.listener.ItemAdderLoadChecker;
import org.screamingsandals.bedwars.utils.flowergun.customgui.InventoryGUICleaner;
import org.screamingsandals.bedwars.utils.flowergun.customgui.InventoryGUIClickListener;
import org.screamingsandals.bedwars.special.listener.*;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CustomBlockListener;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.gadgets.TrampolineListener;
import org.screamingsandals.bedwars.utils.flowergun.fixes.*;
import org.screamingsandals.bedwars.utils.flowergun.mechanics.*;

public class SpecialRegister {

    public static void onEnable(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(new ArrowBlockerListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new GolemListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new LuckyBlockAddonListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new MagnetShoesListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new ProtectionWallListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new RescuePlatformListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new TeamChestListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new ThrowableFireballListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new TNTSheepListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new TrackerListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new TrapListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new WarpPowderListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new AutoIgniteableTNTListener(), plugin);

        plugin.getServer().getPluginManager().registerEvents(new InventoryGUICleaner(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new InventoryGUIClickListener(), plugin);

        plugin.getServer().getPluginManager().registerEvents(new TrampolineListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new CustomBlockListener(), plugin);

        plugin.getServer().getPluginManager().registerEvents(new VineListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new FireListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new TallBlocksListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new FireworkListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new ShieldListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new HeadsListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new ConsumeListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new EndgameDamageListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new SnowballListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new CustomDamageListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new CombustListener(), plugin);

        plugin.getServer().getPluginManager().registerEvents(new ElytraListener(), plugin);

        plugin.getServer().getPluginManager().registerEvents(new ZoglinListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PhantomListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new BlazeListener(), plugin);

        plugin.getServer().getPluginManager().registerEvents(new GhastListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new GuardianListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new HuskListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new StrayListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new SlimeListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new ShulkerListener(), plugin);

        plugin.getServer().getPluginManager().registerEvents(new ItemUseListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new MobSpawnListener(), plugin);

        plugin.getServer().getPluginManager().registerEvents(new PotionThrowListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new VillagerDamageListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PotionEffectsListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerDeathTridentListener(), plugin);

        plugin.getServer().getPluginManager().registerEvents(new ProjectileProcessingEvent(), plugin);

        plugin.getServer().getPluginManager().registerEvents(new UniversalMobTargetingListener(), plugin);

        plugin.getServer().getPluginManager().registerEvents(new ItemAdderLoadChecker(), plugin);
    }

}
