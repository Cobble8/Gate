package mod.gate.features;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.gate.Gate;
import mod.gate.core.events.ChatReceiveEvent;
import mod.gate.core.events.Event;
import mod.gate.core.events.RenderEvent;
import mod.gate.utils.ChatUtils;
import net.minecraft.client.render.*;

import java.awt.*;
import java.util.Locale;

public class MiningAbilityTimer {

    //private final Identifier blank = new Identifier(Reference.MODID, "textures/blank.png");
    private static int r = 255;
    private static int g = 255;
    private static int b = 255;
    private static int a = 255;
    private static int radius = 11;
    private static double width = 0.75;


    @Event(event = RenderEvent.class)
    public void onRender(RenderEvent event) {
        //RenderSystem.setShaderTexture(0, blank);

        int x = Gate.width/2;
        int y = Gate.height/2;

        int percent = (int) ((System.currentTimeMillis()/100)%100);
        Color color = new Color(r, g, b, a);
        drawAntiAliasPolygon(x, y, radius, width, percent, color);
        System.out.println("HELLO");

    }

    /**
     * @param width 0-0.75 (amount of space from center to inner circle
     */
    private static void drawCircle(double x, double y, double radius, double width, double percent, Color color) {
        try {
            int sides = 63;
            double per = percent*sides/100;
            Tessellator tess =  Tessellator.getInstance();
            BufferBuilder buff = tess.getBuffer();
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            buff.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
            float r = color.getRed()/255f;
            float g = color.getGreen()/255f;
            float b = color.getBlue()/255f;
            float a = color.getAlpha()/255f;
            System.out.println(a);
            System.out.println(color.getAlpha());
            System.out.println();
            for(int i=0;i<per;i++) {
                double angle = (Math.PI*2 * i / sides) + Math.toRadians(180);
                buff.vertex(x + Math.sin(angle) * radius * width, y + Math.cos(angle) * radius * width, 0).color(r, g, b, a).next();
                buff.vertex(x + Math.sin(angle) * radius, y + Math.cos(angle) * radius, 0).color(r, g, b, a).next();
                buff.vertex(x + Math.sin(angle+0.1d) * radius, y + Math.cos(angle+0.1d) * radius, 0).color(r, g, b, a).next();
                buff.vertex(x + Math.sin(angle+0.1d) * radius * width, y + Math.cos(angle+0.1d) * radius * width, 0).color(r, g, b, a).next();
            }
            tess.draw();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void drawAntiAliasPolygon(double x, double y, double radius, double width, double percent, Color color) {
        drawCircle(x, y, radius+1.5, width, percent, new Color(color.getRed(), color.getGreen(), color.getBlue(), (float) (color.getAlpha())/2f));
        drawCircle(x, y, radius-1.5, width, percent, new Color(color.getRed(), color.getGreen(), color.getBlue(), (float) (color.getAlpha())/2f));
    }

    @Event(event = ChatReceiveEvent.class)
    public void onChatReceive(ChatReceiveEvent event) {
        String msg = ChatUtils.strip(event.message).substring(10);

        try {
            String[] args = msg.split(" ");
            String cmd = args[0];
            switch (cmd.toLowerCase(Locale.ROOT)) {
                case "color" -> {
                    r = Integer.parseInt(args[1]);
                    g = Integer.parseInt(args[2]);
                    b = Integer.parseInt(args[3]);
                    a = Integer.parseInt(args[4]);
                }
                case "radius" -> radius = Integer.parseInt(args[1]);
                case "width" -> width = Double.parseDouble(args[1]);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }


    }

}
