package app.sequrity;

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

        // Ако всичко е наред, потребителят продължава към сайта
        return true;
    }
}
