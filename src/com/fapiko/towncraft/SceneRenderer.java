package com.fapiko.towncraft;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.vecmath.Vector3d;
import java.awt.*;

public class SceneRenderer extends Thread {

	private static final int FPS = 30;

	private Scene canvas;
	private Graphics screen;
	private TransformGroup transformGroup;

	private boolean shouldStop = false;
	private int frameCounter = 0;
	private int timerInterval = 1000 / FPS;

	public SceneRenderer() {

		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		canvas = new Scene(config);
		SimpleUniverse universe = new SimpleUniverse(canvas);
		universe.getViewingPlatform().setNominalViewingTransform();
		universe.addBranchGraph(createSceneGraph());

	}

	public void applicationLoop() {

		int sign = -1;
		double height = 0;
		Transform3D transform = new Transform3D();

		screen = canvas.getGraphics2D();



		while (!shouldStop) {

			height += .065 * sign;
			if (Math.abs(height * 2) >= 1) sign = -1 * sign;
			transform.setTranslation(new Vector3d(0, height, 0));
			transformGroup.setTransform(transform);

			System.out.printf("Frame [%d]\n", frameCounter);

			// For some reason it's required to draw a string here for it to render properly in postRender
			screen.drawString(" ", 0, 0);

			try {
				sleep(timerInterval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			frameCounter++;

		}

	}

	public BranchGroup createSceneGraph() {

		BranchGroup root = new BranchGroup();
		transformGroup = new TransformGroup();
		transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		root.addChild(transformGroup);

		ColorCube cube = new ColorCube(0.1);
		transformGroup.addChild(cube);

		return root;

	}

	public Canvas3D getCanvas() {
		return canvas;
	}

	public void notifyStop() {
		shouldStop = true;
	}

	@Override
	public void run() {
		applicationLoop();
	}
}
