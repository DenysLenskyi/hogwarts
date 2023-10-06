package ua.foxminded.javaspring.lenskyi.university.repository;

public interface BaseRepository<T> {

    boolean existsByName(String name);
}
