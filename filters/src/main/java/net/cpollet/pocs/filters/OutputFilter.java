package net.cpollet.pocs.filters;

import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by cpollet on 12.01.17.
 */
@Component
public class OutputFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // nothing
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HtmlResponseWrapper capturingResponseWrapper = new HtmlResponseWrapper((HttpServletResponse) response);

        filterChain.doFilter(request, capturingResponseWrapper);

        if (response.getContentType() != null && response.getContentType().contains("text/html")) {
            String content = capturingResponseWrapper.getCaptureAsString();

            // replace stuff here
            String replacedContent = content.replaceAll(
                    "</body>",
                    "<script>alert('this alert was injected');</script></body>");

            response.getWriter().write(replacedContent);
            response.setContentLength(replacedContent.length());
        }
    }

    @Override
    public void destroy() {
        // nothing
    }
}
