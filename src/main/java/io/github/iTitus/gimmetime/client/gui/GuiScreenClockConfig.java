package io.github.iTitus.gimmetime.client.gui;

import org.lwjgl.input.Keyboard;

import io.github.iTitus.gimmetime.client.gui.alarm.GuiScreenAlarmConfig;
import io.github.iTitus.gimmetime.client.util.RenderUtil;
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
public class GuiScreenClockConfig extends GuiScreen {

	private GuiButton selectedButton;

	private GuiIntSliderButton rColor, gColor, bColor;
	private GuiTextField separator;

	@Override
	public void drawScreen(int x, int y, float partialTicks) {
		drawDefaultBackground();
		drawCenteredString(fontRendererObj,
				StatCollector.translateToLocal("gui.clockConfig.name"),
				width / 2, 16, 16777215);
		drawString(fontRendererObj,
				StatCollector.translateToLocal("gui.clockConfig.color"),
				(width / 2) + 10, (height / 8) + (24 * (4 - 2)) - 16 + 10, 16777215);
		drawString(fontRendererObj,
				StatCollector.translateToLocal("gui.clockConfig.separator"),
				(width / 2) + 10, (height / 8) + (24 * (8 - 2)) - 16 + 6, 16777215);
		separator.drawTextBox();
		super.drawScreen(x, y, partialTicks);
	}

	@Override
	public void initGui() {
		int id = 0;
		buttonList.add(new GuiSwitchButton(id, (width / 2) - 210, (height / 8) + (24 * (id + 2)) - 16, StatCollector.translateToLocal("gui.clockConfig.clockFormat"), ConfigHandler.analog_digital,
				new String[]{
						StatCollector.translateToLocal("gui.clockConfig.clockFormat.none"),
						StatCollector.translateToLocal("gui.clockConfig.clockFormat.analog"),
						StatCollector.translateToLocal("gui.clockConfig.clockFormat.digital"),
						StatCollector.translateToLocal("gui.clockConfig.clockFormat.both")}));
		id++;
		buttonList.add(new GuiOnOffButton(id, (width / 2) - 210, (height / 8)
				+ (24 * (id + 2)) - 16,
				StatCollector.translateToLocal("gui.clockConfig.seconds"),
				ConfigHandler.seconds));
		id++;
		buttonList.add(new GuiOnOffButton(id, (width / 2) - 210, (height / 8)
				+ (24 * (id + 2)) - 16,
				StatCollector.translateToLocal("gui.clockConfig.am/pm"),
				ConfigHandler.am_pm));

		id++;
		buttonList.add(new GuiOnOffButton(id, (width / 2) - 210, (height / 8)
				+ (24 * (id + 2)) - 16,
				StatCollector.translateToLocal("gui.clockConfig.shadows"),
				ConfigHandler.shadows));

		id++;
		buttonList.add(new GuiOnOffButton(id, (width / 2) - 210, (height / 8)
				+ (24 * (id + 2)) - 16,
				StatCollector.translateToLocal("gui.clockConfig.smoothSeconds"),
				ConfigHandler.smoothSeconds));

		id++;
		rColor = new GuiIntSliderButton(id, (width / 2) + 10, (height / 8)
				+ (24 * (id - 2)) - 16,
				StatCollector.translateToLocal("gui.clockConfig.color.red"),
				RenderUtil.getRed(ConfigHandler.color), 0, 255);
		buttonList.add(rColor);

		id++;
		gColor = new GuiIntSliderButton(id, (width / 2) + 10, (height / 8)
				+ (24 * (id - 2)) - 16,
				StatCollector.translateToLocal("gui.clockConfig.color.green"),
				RenderUtil.getGreen(ConfigHandler.color), 0, 255);
		buttonList.add(gColor);

		id++;
		bColor = new GuiIntSliderButton(id, (width / 2) + 10, (height / 8)
				+ (24 * (id - 2)) - 16,
				StatCollector.translateToLocal("gui.clockConfig.color.blue"),
				RenderUtil.getBlue(ConfigHandler.color), 0, 255);
		buttonList.add(bColor);

		id++;
		Keyboard.enableRepeatEvents(true);
		separator = new GuiTextField(fontRendererObj, (width / 2) + 110, (height / 8)
				+ (24 * (id - 2)) - 16, 100, 20);
		separator.setText(ConfigHandler.separator);

		id++;
		buttonList.add(new GuiButton(id, (width / 2) - 100, (height / 8)
				+ (24 * (id - 2)) - 16, StatCollector.translateToLocal("gui.alarmConfig.name")));

		id++;
		buttonList.add(new GuiButton(id, (width / 2) - 100, (height / 8)
				+ (24 * (id - 2)) - 16, I18n.format("gui.done")));

	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	public void updateScreen() {
		if (rColor.isSliding() || gColor.isSliding() || bColor.isSliding()) {
			ConfigHandler.color = RenderUtil.getColor(rColor.getSliderPosition(), gColor.getSliderPosition(), bColor.getSliderPosition());
			ConfigHandler.saveConfig();
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		switch (button.id) {
			case 0:
				ConfigHandler.analog_digital = ((GuiSwitchButton) button).getCurrentIndex();
				ConfigHandler.saveConfig();
				break;
			case 1:
				ConfigHandler.seconds = ((GuiOnOffButton) button).getCurrentValue();
				ConfigHandler.saveConfig();
				break;
			case 2:
				ConfigHandler.am_pm = ((GuiOnOffButton) button).getCurrentValue();
				ConfigHandler.saveConfig();
				break;
			case 3:
				ConfigHandler.shadows = ((GuiOnOffButton) button).getCurrentValue();
				ConfigHandler.saveConfig();
				break;
			case 4:
				ConfigHandler.smoothSeconds = ((GuiOnOffButton) button).getCurrentValue();
				ConfigHandler.saveConfig();
				break;
			case 9:
				mc.displayGuiScreen(new GuiScreenAlarmConfig(this));
				break;
			case 10:
				mc.setIngameFocus();
			default:
		}

	}

	@Override
	protected void keyTyped(char par1, int par2) {
		if (separator.textboxKeyTyped(par1, par2)) {
			ConfigHandler.separator = separator.getText();
			ConfigHandler.saveConfig();
		} else {
			super.keyTyped(par1, par2);
		}
	}

	@Override
	protected void mouseClicked(int x, int y, int button) {
		super.mouseClicked(x, y, button);
		separator.mouseClicked(x, y, button);
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
