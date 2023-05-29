package com.example.SpringREST.repository;

import com.example.SpringREST.model.Items;
import org.springframework.data.repository.CrudRepository;

public interface ItemsRepositoryInterface extends CrudRepository<Items, Integer> {
}
