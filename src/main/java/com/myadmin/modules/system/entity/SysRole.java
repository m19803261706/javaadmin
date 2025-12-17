package com.myadmin.modules.system.entity;

import com.myadmin.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * 系统角色实体类
 * <p>
 * 存储系统角色信息，用于权限管理：
 * <ul>
 *     <li>code - 角色编码（唯一标识，如 admin、user）</li>
 *     <li>name - 角色名称（用于展示）</li>
 *     <li>description - 角色描述</li>
 *     <li>status - 角色状态</li>
 * </ul>
 * </p>
 *
 * @author myadmin
 * @since 1.0.0
 */
@Getter
@Setter
@Entity
@Table(name = "sys_role", indexes = {
        @Index(name = "idx_sys_role_code", columnList = "code")
})
public class SysRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 角色编码
     * <p>
     * 角色的唯一标识符，用于程序中的权限判断<br>
     * 示例: admin, user, guest
     * </p>
     */
    @Column(name = "code", length = 50, nullable = false, unique = true)
    private String code;

    /**
     * 角色名称
     * <p>
     * 用于界面展示的名称
     * </p>
     */
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    /**
     * 角色描述
     */
    @Column(name = "description", length = 200)
    private String description;

    /**
     * 角色状态
     * <p>
     * 0: 禁用<br>
     * 1: 启用
     * </p>
     */
    @Column(name = "status", nullable = false)
    private Integer status = 1;

    /**
     * 角色拥有的菜单列表
     * <p>
     * 多对多关系，通过中间表 sys_role_menu 关联
     * </p>
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "sys_role_menu",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id", referencedColumnName = "id")
    )
    private Set<SysMenu> menus = new HashSet<>();

    /**
     * 拥有此角色的用户列表
     * <p>
     * 双向多对多关系的反向引用
     * </p>
     */
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<SysUser> users = new HashSet<>();
}
