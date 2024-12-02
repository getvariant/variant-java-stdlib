package com.variant.client.stdlib;

import com.variant.client.SessionIdTracker;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.Optional;

public class SessionIdTrackerServlet5 implements SessionIdTracker {
	private Optional<String> sid = Optional.empty();
	public SessionIdTrackerServlet5(Object data) {
		final HttpServletRequest req = (HttpServletRequest) data;
		// Servlet API returns null instead of an empty array if no cookies
		final Cookie[] cookies = Optional.ofNullable(req.getCookies()).orElse(new Cookie[0]);
		sid = Arrays.stream(cookies)
			.filter(cookie -> getCookieName().equals(cookie.getName()))
			.findAny()
			.map(Cookie::getValue);
	}
	@Override
	public Optional<String> get() {
		return sid;
	}
	@Override
	public void set(String sid) {
		this.sid = Optional.of(sid);
	}
	@Override
	public Object save(Object data) {
		HttpServletResponse resp = (HttpServletResponse) data;
		Cookie cookie = new Cookie(getCookieName(), sid.get());
		cookie.setHttpOnly(false);
		cookie.setPath("/");
		resp.addCookie(cookie);
		return resp;
	}
}