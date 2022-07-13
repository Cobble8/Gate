package mod.gate.core.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

public interface Command {
    LiteralArgumentBuilder<FabricClientCommandSource> register();

    default String getName() {
        return this.getClass().getSimpleName();
    }
}

