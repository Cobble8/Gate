package mod.gate.mixin;


import mod.gate.events.ActionbarReceiveEvent;
import mod.gate.events.EventHandler;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class ActionbarMixin {

    private static String cachedContent;


    @Inject(at = @At("HEAD"), method = "setOverlayMessage")
    private void setOverlayMessageMixin(Text message, boolean tinted, CallbackInfo ci) {
        if (!message.asString().equals(cachedContent)) {
            //only run if it's different than the current stored cache
            cachedContent = message.asString();


            //run event with updated data
            EventHandler.run(new ActionbarReceiveEvent(message, tinted, 60, ci));
        }
    }

    public String getCachedContent() {
        return cachedContent;
    }
}

