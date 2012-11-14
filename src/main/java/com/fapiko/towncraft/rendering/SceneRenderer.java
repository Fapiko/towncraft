package com.fapiko.towncraft.rendering;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;
import org.apache.log4j.Logger;

import javax.media.j3d.*;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import java.awt.*;
import java.text.DecimalFormat;

public class SceneRenderer extends Thread {

	private Scene canvas;
	private Graphics screen;
	private TransformGroup transformGroup;
	private TransformGroup transformGroup2;
	private TransformGroup cameraTransformGroup;

	private boolean shouldStop = false;
	private float cameraX = 0;
	private float cameraY = 0;
	private float cameraZ = 2;
	private int fps = 30;
	private int averageFPS = fps;
	private int frameCounter = 0;
	private int timerInterval = 1000 / fps;

	private static Logger log = Logger.getLogger(SceneRenderer.class);

	private ColorCube cube1;

	public SceneRenderer() {

		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		canvas = new Scene(config);
		SimpleUniverse universe = new SimpleUniverse(canvas);
		universe.getViewingPlatform().setNominalViewingTransform();
		universe.addBranchGraph(createSceneGraph());

		cameraTransformGroup = universe.getViewingPlatform().getViewPlatformTransform();
	}

	public void applicationLoop() {

		int sign = -1;
		float height = 0;
		Transform3D transform = new Transform3D();
		Transform3D transform2 = new Transform3D();

		screen = canvas.getGraphics2D();
		canvas.setFrameRate(String.format("Frame [%d]", frameCounter));
		canvas.setCameraPosition(String.format("Camera Position (%f, %f, %f)", cameraX, cameraY, cameraZ));

		FPSTimer fpsTimer = new FPSTimer(this);
		fpsTimer.start();

		DecimalFormat decimalFormat = new DecimalFormat("#.##");

		while (!shouldStop) {

			// Indicates we want to travel 2 meters in 1 second
			float travelInterval = 2f / fps;
			height += travelInterval * sign;
			if (Math.abs(height * 2) >= 1) {
				sign = -1 * sign;
			}
			transform.setTranslation(new Vector3f(0f, height, 0f));
			transformGroup.setTransform(transform);

			transform2.setTranslation(new Vector3f(.25f, 0f, 0f));
			transformGroup2.setTransform(transform2);

			canvas.setFrameRate(String.format("Frame [%d|%dfps]", frameCounter, averageFPS));
			canvas.setCameraPosition(String.format("Camera Position (%s, %s, %s)", decimalFormat.format(cameraX),
					decimalFormat.format(cameraY), decimalFormat.format(cameraZ)));

			Transform3D camera = new Transform3D();
			camera.setTranslation(new Vector3d(cameraX, cameraY, cameraZ));
			cameraTransformGroup.setTransform(camera);

			Transform3D cameraTransform3D = new Transform3D();
			Vector3f cameraPosition = new Vector3f();
			cameraTransformGroup.getTransform(cameraTransform3D);
			cameraTransform3D.get(cameraPosition);

			try {
				// For some reason it's required to draw a string here for it to render scaling properly in postRender
				screen.drawString(" ", 0, 0);
			} catch (NullPointerException e) {
				log.warn("InterruptedException");
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

		transformGroup2 = new TransformGroup();
		transformGroup2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		root.addChild(transformGroup2);

		cube1 = new ColorCube(0.1);
		transformGroup2.addChild(cube1);

		return root;

	}

	public void adjustCameraX(float distance) {
		setCameraX(this.cameraX - distance);
	}

	public void adjustCameraY(float distance) {
		setCameraY(this.cameraY - distance);
	}

	public void adjustCameraZ(float distance) {
		setCameraZ(this.cameraZ - distance);
	}

	public void decreaseFPS(int fps) {
		setFPS(this.fps - fps);
	}

	public void increaseFPS(int fps) {
		setFPS(this.fps + fps);
	}

	public void setAverageFPS(int averageFPS) {
		this.averageFPS = averageFPS;
	}

	public void setCameraX(float cameraX) {
		this.cameraX = cameraX;
	}

	public void setCameraY(float cameraY) {
		this.cameraY = cameraY;
	}

	public void setCameraZ(float cameraZ) {
		this.cameraZ = cameraZ;
	}

	public void setFPS(int fps) {
		this.fps = fps;
		timerInterval = 1000 / fps;
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
