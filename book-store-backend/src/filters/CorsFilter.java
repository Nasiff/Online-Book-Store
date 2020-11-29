package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class CorsFilter for enabling CORS on Jersey
 */
@WebFilter("/rest/*")
public class CorsFilter implements Filter {
	
    public CorsFilter() {}

	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;

		// Only add the following headers if they weren't already added before
		if (res.getHeader("Access-Control-Allow-Origin") == null) {
			res.addHeader("Access-Control-Allow-Origin", "*");
			res.addHeader("Access-Control-Allow-Headers", "*");
			res.addHeader("Access-Control-Allow-Methods", "*");			
		}

		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {}

}
