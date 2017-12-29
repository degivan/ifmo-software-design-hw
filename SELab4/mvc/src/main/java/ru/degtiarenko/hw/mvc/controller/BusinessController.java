package ru.degtiarenko.hw.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.degtiarenko.hw.mvc.dao.BusinessDao;
import ru.degtiarenko.hw.mvc.model.Business;
import ru.degtiarenko.hw.mvc.model.BusinessList;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BusinessController {
    @Autowired
    private BusinessDao businessDao;

    @RequestMapping(value = "/business", method = RequestMethod.POST, params = "add")
    public String addBusiness(@ModelAttribute("business") Business business) {
        businessDao.addBusiness(business.getListName(), business);
        return "redirect:/get-lists";
    }

    @RequestMapping(value = "/get-lists", method = RequestMethod.GET)
    public String getLists(ModelMap map) {
        prepareModelMap(map, businessDao.getLists());
        return "index";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST, params = "add")
    public String addList(@ModelAttribute("name") String name) {
        businessDao.addList(new BusinessList(new ArrayList<>(), name));
        return "redirect:/get-lists";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST, params = "delete")
    public String dropList(@RequestParam("name") String name) {
        businessDao.dropList(name);
        return "redirect:/get-lists";
    }

    @RequestMapping(value = "business", method = RequestMethod.POST, params = "delete")
    public String finishBusiness(@ModelAttribute("business") Business  business) {
        businessDao.finishBusiness(business.getListName(), business.getDescription());
        return "redirect:/get-lists";
    }

    private void prepareModelMap(ModelMap map, List<BusinessList> lists) {
        map.addAttribute("lists", lists);
        map.addAttribute("business", new Business());
        map.addAttribute("name", "");
    }
}
