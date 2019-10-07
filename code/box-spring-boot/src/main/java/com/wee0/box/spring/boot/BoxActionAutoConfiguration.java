/*
 * Copyright (c) 2019-present, wee0.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wee0.box.spring.boot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wee0.box.BoxConstants;
import com.wee0.box.util.impl.JacksonJsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 22:29
 * @Description MVC自动配置对象
 * <pre>
 * 补充说明，也可以选择继承WebMvcConfigurationSupport来配置。
 * </pre>
 **/
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@Configuration
@AutoConfigureAfter(BoxDsAutoConfiguration.class)
class BoxActionAutoConfiguration implements WebMvcConfigurer {

    @Value("${box.auth.ignoreUrls:#{null}}")
    private String authIgnoreUrls;

    @Value("${box.auth.cookieDomain:#{null}}")
    private String authCookieDomain;

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(false);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        ObjectMapper _objectMapper = JacksonJsonUtils.me().getObjectMapper();
        MappingJackson2HttpMessageConverter _jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter(_objectMapper);
        _jackson2HttpMessageConverter.setDefaultCharset(BoxConstants.UTF8_CHARSET);
        converters.add(0, _jackson2HttpMessageConverter);
    }

//    @Override
//    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
//        resolvers.add(0, new BoxActionHandlerExceptionResolver());
//    }

//    @Override
//    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//        // 开启扩展名至mimeType的映射 /path.json => application/json
//        configurer.favorPathExtension(true);
//        Map<String, MediaType> _mediaTypes = new HashMap<>(3);
//        _mediaTypes.put("html", MediaType.TEXT_HTML);
//        _mediaTypes.put("json", MediaType.APPLICATION_JSON);
//        _mediaTypes.put("xml", MediaType.APPLICATION_XML);
//        configurer.mediaTypes(_mediaTypes);
//        // 开启 /path/parameter?format=json 的支持
//        configurer.favorParameter(true);
//        configurer.parameterName("format");
//        // 是否忽略Accept Header
//        configurer.ignoreAcceptHeader(false);
//        configurer.defaultContentType(MediaType.APPLICATION_JSON);
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");

        ResourceHandlerRegistration _resourceHandlerRegistration = registry.addResourceHandler("/");
        _resourceHandlerRegistration.addResourceLocations("classpath:/static/");
//        // swagger静态资源
//        _resourceHandlerRegistration.addResourceLocations("classpath:/META-INF/resources/");
//        // 0表示不缓存
//        _resourceHandlerRegistration.setCachePeriod(0);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("OPTIONS", "GET", "POST", "PATCH")
                .allowedHeaders("Content-Type, Accept, Authorization")
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new BoxActionHandlerInterceptor(authIgnoreUrls, authCookieDomain));
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("/static/index.html");
    }

    @Bean
    BoxActionRegistrationsAdapter boxActionRegistrationsAdapter() {
        return new BoxActionRegistrationsAdapter();
    }

    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    BoxActionAfterConfiguration boxActionAfterConfiguration() {
        return new BoxActionAfterConfiguration();
    }

//    @Bean
//    BoxActionExceptionHandler boxActionExceptionController() {
//        return new BoxActionExceptionHandler();
//    }


//    @Override
//    protected RequestMappingHandlerAdapter createRequestMappingHandlerAdapter() {
////        return new BoxActionRequestMappingHandlerAdapter();
//        List<HttpMessageConverter<?>> _messageConverters = new ArrayList<>();
//        _messageConverters.add(new ByteArrayHttpMessageConverter());
//        StringHttpMessageConverter _stringHttpMessageConverter = new StringHttpMessageConverter(BoxConstants.UTF8_CHARSET);
//        _stringHttpMessageConverter.setWriteAcceptCharset(false);
//        _messageConverters.add(_stringHttpMessageConverter);
//        _messageConverters.add(new AllEncompassingFormHttpMessageConverter());
//        _adapter.setMessageConverters(_messageConverters);
//
//        List<HandlerMethodArgumentResolver> _argumentResolvers = new ArrayList<>();
//        _argumentResolvers.add(new BoxActionMethodArgumentResolver());
////        _argumentResolvers.add(new RequestParamMapMethodArgumentResolver());
////        _argumentResolvers.add(new RequestResponseBodyMethodProcessor(this.getMessageConverters(), this.requestResponseBodyAdvice));
////        _argumentResolvers.add(new RequestPartMethodArgumentResolver(this.getMessageConverters(), this.requestResponseBodyAdvice));
////        _argumentResolvers.add(new RequestHeaderMethodArgumentResolver(this.getBeanFactory()));
////        _argumentResolvers.add(new ServletCookieValueMethodArgumentResolver(this.getBeanFactory()));
//        _argumentResolvers.add(new ServletRequestMethodArgumentResolver());
//        _argumentResolvers.add(new ServletResponseMethodArgumentResolver());
////        _argumentResolvers.add(new RedirectAttributesMethodArgumentResolver());
////        _argumentResolvers.add(new ModelMethodProcessor());
//        _argumentResolvers.add(new MapMethodProcessor());
////        _argumentResolvers.add(new ErrorsMethodArgumentResolver());
////        _argumentResolvers.add(new SessionStatusMethodArgumentResolver());
////        _argumentResolvers.add(new UriComponentsBuilderMethodArgumentResolver());
//
////        _argumentResolvers.add(new RequestParamMethodArgumentResolver(this.getBeanFactory(), true));
//        _argumentResolvers.add(new ServletModelAttributeMethodProcessor(true));
//        _adapter.setArgumentResolvers(_argumentResolvers);
//
//        List<HandlerMethodReturnValueHandler> _returnValueHandlers = new ArrayList<>();
//        _returnValueHandlers.add(new ViewMethodReturnValueHandler());
//        _returnValueHandlers.add(new HttpHeadersReturnValueHandler());
//        _returnValueHandlers.add(new CallableMethodReturnValueHandler());
//        _returnValueHandlers.add(new DeferredResultMethodReturnValueHandler());
//        _returnValueHandlers.add(new RequestResponseBodyMethodProcessor(_messageConverters, _contentNegotiationManager, this.requestResponseBodyAdvice));
//        _returnValueHandlers.add(new ViewNameMethodReturnValueHandler());
//        _returnValueHandlers.add(new MapMethodProcessor());
//        _adapter.setReturnValueHandlers(_returnValueHandlers);
//        return _adapter;
//    }

}
