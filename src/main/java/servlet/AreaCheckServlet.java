package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AreaCheckServlet extends HttpServlet {

  private static final String STYLE = "table.inner {\n"
      + "  width: 100%;\n"
      + "  border: 3px solid #4d7198;\n"
      + "  border-collapse: collapse;\n"
      + "  text-align: left;\n"
      + "  margin: auto;\n"
      + "}\n"
      + "\n"
      + "table.inner td {\n"
      + "  border: 1px solid #4d7198;\n"
      + "}\n"
      + "\n"
      + "table.inner th {\n"
      + "  border-bottom: 3px solid #4d7198;\n"
      + "  border-right: 1px solid #4d7198;\n"
      + "  padding: 7px;\n"
      + "}";

  private static final String JSP_HANDLER = "response.jsp";

  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
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
    } else {
      history = (List<Request>) session.getAttribute("history");
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
          } catch (NumberFormatException e) {
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
    checkResults.addAll(history);

    createAndSendPage(newHistory, req, resp);
  }

  private boolean checkPoint(double x, double y, double r) {
    if (r < 0) {
      return false;
    }
    boolean res;
    if (x <= 0 && y <= 0) {
      res = (x >= -r && y >= -r / 2);
    } else if (x < 0) {
      if (Math.abs(x) > r || Math.abs(y) > r) {
        res = false;
      } else {
        res = (Math.sqrt(x * x + y * y) <= r);
      }
    } else {
      res = (y <= -x + r && y >= 0);
    }
    return res;
  }

  private void createAndSendPage(List<Request> resultPoint, HttpServletRequest req,
      HttpServletResponse resp) throws IOException {
    resp.setContentType("text/html; charset=cp1251");
    resp.getWriter().append("<html><body>");
    resp.getWriter().append("<style>" + STYLE + "</style>");
    resp.getWriter().append(
        "<table class=\".inner\">");
    resp.getWriter().append("<tr>");
    resp.getWriter().append("<td>X</td>");
    resp.getWriter().append("<td>Y</td>");
    resp.getWriter().append("<td>R</td>");
    resp.getWriter().append("<td>Result</td>");
    resp.getWriter().append("</tr>");
    if (resultPoint != null) {
      for (Request request : resultPoint) {
        resp.getWriter().append("<tr>");
        resp.getWriter().append("<td>").append(String.valueOf(request.x)).append("</td>");
        resp.getWriter().append("<td>").append(String.valueOf(request.y)).append("</td>");
        resp.getWriter().append("<td>").append(String.valueOf(request.r)).append("</td>");
        resp.getWriter().append("<td>").append(String.valueOf(request.check)).append("</td>");
        resp.getWriter().append("</tr>");
      }
    }
    resp.getWriter().append("</table>");
    resp.getWriter().append("<a href=\"" + req.getContextPath()
        + "\" style=\"position: absolute; right: 10%; bottom: 10%;\">Вернуться</a>");
    resp.getWriter().append("</body></html>");
  }
}
