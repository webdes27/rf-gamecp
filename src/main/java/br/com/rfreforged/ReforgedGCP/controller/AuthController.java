package br.com.rfreforged.ReforgedGCP.controller;

import br.com.rfreforged.ReforgedGCP.dao.UsuarioDAO;
import br.com.rfreforged.ReforgedGCP.model.ApiResponse;
import br.com.rfreforged.ReforgedGCP.model.usuario.Usuario;
import br.com.rfreforged.ReforgedGCP.security.CustomUserPrincipal;
import br.com.rfreforged.ReforgedGCP.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/criar-conta")
    public ApiResponse registrar(@RequestBody Usuario usuario) {
        return usuarioDAO.criarConta(usuario);
    }

    @PostMapping("/login")
    public Usuario login(@RequestBody Usuario usuario, HttpServletResponse response) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuario.getNome(), usuario.getSenha()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = jwtTokenProvider.generateToken(authentication);
        ResponseCookie cookie = ResponseCookie.from("SID", token)
                .maxAge(!token.isEmpty() ? 604800 : 0)
                .sameSite("Lax")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .build();
        response.addHeader("Set-Cookie", cookie.toString());

        usuario = ((CustomUserPrincipal) authentication.getPrincipal()).getUsuario();
        usuario.setSenha(null);

        return usuario;
    }

    @GetMapping("/logout")
    public ApiResponse logout(HttpServletResponse response) {

        jwtTokenProvider.invalidateToken(response);

        return new ApiResponse(true, "Deslogado com sucesso");
    }

}
