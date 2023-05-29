package com.example.SpringREST.controller;

import com.example.SpringREST.model.Items;
import com.example.SpringREST.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@ResponseBody
@RequestMapping("/api")
public class ItemsController {
    @Autowired
    ItemsService its;
    //get all data
    @GetMapping(value = {"/items"})
    @Cacheable(value = "allitems", unless = "#result.size()==0")
    public List<Items> getAll(){
        List<Items> items = its.getAll();
        System.out.println("Get ALl method");
        return items;
    }
    //get item according id
    @GetMapping("/items/{id}")
    @Cacheable(value = "items", key = "#id" , condition = "#id > 1", unless = "#result.itemPrice>140")
    public Items get(@PathVariable("id") Integer id){
        Optional<Items> items = its.get(id);
        System.out.println("Get One item method");
        return items.get();
    }
    //insert item to the list
    @PostMapping("/items")
    @Caching(
            put = {@CachePut(value = "items", key = "#result.itemId")},
            evict = {@CacheEvict(value = "allitems",allEntries = true)}
    )
    public Items add(@RequestBody Items items){
        System.out.println("Insert item method");
        return its.update(items);
    }
    //update an item in database
    @PutMapping("/items")
    @Caching(
            put = {@CachePut(value = "items", key = "#result.itemId")},
            evict = {@CacheEvict(value = "allitems",allEntries = true)}
    )
    public Items edit(@RequestBody Items items){
        System.out.println("Edit an Item method");
        return its.update(items);
    }
    //delete item
    @DeleteMapping("/items")
    @Caching(
            evict = {
                    @CacheEvict(value = "items", key = "#id"),
                    @CacheEvict(value = "allitems", allEntries = true)
            }
    )
    public String delete(@RequestParam(value = "id") Integer id){
        Optional<Items> items = its.get(id);
        its.delete(items.get());
        System.out.println("Delete an item method");
        return "Item Id= " + id + " Deleted Successfully!";
    }
    @DeleteMapping("/deleteAll")
    @Caching(
            evict = {
                    @CacheEvict(value = "items", allEntries = true),
                    @CacheEvict(value = "allitems", allEntries = true)
            }
    )
    public String deleteAll(){
        its.deleteAll();
        System.out.println("deleting all items");
        return "All items deleted successfully";
    }
    @GetMapping("/clearCache")
    @Caching(
            evict = {
                    @CacheEvict(value = "items", allEntries = true),
                    @CacheEvict(value = "allitems", allEntries = true)
            }
    )
    public String clearCache(){
        return "Cleared Cache Successfully!";
    }
}
