package com.example.SpringREST.service;

import com.example.SpringREST.model.Items;
import com.example.SpringREST.repository.ItemsRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemsService {
    @Autowired
    ItemsRepositoryInterface itr;
    public Optional<Items> get(Integer itemId){
        return itr.findById(itemId);
    }
    public List<Items> getAll(){
        return (List<Items>) itr.findAll();
    }
    public Items update(Items items){
        return itr.save(items);
    }
    public void delete(Items items){
        itr.delete(items);
    }
    public void deleteAll(){
        itr.deleteAll();
    }
}
