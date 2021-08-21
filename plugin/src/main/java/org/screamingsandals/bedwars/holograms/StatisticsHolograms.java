package org.screamingsandals.bedwars.holograms;

import java.util.*;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.screamingsandals.bedwars.commands.BedWarsPermission;
import org.screamingsandals.bedwars.config.MainConfig;
import org.screamingsandals.bedwars.lang.LangKeys;
import org.screamingsandals.bedwars.statistics.PlayerStatisticManager;
import org.screamingsandals.bedwars.utils.HologramLocation;
import org.screamingsandals.lib.event.EventPriority;
import org.screamingsandals.lib.hologram.Hologram;
import org.screamingsandals.lib.hologram.HologramManager;
import org.screamingsandals.lib.hologram.event.HologramTouchEvent;
import org.screamingsandals.lib.lang.Message;
import org.screamingsandals.lib.player.PlayerMapper;
import org.screamingsandals.lib.player.PlayerWrapper;
import org.screamingsandals.lib.event.player.SPlayerJoinEvent;
import org.screamingsandals.lib.event.player.SPlayerLeaveEvent;
import org.screamingsandals.lib.event.player.SPlayerWorldChangeEvent;
import org.screamingsandals.lib.plugin.PluginDescription;
import org.screamingsandals.lib.plugin.ServiceManager;
import org.screamingsandals.lib.tasker.Tasker;
import org.screamingsandals.lib.tasker.TaskerTime;
import org.screamingsandals.lib.utils.AdventureHelper;
import org.screamingsandals.lib.utils.annotations.Service;
import org.screamingsandals.lib.utils.annotations.methods.OnPostEnable;
import org.screamingsandals.lib.utils.annotations.methods.OnPreDisable;
import org.screamingsandals.lib.utils.annotations.methods.ShouldRunControllable;
import org.screamingsandals.lib.utils.annotations.parameters.ConfigFile;
import org.screamingsandals.lib.utils.visual.TextEntry;
import org.screamingsandals.lib.world.LocationHolder;
import org.screamingsandals.lib.event.OnEvent;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

@Service(dependsOn = {
        HologramManager.class,
        MainConfig.class,
        PlayerStatisticManager.class
})
@RequiredArgsConstructor
public class StatisticsHolograms {
    private final PluginDescription pluginDescription;
    @ConfigFile(value = "database/holodb.yml", old = "holodb.yml")
    private final YamlConfigurationLoader loader;
    private final MainConfig mainConfig;

    private ArrayList<HologramLocation> hologramLocations = null;
    private Map<UUID, List<Hologram>> holograms = null;

    public void addHologramLocation(LocationHolder eyeLocation) {
        this.hologramLocations.add(new HologramLocation(eyeLocation.add(0, -3, 0)));
        this.updateHologramDatabase();
    }

    @ShouldRunControllable
    public static boolean isEnabled() {
        return PlayerStatisticManager.isEnabled() && MainConfig.getInstance().node("holograms", "enabled").getBoolean();
    }

    public static StatisticsHolograms getInstance() {
        if (!isEnabled()) {
            throw new UnsupportedOperationException("StatisticsHolograms are not enabled!");
        }
        return ServiceManager.get(StatisticsHolograms.class);
    }

    @OnPostEnable
    @SuppressWarnings("unchecked")
    public void loadHolograms() {
        if (this.holograms != null && this.hologramLocations != null) {
            // first unload all holograms
            this.unloadHolograms();
        }

        this.holograms = new HashMap<>();
        this.hologramLocations = new ArrayList<>();

        try {
            var config = loader.load();
            var locations = config.node("locations").getList(HologramLocation.class);
            this.hologramLocations.addAll(locations);
        } catch (ConfigurateException e) {
            e.printStackTrace();
        }

        if (this.hologramLocations.size() == 0) {
            return;
        }

        this.updateHolograms();
    }

    @OnPreDisable
    public void unloadHolograms() {
        holograms.values().forEach(Holograms -> Holograms.forEach(holo -> {
            holo.hide();
            HologramManager.removeHologram(holo);
        }));
    }

    @OnEvent(priority = EventPriority.HIGHEST)
    public void onJoin(SPlayerJoinEvent event) {
        updateHolograms(event.getPlayer(), 10L);
    }

    @OnEvent
    public void onWorldChange(SPlayerWorldChangeEvent event) {
        updateHolograms(event.getPlayer(), 10L);
    }

    @OnEvent
    public void onLeave(SPlayerLeaveEvent event) {
        if (holograms.containsKey(event.getPlayer().getUuid())) {
            Tasker
                    .build(() -> {
                        holograms.get(event.getPlayer().getUuid()).forEach(holo -> {
                            holo.hide();
                            HologramManager.removeHologram(holo);
                        });
                        holograms.remove(event.getPlayer().getUuid());
                    })
                    .async()
                    .start();
        }
    }

    public void updateHolograms(PlayerWrapper player) {
        Tasker.build(() ->
                this.hologramLocations.forEach(holoLocation ->
                        holoLocation.asOptional(LocationHolder.class).ifPresent(location ->
                                this.updatePlayerHologram(player, location)
                        )
                ))
                .async()
                .start();
    }

    public void updateHolograms(PlayerWrapper player, long delay) {
        Tasker.build(() -> this.updateHolograms(player))
                .async()
                .delay(delay, TaskerTime.TICKS)
                .start();
    }

    public void updateHolograms() {
        PlayerMapper.getPlayers().forEach(player ->
                Tasker.build(() ->
                        this.hologramLocations.forEach(holoLocation ->
                                holoLocation.asOptional(LocationHolder.class).ifPresent(location ->
                                        this.updatePlayerHologram(player, location)
                                )
                        ))
                        .async()
                        .start()
        );
    }

