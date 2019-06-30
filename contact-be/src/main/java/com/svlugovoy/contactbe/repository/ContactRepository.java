package com.svlugovoy.contactbe.repository;

import com.svlugovoy.contactbe.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long>, JpaSpecificationExecutor<Contact> {

    public List<Contact> findByCity(String city);

    @Query("select c from Contact c order by c.id desc")
    public List<Contact> findAllIdDesc();

    @Query("select c from Contact c where c.country = :country")
    public List<Contact> queryContactsFromCountry(@Param("country") String country);

}
