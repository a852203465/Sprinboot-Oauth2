package com.github.authorization.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 登录凭证，映射credentials表
 * @date 2020/02/21 09:13
 * @author Rong.Jia
 */
@Data
@TableName("credentials")
public class Credentials implements Serializable {

    private static final long serialVersionUID = -2432679430938552668L;

    /**
     * 主键
     * TableId中可以决定主键的类型,不写会采取默认值,默认值可以在yml中配置
     * AUTO: 数据库ID自增
     * INPUT: 用户输入ID
     * ID_WORKER: 全局唯一ID，Long类型的主键
     * ID_WORKER_STR: 字符串全局唯一ID
     * UUID: 全局唯一ID，UUID类型的主键
     * NONE: 该类型为未设置主键类型
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    private Integer version;

    private String name;

    private String password;

    private Boolean enabled;

}
