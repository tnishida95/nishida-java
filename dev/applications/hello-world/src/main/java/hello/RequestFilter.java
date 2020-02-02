package nishida.hello;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.springframework.stereotype.Component;

@Component
public class RequestFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws ServletException, IOException {
		// do stuff
		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void destroy() {}
}