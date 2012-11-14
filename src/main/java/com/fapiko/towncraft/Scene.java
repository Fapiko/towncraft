package com.fapiko.towncraft;

import javax.media.j3d.Canvas3D;
import javax.media.j3d.J3DGraphics2D;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Scene extends Canvas3D {

	private BufferedImage bufferedImage;
	private Graphics2D graphics2D;
	private J3DGraphics2D graphicsAdapter;

	private String frameRate;
	private String cameraPosition = "";
	private int width = 512, height = width;

	public Scene(GraphicsConfiguration graphicsConfiguration) {
		super(graphicsConfiguration);

		graphicsAdapter = getGraphics2D();
	}

	public void setCameraPosition(String cameraPosition) {
		this.cameraPosition = cameraPosition;
	}

	public void setFrameRate(String frameRate) {
		this.frameRate = frameRate;
	}

	@Override
	public void postRender() {

		bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		graphics2D = bufferedImage.createGraphics();

		graphics2D.setColor(Color.WHITE);
		graphics2D.setFont(new Font("TimesRoman", Font.PLAIN, 12));
		graphics2D.drawString(frameRate, 25, 25);
		graphics2D.drawString(cameraPosition, 25, 40);

		graphicsAdapter.drawAndFlushImage(bufferedImage, 0, 0, this);

	}
}
