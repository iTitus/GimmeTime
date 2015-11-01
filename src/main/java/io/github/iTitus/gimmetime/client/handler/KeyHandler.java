package io.github.iTitus.gimmetime.client.handler;

import org.lwjgl.input.Keyboard;

import io.github.iTitus.gimmetime.GimmeTime;
import io.github.iTitus.gimmetime.client.render.hud.RenderClockHUD;
import io.github.iTitus.gimmetime.common.lib.LibGUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class KeyHandler {

	public static final int KEYBINDING_CLOCK = 0;
	private static final String[] desc = {"key.openClockGUI.desc"};

	private static final KeyBinding[] keys = new KeyBinding[desc.length];

	private static final int[] keyValues = {Keyboard.KEY_C};

	public KeyHandler() {

		for (int i = 0; i < desc.length; i++) {
			keys[i] = new KeyBinding(desc[i], keyValues[i],
					"key.categories.gimmetime");
			ClientRegistry.registerKeyBinding(keys[i]);
		}

	}

	public static KeyBinding getKeyBinding(int index) {
		if (index < 0 || index >= keys.length)
			return null;
		return keys[index];
	}

	public static void init() {
		FMLCommonHandler.instance().bus().register(new KeyHandler());
	}

	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {

		if (Minecraft.getMinecraft().currentScreen == null) {

			for (int i = 0; i < desc.length; i++) {

				if (keys[i].isPressed()) {
					if (!RenderClockHUD.isShowing())
						Minecraft.getMinecraft().thePlayer.openGui(
								GimmeTime.instance, LibGUI.CLOCK_CONFIG_GUI,
								Minecraft.getMinecraft().theWorld,
								(int) Minecraft.getMinecraft().thePlayer.posX,
								(int) Minecraft.getMinecraft().thePlayer.posY,
								(int) Minecraft.getMinecraft().thePlayer.posZ);
					else
						RenderClockHUD.nextAlarm();
				}

			}

		}
	}
}
