package io.github.iTitus.gimmetime.client.handler;

import org.lwjgl.input.Keyboard;

import io.github.iTitus.gimmetime.client.render.hud.RenderClockHUD;
import io.github.iTitus.gimmetime.common.GimmeTime;
import io.github.iTitus.gimmetime.common.lib.LibGUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.common.gameevent.InputEvent.MouseInputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class KeyHandler {

	public static final KeyHandler INSTANCE = new KeyHandler();

	public final KeyBinding clock;

	public KeyHandler() {
		clock = new KeyBinding("key.openClockGUI.desc", Keyboard.KEY_C, "key.categories.gimmetime");
		ClientRegistry.registerKeyBinding(clock);
	}

	public static void init() {
		FMLCommonHandler.instance().bus().register(INSTANCE);
	}

	private void onInput() {
		if (Minecraft.getMinecraft().inGameHasFocus) {
			if (GameSettings.isKeyDown(clock)) {
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

	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {
		onInput();
	}

	@SubscribeEvent
	public void onMouseInput(MouseInputEvent event) {
		onInput();
	}

}
