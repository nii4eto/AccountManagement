package com.westernacher;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/users").setViewName("users");
        registry.addViewController("/createUser").setViewName("createUser");
        registry.addViewController("/editUser").setViewName("editUser");
        registry.addViewController("/books").setViewName("books");
        registry.addViewController("/addBook").setViewName("addBook");
        registry.addViewController("/editBook").setViewName("editBook");
        registry.addViewController("/login").setViewName("login");
    }
}
