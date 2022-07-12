package mod.gate.core.command;

import com.mojang.brigadier.CommandDispatcher;
import mod.gate.features.commands.GateSave;
import mod.gate.core.config.ConfigManager;
import net.minecraft.server.command.ServerCommandSource;

public class CommandRegistry {
    public static void register(Object command, CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        if (ConfigManager.inDebugMode()) System.out.println("registering command: " + ((Command) command).getName());
        ((Command) command).register(dispatcher, dedicated);
    }

    public static void init(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        register(new GateSave(), dispatcher, dedicated);
    }
}
