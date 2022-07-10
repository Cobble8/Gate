package mod.gate.core.mixin;

import mod.gate.core.events.ChatReceiveEvent;
import mod.gate.core.events.EventHandler;
import net.minecraft.client.gui.hud.ChatHudListener;
import net.minecraft.network.MessageType;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(ChatHudListener.class)
public abstract class ClientChatReceiveMixin {
    @Inject(at = @At(value = "HEAD"), method = "onChatMessage", cancellable = true)
    private void receiveChatMessage(MessageType type, Text message, UUID sender, CallbackInfo info) {
        if (type == MessageType.CHAT)
            EventHandler.run(new ChatReceiveEvent(type, message, sender, info));
    }
}
