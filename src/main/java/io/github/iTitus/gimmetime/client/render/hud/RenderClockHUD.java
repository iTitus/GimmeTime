package io.github.iTitus.gimmetime.client.render.hud;


import java.util.Queue;

import com.google.common.collect.Queues;

import io.github.iTitus.gimmetime.client.gui.alarm.Alarm;
import io.github.iTitus.gimmetime.client.handler.AlarmHandler;
import io.github.iTitus.gimmetime.client.handler.KeyHandler;
import io.github.iTitus.gimmetime.client.util.RenderUtil;
import io.github.iTitus.gimmetime.client.util.TimeUtil;
import io.github.iTitus.gimmetime.common.handler.ConfigHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.profiler.Profiler;
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
	private final Queue<Alarm> alarmsToRender = Queues.newConcurrentLinkedQueue();
	private Alarm currentAlarm, previousAlarm;

	public static void add(Alarm alarm) {
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
		if (INSTANCE.previousAlarm != null && INSTANCE.previousAlarm.isRepeating())
			INSTANCE.previousAlarm.setEnabled(true);
		AlarmHandler.save();
	}

	@SubscribeEvent
	public void onRenderGameOverlayPost(Post event) {
		if (event.type == ElementType.ALL) {

			Minecraft mc = Minecraft.getMinecraft();
			FontRenderer fr = mc.fontRenderer;
			Profiler p = mc.mcProfiler;
			p.startSection("gimmetime-hud");

			switch (ConfigHandler.analog_digital) {
				case 0: // None
					break;
				case 1: // Analog
					p.startSection("clock");
					RenderUtil.drawClock(32, 34, 24);
					p.endSection();
					break;
				case 2: // Digital
					p.startSection("clock");
					drawString(TimeUtil.getTime(), 1, 1);
					p.endSection();
					break;
				case 3: // Both
					p.startSection("clock");
					RenderUtil.drawClock(32, 34, 24);
					drawString(TimeUtil.getTime(), 64, 1);
					p.endSection();
					break;
			}

			if (currentAlarm != null) {
				p.startSection("alarm");
				String title = currentAlarm.getTitle() + " - " + TimeUtil.getAlarmTimeString();
				drawString(title, (event.resolution.getScaledWidth() / 2) - (fr.getStringWidth(title) / 2), (event.resolution.getScaledHeight() / 2) - (fr.FONT_HEIGHT + 4));
				String closeHint = StatCollector.translateToLocalFormatted("hud.alarmAlert.close", GameSettings.getKeyDisplayString(KeyHandler.INSTANCE.clock.getKeyCode()));
				drawString(closeHint, (event.resolution.getScaledWidth() / 2) - (fr.getStringWidth(closeHint) / 2), (event.resolution.getScaledHeight() / 2) + (fr.FONT_HEIGHT - 2));
				p.endSection();
			}

			p.endSection();

		}

	}

	private void drawString(String string, int x, int y) {
		if (ConfigHandler.shadows)
			Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(string, x, y, ConfigHandler.color);
		else
			Minecraft.getMinecraft().fontRenderer.drawString(string, x, y, ConfigHandler.color);
	}

}
