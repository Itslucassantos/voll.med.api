package med.voll.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//para dizer que é uma classe de configuração.
@Configuration
// para indicar pro Spring que vamos personalizar as configurações de
// segurança, para isso que serve essa anotação @EnableWebSecurity.
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    // para devolver o objeto para o Spring e ele ler esse método,
    // pe ele não lê sozinho.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //desabilitar contra ataque csrf pq como estamos usando token,
        // ele já faz isso.
        return http.csrf().disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //authorizeRequests() é o método para configurar como vai ser a autirização das requisições.
                .and().authorizeHttpRequests()
                //configurando para dizer que o login, o usuário não precisa está logado pra ter acesso.
                //por isso que ele tem que se logar, se não, não faria sentido.
                //HttpMethod.POST, pq o login é um post.
                //Está querendo dizer que se chegar uma requisição para o Method.POST
                // e a url for de login é para liberar, pq ela é pública, pode disparar sem ser autenticado.
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                //permitindo para a documentação.
                .antMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                // para qualquer outra requisição, a pessoal precisa está autenticada.
                .anyRequest().authenticated()
                // É importante determinar a ordem dos filtros aplicados,
                // para o Spring usar nosso filtro antes do dele.
                // fiz a injeção de dependência da minha classe onde estão os filtros e logo em seguida passei
                // a classe de filtros do Spring que é a que eu qro que meu filtro vem antes.
                .and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class )
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        //cria um objeto authenticationManager pq o Spring não sabe fazer isso automaticamente,
        // apesar de a classer ser dele.
        return configuration.getAuthenticationManager();
    }

    @Bean
    //PasswordEncoder representa a classe de algoritmo de senhas.
    public PasswordEncoder passwordEncoder(){
        //com isso, ensina ao Spring que é pra usar esse hash de senha.
        return new BCryptPasswordEncoder();
    }

}
