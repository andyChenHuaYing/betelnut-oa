package org.betelnut.modules.test.spring;

/**
 * Spring profile 常用方法与profile名称。
 *
 * 一般情况下在Spring的配置文件中会指定相应的Profile类型，
 * 分为:
 * production
 * development
 * test
 * functional
 * 在不同的环境下可以使用不同的数据库连接池或者其它的配置环境
 * 这样在system发布之后会有不同的效果
 */
public class Profiles {

	public static final String ACTIVE_PROFILE = "spring.profiles.active";
	public static final String DEFAULT_PROFILE = "spring.profiles.default";

	public static final String PRODUCTION = "production";
	public static final String DEVELOPMENT = "development";
	public static final String UNIT_TEST = "test";
	public static final String FUNCTIONAL_TEST = "functional";

	/**
	 * 在Spring启动前，设置profile的环境变量。
     * 这里我们使用test的配置环境
	 */
	public static void setProfileAsSystemProperty(String profile) {
		System.setProperty(ACTIVE_PROFILE, profile);
	}
}
