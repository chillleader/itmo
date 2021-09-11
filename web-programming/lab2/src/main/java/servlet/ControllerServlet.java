package servlet;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    resp.setCharacterEncoding("UTF-8");
    req.getRequestDispatcher("/index.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    resp.setCharacterEncoding("UTF-8");

    Enumeration<String> params = req.getParameterNames();

    boolean hasX = false;
    boolean hasY = false;
    boolean hasR = false;
    while (params.hasMoreElements()) {
      String s = params.nextElement();
      if (s.equals("x")) {
        hasX = true;
      } else if (s.equals("y")) {
        hasY = true;
      } else if (s.equals("r")) {
        hasR = true;
      }
    }

    if (hasX && hasY && hasR) {
      RequestDispatcher rd = req.getRequestDispatcher("check");
      rd.forward(req, resp);
    } else {
      resp.getWriter().println("Wrong");
    }
  }
}
