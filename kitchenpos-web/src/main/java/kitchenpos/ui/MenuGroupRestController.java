package kitchenpos.ui;

import kitchenpos.application.MenuGroupService;
import kitchenpos.dto.menugroup.request.MenuGroupCreateRequest;
import kitchenpos.dto.menugroup.response.MenuGroupResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
public class MenuGroupRestController {
    private final MenuGroupService menuGroupService;

    public MenuGroupRestController(final MenuGroupService menuGroupService) {
        this.menuGroupService = menuGroupService;
    }

    @PostMapping("/api/menu-groups")
    public ResponseEntity<Void> create(@RequestBody final MenuGroupCreateRequest request) {
        final Long id = menuGroupService.create(request);
        final URI uri = URI.create("/api/menu-groups/" + id);
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/api/menu-groups")
    public ResponseEntity<List<MenuGroupResponse>> list() {
        return ResponseEntity.ok().body(menuGroupService.list());
    }
}
