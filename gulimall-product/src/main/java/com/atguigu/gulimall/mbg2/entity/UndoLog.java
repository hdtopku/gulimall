package com.atguigu.gulimall.mbg2.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.sql.Blob;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2022-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("undo_log")
public class UndoLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("branch_id")
    private Long branchId;

    @TableField("xid")
    private String xid;

    @TableField("context")
    private String context;

    @TableField("rollback_info")
    private Blob rollbackInfo;

    @TableField("log_status")
    private Integer logStatus;

    @TableField("log_created")
    private Date logCreated;

    @TableField("log_modified")
    private Date logModified;

    @TableField("ext")
    private String ext;

}
