package servlet;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ValidationFilter implements Filter {

  private boolean active = false;
  private FilterConfig config = null;

  private double[] xValues = {-5, -4, -3, -2, -1, 0, 1, 2, 3};
  private double yLowerBound = -5d;
  private double yUpperBound = 5d;
  private double rLowerBound = 1d;
  private double rUpperBound = 4d;

  public void init(FilterConfig filterConfig) throws ServletException {
    this.config = filterConfig;
    String act = config.getInitParameter("active");
    if (act != null) {
      active = (act.toUpperCase().equals("TRUE"));
    }
  }

  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) servletRequest;
    HttpServletResponse resp = (HttpServletResponse) servletResponse;
    if (active) {

      //check if all params are present

      boolean xOk = true, yOk, rOk;

      // check x array
      String[] xArray = req.getParameterValues("x");
      if (xArray == null) {
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST); // Send 400
        return;
      }
      for (String x : xArray) {
        boolean curXOk = false;
        for (Double d : xValues) {
          if (Double.parseDouble(x) == d) {
            curXOk = true;
            break;
          }
        }
        if (!curXOk) {
          xOk = false;
          break;
        }
      }

      //check y
      if (req.getParameter("y") == null) {
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST); // Send 400
        return;
      }
      double y = Double.parseDouble(req.getParameter("y"));
      yOk = (y > yLowerBound && y < yUpperBound);

      //check r
      if (req.getParameter("r") == null) {
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST); // Send 400
        return;
      }
      double r = Double.parseDouble(req.getParameter("r"));
      rOk = (r > rLowerBound && r < rUpperBound);

      if (xOk && yOk && rOk) {
        filterChain.doFilter(servletRequest, servletResponse);
      }
    } else {
      filterChain.doFilter(servletRequest, servletResponse);
    }
  }

  public void destroy() {
    config = null;
  }
}
