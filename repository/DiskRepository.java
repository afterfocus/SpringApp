package com.afterfocus.springapp.repository;

import com.afterfocus.springapp.model.Disk;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface DiskRepository extends CrudRepository<Disk, Integer>{

    @Modifying
    @Transactional
    @Query("UPDATE Disk SET person_id = :personId WHERE disk_id = :diskId")
    void issueDisk(@Param("personId") int personId, @Param("diskId") int diskId);

    @Modifying
    @Transactional
    @Query("UPDATE Disk SET person_id = NULL WHERE disk_id = :diskId")
    void returnDisk(@Param("diskId") int diskId);

    int countDisksByPerson_PersonID(int personId);

    Iterable<Disk> findAllByPerson_PersonID(int personId);
}
