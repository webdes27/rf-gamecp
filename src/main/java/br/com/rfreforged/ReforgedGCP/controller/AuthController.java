package br.com.rfreforged.ReforgedGCP.controller;

import br.com.rfreforged.ReforgedGCP.dao.UsuarioDAO;
import br.com.rfreforged.ReforgedGCP.model.ApiResponse;
import br.com.rfreforged.ReforgedGCP.model.Usuario;
import br.com.rfreforged.ReforgedGCP.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioDAO usuarioDAO;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UsuarioDAO usuarioDAO) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usuarioDAO = usuarioDAO;
    }

    @PostMapping("/login")
    public Usuario login(@RequestBody Usuario usuario, HttpServletResponse response) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuario.getNome(), usuario.getSenha()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = jwtTokenProvider.generateToken(authentication);
        Cookie cookie = new Cookie("SID", token);
        cookie.setPath("/");
//        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.addCookie(cookie);

        usuario = usuarioDAO.getUsuario(usuario.getEmail());
        usuario.setSenha(null);

        return usuario;
    }

    @GetMapping("/logout")
    public ApiResponse logout(HttpServletResponse response) {

        jwtTokenProvider.invalidateToken(response);

        return new ApiResponse(true, "Deslogado com sucesso");
    }

}
