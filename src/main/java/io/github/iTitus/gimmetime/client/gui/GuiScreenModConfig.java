package io.github.iTitus.gimmetime.client.gui;

import io.github.iTitus.gimmetime.client.handler.KeyHandler;
import io.github.iTitus.gimmetime.common.GimmeTime;
import io.github.iTitus.gimmetime.common.handler.ConfigHandler;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.StatCollector;

import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

@SideOnly(Side.CLIENT)
public class GuiScreenModConfig extends GuiConfig {

	public GuiScreenModConfig(GuiScreen parent) {
		super(parent, new ConfigElement(
						ConfigHandler.cfg.getCategory(Configuration.CATEGORY_GENERAL))
						.getChildElements(), GimmeTime.MOD_ID, false, false, GuiConfig
						.getAbridgedConfigPath(ConfigHandler.cfg.toString()),
				StatCollector.translateToLocalFormatted(
						"gui.config.subtitle",
						GameSettings.getKeyDisplayString(KeyHandler.INSTANCE.clock.getKeyCode())));
	}

}
