package user.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import user.entity.UserAccount;
import user.entity.UserAccount.CredentialRole;

/**
 * This Servlet Filter checks if current HttpSession is the session of
 * administrator, and allows the access to administrative pages
 */
@WebFilter(urlPatterns = "/pages/admin/*")
public class AdminFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession();

		if (session.getAttribute("user") != null) {
			UserAccount user = (UserAccount) session.getAttribute("user");
			if (user.getRole().equals(CredentialRole.administrator)) {
				chain.doFilter(request, response);
			}
		} else {
			request.setAttribute("error", "Access denied. Please, log in.");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
	}

}
