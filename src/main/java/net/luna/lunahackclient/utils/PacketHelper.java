package net.luna.lunahackclient.utils;

import net.luna.lunahackclient.LunaHackClient;
import net.luna.lunahackclient.mixin.ClientConnectionInvokerMixin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateDifficultyC2SPacket;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.Difficulty;

public class PacketHelper { // from Liveoverflow
    public static void sendPos(Vec3d pos){
        MinecraftClient client = MinecraftClient.getInstance();
        ClientConnection conn = client.player.networkHandler.getConnection();
        pos = Vec3d.of(Vec3d_to_Vec3i(pos));
        Packet packet = new PlayerMoveC2SPacket.PositionAndOnGround(pos.getX(), pos.getY(), pos.getZ(), false);
        LunaHackClient.LOGGER.warn("hi");
        ((ClientConnectionInvokerMixin) conn).sendIm(packet, null); // at Invoker https://fabricmc.net/wiki/tutorial:mixin_accessors
    }

    public static Vec3i Vec3d_to_Vec3i(Vec3d inD){
        int x = (int) Math.floor(inD.getX());
        int y = (int) Math.floor(inD.getY());
        int z = (int) Math.floor(inD.getZ());
        return new Vec3i(x,y,z);
    }
}
