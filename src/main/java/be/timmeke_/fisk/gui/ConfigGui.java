package be.timmeke_.fisk.gui;

import be.timmeke_.fisk.ModCore;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

public class ConfigGui extends GuiConfig {

    public ConfigGui(GuiScreen parent) {
        super(parent,
                new ConfigElement(ModCore.configFile.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
                ModCore.MODID, false, false, "AutoFish Forge Mod Options"
        );
        titleLine2 = GuiConfig.getAbridgedConfigPath(ModCore.configFile.toString());
    }
}
