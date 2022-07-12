package mod.gate.core.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;

public interface Command {
    void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated);

    default String getName() {
        return this.getClass().getSimpleName();
    }
}

