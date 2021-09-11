package ru.bepis.rest;

import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.bepis.dao.VerdictDaoInterface;
import ru.bepis.model.Verdict;
import ru.bepis.util.CORS;
import ru.bepis.util.Secured;

@Path("/verdicts")
public class AreaCheck {

  @EJB
  VerdictDaoInterface verdictDao;

  @POST
  @Secured
  @CORS
  @Produces(MediaType.APPLICATION_JSON)
  public Response checkPoint(
      @QueryParam("x") String x,
      @QueryParam("y") String y,
      @QueryParam("r") String r) {

    double xd = Double.parseDouble(x);
    double yd = Double.parseDouble(y);
    double rd = Double.parseDouble(r);
    JSONObject response = new JSONObject();
    response.append("x", x);
    response.append("y", y);
    response.append("r", r);
    String verdict = "false";

    if (xd < 0 && yd > 0) verdict = "false";
    else if (xd >= 0 && yd >= 0) {
      if (xd <= rd / 2 && yd <= rd) verdict = "true";
      else verdict = "false";
    } else if (xd > 0 && yd < 0) {
      if (yd >= xd - rd / 2) verdict = "true";
      else verdict = "false";
    } else {
      if (xd >= -Math.sqrt(rd * rd - yd * yd)) verdict = "true";
      else verdict = "false";
    }
    Verdict verd = new Verdict(xd, yd, rd, verdict.equals("true"));
    try {
      verdictDao.add(verd);
      response.append("verdict", verdict);
      return Response.ok(response.toString()).build();
    } catch (Exception e) {
      e.printStackTrace();
      return Response.status(503).build();
    }
  }

  @GET
  @Secured
  @CORS
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllVerdicts() {
    try {
      JSONObject resp = new JSONObject();
      List<Verdict> list = verdictDao.findAll();
      if (list == null) return Response.ok(resp).build();
      JSONArray verdicts = new JSONArray();
      for (Verdict v : list) {
        JSONObject verdict = new JSONObject();
        verdict.append("x", v.getX());
        verdict.append("y", v.getY());
        verdict.append("r", v.getR());
        verdict.append("verdict", v.isVerdict());
        verdicts.put(verdict);
      }
      resp.append("verdicts", verdicts);
      return Response.ok(resp.toString()).build();
    } catch (Exception e) {
      e.printStackTrace();
      return Response.status(503).build();
    }
  }

  @DELETE
  @Secured
  @CORS
  public Response clearDatabase() {
    try {
      verdictDao.removeAll();
      return Response.ok().build();
    } catch (Exception e) {
      e.printStackTrace();
      return Response.status(503).build();
    }
  }

  @CORS
  @OPTIONS
  public Response options() {
    return Response.ok().header("Allow", "OPTIONS, GET, POST").build();
  }
}
