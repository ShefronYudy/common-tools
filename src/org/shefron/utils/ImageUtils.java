package org.shefron.utils;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 图片调整大小及添加水印
 * 
 * @author a
 * 
 */
public final class ImageUtils {

	/**
	 * 批量压缩源文件
	 * 
	 * @param orgImages
	 *            源文件
	 * @param destWidth
	 *            生成文件的最大长度
	 * @param destHeight
	 *            生成文件的最大宽度
	 * @param destPrefix
	 *            生成文件前缀
	 * @param fileType
	 *            文件类型:jpg,png等
	 */
	public static void compressImage(File[] orgImages, int destWidth,
			int destHeight, String destPrefix, String fileType) {

		BufferedImage srcImage = null;
		try {
			int seq = 1;
			for (File orgImage : orgImages) {
				srcImage = ImageIO.read(orgImage);

				int srcWidth = srcImage.getWidth();
				int srcHeight = srcImage.getHeight();

				if (srcWidth <= destWidth && srcHeight <= destHeight) {
					ImageIO.write(srcImage, fileType, orgImage);
					return;
				}

				Image scaledImage = srcImage.getScaledInstance(srcWidth,
						srcHeight, Image.SCALE_SMOOTH);

				double ratioW = (double) destWidth / srcWidth;
				double ratioH = (double) destHeight / srcHeight;
				double ratio = Math.min(ratioW, ratioH);

				AffineTransformOp op = new AffineTransformOp(AffineTransform
						.getScaleInstance(ratio, ratio), null);
				scaledImage = op.filter(srcImage, null);

				String destFilePath = orgImage.getParentFile().getPath()
						+ File.separator + destPrefix + seq + "." + fileType;

				ImageIO.write((BufferedImage) scaledImage, fileType,
						new FileOutputStream(destFilePath));
				seq++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 源文件批量添加水印logo
	 * 
	 * @param orgImages
	 *            源文件
	 * @param destPrefix
	 *            生成文件前缀
	 * @param fileType
	 *            生成文件类型:jpg,png等
	 * @param logoImage
	 *            水印文件
	 */
	public static void mark2Image(File[] orgImages, String destPrefix,
			String fileType, String logoImage) {
		try {
			File waterMarkImage = new File(logoImage);
			if (!waterMarkImage.exists()) {
				return;
			}

			BufferedImage watermarkImage = ImageIO.read(waterMarkImage);
			int watermarkWidth = watermarkImage.getWidth(null);
			int watermarkHeight = watermarkImage.getHeight(null);

			BufferedImage originalImage = null;
			int seq = 1;
			for (File orgImage : orgImages) {
				originalImage = ImageIO.read(orgImage);
				int originalWidth = originalImage.getWidth(null);
				int originalHeight = originalImage.getHeight(null);

				if (originalWidth <= watermarkWidth
						|| originalHeight <= watermarkHeight
						|| originalWidth < 240 || originalHeight < 180) {
					return;
				}

				BufferedImage newImage = new BufferedImage(originalWidth,
						originalHeight, BufferedImage.TYPE_INT_RGB);
				Graphics2D g = newImage.createGraphics();
				g.drawImage(originalImage, 0, 0, originalWidth, originalHeight,
						null);
				g.setComposite(AlphaComposite.getInstance(
						AlphaComposite.SRC_ATOP, 1.0f));

				// 水印位置
				g.drawImage(watermarkImage, (originalWidth - watermarkWidth),
						(originalHeight - watermarkHeight), watermarkWidth,
						watermarkHeight, null);
				g.dispose();

				String destNamePath = orgImage.getParentFile().getPath()
						+ File.separator + destPrefix + seq + "." + fileType;
				ImageIO.write(newImage, fileType, new FileOutputStream(
						destNamePath));
				seq++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 把源图片适应性的调整到指定背景图片上
	 * 
	 * @param bgImage
	 *            背景图片
	 * @param fileType
	 *            生成文件类型jpg,png等
	 * @param images
	 *            源图片
	 * @param destPrefix
	 *            文件名前缀
	 */
	public static void adjustImages(File bgImage, String fileType,
			File[] images, String destPrefix) {
		try {
			BufferedImage originalImage = ImageIO.read(bgImage);

			int originalWidth = originalImage.getWidth(null);
			int originalHeight = originalImage.getHeight(null);

			BufferedImage image = null;
			BufferedImage newImage = null;
			for (File file : images) {
				image = ImageIO.read(file);

				newImage = new BufferedImage(originalWidth, originalHeight,
						BufferedImage.TYPE_INT_RGB);
				Graphics2D g = newImage.createGraphics();
				g.drawImage(originalImage, 0, 0, originalWidth, originalHeight,
						null);
				g.setComposite(AlphaComposite.getInstance(
						AlphaComposite.SRC_ATOP, 1.0f));

				BufferedImage _image = handleImage(originalWidth,
						originalHeight, image);
				int width = _image.getWidth();
				int height = _image.getHeight();

				int x = height;
				int y = width;
				if (width == originalWidth) {
					x = 0;
					y = (originalHeight - height) / 2;
				}
				if (height == originalHeight) {
					y = 0;
					x = (originalWidth - width) / 2;
				}

				g.drawImage(_image, x, y, width, height, null);
				g.dispose();
				ImageIO.write(newImage, fileType, new FileOutputStream((bgImage
						.getParentFile()
						+ File.separator + destPrefix + bgImage.getName())));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static BufferedImage handleImage(int orgWidth, int orgHeigth,
			BufferedImage srcImage) {
		int srcWidth = srcImage.getWidth();
		int srcHeight = srcImage.getHeight();

		Image scaledImage = srcImage.getScaledInstance(srcWidth, srcHeight,
				Image.SCALE_SMOOTH);

		if (srcWidth <= orgWidth && srcHeight <= orgHeigth) {
			return srcImage;
		}

		double ratioW = (double) orgWidth / srcWidth;
		double ratioH = (double) orgHeigth / srcHeight;

		double ratio = Math.min(ratioW, ratioH);

		AffineTransformOp op = new AffineTransformOp(AffineTransform
				.getScaleInstance(ratio, ratio), null);
		scaledImage = op.filter(srcImage, null);

		return (BufferedImage) scaledImage;
	}

}
