package com.aiattoi.track.web_client.ui;

import com.aiattoi.track.web_client.data.TrackClient;
import com.aiattoi.track.web_client.model.TrackDto;
import com.aiattoi.track.web_client.model.TrackWebModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tracks")
public class TrackWebController {
    private final TrackClient trackClient;

    public TrackWebController(TrackClient trackClient) {
        this.trackClient = trackClient;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("tracks", trackClient.readAll());
        return "tracks";
    }

    @GetMapping("/manager")
    public String getManager(@RequestParam Integer id, Model model) {
        model.addAttribute("trackId", id);
        model.addAttribute("managerDto", trackClient.readManager(id));
        return "manager";
    }

    @GetMapping("/changeManager")
    public String changeManager(@RequestParam Integer trackId, @RequestParam Integer managerId, Model model) {
        model.addAttribute("trackDto", trackClient.changeManager(trackId, managerId));
        model.addAttribute("tracks", trackClient.readAll());
        return "tracks";
    }

    @GetMapping("/interestingSites")
    public String getSites(@RequestParam Integer id, Model model) {
        model.addAttribute("trackId", id);
        model.addAttribute("interestingSites", trackClient.readSites(id));
        return "sites";
    }

    @GetMapping("/addSite")
    public String addSite(@RequestParam Integer trackId, @RequestParam Integer isId, Model model) {
        model.addAttribute("trackDto", trackClient.addSite(trackId, isId));
        model.addAttribute("tracks", trackClient.readAll());
        return "tracks";
    }

    @GetMapping("/deleteSite")
    public String deleteSite(@RequestParam Integer trackId, @RequestParam Integer isId, Model model) {
        model.addAttribute("trackDto", trackClient.deleteSite(trackId, isId));
        model.addAttribute("tracks", trackClient.readAll());
        return "tracks";
    }

    @GetMapping("/edit")
    public String editTrack(@RequestParam Integer id, Model model) {
        model.addAttribute("trackDto", trackClient.readById(id));
        return "trackEdit";
    }

    @PostMapping("/edit")
    public String editTrackSubmit(@ModelAttribute TrackDto trackDto, Model model) {
        model.addAttribute("managerDto", trackClient.update(trackDto));
        return "trackEdit";
    }

    @GetMapping("/add")
    public String addTrack(Model model) {
        model.addAttribute("trackWebModel", new TrackWebModel());
        return "trackAdd";
    }

    @PostMapping("/add")
    public String addTrackSubmit(@ModelAttribute TrackWebModel trackWebModel, Model model) {
        model.addAttribute("trackWebModel", trackClient.create(trackWebModel));
        return "managerSet";
    }

    @GetMapping("/delete")
    public String deleteTrack(@RequestParam Integer id, Model model) {
        model.addAttribute("trackDto", trackClient.readById(id));
        return "trackDelete";
    }

    @PostMapping("/delete")
    public String deleteTrackSubmit(@ModelAttribute TrackDto trackDto, Model model) {
        model.addAttribute("trackDto", trackClient.deleteById(trackDto.id));
        return "trackDeleted";
    }
}
