package com.example.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    public Iterable<Company> list() {
        return companyRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void add(@RequestParam String name) {
        companyRepository.save(new Company(name));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void update(@RequestParam Long id, @RequestParam String newName) {
        Company existingCompany = companyRepository.findOne(id);
        existingCompany.setName(newName);
        companyRepository.save(existingCompany);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(@RequestParam Long id) {
        companyRepository.delete(id);
    }

}
