package access.controller;

import access.service.HelpRecordService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/record")
public class AddController {
    private ArrayList<?> list;

    @GetMapping("/{className}/{id}")
    public String getChangeableRecord(
            @PathVariable("className") String className,
            @PathVariable("id") String id
    ) {
        System.out.println("className" + className);
        System.out.println("id" + id);

        //HelpRecordService.getStringFieldsOfClass(className)
        return "change_record";
    }


}
