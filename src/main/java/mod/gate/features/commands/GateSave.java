package mod.gate.features.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import mod.gate.core.command.Command;
import mod.gate.core.config.ConfigManager;
import mod.gate.utils.ChatUtils;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class GateSave implements Command {
    @Override
    public void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        dispatcher.register(CommandManager.literal("gate")
                .then(CommandManager.literal("save").executes(this::run)));
    }

    public int run(CommandContext<ServerCommandSource> context) {
        ConfigManager.save();
        ChatUtils.sendChatMessage("Saving Configuration!");
        return 1;
    }
}

