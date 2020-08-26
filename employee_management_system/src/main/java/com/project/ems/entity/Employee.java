package com.project.ems.entity;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Entity
public class Employee {
	
	@Id    
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GeneratorName")
    @SequenceGenerator(name="GeneratorName", sequenceName = "seq1",allocationSize = 1)
	private Integer id;
	@NotBlank(message = "{validation.name.notBlank}")
	@Size(max = 20,message = "{validation.name.size}")
	private String name;
	@Max(value = 60,message = "{validation.age.max}")
	@Min(value = 20,message = "{validation.age.min}")
	@NotNull(message = "{validation.age.notNull}")
	private Integer age;
	@NotBlank(message = "{validation.email.notBlank}")
	@Size(max = 50)
	@Email(message = "{validation.email.format}")
	private String email;
	@NotBlank(message = "{validation.dept.notBlank}")
	private String dept;
	@NotBlank(message = "{validation.city.notBlank}")
	private String city;
	@NotBlank(message = "{validation.phone.notBlank}")
	@Pattern(regexp="(^$|[0-9]{10})",message ="{validation.phone.pattern}" )
	private String phone;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", age=" + age + ", email=" + email + ", dept=" + dept
				+ ", city=" + city + ", phone=" + phone + "]";
	}

	
	
	

}
