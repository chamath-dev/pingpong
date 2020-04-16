package Model;

public class WorkingList  {
    String empname;
    String empid;
    String resone;
    String start_time;



    String division;

    public WorkingList(String empname, String empid, String resone,String start_time,String division) {
        this.empname = empname;
        this.empid = empid;
        this.resone = resone;
        this.start_time =start_time;
        this.division = division;

    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getResone() {
        return resone;
    }

    public void setResone(String resone) {
        this.resone = resone;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }




}
