package net.luna.lunahackclient.module;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.luna.lunahackclient.LunaHackClient;
import net.luna.lunahackclient.utils.PacketHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class fly {
    private static double oldY;
    private static int floatingTickCount;
    private static float gravity = (float) -0.03125D;
    //private static float gravity = (float) -0.0433D;
    private static boolean isActive = false;


    public static void register() {
        isActive = false;
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            fly.onTick(client);
        });
    }

    //public static void onActive(){MinecraftClient.getInstance().player.}

    public static ActionResult onTick(MinecraftClient client) {// Liveoverflow
        if (client.player == null) {return ActionResult.FAIL;}
        if (!getIsActive()) {client.player.getAbilities().allowFlying = false; return ActionResult.FAIL;}
        client.player.getAbilities().allowFlying = true;
        if(client.player.getPos().getY() >= oldY-gravity) {
            floatingTickCount += 1;
        }
        oldY = client.player.getPos().getY();
        if((floatingTickCount > 20 || false /*forceAntiFly*/)
                && client.player.getWorld().getBlockState(new BlockPos(PacketHelper.Vec3d_to_Vec3i(client.player.getPos().subtract(0, Math.abs(gravity), 0) ))).isAir()) {
            Vec3d temp = client.player.getPos();
            int ipos = 1;
            BlockPos blockPos;
            while(true){
                blockPos = new BlockPos(PacketHelper.Vec3d_to_Vec3i(client.player.getPos().subtract(0,ipos,0)));
                int i;
                if (!client.player.getWorld().getBlockState(BlockPos.ofFloored(blockPos.toCenterPos())).isSolid()) {
                    for(i = 0; i <= 5; i++){
                        PacketHelper.sendPos(client.player.getPos());
                    }
                    PacketHelper.sendPos(blockPos.toCenterPos());
                    LunaHackClient.LOGGER.info("send down");

                    break;
                } else {
                    ++ipos;
                }
            }
            //client.player.teleport(temp.getX(), temp.getY(), temp.getZ());
            PacketHelper.sendPos(temp);
            LunaHackClient.LOGGER.warn("reset floting ticks");
            //LunaHackClient.LOGGER.warn("New Y: " + temp.getY() + "  Old Y: " + client.player.getPos().getY());
            //forceAntiFly = false //youtube.com/watch?v=V4_5x4QtHVg
            floatingTickCount = 0;
        }
        return ActionResult.SUCCESS;
    }

    public static boolean getIsActive() {
        return isActive;
    }

    public static void setIsActive(boolean isActive) {
        fly.isActive = isActive;
    }
    // toggel
}
