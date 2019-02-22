package com.srvblues;

import log4jwebtracker.logging.LoggingUtils;
import org.apache.log4j.Appender;
import org.apache.log4j.Category;
import org.apache.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.LoggerContextFactory;
import org.json.JSONObject;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class MainController {

    protected Logger logger = LogManager.getLogger(getClass());


    @RequestMapping(value="/")
    public String index(Model model) throws Exception{
        model.addAttribute("test", "123");

        logger.debug("test message....");

        if(true) {
            throw new Exception("aaaaa");
        }

        return "index2";
    }


    @RequestMapping(value="/json.do", method= RequestMethod.POST)
    @ResponseBody
    public String json(@RequestBody JSONObject obj,  HttpServletRequest req){

        System.out.println(obj);

        //System.out.println("Recev Data :" + obj.getString("key"));
        return "ServerString....";
    }





    public static List getAllCategories() {
        org.apache.log4j.Logger root = org.apache.log4j.Logger.getRootLogger();
        List results = new LinkedList();
        Enumeration currentCategories = root.getLoggerRepository().getCurrentCategories();

        while(currentCategories.hasMoreElements()) {
            Category category = (Category) currentCategories.nextElement();

            System.out.println("category.getName() ::" + category.getName());

            results.add(category);
        }
        /*
        Collections.sort(results,
                new Comparator(){
                    public int compare(Category o1, Category o2) {
                        if (o1 == null || o2 == null) return 0;
                        if (o1.getName() == null || o2.getName() == null) return 0;
                        return o1.getName().compareTo(o2.getName());
                    }
                }
        );
        */
        results.add(0, root);
        return results;
    }

    public static void setLogLeve(String categoryName, Level level) {
        Category category = findCategory(categoryName);
        if (category == null) {
            System.out.println("[Log4JUtil] Can not find category: " + categoryName); return;
        }
        category.setLevel(level);
    }

    private static Category findCategory(String categoryName) {
        if (categoryName == null) return null;
        org.apache.log4j.Logger root = org.apache.log4j.Logger.getRootLogger();
        if ("root".equals(categoryName)) return root;
        Enumeration categories = root.getLoggerRepository().getCurrentCategories();
        while(categories.hasMoreElements()) {
            Category category = (Category) categories.nextElement();
            if (categoryName.equals(category.getName())) return category;
        } return null;
    }
}
