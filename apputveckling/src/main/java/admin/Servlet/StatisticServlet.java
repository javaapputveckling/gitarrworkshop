package admin.Servlet;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "MyServlet", urlPatterns = "/myservlet")
public class StatisticServlet extends HttpServlet {
    public StatisticServlet() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        String [] month = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};// för db sen, det är bara ändra här
        int [] income = {21000,20000,30000,21000,50000,30000,15000,17000,21000,30000,25000,30000};
        for (int i = 0; i < 12; i++) {
            JsonObject json = Json.createObjectBuilder()
                    .add("month", month[i])
                    .add("income", income[i])
                    .build();
            arrayBuilder.add(json);
        }
        PrintWriter out = response.getWriter();
        out.println(arrayBuilder.build().toString());
        out.close();
    }

}

