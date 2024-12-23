package org.orusmorlans.libros.jpa;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphQuerydslPredicateExecutor;

import org.orusmorlans.libros.jpa.LibrosEntity;

//https://github.com/Cosium/spring-data-jpa-entity-graph/blob/master/doc/MAIN.md
@NoRepositoryBean
public interface LibrosRepository<E extends LibrosEntity<I>, I> 
	extends JpaRepository<E, I>, QuerydslPredicateExecutor<E>, JpaSpecificationExecutor<E>, QueryByExampleExecutor<E>, EntityGraphQuerydslPredicateExecutor<E> {

	Optional<E> findFirstByOrderByIdAsc();
	Slice<E> findFirst10ByOrderByIdAsc();
	
	public default Map<I, E> findAllAndMapById() {
		return findAll().stream().collect(Collectors.toMap(e -> e.getId(), e -> e));
	}
	
}
