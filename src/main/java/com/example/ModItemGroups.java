package com.example;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.text.Text;

public class ModItemGroups {

    public static ItemGroup MOD_TAB;

    public static void init() {
        MOD_TAB = Registry.register(
                Registries.ITEM_GROUP,
                Identifier.of("modid", "mod_tab"),
                FabricItemGroup.builder()
                        .displayName(Text.literal("My Mod"))
                        // IMPORTANT: vanilla item only
                        .icon(() -> new ItemStack(Items.DIAMOND))
                        .entries((context, entries) -> {
                            entries.add(ModItems.CUSTOM_ITEM);
                        })
                        .build()
        );
    }
}
