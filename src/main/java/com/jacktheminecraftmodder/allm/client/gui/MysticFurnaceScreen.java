package com.jacktheminecraftmodder.allm.client.gui;

import com.jacktheminecraftmodder.allm.Reference;
import com.jacktheminecraftmodder.allm.content.Tileentities.MysticFurnaceTile;
import com.jacktheminecraftmodder.allm.content.containers.MysticFurnaceContainer;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.energy.EnergyStorage;

public class MysticFurnaceScreen extends ContainerScreen<MysticFurnaceContainer> {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/gui/mystic_furnace.png");

    public MysticFurnaceScreen(final MysticFurnaceContainer container, final PlayerInventory inventory, final ITextComponent title) {
        super(container, inventory, title);
    }

    @Override
    public void render(final int mouseX, final int mouseY, final float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);

        int relMouseX = mouseX - this.guiLeft;
        int relMouseY = mouseY - this.guiTop;
        final MysticFurnaceTile tileEntity = this.container.tileEntity;
        boolean energyBarHovered = relMouseX > 151 && relMouseX < 166 && relMouseY > 10 && relMouseY < 76;
        if (energyBarHovered) {
            String tooltip = new TranslationTextComponent(
                    "gui." + Reference.MOD_ID + ".energy",
                    tileEntity.energy.getEnergyStored()
            ).getFormattedText();
            this.renderTooltip(tooltip + " " + container.tileEntity.energy.getEnergyStored(), mouseX, mouseY);
        }


        boolean arrowHovered = relMouseX > 72 && relMouseX < 97 && relMouseY > 30 && relMouseY < 47;
        if (arrowHovered && tileEntity.maxSmeltTime > 0) {
            String tooltip = new TranslationTextComponent(
                    "gui." + Reference.MOD_ID + ".smeltTimeProgress",
                    tileEntity.smeltTimeLeft, tileEntity.maxSmeltTime
            ).getFormattedText();
            this.renderTooltip(tooltip, mouseX, mouseY);
        }

    }

    @Override
    protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        String s = this.title.getFormattedText();
        this.font.drawString(s, (float) (this.xSize / 2 - this.font.getStringWidth(s) / 2), 6.0F, 0x404040);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float) (this.ySize - 96 + 2), 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        getMinecraft().getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        int startX = this.guiLeft;
        int startY = this.guiTop;

        // Screen#blit draws a part of the current texture (assumed to be 256x256) to the screen
        // The parameters are (x, y, u, v, width, height)

        this.blit(startX, startY, 0, 0, this.xSize, this.ySize);

        final MysticFurnaceTile tileEntity = container.tileEntity;
        if (tileEntity.energy.getEnergyStored() > 0) { // Draw energy bar
            int energyProgress = getEnergyProgressScaled();
            this.blit(
                    startX + 152, startY + 10 + 65 - energyProgress,
                    176, 30,
                    14, energyProgress
            );
        }

        if (tileEntity.smeltTimeLeft > 0) {
            // Draw progress arrow
            int arrowWidth = getSmeltTimeScaled();
            this.blit(startX + 72, startY + 30, 176, 14, arrowWidth, 16);
        }
    }

    private int getEnergyProgressScaled() {
        final MysticFurnaceTile tileEntity = this.container.tileEntity;
        final EnergyStorage energy = tileEntity.energy;
        final int energyStored = energy.getEnergyStored();
        final int maxEnergyStored = energy.getMaxEnergyStored();
        return Math.round((float) energyStored / maxEnergyStored * 65); // 65 is the height of the arrow
    }


    private int getSmeltTimeScaled() {
        final MysticFurnaceTile tileEntity = this.container.tileEntity;
        final short smeltTimeLeft = tileEntity.smeltTimeLeft;
        final short maxSmeltTime = tileEntity.maxSmeltTime;
        if (smeltTimeLeft <= 0 || maxSmeltTime <= 0)
            return 0;
        return (maxSmeltTime - smeltTimeLeft) * 24 / maxSmeltTime;
    }

}
