package org.betelnut.examples.showcase.entity;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * 角色.
 */
@Entity
@Table(name = "ss_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role extends IdEntity {

	private String name;

	private String permissions;

	public Role() {
	}

	public Role(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	@Transient
	public List<String> getPermissionList() {
		return ImmutableList.copyOf(StringUtils.split(permissions, ","));
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
