package io.github.iTitus.gimmetime.client.handler;

import java.io.File;
import java.util.List;

import com.google.common.collect.Lists;

import io.github.iTitus.gimmetime.client.gui.alarm.Alarm;
import io.github.iTitus.gimmetime.client.render.hud.RenderClockHUD;
import io.github.iTitus.gimmetime.client.util.TimeUtil;
import io.github.iTitus.gimmetime.common.GimmeTime;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.common.util.Constants.NBT;

@SideOnly(Side.CLIENT)
public class AlarmHandler {

	private static final String TAG_ALARMS = "Alarms";

	private static List<Alarm> alarms;
	private static File alarmFile;
	private static int minLastChecked;

	public static void add(Alarm alarm) {
		alarms.add(alarm);
		save();
	}

	public static Alarm getAlarm(int index) {
		if (index < 0 || index >= alarms.size())
			return null;
		return alarms.get(index);
	}

	public static List<Alarm> getAlarms() {
		return alarms;
	}

	public static void init(File suggestedAlarmFile) {

		FMLCommonHandler.instance().bus().register(new AlarmHandler());

		try {

			alarmFile = suggestedAlarmFile;
			loadAlarms(alarmFile);

		} catch (Exception e) {
			GimmeTime.log.error("There was a problem loading the alarm configuration!", e);
		}

	}

	public static void remove(int index) {
		if (index >= 0) {
			alarms.remove(index);
			save();
		}
	}

	public static void save() {
		try {
			saveAlarms();
		} catch (Exception e) {
			GimmeTime.log.error("There was a problem saving the alarm configuration!", e);
		}
	}

	private static void loadAlarms(File suggestedAlarmFile) throws Exception {

		alarms = Lists.newArrayList();

		NBTTagCompound nbt = CompressedStreamTools.read(suggestedAlarmFile);
		if (nbt == null)
			return;

		NBTTagList list = nbt.getTagList(TAG_ALARMS, NBT.TAG_COMPOUND);

		for (int i = 0; i < list.tagCount(); i++) {
			Alarm alarm = new Alarm();
			alarm.readFromNBT(list.getCompoundTagAt(i));
			alarms.add(alarm);
		}

	}

	private static void saveAlarms() throws Exception {
		NBTTagList list = new NBTTagList();

		for (Alarm alarm : alarms) {
			NBTTagCompound nbt = new NBTTagCompound();
			alarm.writeToNBT(nbt);
			list.appendTag(nbt);
		}

		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setTag(TAG_ALARMS, list);
		CompressedStreamTools.safeWrite(nbt, alarmFile);
	}

	@SubscribeEvent
	public void onClientTick(ClientTickEvent event) {
		if (event.phase == Phase.END
				&& Minecraft.getMinecraft().thePlayer != null
				&& !TimeUtil.isMin(minLastChecked)) {
			checkAlarms();
			minLastChecked = TimeUtil.getMin();
		}
	}

	private boolean check(Alarm alarm) {
		if (alarm.isEnabled() && TimeUtil.isHour(alarm.getHour())
				&& TimeUtil.isMin(alarm.getMin()))
			return true;
		return false;
	}

	private void checkAlarms() {
		for (Alarm alarm : alarms) {
			if (check(alarm))
				showAlert(alarm);
		}
	}

	private void showAlert(Alarm alarm) {
		RenderClockHUD.add(alarm);
	}

}
