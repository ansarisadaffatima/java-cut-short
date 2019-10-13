package maven.servlet.captcha.generation;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/captchaGeneration")
public class MyServlet extends HttpServlet {

	public static final String CAPTCHA_KEY = "captcha_key_name";
	
	private int height = 25;
	private int width = 160;

	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServletOutputStream out = response.getOutputStream();
		response.setContentType("image/jpeg");
		String captchaValue = randomCaptchaGeneration().toString();
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = image.createGraphics();
		Color c = new Color(0.6662f, 0.4569f, 0.3232f);
		GradientPaint gp = new GradientPaint(30, 30, c, 15, 25, Color.white, true);
		graphics2D.setPaint(gp);
		Font font = new Font("Verdana", Font.CENTER_BASELINE, 26);
		graphics2D.setFont(font);
		graphics2D.drawString(captchaValue, 2, 20);
		graphics2D.dispose();

		HttpSession session = request.getSession(false);

		session.setAttribute(CAPTCHA_KEY, captchaValue);

		OutputStream outputStream = response.getOutputStream();
		ImageIO.write(image, "jpg", outputStream);
		outputStream.flush();
		outputStream.close();

	}

	StringBuffer randomCaptchaGeneration() {
		char[] charArray = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
		int index;

		Random random = new Random();
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < 8; i++) {
			char newChar;
			index = random.nextInt(charArray.length);
			newChar = charArray[index];
			sb.append(newChar);
		}

		//System.out.println(sb);
		return sb;

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String captchaText = (String) request.getSession().getAttribute(CAPTCHA_KEY);
		String userInput = request.getParameter("captchText");
		
		if(captchaText.equals(userInput)) {
			System.out.println("Equal");
			//response.sendRedirect(response.encodeRedirectURL("success.jsp"));
		}
		else {
			//System.out.println("Incorrect");
			response.sendRedirect(response.encodeRedirectURL("error.jsp"));
		}
		
		request.getSession().invalidate();
	}
}
