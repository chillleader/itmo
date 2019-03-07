package ru.bepis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name="verdicts")
public class Verdict {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;
  private double x;
  private double y;
  private double r;
  private boolean verdict;

  public Verdict(double x, double y, double r, boolean verdict) {
    this.x = x;
    this.y = y;
    this.r = r;
    this.verdict = verdict;
  }


}
