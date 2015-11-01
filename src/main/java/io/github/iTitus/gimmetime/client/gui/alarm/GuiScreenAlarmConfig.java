package io.github.iTitus.gimmetime.client.gui.alarm;

import io.github.iTitus.gimmetime.client.gui.GuiScreenClockConfig;
import io.github.iTitus.gimmetime.client.handler.AlarmHandler;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListExtended.IGuiListEntry;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.StatCollector;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiScreenAlarmConfig extends GuiScreen implements GuiYesNoCallback {

	private GuiAlarmList alarmList;
	private GuiButton editButton, deleteButton;

	private GuiScreenClockConfig parent;

	public GuiScreenAlarmConfig(GuiScreenClockConfig parent) {
		this.parent = parent;
	}

	@Override
	public void confirmClicked(boolean accepted, int index) {
		if (accepted) {
			AlarmHandler.remove(index);
			alarmList.select(-1);
			alarmList.setAlarms(AlarmHandler.getAlarms());
		}
		mc.displayGuiScreen(this);
	}

	@Override
	public void drawScreen(int x, int y, float partialTicks) {
		drawDefaultBackground();
		alarmList.drawScreen(x, y, partialTicks);
		drawCenteredString(fontRendererObj,
				StatCollector.translateToLocal("gui.alarmConfig.name"),
				this.width / 2, 20, 16777215);
		super.drawScreen(x, y, partialTicks);
	}

	public GuiAlarmList getGuiAlarmList() {
		return alarmList;
	}

	@Override
	public void initGui() {

		alarmList = new GuiAlarmList(this, mc, width, height, 48, height - 48, 25);
		alarmList.setAlarms(AlarmHandler.getAlarms());

		int id = 0;
		editButton = new GuiButton(id, (id * ((100 + (width - 400) / 5)))
				+ ((width - 400) / 5), height - 32, 100, 20,
				StatCollector.translateToLocal("gui.alarmConfig.edit"));
		editButton.enabled = false;
		buttonList.add(editButton);

		id++;
		buttonList.add(new GuiButton(id, (id * ((100 + (width - 400) / 5)))
				+ ((width - 400) / 5), height - 32, 100, 20, StatCollector
				.translateToLocal("gui.alarmConfig.add")));

		id++;
		deleteButton = new GuiButton(id, (id * ((100 + (width - 400) / 5)))
				+ ((width - 400) / 5), height - 32, 100, 20,
				StatCollector.translateToLocal("gui.alarmConfig.delete"));
		deleteButton.enabled = false;
		buttonList.add(deleteButton);

		id++;
		buttonList.add(new GuiButton(id, (id * ((100 + (width - 400) / 5)))
				+ ((width - 400) / 5), height - 32, 100, 20, I18n.format(
				"gui.done", new Object[0])));

	}

	public void select(int index) {
		alarmList.select(index);
		if (index >= 0) {
			editButton.enabled = true;
			deleteButton.enabled = true;
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		IGuiListEntry entry = alarmList.getListEntry(alarmList.getSelected());
		if (button.enabled) {
			switch (button.id) {
				case 0:
					if (entry == null)
						break;
				case 1:
					mc.displayGuiScreen(new GuiScreenEditAlarm(this, ((button.id == 0 && entry instanceof GuiAlarmEntry) ? (((GuiAlarmEntry) entry).getAlarm()) : null), button.id == 1));
					break;
				case 2:
					if (entry instanceof GuiAlarmEntry)
						mc.displayGuiScreen(new GuiYesNo(
								this,
								StatCollector.translateToLocal("gui.alarmConfig.delete.question"),
								StatCollector.translateToLocalFormatted(((GuiAlarmEntry) entry).getAlarm().getTitle()),
								alarmList.getSelected()));
					break;
				default:
					mc.displayGuiScreen(parent);
			}
		}
	}

	@Override
	protected void mouseClicked(int x, int y, int button) {
		if (button != 0 || !alarmList.func_148179_a(x, y, button))
			super.mouseClicked(x, y, button);
	}

	@Override
	protected void mouseMovedOrUp(int x, int y, int type) {
		if (type != 0 || !alarmList.func_148181_b(x, y, type))
			super.mouseMovedOrUp(x, y, type);
	}

}
