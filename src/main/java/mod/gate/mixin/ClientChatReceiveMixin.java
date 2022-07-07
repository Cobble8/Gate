package mod.gate.mixin;

import mod.gate.events.ChatRecieveEvent;
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
    private void receiveChatMessage(MessageType type, Text message, UUID sender, CallbackInfo ci) {
        if(type == MessageType.CHAT) {
            boolean cancel = ChatRecieveEvent.onChatReceived(message.getString());
            if(cancel) { ci.cancel(); }
        } else if(type == MessageType.GAME_INFO) {

        } else if(type == MessageType.SYSTEM) {

        }
    }
}
