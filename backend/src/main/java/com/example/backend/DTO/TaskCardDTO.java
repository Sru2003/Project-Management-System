package com.example.backend.DTO;

import javax.xml.crypto.Data;
import java.util.Date;

public class TaskCardDTO {
    private Long id;
    private String name;

    private Date startDate;

    private Date endDate;

    private String status;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TaskCardDTO(Long id, String name, Date startDate, Date endDate, String status) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public TaskCardDTO(Long id, String name) {
        this.id = id;
        this.name = name;

    }

    public TaskCardDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
