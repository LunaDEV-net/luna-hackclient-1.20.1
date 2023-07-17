package net.luna.lunahackclient.module;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;

public class autoplant {
    private static int tickCounter = 0;

    public static void register() {
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            onTick(client);
        });
    }

    public static ActionResult onTick(MinecraftClient client) {
        tickCounter++;
        if (client.world != null && client.player != null) {
            // Sending a chat message every tick
            client.player.sendMessage(Text.literal("Tick: " + tickCounter).formatted(Formatting.YELLOW), false);
        }
        return ActionResult.SUCCESS;
    }
}