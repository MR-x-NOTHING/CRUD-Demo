package com.example.CRUD_Demo.repository;

import com.example.CRUD_Demo.entity.Student;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<Student,Integer> {
    Optional<Student> findByIdAndDeletedIsFalse(Integer id);

    List <Student> findByDeletedIsFalse();

//    long id(Long id);
}
