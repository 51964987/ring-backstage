package ringbackstage.web.model.role;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import ringbackstage.web.model.jsonserializer.CommOperSerializer;
/**
 * 角色
 * @author ring
 * @date 2017年12月25日 16:17:13
 * @version V1.0
 */
public class AdminRole implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private BigDecimal sort;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createDate;
	private String createBy;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updateDate;
	private String updateBy;
	private String remarks;
	private String delFlag;
	private String enabled;
	@JsonSerialize(using=CommOperSerializer.class)
	private String oper;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getSort() {
		return sort;
	}
	public void setSort(BigDecimal sort) {
		this.sort = sort;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}
}