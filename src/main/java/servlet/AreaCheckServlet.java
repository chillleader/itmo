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


    if(xValues == null && yValues == null && rValues == null) {
      pw.println(parseJSON(history));
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

    PrintWriter writer = resp.getWriter();
    writer.append("<html><body>");
    writer.append("<style>" + STYLE + "</style>");
    writer.append(
        "<table class=\"inner\">");
    writer.append("<tr>");
    writer.append("<td>X</td>");
    writer.append("<td>Y</td>");
    writer.append("<td>R</td>");
    writer.append("<td>Result</td>");
    writer.append("</tr>");
    if (resultPoint != null) {
      for (Request request : resultPoint) {
        writer.append("<tr>");
        writer.append("<td>").append(String.valueOf(request.x)).append("</td>");
        writer.append("<td>").append(String.valueOf(request.y)).append("</td>");
        writer.append("<td>").append(String.valueOf(request.r)).append("</td>");
        writer.append("<td>").append(String.valueOf(request.check)).append("</td>");
        writer.append("</tr>");
      }
    }
    writer.append("</table>");
    writer.append("<a href=\"" + req.getContextPath()
        + "\" style=\"position: absolute; right: 10%; bottom: 10%;\">Вернуться</a>");
    writer.append("</body></html>");

  }

  private String parseJSON(List<Request> points) {
    StringBuilder sb = new StringBuilder();
    sb.append("{\"points\":[");
    for (Request r : points) {
      sb.append("{\"x\":");
      sb.append(r.x);
      sb.append(",\"y\":");
      sb.append(r.y);
      sb.append(",\"r\":");
      sb.append(r.r);
      sb.append(",\"result\":");
      sb.append(r.check);
      sb.append("},");
    }
    sb.replace(sb.length() - 1, sb.length(), "");
    sb.append("]}");
    return sb.toString();
  }
}
