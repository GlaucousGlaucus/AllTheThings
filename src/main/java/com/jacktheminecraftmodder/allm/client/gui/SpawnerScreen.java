package com.jacktheminecraftmodder.allm.client.gui;

import com.jacktheminecraftmodder.allm.Reference;
import com.jacktheminecraftmodder.allm.network.Networking;
import com.jacktheminecraftmodder.allm.network.PacketSpawn;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SpawnerScreen extends Screen {

    private static final int WIDTH = 179;
    private static final int HEIGHT = 151;

    private ResourceLocation GUI = new ResourceLocation(Reference.MOD_ID, "textures/gui/mystic_crafting_table.png");


    public SpawnerScreen() {
        super(new StringTextComponent("Spawn"));
    }

    @Override
    protected void init() {
        int relX = (this.width - WIDTH) / 2;
        int relY = (this.height - HEIGHT) / 2;

      //  addButton(new Button(relX + 10, relY + 10, 18, 20, "<", button -> pageup()));
       // addButton(new Button(relX + 150, relY + 10, 18, 20, ">", button -> pagedn()));

       // pageOne();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private void pageup() {
        System.out.println("GG");
        pageTwo();
    }
    private void pagedn() {
        System.out.println("GG");
        pageOne();
    }

    private void pageOne() {

        int relX = (this.width - WIDTH) / 2;
        int relY = (this.height - HEIGHT) / 2;

        addButton(new Button(relX + 45, relY + 10, 100, 20, "Skeleton", button -> spawn("minecraft:skeleton")));
        addButton(new Button(relX + 45, relY + 37, 100, 20, "Zombie", button -> spawn("minecraft:zombie")));
        addButton(new Button(relX + 45, relY + 64, 100, 20, "Cow", button -> spawn("minecraft:cow")));
        addButton(new Button(relX + 45, relY + 91, 100, 20, "Sheep", button -> spawn("minecraft:sheep")));
        addButton(new Button(relX + 45, relY + 118, 100, 20, "The Unknown", button -> spawn("tuu:the_unknown")));
    }

    private void pageTwo() {

        int relX = (this.width - WIDTH) / 2;
        int relY = (this.height - HEIGHT) / 2;

        addButton(new Button(relX + 45, relY + 10, 100, 20, "S", button -> spawn("minecraft:skeleton")));
        addButton(new Button(relX + 45, relY + 37, 100, 20, "Z", button -> spawn("minecraft:zombie")));
        addButton(new Button(relX + 45, relY + 64, 100, 20, "C", button -> spawn("minecraft:cow")));
        addButton(new Button(relX + 45, relY + 91, 100, 20, "S", button -> spawn("minecraft:sheep")));
        addButton(new Button(relX + 45, relY + 118, 100, 20, "TU", button -> spawn("tuu:the_unknown")));
    }

    private void spawn(String id) {
        Networking.INSTANCE.sendToServer(new PacketSpawn(id, minecraft.player.dimension, minecraft.player.getPosition()));
        minecraft.displayGuiScreen(null);
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


    public static void open() {
        Minecraft.getInstance().displayGuiScreen(new SpawnerScreen());
    }

}
