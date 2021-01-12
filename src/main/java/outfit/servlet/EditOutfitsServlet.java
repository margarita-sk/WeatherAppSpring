package outfit.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SQLRepositorium;
import outfit.entity.Outfit;

/**
 * This Servlet allows to change outfit database: doPost method inserts new
 * clothing and doGet method delete selected outfit
 */
@WebServlet(urlPatterns = "/EditOutfitsServlet", name = "EditOutfitsServlet")
public class EditOutfitsServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("action") != null && request.getParameter("action").equals("delete")) {
			int id = Integer.parseInt(request.getParameter("id"));
			SQLRepositorium.deleteOutfit(id);
		}
		request.getRequestDispatcher("/OutfitsServlet").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		request.setCharacterEncoding("utf-8");
		String outfitName = request.getParameter("name");
		try {
			int minTemp = Integer.parseInt(request.getParameter("minTemp"));
			int maxTemp = Integer.parseInt(request.getParameter("maxTemp"));
			Outfit outfit = new Outfit(maxTemp, minTemp, outfitName);
			SQLRepositorium.addClothing(outfit);
			request.getRequestDispatcher("/OutfitsServlet").forward(request, response);
		} catch (NumberFormatException e) {
			request.setAttribute("error", "Incorrect data!");
			request.getRequestDispatcher("/pages/admin/add_outfit.jsp").forward(request, response);
		}
	}
}
