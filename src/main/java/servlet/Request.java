package servlet;

public class Request {
  public double x;
  public double y;
  public double r;
  public boolean check;

  public Request(double x, double y, double r, boolean check) {
    this.x = x;
    this.y = y;
    this.r = r;
    this.check = check;
  }
}
