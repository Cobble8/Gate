package mod.gate.features;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.gate.core.events.Event;
import mod.gate.core.events.FrameEvent;
import mod.gate.utils.Reference;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class GuiTesting {

    private final Identifier github = new Identifier(Reference.MODID, "textures/github.png");


    @Event(event = FrameEvent.class)
    public void draw(FrameEvent event) {
        RenderSystem.setShaderTexture(0, github);
        DrawableHelper.drawTexture(new MatrixStack(), 10, 10, 0, 0, 64, 64, 64, 64);
    }

}
