//package handalab.eggtec.config;
//
//import jakarta.servlet.MultipartConfigElement;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.DispatcherServlet;
//
//@Configuration
//public class WebMvcConfig {
//
//    @Bean
//    public ServletRegistrationBean<DispatcherServlet> dispatcherRegistration(DispatcherServlet dispatcherServlet) {
//        ServletRegistrationBean<DispatcherServlet> registration = new ServletRegistrationBean<>(dispatcherServlet);
//        registration.setLoadOnStartup(1);
//
//        // 파일 업로드 설정
//        MultipartConfigElement multipartConfigElement =
//                new MultipartConfigElement("c:\\upload", 20971520, 41943040, 20971520);
//        registration.setMultipartConfig(multipartConfigElement);
//
//        // 404 오류 발생 시 Exception 던지도록 설정
//        registration.addInitParameter("throwExceptionIfNoHandlerFound", "true");
//
//        return registration;
//
//    }
//
////    @Ovveride
////    proected void customizeRegistration(Dynamic registration){
////        MultipartConfigElement multipartConfigElement
////                = new MultipartConfigElement("c:\\upload", 1,2,3);
////        registration.setMulipartConfig(multipartConfigElement);
////        registration.setInitParameter("throwExceptionIfNoHandlerFound","true");
////    }
//}
