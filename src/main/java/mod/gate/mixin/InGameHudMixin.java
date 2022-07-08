package mod.gate.mixin;


import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    private static String cachedContent;


    @Inject(at = @At("HEAD"), method = "setOverlayMessage")
    private void setOverlayMessageMixin(Text message, boolean tinted, CallbackInfo ci) {
        System.out.println(message.asString());
    }

    public String getCachedContent() {
        return cachedContent;
    }
}

