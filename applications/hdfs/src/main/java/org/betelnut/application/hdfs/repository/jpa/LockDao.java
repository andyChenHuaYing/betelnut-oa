package org.betelnut.application.hdfs.repository.jpa;

import org.betelnut.application.hdfs.bean.Document;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author James
 */
public interface LockDao extends PagingAndSortingRepository<Document, String>, JpaSpecificationExecutor<Document> {

}
