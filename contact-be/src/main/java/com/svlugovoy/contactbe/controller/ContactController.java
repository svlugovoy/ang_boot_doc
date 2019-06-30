package com.svlugovoy.contactbe.controller;

import com.google.common.base.Joiner;
import com.svlugovoy.contactbe.model.Contact;
import com.svlugovoy.contactbe.repository.ContactRepository;
import com.svlugovoy.contactbe.repository.spec.ContactSpecificationsBuilder;
import com.svlugovoy.contactbe.repository.spec.SearchOperation;
import com.svlugovoy.contactbe.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/v1/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @GetMapping
    public List<Contact> getAll() {
        return contactService.getAllContacts();
    }

    @GetMapping("/{id}")
    public Contact getById(@PathVariable Long id) {
        return contactService.getContactById(id).get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Contact saveNew(@RequestBody Contact contact) {
        return contactService.saveContact(contact);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        contactService.deleteContactById(id);
    }

    @PutMapping("/{id}")
    public Contact update(@PathVariable Long id, @RequestBody Contact contact) {
        if (!Objects.equals(id, contact.getId())) {
            throw new IllegalStateException("Id parameter does not match account body value");
        }
        return contactService.updateContact(contact);
    }

    @Autowired
    private ContactRepository repo;

    @GetMapping("/spec")
    public List<Contact> search(@RequestParam(value = "search") String search) {
        ContactSpecificationsBuilder builder = new ContactSpecificationsBuilder();
        String operationSetExper = Joiner.on("|").join(SearchOperation.SIMPLE_OPERATION_SET);
        Pattern pattern = Pattern.compile("(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(4), matcher.group(3), matcher.group(5));
        }
        Specification<Contact> spec = builder.build();
        return repo.findAll(spec);
    }


}
