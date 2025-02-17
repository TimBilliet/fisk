package be.timmeke_.fisk;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoFisk {

    private final Minecraft mc;
    private EntityPlayer player;
    private long castScheduledAt = 0L;

    private static final int TICKS_PER_SECOND = 20;
    private static final String SOUND_NAME = "random.splash";

    public AutoFisk() {
        mc = FMLClientHandler.instance().getClient();
    }

    @SubscribeEvent
    public void onClientTickEvent(TickEvent.ClientTickEvent event) {
        if (ModCore.config_autofish_enable && event.phase == TickEvent.Phase.END) {
            if (!mc.isGamePaused() && mc.thePlayer != null) {
                player = mc.thePlayer;
                if (isHoldingRod() || castScheduledAt > 0) {
                    if (isTimeToCast()) {
                        if (isHoldingRod()) {
                            useRod();
                        }
                        castScheduledAt = 0L;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlaySoundEvent(PlaySoundEvent event) {
        if (ModCore.config_autofish_enable && SOUND_NAME.equals(event.name)) {
            useRod();
            castScheduledAt = mc.theWorld.getTotalWorldTime();
        }
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equals(ModCore.MODID)) {
            ModCore.syncConfig();
        }
    }
    
    private boolean isHoldingRod() {
        ItemStack heldItem = player.getHeldItem();
        return (heldItem != null && heldItem.getItem() instanceof ItemFishingRod);
    }

    private boolean isRodCast() {
        if (!isHoldingRod()) {
            return false;
        }
        return (player.fishEntity != null);
    }


    private boolean isTimeToCast() {
        return (castScheduledAt > 0 && mc.theWorld.getTotalWorldTime() > castScheduledAt + ((ModCore.config_autofish_recastDelay) / 1000f * TICKS_PER_SECOND));
    }

    private void useRod() {
        mc.playerController.sendUseItem(player, mc.theWorld, player.getHeldItem());
        if (isHoldingRod() && !isRodCast()) {
            castScheduledAt = 0L;
        }
    }
}
