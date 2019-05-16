package Filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.Service.Cryptography;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureException;

import java.io.IOException;

public class JwtFilter extends GenericFilterBean {

    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
            throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        final String authHeader = request.getHeader("authorization");


        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(req, res);
        } else {
            final String token = authHeader.substring(7);
            if (authHeader == null || !authHeader.startsWith("Bearer ") || token.equals("null")) {
                ((HttpServletResponse) res).setStatus(401);
                return;
            }

            try {
                final Claims claims = Cryptography.decodeJWT(token);
                request.setAttribute("userId", Integer.parseInt(claims.getId()));
            } catch (final SignatureException e) {
                ((HttpServletResponse) res).setStatus(403);
                return;
            }

            chain.doFilter(req, res);
        }
    }
}