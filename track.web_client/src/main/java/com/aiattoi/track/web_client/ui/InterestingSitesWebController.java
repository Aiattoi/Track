package com.aiattoi.track.web_client.ui;

import com.aiattoi.track.web_client.model.InterestingSiteDto;
import com.aiattoi.track.web_client.model.InterestingSitesWebModel;
import com.aiattoi.track.web_client.data.InterestingSitesClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/interestingSites")
public class InterestingSitesWebController {
    private final InterestingSitesClient sitesClient;

    public InterestingSitesWebController(InterestingSitesClient sitesClient) {
        this.sitesClient = sitesClient;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("interestingSites", sitesClient.readAll());
        return "interestingSites";
    }

    @GetMapping("/edit")
    public String editSite(@RequestParam Integer id, Model model) {
        model.addAttribute("siteDto", sitesClient.readById(id));
        return "interestingSiteEdit";
    }

    @PostMapping("/edit")
    public String editSiteSubmit(@ModelAttribute InterestingSiteDto siteDto, Model model) {
        model.addAttribute("siteDto", sitesClient.update(siteDto));
        return "interestingSiteEdit";
    }

    @GetMapping("/add")
    public String addSite(Model model) {
        model.addAttribute("siteWebModel", new InterestingSitesWebModel());
        return "interestingSiteAdd";
    }

    @PostMapping("/add")
    public String addSiteSubmit(@ModelAttribute InterestingSitesWebModel siteWebModel, Model model) {
        model.addAttribute("siteWebModel", sitesClient.create(siteWebModel));
        return "interestingSiteAdd";
    }

    @GetMapping("/delete")
    public String deleteSite(@RequestParam Integer id, Model model) {
        model.addAttribute("siteDto", sitesClient.readById(id));
        return "interestingSiteDelete";
    }

    @PostMapping("/delete")
    public String deleteSiteSubmit(@ModelAttribute InterestingSiteDto siteDto, Model model) {
        model.addAttribute("siteDto", sitesClient.deleteById(siteDto.id));
        return "interestingSiteDeleted";
    }

    @GetMapping("/greater")
    public String listGreater(Model model) {
        model.addAttribute("interestingSites", sitesClient.listGreater(1000));
        return "interestingSitesGreater";
    }

    @GetMapping("/selectAdd")
    public String listAdd(@RequestParam Integer trackId, Model model) {
        model.addAttribute("trackId", trackId);
        model.addAttribute("interestingSites", sitesClient.readAll());
        return "interestingSiteSelect";
    }
}
