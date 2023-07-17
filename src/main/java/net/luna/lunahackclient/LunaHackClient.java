package net.luna.lunahackclient;

import net.fabricmc.api.ModInitializer;

import net.luna.lunahackclient.module.modules;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LunaHackClient implements ModInitializer {
	public static final String MOD_ID = "luna-hackclient";
    public static final Logger LOGGER = LoggerFactory.getLogger("luna-hackclient");
	MinecraftClient client = MinecraftClient.getInstance();

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
		modules.register();
	}
}