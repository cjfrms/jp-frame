package com.jptech.jpframe.jpauth.conf;

import com.jptech.jpframe.jpauth.entity.SysUser;
import com.jptech.jpframe.jpauth.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private UnauthorizedEntryPoint unauthorizedEntryPoint;

    @Autowired
    private UserMapper userMapper;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        //return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint)
                .and()
                .authorizeRequests().antMatchers(HttpMethod.OPTIONS,"/","/login**", "/oauth/**","/error**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout().permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
        //http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

/*    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(HttpMethod.PUT, "/users/")
                .antMatchers("/users/facebook/**")
                .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/security", "/swagger-ui.html", "/webjars/**");
    }*/

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

/*        auth
                .inMemoryAuthentication()
                .withUser("usr").password(passwordEncoder().encode("pwd")).roles("USER")
                .and().passwordEncoder(passwordEncoder());*/
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public UserDetailsService userDetailsService() {



        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
                SysUser sysUser = userMapper.getUserByLoginId(name);
                if(Objects.isNull(sysUser)){
                    throw new UsernameNotFoundException("用户不存在");
                }
                User user = new User(sysUser.getUsername() , sysUser.getPassword(),
                        AuthorityUtils.createAuthorityList("ROLE_TRUSTED_CLIENT"));
                return user;

            }
        };
    }

}
