package ua.foxminded.javaspring.lenskyi.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {

    boolean existsByName(String name);
}
