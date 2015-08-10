package sh.odb.core.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author peeply
 * @version 创建时间：2012-8-1 下午02:33:02
 * 
 *          所有Po都要直接或间接继承此类，此类实现Serializable接口 JVM能方便的将Po实例序列化到硬盘中，或者通过流的方式进行发送
 *          为缓存、集群功能带来便利
 */

public class BasePojo implements Serializable {

	protected Long id;

	protected int status = 1;

	protected int sort = 0;

	protected Timestamp createTime = new Timestamp(System.currentTimeMillis());

	protected Timestamp modifyTime = new Timestamp(System.currentTimeMillis());

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}
}
