package mod.gate.mixin;

import mod.gate.events.CommandEvent;
import mod.gate.events.EventHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {
    @Inject(at = @At(value = "HEAD"), method = "sendChatMessage", cancellable = true)
    private void sendChatMessage(String message, CallbackInfo info)
    {
        if (message.startsWith("/")) {
            EventHandler.run(new CommandEvent(message, info));
        }
    }
}
