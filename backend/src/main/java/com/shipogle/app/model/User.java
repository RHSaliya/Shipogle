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

	public String getLast_name() {
		return last_name;
	}

	public String getPhone() {
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

	public String getGov_id_url() {
		return gov_id_url;
	}

	public String getProfile_pic_url() {
		return profile_pic_url;
	}

	public Date getDob() {
		return dob;
	}

	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}

	public String getProvince() {
		return province;
	}

	public String getPostal_code() {
		return postal_code;
	}

	public String getCountry() {
		return country;
	}

	public Boolean getIs_activated() {
		return is_activated;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public LocalDateTime getUpdated_at() {
		return updated_at;
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

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
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
}
