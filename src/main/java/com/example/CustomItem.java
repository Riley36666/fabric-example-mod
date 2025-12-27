package com.example;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.text.Text;

public class CustomItem extends Item {

    public CustomItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            if (user.getAbilities().flying) {
                user.getAbilities().flying = false;
                user.getAbilities().allowFlying = false;
                user.sendMessage(Text.literal("Flight Disabled!"), false);
            } else {
                user.getAbilities().allowFlying = true;
                user.getAbilities().flying = true;
                user.sendMessage(Text.literal("Flight Enabled!"), false);
            }

            user.sendAbilitiesUpdate();
        }

        return ActionResult.SUCCESS;
    }
}
