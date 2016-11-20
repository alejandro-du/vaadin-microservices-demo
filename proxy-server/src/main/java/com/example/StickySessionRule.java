package com.example;

import com.netflix.loadbalancer.ClientConfigEnabledRoundRobinRule;
import com.netflix.loadbalancer.Server;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
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
            Optional<Server> serverFound = servers.stream()
                    .filter(s -> hash.getValue().equals("" + s.hashCode()))
                    .findFirst();

            if (serverFound.isPresent()) {
                return serverFound.get();
            }
        }

        return addServer(key);
    }

    private Optional<Cookie> getCookie() {
        Cookie[] cookies = RequestContext.getCurrentContext().getRequest().getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .filter(c -> c.getName().equals(COOKIE_NAME))
                    .findFirst();
        }

        return Optional.empty();
    }

    private Server addServer(Object key) {
        Server server = super.choose(key);
        Cookie newCookie = new Cookie(COOKIE_NAME, "" + server.hashCode());
        newCookie.setPath("/");
        RequestContext.getCurrentContext().getResponse().addCookie(newCookie);
        return server;
    }

}
