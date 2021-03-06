package mod.gate.core.events;

import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.regex.Pattern;

public class ActionbarReceiveEvent {
    //region Patterns
    public final Pattern baseActionbarParser = Pattern.compile("§6(?<currHealth>[0-9].*)/(?<maxHealth>[0-9].*)❤     §a(?<defense>[0-9].*)§a❈ Defense     §b(?<currMana>[0-9].*)/(?<maxMana>[0-9].*)✎ Mana");
    //TODO add other patterns for action bar (when abilities are used, true defense?, etc...)
    //endregion

    //region required fields
    public Text text;
    public boolean tinted;
    public CallbackInfo info;
    //endregion

    //region Convenience Fields
    public String content;
    public int timeRemaining;
    //endregion

    public ActionbarReceiveEvent(Text text, boolean tinted, int timeRemaining, CallbackInfo info) {
        this.info = info;
        this.text = text;
        this.tinted = tinted;
        this.content = text.getString();
        this.timeRemaining = timeRemaining;
    }

    public void cancel() {
        if (this.info.isCancellable())
            this.info.cancel();
    }
}
