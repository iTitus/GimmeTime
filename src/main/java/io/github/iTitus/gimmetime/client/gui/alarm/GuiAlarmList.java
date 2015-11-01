package io.github.iTitus.gimmetime.client.gui.alarm;

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
	private final List<GuiAlarmEntry> alarmList;
	private int indexSelected;

	public GuiAlarmList(GuiScreenAlarmConfig parent, Minecraft mc, int width, int height, int top, int bottom, int slotHeight) {
		super(mc, width, height, top, bottom, slotHeight);
		this.parent = parent;
		this.alarmList = Lists.newArrayList();
		this.indexSelected = -1;
	}

	public List<Alarm> getAlarms() {

		List<Alarm> alarms = Lists.newArrayList();

		for (GuiAlarmEntry alarm : alarmList) {
			alarms.add(alarm.getAlarm());
		}

		return alarms;
	}

	public void setAlarms(List<Alarm> arrayList) {

		alarmList.clear();

		for (Alarm alarm : arrayList) {
			alarmList.add(new GuiAlarmEntry(alarm, parent));
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
		//NO-OP
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
