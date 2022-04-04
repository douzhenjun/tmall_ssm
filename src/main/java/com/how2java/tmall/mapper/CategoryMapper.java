package com.how2java.tmall.mapper;

import com.how2java.tmall.util.Page;
import com.how2java.tmall.pojo.Category;
 
import java.util.List;
 
public interface CategoryMapper {
    public List<Category> list();
    
    public void add(Category category);
    
    public void delete(int id);
    
    public void update(Category category);
    
    public Category getById(int id);
}