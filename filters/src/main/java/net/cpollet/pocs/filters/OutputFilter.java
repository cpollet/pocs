package net.cpollet.pocs.filters;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by cpollet on 12.01.17.
 */
@Component
@Log4j
public class OutputFilter implements Filter {
    @Autowired
    private FriendAndFamilyCacheLoader friendsAndFamilyService;

    private LoadingCache<String, Boolean> friendsAndFamily;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        friendsAndFamily = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .build(friendsAndFamilyService);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (!isActive((HttpServletRequest) request)) {
            filterChain.doFilter(request, response);
            return;
        }

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

    private boolean isActive(HttpServletRequest request) {
        String remoteUser = request.getRemoteUser() == null ? "remote_user" : request.getRemoteUser();

        if (remoteUser == null) {
            return false;
        }

        try {
            return friendsAndFamily.get(remoteUser);
        } catch (ExecutionException e) {
            return false;
        }
    }

    @Override
    public void destroy() {
        // nothing
    }
}
