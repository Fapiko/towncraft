package com.fapiko.towncraft;

import com.fapiko.towncraft.rendering.SceneRenderer;
import org.apache.log4j.Logger;

import javax.media.j3d.Canvas3D;
import java.awt.*;
import java.awt.event.*;

public class TowncraftWindow implements KeyListener, MouseMotionListener {

	private SceneRenderer scene;

	private boolean shiftPressed;
	private int mouseX;
	private int mouseY;

	private static Logger log = Logger.getLogger(TowncraftWindow.class);

	public TowncraftWindow() {

		Frame frm = new Frame("TownCraft v0.0.1");
		frm.setSize(600, 800);

		frm.addKeyListener(this);
		frm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		scene = new SceneRenderer();
		Canvas3D canvas = scene.getCanvas();
		canvas.addKeyListener(this);
		canvas.addMouseMotionListener(this);
		frm.add(canvas);
		scene.start();

		frm.setVisible(true);

	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		if ((e.getModifiers() & KeyEvent.ALT_MASK) != 0) {

			if (e.getKeyCode() == KeyEvent.VK_UP) {
				scene.adjustCameraZ(.5f);
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				scene.adjustCameraZ(-.5f);
			}

		} else {
			if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
				shiftPressed = true;
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		if ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0) {

			if (e.getKeyCode() == KeyEvent.VK_C) {
				scene.notifyStop();
			} else if (e.getKeyCode() == KeyEvent.VK_UP) {
				scene.increaseFPS(10);
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				scene.decreaseFPS(10);
			} else {
				System.out.println(e);
			}

		} else {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				scene.increaseFPS(1);
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				scene.decreaseFPS(1);
			} else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
				shiftPressed = false;
			}
		}

	}

	public void mouseDragged(MouseEvent e) {

		int oldMouseY = mouseY;
		mouseY = e.getY();

		if (mouseY > oldMouseY) {
			// Mouse moved Up
			scene.adjustCameraZ(-.05f);
		} else if (mouseY < oldMouseY) {
			// Mouse moved Down
			scene.adjustCameraZ(.05f);
		}

	}

	public void mouseMoved(MouseEvent e) {

		if (shiftPressed) {
			int oldMouseX = mouseX;
			int oldMouseY = mouseY;
			mouseX = e.getX();
			mouseY = e.getY();

			if (mouseX > oldMouseX) {
				// Mouse moved right
				scene.adjustCameraX(.01f);
			} else if (mouseX < oldMouseX) {
				// Mouse moved left
				scene.adjustCameraX(-.01f);
			}

			if (mouseY > oldMouseY) {
				// Mouse moved Up
				scene.adjustCameraY(-.01f);
			} else if (mouseY < oldMouseY) {
				// Mouse moved Down
				scene.adjustCameraY(.01f);
			}
		}

	}
}
