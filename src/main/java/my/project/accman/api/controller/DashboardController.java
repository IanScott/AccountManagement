package my.project.accman.api.controller;

import my.project.accman.logging.LogMethodSimple;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Controller responsible for service meta data.
 */
@RestController
public class DashboardController {

    /**
     * Method that simply returns true is the service is alive.
     * @return true if alive.
     */
    @LogMethodSimple
    @GetMapping("/v1/info/alive")
    public Boolean isAlive() {
        return Boolean.TRUE;
    }
}