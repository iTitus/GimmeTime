package io.github.iTitus.gimmetime;

import io.github.iTitus.gimmetime.common.proxy.CommonProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = GimmeTime.MOD_ID, version = GimmeTime.MOD_VERSION, guiFactory = GimmeTime.GUI_FACTORY)
public class GimmeTime {

	public static final String MOD_ID = "gimmetime";
	public static final String MOD_VERSION = "@VERSION@";
	public static final String GUI_FACTORY = "io.github.iTitus.gimmetime.client.gui.GimmeTimeGuiFactory";
	public static final String CLIENT_PROXY = "io.github.iTitus.gimmetime.client.proxy.ClientProxy";
	public static final String SERVER_PROXY = "io.github.iTitus.gimmetime.common.proxy.CommonProxy";

	@Instance
	public static GimmeTime instance;

	@SidedProxy(clientSide = CLIENT_PROXY, serverSide = SERVER_PROXY)
	public static CommonProxy proxy;

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}

}
