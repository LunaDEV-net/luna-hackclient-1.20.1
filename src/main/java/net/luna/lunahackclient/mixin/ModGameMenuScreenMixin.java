package net.luna.lunahackclient.mixin;

import net.luna.lunahackclient.gui.ModScreen;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public abstract class ModGameMenuScreenMixin extends Screen {
    protected ModGameMenuScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("HEAD"), method = "initWidgets")
    private void initWidgets(CallbackInfo ci) {
        this.addDrawableChild(new ButtonWidget.Builder(Text.of(Text.literal("Hallo").getString()),
                (buttonWidget) -> {this.client.setScreen(new ModScreen(this, this.client.options));}
        ).size(90, 20).position(10,10).build());
    }
}
