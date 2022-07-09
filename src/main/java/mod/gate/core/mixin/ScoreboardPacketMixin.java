package mod.gate.core.mixin;

import mod.gate.core.events.EventHandler;
import mod.gate.core.events.ingamehud.ScoreboardUpdateEvent;
import mod.gate.utils.Scoreboard;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.TeamS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ScoreboardPacketMixin {
    private long lastUpdateAt = 0;

    @Inject(at= @At("TAIL"), method = "onTeam")
    public void onTeamMixin(TeamS2CPacket packet, CallbackInfo ci) {
        //trigger post operations

        if (lastUpdateAt + 10000 < System.currentTimeMillis()) {//limits the update to every 10 seconds
            Scoreboard.loadScoreboard();

            lastUpdateAt = System.currentTimeMillis();

            //send event
            EventHandler.run(new ScoreboardUpdateEvent(Scoreboard.getLines(), Scoreboard.isOnSkyblock(), lastUpdateAt, ci));
        }
    }
}
