package com.zyf.config;

import com.zyf.entity.AuthUser;
import com.zyf.mapper.UserMapper;
import com.zyf.service.impl.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    UserAuthService service;
    @Autowired
    UserMapper mapper;

    @Autowired
    PersistentTokenRepository repository;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        BCryptPasswordEncoder pe = new BCryptPasswordEncoder();//直接登录的方式
//        auth
//                .inMemoryAuthentication()
//                .passwordEncoder(pe)//密码加密器
//                .withUser("admin")
//                .password(pe.encode("123456"))
//                .roles("user");
        auth
                .userDetailsService(service)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()   //首先需要配置哪些请求会被拦截，哪些请求必须具有什么角色才能访问
                .antMatchers("/static/**","/page/auth/**","/api/auth/**").permitAll()    //静态资源，使用permitAll来运行任何人访问（注意一定要放在前面）
//                .antMatchers("/**").hasRole("user")//所有请求必须登陆并且是user角色才可以访问（不包含上面的静态资源）
                .antMatchers("page/user/**","api/user/**").hasRole("user")
                .antMatchers("page/amin/**","api/admin/**").hasRole("amin")
                .anyRequest().hasAnyRole("user","admin")
                .and()
                .formLogin()//配置Form表单登陆
                .loginPage("/page/auth/login")//登陆页面地址（GET）
                .loginProcessingUrl("/api/auth/login") //form表单提交地址（POST）
//                .defaultSuccessUrl("/index",true) //登陆成功后跳转的页面，也可以通过Handler实现高度自定义
                .successHandler(this::onAuthenticationSuccess)
                .and()
                .logout()
                .logoutUrl("/api/auth/logout")    //退出登陆的请求地址
                .logoutSuccessUrl("/login")    //退出后重定向的地址
                .and()
                .csrf().disable()//关闭csrf
                .rememberMe()
                .rememberMeParameter("remember")
                .tokenRepository(repository)
                .tokenValiditySeconds(60 * 60 * 24 * 7);//Token的有效时间（秒）默认为14天;

    }

    @Bean
    public PersistentTokenRepository jdbcRepository(@Autowired DataSource dataSource){
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();  //使用基于JDBC的实现
        repository.setDataSource(dataSource);   //配置数据源
//        repository.setCreateTableOnStartup(true);   //启动时自动创建用于存储Token的表（建议第一次启动之后删除该行）
        return repository;
    }


    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        HttpSession session=httpServletRequest.getSession();
        AuthUser user = mapper.getPasswordByUsername(authentication.getName());
        session.setAttribute("user",user);
        if(user.getRole().equals("user")){
            httpServletResponse.sendRedirect("/bookManger/page/user/index");
        }else if(user.getRole().equals("admin")){
            httpServletResponse.sendRedirect("/bookManger/page/admin/index");
        }


    }

}
