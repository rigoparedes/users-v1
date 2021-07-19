package com.mycompany.users.adapter.ouput.database.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "phones")
@Getter
@Setter
public class PhoneDb {

  @Id
  @GeneratedValue(generator = "SEQUENCE_GENERATOR", strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "SEQUENCE_GENERATOR", sequenceName = "phones_seq", allocationSize = 1)
  private Integer id;
  @ManyToOne
  private UserDb user;
  private String number;
  private String cityCode;
  private String countryCode;
}
