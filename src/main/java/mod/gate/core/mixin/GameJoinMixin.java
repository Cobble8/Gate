package mod.gate.core.mixin;

import mod.gate.core.events.EventHandler;
import mod.gate.core.events.JoinWorldEvent;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(ClientPlayNetworkHandler.class)
public class GameJoinMixin {

    @Inject(at=@At("HEAD"), method = "onGameJoin")
    public void joinWorldEventMixin(GameJoinS2CPacket packet, CallbackInfo info) {
        EventHandler.run(new JoinWorldEvent(packet, info));
    }
}
