package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OrderProductPK implements Serializable {
	
	@Column(name="ORDERID")
	protected Integer orderId;
	
	@Column(name="PRODUCTID")
	protected Integer productId;
	
	public OrderProductPK(){}
	public OrderProductPK(Integer orderId, Integer productId){
		this.orderId = orderId;
		this.productId = productId;
	}
	
	public Integer getOrderId(){
		return this.orderId;
	}
	public void setOrderId(Integer id){
		this.orderId = id;
	}
	
	public Integer getProductId(){
		return this.productId;
	}
	public void setProductId(Integer id){
		this.productId = id;
	}
	
	public boolean equals( Object obj ) {
		boolean result = false;
		OrderProductPK i = (OrderProductPK) obj;
		if( i.orderId == this.orderId && i.productId == this.productId)
			result = true;
		return result;
	}
}
