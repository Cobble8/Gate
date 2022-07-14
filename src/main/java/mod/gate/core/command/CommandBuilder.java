package mod.gate.core.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

import java.util.Arrays;

import static org.apache.commons.lang3.ArrayUtils.reverse;

public class CommandBuilder {
    private final LiteralArgumentBuilder<FabricClientCommandSource> builder;
    private final Command parentCommand;


    public LiteralArgumentBuilder<FabricClientCommandSource> getBuilder() {
        return this.builder;
    }

    public LiteralCommandNode<FabricClientCommandSource> build() {
        return this.builder.build();
    }

    public Command getparentCommand() {
        return this.parentCommand;
    }

    public CommandBuilder(LiteralArgumentBuilder<FabricClientCommandSource> literalArgumentBuilder, Object parentCommand) {
        this.builder = literalArgumentBuilder;
        this.parentCommand = (Command) parentCommand;
    }

    public void addOption(String path, String option) {
        String[] pathItems = path.split(" ");
        LiteralArgumentBuilder<FabricClientCommandSource> currentInnerBuilder = ClientCommandManager.literal(option).executes(parentCommand::execute);
        reverse(pathItems);

        for (String pathItem: pathItems) {
            currentInnerBuilder = ClientCommandManager.literal(pathItem).then(currentInnerBuilder);
        }
        builder.then(currentInnerBuilder);
    }

    public void addOption(String option) {
        //directly on root command
        builder.then(ClientCommandManager.literal(option).executes(parentCommand::execute));
    }

    public void addBooleanArgument(String path) {
        this.addOption(path, "true");
        this.addOption(path, "false");
    }

    public void addOptions(String path, String[] options) {
        for (String option: options) {
            this.addOption(path, option);
        }
    }

    public <T> void addArgument(String path, String name, ArgumentType<T> argumentType) {
        String[] pathItems = path.split(" ");
        LiteralArgumentBuilder<FabricClientCommandSource> currentInnerBuilder = ClientCommandManager.literal(pathItems[pathItems.length-1])
                .then(ClientCommandManager.argument(name, argumentType).executes(parentCommand::execute));
        pathItems = Arrays.copyOf(pathItems, pathItems.length-1);
        reverse(pathItems);

        for (String pathItem: pathItems) {
            currentInnerBuilder = ClientCommandManager.literal(pathItem).then(currentInnerBuilder);
        }

        builder.then(currentInnerBuilder);
    }

    public <T> void addArgument(String name, ArgumentType<T> argumentType) {
        //directly on root command
        builder.then(ClientCommandManager.argument(name, argumentType).executes(parentCommand::execute));
    }


}
