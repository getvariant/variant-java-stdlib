package com.variant.client.stdlib;

import com.variant.share.util.ReflectUtils;
import org.junit.Test;

/**
 * A very basic test.
 * TODO: Write real test that ensures the tracking in cookie.  (Prob. hard)
 */
public class SessionIdTrackerAkkaTest {
  @Test
  public void instantiationTest() throws Exception {
    ReflectUtils.instantiate(SessionIdTrackerAkka.class, null, null);
  }
}
