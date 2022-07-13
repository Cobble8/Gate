package mod.gate.features.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import mod.gate.core.command.Command;
import mod.gate.core.config.ConfigManager;
import mod.gate.utils.ChatUtils;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

public class GateSave implements Command {
    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> register() {
        return (ClientCommandManager.literal("gate")
                .then(ClientCommandManager.literal("save").executes(this::run)));
    }

    public int run(CommandContext<FabricClientCommandSource> context) {
        ConfigManager.save();
        ChatUtils.sendChatMessage("Saving Configuration!");
        return 1;
    }
}

