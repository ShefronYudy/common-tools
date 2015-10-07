package org.shefron.fc.web;

/*
 * 创建日期 2006-7-23
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 * 参数：
 * count:验证码的字符个数
 * width:验证码图片宽度
 * height:验证码图片高度
 * type:类型 0-纯数字 1-纯大写字母 2-数字和大学字母混合
 * 功能：生成验证码图片，将验证码保存进session里
 */import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.awt.*;
import java.awt.image.*;
import java.util.*;
import javax.imageio.*;

public class ValidationCodeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final int TYPE_NUMBER = 0;

	private final int TYPE_LETTER = 1;

	private final int TYPE_MULTIPLE = 2;

	private int width;

	private int height;

	private int count;

	private int type;

	private String validate_code;

	private Random random;

	private Font font;

	private int line;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		width = 56;
		height = 22;
		count = 4;
		type = TYPE_NUMBER;
		random = new Random();
		line = 200;
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image.jpeg");

		String reqCount = request.getParameter("count");
		String reqWidth = request.getParameter("width");
		String reqHeight = request.getParameter("height");
		String reqType = request.getParameter("type");

		if (reqCount != null && reqCount != "")
			this.count = Integer.parseInt(reqCount);
		if (reqWidth != null && reqWidth != "")
			this.width = Integer.parseInt(reqWidth);
		if (reqHeight != null && reqHeight != "")
			this.height = Integer.parseInt(reqHeight);
		if (reqType != null && reqType != "")
			this.type = Integer.parseInt(reqType);

		font = new Font("Courier New", Font.BOLD, width / count);

		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		Graphics g = image.getGraphics();

		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);

		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < line; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		g.setFont(font);
		validate_code = getValidateCode(count, type);
		request.getSession().setAttribute("validate_code", validate_code);
		for (int i = 0; i < count; i++) {
			g.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			int x = (int) (width / count) * i;
			int y = (int) ((height + font.getSize()) / 2) - 5;
			g.drawString(String.valueOf(validate_code.charAt(i)), x, y);
		}

		g.dispose();
		ImageIO.write(image, "JPEG", response.getOutputStream());
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private Color getRandColor(int from, int to) {
		Random random = new Random();
		if (to > 255)
			from = 255;
		if (to > 255)
			to = 255;
		int rang = Math.abs(to - from);
		int r = from + random.nextInt(rang);
		int g = from + random.nextInt(rang);
		int b = from + random.nextInt(rang);
		return new Color(r, g, b);
	}

	private String getValidateCode(int size, int type) {
		StringBuffer validate_code = new StringBuffer();
		for (int i = 0; i < size; i++) {
			validate_code.append(getOneChar(type));
		}
		return validate_code.toString();
	}

	private String getOneChar(int type) {
		String result = null;
		switch (type) {
		case TYPE_NUMBER:
			result = String.valueOf(random.nextInt(10));
			break;

		case TYPE_LETTER:
			result = String.valueOf((char) (random.nextInt(26) + 65));
			break;

		case TYPE_MULTIPLE:
			if (random.nextBoolean()) {
				result = String.valueOf(random.nextInt(10));
			} else {
				result = String.valueOf((char) (random.nextInt(26) + 65));
			}
			break;
		default:
			result = null;
			break;
		}
		if (result == null)
			throw new NullPointerException("获取验证码出错");
		return result;
	}
}
