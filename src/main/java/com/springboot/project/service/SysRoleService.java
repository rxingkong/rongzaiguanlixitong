package com.springboot.project.service;

import java.util.List;

public interface SysRoleService {
    List<String> findRolesByUserId(long userId);
}
