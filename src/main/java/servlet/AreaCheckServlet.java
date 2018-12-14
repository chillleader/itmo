package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AreaCheckServlet extends HttpServlet {

  private final String JSP_HANDLER = "response.jsp";

  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    PrintWriter pw = resp.getWriter();
    resp.setCharacterEncoding("UTF-8");
    HttpSession session = req.getSession();
    String[] xValues = req.getParameterValues("x");
    String[] rValues = req.getParameterValues("r");
    String[] yValues = req.getParameterValues("y");

    List<Request> checkResults = new ArrayList<Request>();
    List<Request> history;
    List<Request> newHistory = new ArrayList<Request>();
    if (session.isNew() || session.getAttribute("history") == null) {
      history = new ArrayList<Request>();
    }
    else {
      history = (List<Request>)session.getAttribute("history");
    }
    double xi, yi, ri;

    for (String x : xValues) {
      for (String y : yValues) {
        for (String r : rValues) {
          try {
            xi = Double.parseDouble(x);
            yi = Double.parseDouble(y);
            ri = Double.parseDouble(r);
            boolean res = checkPoint(xi, yi, ri);
            checkResults.add(new Request(xi, yi, ri, res));
            newHistory.add(new Request(xi, yi, ri, res));
          }
          catch (NumberFormatException e) {
            session.setAttribute("correct", false);
            pw.println("Incorrect input");
          }
        }
      }
    }
    req.setAttribute("results", checkResults);
    req.setAttribute("history", history);
    session.setAttribute("correct", true);
    history.addAll(newHistory);
    session.setAttribute("history", history);
    session.setAttribute("current", checkResults);
    RequestDispatcher rd = req.getRequestDispatcher(JSP_HANDLER);
    rd.forward(req, resp);

  }

  private boolean checkPoint(double x, double y, double r) {
    if (r < 0) return false;
    boolean res;
    if (x <= 0 && y <= 0)
      res = (x >= -r && y >= -r/2);
    else if (x < 0) {
      if (Math.abs(x) > r || Math.abs(y) > r) res = false;
      else res = (Math.sqrt(x * x + y * y) <= r);
    }
    else {
      res = (y <= -x + r && y >= 0);
    }
    return res;
  }
}
