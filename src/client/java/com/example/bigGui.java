package com.example;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class bigGui extends Screen {

    private static final int WIDTH = 520;
    private static final int HEIGHT = 360;

    private static final int SIDEBAR_WIDTH = 110;
    private static final int BUTTON_WIDTH = 240;
    private static final int BUTTON_HEIGHT = 20;
    private static final int SPACING = 24;

    private Category selected = Category.MOVEMENT;
    private int scrollOffset = 0;

    public bigGui() {
        super(Text.literal("ClickGUI"));
    }

    /* ============================================================
       INIT
       ============================================================ */

    @Override
    protected void init() {
        clearChildren();

        int x = width / 2 - WIDTH / 2;
        int y = height / 2 - HEIGHT / 2;

        int sidebarX = x + 10;
        int sidebarY = y + 40;

        int i = 0;
        for (Category c : Category.values()) {
            addDrawableChild(ButtonWidget.builder(
                    Text.literal(c.display),
                    b -> {
                        selected = c;
                        scrollOffset = 0;
                        init();
                    }
            ).dimensions(
                    sidebarX,
                    sidebarY + i * 26,
                    SIDEBAR_WIDTH - 20,
                    20
            ).build());
            i++;
        }

        buildActions();
    }

    /* ============================================================
       ACTIONS
       ============================================================ */

    private void buildActions() {
        MinecraftClient c = MinecraftClient.getInstance();

        int baseX = width / 2 - WIDTH / 2 + SIDEBAR_WIDTH + 30;
        int baseY = height / 2 - HEIGHT / 2 + 40;

        int y = baseY - scrollOffset;

        for (Action a : selected.actions(c, this)) {
            if (y > baseY - BUTTON_HEIGHT && y < baseY + HEIGHT - 40) {
                addDrawableChild(ButtonWidget.builder(
                        Text.literal(a.label),
                        b -> a.run.run()
                ).dimensions(
                        baseX,
                        y,
                        BUTTON_WIDTH,
                        BUTTON_HEIGHT
                ).build());
            }
            y += SPACING;
        }
    }

    /* ============================================================
       RENDER
       ============================================================ */

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        int x = width / 2 - WIDTH / 2;
        int y = height / 2 - HEIGHT / 2;

        // ===== Soft shadow =====
        context.fill(x - 6, y - 6, x + WIDTH + 6, y + HEIGHT + 6, 0x55000000);

        // ===== Main transparent panel =====
        context.fill(x, y, x + WIDTH, y + HEIGHT, 0xB012141A);

        // ===== Sidebar =====
        context.fill(x, y, x + SIDEBAR_WIDTH, y + HEIGHT, 0xA00F1116);

        // ===== Accent divider =====
        context.fill(x + SIDEBAR_WIDTH, y, x + SIDEBAR_WIDTH + 3, y + HEIGHT, 0xFFFF6A00);

        // ===== Header =====
        context.fill(x, y, x + WIDTH, y + 28, 0xCC1A1F2B);

        context.drawTextWithShadow(
                textRenderer,
                "CLICK GUI",
                x + 12,
                y + 10,
                0xFFFFFFFF
        );

        context.drawText(
                textRenderer,
                selected.display,
                x + SIDEBAR_WIDTH + 16,
                y + 10,
                0xFF9AA0AA,
                false
        );

        super.render(context, mouseX, mouseY, delta);
    }


    /* ============================================================
       SCROLL
       ============================================================ */

    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        scrollOffset -= amount * 16;
        scrollOffset = Math.max(0, scrollOffset);
        init();
        return true;
    }

    @Override
    public boolean shouldPause() {
        return false;
    }


    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {

        // ESC closes GUI
        if (keyCode == 256) { // GLFW_KEY_ESCAPE
            close();
            return true;
        }

        // DO NOT consume keys → lets movement work
        return false;
    }


    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        // prevent blur
    }

    /* ============================================================
       DATA
       ============================================================ */

    enum Category {
        MOVEMENT("Movement"),
        VISUAL("Visual"),
        UTILITY("Utility"),
        SYSTEM("System");

        final String display;

        Category(String display) {
            this.display = display;
        }

        List<Action> actions(MinecraftClient c, bigGui gui) {
            List<Action> list = new ArrayList<>();

            switch (this) {

                case MOVEMENT -> {
                    list.add(new Action("Teleport +10Y", () -> {
                        if (c.player != null) {
                            BlockPos p = c.player.getBlockPos().up(10);
                            c.player.requestTeleport(p.getX() + 0.5, p.getY(), p.getZ() + 0.5);
                        }
                    }));

                    list.add(new Action("Teleport -10Y", () -> {
                        if (c.player != null) {
                            BlockPos p = c.player.getBlockPos().down(10);
                            c.player.requestTeleport(p.getX() + 0.5, p.getY(), p.getZ() + 0.5);
                        }
                    }));
                }

                case VISUAL -> {
                    list.add(new Action("Toggle Night Vision", () -> {
                        if (c.player == null) return;
                        if (c.player.hasStatusEffect(StatusEffects.NIGHT_VISION)) {
                            c.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
                        } else {
                            c.player.addStatusEffect(new StatusEffectInstance(
                                    StatusEffects.NIGHT_VISION, 20 * 600, 0, false, false
                            ));
                        }
                    }));

                    list.add(new Action("Toggle Fullbright", () ->
                            c.options.getGamma().setValue(
                                    c.options.getGamma().getValue() > 1.0 ? 1.0 : 16.0
                            )
                    ));

                    list.add(new Action("Reload Chunks", () ->
                            c.worldRenderer.reload()
                    ));
                }

                case UTILITY -> {
                    list.add(new Action("Clear Chat", () ->
                            c.inGameHud.getChatHud().clear(true)
                    ));

                    list.add(new Action("Copy Coords", () -> {
                        if (c.player != null) {
                            BlockPos p = c.player.getBlockPos();
                            c.keyboard.setClipboard(p.getX() + " " + p.getY() + " " + p.getZ());
                        }
                    }));

                    list.add(new Action("Screenshot", () -> {
                        c.options.screenshotKey.setPressed(true);
                        c.options.screenshotKey.setPressed(false);
                    }));
                }

                case SYSTEM -> {
                    list.add(new Action("Disconnect", () ->
                            c.disconnect(Text.literal("Disconnected"))
                    ));
                    list.add(new Action("Exit Client", c::scheduleStop));
                    list.add(new Action("Close GUI", gui::close));
                }
            }

            return list;
        }
    }

    record Action(String label, Runnable run) {}
}
