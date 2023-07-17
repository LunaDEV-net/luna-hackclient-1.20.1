package net.luna.lunahackclient.utils.world;

import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class LavaCauldronUtils {
    private static int previousSlot = -1;

    public static int findBucketInHotbar(){
        MinecraftClient client = MinecraftClient.getInstance();
        int slot = -1;

        for (int i = 0; i <= 8; i++) {
            ItemStack stack = client.player.getInventory().getStack(i);
            if (stack.getItem() == Items.BUCKET) {
                if (slot == -1) {slot = i;}
            }
        }
        return slot;
    }
    public static boolean getlava(BlockPos blockPos, int slot){ // hand.main_hand
        MinecraftClient client = MinecraftClient.getInstance();

        if (slot < 0 || slot > 8) {return false;} // data überprüfung von findBucketinhotbar
        Hand hand = Hand.MAIN_HAND;
        Vec3d hitPos = Vec3d.ofCenter(blockPos);
        BlockPos neighbour;
        Direction side = getPlaceSide(blockPos);


        if (side == null) {
            side = Direction.UP;
            neighbour = blockPos;
        } else {
            neighbour = blockPos.offset(side.getOpposite());
            hitPos.add(side.getOffsetX() * 0.5, side.getOffsetY() * 0.5, side.getOffsetZ() * 0.5);
        }

        BlockHitResult bhr = new BlockHitResult(hitPos, side, neighbour, false);

        swap(slot, true);
        interact(bhr);
        swapBack();
        return true;
    }

    private static void interact(BlockHitResult blockHitResult) {
        MinecraftClient client = MinecraftClient.getInstance();
        Hand hand = Hand.MAIN_HAND;

        boolean wasSneaking = client.player.input.sneaking;
        client.player.input.sneaking = false;

        ActionResult result = client.interactionManager.interactBlock(client.player, hand, blockHitResult);

        if (result.shouldSwingHand()) {
            client.player.swingHand(hand);
        }

        client.player.input.sneaking = wasSneaking;
    }

    private static Direction getPlaceSide(BlockPos blockPos) {
        MinecraftClient client = MinecraftClient.getInstance();

        for (Direction side : Direction.values()) {
            BlockPos neighbor = blockPos.offset(side);
            Direction side2 = side.getOpposite();

            BlockState state = client.world.getBlockState(neighbor);

            // Check if neighbour isn't empty
            if (state.isAir() || isClickable(state.getBlock())) continue;

            // Check if neighbour is a fluid
            if (!state.getFluidState().isEmpty()) continue;

            return side2;
        }

        return null;
    }

    private static boolean isClickable(Block block) {
        return block instanceof CraftingTableBlock
                || block instanceof AnvilBlock
                || block instanceof ButtonBlock
                || block instanceof AbstractPressurePlateBlock
                || block instanceof BlockWithEntity
                || block instanceof BedBlock
                || block instanceof FenceGateBlock
                || block instanceof DoorBlock
                || block instanceof NoteBlock
                || block instanceof TrapdoorBlock;
    }

    // from Invutils
    private static boolean swap(int slot, boolean swapBack) {
        MinecraftClient client = MinecraftClient.getInstance();

        final int OFFHAND_SLOT = 45; //from SlotUtils

        if (slot == OFFHAND_SLOT) return true;
        if (slot < 0 || slot > 8) return false;
        if (swapBack && previousSlot == -1) previousSlot = client.player.getInventory().selectedSlot;
        else if (!swapBack) previousSlot = -1;

        client.player.getInventory().selectedSlot = slot;
       // ((IClientPlayerInteractionManager) client.interactionManager).syncSelected();
        return true;
    }

    private static boolean swapBack() {
        if (previousSlot == -1) return false;

        boolean return_ = swap(previousSlot, false);
        previousSlot = -1;
        return return_;
    }
}
