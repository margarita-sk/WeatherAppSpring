package user.servlet;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.SQLRepositorium;
import user.entity.UserAccount;

/**
 * This Servlet provides authentication: doPost method allows to log in and
 * doGet method to log out
 */
@WebServlet(urlPatterns = "/AuthenticationServlet", name = "AuthenticationServlet")
public class AuthenticationServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Optional<String> login = Optional.ofNullable(request.getParameter("login"));
		Optional<String> role = Optional.ofNullable(request.getParameter("role"));

		if (role.isPresent()) {
			logOut(request);
		} else if (login.isPresent()) {
			logIn(request, login.get());
		}

		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Optional<Object> role = Optional.ofNullable(session.getAttribute("role"));
		if (role.isPresent()) {
			request.setAttribute("role", role.get().toString());
		}
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	protected void logIn(HttpServletRequest request, String login) {
		char[] password = request.getParameter("pass").toCharArray();
		Optional<UserAccount> user = Optional.ofNullable(SQLRepositorium.authenticateUser(login, password));
		HttpSession session = request.getSession();

		if (user.isPresent()) {
			session.setAttribute("user", user.get());
			request.setAttribute("role", user.get().getRole().toString());
			session.setMaxInactiveInterval(60 * 5);
		} else {
			request.setAttribute("error", "Wrong login or password!");
		}
	}

	protected void logOut(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		session.removeAttribute("role");
	}

}
