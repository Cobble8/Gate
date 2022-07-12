package mod.gate.core.mixin;


import mod.gate.core.events.ActionbarReceiveEvent;
import mod.gate.core.events.EventHandler;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class ActionbarMixin {
    private static String cachedContent;

    @Inject(at = @At("HEAD"), method = "setOverlayMessage", cancellable = true)
    private void setOverlayMessage(Text message, boolean tinted, CallbackInfo info) {
        if (!message.asString().equals(cachedContent)) {
            //only run if it's different than the current stored cache
            cachedContent = message.asString();


            //run event with updated data
            EventHandler.run(new ActionbarReceiveEvent(message, tinted, 60, info));
        }
    }
}

