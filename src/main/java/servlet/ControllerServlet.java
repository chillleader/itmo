package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerServlet extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    Enumeration<String> params = req.getParameterNames();
    PrintWriter pw = resp.getWriter();
    boolean hasX = false;
    boolean hasY = false;
    boolean hasR = false;
    while (params.hasMoreElements()) {
      String s = params.nextElement();
      if (s.equals("x")) hasX = true;
      else if (s.equals("y")) hasY = true;
      else if (s.equals("r")) hasR = true;
    }
    if (hasX && hasY && hasR) {
      RequestDispatcher rd = req.getRequestDispatcher("areaCheck");
      rd.forward(req, resp);
    }
    else pw.println("Wrong");
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    //todo: implement forwarding to jsp
  }
}
