package ru.bepis.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@ToString
public class UserCredentials {

  @Id
  private String username;
  private String password;

/*  @JoinTable(name = "tokens",
      joinColumns = {
          @JoinColumn(name = "username", referencedColumnName = "username")
      },
      inverseJoinColumns = {
          @JoinColumn(name = "username", referencedColumnName = "username", unique = true)
      }
  )
  private Token token;*/

  public UserCredentials(String username, String password) {
    this.username = username;
    this.password = password;
  }
}
