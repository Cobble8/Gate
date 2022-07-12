package mod.gate.core.events;

import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class TablistEvent {
    public PlayerListS2CPacket packet;
    public CallbackInfo info;

    public TablistEvent(PlayerListS2CPacket packet, CallbackInfo info) {
        this.packet = packet;
        this.info = info;
    }
}
