package com.software.blogserver.interceptor;

import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.software.blogserver.annotation.LoginRequired;
import com.software.blogserver.entity.Users;
import com.software.blogserver.exception.BizException;
import com.software.blogserver.service.IUsersService;
import com.software.blogserver.utils.JWTUtils;
import com.software.blogserver.utils.RedisUtils;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;
import java.util.Objects;

import static com.software.blogcommon.constant.RedisConst.TOKEN_ALLOW_LIST;
import static com.software.blogcommon.enums.CodeEnum.TOKEN_EXPIRED;
import static com.software.blogserver.utils.JWTUtils.getTokenInfo;

@Component
public class JWTInterceptor implements HandlerInterceptor {

    @Autowired
    private IUsersService iUsersServiceauto;
    @Autowired
    private RedisUtils redisUtilauto;

    private static IUsersService iUsersService;
    private static RedisUtils redisUtil;
    @PostConstruct
    public void init() {
        iUsersService = this.iUsersServiceauto;
        redisUtil = this.redisUtilauto;//将注入的对象交给静态对象管理
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        // 判断接口是否需要登录
        LoginRequired loginRequired = method.getAnnotation(LoginRequired.class);
        // 有 @LoginRequired 注解，需要认证
        if (loginRequired != null) {
            String token = request.getHeader("Authorization");
            System.out.println("token = "+token);
            if (token == null) {
                throw new BizException("无token，请重新登录");
            }
            String userId;
            try {
                DecodedJWT verify = getTokenInfo(token);
                userId = verify.getClaim("id").asString();
            } catch (JWTDecodeException e) {
                throw new BizException("token无效，请重新登录");
            }
            if (Objects.isNull(redisUtil.get(TOKEN_ALLOW_LIST + userId)))  {    // token已经失效
                throw new BizException(TOKEN_EXPIRED);
            }
            Users user = iUsersService.getById(Long.parseLong(userId));
            if (user == null) {
                throw new BizException("用户不存在，请重新登录");
            }
            // 验证 token
            try {
                JWTUtils.verify(token);
            } catch (JWTVerificationException e) {
                throw new BizException("token无效，请重新登录");
            }
            request.setAttribute("currentUser", user);
            return true;
        }
        return true;
    }
}
