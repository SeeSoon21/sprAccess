package access.controller;

import access.service.HelpRecordService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        HelpRecordService.getStringFieldsOfClass(className);

        //HelpRecordService.getStringFieldsOfClass(className)
        return "get_record";
    }

    @PostMapping("/{className}/{id}")
    public void toChangeRecord(
            @PathVariable("className") String className,
            @PathVariable("id") String id,
            @RequestParam Object object) {
        System.out.println("post object:" + object.toString());
    }


}
