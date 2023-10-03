package com.alfish.arealmvp.service;

import com.alfish.arealmvp.model.Statue;
import com.alfish.arealmvp.model.User;
import com.alfish.arealmvp.repository.StatueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatueService {

    private final StatueRepository repository;

    @Autowired
    public StatueService(StatueRepository repository) {
        this.repository = repository;
    }

    public List<Statue> getStatues() {
        return repository.findAll();
    }

    public Statue getStatueByName(String statueName) {
        return repository.findByStatueName(statueName);
    }

    public Statue storeStatue(Statue statue) {
        return repository.save(statue);
    }

    public void addInteraction(Statue statue) {
        if (statue != null) {
            statue.setInteractions(statue.getInteractions() + 1);
            storeStatue(statue);
        }
    }

    public void addView(Statue statue) {
        if (statue != null) {
            statue.setViews(statue.getViews() + 1);
            storeStatue(statue);
        }
    }

    public void addWrite(Statue statue) {
        if (statue != null) {
            statue.setWrites(statue.getWrites() + 1);
            storeStatue(statue);
        }
    }

}
