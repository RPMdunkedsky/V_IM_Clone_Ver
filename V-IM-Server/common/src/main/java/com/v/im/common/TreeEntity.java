package com.v.im.common;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author 乐天
 */
@Data
public class TreeEntity<T> extends BaseEntity<T> {


    @TableId
    private String id;

    /**
     * 所有父级编号
     */
    protected String parentIds;

    /**
     * 所有父级编号
     */
    protected String parentId;


}
