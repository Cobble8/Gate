package mod.gate.core.mixin;

import mod.gate.Gate;
import mod.gate.core.events.EventHandler;
import mod.gate.core.events.RenderEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(InGameHud.class)
public class RenderEventMixin {
    @Inject(method = "render", at = @At(value = "HEAD"))
    public void render(MatrixStack matrixStack, float tickDelta, CallbackInfo info) {
        EventHandler.run(new RenderEvent(matrixStack, tickDelta, info));
        //This needs to go somewhere, and it's not really a feature. So here it is.
        //assert MinecraftClient.getInstance().currentScreen != null;
        MinecraftClient mc = MinecraftClient.getInstance();
        if(mc.currentScreen != null) {
            Gate.width = mc.currentScreen.width;
            Gate.height = mc.currentScreen.height;
        }


    }
}
