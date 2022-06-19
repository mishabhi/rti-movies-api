package com.rti.api.repository;

import com.rti.api.model.persistence.MovieEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends StandardJpaRepository<MovieEntity, Long> {
}
