package io.github.iTitus.gimmetime.common.proxy;

import java.io.File;

import io.github.iTitus.gimmetime.GimmeTime;
import io.github.iTitus.gimmetime.common.handler.ConfigHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class CommonProxy implements IGuiHandler {

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	public void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(GimmeTime.instance, GimmeTime.proxy);
	}

	public void postInit(FMLPostInitializationEvent event) {
		//NO-OP
	}

	public void preInit(FMLPreInitializationEvent event) {
		ConfigHandler.init(new File(event.getModConfigurationDirectory(), GimmeTime.MOD_ID + File.separatorChar + GimmeTime.MOD_ID + ".cfg"));
	}


	public void registerRenderers() {
		//NO-OP
	}

}
