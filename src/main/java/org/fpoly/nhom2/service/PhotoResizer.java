package org.fpoly.nhom2.service;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

@Component
public class PhotoResizer {
	//TODO Thêm function crop ảnh
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
		BufferedImage combined = new BufferedImage(w, w, BufferedImage.TYPE_INT_ARGB);
		Graphics g = combined.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, w, w);
		g.drawImage(image, x, y, null);
		ImageIO.write(combined, extension, file);
		System.out.println("resized " + file.getName() + " successfully!");
	}
}
