package com.borntocodeme.user.repository;

import com.borntocodeme.user.entity.Group;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface GroupRepository extends JpaRepositoryImplementation<Group, Long> {

    List<Group> findAllByNameIn(Set<String> groupNames);

    boolean existsByName(String name);
}
