package org.example.mypet.Utils.Jwts;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mypet.DTO.Auth.CustomUserDetails;
import org.example.mypet.DTO.Auth.JwtResponse;
import org.example.mypet.DTO.Auth.LoginRequest;
import org.example.mypet.DTO.User.UserDTO;
import org.example.mypet.Service.CustomUserDetailService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailService userDetailsService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

            return authenticationManager.authenticate(authToken);

        } catch (IOException e) {
            log.error("Error reading login request", e);
            throw new AuthenticationException("Cannot read login request", e) {
            };
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException{

        String username = authResult.getName();
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(userDetails.getId());
        userDTO.setUsername(userDetails.getUsername());
        userDTO.setRoles(userDetails.getRoles());

        log.info("Successfully authenticated user: {}", userDTO);

        String accessToken = jwtService.generateAccessToken(userDTO);
        String refreshToken = jwtService.generateRefreshToken(userDTO);

        JwtResponse tokenResponse = new JwtResponse(accessToken, refreshToken);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getOutputStream(), tokenResponse);
    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed){
        throw failed;
    }
}
