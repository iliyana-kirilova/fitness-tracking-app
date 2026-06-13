package app.sequrity;

import app.models.dto.user.UserDto;
import app.models.entity.user.UserRole;
import app.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Set;
import java.util.UUID;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    private static final Set<String> UNAUTHENTICATED_ENDPOINTS =
        Set.of("/", "/login", "/register");

    private static final Set<String> ADMIN_ENDPOINTS = Set.of("/admin/logs");

    private final UserService  userService;

    public SessionInterceptor(UserService userService) {
        this.userService = userService;
    }


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String endpoint = request.getServletPath();

        // 1. Ако страницата е публична, я отваряме директно
        if (UNAUTHENTICATED_ENDPOINTS.contains(endpoint)) {
            return true;
        }

        // 2. Проверяваме дали изобщо съществува сесия
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("/login");
            return false;
        }

        // 3. Проверяваме дали в сесията има записано потребителско ID
        UUID userId = (UUID) session.getAttribute("userId");
        if (userId == null) {
            session.invalidate(); // За всеки случай унищожаваме "счупената" сесия
            response.sendRedirect("/login");
            return false;
        }

        UserDto user = userService.getById(userId);

        // Проверка за ролята на потребителя
        if (ADMIN_ENDPOINTS.contains(endpoint) && !user.getRole().name().equals("ADMIN")) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("You do not have permission to access this resource.");
            return false;
        }

        // Ако всичко е наред, потребителят продължава към сайта
        return true;
    }
}
