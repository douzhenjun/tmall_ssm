package com.how2java.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
public class ProductController {
    
    @Autowired
    CategoryService categoryService;
    
    @Autowired
    ProductService productService;
    
    @RequestMapping("admin_product_add")
    public String add(Model model, Product p){
        p.setCreateDate(new Date());
        productService.add(p);
        return "redirect:admin_product_list?cid="+p.getCid();
    }
    
    @RequestMapping("admin_product_list")
    public String list(Model model, int cid, Page p){
        Category category = categoryService.getById(cid);
        PageHelper.offsetPage(p.getStart(), p.getCount());
        List<Product> productList = productService.list(cid);
        int total = (int) new PageInfo<>(productList).getTotal();
        p.setTotal(total);
        model.addAttribute("ps", productList);
        model.addAttribute("c", category);
        model.addAttribute("page", p);
        return "admin/listProduct";
    }
    
    @RequestMapping("admin_product_delete")
    public String delete(Integer id){
        Product p = productService.get(id);
        productService.delete(id);
        return "redirect:admin_product_list?cid="+p.getCid();
    }
    
    //在编辑页面,点击提交按钮以后触发 
    @RequestMapping("admin_product_update")
    public String update(Product p){
        productService.update(p);
        return "redirect:admin_product_list?cid="+p.getCid();
    }
    
    //点击编辑按钮以后触发, 转发product对象到editProduct.jsp
    @RequestMapping("admin_product_edit")
    public String edit(Model model, int id){
        Product p = productService.get(id);
        Category c = categoryService.getById(p.getCid());
        p.setCategory(c);
        model.addAttribute("p", p);
        return "admin/editProduct";
    }
    
    
    
    
}
