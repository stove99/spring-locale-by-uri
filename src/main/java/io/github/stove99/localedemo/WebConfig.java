
package io.github.stove99.localedemo;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 모든 url 에 대해 인터셉터 설정
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PathLocaleInterceptor()).addPathPatterns("/**");
    }

    /**
     * 디폴트 로케일 리졸버 등록 등록안하면 Cannot change HTTP accept header - use a different
     * locale resolution strategy 어쩌고 오류난다.
     * 
     * @return
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.KOREA);

        return slr;
    }
}

class PathLocaleInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {

        String uri = req.getRequestURI();

        // 서버에 올렸을때 영문 메시지를 쪽바로 못가져 오는 경우가 있는데 고럴때는
        // Locale.ENGLISH 를 Locale.US 로 바꾸고 메시지 번들 파일명을 messages_en_US.properties 로 바꿔보셈
        // /eng 로 시작하는 url 일 경우 로케일을 en으로 바꿈
        Locale locale = uri.startsWith("/eng") ? Locale.ENGLISH : Locale.KOREA;
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(req);
        localeResolver.setLocale(req, res, locale);

        return true;
    }
}