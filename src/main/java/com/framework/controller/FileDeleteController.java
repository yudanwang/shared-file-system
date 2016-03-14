package com.framework.controller;

/**
 * Created by WangYudan on 2016/3/11.
 */

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class FileDeleteController {

    @RequestMapping(method = RequestMethod.GET, value = "/delete")
    public String provideUploadInfo(Model model) {
        File rootFolder = new File(Application.ROOT);
        List<String> fileNames = Arrays.stream(rootFolder.listFiles())
                .map(f -> f.getName())
                .collect(Collectors.toList());

        model.addAttribute("files",
                Arrays.stream(rootFolder.listFiles())
                        .sorted(Comparator.comparingLong(f -> -1 * f.lastModified()))
                        .map(f -> f.getName())
                        .collect(Collectors.toList())
        );

        return "deleteForm";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public String handleFileUpload(@RequestParam("name") String name,
                                   RedirectAttributes redirectAttributes){

        String fullPath = Application.ROOT + "/" + name;
        File deletefile = new File(fullPath);
        if (!deletefile.exists()){
            redirectAttributes.addFlashAttribute("message", "You failed to delete "
                    + name + " because the Artifact is not exist");
            return "redirect:delete";
        }
        else {
            deletefile.delete();
        }
        return "redirect:delete";
    }
}
