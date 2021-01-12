package weather.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.entity.UserAccount;
import weather.dto.InformationServiceImpl;
import weather.dto.InformationStorage;;

/**
 * This Servlet provides the information about the weather and the outfit advice
 */
@WebServlet(urlPatterns = "/WeatherServlet", name = "WeatherServlet")
public class WeatherServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		setAttributesUserInfo(request);

		String cityName = request.getParameter("city").toString();
		InformationStorage info = new InformationServiceImpl().getGeneralInformation(cityName);
		if (cityName.length() > 0 && info != null) {
			setAttributesWeather(info, request);
			request.getRequestDispatcher("/pages/weather.jsp").forward(request, response);
		} else {
			request.setAttribute("error_weather", "The city with such name was not found!");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
	}

	/**
	 * This method receives as the parameters the object of the
	 * {@code InformationStorage} class and the {@code HttpServletRequest} class and
	 * sets the attributes to provide the information about the weather, outfit tips
	 * for the jsp page.
	 */
	private void setAttributesWeather(InformationStorage info, HttpServletRequest request) {
		request.setAttribute("city", info.getCityName());
		request.setAttribute("temperature", info.getTemperature());
		request.setAttribute("condition", info.getCondition());
		request.setAttribute("img", info.getUrlIconWeather());
		request.setAttribute("outfit", info.getOutfit());

		if (info.getAccessories() != null) {
			request.setAttribute("accessories", info.getAccessories());
		} else {
			request.setAttribute("accessories", "a good mood");
		}
	}

	/**
	 * This method receives as the parameters the object of the
	 * {@code HttpServletRequest} class and sets the attributes to provide the
	 * information about the user for the jsp page.
	 */
	private void setAttributesUserInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null) {
			UserAccount user = (UserAccount) session.getAttribute("user");
			session.setAttribute("user", user);
			request.setAttribute("role", user.getRole());
		}
	}

}
