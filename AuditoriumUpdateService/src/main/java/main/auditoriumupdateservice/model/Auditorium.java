package main.auditoriumupdateservice.model;

import lombok.Data;

@Data
public class Auditorium {
    private String id;
    private String number;
    private String name;
    private String building;
    private String type;
    private String purpose;
    private String department;
    private boolean canEdit;
}
