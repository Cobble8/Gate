package mod.gate.commands.test;

import com.mojang.brigadier.context.CommandContext;
import mod.gate.commands.Command;
import mod.gate.utils.ChatUtils;
import net.minecraft.server.command.ServerCommandSource;

public class testCommand extends Command {

    public testCommand() {
        super("trolling");

        args.add(new CommandArg("testering"));
        args.add(new CommandArg("arg2"));
    }

    @Override
    public int run(CommandContext<ServerCommandSource> context) {
        ChatUtils.sendChatMessage(context.getArgument("testering", String.class));
        ChatUtils.sendChatMessage(context.getArgument("arg2", String.class));
        return 1;
    }

}
