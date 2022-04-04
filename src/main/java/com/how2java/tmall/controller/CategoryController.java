package com.how2java.tmall.controller;

import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.util.ImageUtil;
import com.how2java.tmall.util.Page;
import com.how2java.tmall.util.UploadedImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
  
    @RequestMapping("admin_category_list")
    public String list(Model model,Page page){
        List<Category> cs= categoryService.list(page);
        int total = categoryService.total();
        page.setTotal(total);
        model.addAttribute("cs", cs);
        model.addAttribute("page", page);
        return "admin/listCategory";
    }

    @RequestMapping("admin_category_add")
    public String add(Category c, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException {
        System.out.println(c.getId());
        categoryService.add(c);
        System.out.println(c.getId());
        File  imageFolder= new File(session.getServletContext().getRealPath("img/category"));
        System.out.println(imageFolder);
        File file = new File(imageFolder,c.getId()+".jpg");
        if(!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        uploadedImageFile.getImage().transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);

        return "redirect:/admin_category_list";
    }
    
    @RequestMapping("admin_category_delete")
    public String delete(int id, HttpSession httpSession){
        categoryService.delete(id);
        File imageFolder = new File(httpSession.getServletContext().getRealPath("image/category"));
        File file = new File(imageFolder, id+".jpg");
        file.delete();
        return "redirect:/admin_category_list";
    }
    
    /*
    * 当访问admin_category_edit时,跳转到修改分类的jsp页面,并且传入你所选的分类记录信息
    * */
    @RequestMapping(value="/admin_category_edit", method = RequestMethod.GET)
    public String edit(Model model, @RequestParam("id") int id){
        Category category = categoryService.getById(id);
        model.addAttribute("c", category);
        return "admin/editCategory";
    }
    
    /*
    * 修改分类信息的操作, 首先会触发跳转页面editCategory.jsp, 你可以修改分类名称和上传的图片.
    * */
    @RequestMapping("admin_category_update")
    public String update(Category category, HttpSession session, UploadedImageFile imageFile) throws IOException {
        categoryService.update(category);
        MultipartFile multipartFile = imageFile.getImage();
        if(null != multipartFile && !multipartFile.isEmpty()){
            File imageFileFolder = new File(session.getServletContext().getRealPath("image/category"));
            System.out.println(imageFileFolder);
            File file = new File(imageFileFolder, category.getId()+".jpg");
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            multipartFile.transferTo(file);
            BufferedImage bufferedImage = ImageUtil.change2jpg(file);
            ImageIO.write(bufferedImage, "jpg", file);
        }
        return "redirect:/admin_category_list";
    }

}