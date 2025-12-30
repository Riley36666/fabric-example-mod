package com.example;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class bigGui extends Screen {

    private static final int PANEL_WIDTH = 220;
    private static final int PANEL_HEIGHT = 200;

    public bigGui() {
        super(Text.literal("Client Control Panel"));
    }

    @Override
    protected void init() {
        int centerX = width / 2;
        int startY = height / 2 - 60;
        int buttonWidth = 160;
        int buttonHeight = 22;
        int spacing = 26;

        MinecraftClient client = MinecraftClient.getInstance();

        // Teleport up
        addDrawableChild(ButtonWidget.builder(
                Text.literal("Teleport +10Y"),
                b -> {
                    if (client.player != null) {
                        BlockPos pos = client.player.getBlockPos().up(10);
                        client.player.requestTeleport(
                                pos.getX() + 0.5,
                                pos.getY(),
                                pos.getZ() + 0.5
                        );
                    }
                }
        ).dimensions(centerX - buttonWidth / 2, startY, buttonWidth, buttonHeight).build());

        // Toggle night vision
        addDrawableChild(ButtonWidget.builder(
                Text.literal("Toggle Night Vision"),
                b -> {
                    if (client.player != null) {
                        if (client.player.hasStatusEffect(StatusEffects.NIGHT_VISION)) {
                            client.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
                        } else {
                            client.player.addStatusEffect(
                                    new StatusEffectInstance(
                                            StatusEffects.NIGHT_VISION,
                                            20 * 60 * 5,
                                            0,
                                            false,
                                            false
                                    )
                            );
                        }
                    }
                }
        ).dimensions(centerX - buttonWidth / 2, startY + spacing, buttonWidth, buttonHeight).build());

        // Clear chat
        addDrawableChild(ButtonWidget.builder(
                Text.literal("Clear Chat"),
                b -> {
                    if (client.inGameHud != null) {
                        client.inGameHud.getChatHud().clear(true);
                    }
                }
        ).dimensions(centerX - buttonWidth / 2, startY + spacing * 2, buttonWidth, buttonHeight).build());

        // Copy dimension + coords
        addDrawableChild(ButtonWidget.builder(
                Text.literal("Copy Location"),
                b -> {
                    if (client.player != null) {
                        BlockPos pos = client.player.getBlockPos();
                        String dim = client.world.getRegistryKey().getValue().toString();
                        String text = dim + " | " + pos.getX() + " " + pos.getY() + " " + pos.getZ();
                        client.keyboard.setClipboard(text);
                        client.player.sendMessage(Text.literal("Copied location"), false);
                    }
                }
        ).dimensions(centerX - buttonWidth / 2, startY + spacing * 3, buttonWidth, buttonHeight).build());

        // Close
        addDrawableChild(ButtonWidget.builder(
                Text.literal("Close"),
                b -> close()
        ).dimensions(centerX - buttonWidth / 2, startY + spacing * 4 + 10, buttonWidth, buttonHeight).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        // SAFE: no blur, no crash
        renderInGameBackground(context);

        int x1 = width / 2 - PANEL_WIDTH / 2;
        int y1 = height / 2 - PANEL_HEIGHT / 2;

        context.fill(x1, y1, x1 + PANEL_WIDTH, y1 + PANEL_HEIGHT, 0xCC0E1016);

        context.drawCenteredTextWithShadow(
                textRenderer,
                "Client Control Panel",
                width / 2,
                y1 + 10,
                0xFFFFFF
        );

        super.render(context, mouseX, mouseY, delta);
    }


    @Override
    public boolean shouldPause() {
        return false;
    }
}
