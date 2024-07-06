package com.naveenj.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="disease")
public class UserDetails {
	
	 @Id
	 private long diseaseid;
	 
	 private long id;
	    private String patientName;
	    private String email;
	    private String phoneno;
	    private String Bloodgroup;
	    private String patientGen;
	    private  int age;
	    private long doctorid;
	    private String doctorname;
	    private String doctoremail;
	    private String doctorphoneno;
		private String BP;
		private String Sugar;
		private String disease;
		private String healthcondition;
		private String precaution;
		private String bookingDate;
		private String  appointmentTime;

		
		
		
   
		
}
    

	
	

