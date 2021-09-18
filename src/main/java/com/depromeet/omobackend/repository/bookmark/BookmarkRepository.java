package com.depromeet.omobackend.repository.bookmark;

import com.depromeet.omobackend.domain.bookmark.Bookmark;
import com.depromeet.omobackend.domain.bookmark.BookmarkId;
import org.springframework.data.repository.CrudRepository;

public interface BookmarkRepository extends CrudRepository<Bookmark, BookmarkId> {
}