    private Optional<Hologram> getHologramByLocation(List<Hologram> holograms, LocationHolder holoLocation) {
        return holograms.stream()
                .filter(hologram -> {
                    var loc = hologram.getLocation();
                    return loc != null && loc.getWorld().getUuid().equals(holoLocation.getWorld().getUuid())
                            && loc.getX() == holoLocation.getX()
                            && loc.getY() == holoLocation.getY()
                            && loc.getZ() == holoLocation.getZ();
                })
                .findFirst();
    }

    public void updatePlayerHologram(PlayerWrapper player, LocationHolder holoLocation) {
        if (!this.holograms.containsKey(player.getUuid())) {
            this.holograms.put(player.getUuid(), new ArrayList<>());
        }

        var holograms = this.holograms.get(player.getUuid());
        var holo = this.getHologramByLocation(holograms, holoLocation);
        if (holo.isEmpty() && player.getLocation().getWorld().getUuid().equals(holoLocation.getWorld().getUuid())) {
            holograms.add(this.createPlayerStatisticHologram(player, holoLocation));
        } else if (holo.isPresent()) {
            if (player.getLocation().getWorld().getUuid().equals(holo.get().getLocation().getWorld().getUuid())) {
                this.updatePlayerStatisticHologram(player, holo.get());
            } else {
                holograms.remove(holo.get());
                holo.get().hide();
                HologramManager.removeHologram(holo.get());
            }
        }
    }

    private Hologram createPlayerStatisticHologram(PlayerWrapper player, LocationHolder holoLocation) {
        final var holo = HologramManager
                .hologram(holoLocation)
                .firstLine(TextEntry.of(AdventureHelper.toComponent(mainConfig.node("holograms", "headline").getString("Your §eBEDWARS§f stats"))))
                .setTouchable(true)
                .addViewer(player);
        HologramManager.addHologram(holo);
        holo.show();

        this.updatePlayerStatisticHologram(player, holo);
        return holo;
    }

    private void updateHologramDatabase() {
        try {
            // update hologram-database file
            var node = loader.createNode();

            for (var location : hologramLocations) {
                node.node("locations").appendListNode().set(location);
            }
            loader.save(node);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private HologramLocation getHologramLocationByLocation(LocationHolder holoLocation) {
        return hologramLocations.stream()
                .filter(loc -> loc.getWorld().equals(holoLocation.getWorld().getName())
                        && loc.getX() == holoLocation.getX()
                        && loc.getY() == holoLocation.getY()
                        && loc.getZ() == holoLocation.getZ()
                )
                .findFirst()
                .orElse(null);
    }

    @OnEvent
    public void handle(HologramTouchEvent event) {
        var player = event.getPlayer();
        var holo = event.getHologram();
        if (!holograms.containsKey(player.getUuid()) || !holograms.get(player.getUuid()).contains(holo) || holo.getLocation() == null) {
            return;
        }

        if (!player.as(Player.class).hasMetadata("bw-remove-holo") || !player.hasPermission(BedWarsPermission.ADMIN_PERMISSION.asPermission())) {
            return;
        }

        var location = holo.getLocation();

        player.as(Player.class).removeMetadata("bw-remove-holo", pluginDescription.as(JavaPlugin.class));
        Tasker
                .build(() -> {
                    // remove all player holograms on this location
                    for (var entry : holograms.entrySet()) {
                        var iterator = entry.getValue().iterator();
                        while (iterator.hasNext()) {
                            var hologram = iterator.next();

                            if (location.getWorld().getUuid().equals(location.getWorld().getUuid())
                                    && location.getX() == location.getX()
                                    && location.getY() == location.getY()
                                    && location.getZ() == location.getZ()) {
                                hologram.hide();
                                HologramManager.removeHologram(hologram);
                                iterator.remove();
                            }
                        }
                    }

                    var holoLocation = getHologramLocationByLocation(location);
                    if (holoLocation != null) {
                        hologramLocations.remove(holoLocation);
                        updateHologramDatabase();
                    }
                    Message.of(LangKeys.ADMIN_HOLO_REMOVED).defaultPrefix().send(player);
                })
                .async()
                .start();
    }

    private void updatePlayerStatisticHologram(PlayerWrapper player, final Hologram holo) {
        var statistic = PlayerStatisticManager.getInstance().getStatistic(player);

        var lines = Message
                .of(LangKeys.STATISTICS_KILLS)
                .join(LangKeys.STATISTICS_DEATHS)
                .join(LangKeys.STATISTICS_KD)
                .join(LangKeys.STATISTICS_WINS)
                .join(LangKeys.STATISTICS_LOSES)
                .join(LangKeys.STATISTICS_GAMES)
                .join(LangKeys.STATISTICS_BEDS)
                .join(LangKeys.STATISTICS_SCORE)
                .placeholder("player", statistic.getName())
                .placeholder("kills", statistic.getKills())
                .placeholder("deaths", statistic.getDeaths())
                .placeholder("kd", statistic.getKD())
                .placeholder("wins", statistic.getWins())
                .placeholder("loses", statistic.getLoses())
                .placeholder("games", statistic.getGames())
                .placeholder("beds", statistic.getDestroyedBeds())
                .placeholder("score", statistic.getScore())
                .getFor(player);

        var size = holo.getLines().size();
        var increment = size == 1 || size > lines.size() ? 1 : 0;

        for (int i = 0; i < lines.size(); i++) {
            holo.replaceLine(i + increment, lines.get(i));
        }
    }

}
