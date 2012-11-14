package com.fapiko.towncraft;

public class FPSTimer extends Thread {

	private SceneRenderer sceneRenderer;

	private boolean shouldStop = false;

	public FPSTimer(SceneRenderer sceneRenderer) {
		this.sceneRenderer = sceneRenderer;
	}

	@Override
	public void run() {

		while (!shouldStop) {

			int startingFrames = sceneRenderer.getFrameCounter();

			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			sceneRenderer.setAverageFPS(sceneRenderer.getFrameCounter() - startingFrames);

		}

	}
}
