package ru.bepis.rest;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;
import ru.bepis.dao.UserCredentialsDao;
import ru.bepis.dao.UserCredentialsDaoInterface;
import ru.bepis.model.Token;
import ru.bepis.model.UserCredentials;
import ru.bepis.util.CORS;

@Path("/authorize")
public class AuthorizationEndpoint {

  @EJB
  UserCredentialsDaoInterface userDao;

  @Produces(MediaType.APPLICATION_JSON)
  @GET
  @CORS
  public Response authenticateUser(@QueryParam("user") String username,
      @QueryParam("password_sha256") String password) {

    try {
      // Authenticate the user using the credentials provided
      authenticate(username, password);

      // Issue a token for the user
      String token = issueToken(username);
      JSONObject response = new JSONObject();
      response.append("token", token);
      // Return the token on the response
      return Response.ok(response.toString()).build();

    } catch (IllegalAccessException e) {
      return Response.ok().build();
    }
    catch (Exception e) {
      e.printStackTrace();
      return Response.status(503).build();
    }
  }

  private void authenticate(String username, String password) throws Exception {
    try {
      UserCredentials user = userDao.findByUsername(username);
      if (!user.getPassword().equals(password)) throw new IllegalAccessException("Authorization failure");
    } catch (Exception e) {
      UserCredentials user = new UserCredentials(username, password);
      userDao.save(user);
    }
  }

  private String issueToken(String username) {
    String token = username;
    Calendar date = Calendar.getInstance();
    long t = date.getTimeInMillis();
    Date expiry = new Date(t + 10 * 60000);
    Token tok = new Token(username, token, expiry);
    userDao.updateToken(tok);
    return token;
  }
}
