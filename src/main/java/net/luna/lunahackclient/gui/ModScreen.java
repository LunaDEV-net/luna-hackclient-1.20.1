package net.luna.lunahackclient.gui;

import net.luna.lunahackclient.LunaHackClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.Text;

public class ModScreen extends Screen {

    private final Screen parent;
    private final GameOptions settings;

    public ModScreen(Screen parent, GameOptions settings) {
        super(Text.of(new LiteralTextContent("Mod Mod").toString()));
        this.parent = parent;
        this.settings = settings;
    }

    protected void init() {
        this.addDrawableChild(new ButtonWidget.Builder(Text.of(Text.literal("Hallo").getString()),
                (buttonWidget) -> {this.client.setScreen(new ModScreen(this, this.client.options));
                    LunaHackClient.LOGGER.info("hi");
                }
            ).size(90, 20)
                .position(10,10)
                .build());
    }
}
