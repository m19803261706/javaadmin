package com.myadmin.modules.system.entity;

import com.myadmin.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * 系统菜单实体类
 * <p>
 * 存储系统菜单和权限按钮信息，支持树形结构：
 * <ul>
 *     <li>parentId - 父菜单ID（0表示顶级菜单）</li>
 *     <li>name - 菜单名称</li>
 *     <li>path - 前端路由路径</li>
 *     <li>component - 前端组件路径</li>
 *     <li>type - 菜单类型（目录/菜单/按钮）</li>
 *     <li>permission - 权限标识</li>
 * </ul>
 * </p>
 *
 * @author myadmin
 * @since 1.0.0
 */
@Getter
@Setter
@Entity
@Table(name = "sys_menu", indexes = {
        @Index(name = "idx_sys_menu_parent_id", columnList = "parent_id")
})
public class SysMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单类型常量 - 目录
     */
    public static final Integer TYPE_DIRECTORY = 0;

    /**
     * 菜单类型常量 - 菜单
     */
    public static final Integer TYPE_MENU = 1;

    /**
     * 菜单类型常量 - 按钮
     */
    public static final Integer TYPE_BUTTON = 2;

    /**
     * 父菜单ID
     * <p>
     * 0 表示顶级菜单
     * </p>
     */
    @Column(name = "parent_id", nullable = false)
    private Long parentId = 0L;

    /**
     * 菜单名称
     * <p>
     * 显示在导航栏的名称
     * </p>
     */
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    /**
     * 路由路径
     * <p>
     * 前端路由的 path 属性
     * </p>
     */
    @Column(name = "path", length = 200)
    private String path;

    /**
     * 组件路径
     * <p>
     * 前端组件的文件路径，如 system/user/index
     * </p>
     */
    @Column(name = "component", length = 200)
    private String component;

    /**
     * 菜单图标
     * <p>
     * 图标名称，对应前端图标库
     * </p>
     */
    @Column(name = "icon", length = 50)
    private String icon;

    /**
     * 排序号
     * <p>
     * 数值越小越靠前
     * </p>
     */
    @Column(name = "sort", nullable = false)
    private Integer sort = 0;

    /**
     * 菜单类型
     * <p>
     * 0: 目录<br>
     * 1: 菜单<br>
     * 2: 按钮
     * </p>
     */
    @Column(name = "type", nullable = false)
    private Integer type = TYPE_MENU;

    /**
     * 权限标识
     * <p>
     * 格式: {模块}:{资源}:{操作}<br>
     * 示例: system:user:list, system:user:add
     * </p>
     */
    @Column(name = "permission", length = 100)
    private String permission;

    /**
     * 是否可见
     * <p>
     * 0: 隐藏<br>
     * 1: 显示
     * </p>
     */
    @Column(name = "visible", nullable = false)
    private Integer visible = 1;

    /**
     * 菜单状态
     * <p>
     * 0: 禁用<br>
     * 1: 启用
     * </p>
     */
    @Column(name = "status", nullable = false)
    private Integer status = 1;

    /**
     * 拥有此菜单的角色列表
     * <p>
     * 双向多对多关系的反向引用
     * </p>
     */
    @ManyToMany(mappedBy = "menus", fetch = FetchType.LAZY)
    private Set<SysRole> roles = new HashSet<>();
}
