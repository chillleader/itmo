package servlet;

public class PointValidator {
  public static boolean isValidPoint(double x, double y, double r) {
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
        res = (Math.sqrt(x * x + y * y) <= (r/2));
      }
    } else {
      res = (y <= -x + r && y >= 0);
    }
    return res;
  }

}
