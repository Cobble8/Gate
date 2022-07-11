package mod.gate.core.events;

import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class JoinWorldEvent {
    public CallbackInfo info;

    //region packet
    //might not be useful but including it for now
    private final GameJoinS2CPacket packet;

    public GameJoinS2CPacket getPacket() {
        return this.packet;
    };
    //endregion

    public JoinWorldEvent(GameJoinS2CPacket packet, CallbackInfo info) {
        this.info = info;
        this.packet = packet;
    }
}
