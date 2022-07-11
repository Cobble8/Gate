package mod.gate.core.events;

import net.minecraft.world.World;

public class TickEvent {
    public World world;

    public TickEvent(World world) {
        this.world = world;
    }
}
