package com.fapiko.towncraft.rendering.jogl;

import com.fapiko.towncraft.rendering.SceneRendererInterface;
import org.apache.log4j.Logger;

public class SceneRenderer extends Thread implements SceneRendererInterface {

	private boolean shouldStop = true;
	private int fps = 30;
	private int frameCounter = 0;
	private int timerInterval = 1000 / fps;

	private static Logger log = Logger.getLogger(SceneRenderer.class);

	public void applicationLoop() {

		while (!shouldStop) {

			log.info(String.format("Frame: [%d]", frameCounter));

			try {
				sleep(timerInterval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			frameCounter++;

		}

		log.info("Stopping application loop...");

	}

	public void notifyStop() {
		shouldStop = true;
		log.info("Stopping...");
	}

	@Override
	public void run() {
		super.run();

		applicationLoop();
	}

}
