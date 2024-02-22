package com.ms.loginapp.loginapp.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ms.loginapp.loginapp.jwt.JwtService;
import com.ms.loginapp.loginapp.user.UserRepository;
import com.ms.loginapp.loginapp.user.Usuario;
import com.ms.loginapp.loginapp.user.Role;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	
	public AuthResponse login(LoginRequest request) {
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		var userDetails=userRepository.findByUsername(request.getUsername()).orElseThrow();
		var token=jwtService.getToken(userDetails);
		return AuthResponse.builder().token(token).build();
	}
	
	public AuthResponse register( RegisterRequest request) {
		
		Usuario usuario=Usuario.builder()
				 .username(request.getUsername())
		            .password(passwordEncoder.encode( request.getPassword()))
		            .firstname(request.getFirstname())
		            .lastname(request.lastname)
		            .country(request.getCountry())
		            .role(Role.USER)
				.build();
			
		userRepository.save(usuario);
		
			return AuthResponse.builder()
					.token(jwtService.getToken(usuario))
					.build();
		}

}
