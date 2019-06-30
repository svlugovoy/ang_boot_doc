package com.svlugovoy.contactbe.service;

import com.svlugovoy.contactbe.model.Contact;
import com.svlugovoy.contactbe.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;

    public List<Contact> getAllContacts(){
        return contactRepository.findAllIdDesc();
    }

    public Optional<Contact> getContactById(Long id){
        return contactRepository.findById(id);
    }

    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    public void deleteContactById(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact with specified ID not found in database"));
        contactRepository.delete(contact);
    }

    public Contact updateContact(Contact contact) {
        Contact contactInDb = contactRepository.findById(contact.getId())
                .orElseThrow(() -> new RuntimeException("Contact with specified ID not found in database"));
        return contactRepository.save(contact);
    }

    public List<Contact> findByCity(String city) {
        return contactRepository.findByCity(city);
    }

    public List<Contact> findByCountry(String country) {
        return contactRepository.queryContactsFromCountry(country);
    }
}
