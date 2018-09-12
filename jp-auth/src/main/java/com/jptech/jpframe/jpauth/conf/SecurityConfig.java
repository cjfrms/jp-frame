package com.jptech.jpframe.jpauth.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        /*
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        BCryptPasswordEncoder passwordEncode = new BCryptPasswordEncoder();
        String pwd = passwordEncode.encode("123456");
        manager.createUser(User.withUsername("user_1").password(pwd).authorities("USER").build());
        manager.createUser(User.withUsername("user_2").password(pwd).authorities("USER").build());
        return manager;
        */

        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
                // 通过用户名获取用户信息
                //Account account = accountRepository.findByName(name);
                User user = new User("test", "pwd",
                        AuthorityUtils.createAuthorityList("ROLE_01"));
                return user;

//                if (account != null) {
//                    User user = new User("test", "pwd",
//                            AuthorityUtils.createAuthorityList("ROLE_01"));
//                    return user;
//                } else {
//                    throw new UsernameNotFoundException("用户[" + name + "]不存在");
//                }

            }
        };

    }

}
