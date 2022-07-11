package mod.gate.features;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.gate.core.config.Config;
import mod.gate.core.events.Event;
import mod.gate.core.events.RenderEvent;
import mod.gate.utils.Reference;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

//This is a test class
public class HudTesting {

    private final Identifier github = new Identifier(Reference.MODID, "textures/github.png");

    @Config(description = "A random config value that will be written to config file. For Ascynx: this is an example of how to use this, mainly for Cobble")
    public final boolean RandomConfigOption = true;

    @Event(event = RenderEvent.class)
    public void render(RenderEvent event) {
        RenderSystem.setShaderTexture(0, github);
        DrawableHelper.drawTexture(new MatrixStack(), 10, 10, 0, 0, 64, 64, 64, 64);
    }
}
