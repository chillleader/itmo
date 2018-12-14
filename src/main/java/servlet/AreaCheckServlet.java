package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AreaCheckServlet extends HttpServlet {

  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    PrintWriter pw = resp.getWriter();
    String[] xValues = req.getParameterValues("x");
    String[] rValues = req.getParameterValues("r");
    String[] yValues = req.getParameterValues("y");

    List<Boolean> checkResults = new ArrayList<Boolean>();
    double xi, yi, ri;

    for (String x : xValues) {
      for (String y : yValues) {
        for (String r : rValues) {
          try {
            xi = Double.parseDouble(x);
            yi = Double.parseDouble(y);
            ri = Double.parseDouble(r);
            checkResults.add(checkPoint(xi, yi, ri));
          }
          catch (NumberFormatException e) {
            pw.println("Incorrect input");
          }
        }
      }
    }
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
