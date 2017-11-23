package com.example;

import com.netflix.loadbalancer.ClientConfigEnabledRoundRobinRule;
import com.netflix.loadbalancer.Server;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Alejandro Duarte.
 */
@Component
public class StickySessionRule extends ClientConfigEnabledRoundRobinRule {

    public static final String COOKIE_NAME = StickySessionRule.class.getSimpleName();

    @Override
    public Server choose(Object key) {
        Optional<Cookie> cookie = getCookie();

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
            Cookie newCookie = new Cookie(COOKIE_NAME, "" + server.hashCode());
            newCookie.setPath("/");
            response.addCookie(newCookie);
        }
        return server;
    }

    private Optional<Cookie> getCookie() {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        if (request != null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                return Arrays.stream(cookies)
                        .filter(c -> c.getName().equals(COOKIE_NAME))
                        .findFirst();
            }
        }

        return Optional.empty();
    }

}
