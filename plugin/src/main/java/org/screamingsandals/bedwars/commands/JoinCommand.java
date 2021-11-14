package org.screamingsandals.bedwars.commands;

import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import org.screamingsandals.bedwars.config.MainConfig;
import org.screamingsandals.bedwars.econ.Economy;
import org.screamingsandals.bedwars.econ.EconomyProvider;
import org.screamingsandals.bedwars.game.GameManagerImpl;
import org.screamingsandals.bedwars.lang.LangKeys;
import org.screamingsandals.bedwars.player.PlayerManagerImpl;
import org.screamingsandals.lib.lang.Message;
import org.screamingsandals.lib.player.PlayerWrapper;
import org.screamingsandals.lib.sender.CommandSenderWrapper;
import org.screamingsandals.lib.utils.annotations.Service;
import org.screamingsandals.lib.utils.annotations.methods.OnEnable;
import org.screamingsandals.lib.utils.annotations.methods.OnPostEnable;
import org.screamingsandals.lib.utils.annotations.parameters.ProvidedBy;

import java.util.Optional;

@Service(dependsOn = {
        EconomyProvider.class
})
public class JoinCommand extends BaseCommand {
    private Economy economy;

    public JoinCommand() {
        super("join", BedWarsPermission.JOIN_PERMISSION, false);
    }

    @OnEnable
    public void enable(@ProvidedBy(EconomyProvider.class) Economy economy) {
        this.economy = economy;
    }

    @Override
    protected void construct(Command.Builder<CommandSenderWrapper> commandSenderWrapperBuilder, CommandManager<CommandSenderWrapper> manager) {
        manager.command(
                commandSenderWrapperBuilder
                        .argument(manager
                                .argumentBuilder(String.class, "game")
                                .withSuggestionsProvider((c, s) -> GameManagerImpl.getInstance().getGameNames())
                                .asOptional()
                        )
                        .handler(commandContext -> {
                            Optional<String> game = commandContext.getOptional("game");

                            var sender = commandContext.getSender().as(PlayerWrapper.class);
                            var player = sender.as(PlayerWrapper.class);
                            if (PlayerManagerImpl.getInstance().isPlayerInGame(sender)) {
                                sender.sendMessage(Message.of(LangKeys.IN_GAME_ERRORS_ALREADY_IN_GAME).defaultPrefix());
                                return;
                            }

                            if (game.isPresent()) {
                                var arenaN = game.get();
                                GameManagerImpl.getInstance().getGame(arenaN).ifPresentOrElse(
                                        game1 -> {
                                            if (MainConfig.getInstance().node("economy", "enabled").getBoolean(true) && economy != null) {
                                                if (game1.getFee() > 0) {
                                                    if (!economy.withdraw(player, game1.getFee())) {
                                                        Message.of(LangKeys.IN_GAME_ECONOMY_MISSING_COINS)
                                                                .placeholder("coins", game1.getFee())
                                                                .placeholder("currency", economy.currencyName())
                                                                .send(sender);
                                                        return;
                                                    }
                                                }
                                            }
                                            game1.joinToGame(PlayerManagerImpl.getInstance().getPlayerOrCreate(player));
                                        },
                                        () -> sender.sendMessage(Message.of(LangKeys.IN_GAME_ERRORS_GAME_NOT_FOUND).defaultPrefix())
                                );
                            } else {
                                GameManagerImpl.getInstance().getGameWithHighestPlayers(false).ifPresentOrElse(
                                        game1 -> game1.joinToGame(PlayerManagerImpl.getInstance().getPlayerOrCreate(player)),
                                        () -> sender.sendMessage(Message.of(LangKeys.IN_GAME_ERRORS_GAME_NOT_FOUND).defaultPrefix())
                                );
                            }
                        })
        );
    }
}
