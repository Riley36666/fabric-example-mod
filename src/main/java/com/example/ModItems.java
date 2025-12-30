package com.example;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.AttributeModifierSlot;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.attribute.EntityAttributeModifier;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;



public class ModItems {

    public static final RegistryKey<Item> CUSTOM_ITEM_KEY =
            RegistryKey.of(
                    Registries.ITEM.getKey(),
                    Identifier.of("modid", "custom_item")
            );

    public static final Item CUSTOM_ITEM = Registry.register(
            Registries.ITEM,
            CUSTOM_ITEM_KEY,
            new CustomItem(
                    new Item.Settings()
                            .registryKey(CUSTOM_ITEM_KEY)
                            .rarity(Rarity.RARE)
                            .maxCount(16)
            )
    );
    public static final RegistryKey<Item> BANANA_SWORD_KEY =
            RegistryKey.of(
                    Registries.ITEM.getKey(),
                    Identifier.of("modid", "banana_sword")
            );


    public static final Item BANANA_SWORD = Registry.register(
            Registries.ITEM,
            BANANA_SWORD_KEY,
            new BananaSword(
                    new Item.Settings()
                            .registryKey(BANANA_SWORD_KEY)
                            .rarity(Rarity.COMMON)
                            .maxCount(1)
                            .maxDamage(500)
                            .component(
                                    DataComponentTypes.ATTRIBUTE_MODIFIERS,
                                    AttributeModifiersComponent.builder()
                                            .add(
                                                    EntityAttributes.ATTACK_DAMAGE,
                                                    new EntityAttributeModifier(
                                                            Identifier.of("modid", "banana_damage"),
                                                            12.0,
                                                            EntityAttributeModifier.Operation.ADD_VALUE
                                                    ),
                                                    AttributeModifierSlot.MAINHAND
                                            )

                                            .add(
                                                    EntityAttributes.ATTACK_SPEED,
                                                    new EntityAttributeModifier(
                                                            Identifier.of("modid", "banana_speed"),
                                                            5,
                                                            EntityAttributeModifier.Operation.ADD_VALUE
                                                    ),
                                                    AttributeModifierSlot.MAINHAND
                                            )
                                            .add(
                                                    EntityAttributes.MOVEMENT_SPEED,
                                                    new EntityAttributeModifier(
                                                            Identifier.of("modid", "banana_movementspeed"),
                                                            0.65, // movement speed uses MULTIPLIER, not raw values
                                                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                                                    ),
                                                    AttributeModifierSlot.MAINHAND
                                            )
                                            .build()
                            )
            )
            );
    public static final RegistryKey<Item> FIRE_KATANA_KEY =
            RegistryKey.of(
                    Registries.ITEM.getKey(),
                    Identifier.of("modid", "fire_katana") // matches the JSON and PNG name
            );

    public static final Item FIRE_KATANA = Registry.register(
            Registries.ITEM,
            FIRE_KATANA_KEY,
            new FireKatana(
                    new Item.Settings()
                            .registryKey(FIRE_KATANA_KEY)
                            .rarity(Rarity.UNCOMMON)
                            .maxCount(1)
                            .maxDamage(500)
                            .component(
                                    DataComponentTypes.ATTRIBUTE_MODIFIERS,
                                    AttributeModifiersComponent.builder()
                                            .add(
                                                    EntityAttributes.ATTACK_DAMAGE,
                                                    new EntityAttributeModifier(
                                                            Identifier.of("modid", "fire_katana_damage"),
                                                            12.0,
                                                            EntityAttributeModifier.Operation.ADD_VALUE
                                                    ),
                                                    AttributeModifierSlot.MAINHAND
                                            )
                                            .add(
                                                    EntityAttributes.ATTACK_SPEED,
                                                    new EntityAttributeModifier(
                                                            Identifier.of("modid", "fire_katana_speed"),
                                                            5.0,
                                                            EntityAttributeModifier.Operation.ADD_VALUE
                                                    ),
                                                    AttributeModifierSlot.MAINHAND
                                            )
                                            .add(
                                                    EntityAttributes.MOVEMENT_SPEED,
                                                    new EntityAttributeModifier(
                                                            Identifier.of("modid", "fire_katana_movementspeed"),
                                                            0.65, // multiplier
                                                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                                                    ),
                                                    AttributeModifierSlot.MAINHAND
                                            )
                                            .build()
                            )
            )
    );



    public static void init() {
        // no-op
    }
}
