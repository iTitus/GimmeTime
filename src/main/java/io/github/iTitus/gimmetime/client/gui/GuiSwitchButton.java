package io.github.iTitus.gimmetime.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiSwitchButton extends GuiButton {

	private int currentIndex;
	private String[] options;
	private String title;

	public GuiSwitchButton(int id, int x, int y, int width, int height, String title, int defaultIndex, String[] options) {
		super(id, x, y, width, height, title);
		this.options = options;
		this.title = title;
		currentIndex = defaultIndex;
		displayString = ((this.title != null && this.title != "") ? (this.title + ": ") : "") + this.options[currentIndex];
	}

	public GuiSwitchButton(int id, int x, int y, String title, int defaultIndex, String[] options) {
		this(id, x, y, 200, 20, title, defaultIndex, options);
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	@Override
	public boolean mousePressed(Minecraft mc, int x, int y) {
		boolean b = super.mousePressed(mc, x, y);
		if (b)
			nextIndex(GuiScreen.isShiftKeyDown() ? 10 : 1);
		return b;
	}

	public boolean mouseRightPressed(Minecraft mc, int x, int y) {
		boolean b = super.mousePressed(mc, x, y);
		if (b)
			nextIndex(GuiScreen.isShiftKeyDown() ? -10 : -1);
		return b;
	}

	public void nextIndex(int steps) {

		currentIndex += steps;

		while (currentIndex >= options.length)
			currentIndex -= options.length;

		while (currentIndex < 0)
			currentIndex += options.length;

		displayString = ((title != null && title != "") ? (title + ": ") : ("")) + this.options[currentIndex];
	}
}
