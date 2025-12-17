package com.myadmin.modules.system.entity;

import com.myadmin.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * 系统用户实体类
 * <p>
 * 存储系统用户的基本信息，包括：
 * <ul>
 *     <li>username - 用户名（唯一标识）</li>
 *     <li>password - 密码（加密存储）</li>
 *     <li>nickname - 用户昵称</li>
 *     <li>email - 电子邮箱</li>
 *     <li>phone - 手机号码</li>
 *     <li>avatar - 头像URL</li>
 *     <li>status - 账号状态</li>
 * </ul>
 * </p>
 *
 * @author myadmin
 * @since 1.0.0
 */
@Getter
@Setter
@Entity
@Table(name = "sys_user", indexes = {
        @Index(name = "idx_sys_user_username", columnList = "username"),
        @Index(name = "idx_sys_user_status", columnList = "status")
})
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     * <p>
     * 用于登录的唯一标识
     * </p>
     */
    @Column(name = "username", length = 50, nullable = false, unique = true)
    private String username;

    /**
     * 密码
     * <p>
     * 使用 BCrypt 加密存储
     * </p>
     */
    @Column(name = "password", length = 100, nullable = false)
    private String password;

    /**
     * 用户昵称
     * <p>
     * 用于展示的名称
     * </p>
     */
    @Column(name = "nickname", length = 50)
    private String nickname;

    /**
     * 电子邮箱
     */
    @Column(name = "email", length = 100)
    private String email;

    /**
     * 手机号码
     */
    @Column(name = "phone", length = 20)
    private String phone;

    /**
     * 头像URL
     */
    @Column(name = "avatar", length = 255)
    private String avatar;

    /**
     * 账号状态
     * <p>
     * 0: 禁用<br>
     * 1: 启用
     * </p>
     */
    @Column(name = "status", nullable = false)
    private Integer status = 1;

    /**
     * 用户拥有的角色列表
     * <p>
     * 多对多关系，通过中间表 sys_user_role 关联
     * </p>
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "sys_user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<SysRole> roles = new HashSet<>();
}
