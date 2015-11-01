package io.github.iTitus.gimmetime.client.gui.alarm;

import net.minecraft.nbt.NBTTagCompound;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Alarm {

	private static final String TAG_TITLE = "Title", TAG_HOUR = "Hour", TAG_MIN = "Min", TAG_REPEAT = "Repeats", TAG_ENABLED = "Enabled";

	private boolean enabled, repeat;
	private int hour, min;
	private String title;

	public Alarm() {
		this("", 0, 0, false, true);
	}

	public Alarm(String title, int hour, int min, boolean repeat, boolean enabled) {
		this.title = title;
		this.hour = hour;
		this.min = min;
		this.enabled = enabled;
		this.repeat = repeat;
	}

	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setString(TAG_TITLE, title);
		nbt.setInteger(TAG_HOUR, hour);
		nbt.setInteger(TAG_MIN, min);
		nbt.setBoolean(TAG_REPEAT, repeat);
		nbt.setBoolean(TAG_ENABLED, enabled);
	}

	public void readFromNBT(NBTTagCompound nbt) {
		this.title = nbt.getString(TAG_TITLE);
		this.hour = nbt.getInteger(TAG_HOUR);
		this.min = nbt.getInteger(TAG_MIN);
		this.repeat = nbt.getBoolean(TAG_REPEAT);
		this.enabled = nbt.getBoolean(TAG_ENABLED);
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isRepeating() {
		return repeat;
	}

	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}

}
