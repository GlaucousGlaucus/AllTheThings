package com.jacktheminecraftmodder.allm.client.gui;

import com.jacktheminecraftmodder.allm.Reference;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.fonts.TextInputUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public class ScrollScreen extends Screen {

    private static final int WIDTH = 152;
    private static final int HEIGHT = 188;

    private int selectionEnd;
    private int selectionStart;
    private TextInputUtil textInputUtil;

    private ResourceLocation GUI = new ResourceLocation(Reference.MOD_ID, "textures/gui/scroll.png");


    public ScrollScreen() {
        super(new StringTextComponent("Scroll"));
    }

    @Override
    protected void init() {
        int relX = (this.width - WIDTH) / 2;
        int relY = (this.height - HEIGHT) / 2;

        addButton(new Button(relX + 50, relY + 170, 40, 25, "Done", button -> doneEditing()));
    }

    private void doneEditing() {
        minecraft.displayGuiScreen(null);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - WIDTH) / 2;
        int relY = (this.height - HEIGHT) / 2;
        this.blit(relX, relY, 0, 0, WIDTH, HEIGHT);
        super.render(mouseX, mouseY, partialTicks);
    }

    public static void readScroll() {
        Minecraft.getInstance().displayGuiScreen(new ScrollScreen());
    }

}
