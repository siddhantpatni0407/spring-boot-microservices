package com.sid.sb.jwt.app.service;

import com.sid.sb.jwt.app.dto.UserRegisterRequestDTO;
import com.sid.sb.jwt.app.entity.Role;
import com.sid.sb.jwt.app.entity.User;
import com.sid.sb.jwt.app.entity.UserRole;
import com.sid.sb.jwt.app.repository.IUserRepository;
import com.sid.sb.jwt.app.repository.IUserRoleRepository;
import com.sid.sb.jwt.app.security.SecurityPrincipal;
import com.sid.sb.jwt.app.util.AppUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class UserService implements UserDetailsService {
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IUserRoleRepository userRoleRepository;

	@Autowired
	RoleService roleService;

	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByUsername(username);
		if (user != null) {
			List<UserRole> userRoles = userRoleRepository.findAllByUserId(user.getId());

			Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

			userRoles.forEach(userRole -> {
				authorities.add(new SimpleGrantedAuthority(userRole.getRole().getName()));
			});

			UserDetails principal = new org.springframework.security.core.userdetails.User(user.getUsername(),
					user.getPassword(), authorities);

			return principal;
		}
		return null;
	}

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public String createUser(UserRegisterRequestDTO request) {
		try {
			User user = (User) dtoMapperRequestDtoToUser(request);

			user = userRepository.save(user);
			if (!request.getRoleList().isEmpty()) {
				for (String role : request.getRoleList()) {
					Role existingRole = roleService.findRoleByName("ROLE_" + role.toUpperCase());
					if(existingRole != null) {
						addUserRole(user, existingRole);
					}
				}
			} else {
				addUserRole(user, null);
			}

			return "User successfully created.";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getCause().getMessage();
		}

	}

	public List<User> retrieveAllUserList() {
		return userRepository.findAll();
	}

	public User updateUser(UserRegisterRequestDTO userRequestDTO) {

		User user = (User) dtoMapperRequestDtoToUser(userRequestDTO);

		user = userRepository.save(user);
		addUserRole(user, null);

		return user;
	}

	public User findCurrentUser() {
		return userRepository
				.findById(SecurityPrincipal.getInstance().getLoggedInPrincipal().getId())
				.get();

	}

	public List<UserRole> findAllCurrentUserRole() {
		return userRoleRepository.findAllByUserId(SecurityPrincipal.getInstance().getLoggedInPrincipal().getId());

	}

	public Optional<User> findUserById(long id) {
		return userRepository.findById(id);
	}

	public void addUserRole(User user, Role role) {

		UserRole userRole = new UserRole();
		userRole.setUser(user);

		if (role == null) {
			role = roleService.findDefaultRole();
		}
		
		userRole.setRole(role);
		userRoleRepository.save(userRole);
	}

	private Object dtoMapperRequestDtoToUser(UserRegisterRequestDTO source) {
		log.info("dtoMapperRequestDtoToUser() : source -> {}", AppUtils.getJSONString(source));
		User target = new User();
		target.setEntityNo(source.getEntityNo());
		target.setUsername(source.getUsername());
		target.setPassword(source.getPassword());

		return target;
	}

}
