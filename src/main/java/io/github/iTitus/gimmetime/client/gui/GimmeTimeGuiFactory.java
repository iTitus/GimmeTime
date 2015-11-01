package io.github.iTitus.gimmetime.client.gui;

import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GimmeTimeGuiFactory implements IModGuiFactory {

	@Override
	public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
		return null;
	}

	@Override
	public void initialize(Minecraft minecraftInstance) {
		//NO-OP
	}

	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass() {
		return GuiScreenModConfig.class;
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}

}
