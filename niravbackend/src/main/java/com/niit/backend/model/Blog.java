package com.niit.backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "raj_blog")
public class Blog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int blogid;
	private String blogname;
	private int userId;
	private String blogDescription;

	private String status; // Will include A,P,R as keyword for Approved,Pending
							// and Rejected respectively
	private String createddate;
	private String blogComments;

	public int getBlogid() {
		return blogid;
	}

	public void setBlogid(int blogid) {
		this.blogid = blogid;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBlogname() {
		return blogname;
	}

	public void setBlogname(String blogname) {
		this.blogname = blogname;
	}

	public String getBlogDescription() {
		return blogDescription;
	}

	public void setBlogDescription(String blogDescription) {
		this.blogDescription = blogDescription;
	}

	public String getBlogComments() {
		return blogComments;
	}

	public void setBlogComments(String blogComments) {
		this.blogComments = blogComments;
	}

	public String getCreateddate() {
		return createddate;
	}

	public void setCreateddate(String createddate) {
		this.createddate = createddate;
	}

	public String toString() {
		return "Blog [blogid=" + blogid + ",createddate=" + createddate + ", blogname=" + blogname + ", userId="
				+ userId + ", blogDescription=" + blogDescription + ", status=" + status + "," + ", blogComments="
				+ blogComments + "]";
	}
}
