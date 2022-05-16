package me.cumhax.apehax.api.gui.clickgui.button.settings;

import me.cumhax.apehax.Client;
import me.cumhax.apehax.api.gui.clickgui.button.SettingButton;
import me.cumhax.apehax.api.module.Module;
import me.cumhax.apehax.api.setting.Setting;
import me.cumhax.apehax.api.util.font.FontUtil;

import java.awt.*;

/**
 * @author yoink
 * @since 9/20/2020
 */
public class BoolButton extends SettingButton {
	private final Setting setting;

	public BoolButton(Module module, Setting setting, int X, int Y, int W, int H) {
		super(module, X, Y, W, H);
		this.setting = setting;
	}

	@Override
	public void render(int mX, int mY) {
		if (setting.getBooleanValue()) {
			drawButton(mX, mY);
			FontUtil.drawStringWithShadow(setting.getName(), (float) (getX() + 6), (float) (getY() + 4),
					new Color(Client.settingManager.getSetting("ClickGUI", "Red").getIntegerValue(),
							Client.settingManager.getSetting("ClickGUI", "Green").getIntegerValue(),
							Client.settingManager.getSetting("ClickGUI", "Blue").getIntegerValue(), 232).getRGB());
		} else {
			if (isHover(getX(), getY(), getW(), getH() - 1, mX, mY)) {
				Client.clickGUI.drawGradient(getX(), getY(), getX() + getW(), getY() + getH(),
						new Color(25, 25, 25, 170).getRGB(), new Color(25, 25, 25, 170).getRGB());
			} else {
				Client.clickGUI.drawGradient(getX(), getY(), getX() + getW(), getY() + getH(),
						new Color(25, 25, 25, 150).getRGB(), new Color(25, 25, 25, 150).getRGB());
			}

			FontUtil.drawString(setting.getName(), (float) (getX() + 6), (float) (getY() + 4),
					new Color(255, 255, 255, 232).getRGB());
		}
	}

	@Override
	public void mouseDown(int mX, int mY, int mB) {
		if (isHover(getX(), getY(), getW(), getH() - 1, mX, mY) && (mB == 0 || mB == 1)) {
			setting.setBooleanValue(!setting.getBooleanValue());
		}
	}
}
