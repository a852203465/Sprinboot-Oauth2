package com.github.authorization.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * 角色,映射表authority
 * @date 2020/02/21 09:13
 * @author Rong.Jia
 */
@Data
@TableName("authority")
public class Authority implements GrantedAuthority, Serializable {

    private static final long serialVersionUID = -3355358944371583969L;

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

    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }


}
