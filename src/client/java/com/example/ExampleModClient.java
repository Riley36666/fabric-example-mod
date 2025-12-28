package com.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ExampleModClient implements ClientModInitializer {

    private static KeyBinding OPEN_GUI_KEY;

    @Override
    public void onInitializeClient() {
        ClientCommands.commands();

        OPEN_GUI_KEY = KeyBindingHelper.registerKeyBinding(
                new KeyBinding(
                        "key.modid.opengui",
                        InputUtil.Type.KEYSYM,
                        GLFW.GLFW_KEY_M,
                        KeyBinding.Category.MISC
                )
        );



        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (OPEN_GUI_KEY.wasPressed()) {
                openGui(client);
            }
        });
    }

    private static void openGui(MinecraftClient client) {
        if (client.player == null) return;

        client.setScreen(new MyGuiScreen());
    }
}
