package com.shefron.test.utils;

import java.awt.AlphaComposite;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * 图片调整大小及添加水印
 *
 * @author a
 *
 */
public final class ImageUtils {

	public static void handleRGB(List<File> files){
		BufferedImage bis = null;
		for(File file : files){
			try{
				System.out.println("#"+file.getCanonicalPath());
				bis = ImageIO.read(file);
				bis.getWidth();
				bis.getHeight();
				
			}catch(Exception e){
				try
				{
					ThumbnailConvert tc = new ThumbnailConvert();
					tc.setCMYK_COMMAND(file.getPath());
					Image image =null;
					image = Toolkit.getDefaultToolkit().getImage(file.getPath());
					MediaTracker mediaTracker = new MediaTracker(new Container());
					mediaTracker.addImage(image, 0);
					mediaTracker.waitForID(0);
					image.getWidth(null);
					image.getHeight(null);
				}catch (Exception e1){
					e1.printStackTrace();
				}
			}
		}
	}

	/**
     * 批量压缩源文件
     *
     * @param orgImages 源文件
     * @param destWidth 生成文件的最大长度
     * @param destHeight 生成文件的最大宽度
     * @param destPrefix 生成文件前缀
     * @param fileType 文件类型:jpg,png等
     */
	public static void compressImage(List<File> orgImages, int destWidth,
									 int destHeight, String destPrefix, String fileType) {

		BufferedImage srcImage = null;
		try {
			int seq = 1;
			for (File orgImage : orgImages) {
				System.out.println("file="+orgImage.getCanonicalPath());
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
     * @param orgImages 源文件
     * @param destPrefix 生成文件前缀
     * @param fileType 生成文件类型:jpg,png等
     * @param logoImage 水印文件
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
				System.out.println("file="+orgImage.getCanonicalPath());
				originalImage = ImageIO.read(orgImage);
				int originalWidth = originalImage.getWidth(null);
				int originalHeight = originalImage.getHeight(null);

				if (originalWidth <= watermarkWidth
						//|| originalHeight <= watermarkHeight
						|| originalWidth < 240 || originalHeight < 140) {
					continue;
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
						(originalHeight - watermarkHeight+60), watermarkWidth,
						watermarkHeight, null);
				g.dispose();

				System.out.println("file="+orgImage.getName());

//				String name = ""+seq;
				String name = orgImage.getName().substring(0, orgImage.getName().lastIndexOf("."));

				String destNamePath = orgImage.getParentFile().getPath()
						+ File.separator + destPrefix + name + "." + fileType;
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
     * @param bgImage 背景图片
     * @param fileType 生成文件类型jpg,png等
     * @param images 源图片
     * @param destPrefix 文件名前缀
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
	
	private static void weixinReCreateImg(){
	    File dir = new File("E:/temp/res");
        File[] files = dir.listFiles();
        BufferedImage orgImg = null;
        BufferedImage newImage = null;
        for(File file : files){
            try {
                orgImg = ImageIO.read(file);
                newImage = new BufferedImage(orgImg.getWidth(null), orgImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
                Graphics2D g = newImage.createGraphics();
                g.drawImage(orgImg, 0, 0, orgImg.getWidth(null), orgImg.getHeight(null), null);
                g.dispose();
                
                ImageIO.write(newImage, "jpg", new FileOutputStream((file
                        .getParentFile()
                        + File.separator + "_" + file.getName())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
	
	private static void markList(){
	       String logoImage = ImageUtils.class.getResource("/resource/watermark.png").getPath();
	        File dir = new File("E:/temp/res");
	        File[] files = dir.listFiles();

//	        List<File> fileList = Arrays.asList(files);
//
//	        Collections.sort(fileList,new Comparator<File>(){
//	            public int compare(File o1, File o2) {
//	                int seq1 = Integer.parseInt(o1.getName().substring(0, o1.getName().lastIndexOf('.')));
//	                int seq2 = Integer.parseInt(o2.getName().substring(0, o2.getName().lastIndexOf('.')));
//
//	                return seq1>seq2?1:-1;
//	            }
//	        });

        ImageUtils.mark2Image(files, "", "jpg", logoImage);

        // 压缩图片
//	      handleRGB(fileList);
//	      compressImage(fileList,800,4000,"","jpg");
	}


	public static void main(String[] args){
	    markList();
	    
//	    weixinReCreateImg();

	}

}
