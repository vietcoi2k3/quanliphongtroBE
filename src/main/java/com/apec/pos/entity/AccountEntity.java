package com.apec.pos.entity;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntity implements UserDetails{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long id;
	
	private String username;
	
	private String password;
	
	private String phoneNumber;
	
	private String accountName;

	private String imageUser;

	private String email;

	private long money;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "accountEntity")
	@JsonManagedReference(value = "account-motel")
	private List<MotelEntity> motelEntities;

	@OneToMany(fetch = FetchType.LAZY,mappedBy = "accountEntity")
	@JsonManagedReference(value = "account-his")
	private List<HistoryPaymentEntity> historyPaymentEntities;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
	        name="user_role",
	        joinColumns = {@JoinColumn(name="user_id")},
	        inverseJoinColumns = {@JoinColumn(name="role_id")}
	    )	
	private Set<RoleEntity> roles;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.roles;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Set<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleEntity> roles) {
		this.roles = roles;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
