package com.example;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class Commands {
    public static void commands() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(
                    literal("hello").executes(context -> {
                        MinecraftClient client = MinecraftClient.getInstance();
                        if (client.player != null) {
                            client.player.sendMessage(
                                    Text.literal("Hello from client command"),
                                    false
                            );
                        }
                        return 1;
                    })
            );
        });
    }
}