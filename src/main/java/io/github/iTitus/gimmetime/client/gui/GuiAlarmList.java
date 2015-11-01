package io.github.iTitus.gimmetime.client.gui;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.renderer.Tessellator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiAlarmList extends GuiListExtended {

	private final GuiScreenAlarmConfig parent;
	private ArrayList<GuiAlarm> alarmList;
	private int indexSelected;

	public GuiAlarmList(GuiScreenAlarmConfig parent, Minecraft mc, int width,
	                    int height, int top, int bottom, int slotHeight) {
		super(mc, width, height, top, bottom, slotHeight);
		this.parent = parent;
		alarmList = new ArrayList<GuiAlarm>();
		indexSelected = -1;
	}

	public List<GuiAlarm.Alarm> getAlarms() {

		List<GuiAlarm.Alarm> alarms = Lists.newArrayList();

		for (GuiAlarm alarm : alarmList) {
			alarms.add(alarm.getAlarm());
		}

		return alarms;
	}

	public void setAlarms(List<GuiAlarm.Alarm> arrayList) {

		alarmList.clear();

		for (GuiAlarm.Alarm alarm : arrayList) {
			alarmList.add(new GuiAlarm(alarm, parent));
		}

	}

	@Override
	public IGuiListEntry getListEntry(int index) {
		if (index < 0 || index > alarmList.size())
			return null;
		return alarmList.get(index);
	}

	@Override
	public int getListWidth() {
		return super.getListWidth() + 32;
	}

	public int getSelected() {
		return indexSelected;
	}

	@Override
	public boolean isSelected(int index) {
		return indexSelected == index;
	}

	public void select(int index) {
		indexSelected = index;
	}

	@Override
	protected void drawContainerBackground(Tessellator tessellator) {
	}

	@Override
	protected int getScrollBarX() {
		return super.getScrollBarX() + 16;
	}

	@Override
	protected int getSize() {
		return alarmList.size();
	}

}