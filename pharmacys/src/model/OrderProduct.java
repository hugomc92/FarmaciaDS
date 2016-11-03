package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name="ORDER_PRODUCT")
@XmlRootElement(name="orderProduct")
@XmlType(propOrder={
		"orderId",
		"productId",
		"quantity"
})
public class OrderProduct implements Serializable {
	
	@EmbeddedId
	private OrderProductPK orderProductPK;
	
	@Column(name="QUANTITY")
	private int quantity;
	
	public OrderProduct(){
		orderProductPK = new OrderProductPK();
	}
	
	@XmlElement(required=true)
	public int getOrderId(){
		return this.orderProductPK.getOrderId();				
	}
	public void setOrderId(int id){
		this.orderProductPK.setOrderId(id);
	}
	
	@XmlElement(required=true)
	public int getProductId(){
		return this.orderProductPK.getProductId();
	}
	public void setProductId(int id){
		this.orderProductPK.setProductId(id);
	}
	
	@XmlElement(required=true)
	public int getQuantity(){
		return this.quantity;
	}
	public void setQuantity(int q){
		this.quantity = q;
	}
}
