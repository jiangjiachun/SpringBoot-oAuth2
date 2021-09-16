package com.example.web;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterClientController {

  public AuthenticationManager authenticationManagerBean;

  @GetMapping("register")
  public String register() {
    return "1222";
  }

  /*
   * @GetMapping("tLogin") public int login(String username, String password) {
   * UsernamePasswordAuthenticationToken token = new
   * UsernamePasswordAuthenticationToken(username, password); User details = new
   * User(username, password, null); token.setDetails(details);
   * 
   * try { Authentication auth = authenticationManagerBean.authenticate(token);
   * SecurityContextHolder.getContext().setAuthentication(auth); return 1; } catch
   * (BadCredentialsException e) { return 0; } }
   */
}
