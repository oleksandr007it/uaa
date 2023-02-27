package com.idevhub.tapas.web.rest;

import com.idevhub.tapas.HasPrivilege;
import com.idevhub.tapas.domain.enumeration.PrivilegeType;
import com.idevhub.tapas.privilege.service.constant.PRIVILEGE;
import com.idevhub.tapas.security.SecurityUtils;
import com.idevhub.tapas.service.*;
import com.idevhub.tapas.service.dto.PrivilegeCategoryDTO;
import com.idevhub.tapas.service.dto.PrivilegeGroupGeneralDTO;
import com.idevhub.tapas.service.dto.PrivilegeGroupWithPrivilegesDTO;
import com.idevhub.tapas.web.rest.errors.BadRequestAlertException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static java.util.stream.Stream.concat;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * REST controller for managing Address.
 */
@RestController
@RequestMapping("/api/privileges-and-groups")
@RequiredArgsConstructor
@Slf4j
public class PrivilegeResource {

    private final PrivilegeForRepresentService privilegeForRepresentService;
    private final PrivilegeForUserService privilegeForUserService;
    private final PrivilegeForSubjectFindService privilegeForSubjectFindService;
    private final PrivilegeForSubjectDeleteService privilegeForSubjectDeleteService;
    private final PrivilegeForSubjectCreationService privilegeForSubjectCreationService;
    private final PrivilegeCategoriesService privilegeCategoriesService;

    @RequestMapping(method = GET, value = {
        "/user/{userId}/privilege-codes",
        "/user/privilege-codes"
    })
    public Set<String> getUserPrivilegeCodes(@PathVariable(name = "userId", required = false) Optional<Long> optionalUserId) {
        var userId = optionalUserId.orElse(SecurityUtils.getCurrentUserIdIfExists());
        log.debug("Request for getting all privileges for userId={}", userId);

        var userPrivileges = privilegeForUserService.getPrivilegeCodesForUser(userId);
        var representPrivileges = privilegeForRepresentService.getPrivilegeCodesForUserCurrentRepresent(userId);

        return concat(userPrivileges.stream(), representPrivileges.stream()).collect(toSet());
    }


    @HasPrivilege(PRIVILEGE.LEGAL_ENTITY.V1_2_1)
    @GetMapping("/subject/{subjectId}/groups")
    public Set<PrivilegeGroupGeneralDTO> getSubjectGroups(@PathVariable Long subjectId) {
        log.debug("Request for getting privilege groups for subjectId={}", subjectId);

        return privilegeForSubjectFindService.getGroupsForSubject(subjectId);
    }

    @HasPrivilege(PRIVILEGE.LEGAL_ENTITY.V1_2_5)
    @GetMapping(value = "/subject/{subjectId}/groups/{groupCode}")
    public PrivilegeGroupWithPrivilegesDTO getSubjectGroup(@PathVariable Long subjectId, @PathVariable String groupCode) {
        log.debug("Request for getting privilege groups");

        return privilegeForSubjectFindService.getGroupForSubject(subjectId, groupCode);
    }

    @HasPrivilege(PRIVILEGE.LEGAL_ENTITY.V1_2_4)
    @DeleteMapping("/subject/groups/{groupCode}")
    public ResponseEntity<Void> deleteSubjectGroup(@PathVariable String groupCode) {
        log.debug("Request for getting privilege groups for groupCode={}", groupCode);

        privilegeForSubjectDeleteService.deleteGroupForSubject(SecurityUtils.getCurrentUserIdIfExists(), groupCode);

        return ResponseEntity.noContent().build();
    }

    @HasPrivilege(PRIVILEGE.LEGAL_ENTITY.V1_2_2)
    @PostMapping(value = "/subject/{subjectId}/groups")
    public PrivilegeGroupWithPrivilegesDTO createSubjectGroup(@PathVariable Long subjectId, @Valid @RequestBody PrivilegeGroupWithPrivilegesDTO groupDto) {
        log.debug("Request for getting privilege groups");
        if (groupDto.getCode() != null)
            throw new BadRequestAlertException("New group can't have a group code", "privileges", "groupCodeShouldNotExist");

        return privilegeForSubjectCreationService.createOrUpdateGroup(subjectId, groupDto);
    }

    @HasPrivilege(PRIVILEGE.LEGAL_ENTITY.V1_2_3)
    @PutMapping(value = "/subject/{subjectId}/groups")
    public PrivilegeGroupWithPrivilegesDTO updateSubjectGroup(@PathVariable Long subjectId, @Valid @RequestBody PrivilegeGroupWithPrivilegesDTO groupDto) {
        log.debug("Request for getting privilege groups");

        return privilegeForSubjectCreationService.createOrUpdateGroup(subjectId, groupDto);
    }


    @GetMapping("/represent/{representId}/groups")
    public Set<PrivilegeGroupGeneralDTO> getRepresentGroups(@PathVariable Long representId) {
        log.debug("Request for getting privilege groups for representId={}", representId);

        return privilegeForRepresentService.getPrivilegeGroupsForRepresent(representId);
    }

    @GetMapping("/categories")
    public Set<PrivilegeCategoryDTO> getPrivilegeCategories(@RequestParam PrivilegeType privilegeType) {
        log.debug("Request for getting privilege categories by type={}", privilegeType);

        return privilegeCategoriesService.getCategoriesWithPrivileges(privilegeType);
    }
}
