package com.shipogle.app.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "user")
public class User implements UserDetails {
	@Id
	@GeneratedValue
	@Column(name = "user_id")
	private Integer id;
	@Column(name = "first_name")
	private String first_name;
	@Column(name = "last_name")
	private String last_name;
	@Column(name = "phone")
	private String phone;
	@Column(name = "email")
	private String email;
	@Column(name = "password")
	private String password;
	@Column(name = "gov_id_url")
	private String gov_id_url;
	@Column(name = "profile_pic_url")
	private String profile_pic_url;
	@Column(name = "dob")
	private Date dob;
	@Column(name = "address")
	private String address;
	@Column(name = "city")
	private String city;
	@Column(name = "latitude")
	private String latitude;
	@Column(name = "longitude")
	private String longitude;
	@Column(name = "province")
	private String province;
	@Column(name = "postal_code")
	private String postal_code;
	@Column(name = "country")
	private String country;
	@Column(name = "is_active")
	private Boolean is_activated;
	@Column(name = "is_verified")
	private Boolean is_verified;
	@Column(name = "created_at")
	@CreationTimestamp
	private LocalDateTime created_at;
	@Column(name = "updated_at")
	@UpdateTimestamp
	private LocalDateTime updated_at;

	public Integer getUser_id() {
		return id;
	}

	public String getFirst_name() {
		return first_name;
	}

	private String getLast_name() {
		return last_name;
	}

	private String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	private String getGov_id_url() {
		return gov_id_url;
	}

	private String getProfile_pic_url() {
		return profile_pic_url;
	}

	private Date getDob() {
		return dob;
	}

	private String getAddress() {
		return address;
	}

	private String getCity() {
		return city;
	}

	private String getProvince() {
		return province;
	}

	private String getPostal_code() {
		return postal_code;
	}

	private String getCountry() {
		return country;
	}

	private Boolean getIs_activated() {
		return is_activated;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIs_verified() {
		return is_verified;
	}

	public void setIs_verified(Boolean is_verified) {
		this.is_verified = is_verified;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLocation(String latitude, String longitude){
		this.latitude = latitude;
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return String.format("{ " +
				"user_id:%d," +
				"first_name:'%s'," +
				"last_name:'%s'," +
				"email:'%s'," +
				"gov_id_url:'%s'," +
				"profile_pic_url:'%s'," +
				"dob:'%s'," +
				"address:'%s'," +
				"city:'%s'," +
				"province:'%s'," +
				"postal_code:'%s'," +
				"country:'%s' }", id, first_name, last_name, email, gov_id_url, profile_pic_url, dob, address, city,
				province, postal_code, country);
	}

	public void update(User user) {
		this.first_name = user.getFirst_name();
		this.last_name = user.getLast_name();
		this.phone = user.getPhone();
		this.email = user.getEmail();
		this.gov_id_url = user.getGov_id_url();
		this.profile_pic_url = user.getProfile_pic_url();
		this.dob = user.getDob();
		this.address = user.getAddress();
		this.city = user.getCity();
		this.province = user.getProvince();
		this.postal_code = user.getPostal_code();
		this.country = user.getCountry();
		this.is_activated = user.getIs_activated();
		this.is_verified = user.getIs_verified();
		this.updated_at = LocalDateTime.now();
	}

}
