package me.cumhax.apehax.api.gui.clickgui.button;

import me.cumhax.apehax.Client;
import me.cumhax.apehax.api.module.Module;
import net.minecraft.client.Minecraft;

import java.awt.*;

/**
 * @author yoink
 * @since 9/20/2020
 */
public class SettingButton {
	public final Minecraft mc = Minecraft.getMinecraft();
	private final int H;
	private Module module;
	private int X;
	private int Y;
	private int W;

	public SettingButton(Module module, int x, int y, int w, int h) {
		this.module = module;
		X = x;
		Y = y;
		W = w;
		H = h;
	}

	public void render(int mX, int mY) {
	}

	public void mouseDown(int mX, int mY, int mB) {
	}

	public void mouseUp(int mX, int mY) {
	}

	public void keyPress(int key) {
	}

	public void close() {
	}

	public void drawButton(int mX, int mY) {
		if (isHover(getX(), getY(), getW(), getH() - 1, mX, mY)) {
			Client.clickGUI.drawGradient(X, Y, X + W, Y + H, new Color(25, 25, 25, 170).getRGB(),
					new Color(25, 25, 25, 170).getRGB());
		} else {
			Client.clickGUI.drawGradient(X, Y, X + W, Y + H, new Color(25, 25, 25, 150).getRGB(),
					new Color(25, 25, 25, 150).getRGB());
		}
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public int getX() {
		return X;
	}

	public void setX(int x) {
		X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		Y = y;
	}

	public int getW() {
		return W;
	}

	public int getH() {
		return H;
	}

	public boolean isHover(int X, int Y, int W, int H, int mX, int mY) {
		return mX >= X && mX <= X + W && mY >= Y && mY <= Y + H;
	}
}
