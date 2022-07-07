package mod.gate;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import mod.gate.commands.Command;
import mod.gate.commands.CommandDispatcher;
import mod.gate.commands.CommandRegistry;
import mod.gate.commands.test.testCommand;
import mod.gate.events.EventHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GateClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Gate");
    public static CommandRegistry commandRegistry = new CommandRegistry();


    @Override
    public void onInitializeClient() {
        EventHandler.registerEvent(new CommandDispatcher());

        commandRegistry.register(new testCommand());

        CommandRegistrationCallback.EVENT.register(((dispatcher, dedicated) -> {
            for (Command command : commandRegistry.Commands) {
                LiteralArgumentBuilder<ServerCommandSource> LiteralCommand = CommandManager.literal(command.LITERAL_NAME);


                for (Command.CommandArg commandArg: command.args) {
                    LiteralCommand
                            .then(
                                    CommandManager
                                            .argument(commandArg.LITERAL_NAME, StringArgumentType.greedyString())
                                            .executes(command::run));
                }

                dispatcher.register(LiteralCommand);
            }
        }));
        //LOGGER.info("Hello Client");
    }
}