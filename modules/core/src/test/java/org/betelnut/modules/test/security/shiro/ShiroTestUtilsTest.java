package org.betelnut.modules.test.security.shiro;

import org.apache.shiro.SecurityUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ShiroTestUtilsTest {

	@Test
	public void mockSubject() {
		ShiroTestUtils.mockSubject("foo");
		assertThat(SecurityUtils.getSubject().isAuthenticated()).isTrue();
		assertThat(SecurityUtils.getSubject().getPrincipal()).isEqualTo("foo");

		ShiroTestUtils.clearSubject();

		ShiroTestUtils.mockSubject("bar");
		assertThat(SecurityUtils.getSubject().isAuthenticated()).isTrue();
		assertThat(SecurityUtils.getSubject().getPrincipal()).isEqualTo("bar");

	}

}
