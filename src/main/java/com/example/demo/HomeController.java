package com.example.demo;

import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Controller
public class HomeController
{
    @Autowired
    ActorRepository actorRepository;

    @Autowired
    CloudinaryConfig cloudc;

    @RequestMapping("/")
    public String listActors(Model model)
    {
        model.addAttribute("actors", actorRepository.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String newActor(Model model){
        model.addAttribute("actor", new Actor());
        return "form";
    }

    @PostMapping("/add")
    public String processActor(@ModelAttribute Actor actor, @RequestParam("file")MultipartFile file, Model model)
    {
        if (file.isEmpty())
        {
            return "redirect:/add";
        }
        /*
        try  //transformation applied before saving
        {   //image is saved into MAP uploadresult
            //Cloudinary shud auto detect the type
            //cloudinary.uploader().upload("sample.jpg",
            // ObjectUtils.asMap("public_id", "sample_id"));
            Map options = ObjectUtils.asMap("transformation", new Transformation().width(50).height(50).border("2px_solid_black").crop("limit"));
            Map uploadResult =  cloudc.upload(file.getBytes()  , options);
           // Map uploadResult =  cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
            actor.setHeadshot(uploadResult.get("url").toString());


            String filename = uploadResult.get("public_id").toString() + "." + uploadResult.get("format").toString();
            System.out.println("filename = "  + filename);
         //   model.addAttribute("sizedimageurl", cloudc.createUrl(filename,10,70, "crop"));


            actorRepository.save(actor);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return "redirect:/add";
        }
        return "redirect:/";

    }*/




    try
    {   //image is saved into MAP uploadresult
        //Cloudinary shud auto detect the type
        //cloudinary.uploader().upload("sample.jpg",
        // ObjectUtils.asMap("public_id", "sample_id"));
       // Map options = ObjectUtils.asMap("transformation", new Transformation().width(50).height(50).border("2px_solid_black").crop("limit"));
      //  Map uploadResult =  cloudc.upload(file.getBytes()  , options);
         Map uploadResult =  cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
        actor.setHeadshot(uploadResult.get("url").toString());


        String filename = uploadResult.get("public_id").toString() + "." + uploadResult.get("format").toString();
        System.out.println("filename = "  + filename);

         String newURL = cloudc.createUrl(filename,100,50, "crop");
          System.out.println("newurl = "  + newURL);
          
          actor.setNewURL(newURL);
        actorRepository.save(actor);
    }
        catch (IOException e)
    {
        e.printStackTrace();
        return "redirect:/add";
    }
        return "redirect:/";
}
}