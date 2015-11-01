package io.github.iTitus.gimmetime.client.gui.alarm;

import org.lwjgl.input.Keyboard;

import io.github.iTitus.gimmetime.client.gui.GuiOnOffButton;
import io.github.iTitus.gimmetime.client.gui.GuiSwitchButton;
import io.github.iTitus.gimmetime.client.handler.AlarmHandler;
import io.github.iTitus.gimmetime.client.util.TimeUtil;
import io.github.iTitus.gimmetime.common.handler.ConfigHandler;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.StatCollector;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;

@SideOnly(Side.CLIENT)
public class GuiScreenEditAlarm extends GuiScreen {

	private final GuiScreenAlarmConfig parent;
	private final boolean isNewAlarm;
	private final Alarm alarm;

	private GuiButton selectedButton;

	private GuiOnOffButton enabledButton, repeatButton;
	private GuiSwitchButton hourButton, minButton;
	private GuiTextField title;


	public GuiScreenEditAlarm(GuiScreenAlarmConfig parent, Alarm alarm, boolean isNewAlarm) {
		this.parent = parent;
		if (isNewAlarm)
			this.alarm = new Alarm();
		else
			this.alarm = alarm;
		this.isNewAlarm = isNewAlarm;
	}

	@Override
	public void drawScreen(int x, int y, float partialTicks) {
		drawDefaultBackground();
		title.drawTextBox();
		drawCenteredString(
				fontRendererObj,
				isNewAlarm ? StatCollector.translateToLocal("gui.editAlarm.new.name") : StatCollector.translateToLocal("gui.editAlarm.name"),
				width / 2, 20, 16777215);
		fontRendererObj.drawString(
				StatCollector.translateToLocal("gui.editAlarm.alarmTitle"),
				(width / 2) - 100, (height / 4) + 8 - fontRendererObj.FONT_HEIGHT - 1, 16777215);
		drawCenteredString(fontRendererObj, ConfigHandler.separator, width / 2,
				(height / 4) + 37, 16777215);
		super.drawScreen(x, y, partialTicks);
	}

	@Override
	public void initGui() {

		Keyboard.enableRepeatEvents(true);
		title = new GuiTextField(fontRendererObj, (width / 2) - 100,
				(height / 4) + 8, 200, 20);
		if (!isNewAlarm)
			title.setText(alarm.getTitle());

		int id = 2;
		hourButton = new GuiSwitchButton(id, (width / 2) - 100, (height / 4)
				+ (24 * (id + 1)) - 16, 75, 20, null, (isNewAlarm ? 0
				: alarm.getHour()), TimeUtil.getAllHours());
		buttonList.add(hourButton);

		id++;
		minButton = new GuiSwitchButton(id, (width / 2) + 25, (height / 4)
				+ (24 * id) - 16, 75, 20, null, (isNewAlarm ? 0
				: alarm.getMin()), TimeUtil.getAllMins());
		buttonList.add(minButton);
		id--;

		id++;
		enabledButton = new GuiOnOffButton(id, (width / 2) - 100, (height / 4)
				+ (24 * (id + 1)) - 16,
				StatCollector.translateToLocal("gui.editAlarm.enabled"),
				(isNewAlarm ? true : alarm.isEnabled()));
		buttonList.add(enabledButton);

		id++;
		repeatButton = new GuiOnOffButton(id, (width / 2) - 100, (height / 4)
				+ (24 * (id + 1)) - 16,
				StatCollector.translateToLocal("gui.editAlarm.repeat"),
				(isNewAlarm ? false : alarm.isRepeating()));
		buttonList.add(repeatButton);

		id++;
		buttonList
				.add(new GuiButton(
						id,
						((id - 5) * ((200 + (width - 400) / 3)))
								+ ((width - 400) / 3),
						height - 32,
						(isNewAlarm ? StatCollector
								.translateToLocal("gui.editAlarm.new.done")
								: StatCollector
								.translateToLocal("gui.editAlarm.done"))));

		id++;
		buttonList.add(new GuiButton(id,
				((id - 5) * ((200 + (width - 400) / 3))) + ((width - 400) / 3),
				height - 32, I18n.format("gui.cancel", new Object[0])));

	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	private void saveAlarm() {
		alarm.setTitle(title.getText());
		alarm.setHour(hourButton.getCurrentIndex());
		alarm.setMin(minButton.getCurrentIndex());
		alarm.setRepeat(repeatButton.getCurrentValue());
		alarm.setEnabled(enabledButton.getCurrentValue());
		if (isNewAlarm)
			AlarmHandler.add(alarm);
		else
			AlarmHandler.save();
		parent.getGuiAlarmList().setAlarms(AlarmHandler.getAlarms());
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if (button.enabled) {
			switch (button.id) {
				case 5:
					saveAlarm();
				case 6:
					mc.displayGuiScreen(parent);
				default:
					break;
			}
		}
	}

	@Override
	protected void keyTyped(char par1, int par2) {
		if (title.textboxKeyTyped(par1, par2)) {
		} else
			super.keyTyped(par1, par2);
	}

	@Override
	protected void mouseClicked(int x, int y, int button) {
		super.mouseClicked(x, y, button);
		title.mouseClicked(x, y, button);
		if (button == 1) {
			for (Object o : buttonList) {
				if (o instanceof GuiSwitchButton) {
					GuiSwitchButton guibutton = (GuiSwitchButton) o;
					if (guibutton.mouseRightPressed(mc, x, y)) {
						GuiScreenEvent.ActionPerformedEvent.Pre event = new GuiScreenEvent.ActionPerformedEvent.Pre(this, guibutton, buttonList);
						if (MinecraftForge.EVENT_BUS.post(event))
							break;
						selectedButton = event.button;
						event.button.func_146113_a(mc.getSoundHandler());
						actionPerformed(event.button);
						if (equals(mc.currentScreen))
							MinecraftForge.EVENT_BUS.post(new GuiScreenEvent.ActionPerformedEvent.Post(this, event.button, buttonList));
					}
				}
			}
		}
	}

	@Override
	protected void mouseMovedOrUp(int x, int y, int type) {
		super.mouseMovedOrUp(x, y, type);
		if (selectedButton != null && type == 1) {
			selectedButton.mouseReleased(x, y);
			selectedButton = null;
		}
	}
}
