package ru.bepis.util;

import java.io.IOException;
import java.util.Date;
import javax.annotation.Priority;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import ru.bepis.dao.UserCredentialsDaoInterface;
import ru.bepis.model.Token;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

  private static final String AUTHENTICATION_SCHEME = "Bearer";

  @EJB
  UserCredentialsDaoInterface userDao;

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {

    // Get the Authorization header from the request
    String authorizationHeader =
        requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

    // Validate the Authorization header
    if (!isTokenBasedAuthentication(authorizationHeader)) {
      abortWithUnauthorized(requestContext, false);
      return;
    }

    // Extract the token from the Authorization header
    String token = authorizationHeader
        .substring(AUTHENTICATION_SCHEME.length()).trim();

    try {

      validateToken(token);

    } catch (Exception e) {
      abortWithUnauthorized(requestContext, true);
    }
  }

  private boolean isTokenBasedAuthentication(String authorizationHeader) {

    return authorizationHeader != null && authorizationHeader.toLowerCase()
        .startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
  }

  private void abortWithUnauthorized(ContainerRequestContext requestContext, boolean tokenPresent) {

    if (tokenPresent) requestContext.abortWith(Response.status(401).build());
    else requestContext.abortWith(Response.status(403).build());
  }

  private void validateToken(String token) throws Exception {
    Token tok = userDao.findToken(token);
    if (tok == null) throw new Exception();
    if (tok.getExpiry().getTime() <= new Date().getTime()) throw new Exception();
  }
}