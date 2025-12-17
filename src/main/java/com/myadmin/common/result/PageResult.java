package com.myadmin.common.result;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果封装类
 * <p>
 * 用于封装分页查询的返回数据，包含：
 * <ul>
 *     <li>records - 数据列表</li>
 *     <li>total - 总记录数</li>
 *     <li>page - 当前页码</li>
 *     <li>size - 每页大小</li>
 *     <li>pages - 总页数</li>
 * </ul>
 * </p>
 *
 * @param <T> 数据类型
 * @author myadmin
 * @since 1.0.0
 */
@Data
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据列表
     */
    private List<T> records;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 当前页码（从 1 开始）
     */
    private Integer page;

    /**
     * 每页大小
     */
    private Integer size;

    /**
     * 总页数
     */
    private Integer pages;

    /**
     * 私有构造函数
     */
    private PageResult() {
    }

    /**
     * 从 Spring Data Page 对象构建分页结果
     *
     * @param page Spring Data 分页对象
     * @param <T>  数据类型
     * @return 分页结果
     */
    public static <T> PageResult<T> of(Page<T> page) {
        PageResult<T> result = new PageResult<>();
        result.setRecords(page.getContent());
        result.setTotal(page.getTotalElements());
        result.setPage(page.getNumber() + 1); // Spring Data 页码从 0 开始，转换为从 1 开始
        result.setSize(page.getSize());
        result.setPages(page.getTotalPages());
        return result;
    }

    /**
     * 手动构建分页结果
     *
     * @param records 数据列表
     * @param total   总记录数
     * @param page    当前页码
     * @param size    每页大小
     * @param <T>     数据类型
     * @return 分页结果
     */
    public static <T> PageResult<T> of(List<T> records, Long total, Integer page, Integer size) {
        PageResult<T> result = new PageResult<>();
        result.setRecords(records);
        result.setTotal(total);
        result.setPage(page);
        result.setSize(size);
        result.setPages((int) Math.ceil((double) total / size));
        return result;
    }

    /**
     * 判断是否有下一页
     *
     * @return 是否有下一页
     */
    public boolean hasNext() {
        return this.page < this.pages;
    }

    /**
     * 判断是否有上一页
     *
     * @return 是否有上一页
     */
    public boolean hasPrevious() {
        return this.page > 1;
    }
}
