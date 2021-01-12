package outfit.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.SQLRepositorium;
import user.entity.UserAccount;

/**
 * This Servlet provides access to the list of outfits from the other pages
 */
@WebServlet(urlPatterns = "/OutfitsServlet", name = "OutfitsServlet")
public class OutfitsServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		setAttributesAndToDispatch(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		setAttributesAndToDispatch(request, response);
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	private void setAttributesAndToDispatch(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null) {
			UserAccount user = (UserAccount) session.getAttribute("user");
			request.setAttribute("outfits", SQLRepositorium.getClothing());
			request.setAttribute("role", user.getRole().toString().toLowerCase());
			request.getRequestDispatcher("/pages/moderator/outfits.jsp").forward(request, response);
		} else {
			request.setAttribute("error", "Access denied. Please, log in.");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
	}

}
