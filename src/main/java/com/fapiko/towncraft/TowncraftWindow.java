package com.fapiko.towncraft;

import javax.media.j3d.Canvas3D;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TowncraftWindow implements KeyListener {

	private SceneRenderer scene;

	public TowncraftWindow() {

		Frame frm = new Frame("TownCraft v0.0.1");
		frm.setSize(400, 400);

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
			}
		}
	}
}
