package org.betelnut.modules.beanvalidator;

import org.betelnut.modules.test.spring.SpringContextTestCase;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;


/**
 * 采用JSR的标准Validator,故这里在pom.xml里面配置了相应的测试要使用的
 *         <dependency>
             <groupId>org.jboss.spec.javax.el</groupId>
             <artifactId>jboss-el-api_3.0_spec</artifactId>
             <version>1.0.0.Final</version>
             <scope>test</scope>
           </dependency>

 如果没有这个依赖项的话，会摄氏，当然这里也只有一个测试类会使用到jboss-el这个jar包，
 其余的测试类不会使用到这个jar包。故其他项目里不需要申明这个依赖，并没有把这个依赖项
 放在parent项目里。
 *
 *
 */
@ContextConfiguration(locations = { "/applicationContext-core-test.xml" })
public class BeanValidatorsTest extends SpringContextTestCase {

	@Autowired
	private Validator validator;

	@BeforeClass
	public static void beforeClass() {
		// To avoid the non-English environment test failure on message asserts.
		Locale.setDefault(Locale.ENGLISH);
	}

	@Test
	public void validate() {

		Customer customer = new Customer();
		customer.setEmail("aaa");

		Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
		assertThat(violations).hasSize(2);

		// extract message as list
		List<String> result = BeanValidators.extractMessage(violations);
		assertThat(result).containsOnly("not a well-formed email address", "may not be empty");

		// extract propertyPath and message as map;
		Map mapResult = BeanValidators.extractPropertyAndMessage(violations);
		assertThat(mapResult).containsOnly(entry("email", "not a well-formed email address"),
				entry("name", "may not be empty"));

		// extract propertyPath and message as map;
		result = BeanValidators.extractPropertyAndMessageAsList(violations);
		assertThat(result).containsOnly("email not a well-formed email address", "name may not be empty");
	}

	@Test
	public void validateWithException() {
		Customer customer = new Customer();
		customer.setEmail("aaa");

		try {
			BeanValidators.validateWithException(validator, customer);
			failBecauseExceptionWasNotThrown(ConstraintViolationException.class);
		} catch (ConstraintViolationException e) {
			Map mapResult = BeanValidators.extractPropertyAndMessage(e);
			assertThat(mapResult).contains(entry("email", "not a well-formed email address"),
					entry("name", "may not be empty"));
		}
	}

	private static class Customer {

		private String name;

		private String email;

		@NotBlank
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Email
		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

	}
}
