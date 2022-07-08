package mod.gate.events;

import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class ActionbarReceiveEvent {
    public Text text;
    public boolean tinted;
    public CallbackInfo info;

    public int timeRemaining;

    public String content;

    public ActionbarReceiveEvent(Text text, boolean tinted, int timeRemaining, CallbackInfo info) {
        this.text = text;
        this.tinted = tinted;
        this.info = info;
        this.timeRemaining = timeRemaining;

        this.content = text.getString();
    }

    public void cancel() {
        this.info.cancel();
    }
}
