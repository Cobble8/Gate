package mod.gate.events.ingamehud;

import net.minecraft.network.MessageType;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

public class ChatReceiveEvent {
    public MessageType type;
    public Text text;
    public UUID sender;
    public CallbackInfo info;
    public String message;

    public ChatReceiveEvent(MessageType type, Text text, UUID sender, CallbackInfo info) {
        this.type = type;
        this.text = text;
        this.sender = sender;
        this.info = info;

        this.message = text.getString();
    }

    public void cancel() {
        this.info.cancel();
    }
}
