package com.app.emission.EmissionTrack.Entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="emissiontbl")
public class Emission {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private long id;
	
	//@GeneratedValue(generator = "UUID")
	//@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")		
	@Column(name="uuid")
	private UUID uuid;
	
	@Column(name="productId")
	private String productId;

	@Column(name="co2")
	private int co2;

	@Column(name="recorded_at", nullable=false, updatable=false)
	//@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-uuuu'T'HH:mm:ss:SSSXXXXX")
	private LocalDateTime recordedAt;
	
	@Column(name="status")
	private String status;

	@Override
	public String toString() {
		return "Emission [id=" + id + ", uuid=" + uuid + ", productId=" + productId + ", co2=" + co2 + ", recordedAt="
				+ recordedAt + ", status=" + status + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getCo2() {
		return co2;
	}

	public void setCo2(int co2) {
		this.co2 = co2;
	}

	public LocalDateTime getRecordedAt() {
		return recordedAt;
	}

	public void setRecordedAt(LocalDateTime recordedAt) {
		this.recordedAt = recordedAt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Emission(long id, UUID uuid, String productId, int co2, LocalDateTime recordedAt, String status) {
		super();
		this.id = id;
		this.uuid = uuid;
		this.productId = productId;
		this.co2 = co2;
		this.recordedAt = recordedAt;
		this.status = status;
	}

	public Emission() {
		super();
	}
	
}
