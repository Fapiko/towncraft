package com.fapiko.towncraft;

import javax.media.j3d.Canvas3D;
import javax.media.j3d.GraphicsContext3D;
import javax.media.j3d.J3DGraphics2D;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Scene extends Canvas3D {

	private BufferedImage bufferedImage;
	private Graphics2D graphics2D;
	private GraphicsContext3D graphics3D;
	private J3DGraphics2D graphicsAdapter;

	private int width = 512, height = width;

	public Scene(GraphicsConfiguration graphicsConfiguration) {
		super(graphicsConfiguration);

		bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		graphics2D = bufferedImage.createGraphics();
		graphicsAdapter = getGraphics2D();
		graphics3D = getGraphicsContext3D();
	}

	@Override
	public void postRender() {

		graphics2D.setColor(Color.WHITE);
		graphics2D.setFont(new Font("TimesRoman", Font.ITALIC, 18));
		graphics2D.drawString("Arnold Facepalmer", 80, 80);

		graphicsAdapter.drawAndFlushImage(bufferedImage, 0, 0, this);

	}
}
