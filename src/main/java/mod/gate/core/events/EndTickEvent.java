package mod.gate.core.events;

import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class EndTickEvent {
    public World world;

    public EndTickEvent(World world) {
        this.world = world;
    }
}
