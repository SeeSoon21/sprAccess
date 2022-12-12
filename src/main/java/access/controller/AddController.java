package access.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/record")
public class AddController {
    @GetMapping("{className}/{id}")
    @ResponseBody
    public String getChangeableRecord(
            @PathVariable("className") String className,
            @PathVariable("id") String id
    ) {
        System.out.println("className" + className);
        System.out.println("id" + id);
        return className + ", " +id;
    }
}
