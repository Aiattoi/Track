package com.aiattoi.track.web_client.ui;

import com.aiattoi.track.web_client.data.ManagerClient;
import com.aiattoi.track.web_client.model.ManagerDto;
import com.aiattoi.track.web_client.model.ManagerWebModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/managers")
public class ManagerWebController {
    private final ManagerClient managerClient;

    public ManagerWebController(ManagerClient managerClient) {
        this.managerClient = managerClient;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("managers", managerClient.readAll());
        return "managers";
    }

    @GetMapping("/edit")
    public String editManager (@RequestParam Integer id, Model model) {
        model.addAttribute("managerDto", managerClient.readById(id));
        return "managerEdit";
    }

    @PostMapping("/edit")
    public String editManagerSubmit (@ModelAttribute ManagerDto managerDto, Model model) {
        model.addAttribute("managerDto", managerClient.update(managerDto));
        return "managerEdit";
    }

    @GetMapping("/add")
    public String addManager(Model model) {
        model.addAttribute("managerWebModel", new ManagerWebModel());
        return "managerAdd";
    }

    @PostMapping("/add")
    public String addManagerSubmit(@ModelAttribute ManagerWebModel managerWebModel, Model model) {
        model.addAttribute("managerWebModel", managerClient.create(managerWebModel));
        return "managerAdd";
    }

    @GetMapping("/delete")
    public String deleteManager(@RequestParam Integer id, Model model) {
        model.addAttribute("managerDto", managerClient.readById(id));
        return "managerDelete";
    }

    @PostMapping("/delete")
    public String deleteManagerSubmit(@ModelAttribute ManagerDto managerDto, Model model) {
        model.addAttribute("managerDto", managerClient.deleteById(managerDto.id));
        return "managerDeleted";
    }

    @GetMapping("/selectChange")
    public String listChange(@RequestParam Integer id, Model model) {
        model.addAttribute("trackId", id);
        model.addAttribute("managers", managerClient.readAll());
        return "managerSelect";
    }
}
