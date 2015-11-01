package io.github.iTitus.gimmetime.client.gui.alarm;

import io.github.iTitus.gimmetime.client.handler.AlarmHandler;
import io.github.iTitus.gimmetime.client.util.TimeUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiListExtended.IGuiListEntry;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.StatCollector;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiAlarmEntry implements IGuiListEntry {

	private final Alarm alarm;
	private final GuiScreenAlarmConfig parent;
	private long timeLastHit;

	public GuiAlarmEntry(Alarm alarm, GuiScreenAlarmConfig parent) {
		this.alarm = alarm;
		this.parent = parent;
		this.timeLastHit = 0L;
	}

	@Override
	public void drawEntry(int var1, int var2, int var3, int width, int var5, Tessellator tess, int var7, int var8, boolean var9) {
		FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
		fr.drawStringWithShadow(alarm.getTitle(), var2 + 32, var3 + 1, (alarm.isEnabled() ? (65280) : (16711680)));
		fr.drawStringWithShadow(TimeUtil.getTimeString(alarm.getHour(), alarm.getMin()) + ((alarm.isRepeating()) ? " - " + StatCollector.translateToLocal("gui.alarmConfig.repeat") : ""), var2 + 32, var3 + fr.FONT_HEIGHT + 2, alarm.isEnabled() ? 65280 : 16711680);
	}

	public Alarm getAlarm() {
		return alarm;
	}

	@Override
	public boolean mousePressed(int index, int var2, int var3, int var4, int var5, int var6) {
		parent.select(index);
		if (Minecraft.getSystemTime() - timeLastHit < 250L) {
			alarm.setEnabled(!alarm.isEnabled());
			AlarmHandler.save();
		}
		timeLastHit = Minecraft.getSystemTime();
		return false;
	}

	@Override
	public void mouseReleased(int var1, int var2, int var3, int var4, int var5, int var6) {
		//NO-OP
	}


}
