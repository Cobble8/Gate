package mod.gate.core.events;

import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class RenderEvent {

    public MatrixStack matrix;
    public float tickDelta;
    public CallbackInfo info;

    public RenderEvent(MatrixStack matrix, float tickDelta, CallbackInfo ci) {
        this.matrix = matrix;
        this.tickDelta = tickDelta;
        this.info = ci;
    }

    public void cancel() {
        if (this.info.isCancellable())
            this.info.cancel();
    }

}
