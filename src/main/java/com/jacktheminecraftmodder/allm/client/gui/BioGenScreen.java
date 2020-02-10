package com.jacktheminecraftmodder.allm.client.gui;

import com.jacktheminecraftmodder.allm.Reference;
import com.jacktheminecraftmodder.allm.content.Tileentities.BioGenTileEntity;
import com.jacktheminecraftmodder.allm.content.containers.BioGeneratorContainer;
import com.jacktheminecraftmodder.allm.util.ModEnergyStorage;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class BioGenScreen extends ContainerScreen<BioGeneratorContainer> {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/gui/bio_generator.png");

    public BioGenScreen(final BioGeneratorContainer container, final PlayerInventory inventory, final ITextComponent title) {
        super(container, inventory, title);
    }

    @Override
    public void render(final int mouseX, final int mouseY, final float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);

        int relMouseX = mouseX - this.guiLeft;
        int relMouseY = mouseY - this.guiTop;
        boolean energyBarHovered = relMouseX > 151 && relMouseX < 166 && relMouseY > 10 && relMouseY < 76;
        if (energyBarHovered) {
            String tooltip = new TranslationTextComponent("gui." + Reference.MOD_ID + ".energy",this.container.tileEntity.energy.getEnergyStored()
            ).getFormattedText();
            this.renderTooltip(tooltip + " " + container.tileEntity.energy.getEnergyStored(), mouseX, mouseY);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        // Copied from AbstractFurnaceScreen#drawGuiContainerForegroundLayer
        String s = this.title.getFormattedText();
        this.font.drawString(s, (float) (this.xSize / 2 - this.font.getStringWidth(s) / 2), 6.0F, 0x404040);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float) (this.ySize - 96 + 2), 0x404040);
      //  this.font.drawString("Mystic Energy" + container.tileEntity.energy.getEnergyStored(), 10, 10, 0x404040);
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

        final BioGenTileEntity tileEntity = container.tileEntity;

        final ModEnergyStorage energy = tileEntity.energy;
        final int energyStored = energy.getEnergyStored();
        if (energyStored > 0) { // Draw energy bar
            final int energyProgress = Math.round((float) energyStored / energy.getMaxEnergyStored() * 65);
            this.blit(
                    startX + 152, startY + 10 + 65 - energyProgress,
                    176, 14,
                    14, energyProgress
            );
        }

        if (!tileEntity.inventory.getStackInSlot(BioGenTileEntity.FUEL_SLOT).isEmpty()) // Draw flames
            this.blit(
                    startX + 81, startY + 58,
                    176, 0,
                    14, 14
            );
    }

}
