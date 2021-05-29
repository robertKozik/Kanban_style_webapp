package live.rkozik.pm_app.backend.controllers;

import live.rkozik.pm_app.backend.dtos.SimplifiedDto;
import live.rkozik.pm_app.backend.services.HugeTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class TasksController extends AbstractController {

    private final HugeTaskService service;

    @Autowired
    public TasksController(HugeTaskService service) {
        super(TasksController.class);

        this.service = service;
    }

    @GetMapping("/user/{id}/task")
    private ResponseEntity< List<SimplifiedDto> > getHugeTasksByUserId(@PathVariable Long id) {
        logger.info(this.getClass().toString()+":getHugeTasksByUserId:"+getTime());
        List<SimplifiedDto> projectList = service.getTasksByIdUser(id);

        if(projectList.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(projectList);
    }

    @GetMapping("/project/{id}/task")
    private ResponseEntity< List<SimplifiedDto> > getHugeTasksByProjectId(@PathVariable Long id) {
        logger.info(this.getClass().toString()+":getHugeTasksByProjectId:"+getTime());
        List<SimplifiedDto> projectList = service.getTasksByIdProject(id);

        if(projectList.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(projectList);
    }

    @GetMapping("/task/{id}")
    private ResponseEntity<SimplifiedDto> getHugeTaskById(@PathVariable Long id) {
        logger.info(this.getClass().toString()+":getHugeTaskById:"+getTime());

        return ResponseEntity.ok(service.getTaskById(id));
    }

    @PostMapping("/project/{id}/task")
    private ResponseEntity<SimplifiedDto> createHugeTask(@PathVariable Long id, @RequestBody SimplifiedDto task) {
        logger.info(this.getClass().toString()+":createProject:"+getTime());
        SimplifiedDto savedProject = service.dispatchTask(task, id);

        return ResponseEntity.ok(savedProject);
    }

    @PutMapping("/task")
    private ResponseEntity<SimplifiedDto> updateHugeTask(@RequestBody SimplifiedDto task) {
        logger.info(this.getClass().toString()+":createProject:"+getTime());
        SimplifiedDto savedProject = service.updateTask(task);

        return ResponseEntity.ok(savedProject);
    }

    @DeleteMapping("/task")
    private ResponseEntity<Long> removeHugeTask(@RequestBody SimplifiedDto task) {
        logger.info(this.getClass().toString()+":removeProject:"+getTime());

        return service.deleteTask(task)? ResponseEntity.ok(task.getId()): ResponseEntity.badRequest().build();
    }
}
