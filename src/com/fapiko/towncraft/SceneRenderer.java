package com.fapiko.towncraft;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.vecmath.Vector3d;
import java.awt.*;

public class SceneRenderer extends Thread {

	private static final int FPS = 90;

	private Scene canvas;
	private Graphics screen;
	private TransformGroup transformGroup;

	private boolean shouldStop = false;
	private int averageFPS = FPS;
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
		canvas.setFrameRate(String.format("Frame [%d]", frameCounter));

		FPSTimer fpsTimer = new FPSTimer(this);
		fpsTimer.start();

		while (!shouldStop) {

			// Indicates we want to travel 2 meters in 1 second
			double travelInterval = 2f / FPS;
			height += travelInterval * sign;
			if (Math.abs(height * 2) >= 1) sign = -1 * sign;
			transform.setTranslation(new Vector3d(0, height, 0));
			transformGroup.setTransform(transform);

			canvas.setFrameRate(String.format("Frame [%d|%dfps]", frameCounter, averageFPS));

			// For some reason it's required to draw a string here for it to render scaling properly in postRender
			try {
				screen.drawString(" ", 0, 0);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}

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

	public void setAverageFPS(int averageFPS) {
		this.averageFPS = averageFPS;
	}

	public Canvas3D getCanvas() {
		return canvas;
	}

	public int getFrameCounter() {
		return frameCounter;
	}

	public void notifyStop() {
		shouldStop = true;
	}

	@Override
	public void run() {
		applicationLoop();
	}
}
