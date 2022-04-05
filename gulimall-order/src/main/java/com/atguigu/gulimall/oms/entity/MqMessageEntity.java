package com.atguigu.gulimall.oms.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2022-04-04 00:23:02
 */
@Data
@TableName("mq_message")
public class MqMessageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String messageId;
	/**
	 * JSON
	 */
	private String content;
	/**
	 * 
	 */
	private String toExchange;
	/**
	 * 
	 */
	private String classType;
	/**
	 * 0-新建 1-已发送 2-错误抵达 3-已抵达
	 */
	private Integer messageStatus;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Date updateTime;

}
