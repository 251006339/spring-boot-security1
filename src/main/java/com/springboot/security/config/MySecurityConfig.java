package com.springboot.security.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author XAIOHU
 * @date 2019/9/23 --11:57
 **/
//继承adapte 适配器 adapter
@EnableWebSecurity //标注一个注解
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    //重写父类的授权规则: supper.configure(http)
    //定制请求的授权规则 request

    //在httpSecurity http 类里设置请求页面认证角色
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //设置授权规则  authorizeRequests请求规则
        http.authorizeRequests()
                .antMatchers("/").permitAll()//所有的去配置
                //匹配 到level1需要角色vip1
                .antMatchers("/level1/**").hasRole("VIP1")  //post请求
                //匹配 到level2需要角色2   匹配到level3需要角色3
                .antMatchers("/level2/**").hasRole("VIP2")
                .antMatchers("/level3/**").hasRole("VIP3");
        // 角色---访问哪个页面--哪个权限
//LoginPageGeneratingWebFilter  注册页面生成web--系统自动生成的页面
        http.formLogin(); //设置登陆参数
        //.usernameParameter("user") //页面上绑定javabean上默认是:name=username
        // .passwordParameter("pass")   //默认是name=password ,修改为user和pass
        // .loginPage("/userLogin");//开启  表单登陆 页面上的name=user 登陆账号
        //1.会请求自动登陆页  点击如果没有权限就会到---/login
        //2.重定向 /login?error 表示登陆失败
        //更多
        //3一旦定制loginpage:那么loginPage的post请求就是登陆;


        //开启注销功能  logoutSuccessUrl---退出成功之后去哪一块地址
        http.logout().logoutSuccessUrl("/");//注销成功来到首页. //--/ 请求 webcome
        //1.访问/logout请求 --表示请求注销 退出 --需要post请求
        //
        //开启记录功能:


        //sec:isAuto  是否有权限  服务端生成cookie -页面 name="remember"设置参数
        http.rememberMe().rememberMeParameter("remember");//默认是rememer-me


    }

    @Override  //--用户设置角色权限--
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //定义用户---认证---角色  --用户---角色
        auth.inMemoryAuthentication()
                .withUser("xaiohu").password("123456").roles("VIP1", "VIP2")
                .and()
                .withUser("xiaohu1").password("12345").roles("VIP2")
                .and()
                .withUser("xiaohu2").password("123").roles("VIP3");


    }
}
