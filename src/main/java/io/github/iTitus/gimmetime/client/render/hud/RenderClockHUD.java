package io.github.iTitus.gimmetime.client.render.hud;

import java.util.Queue;

import com.google.common.collect.Queues;

import org.lwjgl.input.Keyboard;

import io.github.iTitus.gimmetime.client.gui.GuiAlarm;
import io.github.iTitus.gimmetime.client.handler.KeyHandler;
import io.github.iTitus.gimmetime.client.util.RenderUtil;
import io.github.iTitus.gimmetime.client.util.TimeUtil;
import io.github.iTitus.gimmetime.common.handler.ConfigHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.util.StatCollector;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Post;
import net.minecraftforge.common.MinecraftForge;

@SideOnly(Side.CLIENT)
public class RenderClockHUD {

	public static final RenderClockHUD INSTANCE = new RenderClockHUD();
	private final Queue<GuiAlarm.Alarm> alarmsToRender = Queues.newConcurrentLinkedQueue();
	private GuiAlarm.Alarm currentAlarm, previousAlarm;

	public static void add(GuiAlarm.Alarm alarm) {
		INSTANCE.alarmsToRender.offer(alarm);
		if (INSTANCE.currentAlarm == null)
			nextAlarm();
	}

	public static void init() {
		MinecraftForge.EVENT_BUS.register(INSTANCE);
	}

	public static boolean isShowing() {
		return INSTANCE.currentAlarm != null;
	}

	public static void nextAlarm() {
		if (INSTANCE.currentAlarm != null)
			INSTANCE.currentAlarm.setEnabled(false);
		INSTANCE.previousAlarm = INSTANCE.currentAlarm;
		INSTANCE.currentAlarm = INSTANCE.alarmsToRender.poll();
		if (INSTANCE.previousAlarm != null
				&& INSTANCE.previousAlarm.isRepeating())
			INSTANCE.previousAlarm.setEnabled(true);
	}

	@SubscribeEvent
	public void onRenderGameOverlayPost(Post event) {
		if (event.type == ElementType.ALL) {

			switch (ConfigHandler.analog_digital) {
				case 0: // None
					break;
				case 1: // Analog
					RenderUtil.drawClock((!TimeUtil.isPM() || ConfigHandler.am_pm) ? 31 : 37, 34, 24);
					break;
				case 2: // Digital
					Minecraft.getMinecraft().fontRenderer.drawString(TimeUtil.getTime(), 1, 1, ConfigHandler.color);
					break;
				case 3: // Both
					RenderUtil.drawClock((!TimeUtil.isPM() || ConfigHandler.am_pm) ? 31 : 37, 34, 24);
					Minecraft.getMinecraft().fontRenderer.drawString(TimeUtil.getTime(), (!TimeUtil.isPM() || ConfigHandler.am_pm) ? 62 : 74, 1, ConfigHandler.color);
					break;
			}

			if (currentAlarm != null) {
				Minecraft.getMinecraft().fontRenderer.drawString(currentAlarm.getTitle() + " - " + TimeUtil.make2Digits(currentAlarm.getHour()) + ConfigHandler.separator + TimeUtil.make2Digits(currentAlarm.getMin()), (event.resolution.getScaledWidth() / 2) - (Minecraft.getMinecraft().fontRenderer.getStringWidth(currentAlarm.getTitle() + " - " + TimeUtil.make2Digits(currentAlarm.getHour()) + ConfigHandler.separator + TimeUtil.make2Digits(currentAlarm.getMin())) / 2), (event.resolution.getScaledHeight() / 2) - (Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT + 4), ConfigHandler.color);
				Minecraft.getMinecraft().fontRenderer.drawString(StatCollector.translateToLocalFormatted("hud.alarmAlert.close", Keyboard.getKeyName(KeyHandler.getKeyBinding(KeyHandler.KEYBINDING_CLOCK).getKeyCode())), (event.resolution.getScaledWidth() / 2) - (Minecraft.getMinecraft().fontRenderer.getStringWidth(StatCollector.translateToLocalFormatted("hud.alarmAlert.close", Keyboard.getKeyName(KeyHandler.getKeyBinding(KeyHandler.KEYBINDING_CLOCK).getKeyCode()))) / 2), (event.resolution.getScaledHeight() / 2) + (Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT - 2), ConfigHandler.color);
			}

		}

	}

}