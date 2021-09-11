package servlet;

import static servlet.PointValidator.isValidPoint;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

  private static final String TABLE_ROW_FORMAT =
      "<tr>"
          + "<td>%s</td>\n"
          + "<td>%s</td>\n"
          + "<td>%s</td>\n"
          + "<td>%s</td>\n"
          + "</tr>";

  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    resp.setCharacterEncoding("UTF-8");

    PrintWriter pw = resp.getWriter();
    HttpSession session = req.getSession();

    String valueX = req.getParameter("x");
    String valueY = req.getParameter("y");
    String valueR = req.getParameter("r");

    Request checkResult = null;
    List<Request> history;

    if (session.isNew() || session.getAttribute("history") == null) {
      history = new ArrayList<Request>();
    } else {
      history = (List<Request>) session.getAttribute("history");
    }

    double x, y, r;
    try {
      x = Double.parseDouble(valueX);
      y = Double.parseDouble(valueY);
      r = Double.parseDouble(valueR);
      boolean res = isValidPoint(x, y, r);

      checkResult = new Request(x, y, r, res);

      history.add(checkResult);
    } catch (NumberFormatException e) {
      session.setAttribute("correct", false);
      pw.println("Incorrect input");
    }

    req.setAttribute("history", history);
    session.setAttribute("history", history);

    session.setAttribute("correct", true);
    session.setAttribute("current", checkResult);

    if (req.getParameter("redirect") == null || Boolean.valueOf(req.getParameter("redirect"))) {
      createAndSendPage(history, req, resp);
    } else {
      resp.getWriter().print(parseJSON(Collections.singletonList(checkResult)));
    }
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
        writer.append(
            String.format(TABLE_ROW_FORMAT,
                request.x, request.y, request.r, request.check
            )
        );
      }
    }
    writer.append("</table>");
    writer.append("<a href=\"")
        .append(req.getContextPath())
        .append("\" style=\"position: absolute; right: 10%; bottom: 10%;\">Вернуться</a>");
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
