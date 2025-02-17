package be.timmeke_.fisk;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

@Mod(modid = ModCore.MODID, version = ModCore.VERSION)

public class ModCore {
    public static final String MODID = "fisk";
    public static final String VERSION = "1.2";

    public static Configuration configFile;
    public static boolean config_autofish_enable;
    public static int config_autofish_recastDelay;
    private static final List<String> propertyOrder = new ArrayList<String>(Arrays.asList("Enable AutoFish", "Re-Cast Delay(ms)"));

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configFile = new Configuration(event.getSuggestedConfigurationFile());
        configFile.setCategoryPropertyOrder(Configuration.CATEGORY_GENERAL, propertyOrder);
        syncConfig();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new AutoFisk());
        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
    }

    public static void syncConfig() {
        config_autofish_enable = configFile.getBoolean("Enable AutoFish", Configuration.CATEGORY_GENERAL, true, "Automatically reel in and re-cast when a fish nibbles the hook. If set to false all AutoFish functionality is disabled.");
        config_autofish_recastDelay = configFile.getInt("Re-Cast Delay(ms)", Configuration.CATEGORY_GENERAL, 150, 100, 10000, "Time (in milliseconds) to wait before automatically re-casting. Increase this value if server lag causes re-casting to fail.");
        if (configFile.hasChanged()) {
            configFile.save();
        }
    }
}
