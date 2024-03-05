package com.csi.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CUSTOMER")

public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int custId;

    @Size(min = 4, message = "CUSTNAME MUST BE ATLAEST 4 CHARACTER")
    private String custName;

    @NotNull
    private String custAddress;

    private double custAccountBalance;

    @Column(unique = true)
    @Range(min = 1000000000, max = 9999999999L, message = "CONTACT NUMBER MUST BE OF 10 DIGIT")
    private long custContactNumber;

    private String custGender;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date custDOB;

    @Email(message = "EMAIL ID IS NOT VALID")
    private String custEmailId;

    @Size(min = 4, message = "PASSWORD MUST BE OF ATLEAST 4 CHARACTERS")
    private String custPassword;

}
