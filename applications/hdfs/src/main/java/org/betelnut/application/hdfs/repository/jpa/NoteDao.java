package org.betelnut.application.hdfs.repository.jpa;

import org.betelnut.application.hdfs.bean.Note;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NoteDao extends PagingAndSortingRepository<Note, String>, Specification<Note> {
}
