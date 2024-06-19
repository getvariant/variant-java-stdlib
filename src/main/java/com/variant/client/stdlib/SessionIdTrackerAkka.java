package com.variant.client.stdlib;

import akka.http.javadsl.model.headers.SetCookie;
import akka.http.scaladsl.model.HttpRequest;
import akka.http.scaladsl.model.HttpResponse;
import akka.http.scaladsl.model.headers.HttpCookiePair$;
import com.variant.client.SessionIdTracker;

import java.util.Optional;

import static scala.jdk.javaapi.CollectionConverters.asJava;

public class SessionIdTrackerAkka implements SessionIdTracker {

  private final String cookieName = "variant-ssnid";
  private Optional<String> sid = Optional.empty();

  public SessionIdTrackerAkka(Object data) {
    HttpRequest req = (HttpRequest) data;
    sid = asJava(req.cookies()).stream()
      .filter(cookie -> cookieName.equals(cookie.name()))
      .findAny()
      .map(pair -> pair.value());
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
    HttpResponse resp = (HttpResponse) data;
    var pair = HttpCookiePair$.MODULE$.apply(cookieName, sid.get());
    return resp.addHeader(
      SetCookie.create(
        pair.toCookie()
          .withHttpOnly(false)
          .withPath("/")
      )
    );
  }
}
