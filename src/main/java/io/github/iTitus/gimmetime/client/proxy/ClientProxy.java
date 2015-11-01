package io.github.iTitus.gimmetime.client.proxy;

import java.io.File;

import io.github.iTitus.gimmetime.client.gui.GuiScreenClockConfig;
import io.github.iTitus.gimmetime.client.handler.AlarmHandler;
import io.github.iTitus.gimmetime.client.handler.KeyHandler;
import io.github.iTitus.gimmetime.client.render.hud.RenderClockHUD;
import io.github.iTitus.gimmetime.common.GimmeTime;
import io.github.iTitus.gimmetime.common.lib.LibGUI;
import io.github.iTitus.gimmetime.common.proxy.CommonProxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
			case LibGUI.CLOCK_CONFIG_GUI:
				return new GuiScreenClockConfig();
			default:
				return null;
		}
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
		KeyHandler.init();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		RenderClockHUD.init();
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		AlarmHandler.init(new File(event.getModConfigurationDirectory()
				, GimmeTime.MOD_ID + File.separatorChar + GimmeTime.MOD_ID + "-alarms.dat"));
	}

}
