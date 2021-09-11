package ru.bepis.dao;

import java.util.Date;
import javax.ejb.Local;
import ru.bepis.model.Token;
import ru.bepis.model.UserCredentials;

@Local
public interface UserCredentialsDaoInterface {
  UserCredentials findByUsername (String username);
  void save(UserCredentials user);
  void update(UserCredentials user);
  void delete(UserCredentials user);
  Token findTokenByUsername(String username);
  void updateToken(Token token);
  Token findToken(String token);

}
