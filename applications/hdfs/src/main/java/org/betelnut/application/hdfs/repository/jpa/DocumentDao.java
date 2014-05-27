package org.betelnut.application.hdfs.repository.jpa;

import org.betelnut.application.hdfs.bean.Document;
import org.betelnut.application.hdfs.bean.Note;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author James
 */
public interface DocumentDao extends PagingAndSortingRepository<Document, String>, JpaSpecificationExecutor<Document> {

    /**
     * 根据document的id来查找此文档下的所有note数据
     *
     * @param id document id
     * @return 此document下的所有note数据
     */
    @Query("select note from Note note where note.document.id=?1")
    List<Note> findNotesById(String id);
}
