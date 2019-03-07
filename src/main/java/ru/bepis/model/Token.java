package ru.bepis.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@Entity
@Table(name="tokens")
public class Token {
  @Id
  private String username;
  private String token;
  private Date expiry;

  public Token(String username, String token, Date expiry) {
    this.username = username;
    this.expiry = expiry;
    this.token = token;
  }
}
