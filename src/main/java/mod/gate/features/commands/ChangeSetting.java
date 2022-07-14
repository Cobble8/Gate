package mod.gate.features.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import mod.gate.core.command.Command;
import mod.gate.core.command.CommandBuilder;
import mod.gate.core.config.ConfigHolder;
import mod.gate.core.config.ConfigManager;
import mod.gate.utils.ChatUtils;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.Objects;

public class ChangeSetting implements Command {

    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> register() {
        CommandBuilder commandBuilder = new CommandBuilder(ClientCommandManager.literal("gate"), this);

        for (ConfigHolder config : ConfigManager.getConfigs()) {
            if (config.getField().getType().isPrimitive()) {
                //primitive type
                if (config.getField().getType().getName() == "boolean") {
                    commandBuilder.addBooleanArgument("changesetting " + config.getField().getName());
                }
                else {
                    commandBuilder.addArgument("changesetting " + config.getField().getName(), "value", StringArgumentType.greedyString());
                }

            } else {
                //Object type
                for (Field field: config.getField().getType().getFields()) {
                    if (field.getType().getName() == "boolean") {
                        commandBuilder.addBooleanArgument("changesetting " + config.getField().getName() + " " + field.getName());
                    } else {
                        commandBuilder.addArgument("changesetting " + config.getField().getName() + " " + field.getName(), "value", StringArgumentType.greedyString());
                    }
                }
            }
        }

        return (commandBuilder.getBuilder());
    }

    public int execute(CommandContext<FabricClientCommandSource> context) {
        //0 and 1 are commands (gate changesetting) 2 is setting name and 3 is either path or value and 4 is null or value
        String[] args = context.getInput().split(" ");

        for (ConfigHolder config: ConfigManager.getConfigs()) {
            //skip if isn't the same
            if (!config.getField().getName().equals(args[2])) continue;


            try {
                if (args.length > 4) {
                    //has path and value

                    for (Field field : config.getField().getType().getFields()) {
                        if (!field.getName().equals(args[3])) continue;
                        config.set(field.getName(), parsePrimitive(args[4], field.getType().getName()));

                        ChatUtils.sendChatMessage("succcessfully set " + config.getField().getName() + "." + field.getName() + " to " + args[4]);
                    }

                } else {
                    config.setConfig(parsePrimitive(args[3], config.getField().getType().getName()));

                    ChatUtils.sendChatMessage("succcessfully set " + config.getField().getName() + " to " + args[3]);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }


        }
        ConfigManager.save();
        return 1;
    }

    public Object parsePrimitive(String string, String type) throws Exception {
        try {
            switch (type) {
                case "boolean" -> {
                    Boolean bool = null;
                    if (Objects.equals(string, "true"))
                        bool = true;
                    if (Objects.equals(string, "false"))
                        bool = false;
                    if (Boolean.TRUE.equals(bool == null)) throw new ParseException(string + " is not a boolean", 0);

                    return bool;
                }
                case "string" -> {
                    return string;
                }
                case "long" -> {
                    return Long.parseLong(string);
                }
                case "int" -> {
                    return Integer.parseInt(string);
                }
                case "byte" -> {
                    return Byte.parseByte(string);
                }
                case "double" -> {
                    return Double.parseDouble(string);
                }
                case "float" -> {
                    return Float.parseFloat(string);
                }
                case "short" -> {
                    return Short.parseShort(string);
                }
                default -> {
                    ChatUtils.sendChatMessage("§c" + type + " type is not a primitive type or cannot be parsed");
                    throw new Exception("type could not be parsed");
                }
            }

        } catch (NumberFormatException | ParseException e) {
            ChatUtils.sendChatMessage("§cexpected type §6" + type);
            throw new Exception();
        }
    }
}
