//package com.example;
//
//import net.minecraft.entity.EntityType;
//import net.minecraft.entity.LivingEntity;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.entity.projectile.ArrowEntity;
//
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//
//import net.minecraft.util.ActionResult;
//import net.minecraft.util.Hand;
//import net.minecraft.world.World;
//
//public class GodBow extends Item {
//
//    public GodBow(Settings settings) {
//        super(settings);
//    }
//
//    public int getMaxUseTime(ItemStack stack) {
//        return 72000;
//    }
//
//    @Override
//    public ActionResult use(World world, PlayerEntity user, Hand hand) {
//        user.setCurrentHand(hand);
//        return ActionResult.CONSUME;
//    }
//
//    @Override
//    public boolean onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
//        if (!(user instanceof PlayerEntity player)) return false;
//
//        int charge = getMaxUseTime(stack) - remainingUseTicks;
//        float pull = getPullProgress(charge);
//
//        if (pull < 0.1F) return false;
//
//        if (!world.isClient()) {
//            ArrowEntity arrow = EntityType.ARROW.create(world);
//            if (arrow == null) return false;
//
//            arrow.setOwner(player);
//            arrow.setPosition(
//                    player.getX(),
//                    player.getEyeY() - 0.1,
//                    player.getZ()
//            );
//
//            arrow.setVelocity(
//                    player,
//                    player.getPitch(),
//                    player.getYaw(),
//                    0.0F,
//                    pull * 3.0F,
//                    1.0F
//            );
//
//            world.spawnEntity(arrow);
//        }
//
//        stack.damage(1, player, LivingEntity.getSlotForHand(player.getActiveHand()));
//        return false;
//    }
//
//    private static float getPullProgress(int useTicks) {
//        float f = useTicks / 20.0F;
//        f = (f * f + f * 2.0F) / 3.0F;
//        return Math.min(f, 1.0F);
//    }
//}
