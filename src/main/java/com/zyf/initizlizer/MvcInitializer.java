package com.zyf.initizlizer;

import com.zyf.config.MvcConfiguration;
import com.zyf.config.RootConfiguration;
import com.zyf.config.SecurityConfiguration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
//    @Override
//    public void onStartup(ServletContext servletContext) throws ServletException {
//        servletContext.addFilter("characterEncodingFilter", new CharacterEncodingFilter("UTF-8", true))
//                .addMappingForUrlPatterns(null, false, "/*");
//        super.onStartup(servletContext);
//    }

    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfiguration.class, SecurityConfiguration.class};
    }

    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{MvcConfiguration.class};
    }

    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

//    @Override
//    protected Filter[] getServletFilters() {
//        return new Filter[]{new CharacterEncodingFilter("UTF-8", true)};
//        //使用security时 由于本质为过滤器 要想先通过这个过滤器应该在前面的过滤器中注册
//    }
}
