package io.github.iTitus.gimmetime.client.util;

import org.lwjgl.opengl.GL11;

import io.github.iTitus.gimmetime.common.handler.ConfigHandler;
import io.github.iTitus.gimmetime.common.util.MathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.MathHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderUtil {

	private static final FontRenderer fontRendererer = Minecraft.getMinecraft().fontRenderer;

	public static void drawClock(double x, double y, double radius) {

		//GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		//GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		setColor(ConfigHandler.color);
		drawHollowCircleShort(x, y, radius);
		drawLines(x, y, radius);
		drawHours(x, y, radius);
		drawMinutes(x, y, radius);
		if (ConfigHandler.seconds)
			drawSeconds(x, y, radius);

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		//GL11.glDisable(GL11.GL_BLEND);

		drawNumbers(x, y, radius);

	}

	public static int getRed(int color) {
		return (color >> 16) & 0xFF;
	}

	public static int getGreen(int color) {
		return (color >> 8) & 0xFF;
	}

	public static int getBlue(int color) {
		return color & 0xFF;
	}

	public static int getColor(int r, int g, int b) {
		int color = 0;
		color |= (r & 0xFF) << 16;
		color |= (g & 0xFF) << 8;
		color |= (b & 0xFF);
		return color;
	}

	public static void setColor(int color) {
		GL11.glColor3d(getRed(color) / 255D, getGreen(color) / 255D, getBlue(color) / 255D);
	}

	private static void drawHollowCircleShort(double x, double y, double radius) {
		GL11.glLineWidth(1);
		GL11.glBegin(GL11.GL_LINE_STRIP);
		for (int angle = 0; angle <= 360; angle++) {
			GL11.glVertex2d(
					x + (radius * MathHelper.sin(MathUtil.degToRad(angle))),
					y + (radius * MathHelper.cos(MathUtil.degToRad(angle))));
		}
		GL11.glEnd();
	}

	private static void drawHours(double x, double y, double radius) {
		GL11.glLineWidth(3);
		GL11.glBegin(GL11.GL_LINE_STRIP);
		GL11.glVertex2d(x, y);
		GL11.glVertex2d(
				x + ((radius - (radius / 2D)) * MathHelper.cos(MathUtil.degToRad(((TimeUtil.getHour() + ((TimeUtil.getMin() + (TimeUtil.getSec() / 60F)) / 60F)) / 12F) * 360F) - MathUtil.PI_HALF)),
				y + ((radius - (radius / 2D)) * MathHelper.sin(MathUtil.degToRad(((TimeUtil.getHour() + ((TimeUtil.getMin() + (TimeUtil.getSec() / 60F)) / 60F)) / 12F) * 360F) - MathUtil.PI_HALF)));
		GL11.glEnd();
	}

	private static void drawLines(double x, double y, double radius) {
		GL11.glLineWidth(1);
		for (int angle = 6; angle < 360; angle += 6) {
			GL11.glBegin(GL11.GL_LINE_STRIP);
			GL11.glVertex2d(
					x + ((radius - (radius / ((angle % 5 == 0) ? 8D : 16D))) * MathHelper.sin(MathUtil.degToRad(angle))),
					y + ((radius - (radius / ((angle % 5 == 0) ? 8D : 16D))) * MathHelper.cos(MathUtil.degToRad(angle))));
			GL11.glVertex2d(x + (radius * MathHelper.sin(MathUtil.degToRad(angle))), y + (radius * MathHelper.cos(MathUtil.degToRad(angle))));
			GL11.glEnd();
		}
	}

	private static void drawMinutes(double x, double y, double radius) {
		GL11.glLineWidth(2);
		GL11.glBegin(GL11.GL_LINE_STRIP);
		GL11.glVertex2d(x, y);
		GL11.glVertex2d(
				x + ((radius - (radius / 5D)) * MathHelper.cos(MathUtil.degToRad(((TimeUtil.getMin() + (TimeUtil.getSec() / 60F)) / 60F) * 360F) - MathUtil.PI_HALF)),
				y + ((radius - (radius / 5D)) * MathHelper.sin(MathUtil.degToRad(((TimeUtil.getMin() + (TimeUtil.getSec() / 60F)) / 60F) * 360F) - MathUtil.PI_HALF)));
		GL11.glEnd();
	}

	private static void drawNumbers(double x, double y, double radius) {

		String hour = "12";
		drawString(hour, (int) (x - (fontRendererer.getStringWidth(hour) / 2) + 1), (int) (y - radius - fontRendererer.FONT_HEIGHT));

		hour = "3";
		drawString(hour, (int) (x + radius + 1), (int) (y - (fontRendererer.FONT_HEIGHT / 2)));

		hour = "6";
		drawString(hour, (int) (x - (fontRendererer.getStringWidth(hour) / 2)), (int) (y + radius + 1));

		hour = "9";
		drawString(hour, (int) (x - radius - fontRendererer.getStringWidth(hour) - 1), (int) (y - (fontRendererer.FONT_HEIGHT / 2)));

	}

	private static void drawSeconds(double x, double y, double radius) {
		GL11.glLineWidth(1);
		GL11.glBegin(GL11.GL_LINE_STRIP);
		GL11.glVertex2d(x, y);
		float milliAngle = ConfigHandler.smoothSeconds ? (TimeUtil.getMillis() / (60F * 1000)) * 360F : 0;
		GL11.glVertex2d(
				x + ((radius - (radius / 10D) - 1) * MathHelper.cos(MathUtil.degToRad((TimeUtil.getSec() / 60F) * 360F + milliAngle) - MathUtil.PI_HALF)),
				y + ((radius - (radius / 10D) - 1) * MathHelper.sin(MathUtil.degToRad((TimeUtil.getSec() / 60F) * 360F + milliAngle) - MathUtil.PI_HALF)));
		GL11.glEnd();
	}

	private static void drawString(String string, int x, int y) {
		if (ConfigHandler.shadows)
			fontRendererer.drawStringWithShadow(string, x, y, ConfigHandler.color);
		else
			fontRendererer.drawString(string, x, y, ConfigHandler.color);
	}

}
