package com.myadmin.common.base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础实体类
 * <p>
 * 所有实体类的父类，包含通用字段：
 * <ul>
 *     <li>id - 主键</li>
 *     <li>createTime - 创建时间</li>
 *     <li>updateTime - 更新时间</li>
 * </ul>
 * </p>
 *
 * @author myadmin
 * @since 1.0.0
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键 ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 创建时间
     * <p>
     * 由 JPA 审计功能自动填充
     * </p>
     */
    @CreatedDate
    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime;

    /**
     * 更新时间
     * <p>
     * 由 JPA 审计功能自动填充
     * </p>
     */
    @LastModifiedDate
    @Column(name = "update_time")
    private LocalDateTime updateTime;
}
