package org.betelnut.examples.showcase.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.betelnut.examples.showcase.entity.Role;

public interface RoleDao extends PagingAndSortingRepository<Role, Long> {

}
