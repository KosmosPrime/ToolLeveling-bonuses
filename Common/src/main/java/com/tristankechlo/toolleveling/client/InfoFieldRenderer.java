package com.tristankechlo.toolleveling.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public final class InfoFieldRenderer {

    private List<FormattedCharSequence> lines = new ArrayList<>();
    private int spaceAfterTitle = 2;
    private final int backgroundColor;
    private final int borderColor1;
    private final int borderColor2;

    public InfoFieldRenderer(int backgroundColor, int borderColor1, int borderColor2) {
        this.backgroundColor = backgroundColor;
        this.borderColor1 = borderColor1;
        this.borderColor2 = borderColor2;
    }

    public void setSpaceAfterTitle(int spaceAfterTitle) {
        this.spaceAfterTitle = spaceAfterTitle;
    }

    public void setLines(List<Component> lines) {
        this.lines = lines.stream().map(Component::getVisualOrderText).toList();
    }

    public void setLines(List<Component> lines, Font font, int width) {
        List<FormattedCharSequence> temp = new ArrayList<>();
        for (Component line : lines) {
            var split = font.split(line, width);
            temp.addAll(split);
        }
        this.lines = List.copyOf(temp);
    }

    public int calcWidth(Font font) {
        int width = lines.stream().mapToInt(font::width).max().orElse(0);
        width += 8;
        return width;
    }

    public int calcHeight() {
        int height = 6 + (lines.size() >= 1 ? this.spaceAfterTitle : 0); // add 2 pixels for space after title, when required
        height += (lines.size() * 10);
        return height;
    }

    public void render(GuiGraphics graphics, Font font, int startX, int startY) {
        render(graphics, font, startX, startY, calcWidth(font));
    }

    public void render(GuiGraphics graphics, Font font, int startX, int startY, int width) {
        // if there are no lines, add the title as the first line, otherwise return
        if (this.lines.isEmpty()) {
            return;
        }

        // calculate width and height of the info field
        int height = calcHeight();

        graphics.pose().pushPose();
        graphics.pose().translate(startX, startY, 50);
        // render background
        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        Matrix4f matrix4f = graphics.pose().last().pose();
        CustomTooltipRenderer.renderBackground(matrix4f, bufferBuilder, 0, 0, width, height, backgroundColor, borderColor1, borderColor2);
        RenderSystem.enableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        BufferUploader.drawWithShader(bufferBuilder.end());

        // render text
        graphics.pose().translate(4, 4, 50);
        renderLines(matrix4f, graphics.bufferSource(), font, lines, spaceAfterTitle);
        graphics.bufferSource().endBatch();
        graphics.pose().popPose();
    }

    private static void renderLines(Matrix4f matrix4f, MultiBufferSource bufferSource, Font font, List<FormattedCharSequence> lines, int spaceAfterTitle) {
        int y = 0;
        for (int i = 0; i < lines.size(); i++) {
            FormattedCharSequence line = lines.get(i);
            font.drawInBatch(line, 0, y, -1, false, matrix4f, bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
            y += 10 + (i == 0 ? spaceAfterTitle : 0);
        }
    }

}
