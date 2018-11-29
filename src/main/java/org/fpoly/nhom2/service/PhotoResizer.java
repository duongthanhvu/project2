package org.fpoly.nhom2.service;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

@Component
public class PhotoResizer {
	// TODO Thêm function crop ảnh
	public void resizeLogo(File file) throws IOException {
		String extension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
		BufferedImage image = ImageIO.read(file);
		int w;
		int x;
		int y;
		if (image.getWidth() >= image.getHeight()) {
			w = image.getWidth();
			x = 0;
			y = (w - image.getHeight()) / 2;
		} else {
			w = image.getHeight();
			x = (w - image.getWidth()) / 2;
			y = 0;
		}
		BufferedImage combined = new BufferedImage(w, w, BufferedImage.TYPE_3BYTE_BGR);
		Graphics g = combined.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, w, w);
		g.drawImage(image, x, y, null);
		ImageIO.write(combined, extension, file);
		System.out.println("resized " + file.getName() + " successfully!");
	}

	public InputStream resizeLogo(InputStream inputStream, String extension) throws IOException {
		try {
			BufferedImage image = ImageIO.read(inputStream);
			int w;
			int x;
			int y;
			if (image.getWidth() >= image.getHeight()) {
				w = image.getWidth();
				x = 0;
				y = (w - image.getHeight()) / 2;
			} else {
				w = image.getHeight();
				x = (w - image.getWidth()) / 2;
				y = 0;
			}
			BufferedImage combined = new BufferedImage(w, w, BufferedImage.TYPE_3BYTE_BGR);
			Graphics g = combined.getGraphics();
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, w, w);
			g.drawImage(image, x, y, null);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(combined, extension, os);
			InputStream is = new ByteArrayInputStream(os.toByteArray());
			return is;
		} finally {
			inputStream.close();
		}
	}
}