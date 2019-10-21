package kokhanevych.internetshop.web.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kokhanevych.internetshop.lib.Inject;
import kokhanevych.internetshop.model.Role;
import kokhanevych.internetshop.model.User;
import kokhanevych.internetshop.service.UserService;

public class AuthorizationFilter implements Filter {
    private static final String EMPTY_STRING = "";
    @Inject
    private static UserService userService;
    public static final String COOKIE_NAME = "Mate";

    private Map<String, Role.RoleName> protectedUrls = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        protectedUrls.put("/servlet/getAllUsers", Role.RoleName.ADMIN);
        protectedUrls.put("/servlet/deleteUser", Role.RoleName.ADMIN);
        protectedUrls.put("/servlet/addToBucket", Role.RoleName.USER);
        protectedUrls.put("/servlet/deleteItem", Role.RoleName.USER);
        protectedUrls.put("/servlet/getBucket", Role.RoleName.USER);
        protectedUrls.put("/servlet/addOrder", Role.RoleName.USER);
        protectedUrls.put("/servlet/getOrders", Role.RoleName.USER);
        protectedUrls.put("/servlet/deleteOrder", Role.RoleName.USER);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            processUnAuthenticated(req, resp);
            return;
        }

        String requestedUrl = req.getRequestURI().replace(req.getContextPath(), EMPTY_STRING);
        Role.RoleName roleName = protectedUrls.get(requestedUrl);
        if (roleName == null) {
            processAuthenticated(filterChain, req, resp);
            return;
        }

        String token = null;
        for (Cookie cookie: req.getCookies()) {
            if (cookie.getName().equals(COOKIE_NAME)) {
                token = cookie.getValue();
                break;
            }
        }

        if (token == null) {
            processUnAuthenticated(req, resp);
            return;
        } else {
            Optional<User> user = userService.getByToken(token);
            if (user.isPresent()) {
                if (verifyRole(user.get(), roleName)) {
                    processAuthenticated(filterChain, req, resp);
                    return;
                } else {
                    processDenied(req, resp);
                    return;
                }
            } else {
                processUnAuthenticated(req, resp);
                return;
            }
        }
    }

    private boolean verifyRole(User user, Role.RoleName roleName) {
        return user.getRoles().stream().anyMatch(r -> r.getRoleName().equals(roleName));
    }

    private void processDenied(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp").forward(req, resp);
    }

    private void processAuthenticated(FilterChain filterChain, HttpServletRequest req,
                                      HttpServletResponse resp)
            throws IOException, ServletException {
        filterChain.doFilter(req, resp);
    }

    private void processUnAuthenticated(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.sendRedirect(req.getContextPath() + "/login");
    }

    @Override
    public void destroy() {

    }
}
