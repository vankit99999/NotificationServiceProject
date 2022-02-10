package com.meesho.notificationservice.authentication;

import com.meesho.notificationservice.models.ControllerSuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(value = "v1/authenticate", method = RequestMethod.POST)
    public ResponseEntity<ControllerSuccessResponse> createAuthenticationToken(@RequestBody AuthenticationRequest
                                                                                 authenticationRequest)
            throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()));
        }catch (BadCredentialsException b) {
            throw new Exception("Incorrect username or password", b);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new ControllerSuccessResponse(new AuthenticationResponse(jwt),"success"));
    }
}
