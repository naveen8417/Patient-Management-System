package com.naveenj.model;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
public class UserDetails {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY) // Use GenerationType.IDENTITY for auto-incrementing primary keys
	    private long appointmentid;
	 
	     private String doctorid;
	     private String doctorName;
	     private String specialization;
	     private String availability;
	     private String phoneNo;
	     private String id;
	     private String patientName;
	     private String email;
	     private String date;
	     private String slot;
	     private String status;

   
		
}
    

	
	

