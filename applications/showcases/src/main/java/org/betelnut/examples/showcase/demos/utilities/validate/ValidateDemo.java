package org.betelnut.examples.showcase.demos.utilities.validate;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.Validate;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

/**
 * 演示用Apache Commons3的Validate，在代码中进行防御性校验.
 */
public class ValidateDemo {

	@Test
	public void asserts() {

		// not null Object
		try {
			String parameter = "abc";

			Validate.notNull(parameter);

			// 检验not null，用默认出错信息.
			Validate.notNull(null);
			failBecauseExceptionWasNotThrown(NullPointerException.class);
		} catch (NullPointerException e) {
			assertThat(e).hasMessage("The validated object is null");
		}

		// notBlank String
		try {
			String parameter = "abc";
			// 可选择将输入参数赋值到新变量
			String result = Validate.notBlank(parameter);
			assertThat(result).isEqualTo("abc");

			// 检验not null，用自定义出错信息.
			Validate.notBlank("", "The name must not be blank");
			failBecauseExceptionWasNotThrown(IllegalArgumentException.class);

		} catch (IllegalArgumentException e) {
			assertThat(e).hasMessage("The name must not be blank");
		}

		// notEmpty Collection
		try {
			List<String> parameter = Lists.newArrayList();
			Validate.notEmpty(parameter);
			failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
		} catch (IllegalArgumentException e) {
			assertThat(e).hasMessage("The validated collection is empty");
		}

		// is true
		try {
			// 出錯信息可格式化參數
			Validate.isTrue(1 == 3, "Message %s", "foo");
			failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
		} catch (IllegalArgumentException e) {
			assertThat(e).hasMessage("Message foo");
		}
	}
}
