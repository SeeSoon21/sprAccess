package access.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("new_record")
public class AddController {

    @GetMapping
    public String getNewRecordPage() {
        return "new_record";
    }
}
