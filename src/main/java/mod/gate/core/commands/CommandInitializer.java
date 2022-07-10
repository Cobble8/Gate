package mod.gate.core.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import mod.gate.utils.ChatUtils;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;

public class CommandInitializer {
    private static final ArrayList<CommandRoute> commandRoutes = new ArrayList<>();

    public static void registerCommand(CommandRoute command) {
        commandRoutes.add(command);
    }

    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(CommandManager.literal("gate")
                    .then(CommandManager.argument("help", StringArgumentType.greedyString()))
                    .executes(CommandInitializer::execute)
            );
        });
    }

    private static int execute(CommandContext<ServerCommandSource> ctx) {
        String command = StringArgumentType.getString(ctx, "help");
        if (command.equals("help")) {
            ChatUtils.sendChatMessage("Work In Progress");
        }

        for (CommandRoute route : commandRoutes) {
            if (command.equals(route.path)) route.function.call();
            return 1;
        }

        return 0;
    }
}

