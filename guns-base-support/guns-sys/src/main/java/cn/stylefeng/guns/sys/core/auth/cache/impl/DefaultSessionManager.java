package cn.stylefeng.guns.sys.core.auth.cache.impl;

import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.sys.core.auth.cache.SessionManager;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于内存的会话管理
 * <p>
 * ps:您可以自行拓展内存管理哦
 * zhong: cache在内存之中，重启系统后内存会失效
 * @author fengshuonan
 * @date 2019-09-28-14:43
 */
@Component
public class DefaultSessionManager implements SessionManager {

    private Map<String, LoginUser> caches = new ConcurrentHashMap<>();

    /*
    创建session，将登陆用户信息放进cache，LOGIN_USER_TOKEN为键，LoginUser对象为值
     */
    @Override
    public void createSession(String token, LoginUser loginUser) {
        caches.put(SESSION_PREFIX + token, loginUser);
    }

    /*
    通过键值获取内存中的LoginUser对象
     */
    @Override
    public LoginUser getSession(String token) {
        return caches.get(SESSION_PREFIX + token);
    }

    /*
    清除内存对象
     */
    @Override
    public void removeSession(String token) {
        caches.remove(SESSION_PREFIX + token);
    }

    /*
    判断内存是否存在
     */
    @Override
    public boolean haveSession(String token) {
        return caches.containsKey(SESSION_PREFIX + token);
    }
}
