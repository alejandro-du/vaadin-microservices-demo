package com.example;

import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAvoidanceRule;
import com.netflix.zuul.context.RequestContext;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Alejandro Duarte.
 */
public class StickySessionRule extends ZoneAvoidanceRule {

    public static final String COOKIE_NAME_SUFFIX = "-" + StickySessionRule.class.getSimpleName();

    @Override
    public Server choose(Object key) {
        Optional<Cookie> cookie = getCookie(key);

        if (cookie.isPresent()) {
            Cookie hash = cookie.get();
            List<Server> servers = getLoadBalancer().getReachableServers();
            Optional<Server> server = servers.stream()
                    .filter(s -> s.isAlive() && s.isReadyToServe())
                    .filter(s -> hash.getValue().equals("" + s.hashCode()))
                    .findFirst();

            if (server.isPresent()) {
                return server.get();
            }
        }

        return useNewServer(key);
    }

    private Server useNewServer(Object key) {
        Server server = super.choose(key);
        HttpServletResponse response = RequestContext.getCurrentContext().getResponse();
        if (response != null) {
            String cookieName = getCookieName(server);
            Cookie newCookie = new Cookie(cookieName, "" + server.hashCode());
            newCookie.setPath("/");
            response.addCookie(newCookie);
        }
        return server;
    }

    private Optional<Cookie> getCookie(Object key) {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        if (request != null) {
            Server server = super.choose(key);
            String cookieName = getCookieName(server);
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                return Arrays.stream(cookies)
                        .filter(c -> c.getName().equals(cookieName))
                        .findFirst();
            }
        }

        return Optional.empty();
    }

    private String getCookieName(Server server) {
        return server.getMetaInfo().getAppName() + COOKIE_NAME_SUFFIX;
    }

}
