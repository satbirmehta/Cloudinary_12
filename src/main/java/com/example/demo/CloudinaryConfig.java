package com.example.demo;

import com.cloudinary.Cloudinary;
import com.cloudinary.Singleton;
import com.cloudinary.Transformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Map;

@Component
public class CloudinaryConfig
{
    private Cloudinary cloudinary;

    // constructor uses setting values to populate cloudinary obj
    //using singleton class
    @Autowired
    public CloudinaryConfig(@Value("${cloudinary.apikey}") String key,
                            @Value("${cloudinary.apisecret}") String secret,
                            @Value("${cloudinary.cloudname}") String cloud)
    {
        cloudinary = Singleton.getCloudinary();
        cloudinary.config.cloudName=cloud;
        cloudinary.config.apiSecret=secret;
        cloudinary.config.apiKey=key;
    }

      //uploading image to server
    //return image properties of tranfomred image
    public Map upload(Object file, Map options)
    {
        try
        {

            return cloudinary.uploader().upload(file, options);


        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }


      //URL of transformed inage is reruenrd
     //dimaension r applied each time wen method is called
      public String createUrl(String name, int width, int height, String action)
      {
        return cloudinary.url()
               .transformation(new Transformation().width(width).height(height).border("2px_solid_black").crop(action))
                .imageTag(name);

    }

    public String createSizedUrl(String name, int width, int height, String action)
    {
        return cloudinary.url()
                .transformation(new Transformation().width(width).height(height).border("2px_solid_black").crop(action))
                .imageTag(name);
    }


    public String createColorImageSize(String name, String color,int width, int height, String action)
    {
        return cloudinary.url()
                .transformation(new Transformation()
                        .effect("colorize:30").color(color).chain()
                        .width(width).height(height).border("2px_solid_black").crop(action))
                .imageTag(name);
    }
}