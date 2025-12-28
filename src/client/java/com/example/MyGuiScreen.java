package com.example;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class MyGuiScreen extends Screen {

    private static final int PANEL_WIDTH = 220;
    private static final int PANEL_HEIGHT = 200;

    public MyGuiScreen() {
        super(Text.literal("Client GUI"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int startY = this.height / 2 - 60;
        int buttonWidth = 160;
        int buttonHeight = 20;
        int spacing = 24;

        // Get items (server command)
        this.addDrawableChild(
                ButtonWidget.builder(Text.literal("Get items"), button -> {
                    MinecraftClient client = MinecraftClient.getInstance();
                    if (client.player != null && client.player.networkHandler != null) {
                        client.player.networkHandler.sendChatCommand("getitems");
                    }
                }).dimensions(centerX - buttonWidth / 2, startY, buttonWidth, buttonHeight).build()
        );

        // Say hello (client chat)
        this.addDrawableChild(
                ButtonWidget.builder(Text.literal("Say hello"), button -> {
                    MinecraftClient client = MinecraftClient.getInstance();
                    if (client.player != null) {
                        client.player.sendMessage(Text.literal("Hello world 👋"), false);
                    }
                }).dimensions(centerX - buttonWidth / 2, startY + spacing, buttonWidth, buttonHeight).build()
        );

        // Toggle FOV (client-only fun)
        this.addDrawableChild(
                ButtonWidget.builder(Text.literal("Toggle FOV"), button -> {
                    MinecraftClient client = MinecraftClient.getInstance();
                    if (client.options != null) {
                        double fov = client.options.getFov().getValue();
                        client.options.getFov().setValue(fov > 70 ? 70 : 110);
                    }
                }).dimensions(centerX - buttonWidth / 2, startY + spacing * 2, buttonWidth, buttonHeight).build()
        );

        // Copy coordinates
        this.addDrawableChild(
                ButtonWidget.builder(Text.literal("Copy coords"), button -> {
                    MinecraftClient client = MinecraftClient.getInstance();
                    if (client.player != null) {
                        BlockPos pos = client.player.getBlockPos();
                        String coords = pos.getX() + " " + pos.getY() + " " + pos.getZ();
                        client.keyboard.setClipboard(coords);
                        client.player.sendMessage(Text.literal("Copied: " + coords), false);
                    }
                }).dimensions(centerX - buttonWidth / 2, startY + spacing * 3, buttonWidth, buttonHeight).build()
        );

        // Close GUI
        this.addDrawableChild(
                ButtonWidget.builder(Text.literal("Close"), button -> {
                    this.close();
                }).dimensions(centerX - buttonWidth / 2, startY + spacing * 4 + 10, buttonWidth, buttonHeight).build()
        );
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        int panelX1 = this.width / 2 - PANEL_WIDTH / 2;
        int panelY1 = this.height / 2 - PANEL_HEIGHT / 2;
        int panelX2 = this.width / 2 + PANEL_WIDTH / 2;
        int panelY2 = this.height / 2 + PANEL_HEIGHT / 2;

        // Panel background
        context.fill(panelX1, panelY1, panelX2, panelY2, 0xAA000000);

        // Title
        context.drawCenteredTextWithShadow(
                this.textRenderer,
                "Fun Client GUI",
                this.width / 2,
                panelY1 + 10,
                0xFFFFFF
        );

        super.render(context, mouseX, mouseY, delta);
    }




    @Override
    public boolean shouldPause() {
        return false;
    }
}
