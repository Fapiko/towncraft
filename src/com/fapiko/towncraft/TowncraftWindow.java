package com.fapiko.towncraft;

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
		frm.add(scene.getCanvas());
		scene.start();

		frm.setVisible(true);

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
			if (e.getKeyCode() == KeyEvent.VK_C) {
				scene.notifyStop();
			}
		}
	}
}
