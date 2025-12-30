package com.example;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.item.ItemStack;

public class Commands {
    public static void registercommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                    CommandManager.literal("getitems")
                            .executes(context -> {
                                ServerPlayerEntity player = context.getSource().getPlayer();
                                ItemStack stack = new ItemStack(ModItems.CUSTOM_ITEM);
                                ItemStack stack1 = new ItemStack(ModItems.BANANA_SWORD);
                                ItemStack stack2 = new ItemStack(ModItems.FIRE_KATANA);

                                if (!player.getInventory().insertStack(stack)) {
                                    player.dropItem(stack, false);

                                }
                                if (!player.getInventory().insertStack(stack1)) {
                                    player.dropItem(stack1, false);

                                }
                                if (!player.getInventory().insertStack(stack2)) {
                                    player.dropItem(stack2, false);

                                }
                                return 1;
                            })
            );
        });
    }
}
