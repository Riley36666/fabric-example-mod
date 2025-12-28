package com.example;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.text.DecimalFormat;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class ClientCommands {

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

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(
                    literal("pcinfo").executes(context -> {
                        MinecraftClient client = MinecraftClient.getInstance();

                        if (client.player != null) {
                            Runtime runtime = Runtime.getRuntime();
                            DecimalFormat df = new DecimalFormat("#.##");

                            double maxRam = runtime.maxMemory() / 1024.0 / 1024.0;
                            double usedRam =
                                    (runtime.totalMemory() - runtime.freeMemory()) / 1024.0 / 1024.0;

                            client.player.sendMessage(Text.literal("§6=== PC Info ==="), false);
                            client.player.sendMessage(Text.literal("OS: " + System.getProperty("os.name")), false);
                            client.player.sendMessage(Text.literal("Architecture: " + System.getProperty("os.arch")), false);
                            client.player.sendMessage(Text.literal("Java: " + System.getProperty("java.version")), false);
                            client.player.sendMessage(Text.literal("CPU Cores: " + runtime.availableProcessors()), false);
                            client.player.sendMessage(Text.literal(
                                    "RAM Used: " + df.format(usedRam) + " MB / " + df.format(maxRam) + " MB"
                            ), false);
                        }

                        return 1;
                    })
            );
        });

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(
                    literal("gui").executes(context -> {
                        MinecraftClient client = MinecraftClient.getInstance();
                        if (client.player != null) {
                            client.player.sendMessage(
                                    Text.literal("Opening Gui"),
                                    false
                            );
                        }
                        client.execute(() -> {
                            client.setScreen(new MyGuiScreen());
                        });

                        return 1;
                    })
            );
        });

    }
}
