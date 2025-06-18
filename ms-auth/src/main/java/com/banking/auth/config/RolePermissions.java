package com.banking.auth.config;

import com.banking.auth.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class RolePermissions {

    public final Set<Role> canCreate;
    public final Set<Role> canDelete;

    public RolePermissions(Set<Role> canCreate, Set<Role> canDelete) {
        this.canCreate = canCreate;
        this.canDelete = canDelete;
    }

    public boolean canDelete(Role targetRole) {
        return canDelete.contains(targetRole);
    }

    public boolean canCreate(Role targetRole) {
        return canCreate.contains(targetRole);
    }
}
