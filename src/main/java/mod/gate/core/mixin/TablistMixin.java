package mod.gate.core.mixin;

import mod.gate.core.events.ActionbarReceiveEvent;
import mod.gate.core.events.EventHandler;
import mod.gate.core.events.TablistEvent;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class TablistMixin {

    @Inject(method = "onPlayerList", at = @At("TAIL"))
    private void onPlayerList(PlayerListS2CPacket packet, CallbackInfo info) {
        EventHandler.run(new TablistEvent(packet, info));
    }
}
