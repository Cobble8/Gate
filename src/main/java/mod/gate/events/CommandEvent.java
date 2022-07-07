package mod.gate.events;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CommandEvent {
    private final String commandName;
    private final List<String> arguments = new ArrayList<>();
    private final CallbackInfo info;


    public CommandEvent(String message, CallbackInfo info) {
        String[] text =  message.replace("/", "").split(" ");
        //System.out.println(Arrays.toString(text));
        this.commandName = text[0];
        //System.out.println(this.commandName);
        if (text.length > 1)
            this.arguments.addAll(List.of(Pattern.compile("/ /g").split(text[1])));
        //System.out.println(this.arguments);
        this.info = info;
    }

    public String getCommandName() {
        return commandName;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public CallbackInfo getInfo() {
        return info;
    }
}
