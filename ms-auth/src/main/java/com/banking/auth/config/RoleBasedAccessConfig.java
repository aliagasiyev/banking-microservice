package com.banking.auth.config;

import com.banking.auth.enums.Role;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RoleBasedAccessConfig {

    private static final Map<Role, RolePermissions> rolePermissions = new HashMap<>();

    static {
        Set<Role> allRoles = new HashSet<>(Arrays.asList(Role.values()));

        rolePermissions.put(Role.SUPER_ADMIN, new RolePermissions(allRoles, allRoles));

        rolePermissions.put(Role.ADMIN, new RolePermissions(
                Set.of(Role.ADMIN, Role.TELLER, Role.LOAN_MANAGER, Role.AUDITOR),
                Set.of(Role.ADMIN, Role.TELLER, Role.LOAN_MANAGER, Role.AUDITOR)
        ));

        rolePermissions.put(Role.TELLER, new RolePermissions(
                Set.of(Role.TELLER),
                Collections.emptySet()
        ));

        rolePermissions.put(Role.AUDITOR, new RolePermissions(
                allRoles,
                Collections.emptySet()
        ));

        rolePermissions.put(Role.LOAN_MANAGER, new RolePermissions(
                Set.of(Role.LOAN_MANAGER),
                Collections.emptySet()
        ));

        rolePermissions.put(Role.USER, new RolePermissions(
                Collections.emptySet(),
                Collections.emptySet()
        ));
    }

    public static RolePermissions getPermissions(Role role) {
        return rolePermissions.getOrDefault(role, new RolePermissions(Collections.emptySet(), Collections.emptySet()));
    }
}