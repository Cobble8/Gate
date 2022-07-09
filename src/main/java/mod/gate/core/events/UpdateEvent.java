package mod.gate.core.events;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class UpdateEvent {
    public CallbackInfo info;

    //update timestamps
    public long timeLeftTillUpdate;
    public long lastUpdatedTimestamp;

    public UpdateEvent(long lastUpdated, CallbackInfo info) {
        this.info = info;
        this.lastUpdatedTimestamp = lastUpdated;

        //convenience
        long nextUpdateTimestamp = lastUpdated + 10000;
        this.timeLeftTillUpdate = nextUpdateTimestamp - lastUpdatedTimestamp;

    }

    public void cancel() {
        this.info.cancel();
    }
}
