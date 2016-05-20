package hu.hnk.loginservices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

/**
 * Az authentikációs szolgáltatás.
 * 
 * @author Nandi
 *
 */
@Service("myAuthenticationSuccessHandler")
public class MyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	/**
	 * Az authentikációt végző metódus.
	 * 
	 * @param request
	 *            a {@link HttpServletRequest}
	 * @param response
	 *            a {@link HttpServletResponse}
	 * @param authentication
	 *            {@link Authentication}
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		User currentUser = (User) authentication.getPrincipal();

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(auth.getAuthorities());

		Authentication newAuth = new UsernamePasswordAuthenticationToken(currentUser, auth.getCredentials(),
				authorities);
		SecurityContextHolder.getContext()
				.setAuthentication(newAuth);
		setDefaultTargetUrl("/public/index.xhtml");

		super.onAuthenticationSuccess(request, response, authentication);
	}
}