package servlets;

import model.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import service.UserService;
import util.JsonConverter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "login", value = "/login")
public class LoginServlet extends HttpServlet {
    private final UserService userService = new UserService();
    static final Logger logger = LogManager.getLogger(LoginServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Executing post login command");

        try {
            JSONObject jsonObject = JsonConverter.jsonBodyFromRequest(req, resp);
            String name = (String) jsonObject.get("username");
            String password = (String) jsonObject.get("password");
            User user = userService.loginUser(name, password);

            JsonConverter.makeJsonAnswer(user, resp);
        } catch (Exception e) {
            logger.error("User can`t be logged due to exception: {}", e.getMessage());
            resp.sendError(400);
        }
    }
}
