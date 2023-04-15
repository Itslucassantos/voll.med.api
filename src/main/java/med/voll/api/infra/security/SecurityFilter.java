package med.voll.api.infra.security;

import lombok.var;
import med.voll.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// O @Component é utilizado para que o Spring carregue uma classe/ componente genérico.
@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var tokenJWT = recuperarToken(request);
        //validando o token e mostrando o subject, se tiver o cabeçalho. pq agr o Spring que vê
        //quem está logado ou nn, como configurado no securityConfigurations.
        if(tokenJWT != null){
            var subject = tokenService.getSubject(tokenJWT);
            //considere que essa pessoa está autenticada, eu garanto isso. Feito uma autenticação
            //forçada para o Spring entender.
            //Como precisa acessar o BD, para checar o usuário, então precisa ser feita a injeção de dependência.
            //passa o subject, pq é onde está guardado o login da pessoa.
            // como é stateless, então foi preciso forçar pra dizer que está logado.
            var usuario = repository.findByLogin(subject);
            //para considerar que o usuário está logado, precisa usar uma classe própria do String que é a
            //SecurityContextHolder.
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null,
                    usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        //para continuar o fluxo da requisição, é necessário para chamar os próximos filtros da aplicação.
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        //recupera o token
        var authorizationHeader = request.getHeader("Authorization");
        // agr quem checa se o usuário está logado ou nn é o Spring. Pois foi configurado
        // no SecurityConfigurations/SecurityFilterChain quando usei o authorizeRequests().
        if(authorizationHeader != null){
            //.replace("Bearer", ""), está substituindo a palavra Bearer por nada. Assim será imprimido somente o token.
            return authorizationHeader.replace("Bearer", "");
        }
        // se nn tiver vindo o cabeçalho, ele nn substitui nada, ele devolve null.
        return null;
    }


}
