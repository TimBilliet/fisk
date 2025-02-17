package be.timmeke_.fisk;

import be.timmeke_.fisk.gui.ConfigGui;
import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class KeyInputHandler {

    public KeyBinding options;

    public KeyInputHandler() {
        options = new KeyBinding("Fisk Options", Keyboard.KEY_O, "Fisk Mod");
        ClientRegistry.registerKeyBinding(options);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (this.options.isPressed()) {
            Minecraft.getMinecraft().displayGuiScreen(new ConfigGui(Minecraft.getMinecraft().currentScreen));
        }
    }
}
