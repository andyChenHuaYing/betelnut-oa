package org.betelnut.examples.showcase.repository.mybatis;

import org.betelnut.examples.showcase.entity.Team;

/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 */
@MyBatisRepository
public interface TeamMybatisDao {

	Team getWithDetail(Long id);
}
