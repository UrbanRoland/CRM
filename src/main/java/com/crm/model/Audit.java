package com.crm.model;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
	public abstract class Audit<U> implements Serializable {
	
	
	 @CreatedDate
	    @Column(name = "CREATED_DATE")
	    @Temporal(TemporalType.TIMESTAMP)
	    protected Date createdDate;

	    @CreatedBy
	    @Column(name = "CREATED_BY")
	    protected String createdBy;

	    @LastModifiedBy
	    @Column(name = "LAST_MODIFIED_BY")
	    protected String lastModifiedBy="";

	 
	    @LastModifiedDate
	    @Column(name = "LAST_MODIFIED_DATE")
	    @Temporal(TemporalType.TIMESTAMP)
	    protected Date lastModifiedDate;

		public Date getCreatedDate() {
			return createdDate;
		}

		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}

		public String getCreatedBy() {
			return createdBy;
		}

		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}

		public String getLastModifiedBy() {
			return lastModifiedBy;
		}

		public void setLastModifiedBy(String lastModifiedBy) {
			this.lastModifiedBy = lastModifiedBy;
		}

		public Date getLastModifiedDate() {
			return lastModifiedDate;
		}

		public void setLastModifiedDate(Date lastModifiedDate) {
			this.lastModifiedDate = lastModifiedDate;
		}

	    
	    
}
