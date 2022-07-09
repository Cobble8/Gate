package mod.gate.core.events.ingamehud;

import mod.gate.core.events.UpdateEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

public class ScoreboardUpdateEvent extends UpdateEvent {
    //required
    public ArrayList<String> Lines;
    public boolean onSkyblock;

    public ScoreboardUpdateEvent(ArrayList<String> lines, boolean onSkyblock, long lastUpdated, CallbackInfo info) {
        super(lastUpdated, info);

        this.Lines = lines;
        this.onSkyblock = onSkyblock;
    }
}
